# 162-FInd Peak Element (二分法）
## 题意
题目很简单， 找到一个peak element， num[-1] = num[n] = 负无穷

## 解题
很容易想到二分法， 通过mid的规律来判断。有一点需要注意， 如果m比左右任意一个数小， 那么peak就在相应的那一边。

但是要注意的是， 万一mid左边没有数了怎么办? 比如[1,2]。 1 左面就没有数了 --》 所以我们就只比较M和M右边的数。

**二分法的规律**：

当l!=r的时候， M可能出现在数段的最左端L， 绝不可能出现在最右端R， 所以总有(m+1）存在。 我们可以利用这个来判断。 

```
if (nums[m]>nums[m+1])
            {
                r = m;
            }
            else
            {
                l = m+1;
            }
```

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


