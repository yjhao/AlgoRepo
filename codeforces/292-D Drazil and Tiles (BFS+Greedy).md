#292-D Drazil and Tiles (BFS+Greedy)
##题意
输入一个n * m包括 ' * '和'.'的矩阵，'.'表示该位置为空。' * '表示该位置已有东西。用一个1*2的瓷砖去填满空位置，如果只有一种方法，输出该方法。如果无解或有2种以上的方法输出Not unique.

##解题
这是一个simualtion题， 对于旁边只有一个空位置的空位置 （记out-edge 为一） ， 我们把它加入一个queue中， 然后用一个 1 * 2 的瓷砖来填满这个空位置， 然后再填满之后， 再对这两个位置旁边的空位置进行更新操作， 看这些空位置的旁边的空位置是否为一个， 如果为一个的话， 加入进queue中。

当queue中完毕后， 最后再遍历一次， 看还有没有空位置没有被填满， 如果还有的话， 说明是不存在解得。

这个方法是正确的， 不过怎么能够保证 是唯一的呢？

如果 最小 out-edge 不为一的话， 一定可以形成一个cycle，那么这个cycle中的解肯定不是唯一的。

##代码
```
char[][] g;
    int[] dx = {-1,1,0,0};
    int[] dy = {0,0,-1,1};
    public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        g = new char[n][m];

        for (int i=0; i<n; i++){
            char[] cur = in.next().toCharArray();
            for (int j=0; j<m; j++){
                g[i][j] = cur[j];
            }
        }

        LinkedList<Integer> queue = new LinkedList<>();

        for (int i=0; i<n; i++){
            for (int j=0; j<m; j++){
                if (g[i][j]=='.' && count(i, j)==1){
                    queue.offer(i*m + j);
                }
            }
        }

        while (!queue.isEmpty()){
            int cur = queue.poll();
            int curx = cur/m;
            int cury = cur%m;

            for (int i=0; i<4; i++){
                int newx = curx+dx[i];
                int newy = cury+dy[i];

                if (newx<0 || newy<0 || newx>=g.length || newy>=g[0].length || g[newx][newy]!='.')  continue;

                if (i==0){
                    g[curx][cury] = 'v';
                    g[newx][newy] = '^';
                }else if (i==1){
                    g[curx][cury] = '^';
                    g[newx][newy] = 'v';
                }else if (i==2){
                    g[curx][cury] = '>';
                    g[newx][newy] = '<';
                }else{
                    g[curx][cury] = '<';
                    g[newx][newy] = '>';
                }

                update (curx, cury, queue);
                update (newx, newy, queue);
            }
        }

        for (int i=0; i<n; i++){
            for (int j=0; j<m; j++){
                if (g[i][j]=='.'){
                    out.print("Not unique");
                    return;
                }
            }
        }

        for (int i=0; i<n; i++){
            for (int j=0; j<m; j++){
                out.print(g[i][j]);
            }
            out.print("\n");
        }
    }

    int count(int x, int y){
        int sum = 0;
        for (int i = 0; i<4; i++){
            int newx = x+dx[i];
            int newy = y+dy[i];
            if (newx<0 || newy<0 || newx>=g.length || newy>=g[0].length || g[newx][newy]!='.')  continue;
            sum ++;
        }
        return sum;
    }

    void update(int x, int y, LinkedList<Integer> queue){
        for (int i=0; i<4; i++){
            int newx = x+dx[i];
            int newy = y+dy[i];
            if (newx<0 || newy<0 || newx>=g.length || newy>=g[0].length || g[newx][newy]!='.')  continue;
            int edge = count(newx, newy);
            if (edge==1)    queue.offer(newx*g[0].length + newy);
        }
    }
```