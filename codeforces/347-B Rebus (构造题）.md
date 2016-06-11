#347-B Rebus (构造题）
##题意
给一些 ? + ? - ? + ? = n， 问号 是从 1-n 中的数来选择， 问可不可以组成这样的一个等式， 如果可以， 各个问号是多少。

##解题
首先可以得到有多少个加法， 多少个减法， 然后可以求得 可能的最大值和最小值， 比较 N 是不是在这个范围之内， 如果不是的话， 返回 false。


两个方法：

第一个方法：

填第一个数字，从1-n之间任意选一个， 填了这个数字之后， 再检查这个等式的最小最大值范围， 如果满足的话， 进行下一个， 不满足的话， 重新填这个数。

第二个方法：
将所有问号都设置成 1.

如果等式的值 小于 n， 增大其中一个 加号 的值； 并递增 加号 的 Index
如果小于 n， 增大其中一个减号的值。 并递增 减号 的 Index.

Math.min(n-val, n)

用这个 While loop 既可以求得最终值。

##代码
```
#include <iostream>
#include <cstdio>
#include <cstring>
#include <string>
#include <vector>
#include <cmath>
#include <cstdlib>
using namespace std;
vector<string> vec;
const int maxn = 1001;
int sgn[maxn], num[maxn];
int cur = 0;
int n;
int main()
{
    string buf;
    while (cin >> buf)
        vec.push_back(buf);
    for (int i = 0; i < vec.size(); i++)
    {
        if (vec[i] == "?")
        {
            if (i == 0 || vec[i - 1] == "+") sgn[i] = 1;
            else sgn[i] = -1;
        }
    }
    n = atoi(vec[vec.size() - 1].c_str());
    for (int i = 0; i < vec.size(); i++)
    {
        if (sgn[i] == 1) {
            num[i] = 1;
            cur++;
        } else if (sgn[i] == -1) {
            num[i] = 1;
            cur--;
        }
    }
    for (int i = 0; i < vec.size(); i++)
    {
        if (sgn[i] == -1) {
            if (cur > n) {
                cur++;
                num[i] = min(n, cur - n);
                cur -= num[i];
            }
        } else if (sgn[i] == 1) {
            if (cur < n) {
                cur--;
                num[i] = min(n, n - cur);
                cur += num[i];
            }
        }
    }
    if (cur != n) {
        printf("Impossible\n");
    } else {
        printf("Possible\n");
        for (int i = 0; i < vec.size(); i++) {
            if (vec[i] == "?")
            {
                if (i != 0) {
                    printf(" %d", num[i]);
                } else {
                    printf("%d", num[i]);
                }
            } else {
                printf(" %s", vec[i].c_str());
            }
        }
        printf("\n");
    }
    return 0;
}
```

