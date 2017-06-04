# 581-Shortest Unsorted COntinuous Subarray （极大值极小值数轴）

## 题意
给一个整数 Array， 找出一个最短的subarray， 将这个Subarray排序之后， 整个array也会成为一个排好序的 array。

## 解题 nlogn
当然最简单的方法， 就是将这个array sort, 然后比较 Sort 前后的Array, 找到发生变化的最左边的一个点， 和最右边的一个点， 中间的部分就是这个最短的 array。这个方法的复杂度为 nlogn

当然， 也可以有 O(n) 的方法。

## 解题 O(n)
我们可以用一些 特征来判断， 哪些地方是 out of order 的。

如果从左往右一个一个扫描， 如果是排好序的， 那么后一个一定比前面区间的最小值大， 最大值大。 

那么如果出现了 比之前的区间的最小值或者最大值小， 则一定是出现了乱序。 

但是如果出现了乱序，那么是否一定比最大值小？ 是否一定比最小值小？

因为最大值m一定比最小值n大。 假设有一个数产生乱序， 那么在数轴上来看， 他要么是在 n 的左边， 或者在 m 和 n 的之间。 所以如果用 比 最小值 小 的条件来判断的话， 就会漏掉 m 和 n 之间的部分。 但是如果使用 比最大值m小 来判断的话， 则两个部分都能包括。

所以更充分的条件来判断出现了乱序， 是这个数要比之前的最大值小。这是一个判断是否乱序的**充要条件**。

如果使用“比前面的最小值小” 是不能够充分的判断这是排好序了的， 比如说 1 9 8， 8比最小值1大，所以从条件判断， 他不是乱序的， 但是它确实乱序的。

从左往右依次使用这个条件来判断， 我们就可以找出， 最右边的一个乱序的点， 那么这个点也就是 subarray的右端点。

相似的， 我们也可以用 “比之前的最小值要大” 的准则， 从右往左扫描， 来判断左端点。然后左右端点之间的区间就是这个 subarray。

## 代码
```
public class Solution {
    public int findUnsortedSubarray(int[] nums) {
        int max = nums[0];
        int min = nums[nums.length-1];
        int left = -1;
        int right = -1;
        
        for (int i=1; i<nums.length; i++){
            if (nums[i] < max)  right = i;
            if (nums[nums.length-1-i] > min)    left = nums.length-1-i;
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[nums.length-1-i]);
        }
        
        if (left==-1)   return 0;
        return right-left+1;
    }
}
```

