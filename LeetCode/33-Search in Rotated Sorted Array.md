#33-Search in Rotated Sorted Array

## 题解
二分法

找规律， 分区间， 找**一定成立的规律**

## 代码
```
public class Solution {
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length-1;
        
        while (l<=r){
            int m = l+(r-l)/2;
            if (nums[m] == target){
                return m;
            }else if(nums[l] == target){
                return l;
            }else if(nums[r] == target){
                return r;
            }
            
            if (nums[m]>nums[r]){
                if (target>nums[l] && target<nums[m]){
                    r = m-1;
                }else{
                    l = m+1;
                }
            }else{
                if (target>nums[m] && target<nums[r]){
                    l = m+1;
                }else{
                    r = m-1;
                }
            }
        }
        return -1;
    }
}
```