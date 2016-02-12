/*
Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], reconstruct the itinerary in order. All of the tickets belong to a man who departs from JFK. Thus, the itinerary must begin with JFK.

Note:
If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string. For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
All airports are represented by three capital letters (IATA code).
You may assume all tickets form at least one valid itinerary.
Example 1:
tickets = [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
Return ["JFK", "MUC", "LHR", "SFO", "SJC"].
Example 2:
tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
Return ["JFK","ATL","JFK","SFO","ATL","SFO"].
Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"]. But it is larger in lexical order.
*/


//===========================================================
/* solution: DFS + greedy

Greedy is done by sorting the destinations for each start city, since the resutls are sorted
in lexical order.  for example, list has [0,1,2], if results of [0] is true, then no need to go in
1 and 2.

DFS:
search from JFK, one by one, backtracking:
add current to res, remove current dest from list, DFS, remove current from res, add current dest to list

DFS TRICK:
IF use DFS to determine if a path exist or to get the path, USING BOOL AS THE RETURN TYPE.
*/

public class Solution {
    public List<String> findItinerary(String[][] tickets) {
        List<String> res = new ArrayList<String>();
        if (tickets==null || tickets.length==0)  return res;
        int totalCity = tickets.length+1;

        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for (int i=0; i<tickets.length; i++){
            String from = tickets[i][0]; String to = tickets[i][1];
            if (map.containsKey(from)){
                ArrayList<String> curList = map.get(from);
                addToList(curList, to);
                //map.put(from, curList);    // need to order the "destination" in lexical order
            } else{
                ArrayList<String> curList = new ArrayList<String>();
                curList.add(to);
                map.put(from, curList);
            }
        }

        res.add("JFK");

        DFS("JFK", map, res, totalCity, 1);
        return res;
    }

    // to determine if a path exists, use Bool as return type
    public boolean DFS(String current, HashMap<String, ArrayList<String>> map, List<String> res, int total, int num){
        if (num>=total)     return true;

        if(!map.containsKey(current) || map.get(current).size() == 0){
            return false;
        }

        ArrayList<String> curDest = map.get(current);

        //while (i<curDest.size()){
        for (int i=0; i<curDest.size(); i++){
            String dest = curDest.remove(i);

            res.add(dest);

            if (DFS(dest, map, res, total, num+1)){
                return true;
            }

            addToList(curDest, dest);
            res.remove(res.size()-1);
        }
        return false;
    }

    public void addToList(ArrayList<String> list, String item){
        int len = list.size();
        int i = 0;
        while (i<len){
            if (list.get(i).compareTo(item) >= 0){
                list.add(i, item);
                return;
            }
            i++;
        }
        list.add(item);
        return;
    }
}
