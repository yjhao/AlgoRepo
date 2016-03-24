#57-Insert Interval

## 题意
排好序的interval list， 插入一个新的， 然后merge有重合的

## 解题

## 注意事项
1. left right 指针 在while 之后， 很容易出界。比较的时候， 一定要检查是否出界。
2. 更新插入的interval的右边界的时候， 一定要update in while loop，即合理又方便， 还杜绝了corner case --》 如果while为true， 说明有交集， 说明可以更新边界。

不然停在的后面那个Interval (idx i), 按道理说， 应该是比较当前merge Interval end 和 i-1 的end, 不过万一 i=0呢？
 

### 不是最优的解法
最开始， 想到in place， 用left,right记录下来有重合的， 然后删掉中间的interval， 加入新的Interval。

主要缺点：

1. ArrayList remove 是O(n)复杂度。

### 最优解法
建立一个空的list， 然后一个一个往里面加。

先扫描走到新的interval应该插入的位置，接下来就是插入新的interval并检查后面是否冲突，然后不断更新end。 一直到新的interval的end小于下一个interval的start，然后取新interval和当前interval中end大的即可。

这个方法的优势是不需要处理中间有多少个应该删除的interval, 到哪里了就处理到哪里。


## 代码
```
public class Solution {
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> res = new ArrayList<Interval>();
        
        if (intervals==null || intervals.size()==0){
            res.add(newInterval);
            return res;
        }
        
        int i = 0;
        
        while (i<intervals.size() && intervals.get(i).end < newInterval.start){
            res.add(intervals.get(i));
            i++;
        }
        
        res.add(newInterval);
        
        if (i<intervals.size()){
            newInterval.start = Math.min(newInterval.start, intervals.get(i).start);
        }
        
        while (i<intervals.size() && intervals.get(i).start<=newInterval.end){
            newInterval.end = Math.max(newInterval.end, intervals.get(i).end);
            i++;
        }
        
        while (i<intervals.size()){
            res.add(intervals.get(i));
            i++;
        }
        
        return res;
        
        
    }
}
```