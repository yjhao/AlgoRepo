# 486-Predict the Winner （minmax)

## 题意
有一个数组， 两个人玩这个游戏， 每次轮流从数组的两端任取一个数， 直到数组为空为止。然后比较两个人分别获得的总数， 看谁更多。

假设两个人都是最优的玩， 问首先取这个人能不能获胜。

## 解题
假设数组是从 i 到 j， 那么如果对于第一个人， 可以取出 i 或者 j。

因为是最优的玩， 所以当前的选择的准则是， 在剩下的数组中， 自己获得的期望是最大的。 

如果选的是 I, 那么剩下的是 (i+1, j)， 反之， 剩下的是 （i, j-1。

因为是轮流玩， 在剩下的数组中， 第二个人再来最优的取。对于第二个人， 也有两种取得情况， 分别是两头。 

假设第一个人取得是 I, 所以剩下 (i+1, j)，然后第二个人再来取， 因为第二个人肯定会把最坏情况留给第一个人， 所以对于第一个人， 得到的会是 min [ (i+2, j), (i+1, j-1) ]。 

然后对于第一个人，在先取i， 或者先取j的两种情况下的最坏情况中，得到最好的情况， 就是当前的最佳期望。


##  代码
```
public class Solution {
    public boolean PredictTheWinner(int[] nums) {
        if (nums.length<=2) return true;
        int[][] vals = new int[nums.length][nums.length];
        for (int i=0; i<nums.length; i++){
            for (int j=0; j<nums.length; j++){
                if (i==j)   vals[i][j] = nums[i];
                else    vals[i][j] = -1;
            }
        }
        int len = nums.length-1;
        
        helper(vals, nums, 0, len);
        int sum = 0;
        for (int num : nums)    sum += num;
        if (vals[0][len] >= sum-vals[0][len])   return true;
        else return false;
    }
    
    int helper(int[][] vals, int[] nums, int left, int right){
        if (left>right ) return 0;
        if (vals[left][right]!=-1)  return vals[left][right];
        
        // choose left one
        int sum1 = nums[left];
        sum1 += Math.min(helper(vals, nums, left+1, right-1), helper(vals, nums, left+2, right));
        
        // choose right one
        int sum2 = nums[right];
        sum2 += Math.min(helper(vals, nums, left+1, right-1), helper(vals, nums, left, right-2));
        
        vals[left][right] = Math.max(sum1, sum2);
        return vals[left][right];
    }
}
```
 