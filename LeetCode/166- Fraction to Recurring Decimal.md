# 166- Fraction to Recurring Decimal
## 题意
将两个整数相除的结果 转换为 string， 用括号表示循环。
比如： 1/3 = 0.(3)
## 解题
注意 corner case：

1. 一个正数， 一个负数
2. 一个为0，
3. 为了防止 overflow， 将 Int 变为 long

如何辨别 循环？

将 每次的余数放入 一个 hashmap 中， 记录下此时的 stringbuilder 位置。

如果下次再次碰见相同的余数， 从map中提取相应的位置I。

从I之后， 到现在的这一段结果， 都会再次循环。 所以加上括号即可。

## 代码
```
public class Solution {
    public String fractionToDecimal(int numerator, int denominator) {
        StringBuilder sb = new StringBuilder();
        
        if (numerator==0)   return "0";
        
        if (numerator<0 && denominator>0 || numerator>0 && denominator<0)   sb.append("-");
        
        long num = Math.abs((long)numerator);
        long den = Math.abs((long)denominator);
        
        // integral part
        sb.append(num/den);
        num = num%den;
        if (num==0)     return sb.toString();
        
        // fractional part
        sb.append(".");
        HashMap<Long, Integer> map = new HashMap<Long, Integer>();
        map.put(num, sb.length());
        
        while (num!=0)
        {
            num *= 10;
            sb.append(num/den);
            num = num%den;
            
            if (map.containsKey(num))
            {
                sb.insert(map.get(num), "(");
                sb.append(")");
                break;
            }
            else
            {
                map.put(num, sb.length());
            }
        }
        
        return sb.toString();
    }
}
```

