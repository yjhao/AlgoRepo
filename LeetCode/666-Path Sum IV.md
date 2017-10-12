# 666-Path Sum IV

## 题意
给一个数组， 里面每一个数代表一颗二叉树的Node， 百位数是Level， 十位数是这个node在他那一层的位置， 从1开始； 个位数是这个node的value。 求这棵树的 sum of all paths。

## 解题
我最开始的想法是， 每一个node在树中的位置都能用idx来表示，初始情况是root为1， 左右child分别为 idx*2, idx*2+1。 然后对于每一个node， 我们知道他的左右孩子的idx， 那么减去那一层的起始idx， 就能得到左右孩子在那一层的位置， 也就是十位数。

我们traverse 这个数组， 找到有没有这样的左右孩子。

## 代码
```
class Solution {
    int[] idxSpace = {1,3,7,15};
    public int pathSum(int[] nums) {
        if (nums.length==0) return 0;
        int[] res = {0};
        int cur = 0;
        cur = getOnes(nums[0]);
        
        helper(nums, 1, 0, res, cur, 0);
        return res[0];
    }
    
    void helper(int[] nums, int idx, int level, int[] res, int cur, int lastIdx){
        boolean hasChild = false;
        
        int leftChildIdx = idx*2 - idxSpace[level];
        int rightChildIdx = leftChildIdx + 1;
        
        for (int i=lastIdx+1; i<nums.length; i++){
            int num = nums[i];
            if (getHundreds(num) > level+2) break;
            if (getHundreds(num) == level+2 && getTens(num) == leftChildIdx){
                hasChild = true;
                helper(nums, idx*2, level+1, res, cur+getOnes(num), i);
            }else if(getHundreds(num) == level+2 && getTens(num) == rightChildIdx){
                hasChild = true;
                helper(nums, idx*2+1, level+1, res, cur+getOnes(num), i);
            }
        }
        
        if (!hasChild)  res[0] += cur;
    }
    
    int getHundreds(int num){
        String str = String.valueOf(num);
        return Integer.valueOf(str.substring(0,1));
    }
    
    int getTens(int num){
        String str = String.valueOf(num);
        return Integer.valueOf(str.substring(1,2));
    }
    
    int getOnes(int num){
        String str = String.valueOf(num);
        return Integer.valueOf(str.substring(2));
    }
}
```

## 改进
我们不需要用总的idx来确定Node的位置。 而可以用每一层的相对idx来表示， 也就是十位数所表示的东西。 如果每一层的Node的idx都从1开始， 然后左右孩子分别为 idx*2-1, idx*2， 那么下一层的idx也完全确定下来了， 而且是从1开始的， 并且对应于我们需要找到关系。

而且， 我们其实就是需要找某个特定层数的某个特定位置的Node， 我们可以把 num/10 放入 一个map中来快速寻找， 就不用再遍历数组了。

## 代码
```
class Solution {
    public int pathSum(int[] nums) {
        if (nums.length==0) return 0;
        int[] res = {0};
        
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int num : nums){
            map.put(num/10, num%10);
        }
        int cur = 0;
        cur = nums[0]%10;
        
        helper(map, 1, 0, res, cur);
        return res[0];
    }
    
    void helper(HashMap<Integer, Integer> map, int idx, int level, int[] res, int cur){
        boolean hasChild = false;
        
        int leftChildIdx = idx*2 - 1;
        int rightChildIdx = leftChildIdx + 1;
        
        int leftKey = (level+2)*10 + leftChildIdx;
        if (map.containsKey(leftKey)){
            hasChild = true;
            helper(map, leftChildIdx, level+1, res, cur+map.get(leftKey));
        }
        
        int rightKey = (level+2)*10 + rightChildIdx;
        if (map.containsKey(rightKey)){
            hasChild = true;
            helper(map, rightChildIdx, level+1, res, cur+map.get(rightKey));
        }
        
        if (!hasChild)  res[0] += cur;
    }
}
```