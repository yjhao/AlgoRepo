#332-Reconstruct Itinerary (DFS + 唯一情况)

## 题意
根据飞机票的出发地和目的地， 重建行程单， 并要求输出字典序最小的那一个。

## 解题

标准的 backtracking + DFS， 不过细节很多。

要求返回字典序最小的， 那么就用greedy 的方法， 对每一个城市的目的地 先排序， 然后从字典序最小的开始找， 那么最后第一个出现的答案肯定就是字典序最小的。

**所以可以截枝**！！！ 当 下一层 DFS 返回 true 的时候， 直接也返回 true， 不需要一个一个目的地 的看。

## 注意的细节

##### 1. 如果把 HashSet 的内容 放入一个 for 循环中， 当删除一个 成员之后， 在其他 recursion 的地方， 会出现 “同步问题”， 所以不能这么用。

##### 2.  Backtracking 的时候， 如果 candidates 是在一个 list 中， 先删除， 然后接着 DFS， 最后又加入 list 中的时候， 一定要保证 在加入这个被删除的成员之后， 与没有删除的时候， 这个List是完全相同的。 因为 for 循环走的是 index， 先删掉第一个， 如果不注意的话， 把它加到了最后一个， 那么原来的第二个， 会被Push到第一个 ， for 循环就浏览不到。

正确的方法应该是：

```
curList.remove(i);
DFS(nextCity, sb, str+nextCity, count-1, map);
            
curList.add(i, nextCity);
而不是 curList.add(nextCity)
```


##代码
```
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
```