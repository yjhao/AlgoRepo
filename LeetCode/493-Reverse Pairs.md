# 493-Reverse Pairs
## 题意
给一个数组， 问一共有多少个这样的数对， 排在前面的数m比排在后面的数n要大， 而且 m>2*n

## 分割重现关系(Partition Recurrence Relation)，用式子表示是T(i, j) = T(i, m) + T(m+1, j) + C
在这种情况下， 就是 divide and conquer， 所以我们采用merge sort的方法来计算颠倒的pair。

## 代码
```
class Solution {
    public int reversePairs(int[] nums) {
        int[] res = {0};
        helper (res, nums);
        return res[0];
    }
    
    void helper(int[] res, int[] nums){
        if (nums.length<=1) return;
        int[] left = new int[nums.length/2];
        int[] right = new int[nums.length-left.length];
        for (int i=0; i<left.length; i++)   left[i] = nums[i];
        for (int i=0; i<right.length; i++)  right[i] = nums[left.length+i];
        
        helper(res, left);
        helper(res, right);
        
        int r = 0;
        
        for (int i=0; i<left.length; i++){
            while (r<right.length && (long)right[r]*2<(long)left[i]) r++;
            res[0] += r;
        }
        
        int idx = 0;
        int l = 0;
        r = 0;
        
        for (int i=0; i<nums.length; i++){
            if (r>=right.length || (l<left.length && left[l] < right[r])){
                nums[i] = left[l++];
            }else{
                nums[i] = right[r++];
            }
        }
    }
}
```

## 顺序重现关系(Sequential Recurrence Relation)，用式子表示是T(i, j) = T(i, j - 1) + C
这里非常类似 dp， 在之前的情况下， 如果加上当前的元素， 会发生什么变化。

假设现在有n个数， 那么当加上第n+1个数字的时候， 我们只需要看前n个数字有多少个数字满足大小关系。如果把前面的数字都排好序， 那么我们可以用二分来解决这个问题。 但是当插入新的数字的时候， 复杂度是线性的， 所以即使每次查询是logn， 但是插入数组是o(n)， 那么总的复杂度也变为o(n^2)。 

所以这个时候， 我们应该用 bst 或者 Bit。 BST 一定需要保证是balanced tree， avl 或者 red black。

那么该如何使用 BIT 呢？

## BIT
由于BIT的存储方式不是将数组中的数字对应的一一存入，而是有的对应存入，有的是存若干个数字之和，其设计初衷之一就是要在O(lgn)的时间复杂度下完成 从边缘到中间一点的 求和运算。

以本题为例子， 假设从右往左扫描数组，比如当前数字是6， 那么我们就需要找到， 在这之前， 一共有多少个数字是小于等于2的， 那么这和 bit 有什么关系呢？

在这里， 我们可以把问题理解为， 从最小的数组开始从小到大扫描， 一共有多少个数字是 小于等于 2的， 那么这样的话， 我们就构造出了 “边界” 和BIT 扫描的另外一个边界。

我们需要将 每个数字 Map 到一个Bit对应的 Index 上， 左边界是最小的数字， 然后依次增长。那么当我们要搜索有多少个数字比2小的时候， 我们只需要搜索有多少个数字， 在 2 对应的Index的左边， 俺么这就化为了 BIT 的求和问题， 只是这里， 我们保存的信息是每个数字的当前出现个数。

对于某个数的Index， 如果有重复数字的存在，那么每个数字对应的位置是其排好序之后最后出现的位置。

## 细节
在这里， 我们从右边往左边扫描， 那么我们对于一个数m， 要得到一共有多少个数 n， 2*n 之后还小于m。

晃眼一看， 上界好像是 （m-1）/2， 在都是正数的时候， 这是成立的。 但是如果 m <=0， 那么这就不成立了， 比如说 (4-1)/2= 1; (-4-1)/2 = -2， 而 -2*2 显示不是小于-4的。

所以正确的表达形式应该是 分奇偶的，对于奇数： m/2， 对于偶数: m/2-1。

这个只是一个界限， 我们还要用二分法， 找到这个界限对应的 Idx 是多少， 在这里， 我们用类似于 search for a range 的方法。 

### 除了使用二分来寻找界限的Index， 我们也可以使用 treemap.lowerKey(key)来直接找到这个index

## 代码
```
class Solution {
    public int reversePairs(int[] nums) {
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        int[] numsCopy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(numsCopy);
        for (int i=0; i<numsCopy.length; i++)   map.put(numsCopy[i], i);
        
        int[] bit = new int[nums.length+1];
        int res = 0;
        
        for (int i=nums.length-1; i>=0; i--){            
            int targetIdx = 0;
            if (nums[i]%2==0){
                targetIdx = find(numsCopy, (nums[i])/2-1);
            }else{
                targetIdx = find(numsCopy, (nums[i])/2);
            }
            res += getTotal(bit, targetIdx);
            update(bit, map.get(nums[i]));
        }
        
        return res;
    }
    
    void update(int[] bit, int idx){
        idx++;
        while (idx<bit.length){
            bit[idx]++;
            idx += idx&(-idx);
        }
    }
    
    int getTotal(int[] bit, int idx){
        idx++;
        int sum = 0;
        while (idx>0){
            sum += bit[idx];
            idx -= idx&(-idx);
        }
        return sum;
    }
    
    int find(int[] nums, int val){
        int left = 0;
        int right = nums.length-1;
        
        while (left<=right){
            int mid = left + (right-left)/2;
            
            if (nums[mid] <= val){
                left = mid+1;
            }else{
                right = mid-1;
            }
        }
        
        return right;
    }
}
```

## BIT 交换insert search 顺序。
在这里， 我们求和的范围是从左边界到某一个Index。 我们也可以顺序遍历数组， 那么我们就需要求和从某一个Index到右边界。 我们该如何用 BIT实现这个呢？

很简单， 我们只需要交换一下 在 insert 和 search 中的 index 更新操作。

```
idx += idx&(-idx), 
idx -= idx&(-idx)
```

之前原始的BIT操作， 我们更新的时候， 往右边更新， 然后求和的时候， 往左边求和， 求的是到左边界的sum。

现在我们需要求到右边界的sum， 我们就在求和的时候， 往右边求和，然后更新的时候， 往左边更新。

http://webcache.googleusercontent.com/search?q=cache:z8gQPTfSqcsJ:www.jianzhaoyang.com/grandyang/p/6657956.html+&cd=27&hl=en&ct=clnk&gl=us
