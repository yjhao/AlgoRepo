#218-The Skyline Problem
##题意
非常经典的题目了

##解题
扫描线解法。只计算 有一个长方形加入 和 有一个长方形退出 的情况

如何辨别是加入还是退出？

如果是加入， 将高度“设为负数”， 反之， 是负数。 那么根据高度的正负， 就可以判断是加入还是退出。

在加入或退出的过程中， 如果当前**最高值**发生了变化， 那么这就是一个拐点。

**注意， 在同一个X坐标下， 高的会把矮的覆盖住； 然后先加入的应该比在当前x下退出的先考虑， 因为保持了连续性。**

如果先考虑推出的， 那么很有可能最高值发生了变化，其实有可能根本就没有变化， 因为后面又加入了一段。

**所以在对长方形 加入 退出 排序的时候， 首先对 加入 的 x 坐标进行递增排序， 然后相同x下， 加入的是“负数”， 而且“负的越大， 长度越大”， 所以应该也为递增排序。**

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