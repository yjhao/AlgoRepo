#318- Maximum Product of Word Lengths （bitmap)

## 题意
给一个 String list， 找出两个 string do not share common letters， find the maximum value of length(word[i]) * length(word[j])

## 解题

###错误思路：
最开始， 我的想法是针对某一个字母， 看哪个 string 有这个字母， 哪个string没有这个字母。

但后来发现， 这几乎对解题没用。 最后的目的是 要得到 两个string A 和 B 有没有共同的字母， 如果在某一个字母上， A有而B没有， B有而A没有， 或者 A B 都没有， 都不能说明 A B 没有任何的共同字母。

所以不能拆分一个单词， 得把一个单词拉通为一个整体来看待。

###正确思路
使用 Bitmap 来记录一个单词里面 有还是没有哪个字母。

一共26位可以表示， bitmap的最后结果是一个 整数

然后再比较两个整数， 使用 &, 如果结果为0的话， 说明任何一位上面都没有共同的“1“， 说明两个单词没有 共同的字母。

##代码
```
public class Solution {
    public int maxProduct(String[] words) {
        if (words==null || words.length==0)     return 0;
        int[] bit = new int[words.length];
        for (int i=0; i<words.length; i++){
            bit[i] = bitmap(words[i]);
        }
        
        int max = 0;
        for (int i=0; i<words.length-1; i++){
            for (int j=i+1; j<words.length; j++){
                if ((bit[i] & bit[j])==0){
                    max = Math.max(max, words[i].length() * words[j].length());
                }
            }
        }
        return max;
    }
    
    public int bitmap(String str){
        int res = 0;
        for (char c : str.toCharArray()){
            int cur = 1;
            for (int i=0; i<c-'a'; i++){
                cur = cur<<1;
            }
            res = res | cur;
        }
        return res;
    }
}
```