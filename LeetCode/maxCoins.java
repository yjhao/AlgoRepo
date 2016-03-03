/*
https://leetcode.com/problems/burst-balloons/

nature way to divide the problem is burst one balloon and separate the balloons into
2 sub sections one on the left and one one the right. However, in this problem the
left and right become adjacent and have effects on the maxCoins in the future.

we can find that for any balloons left the maxCoins does not depends on the balloons
already bursted. This indicate that we can use memorization (top down) or dynamic programming
(bottom up) for all the cases from small numbers of balloon until n balloons

Therefore instead of divide the problem by the first balloon to burst, we divide the
problem by the last balloon to burst.

Why is that? Because only the first and last balloons we are sure of their adjacent balloons
before hand!

For the first we have nums[i-1]*nums[i]*nums[i+1] for the last we have nums[-1]*nums[i]*nums[n].

We can see that the balloons is again separated into 2 sections. But this time since the balloon
i is the last balloon of all to burst, the left and right section now has well defined boundary
and do not affect each other! Therefore we can do either recursive method with memoization or dp.

The characteristic of "last burst" is the distance from left to right is the samllest
it can be, which is 2. Thus, the length of window is build up from shorter ranges to
longer ranges.

This dp works in this way: we scan the array from len 2 to len n with ALL POSSIBLE start points and
end points. For EACH combination, we will find the best way "i" to burst balloons.

The "neighbor" of i is always "left" and "right", because assuming, from left to i, from i to right,
all the items have alredy been bursted (except left, i, right).
So only balloons left, i and right exits in current combination,

This problem could be solved using DP (bottom up), or divide and conquer with memorization
(top down).

*/

public class Solution {

	// DP
    public int maxCoins(int[] nums) {
        if (nums==null || nums.length==0)   return 0;

        // include the boundary
        int[] arr = new int[nums.length+2];
        arr[0] = arr[nums.length+1] = 1;
        for (int i=1; i<nums.length+1; i++){
        	arr[i] = nums[i-1];
        }
        int len = nums.length+2;
        int[][] dp = new int[len][len];

        // dp solution
        for (int k=2; k<len; k++){
        	for (int left=0; left<len-k; left++){
        		int right = left+k;
        		for (int i=left+1; i<right; i++){
        			dp[left][right] = Math.max(dp[left][right],
        				dp[left][i]+dp[i][right]+arr[left]*arr[i]*arr[right]);
        		}
        	}
        }

        return dp[0][len-1];
    }

    // divide and conquer with memorization
    public int maxCoins(int[] nums) {
    	if (nums==null || nums.length==0)   return 0;

        // include the boundary
        int[] arr = new int[nums.length+2];
        arr[0] = arr[nums.length+1] = 1;
        for (int i=1; i<nums.length+1; i++){
        	arr[i] = nums[i-1];
        }
        int len = nums.length+2;
        int[][] memo = new int[len][len];

        return helper(memo, arr, 0, len-1);
    }

    public int helper(int[][] memo, int[] arr, int left, int right){
    	if (left+1 == right)	return 0;
    	if (memo[left][right]>0)	return memo[left][right];
    	int ans = 0;

    	for (int i=left+1; i<right; i++){
    		ans = Math.max(ans, helper(memo, arr, left, i)+
    			helper(memo, arr, i, right) + arr[left]*arr[i]*arr[right]);
    	}
    	memo[left][right] = ans;
    	return ans;
    }

}
