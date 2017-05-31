# 361-Bomb Enemy

## 题意
在一个2D格子中放入炸弹， 横竖联通， 炸弹不能穿墙，问最多能够炸掉的敌人的个数。

## 解题
假设是一个1D 的格子
 
 W 0 0 e e 0 0 e W
 
那么可以看到， 第一个 到 第四个 0， 炸掉的e， 都是相同的， 为两个w之间的e。 所以我们可以用一个变量， 当经过第一个 W 之后， 计算一下到下一个w 之间的e有多少， 然后当 visit 到这个区间中的 0 的时候， 这个变量的值就是这个0可以炸掉的个数。

所以只需要O(n)的复杂度。

因为行是连续访问的， 所以只需要一个变量就可以。

但是因为 列 是不连续访问的额， 所以需要一个数组来存一下。

## 细节， 处理边界条件
因为第一个字符不确定是什么， 所以对第一个字符， 也需要进行计算一下 本区间之内的e一共有多少。 if (i==0) 	then ...

当中途遇到w之后， 可以从w马上开始计算到下一个w的情况， 不过为了使Code更简便，联系到第一种情况， 最开始是没有"w"的， 所以可以从w之后的第一个元素开始进行判断。

（也可以想象成 第一个的左面是一个 w）， 所以普通情况可以归纳为：

```
if (c[i-1]=='w' || i==0) then 
```

## 代码
```
public class Solution {
    public int maxKilledEnemies(char[][] grid) {
        if (grid==null || grid.length==0)   return 0;
        int max = 0;
        int row = 0;
        int[] col = new int[grid[0].length];
        
        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                if (j!=0 && grid[i][j-1]=='W' || j==0){
                    row = 0;
                    int curCol = j;
                    while (curCol<grid[0].length && grid[i][curCol]!='W'){
                        if (grid[i][curCol]=='E')   row++;
                        curCol++;
                    }
                }
                
                if (i!=0 && grid[i-1][j]=='W' || i==0){
                    col[j] = 0;
                    int curRow = i;
                    while (curRow<grid.length && grid[curRow][j]!='W'){
                        if (grid[curRow][j]=='E')   col[j] ++;
                        curRow++;
                    }
                }
                
                if (grid[i][j] == '0')  max = Math.max(max, row+col[j]);
            }
        }
        
        return max;
    }
}
```


