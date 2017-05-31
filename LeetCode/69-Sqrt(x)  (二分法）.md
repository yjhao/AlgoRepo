# 69-Sqrt(x)  (二分法）

# 题意
求平方数

# 解题
用二分法

这个题相比其他求range的二分法， 有一个特征， 如果m*m<=x, and (m+1)^2>x，那么答案一定是M

而其他的一些求range的题目， 知道m和m-1相对于x的大小关系， 不能明确得出谁是答案

所以着力于这个特征， 二分法可以实现出来， 不需要考虑边界条件。

另外的一个需要注意的是， m^2很容易overflow。 为了解决这个问题， 我们采用**除法**：m<=x/m

# 代码
```
public class Solution {
    public int mySqrt(int x) {
        if (x==0)   return 0;
        if (x<0)    return -1;
        if (x==1)   return 1;
        
        int l = 1;
        int r = x/2;
        
        while (l<r){
            int m = l+(r-l)/2;
            if (m<=x/m && (m+1)>x/(m+1)){
                return m;
            }else if(m>x/m){
                r = m-1;
            }else{
                l = m+1;
            }
        }
        
        return l;
    }
}
``` 

