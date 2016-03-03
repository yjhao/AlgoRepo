/**
Given one array of length n, create the maximum number of length k.

The solution to this problem is Greedy with the help of stack. The recipe is as following

1). Initialize a empty stack
2). Loop through the array nums
        pop the top of stack if it is smaller than nums[i] until
        a). stack is empty
        b). the digits left is not enough to fill the stack to size k
    if stack size < k push nums[i]
3). Return stack

Since the stack length is known to be k, it is very easy to use an array to simulate the stack.
The time complexity is O(n) since each element is at most been pushed and popped once.
*/

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
