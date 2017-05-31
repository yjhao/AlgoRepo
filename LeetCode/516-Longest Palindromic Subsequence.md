# 516-Longest Palindromic Subsequence

## 题意
给一个String， 找到其中最长的 回文 subsequence，不一定是相连的。

## 解题
用dp 来解决。

dp[i][j] --》 从i到j，这之中的最长回文 subsequence。

归纳法， 从长度短的开始， 长度较长的 substring， 是建立在较短的基础上的。

如果 s(i) == s(j), 那么 dp[i][j] = dp[i+1][j-1]+2;

如果 s(i) != s(j) 呢？ 

**因为是subsequence, 所以不一定是相连接的字符串** 

所以 

```
dp[i][j] =  max (dp[i+1][j], dp[i][j-1]);
```
长度较长的subsequence， 一定包含了较短的 subsequence的最长回文。

## 代码
```
public class Solution {
    public int longestPalindromeSubseq(String s) {
        if (s.length()<=1)  return s.length();
        int[][] dp = new int[s.length()][s.length()];
        for (int i=0; i<s.length(); i++)    dp[i][i] = 1;
        
        for (int len = 2; len<=s.length(); len++){
            for (int i=0; i<=s.length()-len; i++){
                int j = i+len-1;
                char c1 = s.charAt(i);
                char c2 = s.charAt(j);
                if (c1==c2){
                    dp[i][j] = dp[i+1][j-1] + 2;
                }else{
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
                }
            }
        }
        
        return dp[0][s.length()-1];
    }
}
```