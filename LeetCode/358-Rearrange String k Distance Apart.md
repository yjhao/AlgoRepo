#358-Rearrange String k Distance Apart

##题意
将string重新安排一下， 保证相同char之间间隔最少k个单位。

##解题
greedy， 为了保证数量多的char一定能够放的进这个string， 数量越多的char， 就需要越早的放入， 所以用一个优先队列来对char进行排序。

将某个Char放入String中， 在 k 个 距离之内都不能再放入它， 所以我们使用一个 queue来存放暂时不能使用的 Char。

**注意的要点**

我们一定要保证这个 queue 的长度为 k， 不然怎么能够保证这个Window的距离为k？所以每次从 queue 中 poll 出来的元素， 都一定是 K 个据连之前放入的。

为了保证这个 queue 的长度一定为k, 所以无论 char 还有没有剩余， 都需要把它放入这个 queue 来**占位**。

当把 char 从 queue 取出来之后， 再判断还需不要考虑这个 char （ 放入 或者 不放入 pq 来进行下一次分配）。


如果在某个时候， pq 已经拿不出可以选择的 char (因为都在 queue）中， 那么就返回false。

##代码
```
public class Solution {
    public String rearrangeString(String str, int k) {
        if(k==0)    return str;
        PriorityQueue<pair> pq = new PriorityQueue<pair>(new Comparator<pair>(){
           public int compare (pair a, pair b){
               return b.cnt - a.cnt;
           } 
        });
        int[] cnt = new int[26];
        for (char c : str.toCharArray())    cnt[c-'a']++;
        for (char c = 'a'; c<='z'; c++){
            if (cnt[c-'a']==0)  continue;
            pq.offer(new pair(c, cnt[c-'a']));
        }
        LinkedList<pair> queue = new LinkedList<pair>();
        
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<str.length(); i++){
            if (i>=k){
                pair cur = queue.poll();
                if (cur.cnt>0)  pq.offer(cur);
            }
            if (pq.size()==0)   return "";
            pair cur = pq.poll();
            sb.append(cur.c);
            cur.cnt--;
            queue.offer(cur);
        }
        return sb.toString();
    }
}

class pair {
    Character c;
    int cnt;
    public pair(Character c, int cnt){
        this.c = c;
        this.cnt = cnt;
    }
}
```