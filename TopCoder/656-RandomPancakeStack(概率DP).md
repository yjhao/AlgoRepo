#656-RandomPancakeStack(概率DP)

##题意
给一个有n个整数的数组d。第一次随机选择一个数，以后每次随机选择一个没选过的数，如果这个数的下标大于前一个数，则不要这个数并停止选择。否则，继续选数，除非所有的数都选择就停止选择。问最后选出来的数的和的期望值。


##解题
首先想到可以用recursion， 不过为了加快速度， 在recursion之中可以使用Memorization。

在这里， 我们使用二维DP.

用dp[i][j]表示当前可以从i-1到0中选，共有j个数没被选的期望值。那么最后答案就是dp[n][n]。

转移方程式：
```
dp[i][j]+=(d[k]+dp[k][j-1])/j 。0<=k<i
```
##总结
对于求解期望类问题一般是用dp[i]表示从状态当前i走到结束状态的期望值，按照事件发生的先后顺序就行转移，转移一般是：
```
dp[i]+=(val[i]+dp[k])*p;  
(val[i]表示该事件的消费或价值，p表示发生当前事件的概率)
```

##代码
```
double dp[310][310];
class RandomPancakeStack
{
public:
    double expectedDeliciousness(vector <int> d)
    {
        int i,j,k;
        int n=d.size();
        memset(dp,0,sizeof(dp));
        for(i=1;i<=n;i++)
        {
            for(j=1;j<=n;j++)
            {
                for(k=0;k<i;k++)
                {
                    dp[i][j]+=(d[k]+dp[k][j-1])/j;
                }
            }
        }
        return dp[n][n];
    }


};
```