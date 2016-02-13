public class Solution {
    int[] dx = {-1,1,0,0};
    int[] dy = {0,0,1,-1};

    /* solution 1: DFS,
    it works, but very slow, because it repetively process a lot of path
    more than 1 time. time complexity O(n^2 * m^2). need to use memorization.
    */

    /* solution 2: DFS + memorization
    memorization: the recursive function MUST has return type.
                  (line 36 and 48 explain why better use a return type -> directely return the memo value)

                  the work flow of memo is like : 1_2_3_4_5_6_7_8
                                                  9_10_11_5
                  when 2nd time it encounters 5, because first time we processed
                  5678, so don't need to process 5678 again, just use the memorized data.

                  Time complexity O(mn)
    */

    public int longestIncreasingPath2(int[][] matrix) {
        if (matrix==null || matrix.length==0)   return 0;
        int row = matrix.length; int col = matrix[0].length;
        int res = 1;
        int[][] memo = new int[row][col];
        for (int i=0; i<row; i++){
            for (int j=0; j<col; j++){
                res = Math.max(res, helper(matrix, memo, i, j));
            }
        }
        return res;
    }

    public int helper(int[][] matrix, int[][] memo, int i, int j){
        if (memo[i][j]>0)   return memo[i][j];

        int downpath_max = 0;

        for (int k=0; k<4; k++){
            int x = i+dx[k];
            int y = j+dy[k];

            if (x<0 || x>=matrix.length || y<0 || y>=matrix[0].length || matrix[x][y]<=matrix[i][j]){
                continue;
            }

            downpath_max = Math.max(downpath_max, helper(matrix, memo, x, y));
        }

        int res = 1 + downpath_max;
        memo[i][j] = res;
        return res;
    }


    public int longestIncreasingPath1(int[][] matrix) {
        if (matrix==null || matrix.length==0)   return 0;
        int row = matrix.length; int col = matrix[0].length;
        boolean[][] visited = new boolean[row][col];
        int[] max = {1};

        int[][] memo = new int[row*col][2];
        for (int i=0; i<row*col; i++){
            for (int j=0; j<2; j++){
                memo[i][j] = -1;
            }
        }

        DFS(matrix, visited, max, 1, 1, 0, 0);
        return max[0];
    }

    public void DFS(int[][] matrix, boolean[][] visited, int[] max, int decrease, int increase, int i, int j){
        if (visited[i][j])   return;

        visited[i][j] = true;
        max[0] = Math.max(max[0], Math.max(increase, decrease));

        for (int s=0; s<4; s++){
            int x = i+dx[s];
            int y = j+dy[s];
            if (x<0 || x>=matrix.length || y<0 || y>=matrix[0].length)  continue;

            if (matrix[x][y] > matrix[i][j]){
                DFS(matrix, visited, max, 1, increase+1, x, y);
            }else if(matrix[x][y] < matrix[i][j]){
                DFS(matrix, visited, max, decrease+1, 1, x, y);
            }else{
                DFS(matrix, visited, max, 1, 1, x, y);
            }
        }
        visited[i][j] = false;
    }
}
