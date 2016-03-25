#4-Median of two sorted arrays.

# 题意
找出两个排好序的数列中的中值。

总结为找出两个排好序的数列中， 第K大的数。

# 解题
每次比较两个数列的两个点的值， 然后根据这两个值得大小关系， 截断一个或者另外一个的“前半段”, 然后递归。

关键问题是， 比较哪两个点呢？

1. 用（k/2)来截断， k是排序第K大的数， 不一定是中位数。
2. 用 (len/2）来截断

正确的选择应该是用(k/2）来截断。 因为用len/2来截断的话， 如果这两个点的值是一摸一样的， 那么谁是排序第k的值呢？ 反之， 如果用（k/2)来截断， 如果这两个值一样， 马上就知道排序第k的值一定就是这个值自己。

还需要注意的是， 因为是截断出一个Subsequence, 然后递归到下一层。换句话说， 长度总是减小的。 如果下一层的subsequence长度不能减小， 所以就需要人工介入来判断答案。 什么时候长度不会再减小了呢？ k/2=0的时候， 所以K=1的时候需要人工介入。

##代码
```
public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        if ((len1+len2)%2==1){
            return (double) helper(nums1, nums2, 0, len1-1, 0, len2-1, (len1+len2)/2+1);
        }else{
            return ( (double)helper(nums1, nums2, 0, len1-1, 0, len2-1, (len1+len2)/2+1) + (double)helper(nums1, nums2, 0, len1-1, 0, len2-1, (len1+len2)/2))/2;
        }
    }
    
    private int helper(int[] nums1, int[] nums2, int s_num1, int e_num1, int s_num2, int e_num2, int k){
        int len1 = e_num1-s_num1+1;
        int len2 = e_num2-s_num2+1;
        
        if (len1>len2) 
        {
            return helper(nums2, nums1, s_num2, e_num2, s_num1, e_num1, k);
        }
        if (len1==0) 
        {
            return nums2[s_num2+k-1];
        }
        if (k==1) 
        {
            return Math.min(nums1[s_num1], nums2[s_num2]);
        }
        
        int partA = Math.min(k/2, len1);
        int partB = k-partA;
        
        if (nums1[s_num1+partA-1] < nums2[s_num2+partB-1])
        {
            return helper(nums1, nums2, s_num1+partA, e_num1, s_num2, e_num2, k-partA);
        }
        else if (nums1[s_num1+partA-1] > nums2[s_num2+partB-1])
        {
            return helper(nums1, nums2, s_num1, e_num1, s_num2+partB, e_num2, k-partB);
        }
        else
        {
            return nums1[s_num1+partA-1];
        }
    }
}
```