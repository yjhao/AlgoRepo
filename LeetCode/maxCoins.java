/*
https://leetcode.com/problems/burst-balloons/

nature way to divide the problem is burst one balloon and separate the balloons into 
2 sub sections one on the left and one one the right. However, in this problem the 
left and right become adjacent and have effects on the maxCoins in the future.

However, the coins you get for a balloon does not depend on the balloons already burst. 
Therefore instead of divide the problem by the first balloon to burst, we divide the 
problem by the last balloon to burst.

The characteristic of "last burst" is the distance from left to right is the samllest 
it can be, which is 2. Thus, the length of window is build up from shorter ranges to 
longer ranges.

And apprantly, the "neighbor" of i is always "left" and "right", because from left to i, 
from i to right, all the items have alredy been bursted (except left, i, right)

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
