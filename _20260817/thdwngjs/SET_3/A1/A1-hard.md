---
title: 가장 조용한 통신 경로
difficulty: GOLD
tags: 그래프 이론, 분리 집합, 최소 신장 트리, 정렬, 오프라인 질의
timeLimit: 8
memoryLimit: 512
isPublic: true
---
<!-- @description -->
산맥 곳곳에 관측소 $N$개가 흩어져 있다. 관측소에는 $1$번부터 $N$번까지 번호가 붙어 있다. 관측소 사이에는 통신 케이블 $M$개가 깔려 있고, 케이블 하나는 서로 다른 두 관측소를 양방향으로 잇는다. 케이블마다 **잡음 세기**가 정해져 있다.

관측소 $u$에서 관측소 $v$까지의 **경로**란, $u$에서 출발해 케이블을 따라 이동하다가 $v$에 도착하는 이동 방법을 말한다. 경로가 지나는 케이블 중 잡음 세기가 가장 큰 값을 그 경로의 **경로 잡음**이라고 한다. 같은 관측소를 여러 번 지나도 되고, 같은 케이블을 여러 번 지나도 된다.

관측소 $u$에서 $v$로 신호를 보낼 때는 경로 잡음이 가장 작은 경로를 고른다. 질의 $Q$개가 주어질 때, 각 질의 $u$, $v$에 대해 **가능한 모든 경로의 경로 잡음 중 최솟값**을 구하라. 규칙은 다음과 같다.

- $u$와 $v$가 같으면 케이블을 하나도 지나지 않아도 되므로 답은 $0$이다.
- $u$에서 $v$로 가는 경로가 하나도 없으면 답은 $-1$이다.

같은 두 관측소를 잇는 케이블이 여러 개일 수 있고, 그 잡음 세기는 서로 다를 수 있다. 잡음 세기는 최대 $10^{12}$까지 될 수 있어 32비트 정수 범위를 넘을 수 있다.

#### 예시

관측소가 $6$개이고 케이블이 다음 $7$개라고 하자. 괄호 안의 수가 잡음 세기다.

```
1 -(3)- 2      2 -(5)- 3      2 -(6)- 5
1 -(1)- 3      3 -(4)- 4      4 -(2)- 5      5 -(7)- 6
```

- $u = 2$, $v = 5$: 케이블 하나짜리 경로 $2 - 5$는 경로 잡음이 $6$이다. 경로 $2 - 3 - 4 - 5$는 잡음이 $5, 4, 2$이므로 경로 잡음이 $5$다. 경로 $2 - 1 - 3 - 4 - 5$는 잡음이 $3, 1, 4, 2$이므로 경로 잡음이 $4$다. 이보다 더 작게 만들 수는 없으므로 답은 $4$다.
- $u = 1$, $v = 6$: $6$번 관측소에 붙은 케이블은 잡음이 $7$인 것 하나뿐이므로 어떤 경로를 골라도 경로 잡음이 $7$ 이상이다. 답은 $7$이다.
- $u = 2$, $v = 3$: 경로 $2 - 3$은 경로 잡음이 $5$지만, 경로 $2 - 1 - 3$은 잡음이 $3, 1$이므로 경로 잡음이 $3$이다. 답은 $3$이다.
- $u = 4$, $v = 4$: 출발지와 목적지가 같으므로 답은 $0$이다.
<!-- @input -->
첫째 줄에 관측소의 수 $N$, 케이블의 수 $M$, 질의의 수 $Q$가 공백으로 구분되어 주어진다. ($2 \le N \le 200{,}000$, $1 \le M \le 300{,}000$, $1 \le Q \le 200{,}000$)

다음 $M$개의 줄에 케이블이 잇는 두 관측소의 번호 $u$, $v$와 그 케이블의 잡음 세기 $w$가 공백으로 구분되어 주어진다. ($1 \le u, v \le N$, $u \ne v$, $1 \le w \le 10^{12}$) 한 케이블이 같은 관측소를 자기 자신과 잇는 경우는 없다. 같은 두 관측소를 잇는 케이블이 두 개 이상 주어질 수 있고, 잡음 세기가 같은 케이블이 여러 개 있을 수도 있다.

