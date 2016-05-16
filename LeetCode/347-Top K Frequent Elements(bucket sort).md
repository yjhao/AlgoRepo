#347-Top K Frequent Elements(bucket sort)
##题意
给一串数列， 找出频率最高的K个数， 时间复杂度一定要小于 nlogn

##解题
因为必须要小于 nlogn， 而且还必须对频率排序， 那么非常自然的就想到用 bucket sort。

那么bucket sort的上下边界是什么呢？

bucket sort 对频率进行排序， 下界是1， 上界是 nums.length （整个数列都为同一个数）

然后对频率最高的数进行计数， 问题就迎刃而解了。

首先用Hashmap记录每个数字的频率。

##代码
```
public class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        for (int i=0; i<nums.length; i++){
            ArrayList<Integer> cur = new ArrayList<>();
            list.add(cur);
        }
        
        for (int num : nums){
            if (!map.containsKey(num)){
                map.put(num, 0);
            }
            map.put(num, map.get(num)+1);
        }
        
        for (Integer key : map.keySet()){
            int freq = map.get(key);
            list.get(freq-1).add(key);
        }
        
        List<Integer> res = new ArrayList<Integer>();
        for (int i=nums.length-1; i>=0; i--){
            res.addAll(list.get(i));
            k -= list.get(i).size();
            if (k<=0)   break;
        }
        
        return res;
    }
}
```