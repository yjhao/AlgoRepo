# 587-Erect the Fence
## 题意
给一些二维坐标点， 需要找到一个最小的凸包包围住左右的点， 找到正好在这个凸包上的所有点。

## 解题
需要找到 正好在这个凸包上的所有点， 那么就是要找到在这么多点中， 找到一个他们能够组成的最小的凸包， 而这个凸包又必须包括所有的其他点。

所以可以使用 Jarvis's algorithm 来解题。 理论基础就是

1. 确定好起点a， 然后需要找到以这个点作为端点， 连接到另外的某个点b的一条线段， 然后这条线段需要是凸包的一部分。
2. 那么既然要找到这条线断， 这条线段有什么特点呢？因为这条线段是凸包的一部分， 所以可以很容易的看到 a->b->任何的另外一点都需要**全为**顺时针或者**全为**逆时针， 是顺时针还是逆时针取决于构造这个凸包的顺序， 但是如果一个端点处为顺时针或者逆时针， 其他的所有的端点处都需要保持一致。


要找到端点B, 那么 a-b-anyPoint 都需要为逆时针（假设逆时针）， 我们可以先假设B为任意的一点， 那么一旦发现 a-b-onePoint 为顺时针， 那么说明 a-onePoint-b 为逆时针， 而且 a-onePoint-之前检测过的点 都为逆时针。

所以o(n)的方法就能找到 这个 b 点。 

这个题特别的地方在于， 还需要考虑共线的情况。 

这里我们设计的算法是， 找到共线中的， 相比基准点a最远的一个端点m， 然后再在所有的点中， 检测看是不是与a-m共线， 如果共线的话， 那么就加入结果。

### 需要用set来记录保存结果， 考虑 a-b-c-d-e共线， 然后最开始的时候， 从C点开始进行搜索， 那么顺序为 c-d-e-...， 最后转了一圈，到a的时候， a又会找到 e。那么就出现了重复。

set的默认的equals方法比较的是地址， 所以可以直接将points[i]放入set， 因为不同的点地址肯定不同， 相同的点， 他们的地址肯定相同， 也就不会再加第二次了。

### 也可以使用 used array， 但是需要注意的时候， 在寻找下一个b的时候， 不能用 used 来略过已经被加入过的点。 因为我们必须要再次搜索到起点， 形成一个完整的凸包， 如果略过已经被加入过的点话， 那么就永远形成不了凸包。

## 代码
```
/**
 * Definition for a point.
 * class Point {
 *     int x;
 *     int y;
 *     Point() { x = 0; y = 0; }
 *     Point(int a, int b) { x = a; y = b; }
 * }
 */
public class Solution {
    public List<Point> outerTrees(Point[] points) {
        Set<Point> res = new HashSet<Point>();
        if (points.length==0)   return new ArrayList<Point>(res);
        
        int firstIdx = findTheFirstPoint(points);
        res.add(points[firstIdx]);
        
        int pre = firstIdx;
        
        while (true){
            int next = (pre+1)%points.length;
            
            for (int i=0; i<points.length; i++){
                if (i==pre) continue;
                int curDirection = getDirection(points[pre], points[next], points[i]);
                if (curDirection>0 || 
                   (curDirection==0 && distance(points[i], points[pre]) > distance(points[next], points[pre]))) next = i;
            }
            
            res.add(points[next]);
            
            for (int i=0; i<points.length; i++){
                if (i==next || i==pre)  continue;
                if (getDirection(points[pre], points[next], points[i])==0)  res.add(points[i]);
            }
            
            if (next==firstIdx)  break;
            pre = next;
        }
        
        return new ArrayList<Point>(res);
    }
    
    int findTheFirstPoint(Point[] points){
        int resIdx = 0;
        
        for (int i=1; i<points.length; i++){
            if (points[i].y < points[resIdx].y){
                resIdx = i;
            }else if(points[i].y == points[resIdx].y && points[i].x < points[resIdx].x){
                resIdx = i;
            }
        }
        
        return resIdx;
    }
    
    int getDirection(Point a, Point b, Point c){
        int res = (b.y-a.y) * (c.x-b.x) - (b.x-a.x) * (c.y-b.y);
        if (res<0)  return -1;
        if (res>0)  return 1;
        return 0;
    }
    
    int distance(Point a, Point b){
        return (a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y);
    }
}
```