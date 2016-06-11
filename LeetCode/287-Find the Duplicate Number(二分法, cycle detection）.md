#287-Find the Duplicate Number(二分法, cycle detection）
##题意
一个长度为 n+1 的数列里面， 有N个不同的数字， from 1 to N。 求出这个重复的数字。

##解题， 方法一
可以采用二分法 和 抽屉原理来解题。

先找到 Mid, 然后遍历整个数列， 如果小于等于mid的数字个数是大于mid的， 说明肯定有重复， 那么我们就缩小Mid。 反之， 重复的数字肯定是在 mid 的另一侧， 我们就增大 Mid。 

复杂度 O(nlogn)

##代码
```
public class Solution {
    public int findDuplicate(int[] nums) {
        if (nums.length==2) return nums[0];
        int l = 1;
        int r = nums.length-1;
        
        while (l<r){
            int m = l+(r-l)/2;
            int count = 0;
            for (int num : nums){
                if (num<=m) count++;
            }
            if (count>m)
                r = m;
            else
                l = m+1;
        }
        
        return l;
    }
}
```

##解题， 方法二
类似于 find the start point of a linekdlist cycle。 Floyd cycle detection。

可以把所有数字想象成一条线上的数字， 然后赋予”F function", next = f[next]。 所以我们先用 slow , fast 找到一个相交点a，那么在这之前， 一定是出现过重复的数字了， 不然一定会是无限长的。

然后重置 slow， 再次进行 slow fast 的搜索。 如果再次相遇的时候， 相遇点就是重复的地点。

http://keithschwarz.com/interesting/code/?dir=find-duplicate

###细节
一：

search 的出发点一定要是在“直线上”， 一定不能实在“圈内”。 在这个例子中， 因为数字的大小是1-n， 所以肯定不会回到 坐标 为0的地方， 所以可以从0开始。 

类似于 slow = root.next, fast = root.next.next

在这里： slow = nums[0], fast = nums[nums[0]]


二：

找到重合的点， 进行第二次搜索的时候， 保持fast不变，而重置slow。 在这里， slow 要被重置在 "赋予F function“ 之前的本来的点。

类似于 slow = root

在这里： slow = 0；

##代码
```
public class Solution {
    public int findDuplicate(int[] nums) {
        if (nums==null || nums.length<=2)   return 1;
        int left = nums[0];
        int right = nums[nums[0]];
        
        while (left!=right){
            left = nums[left];
            right = nums[nums[right]];
        }
        
        right = 0;
        
        while (left!=right){
            left = nums[left];
            right = nums[right];
        }
        
        return left;
    }
}
```

 