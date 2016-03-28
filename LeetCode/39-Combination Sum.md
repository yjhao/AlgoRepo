# 39-Combination Sum

# 题意
给一些数， 都是正数， 有重复的， 找到加起来为一目标值的非重复组合。

# 解题
题目很简单， DFS, 注意判断重复与否

# 优化
数字都是正数， 说明如果当前的总和大于target， 后面的就不需要再看了， 直接return。 DFS pruning

# 代码
```
public class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (candidates==null || candidates.length==0)   return res;
        Arrays.sort(candidates);
        ArrayList<Integer> list = new ArrayList<>();
        DFS(candidates, res, 0, list, target);
        return res;
    }
    
    public void DFS(int[] candidates, List<List<Integer>> res, int start, ArrayList<Integer> list, int target){
        if (target==0){
            res.add(new ArrayList<Integer>(list));
            return;
        }else if (target<0){
            return;
        }
        
        int len = candidates.length;
        
        for (int i=start; i<len; i++){
            if (i!=start && candidates[i] == candidates[i-1])    continue;
            
            if (target-candidates[i]<0) return;
            
            list.add(candidates[i]);
            DFS(candidates, res, i, list, target-candidates[i]);
            list.remove(list.size()-1);
        }
    }
}
```