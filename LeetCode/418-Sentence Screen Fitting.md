# 418-Sentence Screen Fitting

## 题意
给一个句子， 然后给一个屏幕的长度和宽度， 求最多能够容纳下多少个句子。

## 解题
最brutal的方法， 就是一个一个放， 这一行放满了， 开始放入下一行。

但是很明显， 我们可以看到， 每一行和每一行可能有Cycle出现， 所以如果找到这个Cycle， 不用一个一个放， 根据之前的信息， 也能知道这一行会怎么样。

#### 不太好的方法
我的想法是， 在每一个新的 句子 开始的地方， 记录下col idx， 然后放入map之中， 如果以后再次遇到相同的idx开始， 我们直接回map寻找答案。 这个方法的缺点是 code很麻烦

### 有效的方法， 方法一
与其说找到每一个句子开始的地方， **不如找到每一行开始的第一个单词是什么**， 因为如果第一个单词定了， 这一行有多少个 end， 下一行的开始是第几个单词， 我们都能知道。

所以对每一个单词作为开头的情况都一一做出判断： 这一行有多少个 end， 下一行的第一个单词是哪个。 然后放入map。然后一行一行直接取map的信息就可以得到答案。

这个方法的**不足**：

在现实中， 不一定每一种“开头”的情况都能碰见， 所以很有可能over computation。

```
public int wordsTyping(String[] sentence, int rows, int cols) {
        int[] next = new int[sentence.length];
        int[] cnt  = new int[sentence.length];
        
        for (int i=0; i<sentence.length; i++){
            int loc = 0;
            int curRes = 0;
            int idx = i;
            while (loc+sentence[idx].length() <= cols){
                loc = loc+sentence[idx].length();
                loc++;
                idx++;
                if (idx==sentence.length){
                    idx = 0;
                    curRes++;
                }
            }
            next[i] = idx;
            cnt[i] = curRes;
        }
        
        int res = 0;
        int start = 0;
        for (int i=0; i<rows; i++){
            res += cnt[start];
            start = next[start];
        }
        return res;
    }
```

## 有效的方法， 方法二。
我们先得到句子的总长度（包括空格）， 然后再 用 col 除以 这个总长度， 既可以得到， 在这一行， 可以放多少个完整的 句子。

每一行唯一的不同就是开始的字符， 假设这一行开始的单词的idx是5， 然后一共可以放下3个句子， 所以从idx=5的第一个单词开始，填入这行， 可以实现 3 个这样的 完整cycle ( 5 6 7 1 2 3 4 5...)， 最后的不是一个 完整的cycle 的起点还会是5。所以我们求得剩余的长度， 然后再依次填入单词， 得到在下一行， 开始的单词的 idx。

#### 细节：
每一个单词后面都有一个空格， 唯一的例外是行末的最后一个单词。 为了避免处理这个特例， 我们也假设最后一个单词后面也有个空格， 然后 Col 的长度加一。

为了加快速度， 我们可以引入hashmap， 类似于方法一， 存入以当前idx开始的这一行， 一共可以放入多少个完整的句子， 和下一行， 开始的idx是多少。

```
public int wordsTyping(String[] sentence, int rows, int cols) {
        int len = 0;
        for (String s : sentence)   len += s.length() + 1;
        HashMap<Integer, Integer> next = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> cnt  = new HashMap<Integer, Integer>();
        
        cols++;
        int idx = 0;
        int res = 0;
        
        for (int i=0; i<rows; i++){
            if (next.containsKey(idx)){
                res += cnt.get(idx);
                idx = next.get(idx);
            }else{
                int startIdx = idx;
                int curRes = 0;
                curRes = cols/len;
                res += curRes;
                int curCol = cols-(cols - cols/len*len);
                while (curCol + sentence[idx].length() + 1 <= cols){
                    curCol += sentence[idx].length()+1;
                    idx++;
                    if (idx==sentence.length){
                        idx = 0;
                        curRes ++;
                        res++;
                    }
                }
                next.put(startIdx, idx);
                cnt.put(startIdx,curRes);
            }
        }
        return res;
    }
```