다음 $Q$개의 줄에 질의가 하나씩 주어진다. 각 줄에는 관측소 번호 $u$, $v$가 공백으로 구분되어 주어진다. ($1 \le u, v \le N$) $u$와 $v$가 같을 수도 있다.

케이블이 주어지는 순서, 그리고 한 줄에서 두 관측소가 적히는 순서는 정해져 있지 않다. 모든 관측소가 서로 연결되어 있다는 보장은 없다.
<!-- @output -->
각 질의마다 한 줄씩, 경로 잡음의 최솟값을 출력한다. $u$와 $v$가 같으면 $0$을, 경로가 존재하지 않으면 $-1$을 출력한다. 질의가 주어진 순서대로 출력한다.
<!-- @testcases -->
~~~input sample
6 7 4
1 2 3
1 3 1
2 3 5
3 4 4
4 5 2
5 6 7
2 5 6
2 5
1 6
2 3
4 4
~~~
~~~output
4
7
3
0
~~~
~~~input
2 1 3
1 2 5
1 2
2 1
1 1
~~~
~~~output
5
5
0
~~~
~~~input
5 3 5
1 2 10
2 3 4
4 5 7
1 3
1 4
5 4
3 3
2 5
~~~
~~~output
10
-1
7
0
-1
~~~
~~~input
4 5 4
1 2 6
2 3 6
3 4 6
4 1 6
1 3 6
1 4
2 4
1 2
3 3
~~~
~~~output
6
6
6
0
~~~
~~~input
4 4 4
1 2 1000000000000
2 3 999999999999
3 4 4294967296
1 4 1000000000000
1 3
1 4
2 4
4 1
~~~
~~~output
1000000000000
1000000000000
999999999999
1000000000000
~~~
~~~input
6 8 6
1 2 9
1 2 3
2 3 8
2 3 2
3 4 7
4 5 6
5 6 5
5 6 100
2 2
1 6
6 1
3 5
1 3
4 6
~~~
~~~output
0
7
7
7
3
6
~~~
<!-- @generator -->
생성기 stdin 형식: `seed n m q [typ] [wmax] [kcomp]`
typ 은 rand(완전 랜덤) / chain(사슬) / comp(여러 덩어리로 분리) 중 하나다.
~~~generator python3
import sys, random

def main():
    data = sys.stdin.read().split()
    seed = int(data[0]); n = int(data[1]); m = int(data[2]); q = int(data[3])
    typ = data[4] if len(data) > 4 else "rand"
    wmax = int(data[5]) if len(data) > 5 else 10 ** 12
    kcomp = int(data[6]) if len(data) > 6 else 8
    random.seed(seed)

    # 내부 번호를 무작위 라벨로 바꿔 구조가 번호 순서로 드러나지 않게 한다
    lab = list(range(1, n + 1))
    random.shuffle(lab)
    L = lambda x: lab[x - 1]

    edges = []
    if typ == "chain":
        m = n - 1
        for i in range(1, n):
            edges.append((L(i), L(i + 1), random.randint(1, wmax)))
    elif typ == "comp":
        # 정점을 kcomp 덩어리로 나누고 덩어리 안에서만 케이블을 놓는다 -> -1 답이 많이 나온다
        cut = sorted(random.sample(range(1, n), kcomp - 1))
        bounds = [0] + cut + [n]
        blocks = []
        for i in range(kcomp):
            blocks.append(list(range(bounds[i] + 1, bounds[i + 1] + 1)))
        for blk in blocks:
            for j in range(1, len(blk)):
                a = blk[random.randint(0, j - 1)]
                edges.append((L(a), L(blk[j]), random.randint(1, wmax)))
        big = [blk for blk in blocks if len(blk) >= 2]
        while len(edges) < m and big:
            blk = big[random.randint(0, len(big) - 1)]
            a = blk[random.randint(0, len(blk) - 1)]
            b = blk[random.randint(0, len(blk) - 1)]
            if a == b:
                continue
            edges.append((L(a), L(b), random.randint(1, wmax)))
        m = len(edges)
    else:
        while len(edges) < m:
            a = random.randint(1, n)
            b = random.randint(1, n)
            if a == b:
                continue
            edges.append((L(a), L(b), random.randint(1, wmax)))

    for i in range(len(edges)):
        a, b, w = edges[i]
        if random.random() < 0.5:
            edges[i] = (b, a, w)
    random.shuffle(edges)

    out = ["%d %d %d" % (n, m, q)]
    for a, b, w in edges:
        out.append("%d %d %d" % (a, b, w))
    for _ in range(q):
        if random.random() < 0.03:
            v = random.randint(1, n)
            out.append("%d %d" % (v, v))
        else:
            out.append("%d %d" % (random.randint(1, n), random.randint(1, n)))
    sys.stdout.write("\n".join(out) + "\n")

