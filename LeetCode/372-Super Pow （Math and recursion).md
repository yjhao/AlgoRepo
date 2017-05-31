# 372-Super Pow （Math and recursion)
## 题意
计算 a^b mod 1337, b为一个很大的数， 以一个Array的形式来呈现。 比如说 ``` a = 2, b = [1,0]``` 答案为 1024.

## 解题
```
a^(123)%1337 = (a^120%1337) * (a^3%1337)

a^120%1337 = (a^12%1337) ^ 10

--> 

f(a, 123) = f(a, 120) * powMod(a, 3)
f(a, 120) = powMod(f(a,12), 10)
f(a, 12)  = f(a, 10) * powMod(a, 2)
f(a, 10)  = powMod(f(a, 1), 10)
f(a, 1)   = a%1337;

```

**需要注意的细节**
有可能 a 很大， 所以一开始就要  ```  a %= 1337```

```
public class Solution {
    public int superPow(int a, int[] b) {
        if (b.length==0)  return 0;
        
        return helper(a, b, b.length-1);
    }
    
    int helper(int a, int[] b, int idx){
        if (idx==0){
            return powMod(a, b[0]); 
        }
        int right = powMod(a, b[idx]);
        int left = powMod(helper(a, b, idx-1), 10);
        return (right%1337) * (left%1337) % 1337;
    }
    
    
    int powMod (int num, int pow){
        if (pow==0) return 1;
        num = num%1337;
        int res = num;
        for (int i=1; i<pow; i++){
            res = res*num%1337;
        }
        return res;
    }
}
```


