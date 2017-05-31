# 209-Minimum Size Subarray Sum
## 题意
在一个正数的数列中， 找到最短连续数列， 它的和大于等于一个值。

## 解题
明显 two pointer

## 注意细节
### 注意r++ 之后， r到哪里去了， 计算有效长度的时候
### 有可能所有的和加起来都没有目标值大， 要判断到底是不是真。

## 代码
```
public class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        if (nums==null || nums.length<1)    return 0;
        int r = 0;
        int l = 0;
        int sum = 0;
        int minLen = nums.length+1;
        
        while (r<nums.length){
            while (sum<s && r<nums.length){
                sum += nums[r++];
            }
            
            while (sum>=s && l<nums.length){
                int curLen = r-l;
                minLen = Math.min(curLen, minLen);
                sum -= nums[l++];
            }
        }
        
        if (minLen == nums.length+1){
            return 0;
        }
        return minLen;
    }
}
```


