#218-The Skyline Problem
##题意
非常经典的题目了

##解题
扫描线解法。只计算 有一个长方形加入 和 有一个长方形退出 的情况

根据题目例子可以看出， 删除一个高度之后， 节点的高度为**剩下来**的最高高度， 或者是正好在这个节点**新加入**的高度。

所以 得 **先加入， 再删除**。

如何辨别是加入还是退出？
加入 和 删除， 一个设为负数， 一个设为正数。

###但谁是正数， 谁是负数？
从之前的发现 我们可以得到， 在某一个相同的 X 坐标， “先加入， 再删除”， 所以在处理高度的时候， 得把“加入”的放在“删除前面“。

而且如果先考虑删除的， 那么很有可能最高值发生了变化，其实有可能根本就没有变化， 因为后面又加入了一段。

那么在 加入 和 删除 内部 又怎么排序呢？
 
”一叶障目“ 的故事


”加入的“ 高度中 高的要放在高度低的前面。在这个位置加入 的更矮的高度， 跟这个最高的比起来， ”都不起作用“。

而对于删除， 高度高的要放在高度低的后面。 因为删除掉 较矮 的之后， 起作用的仍然是更高的那一个， 

比如说在 X 要删除掉 4，5，6， 还剩3， 不会出现 （x,5) , (x,4), (x,3) 这样的情况， 其实答案只有（x,3)。

综合以上的结论， 将加入设为负数， 将删除设为正数，绝对值最大的正好在两端 （实现了 绝对值最大的最先加入，或者最后删除）。 将 sort 设计为 “递增顺序” 正好可以实现 “先加入， 再删除“ 

### 更新高度
在加入或退出的过程中， 如果当前**最高值**发生了变化， 那么这就是一个拐点。

## 数据结构
在退出一个长方形的时候， 要remove掉这个值。

如果用priorityqueue的话， remove的复杂度是O(n)， 所以总的复杂度是o(n^2)

所以应该选用 TreeMap， remove的复杂度是O(nlogn)

不过需要注意的是， PQ支持duplciation， 而用treemap的话， 需要把重复的合并在一个key里面。在 删除 和 加入 的时候， 需要先判断一下。

##代码
```
public class Solution {
    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> result = new ArrayList<>();
        List<int[]> height = new ArrayList<>();
        for(int[] b:buildings) {
            height.add(new int[]{b[0], -b[2]});
            height.add(new int[]{b[1], b[2]});
        }
        
        Collections.sort(height, new Comparator<int[]>(){
            public int compare(int[] a, int[] b) {
                if(a[0] != b[0]) return a[0] - b[0];
                return a[1] - b[1];
            }
        });
        
        TreeMap<Integer,Integer> map = new TreeMap<Integer,Integer>(Collections.reverseOrder());
        
        map.put(0, 0);
        int prev = 0;
        for (int[] h : height){
            if(h[1] < 0){
                if (!map.containsKey(-h[1]))
                    map.put(-h[1],0);
                map.put(-h[1], map.get(-h[1])+1);
            }else{
                if (map.get(h[1])==1)
                    map.remove(h[1]);
                else
                    map.put(h[1], map.get(h[1])-1);
            }
            
            int cur = map.firstKey();
            if (cur!=prev){
                result.add(new int[]{h[0], cur});
                prev = cur;
            }
        }
        

        return result;
    }
}
```