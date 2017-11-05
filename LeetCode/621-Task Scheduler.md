# 621-Task Scheduler
## 题意
给一些task， 每个task的执行只需要用一个单位时间， 但是限制条件是， 相同的task， 要执行的话， 有一个最小相差时间。 问执行完所有的Task, 最小的时间是多少。

## 解题一
用Greedy的方法， 当前需要执行的task， 必然是在当前还剩下最多个数的task。

我们用一个queue 来保存“冷冻期” 的task，然后在每次执行task前， 将冷冻期中完成冷冻期的task重新加入 一个 pq 中， 然后从pq中取出最多个数的一个task。 然后再将其放到冷冻期里面去。

需要主要的是， 下一个要执行的task的执行时间不一定是当前时间， 所以我们需要记录每一个task分别的上次执行时间， 然后根据这个执行时间来确定这个task的最终执行时间。

如果这个task是从pq里面取出来的， 执行时间一定会是当前时间， 只有从冷冻期中取出的task， 才会有不同的执行时间。

## 代码
```
class Solution {
    public int leastInterval(char[] tasks, int n) {
        if (tasks.length==0)    return 0;
        if (n==0)   return tasks.length;
        PriorityQueue<pair> pq = new PriorityQueue<pair>(new Comparator<pair>(){
            public int compare (pair a, pair b){
                return b.cnt - a.cnt;
            }
        });
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (char c : tasks) map.put(c, map.getOrDefault(c, 0)+1);
        for (char c : map.keySet()){
            pq.offer(new pair(c, map.get(c)));
        }
        LinkedList<pair> queue = new LinkedList<pair>();
        
        int curTime = 1;
        
        while (pq.size()>0 || queue.size()>0){
            while (queue.size()>0 && curTime - queue.peek().lastTime > n)  {
                pq.offer(queue.poll());
            }
            
            if (pq.size()>0){
                pair curPair = pq.poll();
                curPair.cnt--;
                curPair.lastTime = curTime;
                if (curPair.cnt>0)  queue.offer(curPair);
            }else{
                pair curPair = queue.poll();
                curPair.cnt--;
                curTime = curPair.lastTime + n + 1;
                curPair.lastTime = curTime;
                if (curPair.cnt>0)  queue.offer(curPair);
            }
            curTime ++;
        }
        
        return curTime-1;
    }
}

class pair {
    char c;
    int cnt;
    int lastTime = 0;
    public pair(char c , int cnt){
        this.c = c;
        this.cnt = cnt;
    }
}

```

## 解题二， O(n) 时间复杂度。
我们只需要给出最小时间， 而不需要给出具体的摆放。

我们只需要求出最多频率的任务的个数， 假设为m， 和这个最大频率是多少， 假设为k， 然后使用他们建立 间隔为 n -（m-1) 的格子。 比如说最大频率为3， 一共有AB两个都是最大频率， 间隔需要3，那么格子就类似于：

ABXXABXXAB

我们现在需要将其他的任务填在其中的空格中， 一共有 m-1 组空格， 而剩下的任务最大的频率是m-1， **所以一定有办法不留空的将这些任务放在这些空格中** （就不证明具体的摆放了 题目也没有要求。 这些任务之间是不留空格的， 但是在所有任务都填入之后， 这些任务的end 和 最开始确定的 AB 是有可能有空格的）。

所以我们只需要判断， 最开始确立的空格一共有多少， 然后和剩余的任务相比较， 如果空格更多， 那么答案就是由AB确立的格子个数； 否则， 答案就是 总的任务数 -- 超出空格之后， 在 最后一个"桩子“之后还继续填充。

一个特例是如果 最大任务的频率超过了 n， 那么空格 n -（m-1) 岂不是为负数了？？ 

比如 AAABBBCCCDD, n=2。

其实这里 n 是一个 **最小** 距离， 我们当然可以将任务之间的距离扩大， 所以如果是这样的话，我们总可以满足条件， 因为桩子本身已经满足条件了， 在桩子之间加更多的东西， 也是满足条件的。而加入的东西， 因为频率是小于桩子的， 所以他们也是满足条件的。

https://discuss.leetcode.com/topic/92966/java-o-n-time-o-1-space-1-pass-no-sorting-solution-with-detailed-explanation/2

## 代码
```
public int leastInterval(char[] tasks, int n) {
        int[] counter = new int[26];
        int max = 0;
        int maxCount = 0;
        for(char task : tasks) {
            counter[task - 'A']++;
            if(max == counter[task - 'A']) {
                maxCount++;
            }
            else if(max < counter[task - 'A']) {
                max = counter[task - 'A'];
                maxCount = 1;
            }
        }
        
        int partCount = max - 1;
        int partLength = n - (maxCount - 1);
        int emptySlots = partCount * partLength;
        int availableTasks = tasks.length - max * maxCount;
        int idles = Math.max(0, emptySlots - availableTasks);
        
        return tasks.length + idles;
    }
```

