# 307-Range Sum Query - Mutable
## 题意
给一个array的range， 求出在这个range中的数字的和

## 解题， 线段树。
线段树的步骤：

1. 我们需要将Array放在tree的最底层， 所以首先确定最底层一共有多少个Node， 使用2的完全平方。然后将tree的之前的level也算上， 就是一个tree array了。
2. 然后将Array中的各个数， 放入最底层的各个相对应的Index中。在放入的工程中， 还需要向上更新。
3. 如果更新一个值， 只需要更新 Delta。
4. 如果求一个range sum， 分清楚每一个node所管辖的范围和最终的range的关系。 如果有交叉， 那么往 node.left and node.right 迭代。 node.left 的范围 是 node 的左范围和有范围的左半边。 所以是 left ---> (left+right)/2

注意的细节：
* 在更新的时候， 不能使用 while (idx>0)， 因为 idx有可能就是 0， 所以要先更新， 再判断是不是0

## 代码
```
public class NumArray {
    int[] tree;
    int n;
    int startIdx;
    
    public NumArray(int[] nums) {
        n = 1;
        while (n<nums.length){                      // 得到最底层的宽度， 然后可以得到前面的Node个数， 和起始idx
            n *= 2;
        }
        startIdx = n-1;
        tree = new int[2*n-1];
        for (int i=0; i<nums.length; i++){
            add(startIdx+i, nums[i]);
        }
    }
    
    void add(int idx, int val){
        while (true){           // 这里不能用while (idx>0)， 因为 Idx 起始的时候有可能为0的，比如说原array只有一个元素。
            tree[idx] += val;               
            if (idx==0) break;
            idx = (idx-1)/2;
        }
    }
    
    
    void update(int i, int val) {
        int oldValue = tree[startIdx+i];
        add(startIdx+i, val-oldValue);
    }

    public int sumRange(int i, int j) {
        return helper(0, 0, n-1, i, j);
    }
    
    int helper(int idx, int left, int right, int lb, int rb){
        if (left>rb || right<lb)    return 0;
        if (left>=lb && right<=rb)  return tree[idx];
        return helper(idx*2+1, left, (left+right)/2, lb, rb) + helper(idx*2+2, (left+right)/2+1, right, lb, rb);
    }
}
```

