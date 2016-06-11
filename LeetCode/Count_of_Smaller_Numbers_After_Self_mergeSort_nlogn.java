/**
 * this task eqauls to the task of find the inversion number
 * the natural choice of fining the inversion number is to use merge sort
 * The smaller numbers on the right of a number are exactly those that
   jump from its right to its left during a stable sort
 * The aggregation step is Line 71.
   when plus j, it means from 0 to j-1, all the numbers in the right are smaller
   than current left[i], why are we sure about it? Because they are all added to the
   merged array before left[i], which means they are smaller.


   *****  when compare left and right, USE <= but not <. 

   在右端， 比left[i] 先放入 sorted array的， 一定是比 left[i] 要小的， 所以使用 <=, 而不是 <。

   THIS will make sure the numbers on the 
   right that are equal to left[i] will be added after left[i] is added.

   OR there will be problems--> j includes the numbers whos value is equal to nums[i] when calculating the
   inversion number. Actually, they should not be counted.
 * the time complexity is O(nlogn)
 */

public class Solution {
    class numberIndex{
        int num;
        int idx;

        public numberIndex(int num, int idx){
            this.num = num;
            this.idx = idx;
        }

        public numberIndex(numberIndex other){
            this.num = other.num;
            this.idx = other.idx;
        }
    }

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new LinkedList<Integer>();
        ArrayList<Integer> sorted = new ArrayList<Integer>();
        if (nums==null || nums.length==0) return res;

        numberIndex[] arr = new numberIndex[nums.length];
        int[] smaller     = new int[nums.length];

        for (int i=0; i<nums.length; i++){
            numberIndex cur = new numberIndex(nums[i], i);
            arr[i] = cur;
        }

        mergeSort(arr, smaller);

        for (int i=0; i<nums.length; i++){
            res.add(smaller[i]);
        }

        return res;
    }

    public void mergeSort(numberIndex[] arr, int[] smaller){
        if (arr.length<=1)  return;

        int size = arr.length/2;
        numberIndex[] left = new numberIndex[size];
        numberIndex[] right = new numberIndex[arr.length-size];

        for (int i=0; i<left.length; i++)
            left[i] = new numberIndex(arr[i]);

        for (int i=0; i<right.length; i++)
            right[i] = new numberIndex(arr[i+size]);

        mergeSort(left, smaller);
        mergeSort(right, smaller);

        int i=0; int j=0;

        while (i<left.length || j<right.length){
            if (j==right.length || i<left.length && left[i].num <= right[j].num){
                arr[i+j] = left[i];
                smaller[left[i].idx] += j;
                i++;
            }else{
                arr[i+j] = right[j];
                j++;
            }
        }
        return;
    }


}
