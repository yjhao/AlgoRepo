#47 Permutations II
返回所有的组合， 注意有重复！

## 题解
采用DFS。 在同一层中， 相同的数字， 约定俗成我们只用待选的第一个。

所以禁止用**除第一个**以外的其他所有相同数字。

那么这些数字有什么特征呢？

首先 nums[i] = nums[i-1].

其次， 最重要的一点， 如果在**同一层中**跳开第一个数字不用， 而去用第二个数字， 那么第一个数字就是***unvisited***， 这是绝对不允许的。

那么我们可以用
```
if (!visited[i-1] && nums[i]==nums[i-1])
```
来判断。

## 代码
```
public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (nums==null || nums.length==0)   return res;

        Arrays.sort(nums);
        List<Integer> list = new ArrayList<Integer>();
        boolean[] visited = new boolean[nums.length];

        helper(nums, visited, list, res, 0);
        return res;
    }

    public void helper(int[] nums, boolean[] visited, List<Integer> list, List<List<Integer>> res, int count){
        if (count == nums.length){
            res.add(new ArrayList<Integer>(list));
            return;
        }

        for (int i=0; i<nums.length; i++){
            if (visited[i]) continue;

            if (i>0 && !visited[i-1] && nums[i]==nums[i-1]) continue;

            if (!visited[i]){
                list.add(nums[i]);
                visited[i] = true;

                helper(nums, visited, list, res, count+1);

                visited[i] = false;
                list.remove(list.size()-1);
            }
        }
    }
```
