# 552-Student Attendance Record II （从i-1到i的状态变化，非常厉害的矩阵相乘）

## 题意
用 A, L , P 来组成一个长度为 n 的string, 要求是： 最多只能有一个A, 不能有连续的三个L。 问最后一共有多少种不同的组合方法 % 1000000007。

## 解题， 方法一
首先想到是DP， 我的想法是假设有dp[n]， 那么想办法得到dp[n+1]。 然后最后再插入至多一个 A 进去。

但是这个方法不是很好， 因为 从 n 到 n+1， 如果是加入的一个 L, 那么很容易， 但是如果加入的是 L, 那么就需要判断当前的状态，哪些是合法的， 哪些是不合法的。因为不能有连续的三个L。

#### 之前是 从当前推未来， 我们可以试试**反过来**考虑， 从未来推当前。

 为了 求 dp[n]， 我们可以考虑下，该怎么使用以前的信息得到。 一共有几个不同的情况需要考虑：

1. 以 P 为结尾的。那么就是dp[n-1]
2. 以 PL 为结尾的， 那么就是 dp[n-2]
3. 以 PLL 为结尾的， 那么就是 dp[n-3]

所以 dp[n] = dp[n-1] + dp[n-2] + dp[n-3]。 

然后再把最多的一个A 插进去。

```
public class Solution {
    int mod = 1000000007;
    public int checkRecord(int n) {
        long[] dp = new long[n+1];
        if (n==0)   return 0;
        if (n==1)   return 3;
        if (n==2)   return 8;
        
        dp[0] = 1L;
        dp[1] = 2L;
        dp[2] = 4L;
        
        for (int i=3; i<=n; i++){
            dp[i] = (dp[i-1] + dp[i-2] + dp[i-3])%mod;
        }
        
        long res =  dp[n] ;
        for (int i=0; i<=n-1; i++){
            res = (res + (dp[i] * dp[n-1-i]) % mod) % mod;
        }
        
        return (int)res;
    }
}
```

## 解题， 方法二
可以使用暴力DP的方法： 类似“背包”

在每一个idx， 需要对每一个 A L P 的情况进行判断。 在方法一种提到的，如果要加入 P， 情况很简； 但是如果要加入 A, 得从之前少一个A的结果推来； 如果要加入L, 也是相似的， 从之前的结果推来。

所以可以用一个三维DP背包来表示。  

d[n][i][j] 表示， 长度为n， 整个sequence最多有i个A, **结尾**最多有j个L的组合方式。

注意， 这里使用的是 “最多”， 所以数字大的，会包含掉数字小的情况。

初始情况， 长度为0的时候， 所有的值都为1。

需要注意的是， 在递推式中， 对于L有的地方都为hard code 2， 而对于A, 没有这样的全局hard code。因为 对于L的个数， 是Local的， 而对于A， 个数是对于整个sequence的。

需要注意的是， 虽然有取余， 但是在有乘法的时候， 还是得需要使用long 避免overflow。

```
public class Solution {
    int mod = 1000000007;
    public int checkRecord(int n) {
        int[][][] dp = new int[n+1][2][3];
        dp[0] = new int[][]{{1,1,1},{1,1,1}};
        
        for (int i=1; i<=n; i++){
            for (int j=0; j<2; j++){
                for (int k=0; k<3; k++){
                    int cur = dp[i-1][j][2];    // ...P
                    if (j>0)    cur = (cur + dp[i-1][j-1][2]) % mod;    // ...A
                    if (k>0)    cur = (cur + dp[i-1][j][k-1]) % mod;    // ...L
                    dp[i][j][k] = cur;
                }
            }
        }
        
        return dp[n][1][2];
    }
}
```

## 大BOSS, 从o(n) 到 o(logn) 的进化。
从方法二， 从i-1 到 i 的转化， 对于每一个 “i", 都为相同的关系， 所以可以得到这样的一个 矩阵：

> ```
> f[i][0][0]   | 0 0 1 0 0 0 |   f[i-1][0][0]
> f[i][0][1]   | 1 0 1 0 0 0 |   f[i-1][0][1]
> f[i][0][2] = | 0 1 1 0 0 0 | * f[i-1][0][2]
> f[i][1][0]   | 0 0 1 0 0 1 |   f[i-1][1][0]
> f[i][1][1]   | 0 0 1 1 0 1 |   f[i-1][1][1]
> f[i][1][2]   | 0 0 1 0 1 1 |   f[i-1][1][2]
> ```
> Let `A` be the matrix above, then `f[n][][] = A^n * f[0][][]`, where `f[0][][] = [1 1 1 1 1 1]`. The point of this approach is that we can compute `A^n` using [exponentiating by squaring](https://en.wikipedia.org/wiki/Exponentiation_by_squaring)。which will take O(6^3 * log n) = O(log n) time. Therefore, the runtime improves to O(log n), which suffices to handle the case for much larger n, 

最后的答案为 f[n][1][2]。 所以只需要计算 A^n,  然后根据矩阵的对应关系， 使用最后一行乘以 f[0][][] = 【1 1 1 1 1 1】

通过观察发现， A 的第三列都为 1， 跟 f[0][][]相同， 所以我们不需要 explicit 乘以 f[0][][]， 只需要在 f[n]的基础上， 多乘以一个 A， 然后结果就出现在 f[n+1][5][2]。

```
public class Solution {
    int mod = 1000000007;
    int M = 6;
    public int checkRecord(int n) {
        int[][] A = {
            {0, 0, 1, 0, 0, 0},
            {1, 0, 1, 0, 0, 0},
            {0, 1, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 1},
            {0, 0, 1, 1, 0, 1},
            {0, 0, 1, 0, 1, 1},
        };
        return pow(A, n+1)[5][2];
    }
    
    int[][] pow (int[][] A, int cnt){
        if (cnt==1) return A;
        int[][] res = new int[M][M];
        
        int[][] half = pow(A, cnt/2);
        
        res = multi(half, half);
        
        if (cnt%2==1){
            res = multi(res, A);
        }
        return res;
    }
    
    int[][] multi (int[][] mat1, int[][] mat2){
        int[][] res = new int[mat1.length][mat2[0].length];
        
        for (int i=0; i<mat1.length; i++){
            for (int j=0; j<mat1[0].length; j++){
                for (int k=0; k<mat2[0].length; k++){
                    res[i][k] = (int)(res[i][k] + (long)mat1[i][j] * mat2[j][k] % mod) % mod;
                }
            }
        }
        
        return res;
    }
}
```

还有一个更快的方法求 pow，使用iterative的方法。虽然 big o 是一样的。

这个方法的巧妙之处在于， 将一个整数设想成了二进制的数， 101101101。

A 的 平方， 4次方， 8次方， 16次方一直在计算 【A = multi(A, A)】， 当遇见 “1“的时候， （cnt%2==1）， 将之前的结果和现在的累加的结果”相乘“， 比比如说 10100， 也就是将以前的结果100 和现在的 10000 相组合在一起。

```
int[][] pow (int[][] A, int cnt){
	int[][] res = new int[M][M];
    for (int i=0; i<M; i++) res[i][i] = 1;
        
    while (cnt>0){
        if (cnt%2==1)   res = multi(res, A);
        A = multi(A, A);
        cnt /=2;
    }
    return res;
}
```





 