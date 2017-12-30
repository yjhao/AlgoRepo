# 301-2-Remove Invalid Parentheses

## 题意
给一个字符串， 删掉最少的括号， 是这个字符串valid， 求出所有的合法字符串。

## 解题
要删除字符串使其合法， 比如说 （））, 要删掉右括号。 我们用一个counter来表示现在的状态， 如果是左括号就加一， 否则减一。

当发现这个counter小于零的时候， 说明我们要删掉一个右括号。 但是为了防止重复， 我们只删除掉第一个 右括号。

但是光这样限制还不行， 我们删除掉这个右括号之后， 还得保证以后的删除右括号， 删除的都是这个右括号之后的右括号， 不然我们就只是顺序不一样的删掉两处不同的右括号。 比如说 (()) ())， 如果我们要删除掉6，那么就不能删除6之前， 否则就会出现重复， 因为有可能我们先删掉了6之前的， 再删除掉6。

那么如何删掉多余的左括号呢？ 当我们遍历完， 修改完所有的右括号之后， 我们就可以把字符串翻转， 然后再来修改左括号， 这样的话， 代码就没有变化。

### 细节
当删除掉一个字符串之后， 之后的字符串的开始位置应该是当前的“i", 因为我们删掉了一个字符串， 所以其实下一层的i， 已经是步进过了的。

相同的道理， 我们记录之前的右括号的删除地点， 或者下一层的可以开始删除右括号的地点， 也可以就是用j, 这个时候 j 其实已经是步进过了的。

如果在DFS中发现了需要删除掉某个字符， 在这个For loop的最后应该直接return， 而不应该进入翻转的那个环节。 因为翻转只是针对合法的字符。 当前的字符串显然不合法。

每次DFS 就修改一处不合法的地方， 但是有可能会有很多种不同的修改方法。 在修改完所有之后， 直接return。

## 代码
```
class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<>();
        helper(s, 0, 0, res, new char[]{'(', ')'});
        return res;
    }
    
    void helper(String s, int lastI, int lastJ, List<String> res, char[] pair){
        int cnt = 0;
        for (int i=lastI; i<s.length(); i++){
            if (s.charAt(i)==pair[0])   cnt++;
            if (s.charAt(i)==pair[1])   cnt--;
            if (cnt>=0) continue;
            for (int j=lastJ; j<=i; j++){
                if (s.charAt(j)==pair[1]  && (j==lastJ || s.charAt(j-1)!=pair[1])){
                    helper(s.substring(0, j) + s.substring(j+1), i, j, res, pair);
                }
            }
            return;
        }
        
        String reversed = new StringBuilder(s).reverse().toString();
        if (pair[0] == '('){
            helper(reversed, 0, 0, res, new char[] {')', '('});
        }else{
            res.add(reversed);
            return;
        }
    }
}
```