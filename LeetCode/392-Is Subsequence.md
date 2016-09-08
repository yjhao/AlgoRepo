#392-Is Subsequence
## 题意
两个String, s and t， check if s is substring of t, s 中的字符 的相对顺序 要与t中一致。

##解题， 方法一
用two pointers of s and t， 分别往前进， 如果 t[i] == s[j]， 那么就步进j， 否则只步进i。

这样的方法， 可以保证相对顺序能够保持， 对于 t中的 a b 字符来说， 如果没有遇到a， 那么永远也不会到 进行判断b有没有的这一步。

时间复杂度为 O(s.length+t.length)。

##代码
```
public boolean isSubsequence(String s, String t) {
        if (s.length()==0)  return true;
        int idxs = 0;
        int idxt = 0;
        while (idxt<t.length()){
            if (t.charAt(idxt)==s.charAt(idxs)) idxs++;
            if (idxs==s.length())   return true;
            idxt++;
        }
        return false;
    }
```

##解题， 方法二。
可以使用induction的方法， 对于 a b 来说。 当判断b的时候， 我们需要知道a在原来的string中的最早可能出现的位置， 然后可以判断， 在a的位置之后， 最早的b的位置。 如果在a之后， 没有b了， 那么肯定就找不到这样的substring。

对每一个字符， 都找的是“最早的位置”， 所以可以保证充要性： 如果没找到， 那么就一定不会有了。

使用Map来存储每一个char的所有的位置， 然后用二分法来进行搜索。

m = s.length, n = t.length;

时间复杂度为 O(mlogn);

##代码
```
public class Solution {
    public boolean isSubsequence(String s, String t) {
        if (s==null || s.length()==0)   return true;
        HashMap<Character, ArrayList<Integer>> map = new HashMap<Character, ArrayList<Integer>>();
        int idx = 0;
        for (char c : t.toCharArray()){
            if (!map.containsKey(c))    map.put(c, new ArrayList<Integer>());
            map.get(c).add(idx);
            idx++;
        }
        
        char first = s.charAt(0);
        if (!map.containsKey(first))    return false;
        int minPoss = map.get(first).get(0);
        
        for (int i=1; i<s.length(); i++){
            char c  = s.charAt(i);
            if (!map.containsKey(c))    return false;
            minPoss = helper(map.get(c), minPoss);
            if (minPoss==-1)    return false;
        }
        
        return true;
    }
    
    int helper(ArrayList<Integer> list, int minVal){
        int l = 0;
        int r = list.size()-1;
        while (l<r){
            int m = l+(r-l)/2;
            if (list.get(m)<=minVal)    l = m+1;
            else r = m;
        }
        if (l>=list.size() || list.get(l)<=minVal) return -1;
        return list.get(l);
    }
}
```