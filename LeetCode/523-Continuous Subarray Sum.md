# 523-Continuous Subarray Sum
## 题意
给一个数组和一个整数k， 问在数组之中， 有没有连续的， 长度大于2的子数组， 他们的和为 k 的 整数倍。

## 解题
如果问， 有没有子数组， 他们的和为一个固定的数， 那么很容易的， 可以先确定Prefix sum 和这个固定的数的差值， 然后用map来找。

但是这里， 问的是一个K的整数倍， 那么如果存在这样的情况的话， 有什么特征呢？假设在那个位置的prefix sum 为 p, 

假设当前的prefix sum 为 m， 那么 m%k 一定是等于 p%k 的， 因为余数相同， 中间的和一定为 K 的倍数。

注意K=0的情况， 如果K=0的话， 就不能算余数了； 如果K!=0， 我们每次直接将sum更新为sum%k。

## 代码
```
public class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, -1);
        int sum = 0;
        
        for (int i=0; i<nums.length; i++){
            sum += nums[i];
            if (k!=0)   sum %= k;
            if (map.containsKey(sum)){
                if (map.get(sum)+1!=i)    return true;
            }else{
                map.put(sum, i);
            }
            
        }
        return false;
    }
}
```