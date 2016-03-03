/*
Given a sorted positive integer array nums and an integer n, add/patch elements to the array such that any number in range [1, n] inclusive can be formed by the sum of some elements in the array. Return the minimum number of patches required.

Example 1:
nums = [1, 3], n = 6
Return 1.

Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4.
Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3].
Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6].
So we only need 1 patch.

Example 2:
nums = [1, 5, 10], n = 20
Return 2.
The two patches can be [2, 4].

Example 3:
nums = [1, 2, 2], n = 5
Return 0.
*/

/* thinking process.
Induction : for example, [1,2,3,8,14]. target = 35.

The first number to get (first_miss) is 1, we have it here, first_miss is 1+1 = 2;
then from 1, first_miss is 2, we have it here. From 1,2, first_miss is 2+2=4. However, we still
have 3 here, 3 is smaller than first_miss(4), thus, from 1,2,3, we can get 1-6, the first_miss is 7

Now. In the list, next number is 8, there is no way we could get 7 w/o patching. Thus here we patch
a new number, but what to patch?

If a number n is patched, from induction, the range [1-first_miss+n) would be covered. Thus we patch
the maximum possible number, but also covers the range we missed (here 7). So we patch 7.

first_miss becomes to be 7 + 7 = 14.

the next number in the list is 8, which is smaller than first_miss, first_miss becomes to be 14+8=22.
Then the next is 14, which is covered by current first_miss. first_miss becomes to be 22+14=36.

first_miss > target, we successfully cover the range [1, first_miss).
*/

public class Solution {
    public int minPatches(int[] nums, int n) {
        long first_miss = 1;
        int cnt = 0;
        int i=0;
        while (first_miss<=n){
            if (i<nums.length && nums[i]<=first_miss){
                first_miss += nums[i];
                i++;
            }else{
                cnt++;
                first_miss += first_miss;
            }
        }
        return cnt;
    }
}
