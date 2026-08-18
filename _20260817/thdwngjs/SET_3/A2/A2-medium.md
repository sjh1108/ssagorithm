---
title: 관측소 케이블 부설
difficulty: GOLD
tags: 최소 스패닝 트리, 그래프 이론, 유니온 파인드, 정렬
timeLimit: 4
memoryLimit: 512
isPublic: true
---
<!-- @description -->
산맥 곳곳에 관측소 $N$개가 흩어져 있다. 관측소에는 $1$번부터 $N$번까지 번호가 붙어 있다.

관측소끼리 자료를 주고받으려면 케이블을 깔아야 한다. 측량 결과 부설이 가능한 **후보 케이블** $M$개가 나왔다. $i$번 후보 케이블은 관측소 $u_i$와 $v_i$를 직접 잇고, 부설하는 데 비용 $w_i$가 든다. 후보에 없는 두 관측소를 직접 잇는 것은 지형 때문에 불가능하다.

이 후보 중 일부를 골라 부설한다. 어떤 두 관측소든 부설한 케이블만 타고(중간에 다른 관측소를 여러 번 거쳐도 된다) 자료가 오갈 수 있으면 관측소 전체가 **연결되었다**고 한다.

전체를 연결하는 데 드는 최소 총비용을 구한다. 후보 케이블을 전부 부설해도 전체가 연결되지 않으면 연결이 불가능하다.

같은 두 관측소를 잇는 후보 케이블이 비용만 다르게 여러 개 나올 수 있다. 자기 자신을 잇는 후보는 없다.

#### 예시

관측소가 $6$개, 후보 케이블이 $7$개이고 다음과 같다고 하자.

```
1-2 (비용 3)   1-3 (비용 1)   2-3 (비용 5)   3-4 (비용 4)
4-5 (비용 2)   5-6 (비용 7)   2-5 (비용 6)
```

비용이 싼 케이블부터 훑으면서, 그 케이블이 **아직 서로 연결되지 않은 두 덩어리**를 이어 줄 때만 부설한다고 하자.

| 순서 | 케이블 | 비용 | 판단 |
|---|---|---|---|
| 1 | 1-3 | 1 | 부설한다. 덩어리: $\{1,3\}$ |
| 2 | 4-5 | 2 | 부설한다. 덩어리: $\{1,3\}, \{4,5\}$ |
| 3 | 1-2 | 3 | 부설한다. 덩어리: $\{1,2,3\}, \{4,5\}$ |
| 4 | 3-4 | 4 | 부설한다. 덩어리: $\{1,2,3,4,5\}$ |
| 5 | 2-3 | 5 | $2$와 $3$은 이미 같은 덩어리다. 건너뛴다 |
| 6 | 2-5 | 6 | $2$와 $5$는 이미 같은 덩어리다. 건너뛴다 |
| 7 | 5-6 | 7 | 부설한다. 덩어리: $\{1,2,3,4,5,6\}$ |

부설한 케이블은 $1-3$, $4-5$, $1-2$, $3-4$, $5-6$ 다섯 개이고 총비용은 $1+2+3+4+7 = 17$이다.
<!-- @input -->
첫째 줄에 관측소의 수 $N$과 후보 케이블의 수 $M$이 공백으로 구분되어 주어진다. ($2 \le N \le 200{,}000$, $1 \le M \le 300{,}000$)

다음 $M$개의 줄에 후보 케이블 하나의 정보 $u$, $v$, $w$가 공백으로 구분되어 주어진다. ($1 \le u, v \le N$, $u \ne v$, $1 \le w \le 10^{12}$) 이 케이블은 관측소 $u$와 $v$를 잇고 비용은 $w$이다.

같은 관측소 쌍이 여러 줄에 걸쳐 서로 다른 비용으로 주어질 수 있다. 케이블이 주어지는 순서는 정해져 있지 않으며, 비용 순으로 정렬되어 들어올 수도 있다.

케이블 비용이 최대 $10^{12}$이고 최대 $199{,}999$개를 부설할 수 있으므로, **총비용은 32비트 정수 범위를 훨씬 넘을 수 있다.** 64비트 정수를 써야 한다.
<!-- @output -->
전체 관측소를 연결하는 데 드는 최소 총비용을 한 줄에 출력한다.

