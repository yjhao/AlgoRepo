package codingSrc;

public class segmentTree{
	  public int[] tree;
	  public int n;

	  public segmentTree(int num){
	    n= 1;
	    while (n<num)  n *= 2;  // complete tree
	    tree = new int[2*n-1];

	    for (int i=0; i<n*2-1; i++){
	      tree[i] = Integer.MAX_VALUE;
	    }
	  }

	  // insert value a to node idx
	  public void insert(int idx, int a){
	    int k = n-1 + idx;   //叶子节点
	    tree[k] = a;

	    while (k>0){
	      k = (k-1)/2;
	      tree[k] = Math.min(tree[k*2+1], tree[k*2+2]);
	    }
	  }

	  // search the minimum value in range [a ,b]
	  // k : 节点编号
	  public int getMini(int a, int b, int k, int l, int r){
	    // 如果两个范围不相交， 输出MAX_VALUE
	    if (l>b || r<a) return Integer.MAX_VALUE;

	    // 如果ab完全包含了lr， 直接输出节点值
	    if (a<=l && b>=r) return tree[k];

	    // 递归的分左右返回
	    int v1 = getMini(a, b, k*2+1, l, (l+r)/2);
	    int v2 = getMini(a, b, k*2+2, (l+r)/2+1, r);
	    return Math.min(v1, v2);
	  }
}
