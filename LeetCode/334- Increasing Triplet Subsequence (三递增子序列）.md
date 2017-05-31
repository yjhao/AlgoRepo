# 334- Increasing Triplet Subsequence (三递增子序列）

## 题意
求有没有一个子序列 ， i<j<k and nums[i]<nums[j<nums[k]

## 解题
首先想到保存最小值 和 第二最小值， 比较当前的值和第二最小值得大小关系

但是万一出现了 这个数字比先前出现的最小值还要小了， 该怎么办呢？

**直接更新最小值为当前的数值**

因为第二最小值没有更新， 第二最小值还“保留”了先前的信息， 如果等会出现的数字， 比第二最小值大， 说明他一定满足题目的三递增关系

如果等会出现的数字， 比第二最小值小， 而比第一最小值大， 更新第二最小值。

## 代码
```
public class Solution {
    public boolean increasingTriplet(int[] nums) {
        if (nums==null || nums.length<=2)   return false;
        int min_one = Integer.MAX_VALUE;
        int min_two = Integer.MAX_VALUE;
        
        for (int num : nums){
            if (num<min_one){
                min_one = num;
            }else if (num<min_two && num>min_one){
                min_two = num;
            }else if(num>min_two){
                return true;
            }
        }
        
        return false;
    }
}
``` 

