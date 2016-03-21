# 302B-Spongebob and Joke

## 题意
已知f[a[i]]=b[i]，给出f序列，b序列，问是否存在唯一确定的a序列。

## 题解
用hashmap来解答。 

如果对于一个b[i]值，它在f中没有出现，那么就是Impossible；如果出现1次以上，就是Ambiguity；如果出现一次，那就把hash记录的位置赋值给a[i]。

**要注意的是**：一定要全部判断完才能下结论， Ambiguity的有可能最后会出现impossible的结果！

## 代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();

        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        int[] f   = new int[n];
        int[] res = new int[m];

        for (int i=0; i<n; i++){
            int cur = in.nextInt();
            f[i] = cur;
            if (map.containsKey(cur)){
                ArrayList<Integer> list = map.get(cur);
                list.add(i);
            }else{
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(cur, list);
            }
        }

        boolean ambi = false;
        for (int i=0; i<m; i++){
            int cur = in.nextInt();
            if (!map.containsKey(cur)){
                out.print("Impossible");
                return;
            }else {
                if (map.get(cur).size() > 1) {
                    ambi = true;
                }else{
                    res[i] = map.get(cur).get(0)+1;
                }
            }
        }

        if (ambi){
            out.print("Ambiguity");
            return;
        }

        out.println("Possible");
        for (int i=0; i<m; i++){
            out.print(res[i]+" ");
        }
    }
```