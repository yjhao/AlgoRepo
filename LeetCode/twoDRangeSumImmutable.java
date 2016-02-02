class NumMatrix {
    int[][] sum;
    public NumMatrix(int[][] matrix) {
        if (matrix.length==0)   return;
        int row = matrix.length; int col = matrix[0].length;
        sum =  new int[row][col];
        sum[0][0] = matrix[0][0];
        for (int i=1; i<col; i++){
            sum[0][i] = matrix[0][i] + sum[0][i-1];
        }
        for (int i=1; i<row; i++){
            sum[i][0] = matrix[i][0] + sum[i-1][0];
        }

        for (int i=1; i<row; i++){
            for (int j=1; j<col; j++){
                sum[i][j] = sum[i-1][j] + sum[i][j-1] - sum[i-1][j-1] + matrix[i][j];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int res = 0;
        res = sum[row2][col2];

        if (col1>0)
            res -= sum[row2][col1-1];
        if (row1>0)
            res -= sum[row1-1][col2];
        if (row1>0 && col1>0)
            res = res + sum[row1-1][col1-1];

        return res;
    }
}

public class twoDRangeSumImmutable {
  public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] cur = {{3,0,1,4,2},{5,6,3,2,1},{1,2,0,1,5},{4,1,0,1,7},{1,0,3,0,5}};
		NumMatrix matrix = new NumMatrix(cur);
		System.out.println(matrix.sumRegion(2,1,4,3));

	}
}
