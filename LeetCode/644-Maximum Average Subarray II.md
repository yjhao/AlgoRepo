# 644-Maximum Average Subarray II

## 题意
给一个Array和一个长度k， 问在这个Array中寻找长度大于等于K的子序列中平均值最大的那一个值。

## 解题一
简单粗暴的方法就是用prefix sum对每个子序列进行求和， 然后求出最大值。 但是还有更好的方法。 

## 解题二
返回的是double， 精度判读是十的负五次方。 那么我们可以尝试用二分来做一下， 二分的内容是： 在当前的数组中， 能不能找到长度大于等于k的子序列平均值大于等于 m 的， 如果可以的话， 那么 l = m， 否则的话， r = m。

具体在于怎么简单的实现 “能不能找到长度大于等于k的子序列平均值大于等于 m”。

我们可以用O(n)的复杂度解决这个问题。

用的是greedy的思想， 抛弃做负贡献的子数列， 而且任何时候都保持一个最短长度为k的子数列的和。

首先将前k项相加得到 curSum， 如果其大于等于 k*m 了， 那么直接返回true。 否则的话我们还需要对下面的数进行visit。 那么子序列长度是可以变的， 我们怎么能够确定有一个方法适用于所有长度大于等于k的呢?

我们首先将下一个数 i 加入 curSum， 然后将“有可能需要踢出去”的 数i-k加入 preSum。 如果我们发现preSum 的和小于 preSum.size * k， 那么说明前面的数 做的是负贡献， 那么我们可以判断， 如果把前面的那些数全部截断的话， 后面的和一定会是更大的。 如果后面部分的和的平均数都不能满足 大于等于m， 那么加上前面的数更不可能完成。在截掉前面的preSum之后， 后面的数列的长度为k。

在更新 curSum 之后， 然后判断他是不是平均数大于等于m的。

### 技巧
与其判断平均数是否大于 m， 我们可以在做加和的时候， 只加上与平均数 m 的差值的那一部分， 这样的话， 如果curSum >=0， 那么就可以判断为真； 如果 preSum<0 的话， 就可以判断他一定是做的负功。

## 代码
```
class Solution {
    public double findMaxAverage(int[] nums, int k) {
        double l = -10000.0;
        double r = 10000.0;
        
        while (r-l > 0.000004){
            double mid = l + (r-l)/2;
            if (valid(nums, mid, k)) l = mid;
            else r = mid;
        }
        
        return l;
    }
    
    boolean valid (int[] nums, double target, int k){
        double curSum = 0.0;
        for (int i=0; i<k; i++) curSum += nums[i]-target;
        if (curSum >= 0) return true;
        double preSum = 0.0;
        
        for (int i=k; i<nums.length; i++){
            curSum += nums[i] - target;
            preSum += nums[i-k] - target;
            
            if (preSum < 0){
                curSum -= preSum;
                preSum = 0.0;
            }
            
            if (curSum >= 0)    return true;
        }
        return false;
    }
}
```  