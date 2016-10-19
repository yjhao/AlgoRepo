# 369-C Coloring Trees

## 题意
公园有n棵树,有些已经被涂上颜色,有些还未上色

现在有m种不同颜色的颜料,给还未上色的树涂上颜色

已知对于第i棵树,若用颜料j来上色,需要的使用量为P(i,j)升

问美丽度恰好为k时,最少需要多少升颜料

美丽度的定义为连续颜色段的段数

如2, 1, 1, 1, 3, 2, 2, 3, 1, 3,它的美丽度为7,因为它有7个连续颜色段,分别为{2},{1,1,1},{3},{2,2},{3},{1},{3}

##解题
显然,这是一道DP题。

令dp[i][j][l]表示第i颗树涂颜色l,美丽度为j时前i棵树共用颜料的升数

对于第i棵树， 如果不需要上色， 那么如果前面一个树的颜色一样， 那么当前的美丽度的花费就是一样的：
    dp[i][j][l] = dp[i-1][j][l]
    
如果颜色与之前的不一样， 那么如果当前的美丽度为L的话， 之前的美丽度一定为L-1。 在之前一颗树的所有美丽度为 L-1， 而且颜色与当前树的颜色不一样的配置中， 寻找最便宜的一个， 即为当前的最便宜的价格。

```
if(l!=c[i])  
	dp[i][j][c[i]]=min(dp[i][j][c[i]],dp[i-1][j-1][l]);
```

如果对于第i棵树， 需要上色话， 有 K 种不同的染色方法。 如果不提升美丽度， 那么就需要之前一颗树的颜色与当前一样， 如果需要提升美丽度的话， 在所有美丽度比当前小一， 而且颜色与当前不一样的设置中， 寻找最便宜的一个。所以一共需要 k * k 个循环。

## 边界条件
三维dp, 边界条件是很重要的， 在这里， 如果第一课数已经染色了， 那么dp[1][1][c[1]]=0， 否则 
```
for(i=1;i<=m;i++)  
            dp[1][1][i]=p[1][i]; 
```

## 代码
```
using namespace std;  
const int N = 105;  
const int M = 100005;  
const __int64 inf = 1e18;  
const int mod = 1000000007;  
__int64 dp[N][N][N];  
int c[N],p[N][N];  
int main()  
{  
    int n,m,k,i,j,l,q;  
    __int64 Min=inf;  
    scanf("%d%d%d",&n,&m,&k);  
    for(i=1;i<=n;i++)  
        scanf("%d",&c[i]);  
    for(i=1;i<=n;i++)  
        for(j=1;j<=m;j++)  
            scanf("%d",&p[i][j]);  
    for(i=0;i<=n;i++)  
        for(j=0;j<=k;j++)  
            for(l=0;l<=m;l++)  
                dp[i][j][l]=inf;  
    if(c[1])  
        dp[1][1][c[1]]=0;  
    else  
        for(i=1;i<=m;i++)  
            dp[1][1][i]=p[1][i];  
    for(i=2;i<=n;i++)  
        for(j=1;j<=k;j++)  
            if(c[i])  
            {  
                dp[i][j][c[i]]=min(dp[i][j][c[i]],dp[i-1][j][c[i]]);  
                for(l=1;l<=m;l++)  
                    if(l!=c[i])  
                        dp[i][j][c[i]]=min(dp[i][j][c[i]],dp[i-1][j-1][l]);  
            }  
            else  
            {  
                for(l=1;l<=m;l++)  
                {  
                    dp[i][j][l]=min(dp[i][j][l],dp[i-1][j][l]+p[i][l]);  
                    for(q=1;q<=m;q++)  
                        if(q!=l)  
                            dp[i][j][l]=min(dp[i][j][l],dp[i-1][j-1][q]+p[i][l]);  
                }  
            }  
    for(i=1;i<=m;i++)  
        Min=min(Min,dp[n][k][i]);  
    if(Min!=inf)  
        printf("%I64d\n",Min);  
    else  
        puts("-1");  
    return 0;  
}  
```

