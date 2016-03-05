/** Problem Statements     https://leetcode.com/problems/search-insert-position/
 *  Given a sorted array and a target value, return the index if the target is found. If not, r
    eturn the index where it would be if it were inserted in order.

    You may assume no duplicates in the array.

    Here are few examples.
    [1,3,5,6], 5 → 2
    [1,3,5,6], 2 → 1
    [1,3,5,6], 7 → 4
    [1,3,5,6], 0 → 0
 */

/** Solution:  Binary search: LEFT always is the insert position
 */


 public class Solution {
     public int searchInsert(int[] nums, int target) {
         if (nums.length==0)
             return 0;
         int l=0, r=nums.length-1;

         while (l<=r){
             int m = l + (r-l)/2;
             if (nums[m]<target){
                 l++;
             }else if (nums[m]>target){
                 r--;
             }else{
                 return m;
             }
         }
         return l;
     }
 }
