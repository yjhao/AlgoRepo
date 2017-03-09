#494-Target Sum （一维背包）
##题意
给以个Int array和一个target， 然后在每一个数字前面加上加号或者减号， 问最后的计算结果达到target， 一共有多少种组合方式。

比如说：
Input: nums is [1, 1, 1, 1, 1], S is 3. 
Output: 5
Explanation: 

-1+1+1+1+1 = 3

+1-1+1+1+1 = 3

+1+1-1+1+1 = 3


+1+1+1-1+1 = 3

+1+1+1+1-1 = 3

##解题
假设正数的和为 m， 负数的和为 n, 一共有p个数， 所有数字的和为 sum, 那么：

```
m+n = sum;
m-n = target;
==> 2m = (sum+target);
==> m = (sum+target)/2;
```
所以这个问题就转化为， 在所有的数里面， 能不能找到 若干个数， 他们的和为 m =（sum+target)/2， 而且这样的组合一共有多少种。

所以可以归纳为一个 dp 问题， dp[p][m]， 在p个数的情况下， 有多少种组合方式可以达到和为m。

```
dp[x][m] = dp[x-1][m] + dp[x-1][m-nums[x]]
```

或者我们也可以将其转化为一维的背包问题， 如果将其变为一维的话， 那么For循环必须从大到小， 因为如果从小到大的话， 小的数目发生变化之后 （新的一层覆盖掉旧的一层）， 就会影响到之后update大的数目的情况 （本来应该使用旧的一层进行计算， 但是旧的一层已经被更新为新的一层）。 反正，如果从大到小的话， 就不会出现问题。 因为当update小的数目的时候， 使用的一定是更小的数目， 而这些更小的数目还没有被更新到。

##代码
```
public class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        int sum = 0;
        for (int num : nums)    sum += num;
        if (sum < S || S<-sum || (sum + S)%2==1)  return 0;
        int target = (sum + S)/2;  
        
        int[] dp = new int[target+1];
        
        dp[0] = 1;
        for (int num : nums){
            for (int i=target; i>=num; i--){
                dp[i] += dp[i-num];
            }
        }
        return dp[target];
    }
}
```