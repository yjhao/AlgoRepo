# 288- Unique Word Abbreviation
## 题意
给一个字典里的每一个单词 添加一个缩写。

然后再给出一些 query， 检查这些单词的 缩写 是否是 Unique 的。

A word's abbreviation is unique if **no other** word from the dictionary has the same abbreviation.

## 解答
最简答的方法， 当然是 用一个set来保存 相同 缩写的单词， 然后检查 需要检查的单词 是否在这个 set 中。

但是还有更节约空间的方法.

**不过需要注意， 有可能字典中出现重复！**

当一个缩写， 第一次出现的时候， 加入 map（缩写， 单词本身)。

当这个缩写第二次出现的时候， 如果这个单词与之前那个单词不一样（比较 map 中的 val)， 那么这个缩写无论如何都不会是独一无二的， 所以用 “#” 来标记。

**特例** 字典中有重复的单词。

比如 （a, a)， check (a)， 应该返回 true，因为 a 和 a 是同一个， 只不过出现了两次。

所以这个时候， 缩写会第二次出现， 但是比较 val 之后， 就发现其实是同一个单词。

要判断是否是重复， 就很简单了， 只需要判断 map 有没有这个缩写，和这个缩写相对应的 单词 是什么。

## 代码
```
public class ValidWordAbbr {
    HashMap<String, String> map = new HashMap<String, String>();
    public ValidWordAbbr(String[] dictionary) {
        for (String str : dictionary){
            String cur = getAbr(str);
            if (!map.containsKey(cur)){
                map.put(cur, str);
            }else{
                if (!str.equals(map.get(cur)))
                    map.put(cur, "*");
            }
        }
    }

    public boolean isUnique(String word) {
        String cur = getAbr(word);
        return !map.containsKey(cur) || map.get(cur).equals(word);
    }
    
    public String getAbr(String str){
        int len = str.length();
        if (len<=2) return str;
    
        String cur = "" + str.charAt(0) + len + str.charAt(str.length()-1);
        return cur;
    }
}


// Your ValidWordAbbr object will be instantiated and called as such:
// ValidWordAbbr vwa = new ValidWordAbbr(dictionary);
// vwa.isUnique("Word");
// vwa.isUnique("anotherWord");
```

