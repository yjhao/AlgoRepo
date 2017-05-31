# 279- Perfect Squares

## 题意
Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.

For example, given n = 12, return 3 because 12 = 4 + 4 + 4; given n = 13, return 2 because 13 = 4 + 9.

## 解题
方法一：

在 i 的基础上， 每次加上一个**相同**的 完全平方数。并递增i

```
for i = 1:n
	for j = 1:k
		dp[i+ time * j * j] = min (dp[i+time * j *j], dp[i+ (time-1) * j * j]+1)
		time++;
```

方法是正确的， 不过速度太慢， 要超时。 因为每次都新计算出一个值， 然后在这个值上加一。 而且还要判断 i+ time * j * j 是否已经超越界限。



方法二：
在方法一上稍微变化一下， 

在 i 的基础上， 每次加上一个递增的完全平方数。

```
for (int i=0; i<n; i++){
            for (int j=1; i+j*j<=n; j++){
                count[i+j*j] = Math.min(count[i+j*j], count[i]+1);
            }
        }
```
每次都在 i 的基础上 加一。

## 代码
```
public class Solution {
    public int numSquares(int n) {
        int[] count = new int[n+1];
        for (int i=0; i<=n; i++){
            count[i] = Integer.MAX_VALUE;
        }
        count[0] = 0;
        
        /* too slow
        for (int i=0; i<n; i++){
            for (int j=1; j<=n/2+1; j++){
                int incre = j*j;
                int old = i;
                while (old+incre<=n){
                    count[old+ incre] = Math.min( count[old+incre], count[old]+1 );
                    old = old+incre;
                }
            }
        }
        */
        
        for (int i=0; i<n; i++){
            for (int j=1; i+j*j<=n; j++){
                count[i+j*j] = Math.min(count[i+j*j], count[i]+1);
            }
        }
        
        return count[n];
    }
}
```

