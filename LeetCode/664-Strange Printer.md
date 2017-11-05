# 664-Strange Printer

## 题意
给一个字符串， 现在可以执行写操作， 但是限制条件是，这个写操作规定一个起点和终点， 在这之间的所有字符都变为同一个字符。 问要实现这个字符串， 最少的操作次数是多少。

## 解题
显然， 这是一个dp题。

dp[i][j] 表示从i到j， 最少的操作次数。

dp[i][i] = 1

最主要是， 如何构造dp[i][j]的转移方程。 自然的想法是 dp[i][j] = dp[i][k] + dp[k][j]， 如果 k 处的字符等于 i 出的字符， 那么情况就会不一样了， 我们可以先打印从 i+1 到 k-1处的字符， 然后把 i 处的字符 “连接” 在 k处的字符上， 打印k的字符的时候， 顺带也把 i 的字符也一起打印了， 这样的话， 转移方程就变为：dp[i][j] = Math.min(dp[i][j], dp[i+1][k-1] + dp[k][j]) if char(i) == char(k)。

需要注意的是， 初始条件的 dp[i][j] 不能使用 j-1+1， 而应该使用之前的信息来作为表达： dp[i][i] + d[i+1][j]。 

这样有两个作用：

1. 因为k 是从 i+1 一直遍历到 j， 所以我们应该在初始条件下， 使用k=i的结果, 把之前的结果传递到现在的情况中来。
2. 因为后面的math.min仅仅是在 char(k) = char(i)的时候使用，所以如果在初始化的时候没有使用之前的dp结果的话， 有可能没有找到这样的k，从而更新不了dp[i][j], 使得dp[i][j]=len是错误的。 比如说 abb， 如果初始化为3的话， 遍历k的时候， bb 都不等于a， 所以math.min就不可能被触发。 而我们使用 1+ dp[i+1][j]的时候， 就潜在的处理到了这个情况： 后面和前面分别是两个整体。


## 代码
```
class Solution {
    public int strangePrinter(String s) {
        if (s.length()<=1)  return s.length();
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i= 0; i<n; i++)    dp[i][i] = 1;
        
        for (int len = 2; len<=n; len++){
            for (int left = 0; left+len<=n; left++){
                int right = left+len-1;
                dp[left][right] = 1 + dp[left+1][right];
                for (int k=left; k<=right; k++){
                    if (s.charAt(k) == s.charAt(left) && k!=left){
                        dp[left][right] = Math.min(dp[left][right], dp[left+1][k-1] + dp[k][right]);
                    }
                }
            }
        }
        
        return dp[0][n-1];
    }
}
``` 