# 320- Generalized Abbreviation(DFS)
## 题意
把一个单词 变成缩写， 返回所有的可能性。

比如 Word 可以变为：
["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]

## 解题
每一个字母， 有两个选择， 要么变成缩写， 要么维持原来的。

用一个count来表示当前的缩写字母个数。

如果变成缩写， 直接把Count ++， 然后进入下一层。

如果不变成缩写， 直接已经缩写的 count 要加上， 然后再加上当前字母， 然后进入下一层。

复杂度 2^n

## 小技巧
使用 StringBuilder 来记录 string

返回到上一层的时候， 直接 使用：

sb.setLength(len)

## 代码
```
public class Solution {
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<String>();
        helper(word.toCharArray(), 0, 0, new StringBuilder(), res);
        return res;
    }
    
    public void helper(char[] word, int num, int idx, StringBuilder sb, List<String> res){
        int len = sb.length();
        if (idx==word.length){
            if (num!=0) sb.append(num);
            res.add(sb.toString());
            return;
        }
        
        helper(word, num+1, idx+1, sb, res);
        sb.setLength(len);
        
        if(num!=0)  sb.append(num);
        sb.append(word[idx]);
        helper(word, 0, idx+1, sb, res);
        
        sb.setLength(len);
    }
}
```


