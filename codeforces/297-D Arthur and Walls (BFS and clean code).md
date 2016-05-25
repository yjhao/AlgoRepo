#297-D Arthur and Walls (BFS and clean code)
##题意
给你一个矩阵 只含有 * 和 ‘.’ ，问你使得所有的'.' 的联通块都是矩形要删除最少的 '*'， 问你要删多少个。

##解题
对于当前的点(i,j)并且此点为 * 来说，若存在包含它的2*2正方形中除了它自己外，另外三个点都是”.”，那么这个点就必须要变成”.”。 否则就不会形成长方形。

由于去掉这个点之后会对周围的8个点造成影响，所以可以用BFS去搜。

去掉一个点之后， 搜索周围的八个点， 看有没有合理的加入queue的。

##clean code 细节
用两个方法， 来验证一个点是不是合理 （judge method)， 和这个点是不是 在范围内 与 为 "." （check method）

##代码
```
int jx[]={0,0,1,-1,1,-1,1,-1};
    int jy[]={1,-1,0,0,1,-1,-1,1};

    public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        char[][] s = new char[n][m];
        for (int i=0; i<n; i++){
            char[] line = in.next().toCharArray();
            for (int j=0; j<m; j++){
                s[i][j] = line[j];
            }
        }

        LinkedList<Integer> queue = new LinkedList<>();

        for (int i=0; i<n; i++){
            for (int j=0; j<m; j++){
                if (judge(s, i, j)){
                    queue.offer(i*m + j);
                }
            }
        }

        while(!queue.isEmpty()){
            int cur = queue.pop();
            int x = cur/m;
            int y = cur%m;
            if (s[x][y]=='.')   continue;
            s[x][y] = '.';
            for (int i=0; i<8; i++){
                int a = x+jx[i];
                int b = y+jy[i];
                if (a>=0 && a<n && b>=0 && b<m && judge(s, a, b)){
                    queue.offer(a*m + b);
                }
            }
        }

        for (int i=0; i<n; i++){
            for (int j=0; j<m; j++){
                out.print(s[i][j]);
            }
            out.print("\n");
        }
    }
    
    public boolean judge(char[][] s, int x, int y){
        if (s[x][y]!='*')   return false;

        if(check(s,x+1,y)&&check(s,x+1,y+1)&&check(s,x,y+1)) return true;
        if(check(s,x+1,y)&&check(s,x+1,y-1)&&check(s,x,y-1)) return true;
        if(check(s,x-1,y)&&check(s,x-1,y+1)&&check(s,x,y+1)) return true;
        if(check(s,x,y-1)&&check(s,x-1,y-1)&&check(s,x-1,y)) return true;
        return false;
    }

    public boolean check(char[][] s, int x, int y){
        if (x<0 || y<0 || x>=s.length || y>=s[0].length)  return false;
        if (s[x][y]=='*')   return false;
        return true;
    }
``` 