#312-C Amr and Chemistry (位操作）
## 题意
给你若干个数。
对每个数都可以进行除或者乘的操作。  3/2=1
问 最少 多少步操作，可以让所有数字都相等。

## 解法
暴力穷尽 位操作

不过细节在于

如果一个数字为偶数， 左移再又移之后为自己， 但是如果这个数字为奇数， 那么左移再右移之后， 结果是**不一样的**。

所以抓住这个特点， 如果数字为偶数， 那么只做左移 （第一个初始数字例外）； 如果数字为奇数， 不仅做左移， 还要做右移。

## 代码
```
int ci[101000];
int buhe[101000];
int main()
{
    int n;
    int lim=100000;
    while(scanf("%d",&n)!=EOF)
    {
        memset(ci,0,sizeof ci);
        memset(buhe,0,sizeof buhe);
        for(int i=0;i<n;i++)
        {
            int num;
            scanf("%d",&num);
            int flag=1;
            int bu=0;
            while(num)
            {
                if(flag)
                {
                    int tt=bu+1;
                    int num2=num*2;;
                    while(num2<=lim)
                    {
                        ci[num2]++;
                        buhe[num2]+=tt;
 
                        num2*=2;
                        tt++;
                    }
                    flag=0;
                }
                ci[num]++;
                buhe[num]+=bu;
                bu++;
                if(num%2==0)
                    num/=2;
                else
                {
                    flag=1;
                    num/=2;
                }
            }
        }
        int minn=999999999;
        for(int i=1;i<=lim;i++)
        {
            if(ci[i]==n)
                minn=min(minn,buhe[i]);
        }
        printf("%d\n",minn);
    }
    return 0;
}
```