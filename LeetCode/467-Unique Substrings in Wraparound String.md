# 467-Unique Substrings in Wraparound String
## 题意
给一个String， 看在这个String里面， 有多少个 unique substring， 符合 abcdefg...zabc这样的模式。

## 解题， 方法一， O(n2)
比如说 abcdebem， 我们对每一个字母作为起点开始visit后面的字母， 看符不符合模式， 如果不符合， 就终止， 并从下一个字母再次开始这样的往后的遍历。

这个方法是brutal force， 但是问题的关键是 需要求unique的。 我们可以记录下来每一个substring， 这样的话， 需要很多的memory。

因为substring的规律我们知道， 比如以a开始， 长度为5的， 必定是 abcde。 所以我们可以记录下来， 以某个字母开头， 最长的符合模式的substring有多长， 比如说 abcde, 以a开头的就有5个 unique substring。 比5短的， 以a开头的substring， 一定被 abced 包含在内。

求最后的答案的时候， 对于每一个不同的字母作为开头的情况， 做一个加法， 就可以得到答案。

这个算法的复杂度为 O(n2)。

## 解题， 方法二， O(n)
如果往前看， 然后记录下每一个Substring的起点。 当一次遍历终止的时候 （当一个连续的字符串结束之后）， 我们得转头过来， 找到下一个起点， 继续下一次的遍历。所以我们就没有利用好之前的信息， 重复遍历。

更好的方法是， 我们记录下来， **以某一个字母为终点**的字符串， 这样的话， 我们可以一边遍历， 一边更新 当前终点和字符串长度。 当一个连续的字符串结束之后， 我们不需要回头重来， 而是更新一下当前字符串长度， 并继续往下遍历。

## 代码
```
public class Solution {
    public int findSubstringInWraproundString(String p) {
        if (p.length()==0)  return 0;
        int res = 0;
        int[] cnt = new int[26];
        
        int idx = 1;
        int len = 1;
        cnt[p.charAt(0)-'a'] = 1;
        
        while (idx<p.length()){
            char cur = p.charAt(idx);
            if (cur-p.charAt(idx-1)==1 || p.charAt(idx-1)=='z' && cur == 'a'){
                len++;
            }else{
                len = 1;
            }
            cnt[cur-'a'] = Math.max(cnt[cur-'a'], len);
            idx++;
        }
        
        for (int i=0; i<26; i++)    res += cnt[i];
        return res;
    }
}
```


