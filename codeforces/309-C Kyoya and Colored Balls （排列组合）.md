#309-C Kyoya and Colored Balls （排列组合）

##题意
有一堆球， 一个一个取出直到不剩， 要求为序号更大的球要比序号小一点的球最后取完。 求一共有多少种不同的取球方法。

##解题
如果只有一个序号的球， 取球方法只有一种

如果有两个序号的球， 那么序号为2的球要比序号为1的球更晚取出， 那么最后一个取出的球肯定是2号球。 而之前的球， 可以任意顺序取出， 那么排列组合为 f_12 =（n1+n2-1, n2-1), n1+n2-1个球中取n2-1个。

如果现在有三号球了， 假设之前已经满足了取出1和2球的正确顺序， 那么最后一个取的球为3球， 然后要把n3-1个3号球插入到之前的合法序列中， 那么排序 f_123 = f_12 * (n1+n2+n3-1, n3-1)

所以递推式就很明显了。

## 二项式公式
要得到（n,m） 的值， 可以用DP....

DP[n][m]  :  从n球中取出m球的方法。

```
dp[n][m] = dp[n-1][m] + dp[n-1][m-1]
```

要注意边界条件

```
dp[1][1] = 1;   dp[1][0] = 1;     dp[0][0] = 1; 
dp[i][i] = 1;  
```

##代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        int[][] bi = binomialCoe(1000);

        int[] c = new int[n];
        for (int i=0; i<n; i++)
            c[i] = in.nextInt();

        int sumBall = 0;
        long ans = 1;

        for (int i=0; i<n; i++){
            sumBall += c[i];
            ans *= bi[sumBall-1][c[i]-1];
            ans %= MOD;
        }

        out.print(ans);


    }

    public int[][] binomialCoe(int n){
        int[][] bi = new int[n+1][n+1];   // n chooses i
        bi[0][0] = 1;
        bi[1][0] = 1;
        bi[1][1] = 1;

        for (int i=2; i<n+1; i++){
            bi[i][0] = 1;
            bi[i][i] = 1;
            for (int j= 1; j<i; j++){
                bi[i][j] = (bi[i-1][j-1] + bi[i-1][j]) % MOD;
            }
        }
        return bi;
    }
```   