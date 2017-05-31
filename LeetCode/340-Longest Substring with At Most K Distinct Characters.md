# 340-Longest Substring with At Most K Distinct Characters 

## 题意
Given a string, find the length of the longest substring T that contains at most k distinct characters.

## 解题
明显的， 用双指针

用map记录字符的**最后出现**位置， 这样的方法， **可以适用于“流”， 减少内存空间。**

用一个counter记录当前 window 的不相同字符数， 同时记录当前window 的左端位置。

当 visit 到一个新的位置， 分几种情况：

1. 如果字符第一次出现， 直接加入， 并更新长度， 更新counter
2. 如果不是第一次出现， 但是 counter 小于 K, 直接加入， 更新长度。
3. 如果不是第一次出现， 而且 counter == k， 从左端开始进行删除， 知道 不相同的字符数 小于k。 然后再加入新字符， 更新长度与Counter。

## 细节
窗口左端 while 进项删除， 更新 counter 之后， 加入右端的新字符之后， 一定也要 **更新 Counter**

## 代码
```
public class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s==null || s.length()<=k)   return s.length();
        if (k<1)   return k;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        
        int res = 0;
        int count = 0;
        int left = 0;
        
        for (int i=0; i<s.length(); i++){
            if (map.containsKey(s.charAt(i))){
                map.put(s.charAt(i), i);
                res = Math.max(res, i-left+1);
            }else if (!map.containsKey(s.charAt(i))){
                if (count<k){
                    map.put(s.charAt(i),i);
                    count ++;
                    res = Math.max(res, i-left+1);
                }else if (count>=k){
                    while (left<i && count >=k){
                        char cur = s.charAt(left);
                        if (map.get(cur)==left){
                            count --;
                            left++;
                            map.remove(cur);
                        }else{
                            left++;
                        }
                    }
                    count++;
                    map.put(s.charAt(i), i);
                    res  = Math.max(res, i-left+1);
                }
            }
        }
        
        return res;
    }
}
```

