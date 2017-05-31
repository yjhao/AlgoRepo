# 336 Palindrome Pairs
## 题意
给一个String list: words， 找出i 和 j， 使得 words[i] + words[j] 为回文。

## 解题
最容易的解法， 一个一个尝试， 对某一个str， 尝试其他所有的str，加在一起， 看能不能是回文， 复杂度为 n^2 * k， k 为每个str的长度。 显然， 这样的方法会超时， 因为有可能List很长

优化的方法：

对于每一个str a， **在前面**， 或者后面， 看能不能构造一个str b， 使得 a+b 或者 b+a （b 在前） 为一个回文。

要点： 虽然题目说是 i+j, 看似好像是只能在i后面加上一个j， 但其实是 还不得不在前面构造。

对于每一个str a进行构造， 也就是说， 这个palindrome的中心在a中， 加入的 b 有可能在a前面， 也有可能在a后面。但是对于b来说， 由于中心不在b中， 所以是不可能得知该怎么构造出a的。 比如说 sll 和 s， 对于s， 是不可能推出 sll 的。


由于对每一个str a都在两个方向进行构造， 最后的答案中是可能发生重复的。

但是什么时候会发生重复呢？ 

当回文的中心在 str A 之间 （比如说 abcd 之间） 的时候， 显而易见，最后的答案中 是不可能发生重复的， 因为 abcd 是 独一无二的。

重复的 是 当回文的中心在 A 之外的时候！  比如说 abcd， dcba， 此时回文的中心在第一个 a 之前， 或者 d 之后， 此时就会发生重复。

要避免重复 两个方法：

1. 使用 ```Set < List< Integer > > ret = new HashSet<>(); 然后 return new ArrayList<>(ret); ```
2. 加入前后部分的时候， **对其中一个检查， 看回文部分是不是空字符 （说明中心此时在 A 之外)**


还需要注意的是：
```
i != j
```


如何判断是否存在一个回文中心呢？ 

**简单方法：  将A一份为二， 对两个中的任何一个substring， 看是否是回文， 然后要加入的部分就是另外一个substring的逆序。**

## 代码
```
public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> ret = new ArrayList<>(); 
    if (words == null || words.length < 2) return ret;
    Map<String, Integer> map = new HashMap<String, Integer>();
    for (int i=0; i<words.length; i++) map.put(words[i], i);
    for (int i=0; i<words.length; i++) {
        // System.out.println(words[i]);
        for (int j=0; j<=words[i].length(); j++) { // notice it should be "j <= words[i].length()"
            String str1 = words[i].substring(0, j);
            String str2 = words[i].substring(j);
            if (isPalindrome(str1)) {
                String str2rvs = new StringBuilder(str2).reverse().toString();
                if (map.containsKey(str2rvs) && map.get(str2rvs) != i) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(map.get(str2rvs));
                    list.add(i);
                    ret.add(list);
                    // System.out.printf("isPal(str1): %s\n", list.toString());
                }
            }
            if (isPalindrome(str2)) {
                String str1rvs = new StringBuilder(str1).reverse().toString();
                // check "str.length() != 0" to avoid duplicates
                if (map.containsKey(str1rvs) && map.get(str1rvs) != i && str2.length()!=0) { 
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(i);
                    list.add(map.get(str1rvs));
                    ret.add(list);
                    // System.out.printf("isPal(str2): %s\n", list.toString());
                }
            }
        }
    }
    return ret;
}

private boolean isPalindrome(String str) {
    int left = 0;
    int right = str.length() - 1;
    while (left <= right) {
        if (str.charAt(left++) !=  str.charAt(right--)) return false;
    }
    return true;
}
```





