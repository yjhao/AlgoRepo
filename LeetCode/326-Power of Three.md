#326-Power of Three

##题意
判断一个数是否为3的方次数， 不能用loop

##解题
用最大的一个 3的方次数 对 n 取余， 即可判断是否为3的方次数。

##代码
```
public class Solution {
    public boolean isPowerOfThree(int n) {
        // The expression "(int) Math.pow(3, (int) (Math.log(Integer.MAX_VALUE) / Math.log(3.0))" returns max integer that is "power of 3"
        return n > 0 && (int) Math.pow(3, (int) (Math.log(Integer.MAX_VALUE) / Math.log(3.0))) % n == 0;
    }
}
```