/*
You want to build a house on an empty land which reaches all buildings in the shortest amount of distance. You can only move up, down, left and right. You are given a 2D grid of values 0, 1 or 2, where:

Each 0 marks an empty land which you can pass by freely.
Each 1 marks a building which you cannot pass through.
Each 2 marks an obstacle which you cannot pass through.
For example, given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2):

1 - 0 - 2 - 0 - 1
|   |   |   |   |
0 - 0 - 0 - 0 - 0
|   |   |   |   |
0 - 0 - 1 - 0 - 0
The point (1,2) is an ideal empty land to build a house, as the total travel distance of 3+3+1=7 is minimal. So return 7.
*/

//==========================================
/* 1st solution, serach from each empty node --> very slow, due to the fact that empty space are much
more than house

2nd solution, search from each house --> must faster.

And Key optimization : we do not go into a land, if it is not accessible by at least
one of previous buildings:
After the entire search of each house, we eliminate the empty spot that can't reach the current house.
For the future search, we will bypass those houses.
*/


public class Solution {
    int[][] move = {{1,0},{-1,0},{0,1},{0,-1}};

    // solution 2
    public int shortestDistance(int[][] grid) {
        if (grid==null || grid.length==0)   return -1;
        int row = grid.length; int col = grid[0].length;
        boolean found = false;

        int houseNum = getHouseNumber(grid);
        if (houseNum==0)    return -1;

        int min = Integer.MAX_VALUE;
        int[][] dist = new int[row][col];
        int[][] toHouseNum = new int[row][col];
        int curHouse = -1;  // key optimization

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

    // solution 1 : search from each empty space
    public int shortestDistance1(int[][] grid) {
        if (grid==null || grid.length==0)   return -1;
        int houseNum = getHouseNumber(grid);
        if (houseNum==0)    return -1;

        int min = Integer.MAX_VALUE;
        boolean found = false;

        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                if (grid[i][j]==1 || grid[i][j]==2) continue;
                int curVal = BFS(grid, houseNum, i, j);
                if (curVal!=-1){
                    found = true;
                    min = Math.min(min, curVal);
                }
            }
        }

        return found==false? -1:min;
    }


    public int BFS(int[][] grid, int houseNum, int curX, int curY){
        int row = grid.length; int col = grid[0].length;
        boolean[][] visited = new boolean[row][col];

        LinkedList<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[] {curX, curY});
        visited[curX][curY]=true;
        int lastLevel = 1;
        int dist = 1;
        int total = 0;
        int currentLevel = 0;

        while (!queue.isEmpty()){
            int[] cur = queue.poll();
            lastLevel--;
            for (int i=0; i<4; i++){
                int x = cur[0] + move[i][0];
                int y = cur[1] + move[i][1];
                if (x<0 || x>=row || y<0 || y>=col || visited[x][y] || grid[x][y]==2) continue;
                visited[x][y] = true;
                if (grid[x][y]==1){
                    total += dist;
                    houseNum--;
                }else{
                    queue.offer(new int[] {x,y});
                    currentLevel++;
                }
            }
            if (lastLevel==0){
               lastLevel = currentLevel;
               currentLevel = 0;
               dist++;
            }
        }

        return houseNum!=0 ? -1:total;
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
