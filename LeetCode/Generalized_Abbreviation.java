/*
    DFS
    not the best solution, but it works.
*/


public class Solution {
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<String>();
        if (word.length()==1){
            res.add(""+1);
            res.add(word);
            return res;
        }

        helper(res, word.toCharArray(), "", 0);
        return res;
    }

    public void helper(List<String> res, char[] word, String cur, int idx){
        if (idx >= word.length){
            res.add(cur);
            return;
        }

        // no abbr.
        String noAdd = "";
        for (int i=idx; i<word.length; i++){
            noAdd += word[i];
        }
        res.add(cur+noAdd);

        // has abbr.
        int maxNum = word.length-idx;
        for (int i=1; i<=maxNum; i++){
            for (int k=idx; k<=word.length-i; k++){
                String curStr = "";
                for (int j=idx; j<k; j++){
                    curStr += word[j];
                }
                curStr += i;
                if (k+i<word.length){
                    curStr += word[k+i];
                }
                helper(res, word, cur+curStr, k+i+1);
            }
        }
    }
}
