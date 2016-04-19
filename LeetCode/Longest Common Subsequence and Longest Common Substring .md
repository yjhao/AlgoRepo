#Longest Common Subsequence and Longest Common Substring

##题意
给出两个字符串，找到最长公共子序列(LCS)，返回LCS的长度。

给出两个字符串，找到最长公共子串，并返回其长度。

## 解题
两个问题很相似， 唯一的区别是LCS不一定是连续的， 而substring必须是连续的

用 DP[i][j] 来表示 结尾 在第一个字符串的i处，在第二个字符串的j处， 此时的最长LCS和最长子串。

递推方程很相似， 唯一的区别来自于 LCS 不一定是连续的， 而substring必须是连续的。

所以如果 a[i] ！= b[j]

那么对于Substring来说， 此时为0， 因为显然不是相同的子串

```
dp[i][j]=0
```

但是对于LCS来说， 即使 a[i] not = b[j]， 也不能磨灭之前的最长LCS结果， 所以 

```
dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1])
```

如果 a[i] == b[j]

对于两个问题， 递推式都为

```
dp[i][j] = dp[i-1][j-1]+1
```

##LCS 代码
```
public int longestCommonSubsequence(String A, String B) {
        // write your code here
        if (A==null || B==null || A.length()==0 || B.length()==0) return 0;
        int[][] dp = new int[A.length()][B.length()];
        if (A.charAt(0)==B.charAt(0)) dp[0][0] = 1;
        
        for (int i=1; i<B.length(); i++){
            if(B.charAt(i) == A.charAt(0)){
                dp[0][i] = 1;
            }else{
                dp[0][i] = dp[0][i-1];
            }
        }
        
        for (int i=1; i<A.length(); i++){
            if (A.charAt(i) == B.charAt(0)){
                dp[i][0] = 1;
            }else{
                dp[i][0] = dp[i-1][0];
            }
        }
        
        for (int i=1; i<A.length(); i++){
            for (int j=1; j<B.length(); j++){
                if (A.charAt(i)!=B.charAt(j)){
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
                }else{
                    dp[i][j] = dp[i-1][j-1]+1;
                }
            }
        }
        
        return dp[A.length()-1][B.length()-1];
    }
```

## 最长子串 subsequence
```
public int longestCommonSubstring(String A, String B) {
        // write your code here
        if (A==null || B==null || A.length()==0 || B.length()==0)   return 0;
        
        int[][] dp = new int[B.length()][A.length()];
        int max = 0;
        
        for (int i=0; i<B.length(); i++){
            if (B.charAt(i) == A.charAt(0)){
                dp[i][0] = 1;
                max = 1;
            }
        }
        
        for (int i=0; i<A.length(); i++){
            if (A.charAt(i) == B.charAt(0)){
                dp[0][i] = 1;
                max = 1;
            }
        }
        
        for (int i=1; i<B.length(); i++){
            for (int j=1; j<A.length(); j++){
                if (B.charAt(i) == A.charAt(j)){
                    dp[i][j] = dp[i-1][j-1] +1;
                    max = Math.max(max, dp[i][j]);
                }
            }
        }
        
        return max;
    }
```


