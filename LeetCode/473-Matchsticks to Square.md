#473-Matchsticks to Square
##题意
给最长15根火柴棍， 问可不可以组成一个正方形。

##解题
因为只有最长15个数字， 所以可以想到用Brutal force dfs。

我的第一个dfs想法是， 首先先组成正方形的一边， 然后再在剩下的棍子中寻找第二边， 然后依次第三边， 第四边。

这样的算法， 小的case是可以通过的， 不过通过不了大的case。 原因在于：

1. 要使用一个array来标记哪些是已经使用过的。
2. 在下一层迭代中， 需要从一个小的Index依次增大， 来找到下一个还没有被使用过的火柴棍， 然后再进入下一层的dfs。 这样的算法， 要浪费很多时间在 traverse list 上。

##更好的DFS
如何更少的遍历火柴棍？我们对于每一根火柴棍， 依次将他放入 第一条边， 或者第二条边， 或者第三条边， 或者第四条边。 如果有任何一边返回 true， 那么就返回 true。 

这样的算法， 虽然也是brutal force， 但是我们省去了很多遍历list的时间， 每一次增加或者减小Index， 都做出了决定， 而不是没有意义的遍历。

##细节
如果一个搭配是false，为了让其更早的返回 false 从而不再继续dfs下去， 我们需要 greedy 的把大的数字越早考虑越好。 所以**我们首先要对所有火柴棍排序**， 然后从最大的开始考虑。

##代码
```
public class Solution {
    public boolean makesquare(int[] nums) {
        if (nums.length<4)  return false;
        int sum = 0;
        for (int num : nums)    sum += num;
        if (sum%4!=0)   return false;
        for (int num : nums)    if (num > sum/4)    return false;
        Arrays.sort(nums);
        
        return helper(nums, nums.length-1, sum/4, 0, 0, 0, 0);
    }
    
    boolean helper(int[] nums, int idx, int sum, int s1, int s2, int s3, int s4){
        if (idx==-1){
            if (s1==sum && s2==sum && s3==sum && s4==sum)   return true;
            else return false;
        }
        
        if (s1>sum || s2>sum || s3>sum || s4>sum)   return false;
        
        int cur = nums[idx];
        
        return helper(nums, idx-1, sum, s1+cur, s2, s3, s4) ||
               helper(nums, idx-1, sum, s1, s2+cur, s3, s4) ||
               helper(nums, idx-1, sum, s1, s2, s3+cur, s4) ||
               helper(nums, idx-1, sum, s1, s2, s3, s4+cur);
    }
}
``` 