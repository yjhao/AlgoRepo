# 499-The Maze III
## 题意
跟Maze很像， 一个球只能在碰到墙壁之后才能换方向。 然后有一个洞， 问这个球能不能掉到这个洞里， 如果可以掉进去的话， 求最短距离下的路径方向。如果有两条最短路劲， 求出字典序最小的路径。

## 解题
同样的， 把碰到墙壁时候的点的位置看为一个 graph node。 但是因为我们要求的是最短路， 所以我们需要保持每个点的”当前最短距离“， 并且随时更新。 

又因为求得是字典序最小的路径， 所以还需要保存每个点的当前最小距离下的字典序最小路径， 并且也随时更新。

我们可以用 bfs 和 dfs 来解决。

## DFS
使用 DFS 的话， 需要注意的是。

1. 要用visite来记录”当前PATH“， 我们不能访问当前path下的之前的点， 但是可以访问不是当前path下的其他的已经被访问过得点。 所以是一个 backtracking， 退出dfs的时候， 需要重现现场。
2. 在访问到一个点的时候， 比较当前距离和这个点之前的最短距离的关系， 如果当前距离已经大于， 或者在等于的状态下， 字典序更大， 那么就没有必要再次访问这个点了。
3. 访问到一个点之后， 如果距离更小， 或者字典序更小， 需要及时更新。
4. 在x!=hole[0] || y!=hole[1] 的情况下， 这个球还可以继续滚动。

## 代码
```
class Solution {
    int[] dx = {0,0,1,-1};
    int[] dy = {1,-1,0,0};
    String[] dir = {"r", "l", "d", "u"};
    String res = "";
    int min = Integer.MAX_VALUE;
    
    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        int[][] eachDis = new int[maze.length][maze[0].length];
        for (int i=0; i<maze.length; i++){
            for (int j=0; j<maze[0].length; j++){
                eachDis[i][j] = Integer.MAX_VALUE;
            }
        }

        dfs(maze, visited, ball[0], ball[1], hole, "", 0, eachDis);
        if (min == Integer.MAX_VALUE)    return "impossible";
        return res;
    }
    
    void dfs(int[][] maze, boolean[][] visited, int x, int y, int[] hole, String path, int dis, int[][] eachDis){
        if (visited[x][y] || dis>=min || dis>eachDis[x][y])   return; // why dis>=min, not dis>min? == is for equal distance, but smaller order path
        visited[x][y] = true;
        eachDis[x][y] = dis;
        
        for (int i=0; i<4; i++){
            int curDis = dis;
            int curx = x;
            int cury = y;
            
            while (curx+dx[i]>=0 && curx+dx[i]<maze.length && cury+dy[i]>=0 && cury+dy[i]<maze[0].length && 
                  maze[curx+dx[i]][cury+dy[i]]!=1 && (hole[0] != curx || hole[1] != cury)){
                curx += dx[i];
                cury += dy[i];
                curDis++;
            }
            
            if (hole[0] == curx && hole[1] == cury){
                String curStr = path + dir[i];
                if (curDis < min){
                    min = curDis;
                    res  = curStr;
                }else if (curDis == min){
                    res  = curStr.compareTo(res)>0 ? res : curStr;
                }
                visited[x][y] = false;
                return;
            }
            
            if (visited[curx][cury])    continue;
            
            dfs(maze, visited, curx, cury, hole, path+dir[i], curDis, eachDis);
        }
        visited[x][y] = false;
    }
}
```

## BFS
BFS 跟 DFS 很相似， 我们使用 priorityQueue， 每次取出之前的点中， 距离最小或者 距离相同下， 字典序最小的点进行访问。

所以我们可以将point设为一个class， 并且 implements Comparable<point> and compareTo method, 那么我们就可以之后就使用 compareTo来进行顺序比较了。

有几个细节需要注意：

1. 在当问到某点之后， 如果发现这个点之前的 compareTo 是**小于等于**当前状态的， 那么就没有必要再次访问这个点了， 也就不需要将这个点加入pq中， 反之则加入pq中。
2. 当拿出一个pq中的点的时候， 虽然之前我们把点加入到pq的时候， 保证的是只加入更小的点， 所以拿出来的时候， 有可能拿出来的是以前的最优点， 但是现在已经不是了。所以我们需要做两件事情。

	a. 第一件事情，有可能拿出来的点， 是一个之前的更大的状态下的点， 所以这个时候，我们需要和之前最优状态下的点进行比较，如果发现， 他大于最优状态， 那么我们就没有必要进行访问了， 可以直接continue；
	
	b.第二件事情， 如果发现， 当前拿出来的点， 通过比较之后， 是要小于之前的最优状态， 那么我们需要更新最优状态。
	
## 代码
```
class Solution {
    int[] dx = {0,0,1,-1};
    int[] dy = {1,-1,0,0};
    String[] dir = {"r", "l", "d", "u"};
    
    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        point[][] grid = new point[maze.length][maze[0].length];
        for (int i=0; i<maze.length; i++){
            for (int j=0; j<maze[0].length; j++){
                grid[i][j] = new point(i, j);
            }
        }
        
        PriorityQueue<point> pq = new PriorityQueue<point>();
        pq.offer(new point(ball[0], ball[1], 0, ""));
        
        while (!pq.isEmpty()){
            point cur = pq.poll();
            if (grid[cur.x][cur.y].compareTo(cur)<=0)   continue;
            grid[cur.x][cur.y] = cur;
        
            
            for (int i=0; i<4; i++){
                int dis = cur.l;
                int x = cur.x;
                int y = cur.y;
                
                while (x+dx[i]>=0 && y+dy[i]>=0 && x+dx[i]<maze.length && y+dy[i]<maze[0].length && maze[x+dx[i]][y+dy[i]]!=1
                      && (x!=hole[0] || y!=hole[1])){
                    x += dx[i];
                    y += dy[i];
                    dis++;
                }
                
                if (x==hole[0] && y==hole[1]){
                    if (dis<grid[x][y].l){
                        grid[x][y] = new point(x, y, dis, cur.s+dir[i]);
                    }else if (dis==grid[x][y].l){
                        if ((cur.s+dir[i]).compareTo(grid[x][y].s)<0){
                            grid[x][y] = new point(x, y, dis, cur.s+dir[i]);
                        }
                    }
                    continue;
                }
                
                point p = new point(x, y, dis, cur.s+dir[i]);
                
                if (grid[x][y].compareTo(p)<=0) continue;
                pq.offer(new point(x, y, dis, cur.s+dir[i]));
            }
        }
                 
        if (grid[hole[0]][hole[1]].l!=Integer.MAX_VALUE)    return grid[hole[0]][hole[1]].s;
        return "impossible";
    }
}

class point implements Comparable<point>{
    int x;
    int y;
    int l;
    String s;
    
    public point(int x, int y){
        this.x = x;
        this.y = y;
        this.s = "";
        this.l = Integer.MAX_VALUE;
    }
    
    public point(int x, int y, int l, String s){
        this.x = x;
        this.y = y;
        this.s = s;
        this.l = l;
    }
    
    public int compareTo(point b){
        if (b.l==l){
            return s.compareTo(b.s);
        }
        return l - b.l;
    }
}
```
