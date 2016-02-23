/*
Given two array of length m and n, create maximum number of length k = m + n.

OK, this version is a lot closer to our original problem with the exception that we will use
all the digits we have.

Still, for this version, Greedy is the first thing come to mind. We have k decisions to make,
each time will just need to decide ans[i] is from which of the two. It seems obvious, we should
always choose the larger one right? This is correct, but the problem is what should we do if they are equal?

This is not so obvious. The correct answer is we need to see what behind the two to decide.
For example,

nums1 = [6, 7]
nums2 = [6, 0, 4]
k = 5
ans = [6, 7, 6, 0, 4]

We decide to choose the 6 from nums1 at step 1, because 7 > 0.
What if they are equal again? We continue to look the next digit until they are not equal.
If all digits are equal then choose any one is ok. The procedure is like the merge in a merge sort.
However due to the “look next until not equal”, the time complexity is O(nm).
*/

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
