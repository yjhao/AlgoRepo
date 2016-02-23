/** Problem Statement
Given an unsorted array return whether an increasing subsequence of length 3 exists
or not in the array.

Formally the function should:
Return true if there exists i, j, k
such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
Your algorithm should run in O(n) time complexity and O(1) space complexity.

Examples:
Given [1, 2, 3, 4, 5],
return true.

Given [5, 4, 3, 2, 1],
return false.
*/

/** Solution
 * The main idea is to keep two values when check all elements in the array: the minimum value
 min_one until now and the second minimum value min_two from the minimum value's position until
 now.

 Then:  there are three different scenarios:

        1). if cur > min_two, it means we found a triplet.
        2). if cur > min_one, considering cur is not larger than min_two, which means it is between
        min_one and min_two, thus we found a smaller value for min_two, then update min_two.
        3). if cur < min_one, we found a smaller value for min_one
 */

 public class Solution {
    public boolean increasingTriplet(int[] nums) {
        if (nums==null || nums.length<=2)   return false;
        int min_one = Integer.MAX_VALUE;
        int min_two = Integer.MAX_VALUE;

        for (int val : nums){
            if (val>min_two)
                return true;
            else if (val>min_one)
                min_two = val;
            else if (val<min_one)
                min_one = val;
        }

        return false;
    }
}
