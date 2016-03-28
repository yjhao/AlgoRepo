#327-C Median Smoothing

## 题意
给出一段01的序列，问是否能通过题目中给的方法得到一个稳定的序列，输出变化次数和最后稳定的序列
变化方法:a1 = b1, an = bn, bi 是 ai-1, ai, ai+1的中位数(2 <= i <= n - 1)

## 解题
**这种题， 一定要多试多观察**

可以尝试得知， 如果一个数是稳定的， 那么他等于左边的数或者右边的数。 如果要变化， 那么一定是01010这样的交错的序列。

那么就在原数列中找到各个01010 的序列， 然后求得最长长度。

如果这个序列的长度是奇数， 最后的数字一定都会是000或者111

如果长度是偶数， 那么最后会变成000111这样的。

因为只有0 1 两种数， 所以可以用 ```While (next!=cur） i++ ```  来递进找0101的序列。

# 代码
```
void solve() throws IOException {
		int n = nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = nextInt();
		}

		int[] ret = new int[n];

		int cnt = 0;
		for (int i = 0; i < n;) {
			int j = i;
			while (j + 1 < n && a[j] != a[j + 1]) {
				j++;
			}
			j++;
			cnt = Math.max(cnt, (j - i - 1) / 2);
			
			if ((j - i) % 2 == 1) { // 0 1 0 1 0
				for (int k = i; k < j; k++) {
					ret[k] = a[i];
				}
			} else { // 0 1 0 1
				for (int k = i; k < (i + j) / 2; k++) {
					ret[k] = a[i];
				}
				for (int k = (i + j) / 2; k < j; k++) {
					ret[k] = a[j - 1];
				}
			}
			i = j;
		}
		
		out.println(cnt);
		for (int x : ret) {
			out.print(x + " ");
		}
		out.println();
	}
```