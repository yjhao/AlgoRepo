# 79-Sort Colors
## 题意
一个数组， 有 0 1 2， 将他们排好序， 用一次遍历

## 解题
**长江后浪推前浪**

用ptr表示0，1，2的最后idx。

如果加入了0， 那么0就会往后延一个， 那么1的区间的第一个就会变为0。 为了弥补1的缺失，1的末尾就会往后延一个。

ptr改写的时候， 顺序很重要， 要把当前数字的ptr放在最后改写

## 代码
```
public class Solution {
    public void sortColors(int[] nums) {
        int zero = -1;
        int one = -1;
        int two = -1;
        
        for (int i=0; i<nums.length; i++){
            if (nums[i] == 0){
                nums[++two] = 2;
                nums[++one] = 1;
                nums[++zero] = 0;
            }else if (nums[i] == 1){
                nums[++two] = 2;
                nums[++one] = 1;
            }else if(nums[i] == 2){
                nums[++two] = 2;
            }
        }
        
        return;
    }
}
```


