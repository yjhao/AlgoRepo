#269-Alien Dictionary(Topological Sort)

##题意
给一些单词， 按照他们的顺序， 列出字典序。

##解题， 和细节
典型的拓扑排序。

但是与 course number 的区别主要是：

1. 不是a-z的所有字母都在这个字典中， 所以要遍历所有的单词， 确定哪些字母存在， 用boolean array 来存储。
2. 如果一个字母的 outDegree = 0， 还有可能是 它根本就不存在于这个字典中， 所以一定要确定他是在这个字典中的， 用第一步建立的 Boolean array。

##代码
```
public class Solution {
    public String alienOrder(String[] words) {
        int[] outDegree = new int[26];
        boolean[] has   = new boolean[26];
        ArrayList<ArrayList<Character>> graph = new ArrayList<ArrayList<Character>>();
        for (int i=0; i<26; i++){
            ArrayList<Character> list = new ArrayList<Character>();
            graph.add(list);
        }
        
        for(String s: words){
            for(char c: s.toCharArray()){
                has[c-'a'] = true;
            }
        }
        
        if (words==null || words.length==0) return "";
        for (int i=1; i<words.length; i++){
            String s1 = words[i-1];
            String s2 = words[i];
            int i1 = 0;
            int i2 = 0;
            while (i1<s1.length() && i2<s2.length()){
                if (s1.charAt(i1)==s2.charAt(i2)){
                    i1++; 
                    i2++;
                    continue;
                }   
                outDegree[s1.charAt(i1)-'a']++;
                graph.get(s2.charAt(i2)-'a').add(s1.charAt(i1));
                
                break;
            }
        }
        
        LinkedList<Character> queue = new LinkedList<Character>();
        for (int i=0; i<26; i++){
            if (outDegree[i]==0 && has[i]){
                queue.offer((char)('a'+i));
            }
        }
        StringBuilder sb = new StringBuilder();
        
        while (!queue.isEmpty()){
            char cur = queue.poll();
            sb.append(cur);
            for (char c : graph.get(cur-'a')){
                outDegree[c-'a']--;
                if (outDegree[c-'a']==0)
                    queue.offer(c);
            }
        }
        
        for (int i=0; i<26; i++){
            if (outDegree[i]!=0)
                return "";
        }
        
        return sb.reverse().toString();
    }
}
```