main()
~~~
~~~solution python3
import sys

def main():
    data = sys.stdin.buffer.read().split()
    p = 0
    n = int(data[p]); m = int(data[p + 1]); q = int(data[p + 2]); p += 3

    es = []
    ap = es.append
    for _ in range(m):
        u = int(data[p]); v = int(data[p + 1]); w = int(data[p + 2]); p += 3
        ap((w, u, v))
    es.sort()

    par = list(range(n + 1))
    sz = [1] * (n + 1)
    qs = [None] * (n + 1)       # 각 대표 정점에 매달아 둔 "그 컴포넌트에 걸린 질의 id" 집합
    ans = [-1] * q
    for i in range(q):
        u = int(data[p]); v = int(data[p + 1]); p += 2
        if u == v:
            ans[i] = 0
            continue
        s = qs[u]
        if s is None:
            s = qs[u] = set()
        s.add(i)
        s = qs[v]
        if s is None:
            s = qs[v] = set()
        s.add(i)

    for w, u, v in es:
        # 경로 압축 find (재귀 없이)
        r = u
        while par[r] != r:
            r = par[r]
        while par[u] != r:
            par[u], u = r, par[u]
        ru = r
        r = v
        while par[r] != r:
            r = par[r]
        while par[v] != r:
            par[v], v = r, par[v]
        rv = r
        if ru == rv:
            continue
        a = qs[ru]; b = qs[rv]
        if a is None:
            a = set()
        if b is None:
            b = set()
        if len(a) < len(b):     # small to large: 작은 집합을 큰 집합에 부어 넣는다
            a, b = b, a
        if b:
            for qid in b:
                if qid in a:
                    # 양쪽 끝이 지금 이 간선으로 처음 이어졌다 -> 답이 확정된다
                    ans[qid] = w
                    a.discard(qid)
                else:
                    a.add(qid)
        if sz[ru] < sz[rv]:
            ru, rv = rv, ru
        par[rv] = ru
        sz[ru] += sz[rv]
        qs[ru] = a
        qs[rv] = None

    sys.stdout.write("\n".join(map(str, ans)) + "\n")

main()
~~~
~~~validator cpp
// 독립 구현(모범답안과 다른 접근):
// 크루스칼로 최소 신장 트리(숲)를 만든 뒤, 희소 배열(binary lifting)로
// 두 정점을 잇는 트리 경로 위의 최대 간선을 구한다. 컴포넌트가 다르면 -1.
// 깊이가 20만인 사슬에 대비해 재귀 DFS 없이 명시적 스택만 쓴다.
#include <bits/stdc++.h>
using namespace std;
typedef long long ll;

static int par[200005];
static int rnk[200005];

int find(int x) {
    while (par[x] != x) {
        par[x] = par[par[x]];
        x = par[x];
    }
    return x;
}

static char ibuf[1 << 16];
static size_t ipos = 0, ilen = 0;
static inline int gc() {
    if (ipos == ilen) { ilen = fread(ibuf, 1, sizeof(ibuf), stdin); ipos = 0; if (ilen == 0) return -1; }
    return ibuf[ipos++];
}
static inline ll readInt() {
    int c = gc();
    while (c != -1 && (c < '0' || c > '9') && c != '-') c = gc();
    int sg = 1;
    if (c == '-') { sg = -1; c = gc(); }
    ll x = 0;
    while (c >= '0' && c <= '9') { x = x * 10 + (c - '0'); c = gc(); }
    return x * sg;
}

