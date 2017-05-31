#553-Optimal Devision (divide and conquer)
##题意
给一组整数， 加上除法， 和括号之后， 问如果要得到计算结果的最大值， 括号和除法该怎么加。

##解题一
这个问题可以化为一个数学题。对于一个整数， 如果作为除数， 相比作为被除数， 结果肯定是更大的。

第一个数肯定只能当除数， 第二个数只能当被除数。 如果要使得结果最大， 而所有的数字又是整数， 那么就希望从第三个数字开始， 都成为除数。 那么 a/(b/c/d) , c d 都成为了除数。

##代码
```
public class Solution {
    public String optimalDivision(int[] nums) {
        StringBuilder sb = new StringBuilder();
        sb.append(nums[0]);
        if (nums.length>1)  sb.append("/");
        
        if (nums.length>2)  sb.append("(");
        for (int i=1; i<nums.length; i++){
            sb.append(nums[i]);
            if (i!=nums.length-1)   sb.append("/");
        }
        if (nums.length>2)  sb.append(")");
        
        return sb.toString();
    }
}
```

##解题二 （More general method)
这里我们就要使用 divide and conquer 了。。。

因为是除法， 所以肯定有两个部分。 所以可以想到， 将整个整数， 找到最合适的一分为二处， A/B， 然后再对于 A B 再找到最合适的分界处。

如果确定分界处最优？ 第一部分的**最大值**， 除以第二部分的**最小值**， 使得到的结果最大的地方。

为什么要记录 最小值和最大值？ 因为 一个部分， 有可能现在是被除数，那么就需要最小值； 也有可能一会儿就变成了除数， 那么就需要最大值。

可以使用 cache 来降低重复运算。

需要注意的是， 需要使用 Double 来记录除法结果。

```
public class Solution {
    public String optimalDivision(int[] nums) {
        if (nums.length==1) return ""+nums[0];
        HashMap<String, pair> map = new HashMap<String, pair>();
        return helper(0, nums.length-1, map, nums).maxString;
    }
    
    pair helper(int left, int right, HashMap<String, pair> map, int[] nums){
        String key = left + "#" + right;
        if (map.containsKey(key))   return map.get(key);
        if (left>right) return null;
        if (left==right){
            return new pair(nums[left], nums[right], ""+nums[left], ""+nums[right]);
        }
        
        double min = Integer.MAX_VALUE;
        double max = 0;
        String minS = "";
        String maxS = "";
        
        for (int i=left; i<right; i++){
            pair p1 = helper(left, i, map, nums);
            pair p2 = helper(i+1, right, map, nums);
            
            double maxValue = p1.max / p2.min;
            if (maxValue > max){
                max = maxValue;
                maxS = p1.maxString + "/";
                if (i+1!=right) maxS += "(";
                maxS += p2.minString;
                if (i+1!=right) maxS += ")";
            }
            
            double minValue = p1.min / p2.max;
            if (minValue < min){
                min = minValue;
                minS = p1.minString + "/";
                if (i+1!=right) minS += "(";
                minS += p2.maxString;
                if (i+1!=right) minS += ")";
            }
        }
        
        pair cur = new pair(min, max, minS, maxS);
        map.put(key, cur);
        return cur;
    }
}

class pair{
    double min;
    double max;
    String minString;
    String maxString;
    
    public pair(double min, double max, String minString, String maxString){
        this.min = min;
        this.max = max;
        this.minString = minString;
        this.maxString = maxString;
    }
}
```