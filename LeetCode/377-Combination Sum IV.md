# 377-Combination Sum IV
## 题意
给一个数组 其中没有重复的数字 和 一个target， 问一共有多少种组合方式， 使这个组合的和为 target。注意的是， 1 1 2 和 1 2 1要被算为两个不同的组合。

## 解题
使用DP来解题， dp[n] 为总和为 n 的组合一共有多少， 那么假设当前数字为 m， 那么 dp[n] += dp[n-m]。 需要注意的是， 因为每个数字可以使用任意多次， 所以for loop 的顺序是从小到大。

还需要注意的是， for loop 的外层一定要是对 target 的循环， 内循环是对数组的循环。 为什么要这样的？ 

因为 dp[n] += dp[n-m] 的时候， dp[n] 是建立在 dp[n-m]的基础上的， **dp[n-m]的组成， 是由Nums中的任意几个数字组成的， 而不是在当前的数字之前的数字组成的**， 所以我们求解dp[n]的时候，  dp[n-m] 必须求解已经完成（包含了Nums中的所有数字）， 要使其完整， 那么一定要把它放在外循环。 反之，如果把他放在内循环， 那么当使用 dp[n] += dp[n-m]的时候， dp[n-m] 还没有被求解完整， 会有一些i之后的数字没有被包括进来。

这与Target Sum 那道题截然相反。 Target Sum 那道题， 外循环是数组， 而内循环是target sum。 **因为数字是一个一个从左到右进行计算的， 当计算到i的时候， 一定是没有考虑到i之后的数字的情况.**

### 细节
我们可以先把nums sort一下， 这样的话， 当在loop中发现nums[i] > n的时候，就可以快速的break。

## 不能使用2D DP
还有一种想法就是， 使用 dp[i][n] 来求解， 前I个数的， 总和为 n 的组合数。 然后 dp[i][n] = dp[i-1][n] + dp[i-1][n-nums[i]] + dp[i][n-nums[i]];

但这被证明是不对的， 为什么呢？

**因为答案和数字的顺序有关， 不能使用 前i个这样的设定。**

## 代码
```
public class Solution {
    public int combinationSum4(int[] nums, int target) {
        if (nums.length==0) return 0;
        Arrays.sort(nums);
        int[] cnt = new int[target+1];
        cnt[0] = 1;
        
        for (int i=1; i<=target; i++){
            for (int j=0; j<nums.length; j++){
                if (nums[j]>i)  break;
                cnt[i] += cnt[i-nums[j]];
            }
        }
        
        return cnt[target];
    }
}
```
