/* Problem Statement
Given two arrays of length m and n with digits 0-9 representing two numbers.
Create the maximum number of length k <= m + n from digits of the two.
The relative order of the digits from the same array must be preserved.
Return an array of the k digits. You should try to optimize your time and space complexity.
*/

/** Solution
 * Let's take a few steps back.
   1). First, take k numbers from a single array to form the maximum numbers,
       refer to the "generate_maxArray_fromOneArray"
   2). Second, take (n+m) numbers from two array to form the maximum numbers,
       whose length are n and m respectively. Refer to "generate_maxNum_fromTwoArray_full"
   3). Third, we divide the k digits required into two parts, i and k-i. We then find the
       maximum number of length i in one array and the maximum number of length k-i in the
       other array using the algorithm in section 1. Now we combine the two results in to
       one array using the algorithm in section 2. After that we compare the result with
       the result we have and keep the larger one as final answer.

 * Time complexity O(mn), which is from section 2.
 It is much better than DFS, which is 2^(n+m).

 */

public int[] maxNumber(int[] nums1, int[] nums2, int k) {
    int n = nums1.length;
    int m = nums2.length;
    int[] ans = new int[k];
    for (int i = Math.max(0, k - m); i <= k && i <= n; ++i) {
        int[] candidate = merge(maxArray(nums1, i), maxArray(nums2, k - i), k);
        if (greater(candidate, 0, ans, 0)) ans = candidate;
    }
    return ans;
}

// section 1
public int[] maxArray(int[] nums, int k){
    if (k==0) 	return new int[0];
    int len = nums.length;
    if (len==k)	return nums;
    int[] ans = new int[k];

    int j=0;
    for (int i=0; i<nums.length; i++){
        // len-i: how many more candidates there are, including i;
        // k-i: how many more slots are, including j;
        // thus len-i should at least = k-i, or there won't be enough to make an length k array
        while (len-i>=k-j && j>=0 && ans[j]<nums[i])
            j--;
        // to check, in case the while loop does not decrease j, (nums[i] < ans[k-1])
        if (j<ans.length-1)
            ans[++j] = nums[i];
    }

    return ans;
}

// section 2
private int[] merge(int[] nums1, int[] nums2, int k) {
    int[] ans = new int[k];
    for (int i = 0, j = 0, r = 0; r < k; ++r)
        ans[r] = greater(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
    return ans;
}
public boolean greater(int[] nums1, int i, int[] nums2, int j) {
    while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
        i++;
        j++;
    }
    return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
}
