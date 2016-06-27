#288-D Tanya and Password (Euler Path 欧拉路径)
##题意
給 n 個長度為 3 的由大小寫字母及數字組成的字串，問能否找到長度為 n+2 的字串使得此字串所有長度為 3 的子字串恰是所給定的 n 個字串，若能找到請輸出解

##解题
经典的欧拉路径题。 解法就是把所有兩個字母組成的字串視為點，並把所有題目給定字串 c1c2c3 想像成由點 c1c2 指向 c2c3 的有向邊，可以發現在這張圖上找到尤拉路徑就能找到一組解。

##欧拉路径
“一笔画”

验证是不是欧拉路径：

想想从一点到另外一点。所以中间的所有点， In-edge = out-edge， 或者说， edge的数目是偶数。

如果不是环的话， euler path， 那么edge数目是奇数的 Node 为2个， 一头一尾。 

如果是环的话， euler circuit， 那么 edge 数目是奇数的 node 个数为0。

##如何得到 euler path
Fleury’s Algorithm

http://www.geeksforgeeks.org/fleurys-algorithm-for-printing-eulerian-path/

关键就是 “留后路， don't burn bridge”

首先， 从一个 edge 数目为奇数的 node 出发， 然后依次 traverse edges。

如何判断一个 edge 是 valid path 呢？

两个条件满足一个就可以：

1. 这是唯一的 edge 了。
2. 先不删除这条 edge, 然后 DFS， 看能到达多少个 node， 记为 count 1。 然后删除条这个 edge, 然后 DFS， 看能到达多少个 node， 记为 Count 2。 如果 Count 1 > count 2， 那么说明 这个Edge 是一个 bridge， 不能走过去 ， 不然就再也回不来了！