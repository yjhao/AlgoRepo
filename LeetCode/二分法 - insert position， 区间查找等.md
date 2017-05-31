# 二分法 - insert position， 区间查找等

## 一道Facebook面试题
有一串数， 如何随机抽出一个数， 某个数被随机抽中的概率正比于其大小。 比如 1，3，5， 3被抽中的概率为3/9。

### 解题
求prefix sum， 组成数列： 1，4，9。然后随机生成0-9中的一个数， 然后看在哪个区间之内。 比如说生成3， 那么比4小， 比1大， 所以4被抽中。

如何找到这个数？最简单的方法， 一次遍历， O(n)。

但由于数字是按顺序排列的， 所以可以用二分法。

那么如何确定二分法边界和while 条件呢？

本问题的几个特征：

1. 生成的数字， 一定是小于等于答案的， 所以是左区间。
2. 如果一个数字大于某个数a， 那么a一定不会是答案。
3. 如果一个数小于某个数a， 那么a有可能是最后的答案。**所以处理这里的边界的时候， 需要包括“等于”**


二分法， 最好的验证方法就是：

####用三个数字来测试假设是否正确， 三个数字， 测试四个区间####

以下是最后的代码，注意向左收紧的时候， **r=m**, 由特征的第二点推出。

```
public static int choose (int[] n, int ran){
		int l = 0;
		int r = n.length-1;
		
		while (l<r){
			int m = l+(r-l)/2;
			if (ran<n[m]){
				r = m;
			}else if (ran==n[m]){
				return n[m];
			}else{
				l = m+1;
			}
		}
		
		return n[l];
	}
```

## Insert Position
### 题意
将一个数字插入一个有序的序列中， 求插入位置。

### 解题
这个问题， 很类似于上一个问题， 也是求“区间范围”

唯一需要注意的是， 对于1，3，5的数列， 因为前面的方法只考了吧的左区间， 所以当被插入数字大于5的时候， 也即是5的右区间， 我们需要首先特殊考虑。 其他的都和前一个问题相同。

1. 如果被插入数小于某个数a， 他被插入的位置**有可能就是a的位置。**
2. 如果被插入书大于某个数a， 他被插入的位置一定比a的位置大。

### 代码
```
public int searchInsert(int[] nums, int target) {
        if (nums.length==0)
            return 0;
        int l=0, r=nums.length-1;
        
        if (target>nums[r]){
            return r+1;
        }
        
        while (l<r){
            int m = l + (r-l)/2;
            if (nums[m]<target){
                l = m+1;
            }else if (nums[m]>target){
                r = m;
            }else{
                return m;
            }
        }
        return l;
    }
```


