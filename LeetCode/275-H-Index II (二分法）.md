# 275 H-Index II (二分法）

## 题意
求H-Index， 引用已经按大小顺序排好了， 找到H, 有H个数字至少大于等于H, 所以是比较**离端点距离和当前数字的关系**， 采用Binary search

## 解题
使用二分法， 比较右侧的论文数量和Mid的引用 a。返回的结果为 右边论文的数量。

如果论文数量大于a， 那么说明有太多论文被计算在内， 最小引用数并没有达到论文的数量， 所以论文的数量得减少，区间右移； 

反之即为 被计算在内的论文数太少了， 以至于最小的引用数大于论文的数量， 所以可以再包括几篇论文， 区间左移。

最后的返回， 应该是在Mid右侧的论文数量， 而不是mid的引用.

因为还要对最后一个 Mid 进行判断， 看 Mid 是否合格， 所以必须得使用 While (left<=right), 而不是 While (left<right)

## 代码
```
public class Solution {
    public int hIndex(int[] citations) {
        if (citations==null || citations.length==0)
            return 0;
        int left = 0;
        int right = citations.length-1;
        int len = citations.length;
        
        while (left<=right){
            int mid = left + (right-left)/2;
            if (citations[mid]==len-mid)
                return len-mid;
            
            if (len-mid>citations[mid]){
                left = mid+1;
            }else{
                right = mid-1;
            }
        }
        
        return len-left;
    }
}
```

