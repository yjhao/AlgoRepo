# 533-Lonely Pixel II
## 题意
给一个只有 B W 的矩阵， 问有多少个这样的点 (r, c)：

1. 在第r行， 第c列， 都有n个B。
2. 这n行 在第c列都为B 的行 必须相等。

问一共有多少个这样的点。

## 解题
首先理清楚逻辑关系， 首先这些点所处在的行， 必须只有N个b， 所处在的列， 必须只有n个b， 而且在这一列中的n个b 所对应的 n 行，必须相等。

那么我们

1. 首先找到哪些行有n个b， 这些点只能出现在这些行中。
2. 在找到哪些列有n个b，这些点只能出现在这些列中。并且记录下来满足条件的列中， 分别是哪n个行有b。以便快速查找。 
3. 然后在Step1中的行中， 对于Step2中的每一列，看其对应的n行有b的行中， 是否满足条件：必须要相等。

其实也可以这样理解， 对于这一行， 一定有且只有另外的N-1与其相等。 如果大于n-1行的话， 这一列中， 有b的位置一定不止n个， 如果小于n-1行的话， 那么因为一共有另外的n-1行在某个列的位置都有b， 所以还有另外几行，在这个位置有b， 而不等于我们这一行， 不满足条件中的“这n行必须相等”。

所以就引来的改进， 我们可以对 row 进行grp， 相同的放到一组， 这一组的size必须要是n， 不然就肯定不满足条件。

## 代码
```
class Solution {
    public int findBlackPixel(char[][] picture, int N) {
        int row = picture.length;
        int col = picture[0].length;
        String[] strs = new String[row];
        int res = 0;
        
        for (int i=0; i<row; i++){
            StringBuilder sb = new StringBuilder();
            for (int j=0; j<col; j++)   sb.append(picture[i][j]);
            strs[i] = sb.toString();
        }
        HashSet<Integer> rowSet = new HashSet<Integer>();
        ArrayList<Integer> colList = new ArrayList<Integer>();
        
        for (int i=0; i<row; i++){
            int cnt = 0;
            for (int j=0; j<col; j++){
                if (picture[i][j] == 'B')   cnt++;
            }
            if (cnt==N) rowSet.add(i);
        }
        
        for (int j=0; j<col; j++){
            int cnt = 0;
            for (int i=0; i<row; i++){
                if (picture[i][j] == 'B')   cnt++;
            }
            if (cnt==N) colList.add(j);
        }
        
        HashMap<Integer, ArrayList<Integer>> colMap = new HashMap<Integer, ArrayList<Integer>>();
        for (Integer colIdx : colList){
            colMap.put(colIdx, new ArrayList<Integer>());
            for (int i=0; i<row; i++){
                if (picture[i][colIdx]=='B')    colMap.get(colIdx).add(i);
            }
        }
        
        for (Integer rowIdx : rowSet){
            for (int i=0; i<colList.size(); i++){
                int colIdx = colList.get(i);
                if (picture[rowIdx][colIdx]=='W')  continue;
                boolean notValid = false;
                
                for (Integer bRow : colMap.get(colIdx)){
                    if (bRow == rowIdx)  continue;
                    if (!strs[rowIdx].equals(strs[bRow])){
                        notValid = true;
                        break;
                    }
                }
                
                if (!notValid)   res++;
            }
        }
        return res;
    }
}
```

## 改进
我们可以把逻辑理得更清楚。

这n行 在第c列都为B 的行 必须相等。而且这些行的每一行当中， 有且只有N个b。

**最重要的一个结论就是， 相等的行， 如果要满足题目的话， 不仅每一行只能有N个B, 而且最重要的是， 这些相等的行的个数， 有且只能为n， 大于或者小于都不可以。 如果大于的话， 这一列的B肯定不止N个， 如果小于的话， 那么一定还有其他几行在这一列上有b， 而这几行又不等于我们的这几行。**

所以我们可以对 满足一行有n个b 的所有行， 用 String(row)来进行group， 然后扔掉group size不为 n 的group。

但是即使满足这样的条件， 也不能说一定满足在某一列一定只有 n 个b， 应为这些行在这一列组成的只是一个subset， 还有一些其他的行没有被包括进来。 所以还得保证， 在他们出现b的这一列， 一共也就只有n个b。

那么如果以上的条件都满足的话， 在这一列上， 这n行的这n个点就同时满足条件， 可以一次全部加入最后的答案中，而不用一个一个检验。

然后再对下一个出现条件的列来进行判断， 是否有且仅有n个b。 

##  代码
```
class Solution {
    public int findBlackPixel(char[][] picture, int N) {
        int row = picture.length;
        int col = picture[0].length;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int[] colCnt = new int[col];
        
        for (int i=0; i<row; i++){
            addValidRowToMap(map, i, picture, N);
            for (int j=0; j<col; j++){
                if (picture[i][j]=='B') colCnt[j]++;
            }
        }
        
        int res = 0;
        
        for (String str : map.keySet()){
            if (map.get(str)!=N)    continue;
            for (int i=0; i<col; i++){
                if (str.charAt(i)=='B' && colCnt[i]==N) res += N;
            }
        }
        
        return res;
    }
    
    void addValidRowToMap(HashMap<String, Integer> map, int idx, char[][] pics, int N){
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        for (int i=0; i<pics[0].length; i++){
            sb.append(pics[idx][i]);
            if (pics[idx][i]=='B') cnt++;
        }
        if (cnt!=N) return;
        String str = sb.toString();
        map.put(str, map.getOrDefault(str, 0)+1);
        return;
    }
}
```