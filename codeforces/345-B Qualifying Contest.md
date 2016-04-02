#345-B Qualifying Contest (override comparator of a class)
## 题意
提议很简单， 在每个组里面分别排序，然后输出每个组的前两名。

## 解题
首先想到， 对于每个组， 建立一个PriorityQueue， 然后再依次加入成员。

这个方法是正确的， 但是复杂度为 O(nlog(n/m)), m是组的数量。

m*(n/m)log(n/m) = nlog(n/m)

如果组数很多的话， 就非常慢。（为什么复杂度分析这里还要快一些？？）

**所以更好的方法是， 拉通一起排序， 先对组号排序， 再在同一个组内对成员根据积分排序。复杂度为O(nlogn)**

## 代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();

        PriorityQueue<person_346> queue = new PriorityQueue<person_346>(new Comparator<person_346>(){
            public int compare(person_346 a, person_346 b){
                if (a.region != b.region){
                    return a.region - b.region;
                }
                return b.score - a.score;
            }
        });

        for (int i=0; i<n; i++){
            String name = in.next();
            int region = in.nextInt()-1;
            int score = in.nextInt();
            person_346 cur = new person_346(name, region, score);
            queue.offer(cur);
        }

        StringBuilder sb = new StringBuilder();

        int i = 0;

        while (!queue.isEmpty()){
            person_346 cur1 = queue.poll();

            person_346 cur2 = queue.poll();

            int count = 0;
            int score_3 = 0;
            while (!queue.isEmpty() && queue.peek().region == cur2.region){
                count ++;
                if (count==1){
                    score_3 = queue.poll().score;
                }else{
                    queue.poll();
                }

            }

            if (count==0 || score_3!=cur2.score){
                sb.append(cur1.name + " " + cur2.name + "\n");
            }else if(score_3==cur2.score){
                sb.append("?\n");
            }
        }

        out.print(sb.toString());
    }
```

## 另外一个方法
override person class comparator

```
class Unit implements Comparable<Unit> {
		final String name;
		final int score;

		public Unit(String name, int score) {
			this.name = name;
			this.score = score;
		}

		@Override
		public int compareTo(Unit u) {
			return Integer.compare(u.score, score);
		}
	}

	void run() {
		int n = nextInt(), m = nextInt();

		List<Unit>[] res = new List[m];
		for (int i = 0; i < m; i++) {
			res[i] = new ArrayList<Unit>();
		}

		for (int i = 0; i < n; i++) {
			String name = next();
			int j = nextInt() - 1;
			int score = nextInt();

			res[j].add(new Unit(name, score));
		}

		for (int i = 0; i < m; i++) {
			List<Unit> team = res[i];
			Collections.sort(team);

			Unit a = team.get(0);
			Unit b = team.get(1);
			Unit c = null;
			...
```