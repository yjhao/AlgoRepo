# 459-Repeated Substring Pattern (KMP)
## 题意
给定一个非空字符串，判断它是否可以通过自身的子串重复若干次构成
## 解题， 非暴力法，O(n)
采用KMP 算法

计算kmp的pre 数组， 字符串长度为size， k = pre[size-1]， 如果size 能够除尽 (size-k） 的话， 那么就说明这个字符可以被 长为 (size-k)的字符用若干次构成。

## 细节
需要注意的是， 一定要保证 k 不能为0， 如果为0的话， 说明一次循环都没有发生， 但是 size 又能够除尽 (size-k)。

## 代码
```
public boolean repeatedSubstringPattern(String str) {
        if (str.length()<=1)    return false;
        
        int[] kmp = new int[str.length()];
        
        for (int i=1; i<str.length(); i++){
            int pre = kmp[i-1];
            while (pre>0 && str.charAt(pre) != str.charAt(i)){
                pre = kmp[pre-1];
            }
            if (str.charAt(i) ==  str.charAt(pre)){
                kmp[i] = pre+1;
            }
        }
        
        if (kmp[str.length()-1]==0) return false;
        return str.length() % (str.length() - kmp[str.length()-1]) == 0;
    }
```

