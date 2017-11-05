# 670-Maximum Swap

## 题意
给一个正数， 问Swap两个数位之后， 最大的数是多少。 如果Swap得不到更大的数， 那么就返回自己。

## 解题
对于某一位I, 如果要用某一位Swap它， 那么一定是用的在i之后的最大的数。

而且， Swap发生在越前面的位数， 最后的结果肯定就越大。

所以我们只需要从最后面往前面扫描， 记录下之前的最大值， 看当前的数字是不是小于最大值， 如果小于最大值的话， 那么swap就可以发生在这一个位置。

## 细节
我们用idx来记录下之前的最大的数的位置， 并随时更新。 我们得需要用另外的一个数据结构来记录下Swap发生的位置， 因为 Swap 发生的位置， 和 当前的 最大数的位置有可能不一样。 所以我们不能使用最大数的位置来代表 swap 的发生的位置。

比如说938，当遍历完数字之后，当前的最大数的位置是9， 而发生Swap的地方是8 和 3。

## 代码
```
class Solution {
    public int maximumSwap(int num) {
        char[] nums = String.valueOf(num).toCharArray();
        
        char max = nums[nums.length-1];
        int idx2 = nums.length-1;
        int[] swapIdx = new int[] {-1,-1};
        
        for (int i=nums.length-2; i>=0; i--){
            if (max > nums[i]){
                swapIdx[0] = i;
                swapIdx[1] = idx2;
            }
            
            if (nums[i]>max){
                max = nums[i];
                idx2 = i;
            }
        }
        
        if (swapIdx[0] == -1) return num;
        
        char temp = nums[swapIdx[0]];
        nums[swapIdx[0]] = nums[swapIdx[1]];
        nums[swapIdx[1]] = temp;
        
        return (int)Integer.valueOf(new String(nums));
    }
}
```