public class Solution {
    /*
    find the min height of a graph
    https://leetcode.com/problems/minimum-height-trees/

    use "double pointer", start the search from leaves, and marches to the center
    */


    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> leaves = new ArrayList<Integer>();

        if (n==0)    return leaves;
        if (n==1)    return Collections.singletonList(0);

        ArrayList<HashSet<Integer>> list = new ArrayList<HashSet<Integer>>();
        for (int i=0; i<n; i++)     list.add(new HashSet<Integer>());
        for (int i=0; i<edges.length; i++){
            list.get(edges[i][0]).add(edges[i][1]);
            list.get(edges[i][1]).add(edges[i][0]);
        }

        for (int i=0; i<n; i++){
            if (list.get(i).size()==1){
                leaves.add(i);
            }
        }

        int total = n;
        while (total>2){
            total -= leaves.size();
            List<Integer> tempLeaves = new ArrayList<Integer>();
            for (int cur: leaves){
                //remove edges
                int curTo = list.get(cur).iterator().next();
                list.get(curTo).remove(cur);
                if (list.get(curTo).size()==1)
                    tempLeaves.add(curTo);
            }
            leaves = tempLeaves;
        }

        return leaves;
    }
}
