# 275 H-Index II
## 题意
类似于274， 根据一个数列， 得出H-Index. 有H个数字**至少大于等于H**，但此时这个数列已经排好序了。

## 题解
类似于274 题解1， 找出一篇paper，（在他右边的Paper的引用数大于等于它），右边Paper的总数， 要大于等于他们的最小引用， 也就是此时的这篇Paper。 

找到最大h-index, 就是找到 右边Paper的总数， 要**等于**他们的最小引用。

所以是比较离端点距离和当前数字的关系， 采用Binary search

注意特殊情况： 数组全为0

```
public int hIndex(int[] citations) {
        if (citations==null || citations.length==0) return 0;
        int left = 0;
        int right = citations.length-1;
        
        while (left<=right)
        {
            int mid = left + (right-left)/2;
            if (citations[mid] == citations.length-mid)
                return citations.length-mid;
            
            if (citations[mid]>citations.length-mid)
            {
                right = mid-1;
            }
            else
            {
                left = mid+1;
            }
        }
        
        return citations.length-left;
    }
```


