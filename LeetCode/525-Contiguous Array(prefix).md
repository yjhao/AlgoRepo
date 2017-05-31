#525-Contiguous Array(prefix)
##题意
给一个0 1 的数组， 找到其中最长的连续子数组， 其中的0 1 的总数分别是一样的

##解题
肯定得想办法使用 prefix sum来解答这个问题， 但是得使用什么value来作为 prefix sum呢？

假设现在0 1 的个数分别为 7 4， 那么如果在这个时候， 有一个Subarray，满足 0 1 的个数相同， 那么在这个subarray的起点， 0 1 的个数可能为 ： 3 0， 4 1， 5 2， 6 3。 

如果一个一个组合的在map中去找， 就非常的麻烦。

但是我们可以观察到， 他们的差值是固定的， 所以我们可以在 map 中的 prefix中存入当前 0 1 的个数差值， 如果存在相同的差值， 那么就说明之间的0 1 个数一定是相同的。

需要注意的是， 初始状态， 需要在map中放入0

##代码
```
public class Solution {
    public int findMaxLength(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        int sum = 0;
        int max =0;
        map.put(0, -1);
        
        for (int i=0; i<nums.length; i++){
            if (nums[i] == 0)   sum -= 1;
            else sum += 1;
            
            if (map.containsKey(sum)){
                max = Math.max(max, i - map.get(sum));
            }else{
                map.put(sum, i);
            }
        }
        
        return max;
    }
}
``` 