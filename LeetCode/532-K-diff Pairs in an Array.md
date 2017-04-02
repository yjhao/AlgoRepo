#532-K-diff Pairs in an Array
##题意
给一个Array, 在这个数列中， 找出绝对值相差为K的pair， 要求这个pair必须是Unique的。

##解题一
首先可以想到用hashmap来做。 需要注意的是， 如果k=0的时候， 那么pair中的数的大小要相同， 但是Index不能一样， 也就是说， 相同的数的个数一定要大于一。如果K不等于0， 那么只要存在num+k， 就可以了。

##代码
```
public class Solution {
    public int findPairs(int[] nums, int k) {
        if (k<0)    return 0;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int num : nums){
            if (!map.containsKey(num))  map.put(num, 0);
            map.put(num, map.get(num)+1);
        }
        int res = 0;
        
        for (int num : map.keySet()){
            if (k==0){
                res += map.get(num)>1? 1:0;
            }else{
                res += map.containsKey(num+k) ? 1:0;
            }
        }
        
        return res;
    }
}
```

#解题二
这个题目也可以用two pointers来做， 首先将数列排序， 那么数字就有单调性的， ```nums[l1] < nums[l2]```, 那么 如果存在一个数字分别为 ```nums[r1]-nums[l1] = nums[r2]-nums[l2] = k```, 一定可以得到的是， ```nums[r2] > nums[r1]```. 所以可以使用Two pointers， 因为不用再走回头路了。

我们可以针对每个不重复的l， 来找到 r。

所以可以用 for loop 来包含L, 因为无论如何， 我们也要走一遍L。 需要处理的是 当K=0的时候， 要确保 l!=r。

```
public class Solution {
    public int findPairs(int[] nums, int k) {
        if (k<0)    return 0;
        int r = 0;
        int res = 0;
        Arrays.sort(nums);
        
        for (int i=0; i<nums.length; i++){
            if (r>=nums.length) break;
            if (i!=0 && nums[i] == nums[i-1])   continue;
            while (r<nums.length && nums[r]-nums[i]<k)  r++;
            if (r>=nums.length) break;
            if (nums[r]-nums[i]>k)  continue;
            if (k==0){
                boolean flag = false;
                while (r<nums.length && nums[r]-nums[i]==0){
                    if (r!=i && !flag){
                        flag = true;
                        res++;
                    }
                    r++;
                }
            }else{
                res++;
                while (r<nums.length && nums[r] - nums[i] == k){
                    r++;
                }
            }
        }
        
        return res;
    }
}
```

也可以使用 while  loop 来实现。 需要注意的是， 每次都需要步进l 和 跳过重复的 R.

```
public class Solution {
    public int findPairs(int[] nums, int k) {
        int res = 0;
        if (k<0)    return 0;
        Arrays.sort(nums);
        
        int l = 0;
        int r = 0;
        
        while (l<nums.length && r<nums.length){
            while (l<nums.length && l!=0 && nums[l]==nums[l-1]) l++;
            while (r<nums.length && nums[r]-nums[l] < k) r++;
            if (l==nums.length || r==nums.length)   break;
            if (nums[r] - nums[l]==k){
                if (k==0){
                    r++;
                    while (r<nums.length && nums[r] == nums[l]){
                        r++;
                    }
                    if (r-l>1)  res++;
                }else{
                    res++;
                }
            }
            
            while (r<nums.length && nums[r]-nums[l]==k) r++;
            l++;
        }
        
        return res;
    }
}
```

