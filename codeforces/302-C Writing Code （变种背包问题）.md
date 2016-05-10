#302-C Writing Code （变种背包问题）
##题意
n个程序员写m行代码，给出每个程序员每行会产生的代码，问BUG不超过b的程序员分配方案有多少种。

##解题
眨眼一看 不是很容易明白

但其实这是**背包问题**： 背包一共放入 m 个物品， 但是总的物品数量是 n， 每个物品的权重是“每个程序员的每行bug”， 要解答的是， 放满这么多位置， 总的权重不超过b的方法一共有多少种方法。

使用 dp[i][j] 来记录： 填满 i 个空位， 总的权重为 j 的方法一共有多少种。

递推式为： dp[i][j] += dp[i-1][j-a[k]]

这是在第 i 行使用第 k 个物品的情况。 

还有另外一个情况，那就是在这个时候 不使用 第 k 个物品， 那么此时的累加值为0。 因为既然不使用， 那么在第 i 行， 总的权重为 j 的情况不会发生任何变化。

需要注意的是， 问的是“有多少种方法”， 所以 dp[0][0] = 1， 如果是求的一个值， 那么 dp[0][0] = 0;

##代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        int b = in.nextInt();
        int mod = in.nextInt();
        int[] a = new int[n];
        for (int i=0; i<n; i++){
            a[i] = in.nextInt();
        }

        int[][] dp = new int[m+1][b+1];
        dp[0][0] = 1;

        for (int i=0; i<n; i++){
            for (int j=1; j<=m; j++){
                for (int k = a[i]; k<=b; k++){
                    dp[j][k] += dp[j-1][k-a[i]];
                    dp[j][k] %= mod;
                }
            }
        }

        int res = 0;
        for (int i=0; i<=b; i++){
            res = (res + dp[m][i])%mod;
        }

        out.print(res);
    }
```

