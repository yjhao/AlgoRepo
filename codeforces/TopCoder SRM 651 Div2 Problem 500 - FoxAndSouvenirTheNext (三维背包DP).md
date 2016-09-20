#TopCoder SRM 651 Div2 Problem 500 - FoxAndSouvenirTheNext (三维背包DP)

##题意
有一个 长度为 N 的数组， 问能不能选出n/2个数，使得她们的和为sum/2。

##解题
使用DP。

因为我们不仅要满足能找出 这样的 subset， 使其的总和为sum/2， 还得满足这样的 subset 中的元素个数为 n/2。所以我们使用 三维 DP

dp[i][j][k]， 表示 在前 K 个元素中， 能够找到 j 个元素， 他们的和为 i，

对于 dp[i][j][k]， 首先  dp[i][j][k] 肯定等于  dp[i][j][k-1]， 因为如果满足了 k 中选 j， 一定满足 k+1 中选 j。

其次， 如果要把当前的 第  j 个元素算入 subset 中， 那么 

```
if (dp[i-val[k][j-1][k-1]) dp[i][j][k] = true;
```
前k-1个中选了 j-1 个能够凑出 i-val[k]， 那么加上当前的这一个之后， 一定能够在前k中选择出 j 个凑出 i。

##代码
```
public String ableToSplit(int[] value)
	{
		int sum = 0; for (int val : value)	sum+=val; if (sum%2==1 || value.length%2==1)	return "Impossible";
		boolean[][][] dp = new boolean[sum/2+1][value.length/2+1][value.length+1];
		dp[0][0][0] = true;
		for (int i = 0; i<value.length+1; i++)	dp[0][0][i] = true;
		
		for (int i=1; i<=sum/2; i++){
			for (int j=1; j<=value.length/2; j++){
				for (int k=1; k<=value.length; k++){
					if (i-value[k-1] <0)	continue;	
					dp[i][j][k] = dp[i][j][k-1];
					if (dp[i-value[k-1]][j-1][k-1])	dp[i][j][k] = true;
				}
			}
		}
		
		if (dp[sum/2][value.length/2][value.length])	return "Possible";
		return "Impossible";			
	}
```