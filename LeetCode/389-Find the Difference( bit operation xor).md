# 389-Find the Difference( bit operation xor)
## 题意
有两个string， 一个比另外一个多了一个字母， 问这是哪个字母。

## 解题
因为只多了一个字母， 其他都一样， 所以这个字母肯定是落单的， 所以我们可以用 single number (xor) 的方法来获得这个字母。

不过需要注意的是， 位操作 出来的结果是int， 所以需要强制转换， 将其变为Char。

或者 直接使用  res ^= s.charAt(i)， 自动强制转换了。

## 代码
```
public class Solution {
    public char findTheDifference(String s, String t) {
        if (s.length()==0)  return t.charAt(0);
        char res = s.charAt(0);
        for (int i=1; i<s.length(); i++)    res ^= s.charAt(i); //res = (char)(res^s.charAt(i));     // both works
        for (int i=0; i<t.length(); i++)    res ^= t.charAt(i); //res = (char)(res^t.charAt(i));
        return res;
    }
}
```

