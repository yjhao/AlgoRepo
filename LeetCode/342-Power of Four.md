# 342-Power of Four

## 解题
不能使用 类似 power of 3 的 用最大4的方次数 来判断一个数是否合格。

所以我们来看4的完全方次数有什么特点。

第一， 肯定是2的完全方次数， Bit 中只有1个1， 所以， n&（n-1) == 0

第二， 因为是4的倍数， 所以 1 出现在 奇数位置上， 比如 4^0 = 1, 4^1 = 100, 4^2 = 10000。所以我们用一个 Bit mask 来判断。
0x55 = 01010101


## 代码
```
public class Solution {
    public boolean isPowerOfFour(int num) {
        if (num<=0 || (num&(num-1))!=0) return false;
        if ((num & 0x55555555) != num) return false;
        return true;
    }
}
```

