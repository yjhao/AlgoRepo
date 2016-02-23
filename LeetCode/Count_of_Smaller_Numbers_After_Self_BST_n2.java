/**
  * use binary tree to find the inversion number
  * for each number from right to left, insert it into a sorted array,
  * use binary search to find the insertion place
  * worst case complexity o(n^2)

  * in the binary search, dont't use
  *     if (sorted.get(mid)>val)
            right = mid-1;
        else
            left = mid;
    "left=mid" could result in an endless loop
  */


public class Solution {
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new LinkedList<Integer>();

        ArrayList<Integer> sorted = new ArrayList<Integer>();

        if (nums==null || nums.length==0) return res;

        // the last element
        sorted.add(0, nums[nums.length-1]);
        res.add(0, 0);

        for (int i=nums.length-2; i>=0; i--){
            int rightSmall = insertionPlace(sorted, nums[i]);   // use binary search to obtain the insertion place
            sorted.add(rightSmall, nums[i]);   // insert current number to the sorted array
            res.add(0, rightSmall);    // rightSmall is the numebr of smaller elements from right
        }

        return res;
    }

    public int insertionPlace(ArrayList<Integer> sorted, int val){
        int size = sorted.size();
        if (val > sorted.get(size-1)) return size;
        if (val < sorted.get(0))    return 0;

        int left = 0;
        int right = size-1;

        while (left<right){
            int mid = left + (right-left)/2;
            if (sorted.get(mid)<val)
                left = mid+1;
            else
                right = mid;
        }

        return left;
    }
}
