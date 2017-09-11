# 526-Word Abbreviation
## 题意
给一个字符串list， 要给每个字符串一个缩写， 第一个字母 + 中间的长度 + 最后一个字母。但是限制条件是， 对于这个单词的缩写， 不能表达另外一个单词， 否则的话， 需要增加前面的字母的长度， 并缩短“中间的长度”， 比如对于 interval 和 internal， 满足条件的缩写分别得是： interval internal。问最后出来的对于每个单词的缩写都是什么。 


## 解题一
首先可以采用greedy 的方法， 对每个单词进行最短的缩写， 然后group by 缩写，如果发现某一个缩写有多个不同的单词与之对应， 那么对于这些单词， 要扩大 prefix 的长度， 以满足不重合。

细节在于， 假设这个list中有 i j k 三个位置的字符串，我们可以先扩大 i 的前缀长度， 使得在i处的缩写不能满足 j k之后； 当我们处理到 J 处的时候， 要扩大j的前缀长度， 并寻找他有没有满足其他的字符串缩写的时候， 我们不仅要把它拿来比较 k 处的， 还应该比较 i 处的， 因为之前即使我们已经找到 I 处的unique的缩写， 但是那只能保证 i 的缩写不能代表j的， 不能保证现在 j 的缩写不能代表 i 的。

所以list中的循环是

```
for (int i=0; i<..){
	for (int j=0; j<..){
```

要改进这个算法， 首先要知道即使 i 已经找出了unique的缩写， 为什么 j 还需要 查询 i 处的状态， 是因为 i 在增加前缀长度的时候， j 处的前缀长度也应该相应增加并被保存起来。

比如 对于 abcdefg, abceefg, abcdefg 初始状态是 a5g, 它同样可以代表 abceefg, 所以我们最终把它变为 abcd2g; 

<strike>当我们对于 abcddfg 做转换的时候， 其实是没有必要在 b c d 处作出转换的， 因为在他们之前的prefix和 abcdefg 一模一样， 做出来的缩写就会是一样。 

所以我们应该记录， 第一个使得 abcdefg 的缩写Unique的位置， 那么之后的所有单词， 都最起码得从这个位置开始进行转换所写， 那么这样的话， 我们就只需要比较 j 后面的单词了， 因为我们知道， j 之前的单词一定是不重合的。</strike>


***前面的两段是错误的*** ，特别是 “<strike>第一个使得 abcdefg 的缩写Unique的位置， 那么之后的所有单词， 都最起码得从这个位置开始进行转换所写</strike>”

举例： bbbbaa, bbbbca, baaaaa， 当visit到 bbbbaa的时候， 我们走完了所有五个字符， 但是对于 baaaaa， 我们不需要从第五个字符开始， 只需要从第二个开始就可以了。因为当 bbbbaa走到第二个字母的时候， 和他相似的只有 bbbbca了， 并没有baaaaa了。

**所以正确的改进方法应该是**， 建立一个 dynamic 的list， 只保存在当前相似的缩写， 因为在当前不相似的缩写， 我们已经没有必要再进行缩写了。然后对于这些相似的缩写， 我们再进行递进前缀。

## 代码, 优化版本
```
class Solution {
    public List<String> wordsAbbreviation(List<String> dict) {
        List<String> res = new ArrayList<String>();
        int[] preLen = new int[dict.size()];
        Arrays.fill(preLen, 1);
        String[] strs = new String[dict.size()];
        
        for (int i=0; i<dict.size(); i++){
            strs[i] = getAbbr(dict.get(i), 1);
        }
        
        for (int i=0; i<dict.size(); i++){
            while (true){
                List<Integer> list = new ArrayList<Integer>();
                list.add(i);
                for (int j=i+1; j<dict.size(); j++){
                    if (strs[j].equals(strs[i]))    list.add(j);
                }
                
                if (list.size()==1)  break;
                for (Integer idx : list){
                    strs[idx] = getAbbr(dict.get(idx), preLen[idx]++);
                }
            }
        }
        
        for (String str : strs) res.add(str);
        return res;
    }
    

    
    String getAbbr(String str, int pre){
        if (pre+2 == str.length() || pre +1 == str.length())  return str;
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, pre));
        sb.append(str.length()-1-pre);
        sb.append(str.charAt(str.length()-1));
        return sb.toString();
    }
}
```

## 代码, 优化前
```
class Solution {
    public List<String> wordsAbbreviation(List<String> dict) {
        HashMap<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        HashMap<String, Integer> idxMap = new HashMap<String, Integer>();
        for (int i=0; i<dict.size(); i++){
            idxMap.put(dict.get(i), i);
            String abbr = getAbbr(dict.get(i), 1);
            if (!map.containsKey(abbr)){
                ArrayList<String> list = new ArrayList<String>();
                map.put(abbr, list);
            }
            map.get(abbr).add(dict.get(i));
        }
        
        String[] strs = new String[dict.size()];
        List<String> res = new ArrayList<String>();
        
        for (String key : map.keySet()){
            ArrayList<String> candidates = map.get(key);
            if (candidates.size()==1){
                strs[idxMap.get(candidates.get(0))] = key;
                continue;
            }
            for (int i=0; i<candidates.size(); i++){
                String cur = candidates.get(i);
                for (int len=2; len<cur.length()-1; len++){
                    boolean flag = false;
                    String strShort = getAbbr(cur, len);
                    for (int j=0; j<candidates.size(); j++){
                        if (i==j)   continue;
                        if (match(candidates.get(j), strShort)){
                            flag = true;
                            break;
                        }
                    }
                    if (!flag){
                        strs[idxMap.get(cur)] = strShort;
                        break;
                    }
                }
            }
        }
        for (String str: strs) res.add(str);
        return res;
    }
    
    boolean match(String str, String abbr){
        boolean match = true;
        if (str.charAt(str.length()-1) != abbr.charAt(abbr.length()-1)) return false;
        for (int i=0; i<abbr.length(); i++){
            char cur = abbr.charAt(i);
            if (cur>'0' && cur<='9')    break;
            if (cur != str.charAt(i)){
                match = false;
                break;
            }
        }
        
        return match;
    }
    
    String getAbbr(String str, int pre){
        if (pre+2 == str.length() || pre +1 == str.length())  return str;
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, pre));
        sb.append(str.length()-1-pre);
        sb.append(str.charAt(str.length()-1));
        return sb.toString();
    }
}
```
