# 632-Smallest Range
## 题意
给K个排好序的list，每一个list里面都是从小到大的正数， 问求出一个最小的range， 使得每一个list都能至少找到一个数， 在这个range之中。

## 解法一。
我们可以每次操作的时候， 分别加入下一个最小的数，加入到一个queue里面， 这个queue记录着当前的range有多长。 加入这个最小的数之后，如果这个range里面有来自于K个list的数，我们再增大左端点，同时检查这个range里面是否有来自于k个list的数， 并且更新min range。

时间复杂度： knlog(k)。 但是需要用一个queue来记录下当前的range。

## 代码
```
public int[] smallestRange(List<List<Integer>> nums) {
        int[] res = new int[2];
        int min = Integer.MAX_VALUE;
        LinkedList<pair> queue = new LinkedList<>();
        PriorityQueue<pair> pq = new PriorityQueue<pair>(new Comparator<pair>(){
            public int compare (pair a, pair b){
                return a.val - b.val;
            }
        });
        int[] cnt = new int[nums.size()];
        int num = 0;
        
        for (int i=0; i<nums.size(); i++){
            pq.offer(new pair(i, 0, nums.get(i).get(0)));
        }
        
        while (pq.size()>0){
            pair cur = pq.poll();
            int curListIdx = cur.listIdx;
            cnt[curListIdx]++;
            if (cnt[curListIdx]==1) num++;
            int curVal = cur.val;
            if (cur.idx <= nums.get(curListIdx).size()-2){
                pq.offer(new pair(curListIdx, cur.idx+1, nums.get(curListIdx).get(cur.idx+1)));
            }
            queue.offer(cur);
            
            while (num == nums.size()){
                if (curVal - queue.peek().val < min){
                    min = curVal - queue.peek().val;
                    res[0] = queue.peek().val;
                    res[1] = curVal;
                }
                pair first = queue.poll();
                int firstListIdx = first.listIdx;
                cnt[firstListIdx]--;
                if (cnt[firstListIdx]==0)   num--;
            }
        }
        
        return res;
    }
```

## 解法二。
在解法一中， 我们用了一个pq来得到下一个最小的数字， 用了一个queue来记录当前的range。

那么我们再想想， 其实这个题目的意思就是找到一个range， 这个range里面一定有来自k个list的数字。 初始情况一定是， 每一个List的第一个数字在这个Range里面， 然后这是一个合法的range， 我们可以求出一个大小。 

假设我们已经有了一个合法的range， 那么我们可以稍加改动， 就任何时候都能保持一个合法的Range： 每次删掉一个数，然后加上这个数所属于的list中的另外一个数， 那么一定是一个合法的Range。 

假设有三个数字在这个range之中， 1， 2， 3。 如果我们要更新的话， 应该是删掉1， 然后再加入1所属于的那一个list中的下一个数字到这个pq里面。 这样的话， 可以保证下一个也是一个合法的range，并且这个range有可能更小， 我们可以求出新的range的长度， 并与之前的进行比较。

为什么去掉1， 而不却掉2， 或者3来更新range呢？

假设去掉2， 那么2的list的下一个数， 有可能在2到3之间， 或者大于3， 无论哪种情况下， 这个range都不可能更小。

如果去掉3， 那么3的list的下一个数， 一定比3大， 那么新的range一定不可能更小。

所以只有去掉最小的数， 也就是1， 有可能得到一个更小的range。

使用一个pq来维护这一个Range: 每次删掉最小的一个数。 

## 代码
```
class Solution {
    public int[] smallestRange(List<List<Integer>> nums) {
        int[] res = new int[2];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        PriorityQueue<pair> pq = new PriorityQueue<pair>(new Comparator<pair>(){
            public int compare (pair a, pair b){
                return a.val - b.val;
            }
        });
        
        for (int i=0; i<nums.size(); i++){
            pq.offer(new pair(i, 0, nums.get(i).get(0)));
            max = Math.max(nums.get(i).get(0), max);
        }
        
        while (pq.size()==nums.size()){
            if (max-pq.peek().val < min){
                min = max-pq.peek().val;
                res[0] = pq.peek().val;
                res[1] = max;
            }
            
            pair cur = pq.poll();
            if (cur.idx+1 == nums.get(cur.listIdx).size())  break;
            pq.offer(new pair(cur.listIdx, cur.idx+1, nums.get(cur.listIdx).get(cur.idx+1)));
            max = Math.max(max, nums.get(cur.listIdx).get(cur.idx+1));
        }
        
        return res;
    }
}

class pair{
    int listIdx;
    int idx;
    int val;
    public pair (int listIdx, int idx, int val){
        this.listIdx = listIdx;
        this.idx = idx;
        this.val = val;
    }
}
``` 