# 472- Concatenated Words

## 题意
给一个 string list， 求出在这个list中的， 所有可以由另外两个或者以上的String组成的String。

## 解题
因为是让求出所有的String， 所有每一个string， 至少会被visit一遍。 在visit这个string的时候， 就需要判断能不能有其他的单词来组成。

brutal force， 就是把所有单词放在set里面， 然后visit一个单词的时候， 到某个时候， 判断从之前的起点， 到当前的位置， 有没有单词存在。 如果有的话， 可以选择更新起点， 也可以选择不更新起点， 继续下去。

这个算法的问题是， 判断 String 是不是在 map中， 还需要对string进行一次遍历。 为了解决这个问题， 很自然的就想到了用 trie来代替 map。

## 代码
```
public class Solution {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> res = new ArrayList<String>();
        if (words.length<=1)    return res;
        TrieNode root = new TrieNode();
        for (String word : words)   if (word.length()!=0)   addToTrie(root, word);
        
        for (String word : words){
            if (word.length()==0)   continue;
            if (dfs(word, root, root, 0, 0))    res.add(word);
        }
        return res;
    }
    
    boolean dfs(String word, TrieNode root, TrieNode node, int idx,int cnt){
        if (node==null || idx>word.length())  return false;
    
        if (node.hasWord){
            if (idx==word.length()){
                if (cnt>=1) return true;
                return false;
            }else{
                if (dfs(word, root, root, idx, cnt+1)) return true;
            }
        }
        
        if (idx==word.length())    return false;
        char next = word.charAt(idx);
        if (dfs(word, root, node.children[next-'a'], idx+1, cnt))    return true;
        return false;
    }
    
    void addToTrie(TrieNode root, String word){
        for (char c : word.toCharArray()){
            int idx = c-'a';
            if (root.children[idx]==null)   root.children[idx] = new TrieNode();
            root = root.children[idx];
        }
        root.hasWord = true;
    }
}

class TrieNode{
    boolean hasWord = false;
    TrieNode[] children = new TrieNode[26];
}
```