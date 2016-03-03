/*  Union find:
An example of union find using path compression.

After union all nodes, to calcualte the number of different roots,
need to re-run findRoot for each node, making sure tracking up to the Root.
(becasue a real root may be added at last, that previous nodes did not see).

*/

public class Solution {
    public int countComponents(int n, int[][] edges) {
        if (n<=1)   return n;
        int[] root = new int[n];
        int[] size = new int[n];
        for (int i=0; i<n; i++){
            root[i] = i;
            size[i] = 1;
        }

        for (int i=0; i<edges.length; i++){
            int cur1 = edges[i][0];
            int cur2 = edges[i][1];
            union(root, size, cur1, cur2);
        }

        // calcualate different value of roots
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i=0; i<n; i++){
            set.add(findRoot(root, i));
        }
        return set.size();

    }

    public int findRoot(int[] root, int i){
        while (i!=root[i]){
            root[i] = root[root[i]];   // path compression
            i = root[i];
        }
        return root[i];
    }

    public void union(int[] root, int[] size, int node1, int node2){
        int i = findRoot(root, node1);
        int j = findRoot(root, node2);
        if (size[i]>=size[j]){
            root[j] = i;
            size[i] =+ size[j];
        }else{
            root[i] = j;
            size[j] += size[i];
        }
    }
}
