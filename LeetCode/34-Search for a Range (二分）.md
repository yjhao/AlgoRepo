#34-Search for a Range (二分）

## 题意
给一个数， 求它在一个排好序的数列中的起始位置， 如果没有找到的话， 返回-1。

## 解题
因为是找开始和终止的位置， 所以至少要两次二分搜索

在一次中， 我们将边界往右推， 在第二次中， 将边界往左左退。

怎么才能做代将边界往右推呢？

很显然。

1. 如果 val[m] < target, --> l = m+1

2. 如果 val[m] > target, --> r = m-1;

3. 那么如何处理 val[m] == target，使边界往右推？ 把边界往右推， 实际上使我们还想看看右边还有没有相同的值， 所以这个时候 l = m+1, 可以保证我们会继续搜索 m 右边的数， 看它的值是不是也是 m。

相似的， 如果要将边界往左推， 那么 r=m-1 ， 当 val[m]==target 的时候。

while loop 设计成 l<=r， 所以当完成搜索之后，必定会交错。 如果是往右推， 最后L一定会被推出“合理”的区间， 那么r就是我们需要的右边界。

相似的， 如果是向左推， 最后R一定会被推出“合理”的区间， 留下l是我们的答案


如果这个值没有找到， 两个二分搜索的结果应该会一样

##代码
```
public class Solution {
    public int[] searchRange(int[] nums, int target) {
        int l1=0, l2=0;
        int r1=nums.length-1, r2=nums.length-1;
        int res[] = {-1,-1};
        
        while (l1<=r1){
            int mid = (l1+r1)/2;
            if (nums[mid]<=target){
                l1 = mid+1;
            }else{
                r1 = mid-1;
            }
        }
        
        while (l2<=r2){
            int mid = (l2+r2)/2;
            if (nums[mid]>=target){
                r2 = mid-1;
            }else{
                l2 = mid+1;
            }
        }
        
        if (l2<=r1){
            res[0] = l2;
            res[1] = r1;
        }
        
        return res;
    }
}
```   