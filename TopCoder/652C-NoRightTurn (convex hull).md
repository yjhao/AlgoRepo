#652C-NoRightTurn (convex hull)

##题意
给一些二维的点， 保证没有三点共线的。 问能不能将所有点连接起来， 保证： 每一个线段不穿过另外一个线段， 而且从第一个点到最后一个点， 没有右转。 也就是说能不能将所有点， 逆时针的连接起来。

##解题
我们从外层开始画逆时针凸包， 然后一层层的往里面旋进去， 所以最后肯定能够达到目的。 因为每一个点， 我们现在选择的都是， 当前最外层而且最逆时针的一个点。

第一个点， 我们选择最左面的一个点， 如果有多个点都是最左面， 这个时候， 我们选择最下面的一个点。

然后我们要找第二个点， 第二个点的定义是， 连接了第二个点， 和未来的三个点之后， 1-2-3这个折现一定是一个逆时针。

我们可以用 Dot product 根据斜率来判断这三个点组成的线段是不是逆时针。

假设I是当前的第二点，m是之前找到的第一个点， n是未来的第三个点，  如果发现 （m, i, n) 不是逆时针， 说明 （m, n, i)是逆时针， 那么我们就将 I 设为 第二个点。

这样的逻辑， 最后一个被设置为 “第二个点” 的点， 一定是当前最 逆时针 的点。

我们需要记录下， 哪些点已经被使用过了 （外层）， 以后就不需要再访问。

重复这样的逻辑， 直到所有的点都被找到。

##代码
```
public class NoRightTurnDiv2
{
	public int[] findPath(int[] x, int[] y)
	{
		int len = x.length;
		int first = 0;
		
		// find the first point
		for (int i=1; i<len; i++){
			if (x[i]<x[first])	first = i;
			else if (x[i] == x[first]){
				if (y[i]<y[first])	first = i;
			}
		}
		
		boolean[] used = new boolean[len];
		used[first] = true;
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(first);
		int p = first;
		int q = 0;
		
		while (list.size()<len){
			for (int i=0; i<len; i++){
				if (!used[i]){
					q = i;
					used[q] = true;
					break;
				}
			}
			
			for (int i=0; i<len; i++){
				if (i==q || used[i])	continue;
				if (!counterClockwise(x[p], y[p], x[i], y[i], x[q], y[q])){
					used[q] = false;
					q = i;
					used[q] = true;
				}
			}
			
			list.add(q);
			p = q;
		}
		
		int[] res = new int[len];
		int i = 0;
		for (int val : list)	res[i++] = val;
		return res;
	}
	
	boolean counterClockwise(int x1, int y1, int x2, int y2, int x3, int y3){
		if ((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1)>0)	return true;
		else return false;
	}
	

}
```