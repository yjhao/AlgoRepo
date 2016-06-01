#140- Word Break II

## 题意
根据字典里面的单词， 给出所有可能的 word break 组合

##解答
首先， 因为是给出 所有 的 组合， 所以肯定是DFS搜索。

但是， 因为 根据 “相同的 string, 最后的答案是唯一的”, 所以可以采用“截枝” 的手法， 用一个hashmap来存一下 当前的 string 有没有出现， 和如果出现了， 答案是多少。

因为是采用的 截枝， 所以 recursion 的返回值应该就是 这个 list。

采用截枝的话 ， DFS 是 自底向上 的 return 和“得到答案”。

##注意细节

1. 对于一个String的最末尾之后， 返回的答案应该是 ”“， 而不是什么都没有。 ”什么都没有“发生在 一个 substring 完全一个答案都没有的时候。

比如说 [abcd] ,[abc, abcd], 在 c 之后的 d 返回的就应该是个空list, 说明这个 substring 一个答案都没有。

如果 d 之后也返回 空list 的话， 就不能分辨出来 究竟是哪种情况。 因为 第一种情况可以组成答案， 而第二种答案不可能组成答案。

2. 在合并string 的时候， 如果 第二个部分是 空string （“”)， 两个合并的部分中间是不应该放 空格 的。不然就会出现 "a hand " ， 而正确答案应该是 “a hand"。

##代码
```
public class Solution {
    public List<String> wordBreak(String s, Set<String> wordDict) {
        List<String> res = new ArrayList<String>();
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        return helper(s, wordDict, map, res);
    }
    
    public List<String> helper(String s, Set<String> wordDict, HashMap<String, List<String>> map, List<String> res){
        if (s.equals("")){
            List<String> list = new ArrayList<String>();
            list.add("");
            return list;
        }
            
        if (map.containsKey(s))     return map.get(s);
        
        List<String> list = new ArrayList<String>();
        
        for (String word : wordDict){
            if (s.startsWith(word)){
                String first = word;
                for (String next : helper(s.substring(word.length()), wordDict, map, res)){
                    String cur = first;
                    if (next.equals("")){
                        cur = first;
                    }else{
                        cur = first + " " + next;
                    }
                    list.add(cur);
                }
            }
        }
        map.put(s, list);
        return list;
    }
}
```

