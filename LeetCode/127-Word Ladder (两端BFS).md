# 127-Word Ladder (两端BFS)
## 题意
从一个单词， 变换到另外一个单词， 路劲都必须在一个Dict里面， 求最短距离

## 解题
最短距离， 肯定用BFS

传统的BFS, 从起点开始搜索， 一直到碰到终点为止

在这里， 我们从 起点 和 终点 依次 往对方搜索， 虽然看上去复杂度是一样的， 不过因为BFS是无方向性的，往各个方向扩展， 所以从两边同时搜索， 速度要快很多。

数学的角度来看， 这个方法更快的原理在于 b^(d/2) + b^(d/2) < b^d。 b is branching factor, and d is the depth。

用Set来表示“每一层” 的单词， 在While的开始， 比较一下两个Set的大小， 搜索总是从小的那一个开始。 这样就能保证 搜索是 一次交替进行的。

## 代码
```
public class Solution {    public int ladderLength(String beginWord, String endWord, Set<String> wordDict) {        Set<String> beginSet = new HashSet<String>(), endSet = new HashSet<String>();        int len = 1;        int strLen = beginWord.length();        HashSet<String> visited = new HashSet<String>();        beginSet.add(beginWord);        endSet.add(endWord);        while (!beginSet.isEmpty() && !endSet.isEmpty()) {            if (beginSet.size() > endSet.size()) {                Set<String> set = beginSet;                beginSet = endSet;                endSet = set;            }            Set<String> temp = new HashSet<String>();            for (String word : beginSet) {                char[] chs = word.toCharArray();                for (int i = 0; i < chs.length; i++) {                    for (char c = 'a'; c <= 'z'; c++) {                        char old = chs[i];                        chs[i] = c;                        String target = new String(chs);                        if (endSet.contains(target)) {                            return len + 1;                        }                        if (!visited.contains(target) && wordDict.contains(target)) {                            temp.add(target);                            visited.add(target);                        }                        chs[i] = old;                    }                }            }            beginSet = temp;            len++;        }        return 0;    }}
```

