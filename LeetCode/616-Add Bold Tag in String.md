# 616-Add Bold Tag in String

## 题意
给一个字符串和字典， 用字符串中的子串， 看能不能match字典中的单词， 如果可以的话， 用 《》来包围一下， 如果有相邻的《》《》， 合并成一个， 如果有嵌套的《》《》， 合并成最大的那一个。

## 解题
因为是exact match 某个单词， 所以只能 hard compare。

在每个char的位置， 对于字典中的每一个单词， 看是不是一个新的 match， 使用

```
s.startsWith(word, i)
```

如果存在的话， 找到以当前字母为开始的最长的字符串长度l1， 那么一定的， 右边的括号最少要到这个i+l1。

所以我们可以得到很多个这样的 range， 最后就像 merge interval 一样 merge 在一起就好。

当然， 还有更好的方法， 当我们在 i 的时候， 可以判断 i 是否属于一个括号之间。我们Keep 一个 Boolean array， 保存一下对于每一个字母， 是否在括号之间。 如果当前正在visit的点小于当前括号的终点， 那么当前的这一个点一定是在括号之间。那么我们最后只需要找到每个括号的起点和终点就可以了。

当前括号的起点不用更新， 但是我们有必要， 随时更新当前的括号的终点在哪里。 比如说在i=0处， 找到的end 是 4，所以从 1-4处都是在括号之间。 但是有可能到 i=2处， 找到的end 是 6， 那么我们就应该更新当前 end from 4 to 6。 这样的话， 5 6 都可以被判断为是在括号之间。

这个有点像 in place merge interval， 因为我们一直在不停的更新括号的 end。

## 代码

```
class Solution {
    String left = "<b>";
    String right = "</b>";
    
    public String addBoldTag(String s, String[] dict) {
        boolean[] bold = new boolean[s.length()];
        int max = -1;
        
        for (int i=0; i<s.length(); i++){
            for (String str : dict){
                if (s.startsWith(str, i)){
                    max = Math.max(max, i+str.length()-1);
                }
            }
            bold[i] = max>=i;
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if (!bold[i]){
                sb.append(c);
                continue;
            }
            
            int j=i;
            while (j<s.length() && bold[j]) j++;
            sb.append(left).append(s.substring(i, j)).append(right);
            i = j-1;
        }
        
        return sb.toString();
        
    }
}
```