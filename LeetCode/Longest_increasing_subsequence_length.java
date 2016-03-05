/** Problem Statements   https://leetcode.com/problems/longest-increasing-subsequence/
 *  Given an unsorted array of integers, find the length of longest increasing subsequence.

    For example,
    Given [10, 9, 2, 5, 3, 7, 101, 18],
    The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4. Note
    that there may be more than one LIS combination, it is only necessary for you to return the length.

    Your algorithm should run in O(n2) complexity.

    Follow up: Could you improve it to O(n log n) time complexity?
  */


/** Solution:
  * Use Binary search and DP, O(nlogn) time complexity
  * Steps:

    1) traverse from 0 to end, built a DP array where stores an increasing subsequence (however, only the length is true, will
       discuss later).

    2) for each number, use binary search to find the insert location in the DP array, and update it.
    3) The point of step 2 is to greedily update the array, for example, we have [1,3,5], and the next is [2], we replace [3] with [2].

    4) However, as discussed in step 1, this DP always has the maxium length, but not exact every item. For example [1,3,5,2].
       the length of DP array is 3, however, the item is [1,2,5], which is not correct.

    Question: is there a O(nlogn) solution to PRINT a LIS?
 */

 public class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums.length<=1) return nums.length;

        int[] dp = new int[nums.length];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = nums[0];

        for (int i=1; i<nums.length; i++)
        {
            insertPos(dp, nums[i]);
        }

        for (int i=nums.length-1; i>=0; i--)
        {
            if (dp[i]!=Integer.MAX_VALUE)
            {
                return i+1;
            }
        }

        return -1;
    }

    private void insertPos(int[] dp, int num) {
        int left = 0;
        int right = dp.length-1;
        int res = -1;

        while (left<=right)
        {
            int mid = left + (right-left)/2;
            if(dp[mid]<num)
            {
                left = mid+1;
            }
            else
            {
                right = mid-1;
            }
        }

        dp[left] = num;
    }
}
