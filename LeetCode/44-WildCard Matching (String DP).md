# 44-WildCard Matching (String DP)
## 题意
本题很像 正则匹配。 

'?' Matches any single character.

'*' Matches any sequence of characters (including the empty sequence).

唯一的区别就是 * 不需要看s和j前面的字符是什么

## 解题
采用DP。 isMatch(String s, String p)

相似于 正则匹配， 需要对边界条件 DP[0][i] 进行判断。

判断完之后， 

如果 p[j] ！= '*'， 只需要比较当前字符， 由dp[i-1][j-1]递进到 dp[i][j]

如果 p[j] == ‘*’ 。 与三个情况

1. * 一个都不匹配， 那么只需要看 dp[i][j-1]。
2. * 匹配一个s中的字符， 那么需要看 dp[i-1][j-1]。
3. * 匹配多个s中的字符， 那么需要看 s[i] 前面的情况。 因为 这是wildcard， 所以可以是任意的 序列， 所以只要前面的情况是true， 那么现在也会是true。 所以只需要看 dp[i-1][j]。

综上所述。dp[i][j] = dp[i-1][j-1] || dp[i-1][j] || dp[i][j-1];

## 代码
```
public class Solution {
    public boolean isMatch(String s, String p) {
        if (s == null || p == null)
            return true;

        int row = s.length();
        int col = p.length();

        boolean[][] match = new boolean[row+1][col+1];


        match[0][0] = true;

        for (int i = 1; i <= col && match[0][i-1] && p.charAt(i-1) == '*'; i++){
            match[0][i] = true;
        }

        for (int i = 1; i <= row; i++){
            for (int j = 1; j <= col; j++){
                if (p.charAt(j-1) == '*'){
                    match[i][j] = match[i-1][j-1] || match[i-1][j] || match[i][j-1];
                }
                else if (p.charAt(j-1) == '?'){
                    match[i][j] = match[i-1][j-1];
                }
                else{
                    match[i][j] = match[i-1][j-1] && s.charAt(i-1) == p.charAt(j-1);
                }
            }
        }    

        return match[row][col];
    }
}
```


