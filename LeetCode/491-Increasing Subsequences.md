# 491-Increasing Subsequences
## 题意
给一个Array， 找出这个里面的所有 Subarray whose length >=2, 这些 Subarray必须是不递减的， 而且必须是Unique的。

## 解题， 方法一 （不是特别好）
首先这肯定是一个 DFS 问题， 在某一个位置的时候， 处理下一个数，如果这个数字大于等于当前的末尾， 那么我们可以选择加， 也可以选择不加； 如果下一个数不是大于等于当前末尾， 那么就一定不能够加。

当这个subarray长度大于等于2的时候， 我们就可以把它作为答案的候选人之一。

那么如果判断Unique呢？ 我们可以把它转换为 String， 也可以用另外一个办法， **就是 在 set 里面直接装 arraylist**。然后最后再使用：
```
list = new ArrayList(set)
```
将其变为list。

**这样的方法， dfs需要走遍每一个数字， 而且是一个一个走。**

## 代码
```
public class Solution {
    public List<List<Integer>> findSubsequences(int[] nums) {
        Set<List<Integer>> set= new HashSet<List<Integer>>();
        if (nums.length<=2){
            return new ArrayList<List<Integer>>();
        }
        
        List<Integer> cur1 = new ArrayList<Integer>();

        List<Integer> cur2 = new ArrayList<Integer>();
        cur2.add(nums[0]);
        
        helper(cur1, 1, nums, -101,   set);
        helper(cur2, 1, nums, nums[0],set);
        
        List result = new ArrayList(set);
        
        return result;
    }
    
    void helper(List<Integer> list, int left, int[] nums, int last, Set<List<Integer>> set){
        if (list.size()>=2) {
            set.add(new ArrayList<>(list));
        }
        
        if (left>=nums.length)  return;
        
        if (list.size()==0){
            helper(list, left+1, nums, last, set);
            list.add(nums[left]);
            helper(list, left+1, nums, nums[left], set);
            list.remove(list.size()-1);
        }else{
            helper(list, left+1, nums, last, set);
            
            if (nums[left] >= last){
                list.add(nums[left]);
                helper(list, left+1, nums, nums[left], set);
                list.remove(list.size()-1);
            }
        }
    }
}
```

## 解题， 方法二， 更好的方法。
在前一个方法中， 在Dfs中，我们一一的遍历数字。 那么我们也可以采取另外一个方法： 我们在剩余的所有数字中， 选择一个合适的数字来加入。

这样的方法的话， 就不会一一的遍历各个数字了。

接下来， 我们如何去重复呢?

假设当前的末尾为 2,然后现在我们需要找下一层的数字， 显而易见的是， 下一层的数字， 一定不能重复，比如说 2 3， 然后等一会又遇到 3， 那么我们是不能加入这个3 并开始新一轮的 dfs的， 因为2 3 在之前就已经开始了一轮 dfs了。如果现在加入这个3, 那么一定会发生重复的。 

所以在每一层， 我们建立一个 hashset， 这样的话， 就可以去重。

**使用这样的方法， 如果是重复的结果， 我们压根就没有进入过访问过， 而不像前一个方法， “访问过， 但只是没有加入到答案之中”。** 所以本方法速度更快。

## 代码
```
public class Solution {
    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> res= new ArrayList<List<Integer>>();
             
        List<Integer> list = new ArrayList<Integer>();

        helper(0, nums, res, list, -101);
        return res;
    }

    void helper(int idx, int[] nums, List<List<Integer>> res, List<Integer> list, int last){
        //if (idx >= nums.length) return;
        if (list.size()>=2){
            res.add(new ArrayList<Integer>(list));
        }

        HashSet<Integer> levelUnique = new HashSet<Integer>();

        for (int i=idx; i<nums.length; i++){
            if (levelUnique.contains(nums[i]))  continue;
            if (nums[i] >= last){
                list.add(nums[i]);
                levelUnique.add(nums[i]);
                helper(i+1, nums, res, list, nums[i]);
                list.remove(list.size()-1);
            }    
        }
    }
}
```

