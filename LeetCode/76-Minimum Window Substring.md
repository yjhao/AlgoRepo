# 76-Minimum Window Substring
## 题意
Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

For example,
S = "ADOBECODEBANC"
T = "ABC"
Minimum window is "BANC".

## 解题
首先， 很明显要用two pointer方法。 并且可以使用数组， 来代替hashmap。

那么对于一个区间内， 如何判断包括了所有的字符呢？
-- 用一个count来表示， 当Count等于t的长度的时候， 说明包括了所有的字符。

那么如何计算count呢？

1. 如果一个字符不存在于t中， 直接跳过.
2. 如果一个字符存在于t中， 那么只有当前这个字符的个数小于在t中的相应个数的时候， 才计算Count.


当Count=t长度的时候， 我们就计算此时window长度， 并收紧左端， 更新Count， **更新最小窗口大小**。如果此时一个字符在window中的个数小于相应的在T中的个数， 那么count就应该减一， 因为此时已经不是一个valid window了。

## 代码
```
public class Solution {
    public String minWindow(String s, String t) {
        // first process the string t
        int[] map       = new int[128];
        boolean[] valid = new boolean[128];
        for (int i=0; i<t.length(); i++){
            char cur = t.charAt(i);
            map[(int)cur]++;
            valid[(int)cur] = true;
        }
        
        // process the s string
        int left = 0;
        int right = 0;
        String res = "";
        int min = s.length()+1;
        int count = 0;
        
        while (right<s.length()){
            char cur = s.charAt(right);
            if (!valid[(int)cur]){
                right++;
            }else{
                map[(int)cur]--;
                if (map[(int)cur]>=0){
                    count++;
                }
            
                while (count==t.length()){
                    if (right-left+1<min){
                        min = right-left+1;
                        res = s.substring(left, right+1);
                    }
                
                    char leftOne = s.charAt(left);
                    if (valid[(int)leftOne]){
                        map[(int)leftOne]++;
                        if (map[(int)leftOne]>0){
                            count--;
                        }
                    }
                    left++;
                }
                right++;
            }
        }
        
        return res;
    }
}
```


