# 28-strStr (Rowling Hash)
## 题意
实现 strStr

## 解题
使用rowling hash 来判断， 复杂度为 O(n)。

得注意的几个细节：

---
hash 更新的时候， 理论上说， 可以用乘法， 也可以用除法， 取决于左面大还是右面大。 但是，只能用乘法， 所以最大的要放在最左面， 这样更新的时候， 就可以 *base 来更新。 为什么呢？

因为用除法的话， 有可能在某一项 m * base， m 是大于 base的， 所以除出来的结果不一定是 m。 或者是余数很难处理？ （不敢确定）


---
其次， 在更新 hash 的时候， 要减去要删去的一个字符的val。 此时不能直接 hash = hash-val， 而是需要 使用 hash = hash+mod-val。 因为在每一步都是取了余的， 所以会导致 hash 有可能是小于 val的

## 代码
```
public class Solution {
    long MOD = 1000000007;
    long base = 31;
    public int strStr(String haystack, String needle) {
        if (needle.length()==0) return 0;
        if (haystack.length()<needle.length())    return -1;
        long target = helper(needle);
        long val = helper(haystack.substring(0, needle.length()));
        if (val==target)    return 0;
        
        long temp =1 ;
        for (int i=0; i<needle.length()-1; i++){
            temp = temp*base%MOD;
        }
        
        for (int i=0; i<haystack.length()-needle.length(); i++){
            int next = (int)haystack.charAt(i+needle.length());
            long add = (long)(next);
            
            int last = (int)haystack.charAt(i);
            long delete = (long)last*temp%MOD;
            val = (val+MOD-delete)%MOD;
            val = (val*base+add)%MOD;
            if (val == target )  return i+1;
        }
        return -1;
    }
    
    long helper(String str){
        if (str.length()==0)    return 0;
        long val = 0;
        for (int i=0; i<=str.length()-1; i++){
            val = (val*base%MOD + (int)str.charAt(i))%MOD;
        }
        return val;
    }
}
```


