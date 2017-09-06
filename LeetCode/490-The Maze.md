# 490-The Maze
## 题意
给一个棋盘， 有一些是填满了的， 有一些是空的。然后在起点出放一个球， 给一个一个终点， 问从起点能不能够达到终点。 规则是一旦一个运动开始之后， 只有在碰到墙壁之后才能改变运动方向。

## 解题
首先我们可以发现， 在“运动的中途的某一点”， 既然在它周围没有墙壁， 所以运动是不可能停止的， 运动只可能停止在周围有墙壁的点， 然后才可以更改运动方向。 所以我们可以构建一个graph， 每个Node就是这些周围有墙壁的点。 中间的运动可以用 while 来略过， 直到找到了下一个node。

## 细节
#### 使用while的时候， 不要使用

```
while (true){
  do ONE step, 
  then check;
}
```
这样效率很低，因为check 对每一个Step都要运行。而应该使用 

```
while (condition) {
  change condition
}
```
这样的效率比前者高很多。

#### 其次，
再计算下一个Node的时候， 当前node的条件也是满足的： 周围有墙壁， 所以要排除当前node。

有很多方法可以排除当前node作为下一个Node， 可以比较坐标， 也可以用 是否曾经 visit 过来比较。 用是否visit来比较， code比较Clean。

## 代码
```
class Solution {
    int[] dx = {0,0,1,-1};
    int[] dy = {1,-1,0,0};
    
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        
        return valid(maze, visited, start[0], start[1], destination);
    }
    
    boolean valid(int[][] maze, boolean[][] visited, int x, int y, int[] destination){
        if (x==destination[0] && y==destination[1]) return true;        
        visited[x][y] = true;
        
        for (int i=0; i<4; i++){
            int curx = x;
            int cury = y;
            
            while (curx>=0 && cury>=0 && curx<maze.length && cury<maze[0].length && maze[curx][cury]==0){
                curx += dx[i];
                cury += dy[i];
            }
            
            curx -= dx[i];
            cury -= dy[i];
            
            if (visited[curx][cury])    continue;
            
            if (valid(maze, visited, curx, cury, destination))   return true;
        }
        
        return false;
    }
}
```