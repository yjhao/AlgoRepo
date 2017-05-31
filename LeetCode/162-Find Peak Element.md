# 162-Find Peak Element

## 题意
在一个数组中， 找到一个相对于邻居的peak， -1 和 N+1 index 都为负无穷

## 解题
peak只是相对于邻居的， 所以不需要全局看， 只需要比较这个数和他旁边的数的大小关系

if  val[m] > val[m+1], then one peak must be found on the left hand of m, but could be m, so "r=m"

otherwise, val[m] < val[m+1], one peak must be found on the right hand of m, so "l=m+1"

**如果更新 l=m， 一定要小心， 因为有可能死循环！** 

## 代码
```
public class Solution {
    public int findPeakElement(int[] nums) {
        if (nums.length==1)     return 0;
        
        int left = 0;
        int right = nums.length-1;
        
        while (left<right)
        {
            int mid = (left+right)/2;
            
            if (nums[mid]>nums[mid+1])
            {
                right = mid;
            }
            else
            {
                left = mid+1;
            }
        }
        
        return left;
    }
}
```

