#274 H-Index

## 题意
根据一个数列， 得出H-Index. 有H个数字**至少大于等于H**.

## 题解一
最简单的想法， 首先对数组排序， 然后从后往前遍历。 维持一个计数器， 如果当前数字大于等于当前计数器， 说明这个数字肯定会算在最后的H-index中。 然后继续遍历。

那么什么时候停止呢？ 当发现当前数字小于计数器了。 所以就不满足“至少大于等于H”

时间复杂度 O(nlogn)

```
public int hIndex(int[] citations) {
        if (citations==null || citations.length==0) return 0;
        
        Arrays.sort(citations);
        
        int res = 0;
        int count = 1;
        for (int i=citations.length-1; i>=0; i--){
            res++;
            if (citations[i]<count++){
                res--;
                break;
            }
        }
        
        return res;
    }
```

## 题解二
这么多paper， 最大的h-index的可能是多少呢？ 数列的长度。 最小的h-index是多少呢？ 0

所以我们可以用一个Bucket来计数， 时间复杂度降为O(n)。

然后我们统计：  最大的可能H value，被引用数在它之上的paper篇数大于等于这个H value.

```
public int hIndex(int[] citations) {
        if (citations==null || citations.length==0) return 0;
        
        int[] bucket = new int[citations.length+1];
        Arrays.fill(bucket, 0);
        
        for (int item: citations){
            if (item>citations.length){
                bucket[citations.length]++;
            }else{
                bucket[item]++;
            }
        }
        
        int count = 0;
        int res = 0;
        
        for (int i=citations.length; i>=0; i--){
            count += bucket[i];
            if (i<=count){
                res = i;
                break;
            }
        }
        return res;
    }
```   