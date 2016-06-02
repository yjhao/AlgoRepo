#317-Shortest Distance from All Buildings(BFS + 剪枝)

##题意
给一个2D grid， 在一些可以选择的地方， 选一个地方， 使他到**所有** 有标记物的的地方 的距离最短。 限制： 有障碍物。

##解题
因为是最短， 所以使用 BFS

将每一个标记物的位置放入 queue，然后分别进行 BFS

因为是到每一个的距离之后最短而且必须能够到每一个标记物。  所以选择的这个地方， 必须能够到达所有标记物。--》可以拿来剪枝。

比如说， 这是第5次BFS, 现在到了一个候选地， 但是这个地方之前一共只能到达 3 个标记物， 那么这个候选人就明显不会是答案。 这个时候， 这个候选人就不被加入 queue 中。

如果记录 之前 到过几个标记物的话， 还可以省去 visited 这个 boolean。 因为在当前层， 如果被 visited 过了的话， “已经到过的地方” 显示 已经在原基础上累加了 1。

这一个剪枝， 大大的加快了速度。

##代码
```
public class Solution {
    int[][] move = {{1,0},{-1,0},{0,1},{0,-1}};
    
    public int shortestDistance(int[][] grid) {
        if (grid==null || grid.length==0)   return -1;
        int row = grid.length; int col = grid[0].length;
        boolean found = false;
        
        int houseNum = getHouseNumber(grid);
        if (houseNum==0)    return -1;
        
        int min = Integer.MAX_VALUE;
        int[][] dist = new int[row][col];
        int[][] toHouseNum = new int[row][col];
        int curHouse = -1;
        
        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                if (grid[i][j]==2 || grid[i][j]==0) continue;
                
                curHouse++;
                boolean[][] visited = new boolean[row][col];
                LinkedList<int[]> queue = new LinkedList<int[]>();
                queue.offer(new int[] {i,j});
                visited[i][j] = true;
                int lastLevel = 1;
                int disToRoot = 1;
                int currentLevel = 0;
                
                while (!queue.isEmpty()){
                    int[] cur = queue.poll();
                    lastLevel--;
                    for (int k=0; k<4; k++){
                        int x = cur[0]+move[k][0];
                        int y = cur[1]+move[k][1];
                        if (x<0 || x>=row || y<0 || y>=col || visited[x][y] || grid[x][y]==2 || grid[x][y]==1 || toHouseNum[x][y]!=curHouse)  continue;
                        visited[x][y] = true;
                        dist[x][y] += disToRoot;
                        toHouseNum[x][y] ++;
                        queue.offer(new int[] {x,y});
                        currentLevel++;
                    }
                    if (lastLevel==0){
                        lastLevel = currentLevel;
                        currentLevel = 0; 
                        disToRoot++;
                    }
                }
            }
        }
        
        for (int i=0; i<row; i++){
            for (int j=0; j<col; j++){
                if (grid[i][j]==0 && toHouseNum[i][j]==houseNum){
                    found = true;
                    min = Math.min(min, dist[i][j]);
                }
            }
        }
        
        return found==false? -1:min;
    }
    
    public int getHouseNumber(int[][] grid){
        if (grid==null || grid[0].length==0)    return 0;
        int count = 0;
        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                if (grid[i][j]==1)  count++;
            }
        }
        return count;
    }
    
}
``` 