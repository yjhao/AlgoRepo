# 583-Delete Operation for Two Strings (LCS)

## 题意
给两个String， 问经过多少次 删除 操作后， 两个String剩下的内容会变为相同的。

## 解题
这个题目换种说法， 就是求 longest common subsequence。 

跟 edie distance 很类似。

稍加试验， 就知道 greedy的方法肯定行不通， 所以要么使用 recursion， 要么使用 dp。 状态参数就是两个String的指针当前分别位置 i 和 j。

如果使用Recursion的话， 得需要使用 memorization， 因为有很多相同的subproblems。

这里给出 dp 的答案。dp[i][j]表示， 在 i j处， 最小的操作数， 使得到i j处， 获得相同的substring。

在 i j 处, 如果字符相同， 则 
```
dp[i][j] = dp[i-1][j-1]
```
如果不相同， 则删掉两个中的一个。 ```dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + 1```

## 代码
```
public class Solution {
    public int minDistance(String word1, String word2) {
        int min = word1.length() + word2.length();
        
        int[][] dp = new int[word1.length()+1][word2.length()+1];
        
        for (int i=0; i<=word1.length(); i++)  dp[i][0] = i;
        for (int i=0; i<=word2.length(); i++)  dp[0][i] = i;
        
        for (int i=1; i<=word1.length(); i++){
            for (int j=1; j<=word2.length(); j++){
                if (word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1])+1;
                }
            }
        }
        
        return dp[word1.length()][word2.length()];
    }
}
```