후보 케이블을 모두 부설해도 전체가 연결되지 않으면 $-1$을 출력한다.
<!-- @testcases -->
~~~input sample
6 7
1 2 3
1 3 1
2 3 5
3 4 4
4 5 2
5 6 7
2 5 6
~~~
~~~output
17
~~~
~~~input
2 1
1 2 1000000000000
~~~
~~~output
1000000000000
~~~
~~~input
5 3
1 2 4
2 3 9
4 5 6
~~~
~~~output
-1
~~~
~~~input
5 10
1 2 1000000
1 3 1000000
1 4 1000000
1 5 1000000
2 3 1000000
2 4 1000000
2 5 1000000
3 4 1000000
3 5 1000000
4 5 1000000
~~~
~~~output
4000000
~~~
~~~input
6 7
1 2 1000000000000
2 3 999999999999
3 4 1000000000000
4 5 999999999998
5 6 1000000000000
1 6 1000000000000
2 5 999999999997
~~~
~~~output
4999999999994
~~~
~~~input
4 7
1 2 1
1 2 2
2 3 3
2 3 4
3 4 5
1 4 6
1 3 7
~~~
~~~output
9
~~~
<!-- @generator -->
생성기 stdin 형식: `seed n m wmax type [comps]`
type 은 rand(무작위 연결) / chain(사슬 + 무작위 추가 간선) / disc(comps 개 덩어리로 쪼개 비연결).
케이스 크기는 생성 입력 4MB 미만 규칙에 맞춰 잡았다(비용이 13자리까지 가면 한 줄이 27바이트라 간선 수를 줄여야 한다).
~~~generator python3
import sys, random

def main():
    data = sys.stdin.read().split()
    seed = int(data[0]); n = int(data[1]); m = int(data[2]); wmax = int(data[3])
    typ = data[4] if len(data) > 4 else "rand"
    comps = int(data[5]) if len(data) > 5 else 1
    rnd = random.Random()
    rnd.seed(seed)

    pairs = []
    if typ == "disc":
        # 정점을 comps 개의 덩어리로 나누고 덩어리 안에서만 간선을 만든다 -> 반드시 비연결
        bound = [1]
        step = n // comps
        for c in range(1, comps):
            bound.append(c * step + 1)
        bound.append(n + 1)
        blocks = [(bound[c], bound[c + 1] - 1) for c in range(comps)]
        for lo, hi in blocks:
            for i in range(lo + 1, hi + 1):
                pairs.append((rnd.randint(lo, i - 1), i))
        rest = m - len(pairs)
        for _ in range(max(0, rest)):
            lo, hi = blocks[rnd.randrange(comps)]
            if hi - lo < 1:
                lo, hi = blocks[-1]
            a = rnd.randint(lo, hi); b = rnd.randint(lo, hi)
            while b == a:
                b = rnd.randint(lo, hi)
            pairs.append((a, b))
    else:
        # 먼저 신장 트리를 깔아 연결을 보장하고, 남은 개수만큼 아무 쌍이나 덧붙인다
        for i in range(2, n + 1):
            if typ == "chain":
                pairs.append((i - 1, i))
            else:
                pairs.append((rnd.randint(1, i - 1), i))
        for _ in range(max(0, m - (n - 1))):
            a = rnd.randint(1, n); b = rnd.randint(1, n)
            while b == a:
                b = rnd.randint(1, n)
            pairs.append((a, b))

    # 번호를 섞어 구조가 입력 순서에 드러나지 않게 한다
    lab = list(range(n + 1))
    tail = lab[1:]
    rnd.shuffle(tail)
    lab[1:] = tail

    rnd.shuffle(pairs)
    out = ["%d %d" % (n, len(pairs))]
    ri = rnd.randint
    for a, b in pairs:
        x, y = lab[a], lab[b]
        if ri(0, 1):
            x, y = y, x
        out.append("%d %d %d" % (x, y, ri(1, wmax)))
    sys.stdout.write("\n".join(out) + "\n")

main()
~~~
~~~solution python3
import sys

