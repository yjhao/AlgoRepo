# 10-Regular Expression Matching (DP / 暴力）

## 题意
正则匹配。 . 可以匹配任意的字符， * 匹配0个或多个前一个字符。

## 解题, 暴力解法
首先我们看看 brutal force 的解题方法。假设 输入的 字符 是 s 和 p， s 是需要匹配的字符串。

如果在p的 j 位置， 遇到一个正常的字符， 而 J+1 不是 “*”， 那么我们只需要 看 p 和 s 的当前位置是否一样 （或者 P[j] 是否为 .）， 如果不一样的话， 返回false。

如果 j+1 的位置出现的是 *。因为 * 匹配0个或多个前一个字符， 所以有几个情况需要考虑。

如果是 匹配 多个字符的情况， 如果 s[i] = p[j]， 那么因为 p[j+1] = *， 所以我们还应该看 s[i] 后面的字符是否和 p[j] 相同， 满足 ”匹配多个字符的情况"。 所以我们在满足 s[i]=p[j]的情况下，递进 i。

如果是 匹配 0个字符， 那么就应该直接跳过 j+1 和 j （*和当前字符）， 无论 p[j]是否等于 s[i]， 直接跳到 p[j+2] 进行判断。

综上所述， 最后的代码为， 注意 i 和 j 的范围。

终止条件为 i j 都为 len。 

```
public boolean isMatch(String s, String p) {
        return helper(s,p,0,0);
    }
    
private boolean helper(String s, String p, int i, int j){
       if (j==p.length())
           return i==s.length();
        
        
       if (j==p.length()-1 || p.charAt(j+1)!='*')
       {
           if (i>=s.length() || p.charAt(j) != s.charAt(i) && p.charAt(j)!='.')
           {
               return false;
           }
           else
           {
               return helper(s,p,i+1, j+1)             
           }
       }
        
       //p.charAt(j+1)=='*'
       while(i<s.length() && (p.charAt(j)=='.' || s.charAt(i)==p.charAt(j)))  
       {  
           if(helper(s,p,i,j+2))    // '*' means zero element.  
               return true;  
           i++;                     // test i,i+1,i+2,i+3 with j
       }
        
       return helper(s,p,i,j+2);    // 
   }
```

## 解题 DP
我们用 dp[i][j] 来 代表 s 和 p 的前 I 个 和 前 j 个字符是否Match。

相似的， 分几种情况。

1. 如果 p.charAt(j) 不为 *， 那么直接比较 p.charAt(j)?=s.charAt(i) || p.charAt(j)?=. 。 还需要 dp[i][j] 为真。dp[i+1][j+1] 才可以为真
2. 如果 p.charAt(j) == *
		
	a. 匹配 0 个字符 ： 检验 dp[i][j-1]是否为真
	
	b. 匹配 1 个或多个字符 ： 首先需要满足的是 s.charAt(i)== p.charAt(j-1) 或者 p.charAt(j-1) == '.'。 其次， 因为是 一个 或者 多个字符， 所以我们得确保， 在 s 中， i 之前的字符是满足匹配的， 不然就不存在“一个或者多个了”。 所以dp[i-1][j] 必须为真。
	
	以上两个情况， 只要有一个情况为真， dp[i+1][j+1] 都会为真。
	
## 注意的细节

DP的边界条件需要初始化。 DP[0][j] 需要一个一个进行判断。 所以traverse String 的时候， 不能用 string index， 得用 dp 的 index。 这样的话， 就可以保证 DP[0][j] 会得到判断

比如说 ： a, a* 就为真，dp[1][2] = true, from  dp[0][2] = true && s.charAt(0) = j.charAt(0)。 （匹配一个或多个字符）。

如果使用 dp[i+1][j+1] 进行更新的话， 永远不会 得到 dp[0][2] = true；

## 代码
```
public class Solution {
    public boolean isMatch(String s, String p) {
        int sL=s.length(), pL=p.length();

    boolean[][] dp = new boolean[sL+1][pL+1];
    dp[0][0] = true; // If s and p are "", isMathch() returns true;

    for(int i=0; i<=sL; i++) {

        // j starts from 1, since dp[i][0] is false when i!=0;
        for(int j=1; j<=pL; j++) {
            char c = p.charAt(j-1);

            if(c != '*') {
                // The last character of s and p should match;
                // And, dp[i-1][j-1] is true;
                dp[i][j] = i>0 && dp[i-1][j-1] && (c=='.' || c==s.charAt(i-1));
            }
            else {
                // Two situations:
                // (1) dp[i][j-2] is true, and there is 0 preceding element of '*';
                // (2) The last character of s should match the preceding element of '*';
                //     And, dp[i-1][j] should be true;
                dp[i][j] = (j>1 && dp[i][j-2]) ||
                           (i>0 && dp[i-1][j] && (p.charAt(j-2)=='.' || p.charAt(j-2)==s.charAt(i-1)));
            }
        }
    }

    return dp[sL][pL];
    }
}
``` 


