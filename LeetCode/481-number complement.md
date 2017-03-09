#481-number complement
##题意
对一个数字， 求出他的二进制反。比如说101， 变成 010。
##解题
取反， 首先想到的是 取反操作 ~， 但是这个操作的问题是 会把000101 变成 111010， 这就是一个负数了， 根据two component的表示。

答案应该是 000010， 那么如何把111010 变成 000010呢？ 

无论是0还是1， &1 之后都还是自己，&0之后一定变为0。 所以我们可以设计一个 mask， 在 010 这几位， 是1， 但是在 111这几位， 为0。所以这个Mask就为 000111。

构造权威1， 我们可以用 1000减去1， 即为 0111。

mask最高位的1， 恰好在原数的最高位。所以我们采用下面的方法：

```
(Integer.highestOneBit(num) << 1) - 1
```

然后 and ~num 就是答案了。


###two component：

1. 第一位是符号， 1 代表负数， 0 代表正数。
2. 1000 代表 -8， 1001 代表 -8+1 = 7， 所以除了第一位数字， 后面的都代表正数。
3. 这个表达方式的优点是， 正数加负数， 直接正常的加法就可以了， 最高一位不进位， 因为已经是最高位了。
4. 有些时候， 两个数相加之后， 会超出这么多位数能够表达的最大或者最小数。 那么如何知道这个数是不是正确的结果呢？ 正确的结果， 最高两位数， 一定是要么都是0， 要么都是1， 如果是一个1， 一个0， 就说明结果是错误的。


##代码
```
public class Solution {
    public int findComplement(int num) {
        int mask = (Integer.highestOneBit(num) << 1) - 1;
        num = ~num;
        return num & mask;
    }
}
```