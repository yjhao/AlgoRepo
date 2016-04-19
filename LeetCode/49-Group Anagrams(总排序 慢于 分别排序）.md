#49-Group Anagrams(总排序 慢于 分别排序）
##题意
归类每个单词的变形体， 然后按字典序输出

##解题
具体方法很简单， 就不多说了

细节在于， 如果对**每一个Sub-list分别排序**， 比在一开始对所有的单词排序， 要快得多。

```
public class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<List<String>>();
        //Arrays.sort(strs);
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        
        for (String str : strs){
            char[] charArr = str.toCharArray();
            Arrays.sort(charArr);
            String cur = new String(charArr);
            if (!map.containsKey(cur)){
                ArrayList<String> list = new ArrayList<String>();
                map.put(cur, list);
            }
            ArrayList<String> list = map.get(cur);
            list.add(str);
        }
        
        for (String key : map.keySet()){
            ArrayList<String> list = map.get(key);
            Collections.sort(list);
            res.add(list);
        }
        
        return res;
    }
}
```