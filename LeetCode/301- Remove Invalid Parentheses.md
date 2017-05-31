# 301- Remove Invalid Parentheses
## 题意
在一个字符串里面， 用最小的删除数目， 使字符串的括号合法， 并输出所有合法字符串。

## 解题 BFS
首先想想有没有Greedy算法， 好像没有， 因为要一个一个尝试，还要输出合法的子串。

因为是最小的删除， 所以想到bfs。

当合法之后， 检查当前的所在层数， 跳过所有的在下面一层的子串。

## 这个方法的不足
这个方法速度很慢， 因为它穷尽所有可能的组合， 而不论是否是最短解。比如说首先先减一再加一，虽然看似好像归零了，但是用了两次操作， 不是最优解。

解决办法是使用DFS, 如下文描述
https://leetcode.com/discuss/72208/easiest-9ms-java-solution

## 代码
```
public List<String> removeInvalidParentheses(String s) {
    Set<String> res = new HashSet<>();
    int rmL = 0, rmR = 0;
    for(int i = 0; i < s.length(); i++) {
        if(s.charAt(i) == '(') rmL++;
        if(s.charAt(i) == ')') {
            if(rmL != 0) rmL--;
            else rmR++;
        }
    }
    DFS(res, s, 0, rmL, rmR, 0, new StringBuilder());
    return new ArrayList<String>(res);  
}

public void DFS(Set<String> res, String s, int i, int rmL, int rmR, int open, StringBuilder sb) {
    if(i == s.length() && rmL == 0 && rmR == 0 && open == 0) {
        res.add(sb.toString());
        return;
    }
    if(i == s.length() || rmL < 0 || rmR < 0 || open < 0) return;

    char c = s.charAt(i);
    int len = sb.length();

    if(c == '(') {
        DFS(res, s, i + 1, rmL - 1, rmR, open, sb);
        DFS(res, s, i + 1, rmL, rmR, open + 1, sb.append(c)); 

    } else if(c == ')') {
        DFS(res, s, i + 1, rmL, rmR - 1, open, sb);
        DFS(res, s, i + 1, rmL, rmR, open - 1, sb.append(c));

    } else {
        DFS(res, s, i + 1, rmL, rmR, open, sb.append(c)); 
    }

   
```

## DFS
https://leetcode.com/discuss/72208/easiest-9ms-java-solution

DFS的逻辑就是对于一个左括号或者右括号， 有两个选择， 留下或者不留。

留下或者不留 对于下一次迭代 分别有不同的效果。

DFS按道理说应该比BFS慢， 但是考虑到截肢， DFS比BFS快。

如何确定截肢范围？ 首先遍历string， 看需要删除的左括号和右括号的个数， 那么就以这个个数作为一个上界， 如果在DFS中删除的左括号或者右括号个数大于这个数目了， 那么就不是最优解， 需要返回。

同时还需要考虑， 有多少个open的括号， 如果open的个数小于0了， 说明右括号比左括号多， 需要返回。

最后的答案， open应该等于0， 删除的左右括号都应该是最优个数。

那么如何确定max removal rmL and rmR for backtracking boundary 呢？比如())(.

```
for(int i = 0; i < s.length(); i++) {
        if(s.charAt(i) == '(') rmL++;
        if(s.charAt(i) == ')') {
            if(rmL != 0) rmL--;
            else rmR++;
        }
    }
```

**特别需要注意的是， 因为是DFS, 所以最后的结果有可能会有重复！需要用Set来保存结果！**

## 代码
```
public List<String> removeInvalidParentheses(String s) {
    Set<String> res = new HashSet<>();
    int rmL = 0, rmR = 0;
    for(int i = 0; i < s.length(); i++) {
        if(s.charAt(i) == '(') rmL++;
        if(s.charAt(i) == ')') {
            if(rmL != 0) rmL--;
            else rmR++;
        }
    }
    DFS(res, s, 0, rmL, rmR, 0, new StringBuilder());
    return new ArrayList<String>(res);  
}

public void DFS(Set<String> res, String s, int i, int rmL, int rmR, int open, StringBuilder sb) {
    if(i == s.length() && rmL == 0 && rmR == 0 && open == 0) {
        res.add(sb.toString());
        return;
    }
    if(i == s.length() || rmL < 0 || rmR < 0 || open < 0) return;

    char c = s.charAt(i);
    int len = sb.length();

    if(c == '(') {
        DFS(res, s, i + 1, rmL - 1, rmR, open, sb);
        DFS(res, s, i + 1, rmL, rmR, open + 1, sb.append(c)); 

    } else if(c == ')') {
        DFS(res, s, i + 1, rmL, rmR - 1, open, sb);
        DFS(res, s, i + 1, rmL, rmR, open - 1, sb.append(c));

    } else {
        DFS(res, s, i + 1, rmL, rmR, open, sb.append(c)); 
    }

    sb.setLength(len);
}
```



## 另外一个方法：DFS-2
BFS的问题是， 在遍历下一层的时候， 有些substring已经是合法的， 也要继续删除一个括号，放到下一层。

所以有人想出了速度很快的DFS， 基本逻辑是：使前一部分substring合法， 然后迭代计算下一个部分的substring。

检查一个字符串是否合法的时候， 用一个Count来计算， 当Count小于0的时候， 说明 ")" 多于 "("了。 

如果要检查 "(" 是否多于 ")" ？ 翻转字符串。。。。

https://leetcode.com/discuss/81478/easy-short-concise-and-fast-java-dfs-3-ms-solution

```
public List<String> removeInvalidParentheses(String s) {
    List<String> ans = new ArrayList<>();
    remove(s, ans, 0, 0, new char[]{'(', ')'});
    return ans;
}

public void remove(String s, List<String> ans, int last_i, int last_j,  char[] par) {
    for (int stack = 0, i = last_i; i < s.length(); ++i) {
        if (s.charAt(i) == par[0]) stack++;
        if (s.charAt(i) == par[1]) stack--;
        if (stack >= 0) continue;
        for (int j = last_j; j <= i; ++j)
            if (s.charAt(j) == par[1] && (j == last_j || s.charAt(j - 1) != par[1]))
                remove(s.substring(0, j) + s.substring(j + 1, s.length()), ans, i, j, par);
        return;
    }
    String reversed = new StringBuilder(s).reverse().toString();
    if (par[0] == '(') // finished left to right
        remove(reversed, ans, 0, 0, new char[]{')', '('});
    else // finished right to left
        ans.add(reversed);
}
```

