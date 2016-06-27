#291-C Watto and Mechanism (Hash Or Trie)
## 题意
给一组 String 作为字典， 然后有一些另外的 String， 每一个 string 的其中一个字母 变为 a,b,c 中的其中一个, 问变化之后， 这个新的 string 与字典中的词有没有 match 的。

##解题 Trie
match  string， 首先应该想到用 trie。 

先把字典建立 trieTree。 然后对新的 string 进行 DFS, 约束条件是 变化数必须为1； 最后形成的词， 一定要是存在与字典中的。

##代码
```
trieNode head = new trieNode();
    public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        String[] memory = new String[n];

        for (int i=0; i<n; i++){
            memory[i] = in.next();
        }

        buildTree(memory);

        String[] query = new String[m];

        for (int i=0; i<m; i++){
            query[i] = in.next();
        }

        StringBuilder sb = new StringBuilder();

        for (String str : query){
            if (search(head, str, 0, 1)){
                sb.append("YES").append("\n");
            }else{
                sb.append("NO").append("\n");
            }
        }

        out.print(sb.toString());
    }

    public boolean search(trieNode cur, String str, int idx, int change){
        if (cur==null)  return false;
        if (change<0)   return false;
        if (idx==str.length() && change == 0 && cur.hasWord)    return true;
        if (idx==str.length() && change == 0)  return false;
        if (idx==str.length()) return false;


        char c = str.charAt(idx);

        // use itself
        if (search(cur.child[c-'a'], str, idx+1, change)){
            return true;
        }

        // not use itself, change to another one
        if (change==1){
            for (int i=0; i<3; i++){
                char maybe = (char)('a'+i);
                if (maybe==c)   continue;
                if (search(cur.child[maybe-'a'], str, idx+1, change-1)){
                    return true;
                }
            }
        }

        return false;
    }
```

##解题 Hash
我们还可以对 string 建立 hash， 然后检测 更改过之后的string  的hash存不存在hashset 中

求Hashvalue的技巧：

1. base 使用质数
2. 先求得 pow[] array。 好处是不用每次都重新计算了， 直接使用。
3. 在 计算的时候， 在任何时候， 都应该 mod 一个很大的奇数， 防止overflow， 在这里， 使用了 1e16+7
4. 在每一位， 为了区别 没有字母 和 有字母的区别， a 应该被转换为 1， 而不是 0。
5. 如果要变化某一位， 先用 old hash value 减去 这一位 的权重， 再加上 改变过之后的权重， 看新的 hash value 是不是在 hashset之中 

##代码
```
long mod = (long)(1e16+7);
    long base = 31;
    HashSet<Long> set = new HashSet<>();
    long[] pow = new long[600000+1];

    public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();

        calcPow();

        for (int i =0; i<n; i++){
            String str = in.next();
            set.add(hash(str));
        }

        StringBuilder sb = new StringBuilder();

        for (int i=0; i<m; i++){
            String str = in.next();
            long curHash = hash(str);

            if (hashContains(str, curHash)){
                sb.append("YES");
            }else{
                sb.append("NO");
            }
            sb.append("\n");
        }

        out.print(sb.toString());
    }

    boolean hashContains(String str, long oldHash){
        for (int i=0; i<str.length(); i++){
            long temp = oldHash - (pow[i]*(str.charAt(i)-'a'+1))%mod;
            for (char c='a'; c<='c'; c++){
                if (str.charAt(i) == c) continue;
                int increment = c-'a'+1;
                long next = temp + pow[i]*(increment)%mod;
                if (set.contains(next)) return true;
            }
        }
        return false;
    }

    long hash(String str){
        long res = 0;
        for (int i=0; i<str.length(); i++){
            res += pow[i]*(str.charAt(i)-'a'+1)%mod;
        }

        return res;
    }

    void calcPow(){
        pow[0] = 1;
        for (int i=1; i<pow.length; i++){
            pow[i] = (pow[i-1]*base)%mod;
        }
    }
```