def main():
    nums = list(map(int, sys.stdin.buffer.read().split()))
    n = nums[0]
    # 간선을 (비용, u, v) 로 묶어 비용 오름차순 정렬 -> 크루스칼
    edges = sorted(zip(nums[4::3], nums[2::3], nums[3::3]))
    par = list(range(n + 1))
    sz = [1] * (n + 1)
    total = 0
    used = 0
    need = n - 1
    for w, u, v in edges:
        ru = u
        while par[ru] != ru:
            ru = par[ru]
        while par[u] != ru:      # 경로 압축
            par[u], u = ru, par[u]
        rv = v
        while par[rv] != rv:
            rv = par[rv]
        while par[v] != rv:
            par[v], v = rv, par[v]
        if ru == rv:
            continue
        if sz[ru] < sz[rv]:      # union by size
            ru, rv = rv, ru
        par[rv] = ru
        sz[ru] += sz[rv]
        total += w
        used += 1
        if used == need:
            break
    sys.stdout.write(("%d" % total if used == need else "-1") + "\n")

main()
~~~
~~~validator cpp
// 독립 구현: 정렬 + 유니온 파인드(크루스칼) 대신
// 우선순위 큐를 쓰는 프림 알고리즘으로 최소 신장 트리를 만든다.
// 연결 여부는 트리에 들어온 정점 개수로 판정한다.
#include <bits/stdc++.h>
using namespace std;

static char ibuf[1 << 16];
static size_t ipos = 0, ilen = 0;

static inline int gc() {
    if (ipos == ilen) {
        ilen = fread(ibuf, 1, sizeof(ibuf), stdin);
        ipos = 0;
        if (ilen == 0) return -1;
    }
    return ibuf[ipos++];
}

static inline long long readInt() {
    int c = gc();
    while (c != -1 && (c < '0' || c > '9') && c != '-') c = gc();
    long long sgn = 1;
    if (c == '-') { sgn = -1; c = gc(); }
    long long x = 0;
    while (c >= '0' && c <= '9') { x = x * 10 + (c - '0'); c = gc(); }
    return x * sgn;
}

int main() {
    int n = (int)readInt();
    int m = (int)readInt();
    vector<int> eu(m), ev(m);
    vector<long long> ew(m);
    vector<int> deg(n + 2, 0);
    for (int i = 0; i < m; i++) {
        eu[i] = (int)readInt();
        ev[i] = (int)readInt();
        ew[i] = readInt();
        deg[eu[i]]++;
        deg[ev[i]]++;
    }
    // CSR 인접 리스트
    vector<int> start(n + 2, 0);
    for (int v = 1; v <= n; v++) start[v + 1] = start[v] + deg[v];
    vector<int> pos(start.begin(), start.end());
    vector<int> adjTo(2 * (size_t)m);
    vector<long long> adjW(2 * (size_t)m);
    for (int i = 0; i < m; i++) {
        adjTo[pos[eu[i]]] = ev[i]; adjW[pos[eu[i]]++] = ew[i];
        adjTo[pos[ev[i]]] = eu[i]; adjW[pos[ev[i]]++] = ew[i];
    }

    vector<char> vis(n + 1, 0);
    priority_queue<pair<long long, int>, vector<pair<long long, int>>, greater<pair<long long, int>>> pq;
    pq.push(make_pair(0LL, 1));
    long long total = 0;
    int taken = 0;
    while (!pq.empty()) {
        pair<long long, int> cur = pq.top();
        pq.pop();
        int v = cur.second;
        if (vis[v]) continue;
        vis[v] = 1;
        total += cur.first;
        taken++;
        if (taken == n) break;
        for (int e = start[v]; e < start[v + 1]; e++) {
            int u = adjTo[e];
            if (!vis[u]) pq.push(make_pair(adjW[e], u));
        }
    }
    if (taken == n) printf("%lld\n", total);
    else printf("-1\n");
    return 0;
}
~~~
~~~case
1009 130000 150000 1000000000000 rand
~~~
~~~case
2027 140000 165000 1000000000 chain
~~~
~~~case
3041 120000 140000 1000000000000 disc 6
~~~
~~~case
4093 200000 230000 5 rand
~~~
