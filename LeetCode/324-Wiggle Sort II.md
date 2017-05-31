# 324-Wiggle Sort II

## 题意
iven an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]...

## 解题

因为这里没有 等于 ， 所以不能像 wiggle sort I 一样， 进行局部的调整来实现整体的要求

所以我们把nums 按大小分成两部分， 

s s s s m m m L L L 

小的一部分 放在偶数index上， 大的一部分， 放在奇数index上。

问题是，该怎么安排呢？

我最开始的想法是 index从左至右， 小的一部分和大的一部分也从左至右的排放。 但是如果遇到 median 超过1个的时候， 就有可能出现问题。  

比如原array： (4556) s (0) m (1) m (2) L (3)， 这样排放之后 会出现 ：  s (0) m(2) m(1) L(3)。 明显不符合要求

所以我们就从 index **从右至左** 的排放。

比如上面的例子， 答案为：  m（1） L（3） s（0） m（2）

正好 m 被隔开了。

## 代码
```
public class Solution {
    public void wiggleSort(int[] nums) {
        int[] copy = new int[nums.length];
        for (int i=0; i<nums.length; i++){
            copy[i] = nums[i];
        }
        
        Arrays.sort(copy);
        
        int idx = 0;
        int start = 0;
        
        if (nums.length%2==0)
            start = nums.length-2;
        else
            start = nums.length-1;
        
        while (start>=0){
            nums[start] = copy[idx++];
            start -= 2;
        }
        
        if (nums.length%2==0)
            start = nums.length-1;
        else
            start = nums.length-2;
        
        while (start>=0){
            nums[start] = copy[idx++];
            start -= 2;
        }
        
    }
}
``` 

