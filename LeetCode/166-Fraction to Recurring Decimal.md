# 166-Fraction to Recurring Decimal

## 题意
用 String 表示两个正数除法的结果， 如果有循环， 用括号表示。
比如 1/3 = 0.(6)

## 解题
使用 hashmap 来记录之前的余数， 如果当前运算出现了相同的余数， 说明从上次的余数开始， 到现在之前的所有部分， 都会再次循环。

## 几个细节
1. 有正数， 负数
2. 为了防止 overflow， 使用 long 来记录被除数， 除数， 和余数。

## 代码
```
public class Solution {
    public String fractionToDecimal(int numerator, int denominator) {
        StringBuilder sb = new StringBuilder();
        HashMap<Long, Integer> map = new HashMap<>();
        
        if (numerator==0)   return "0";
        
        if (numerator<0 && denominator>0 || numerator>0 && denominator<0)   sb.append("-");
        
        long nume = Math.abs((long)numerator);
        long deno = Math.abs((long)denominator);
        
        long first = nume/deno;
        sb.append(first);
        long left = nume%deno;
        if (left==0){
            return sb.toString();
        }else{
            sb.append(".");
            map.put(left, sb.length());
        }
        
        while (true){
            left*=10;
            sb.append(left/deno);
            left = left%deno;
            
            if (map.containsKey(left)){
                sb.insert(map.get(left), "(");
                sb.append(")");
                break;
            }
            map.put(left, sb.length());
            if (left==0)    break;
        }
        
        return sb.toString();
    }
}
```

