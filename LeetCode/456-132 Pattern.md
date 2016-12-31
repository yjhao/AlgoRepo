#456-132 Pattern
## 题意
给定整数序列a1, a2, ..., an，132模式是指一组序列 ai, aj, ak 满足  i < j < k and ai < ak < aj。设计算法判断长度为n的整数列表中是否包含132模式。

##解题
## O(n2) 方法
如果我们确定了最右边的数字 j， 那么只需要在其左面找到两个数， i , k， ai < ak, aj > ak, i<j。 所以从左边开始搜索I, 从J的右边开始搜索k， 如果找到满足这个要求的i, k pair， 那么就返回 true

###代码
```
public boolean find132pattern(int[] nums) {
        if (nums.length<3)  return false;
        
        for (int i=nums.length-1; i>1; i--){
            int cur = nums[i];
            int left = 0;
            int right = i-1;
            while (left<right){
                while (left < right && nums[left]>=cur) left++;
                while (left < right && nums[right]<=cur)    right--;
                if (left<right && nums[left]<cur && nums[right]>cur)    return true;
            }
        }
        
        return false;
    }
```

##O(nlogn)方法， 二分查找树， treemap
在这里， 当遍历到I的时候， 我们随时更新i之前的最小的数是多少， 那么假设ai是三个数中最大的数， 那么右边有没有一个数 大于最小的数， 并小于ai。

普通的搜索是O(n)， 但是我们使用了二分查找树， treemap之后， 这个搜索可以简化为 logn。

注意， 因为是要严格的大于最小数， 所以不能使用ceilingKey （大于等于）， 而要使用 higherKey().

###代码
```
public boolean find132pattern(int[] nums) {
        if (nums.length<3)  return false;
        
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        for (int num : nums)    map.put(num, map.getOrDefault(num, 0) + 1);
        int min = Integer.MAX_VALUE;
        
        for (int i=0; i<nums.length; i++){
            int cur = nums[i];
            if (map.get(cur)==1)    map.remove(cur);
            else    map.put(cur, map.get(cur)-1);
            
            if (cur<=min)    min = cur;
            else{
                Integer val = map.higherKey(min);
                if (val !=null && val < cur)  return true;
            }
        }
        return false;
    }
```

##O(n) 方法， 使用stack。
之前的方法， 我们都没有办法保存之前的信息。

我最开始想到保存两个min, max， 然后随时更新， 但是很快就发现， 这样的方法不能保存min , max 的相对位置， 如果出现了max， 我们可以更新max， 但是出现了min， 不能反映min是在max之后的信息。

如果是要寻找 s1 < s2 < s3， 那么首先找s1, then s2 and s3， but here it is s2 is max, s2>s3>s1， 所以我们首先找 s2, s3， 最后再确定s1。

我们从最末尾开始往前走， 假设当前为i， 我们需要找到在i右边， 最大的一个比ai小的数是多少（ 这个数即为s3）， 然后如果之后（在i的位置前面）出现了一个比这个数小的数 （即为S1)， 那么就一定满足132 pattern。

诀窍是使用 stack 不停的更新。 加入一个数之后， 不停的pop出 stack 中比其小的数， 那么就可以确定这个数。

如果加入100， stack 中为 60 80 110， 那么到110的时候停止， s3Max 为80， 所以之后出现了一个比80 小的数， 一定比100小， 一定满足132。

那么问题是， 如果110 之后还有比80大的数呢？ 那么恭喜了， 在80的时候， 就已经满足了132 pattern。

另外一个例子是 加入110， 现在的stack 中是 60， 80, 那么S3Max = 80, s2 = 110。 这个时候， 下一个数字有几种情况：

1. 比 110 大， 那么继续更新 s3Max 为 110。
2. 小于80， 满足132.
3. 在80 和 110 之间， 这是最关键的地方。比如说是100， 直接将他加入stack。
4. 那么如果之后出现一个数字比100大， 那么 s3Max 就修改为100；（100比80 更有利）。 如果小于80， 那么满足132； 如果在 80 和 100 之间， 将其加入Stack。 然后情况类似于之前的加入100， 因为如果要更新s2Max的话， 新的s2Max 总是比 之前的s2Max （80） 更大。


###细节
为什么不从数组的左端开始遍历， 而要从右端开始呢？

如果从左端开始， 如果i （s2) 大于stack中的数字，那么就pop并记录， 在i左面的最小的数是多少。 但是这样的信息， 并不适合stack， **这样的stack， 适合找到比I小的之中， 最大的数是多少**

另外一个原因是，s1<s3<s2， 似乎找到 s3, s2之后， 更容易利用线性找到 s1？ 如果找到 s1, s3，那么 s2没有这么方便确定？

###代码
```
public boolean find132pattern(int[] nums) {
        if (nums.length<3)  return false;
        
        LinkedList<Integer> stack = new LinkedList<Integer>();
        int s2Max = Integer.MIN_VALUE;
        
        for (int i=nums.length-1; i>=0; i--){
            if (nums[i] < s2Max)    return true;
            while (!stack.isEmpty() && stack.peek() < nums[i]){
                s2Max = stack.pop();
            }
            stack.push(nums[i]);
        }
        
        return false;
    }
```
   
