# 391-Perfect Rectangle （扫描线 or 几何）
## 题意
给一些小的矩形， 看能不能组成一个大的矩形， 而且没有重叠和空隙。

## 解题， 方法一
首先， 所有的小矩形的面积加起来， 应该为大矩形的面积， 大矩形的面积可以由 minx miny maxx maxy 得到。

仔细观察之后， 可以得到一个规律：

1.所以在大矩形之内的交点， 都会有偶数个 小矩形 相交。如果是奇数个的话， 那么一定出现了Overlap。  
2. 大矩形的四个Corner, 一定分别只有一个小矩形的交点。 而且只有一个交点的Corner， 有且只能有四个， 不然的话， 就说明有空隙。

所以用hashmap 对每一个小矩形的Corner进行操作， 如果遇到存在， 就删除（偶数）。 然后检查最后剩下的corner 是不是有且只有四个， 而且就是由 minx, miny, maxx, maxy 组成的四个点。 

还得确保总面积相等。

时间复杂度 O(n).

## 代码
```
public class Solution {
    public boolean isRectangleCover(int[][] rectangles) {
        HashSet<String> set = new HashSet<String>();
        int minx = Integer.MAX_VALUE;
        int miny = Integer.MAX_VALUE;
        int maxx = Integer.MIN_VALUE;
        int maxy = Integer.MIN_VALUE;
        int area = 0;
        
        for (int i=0; i<rectangles.length; i++){
            String p1 = toString(rectangles[i][0], rectangles[i][1]);
            String p2 = toString(rectangles[i][2], rectangles[i][3]);
            String p3 = toString(rectangles[i][0], rectangles[i][3]);
            String p4 = toString(rectangles[i][2], rectangles[i][1]);
            if (set.contains(p1))   set.remove(p1);
            else set.add(p1);
            
            if (set.contains(p2))   set.remove(p2);
            else set.add(p2);
            
            if (set.contains(p3))   set.remove(p3);
            else set.add(p3);
            
            if (set.contains(p4))   set.remove(p4);
            else set.add(p4);
            
            area += calcArea(rectangles[i]);
            minx = Math.min(minx, rectangles[i][0]);
            miny = Math.min(miny, rectangles[i][1]);
            maxx = Math.max(maxx, rectangles[i][2]);
            maxy = Math.max(maxy, rectangles[i][3]);
        }
        
        if (area != (maxx-minx)*(maxy-miny))    return false;
        if (set.size()!=4)  return false;
        String p1 = toString(minx, miny);
        String p2 = toString(minx, maxy);
        String p3 = toString(maxx, maxy);
        String p4 = toString(maxx, miny);
        if (!set.contains(p1) || !set.contains(p2) || !set.contains(p3) || !set.contains(p4))   return false;
        return true;
    }
    
    int calcArea(int[] s){
        return (s[2]-s[0])*(s[3]-s[1]);
    }
    
    String toString(int x, int y){
        return x+" "+y;
    }
}
```

## 解题， 方法二， 扫描线算法， treeset去重
如何使用 扫描线： 

对每一个小矩形， 从左至右 加入和删除 y 线段。 在小矩形的最左端加入y线段， 在小矩形的最右端相应的删除这个y线段。

一定得按照从左至右的顺序来加入和删除， 先删除， 再加入。否则先加入的话， 有可能会出现重复的。 

我们可以对 x 排序，以满足 从左至右。 但是在同一个x， 有可能是一个矩形的最左端， 也有可能是一个矩形的最右端， 所以为了满足先删除， 在这个情况下， 我们得把 矩形的 最右端放在前面， 把矩形的最左端放在后面。

### 扫描线
如果用这样的方法， 那么在每一个 X 的位置， 加入删除y线段之后， 总的y线段的长度一定为 maxy - miny。否则说明一定有**空隙**。有一个例外， 当处理完所有的矩形之后， ysum一定为0， 所以要排除出这样的情况。

那么如何确保没有**Overlap**呢？ 使用treeset comparator 来比较， 如果没有overlap， 那么一定是 y1[0]>y2[1] or y1[1]>y2[0]， 如果这两个不等式不成立的话， 那么就说明有Overlap， 那么在comparator中既可以返回0， 说明遇到相等的了。 而如果是相等的， 那么 set.add() 就会返回 false。

## 代码
```
public class Solution {
    public boolean isRectangleCover(int[][] rectangles) {
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        PriorityQueue<item> pq = new PriorityQueue<item>();
        for (int[] sq : rectangles){
            minY = Math.min(minY, sq[1]);
            maxY = Math.max(maxY, sq[3]);
            pq.offer(new item(sq[0], sq));
            pq.offer(new item(sq[2], sq));
        }
        
        TreeSet<int[]> set = new TreeSet<int[]>(new Comparator<int[]>(){
           public int compare (int[] a, int[] b){
               if (a[0] >= b[1])    return 1;
               else if (b[0] >= a[1])   return -1;
               else return 0;
           } 
        });
        
        int ysum = 0;
        while (pq.size()>0){
            int curLoc = pq.peek().loc;
            while (pq.size()>0 && pq.peek().loc == curLoc){
                item cur = pq.poll();
                int[] thisY = {cur.sq[1], cur.sq[3]};
                if (cur.loc == cur.sq[2]){
                    set.remove(thisY);
                    ysum = ysum - (thisY[1]-thisY[0]);
                }else{
                    if (!set.add(thisY))    return false;
                    ysum = ysum + (thisY[1]-thisY[0]);
                }
            }
            if (!pq.isEmpty() && ysum != maxY-minY)  return false;
        }
        return true;
    }
}

class item implements Comparable<item> {
    int loc;
    int[] sq;
    public item(int loc, int[] sq){
        this.loc = loc;
        this.sq = sq;
    }
    
    public int compareTo(item other){
        if (other.loc == this.loc)  return this.sq[0] - other.sq[0];
        return this.loc - other.loc;
    }
}
```

