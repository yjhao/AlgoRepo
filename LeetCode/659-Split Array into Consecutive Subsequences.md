# 659-Split Array into Consecutive Subsequences

## 题意
给以个递增的数组， 问能不能将这些数组split 成很多个小的subsequence， 每一个subsequence里面都是连续递增的数字， 而且长度大于等于3.

## 解题， 方法一, O(n) space
这个题目就是打扑克牌题目， 问能不能连若干个顺子。

对于任何一张牌， 要满足条件形成一个顺子中的一张牌，他有两个可能性。 

1. 首先， 他可能是一个新的顺子的开始， 要满足他是一个新的顺子， 最起码还有比他大一和大二的数。 所以我们就得提前知道， 牌的分布， 可以用一个map来记录。

2. 或者他可能是接在前面的已有的一个顺子上。那么这个数字怎么知道他可以接在前面的一个已有的顺子上呢？ 
	3. 	我们组合成一个新的顺子之后， 假设末尾的数字是i， 我们就需要保持一个i+1的变量， 当我们遍历到 i+1的时候, i+1就知道这个时候他可以接在前面的一个已有的顺子上。当街上i+1之后， 修改这个变量， 并修改i+2的变量， 因为这个时候 i+2 也可以接在这个顺子上了。
	4. 这个变量不能是Boolean， 而应该是一个 Counter， 因为有可能前面有若干个顺子都可以用到i+1。


如果对于一张牌，如果这两个条件都满足不了， 说明对于他一定是形成不了顺子的。 所以直接返回 false。

## 代码
```
class Solution {
    public boolean isPossible(int[] nums) {
        HashMap<Integer, Integer> freq = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> need = new HashMap<Integer, Integer>();
        for (int num : nums)    freq.put(num, freq.getOrDefault(num, 0)+1);
        
        for (int num : nums){
            if (freq.get(num)==0)   continue;
            if (need.getOrDefault(num, 0) > 0){
                need.put(num, need.get(num)-1);
                need.put(num+1, need.getOrDefault(num+1, 0)+1);
            }else if (freq.getOrDefault(num+1,0) > 0 && freq.getOrDefault(num+2, 0) > 0){
                freq.put(num+1, freq.get(num+1)-1);
                freq.put(num+2, freq.get(num+2)-1);
                need.put(num+3, need.getOrDefault(num+3, 0) + 1);
            }else{
                return false;
            }
            freq.put(num, freq.get(num)-1);
        }
        
        return true;
    }
}
```

## 解题二， O(1) space
之前的方法需要一个map来维护每个数字各有多少个。 在这里我们不用Map， 利用这个数组是有序的。

我们需要维护当前长度为1的顺子的个数， 长度为2的顺子的个数， 和长度大于等于三的顺子的个数。然后在每一个不同数字的地方作出更新。

对于一个相同的数， 首先求得他的个数C

如果这个数之前没有比他小1的数， 那么他一定是一个全新的顺子， 前面的所有顺子都结束了。 如果这个时候发现 长度为2， 长度为1的顺子的个数不等于0， 那么直接返回false。 更新长度为1的顺子的个数为c， 长度为2 长度为3 的顺子的个数为0。

如果他前面的数比他正好小一， 那么就可以接在前面的顺子上。

如果发现C < pre1 + pre2， 那么说明即使全部都接在前面的顺子上， 还是会有顺子的长度达不到3， 那么直接返回false。

greedy的考虑， 我们先把这个数接在长度为1的顺子上， 然后是长度为2， 最后才是长度大于等于三的顺子。如果还有剩下的， 才会形成从自己开始的一个新的顺子。

当接在长度为一的顺子上的时候， 当前的长度为2的顺子的个数也就确定了。temp2 = pre1;

为了确定当前长度大于等于3的顺子的个数， 它来自两个部分， 第一个部分， 我们首先是将其接在之前长度为2的顺子的后面。 第二个部分，然后在把如果还有剩下的个数， 都接在之前长度大于等于3的顺子的后面。temp3 = pre2 + Math.min(pre3, cnt-pre1-pre2)。

如果这个时候， 我们都接在了之前的顺子上， 发现还有剩下的， 那么剩下的就是 当前长度为1的顺子的个数。temp1 = Math.max(0, cnt-temp2-temp3);

## 代码
```
class Solution {
    public boolean isPossible(int[] nums) {
        int pre1 = 0;
        int pre2 = 0;
        int pre3 = 0;
        
        int idx = 0;
        while (idx<nums.length){
            int temp1 = 0;
            int temp2 = 0;
            int temp3 = 0;
            int cnt = 1;
            
            int firstIdx = idx;
            
            idx++;
            while (idx<nums.length && nums[idx] == nums[idx-1]){
                idx++;
                cnt++;
            }
            
            if (firstIdx==0 || nums[firstIdx-1] + 1 != nums[firstIdx]){
                if (pre1!=0 || pre2!=0) return false;
                pre1 = cnt;
                pre2 = 0;
                pre3 = 0;
            }else{
                if (cnt<pre1+pre2)  return false;
                temp2 = pre1;
                temp3 = pre2 + Math.min(pre3, cnt-pre1-pre2);
                temp1 = Math.max(0, cnt-temp2-temp3);  
            
                pre1 = temp1;
                pre2 = temp2;
                pre3 = temp3;
            }
            
        }
        
        return pre1==0 && pre2==0;
    }
}
```