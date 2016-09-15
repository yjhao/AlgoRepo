#395-Longest Substring with At Least K Repeating Characters

##题意
知道一个最长的 substring， 其中的所有字符的个数都至少为K。

##解题
首先我们对string s中的每一个字符来判断， 个数是否至少为k， 如果是的话， 直接返回s.length。

如果不是的话， 那么答案中就一定不能包含 没有满足条件的 char c。 那么我们就把原string分成几段， 以c出现过的位置来分， 对划分出来的每一段， 重复此过程来进行判断。

如果一个String的长度小于k了， 那么直接返回0。

时间复杂度为 O(26n)， 因为最多只可能 recursion 26层， 每层最多耗时n。

##代码
```
public class Solution {
    public int longestSubstring(String s, int k) {
        return helper(s, 0, s.length()-1, k);
    }
    
    int helper(String s, int start, int end, int k){
        if (end-start+1<k)  return 0;
        HashMap<Character, ArrayList<Integer>> map = new HashMap<Character, ArrayList<Integer>>();
        for (int i=start; i<=end; i++){
            if (!map.containsKey(s.charAt(i)))  map.put(s.charAt(i), new ArrayList<Integer>());
            map.get(s.charAt(i)).add(i);
        }
        char toRemove = '-';
        boolean flag = false;
        for (char c = 'a'; c<='z'; c++){
            if (!map.containsKey(c) || map.get(c).size()>=k)  continue;
            flag = true;
            toRemove = c;
            break;
        }
        
        if (!flag)  return end-start+1;
        int max = 0;
        int idx = 0;
        ArrayList<Integer> list = map.get(toRemove);
        while (idx<list.size()){
            max = Math.max(max, helper(s, start,list.get(idx)-1, k));
            start = list.get(idx)+1;
            idx++;
        }
        max = Math.max(max, helper(s, list.get(list.size()-1)+1, end, k));
        return max;
    }
}
```