# 498-Diagonal Traverse
## 题意
给一个M*n的矩阵， 然后蛇形z字形的打印里面的数字。

## 解题一
这个题是对角线打印的升级版本。
首先我们可以得到一共有多少个对角线， 然后对于每个对角线， 可以根据他的序列号， 判断他是正对角线还是反对角线。 对于每一个对角线， 我们可以找到他的起点 （必然是边界上的一点）， 然后根据正反对角线的规律， 依次打印出来。

## 代码
```
public class Solution {
    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix.length==0)   return new int[0];
        int row = matrix.length;
        int col = matrix[0].length;
        int[] res = new int[row*col];
        
        int totalLine = col + row - 1;
        int idx = 0;
        
        for(int i=0; i<totalLine; i++){
            int curRow = 0;
            int curCol = 0;
            if (i%2==0){
                if (i < row){   // starts from left boundary// starts from left boundary
                    curRow = i;
                    curCol = 0;
                }else{
                    curRow = row-1;
                    curCol = i-row+1;
                }
                while (curRow>=0 && curCol<col){
                    res[idx++] = matrix[curRow--][curCol++];
                }
            }else{
                if (i < col){
                    curRow = 0;
                    curCol = i;
                }else{
                    curRow = i-col+1;
                    curCol = col-1;
                }
                while (curRow<row && curCol>=0){
                    res[idx++] = matrix[curRow++][curCol--];
                }
            }
        }
        
        return res;
    }
}
```

## 解题二
这一个方法有点需要技巧， 首先是几个观察：

1. 当对角线回头的时候， dx dy 就会反向
2. 对角线回头， 一定是出了边界。
3. 在前半部分出边界之后（出了上边界， 左边界）， 只需要对 X 或者 y 坐标稍微的调整一下， 就可以找到下一个对角线的起点。
4. 在后半部分出边界之后 （出了下边界， 右边界）， 为了找到下一个对角线的起点， 对坐标的微调与前半部分不太一样。
5. 需要主要的是， 稍加实验便可得知，最长的那一根对角线， 得归纳为“后半部分”， 所以对“后半部分”微调的代码， 得在“前半部分” 之前。 这样的话， 就可以bypass掉“前半部分”的微调。

通过不断的微调和行进dx, dy， 我们一一遍历这个矩阵， 然后遍历的顺序， 就是打印的顺序。

## 代码
```
public class Solution {
    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix.length==0)   return new int[0];
        int row = matrix.length;
        int col = matrix[0].length;
        int[] res = new int[row*col];
        
        int curRow = 0;
        int curCol = 0;
        int d = 1;
        
        for (int i=0; i<row*col; i++){
            res[i] = matrix[curRow][curCol];
            curRow -= d;
            curCol += d;
            
            if (curRow>=row){
                curRow = row-1;
                curCol += 2;
                d  = -d;
            }
            
            if (curCol>=col){
                curRow += 2;
                curCol = col-1;
                d = -d;
            }
            
            if (curRow<0){
                curRow = 0;
                d = -d;
            }
            
            if (curCol<0){
                curCol = 0;
                d = -d;
            }
        }
        return res;
    }
}
```


