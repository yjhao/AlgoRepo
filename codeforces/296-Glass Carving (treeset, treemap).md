#296-Glass Carving (treeset, treemap)
## 题意
给你n*m大的矩形，然后切k刀，横切或者竖切， 让你输出每切一刀后所分割的小矩形的最大面积是多少

## 解题
对于每横切或者纵切之后， 最大的小长方形， 一定是由 最长的横线段， 和 最长的竖线段组成。 那么**为什么呢？**

原因：

横切和竖切， 分别形成 竖线段 和 横线段。

对于每一个竖线段长度， 都能匹配上任何一个的 横线段长度。 因为 竖切和横切 都是贯穿全部 而且 独立的。

所以分别在 横线段 和 竖线段 中取最大值， 即可以得到最大的 小长方形。 

所以这个题就转化为 在每切一刀后，分别找到当前的最长的 横线段 和 竖线段。

用 一个 treeset 来记录下 每一个cut的位置， 为什么要用 treeset？

因为可以方便的得到， 之前的cut中， 比当前cut 小的 和 大的 cut的位置， 从而确定当前Cut所形成的新的 线段 的大小。

```
set.floor(n); set.ceiling(n)
```

用一个treemap 记录下当前 拥有的 线段。 使用treemap， 不仅可以快速locate每根线段进行加减或者删除， 还可以排序 并取得最大的那一个。

```
map.floorKey(n); map.ceilingKey(n); 
map.lastKey();
```

#代码
```
public void solve() throws IOException {
        int w = in.nextInt();
        int h = in.nextInt();
        int n = in.nextInt();

        TreeMap<Integer, Integer> w_map = new TreeMap<>();
        TreeMap<Integer, Integer> h_map = new TreeMap<>();
        TreeSet<Integer> w_cut = new TreeSet<>();
        TreeSet<Integer> h_cut = new TreeSet<>();

        w_cut.add(0);
        w_cut.add(w);
        h_cut.add(0);
        h_cut.add(h);

        w_map.put(w, 1);
        h_map.put(h, 1);

        StringBuilder sb = new StringBuilder();

        for (int i=0; i<n; i++){
            String s = in.next();
            int pos = in.nextInt();
            if (s.equals("H")){
                modify(pos, h_map, h_cut);
            }else{
                modify(pos, w_map, w_cut);
            }

            sb.append((long)h_map.lastKey() * (long)w_map.lastKey());
            sb.append("\n");
        }

        out.print(sb.toString());
        return;
    }

    public void modify(int pos, TreeMap<Integer, Integer> diff, TreeSet<Integer> cut){
        int l = cut.floor(pos);
        int r = cut.ceiling(pos);
        int d = r-l;

        // remove old section
        if (diff.get(d)==1){
            diff.remove(d);
        }else{
            diff.put(d, diff.get(d)-1);
        }

        // add new section
        int s1 = pos-l;
        if (diff.containsKey(s1)){
            diff.put(s1, diff.get(s1)+1);
        }else{
            diff.put(s1, 1);
        }

        int s2 = r-pos;
        if (diff.containsKey(s2)){
            diff.put(s2, diff.get(s1)+1);
        }else{
            diff.put(s2, 1);
        }

        // add this cut
        cut.add(pos);
    }
```