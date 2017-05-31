# 483-Smallest Good Base
## 题意
给一个数， 转化进制， 将这个数转为话某个进制的“11111....1“， 为了使这个 1 的串尽可能的长， 求出这个进制是多少。

## 解题
首先， 可以明确的是， 无论什么数， 都能转化为这样的1串， 对于N, 如果转化为 n-1 的进制， 那么就会变为 11。

我们知道最后可能会是一个全为1的字符串， 那么我们就可以对于每一个固定长度的字符串， 进行二分搜索。

那么有两个问题。

1. 这个字符串的最小长度肯定为2， 那么最大长度为多少呢？
2. 对于每一次二分搜索， 上界和下界在哪里？

## 解答问题1
最大长度的时候， Base 肯定为2。 

1 + k + k^2 + k^3 + ... + k^(m-1) = (k^m - 1) / (k-1) = (2^m-1)/(2-1) = 2^m - 1 ;


那么 **如果** 2^m-1 == n, m = log2（n+1)。

所以这就是 M 的上界。

注意Java没有 Base2， 所以要使用 log(n+1) / log(2)

## 解答问题2
这又是一个数学问题了。。。

1 + k + k^2 + k^3 + ... + k^(m-1) = n 
--->

n > k^(m-1) 
---> 

**k < n^(1/(m-1))**

1 + k + k^2 + k^3 + ... + k^(m-1) = (k^m - 1) / (k-1) = n
--->

k^m - 1 > n
--->

**k>(n+1)^(1/m)**

以上就是上界和下界的确定。。

## 代码
```
public class Solution {
    public String smallestGoodBase(String n) {
        long val = Long.valueOf(n);
        int m1 = 2;
        int m2 = (int)(Math.log(val+1) / Math.log(2));
        for (int i=m2; i>=m1; i--){
            long l = (long)(Math.pow(val+1, 1.0/i));
            long r = (long)(Math.pow(val, 1.0/(i-1)));

            while (l<=r){
                long k = l + (r-l)/2;
                long f = 0L;
                for (int j=0; j<i; j++, f = f*k + 1);

                if (f == val){
                    return String.valueOf(k);
                }else if (f < val){
                    l = k+1;
                }else{
                    r = k-1;
                }
            }
        }

        return String.valueOf(val-1);
    }
}
```