int main() {
    int n = (int)readInt(), m = (int)readInt(), q = (int)readInt();
    vector<int> eu(m), ev(m);
    vector<ll> ew(m);
    vector<int> ord(m);
    for (int i = 0; i < m; i++) {
        eu[i] = (int)readInt(); ev[i] = (int)readInt(); ew[i] = readInt();
        ord[i] = i;
    }
    sort(ord.begin(), ord.end(), [&](int a, int b) { return ew[a] < ew[b]; });
    for (int i = 1; i <= n; i++) { par[i] = i; rnk[i] = 0; }

    // 크루스칼로 채택된 간선만 인접 리스트에 담는다(최소 신장 숲)
    vector<int> head(n + 1, -1);
    vector<int> nxt; vector<int> dst; vector<ll> wt;
    for (int t = 0; t < m; t++) {
        int i = ord[t];
        int a = find(eu[i]), b = find(ev[i]);
        if (a == b) continue;
        if (rnk[a] < rnk[b]) swap(a, b);
        par[b] = a;
        if (rnk[a] == rnk[b]) rnk[a]++;
        int u = eu[i], v = ev[i];
        dst.push_back(v); wt.push_back(ew[i]); nxt.push_back(head[u]); head[u] = (int)dst.size() - 1;
        dst.push_back(u); wt.push_back(ew[i]); nxt.push_back(head[v]); head[v] = (int)dst.size() - 1;
    }

    int LOG = 1;
    while ((1 << LOG) <= n) LOG++;

    vector<int> dep(n + 1, 0), comp(n + 1, 0);
    vector<vector<int> > up(LOG, vector<int>(n + 1, 0));
    vector<vector<ll> > mx(LOG, vector<ll>(n + 1, 0));
    vector<char> vis(n + 1, 0);
    vector<int> stk;
    stk.reserve(n);
    for (int s = 1; s <= n; s++) {
        if (vis[s]) continue;
        vis[s] = 1; up[0][s] = s; mx[0][s] = 0; dep[s] = 0; comp[s] = s;
        stk.push_back(s);
        while (!stk.empty()) {
            int v = stk.back(); stk.pop_back();
            for (int e = head[v]; e != -1; e = nxt[e]) {
                int u = dst[e];
                if (vis[u]) continue;
                vis[u] = 1;
                up[0][u] = v; mx[0][u] = wt[e]; dep[u] = dep[v] + 1; comp[u] = s;
                stk.push_back(u);
            }
        }
    }
    for (int k = 1; k < LOG; k++) {
        for (int v = 1; v <= n; v++) {
            int mid = up[k - 1][v];
            up[k][v] = up[k - 1][mid];
            mx[k][v] = max(mx[k - 1][v], mx[k - 1][mid]);
        }
    }

    string out;
    out.reserve((size_t)q * 8);
    char buf[32];
    for (int i = 0; i < q; i++) {
        int u = (int)readInt(), v = (int)readInt();
        if (u == v) { out += "0\n"; continue; }
        if (comp[u] != comp[v]) { out += "-1\n"; continue; }
        ll best = 0;
        int a = u, b = v;
        if (dep[a] < dep[b]) swap(a, b);
        int d = dep[a] - dep[b];
        for (int k = 0; d; k++, d >>= 1) {
            if (d & 1) { best = max(best, mx[k][a]); a = up[k][a]; }
        }
        if (a != b) {
            for (int k = LOG - 1; k >= 0; k--) {
                if (up[k][a] != up[k][b]) {
                    best = max(best, max(mx[k][a], mx[k][b]));
                    a = up[k][a]; b = up[k][b];
                }
            }
            best = max(best, max(mx[0][a], mx[0][b]));
        }
        int len = snprintf(buf, sizeof(buf), "%lld\n", best);
        out.append(buf, len);
    }
    fwrite(out.data(), 1, out.size(), stdout);
    return 0;
}
~~~
대규모 랜덤 그래프: 연결된 질의와 -1 질의가 섞인다.
~~~case
1 120000 110000 60000 rand 1000000000000
~~~
사슬 그래프: 경로가 길고 -1 이 전혀 없다.
~~~case
2 100000 99999 60000 chain 1000000000000
~~~
8개 덩어리로 분리된 그래프: -1 답이 대량으로 나온다.
~~~case
3 60000 100000 90000 comp 1000000000000 8
~~~
잡음 값 범위가 아주 좁은 조밀 그래프 + 질의 최대: 동점이 대량 발생한다.
~~~case
4 30000 95000 200000 rand 30
~~~
정점 수 최대(20만) + 케이블이 매우 적어 고립 정점이 많은 그래프.
~~~case
5 200000 6000 6000 rand 1000000000000
~~~
