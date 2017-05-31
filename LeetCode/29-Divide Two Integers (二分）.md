# 29-Divide Two Integers (二分）

## 题意
除法

## 解题
不能用减法， 因为太慢， 所以只有用类似于二分法的方法

比如说15除以3， 我们看有多少个3的 2^n 的倍数

首先是3， 然后是6， 然后是12， 都没问题， 此时至少有4个3

但是接下来24就不可以了， 大于除数15了

没关系， 锯掉之前已经处理过的部分， 也就是12， 再次往复之前的操作

除数现在变为了15-12=3.

被除数首先是3， 然后是6， 发现已经大于除数了， 所以中支， 加上这个循环中1个3

所以总共是5个3

**细节**
注意overflow， 所以使用long

## 代码
```
public class Solution {
    public int divide(int dividend, int divisor) {
        int flag = 1;
        if (dividend<0 && divisor>0 || dividend>0 && divisor<0)
            flag = -1;
        long divid = (long)Math.abs((long)dividend);
        long divis = (long)Math.abs((long)divisor);
        
        long res = 0;
        while (divid>=divis){
            long temp = 1; long tempd = divis;
            while (divid>=(tempd<<1)){
                tempd = tempd<<1;
                temp = temp<<1;
            }
            res += temp;
            divid -= temp*divis;
        }
        
        if (res*flag>Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        return (int)res*flag;
        
        
    }
}
```

