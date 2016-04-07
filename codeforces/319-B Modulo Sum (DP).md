#319-B Modulo Sum (DP)
##题意
给出一个数列， 长度为n，和一个数m，问能不能从这个数列中选出若干个数，使得这些数的和可以整除m

##解题
01背包问题

对于一个数， 要么取， 要么不取。 dp[i][j]表示选择到第i个数，和模m==j的情况有没有(有1，没有0)

##特例
如果数列长度n>=m了， 那么一定是成立的。 根据抽屉原理， 或者pigeonhole principle。

原因：
计算各个前缀和除以m的余数， 因为没有整除的， 而长度大于m， 所以必然有两个的模是相同的。 R_l = R_r。 那么在r和l之间的数， 他们的和一定能够被m整除。

##代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        int[] arr = new int[n];
        for (int i=0; i<n; i++){
            arr[i] = in.nextInt();
        }

        if (n>=m){
            out.print("YES");
            return;
        }


        boolean dp[][] = new boolean[n + 1][m];
        for (int i = 0; i < n; i++) {
            int val = arr[i];
            dp[i + 1][val % m] = true;
            for (int j = 0; j < m; j++) {
                if (dp[i][j]) {
                    dp[i + 1][(j + val) % m] = true;
                    dp[i + 1][j] = true;
                }
            }

            if (dp[i+1][0]==true){
                out.print("YES");
                return;
            }
        }

        out.print("NO");

    }
```

