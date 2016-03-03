/*Given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3]....

For example, given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].
*/

public class Solution {
    public void wiggleSort(int[] nums){
        if (nums==null || nums.length<=1)   return;

        // just compare i and i-1
        // if i is odd, then nums[i] must >= nums[i-1];
        // if i is even, then nums[i] must <= nums[i-1];
        // after swapping, it won't affect the relationship between nums[i-1] and nums[i-2]

        for (int i=1; i<nums.length; i++){
            if (i%2==1 && nums[i]<nums[i-1] || i%2==0 && nums[i]>nums[i-1]){
                int temp = nums[i];
                nums[i] = nums[i-1];
                nums[i-1] = temp;
            }
        }
        return;
    }

    /* quite slow
    public void wiggleSort(int[] nums) {
        if (nums==null || nums.length<=1)   return;
        int i=0;
        int count = 0;
        while (i<nums.length)
        {
            if (count%2==0)
            {
                int idx = minValIdx(nums, i, nums.length-1);
                swap(nums, i, idx);
                i++;
                count++;
            }
            else
            {
                int idx = maxValIdx(nums, i, nums.length-1);
                swap(nums, i, idx);
                i++;
                count++;
            }
        }

        return;
    }

    private int maxValIdx(int[] nums, int left, int right) {
        if (left==right)    return left;
        int max = nums[left];
        int res = left;
        for (int i=left; i<=right; i++)
        {
            if (nums[i]>max)
            {
                max = nums[i];
                res = i;
            }
        }

        return res;
    }

    private int minValIdx(int[] nums, int left, int right) {
        if (left==right)    return left;
        int min = nums[left];
        int res = left;
        for (int i=left; i<=right; i++)
        {
            if (nums[i]<min)
            {
                min = nums[i];
                res = i;
            }
        }

        return res;
    }

    private void swap(int[] nums, int left, int right) {
        if (left>=right)    return;
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
        return;
    }
    */
}
