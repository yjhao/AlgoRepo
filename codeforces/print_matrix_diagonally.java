/**
 * Solution:
 * 1). key pattern: for each diagonal strip, the length of each strip is known, such as 1,2,3,4,3,2,1
   2). For each strip, we know the starting point (x,y), x=0 or y=0
   3). For \ or / strip, we know how x and y will change, for exmaple, with \, x and y will incerase
       with /, x will decrease, and y will increase.

   Method 1 and 3 are based on this idea,  method 1 use the strip length to eliminate the expensive while loop.
 */
public class matrix_daigonal {
    public static void diagonal(int[][] mat){
        int N = mat.length;

        // method 1, based on the idea of method 3, but eliminate the while loop

        // 打印右上部分
        int size = 0;
        for (int i = N - 1; i >= 0; i--) {
            int row = 0;
            int col = i;
            size ++;
            for (int k=0; k<size; k++){
                System.out.printf("%4d ", mat[row][col]);
                row += 1;
                col += 1;
            }
            System.out.printf("\n");
        }

        // 打印左下部分
        size = N;
        for (int i = 1; i < N; i++) {
            int row = i;
            int col = 0;
            size -- ;
            for (int k=size; k>0; k--){
                System.out.printf("%4d ", mat[row][col]);
                row += 1;
                col += 1;
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");


        // 打印左上部分
        size = 0;
        for (int i=0; i<N; i++){
            int row = i;
            int col = 0;
            size ++;
            for (int k=0; k<size; k++){
                System.out.printf("%4d ", mat[row][col]);
                row -= 1;
                col += 1;
            }
            System.out.printf("\n");
        }

        // 打印右下部分
        size = N;
        for (int i=1; i<N; i++){
            int row = N-1;
            int col = i;
            size --;
            for (int k=size; k>0; k--){
                System.out.printf("%4d ", mat[row][col]);
                row -= 1;
                col += 1;
            }
            System.out.printf("\n");
        }

        // -------------------------------------------------------------------------------------
        // method 2, very quick

        // / diagonal
        for (int slice = 0; slice < N*2-1; ++slice) {
            int z = slice < N ? 0 : slice - N + 1;
            for (int j = z; j <= slice - z; ++j) {
                int c2=(N-1)-(slice-j);
                System.out.printf("%4d ", mat[j][c2]);
            }
            System.out.printf("\n");
        }

        // \ diagonal
        System.out.printf("\n");
        for (int slice = 0; slice < N*2-1; ++slice) {
            int z = slice < N ? 0 : slice - N + 1;
            for (int j = z; j <= slice - z; ++j) {
                int c2=slice-j;
                System.out.printf("%4d ", mat[j][c2]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");

        // -----------------------------------------------------------------------------------------
        // method 3, slow, but easy
        // 打印右上部分
        int size = 0;
        for (int i = N - 1; i >= 0; i--) {
            int row = 0;
            int col = i;
            size ++;
            for (int k=0; k<size; k++){
                System.out.printf("%4d ", mat[row][col]);
                row += 1;
                col += 1;
            }
            System.out.printf("\n");
        }

        // 打印左下部分
        size = N;
        for (int i = 1; i < N; i++) {
            int row = i;
            int col = 0;
            size -- ;
            for (int k=size; k>0; k--){
                System.out.printf("%4d ", mat[row][col]);
                row += 1;
                col += 1;
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");

        // 打印左上部分
        for (int i=0; i<N; i++){
            int row = i;
            int col = 0;
            while (row>=0 && col<N){
                System.out.printf("%4d ", mat[row][col]);
                row--;
                col++;
            }
            System.out.printf("\n");
        }

        // 打印右下部分
        for (int i=1; i<N; i++){
            int row = N-1;
            int col = i;
            while (row>=0 && col<N) {
                System.out.printf("%4d ", mat[row][col]);
                row--;
                col++;
            }
            System.out.printf("\n");
        }

    }

    public static void printMat(int[][] mat){
        int row = mat.length;
        int col = mat[0].length;

        for (int i=0; i<row; i++){
            for (int j=0; j<col; j++){
                System.out.printf("%4d ", mat[i][j]);
            }
            System.out.println("");
        }
        System.out.println("----------");
    }


    public static void main(String[] arg) {
        int[][] mat = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        printMat(mat);
        diagonal(mat);
    }

}
