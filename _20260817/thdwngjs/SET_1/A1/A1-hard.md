---
title: 가장 위험한 터널
difficulty: GOLD
tags: 트리, 최소 공통 조상, 희소 배열, 그래프 탐색
timeLimit: 8
memoryLimit: 512
isPublic: true
---
<!-- @description -->
남극 빙하 아래에 조사 기지 $N$개가 있다. 기지에는 $1$번부터 $N$번까지 번호가 붙어 있고, 얼음 터널 $N-1$개가 기지들을 잇는다. 터널만 이용해 어떤 기지에서 다른 어떤 기지로도 갈 수 있다. 터널마다 **위험도**가 매겨져 있다.

터널의 개수가 $N-1$개이고 전부 연결되어 있으므로, 서로 다른 두 기지 $u$, $v$ 사이를 잇는 경로는 하나뿐이다. 이 경로가 지나는 터널 중 위험도가 가장 큰 값을 그 경로의 **최대 위험도**라고 하고, 경로가 지나는 터널 중 위험도가 최대 위험도와 정확히 같은 터널의 개수를 **위험 구간 수**라고 한다.

탐사대가 질의 $Q$개를 보낸다. 각 질의는 기지 번호 두 개 $u$, $v$로 이루어지며, 두 기지를 잇는 경로의 최대 위험도와 위험 구간 수를 답해야 한다. 단 $u$와 $v$가 같으면 지나는 터널이 하나도 없으므로 둘 다 $0$으로 답한다.

위험도는 최대 $10^{12}$까지 될 수 있어 32비트 정수 범위를 넘을 수 있다.

#### 예시

기지가 $8$개이고 터널이 다음과 같다고 하자. 괄호 안의 수가 위험도다.

```
1 -(5)- 2 -(7)- 4
|       |
|       +-(5)- 5
|
+-(9)- 3 -(9)- 6 -(2)- 7
                |
                +-(9)- 8
```

- $u = 4$, $v = 5$: 경로는 $4 - 2 - 5$이고 위험도는 $7, 5$이다. 최대는 $7$이고 그런 터널은 $1$개이므로 `7 1`.
- $u = 7$, $v = 8$: 위험도는 $2, 9$이므로 `9 1`.
- $u = 4$, $v = 7$: 경로는 $4 - 2 - 1 - 3 - 6 - 7$이고 위험도는 $7, 5, 9, 9, 2$이다. 최대는 $9$이고 그런 터널이 $2$개이므로 `9 2`.
- $u = 5$, $v = 5$: 지나는 터널이 없으므로 `0 0`.
- $u = 1$, $v = 5$: 위험도는 $5, 5$이다. 최대는 $5$이고 그런 터널이 $2$개이므로 `5 2`.
- $u = 8$, $v = 3$: 위험도는 $9, 9$이므로 `9 2`.
<!-- @input -->
첫째 줄에 기지의 수 $N$과 질의의 수 $Q$가 공백으로 구분되어 주어진다. ($2 \le N \le 100{,}000$, $1 \le Q \le 100{,}000$)

다음 $N-1$개의 줄에 터널이 잇는 두 기지의 번호 $a$, $b$와 그 터널의 위험도 $w$가 주어진다. ($1 \le a, b \le N$, $a \ne b$, $1 \le w \le 10^{12}$) 같은 터널이 두 번 주어지지는 않으며, 주어진 터널로 모든 기지가 연결된다. 위험도가 같은 터널이 여러 개일 수 있다.

다음 $Q$개의 줄에 질의 $u$, $v$가 주어진다. ($1 \le u, v \le N$) $u$와 $v$가 같을 수도 있다.

터널이 주어지는 순서, 그리고 한 줄에서 두 기지가 적히는 순서는 정해져 있지 않다. 트리가 한 줄로 길게 이어진 모양이어서 깊이가 $100{,}000$에 이를 수도 있다.
<!-- @output -->
각 질의마다 한 줄씩, 최대 위험도와 위험 구간 수를 공백으로 구분해 출력한다. 질의가 주어진 순서대로 출력한다.
<!-- @testcases -->
~~~input sample
8 6
1 2 5
1 3 9
2 4 7
2 5 5
3 6 9
6 7 2
6 8 9
4 5
7 8
4 7
5 5
1 5
8 3
~~~
~~~output
7 1
9 1
9 2
0 0
5 2
9 2
~~~
~~~input
2 3
2 1 1000000000000
1 2
2 1
1 1
~~~
~~~output
1000000000000 1
1000000000000 1
0 0
~~~
~~~input
7 5
1 2 4
2 3 4
3 4 4
4 5 4
5 6 4
6 7 4
1 7
7 1
3 5
4 4
2 3
~~~
~~~output
4 6
4 6
4 2
0 0
4 1
~~~
~~~input
7 6
1 2 10
1 3 20
1 4 30
1 5 30
1 6 1
1 7 999999999999
2 3
4 5
6 7
7 1
2 2
3 4
~~~
~~~output
20 1
30 2
999999999999 1
999999999999 1
0 0
30 1
~~~
~~~input
10 6
1 2 1000000000000
2 3 1
3 4 1
4 5 1
5 6 1
6 7 1
7 8 1
8 9 1
9 10 1000000000000
1 10
2 9
10 3
5 6
1 2
9 10
~~~
~~~output
1000000000000 2
1 7
1000000000000 1
1 1
1000000000000 1
1000000000000 1
~~~
~~~input
6 5
2 1 999999999999
3 2 999999999999
4 3 1
5 4 999999999999
6 5 1
1 6
1 5
4 6
3 4
6 1
~~~
~~~output
999999999999 3
999999999999 3
999999999999 1
1 1
999999999999 3
~~~
<!-- @generator -->
~~~generator python3
import sys, random

def build_par(n, typ, rnd):
    par = [0] * (n + 1)
    for i in range(2, n + 1):
        if typ == "chain":
            par[i] = i - 1
        elif typ == "star":
            par[i] = 1
        elif typ == "deep":
            par[i] = i - 1 if rnd.random() < 0.92 else rnd.randint(1, i - 1)
        elif typ == "broom":
            h = n // 2
            par[i] = i - 1 if i <= h else rnd.randint(max(1, h - 1), h)
        else:
            par[i] = rnd.randint(1, i - 1)
    return par

def main():
    data = sys.stdin.read().split()
    seed = int(data[0]); n = int(data[1]); q = int(data[2])
    typ = data[3] if len(data) > 3 else "rand"
    wmode = data[4] if len(data) > 4 else "wide"
    rnd = random.Random()
    rnd.seed(seed)
    par = build_par(n, typ, rnd)

    W = 10 ** 12
    w = [0] * (n + 1)
    for i in range(2, n + 1):
        if wmode == "tie":
            w[i] = rnd.randint(1, 3)
        elif wmode == "same":
            w[i] = W
        elif wmode == "few":
            w[i] = rnd.choice([1, 500000000000, W])
        else:
            w[i] = rnd.randint(1, W)

    ch = [[] for _ in range(n + 1)]
    for i in range(2, n + 1):
        ch[par[i]].append(i)

    anc_pairs = []
    need = q // 2
    path = []
    st = [(1, 0)]
    while st:
        v, state = st.pop()
        if state == 0:
            path.append(v)
            st.append((v, 1))
            if len(path) > 1 and len(anc_pairs) < need and rnd.random() < 0.6:
                a = path[rnd.randint(0, len(path) - 2)]
                anc_pairs.append((a, v))
            for c in ch[v]:
                st.append((c, 0))
        else:
            path.pop()

    qs = []
    for a, b in anc_pairs[:need]:
        if rnd.random() < 0.5:
            qs.append((b, a))
        else:
            qs.append((a, b))
    while len(qs) < q:
        if rnd.random() < 0.04:
            v = rnd.randint(1, n); qs.append((v, v))
        else:
            qs.append((rnd.randint(1, n), rnd.randint(1, n)))
    rnd.shuffle(qs)

    lab = list(range(n + 1))
    tail = lab[2:]
    rnd.shuffle(tail)
    lab[2:] = tail

    out = ["%d %d" % (n, q)]
    edges = []
    for i in range(2, n + 1):
        a, b = lab[i], lab[par[i]]
        if rnd.random() < 0.5:
            a, b = b, a
        edges.append((a, b, w[i]))
    rnd.shuffle(edges)
    for a, b, ww in edges:
        out.append("%d %d %d" % (a, b, ww))
    for u, v in qs:
        out.append("%d %d" % (lab[u], lab[v]))
    sys.stdout.write("\n".join(out) + "\n")

main()
~~~
~~~solution python3
import sys

def main():
    data = sys.stdin.buffer.read().split()
    p = 0
    n = int(data[p]); p += 1
    q = int(data[p]); p += 1
    head = [0] * (n + 1)
    sz = 2 * n
    nxt = [0] * sz
    dst = [0] * sz
    wt = [0] * sz
    cnt = 1
    for _ in range(n - 1):
        a = int(data[p]); b = int(data[p + 1]); w = int(data[p + 2]); p += 3
        cnt += 1; dst[cnt] = b; wt[cnt] = w; nxt[cnt] = head[a]; head[a] = cnt
        cnt += 1; dst[cnt] = a; wt[cnt] = w; nxt[cnt] = head[b]; head[b] = cnt

    par = [0] * (n + 1)
    dep = [0] * (n + 1)
    pw = [0] * (n + 1)
    vis = bytearray(n + 1)
    vis[1] = 1
    stack = [1]
    while stack:
        v = stack.pop()
        dv = dep[v]
        e = head[v]
        while e:
            u = dst[e]
            if not vis[u]:
                vis[u] = 1
                par[u] = v
                dep[u] = dv + 1
                pw[u] = wt[e]
                stack.append(u)
            e = nxt[e]

    LOG = 1
    while (1 << LOG) <= n:
        LOG += 1

    up = [par]
    mxs = [pw]
    cns = [[0] + [1] * n]
    cns[0][0] = 0
    for _ in range(1, LOG):
        pu = up[-1]; pm = mxs[-1]; pc = cns[-1]
        nu = [pu[x] for x in pu]
        mm = [pm[x] for x in pu]
        mc = [pc[x] for x in pu]
        nm = [x if x >= y else y for x, y in zip(pm, mm)]
        nc = [(c1 if m1 > m2 else (c2 if m2 > m1 else c1 + c2))
              for m1, m2, c1, c2 in zip(pm, mm, pc, mc)]
        up.append(nu); mxs.append(nm); cns.append(nc)

    out = []
    ap = out.append
    for _ in range(q):
        u = int(data[p]); v = int(data[p + 1]); p += 2
        if u == v:
            ap("0 0")
            continue
        if dep[u] < dep[v]:
            u, v = v, u
        bm = 0; bc = 0
        d = dep[u] - dep[v]
        k = 0
        while d:
            if d & 1:
                m = mxs[k][u]
                if m > bm:
                    bm = m; bc = cns[k][u]
                elif m == bm:
                    bc += cns[k][u]
                u = up[k][u]
            d >>= 1
            k += 1
        if u != v:
            for k in range(LOG - 1, -1, -1):
                uk = up[k]
                if uk[u] != uk[v]:
                    mk = mxs[k]; ck = cns[k]
                    m = mk[u]
                    if m > bm:
                        bm = m; bc = ck[u]
                    elif m == bm:
                        bc += ck[u]
                    m = mk[v]
                    if m > bm:
                        bm = m; bc = ck[v]
                    elif m == bm:
                        bc += ck[v]
                    u = uk[u]; v = uk[v]
            for x in (u, v):
                m = pw[x]
                if m > bm:
                    bm = m; bc = 1
                elif m == bm:
                    bc += 1
        ap("%d %d" % (bm, bc))
    sys.stdout.write("\n".join(out) + "\n")

main()
~~~
~~~validator cpp
// 독립 구현: 희소 배열(binary lifting) 대신
// 무거운 간선 분해(HLD) + 세그먼트 트리((최댓값, 개수) 병합)로 경로 질의를 처리한다.
#include <bits/stdc++.h>
using namespace std;
typedef long long ll;

struct Node { ll mx; int c; };
static inline Node mrg(const Node& a, const Node& b) {
    if (a.mx > b.mx) return a;
    if (b.mx > a.mx) return b;
    Node r; r.mx = a.mx; r.c = a.c + b.c; return r;
}

int N;
vector<Node> seg;
int SZ;

void build(vector<Node>& base) {
    SZ = 1;
    while (SZ < (int)base.size()) SZ <<= 1;
    seg.assign(2 * SZ, (Node){0, 0});
    for (size_t i = 0; i < base.size(); i++) seg[SZ + i] = base[i];
    for (int i = SZ - 1; i >= 1; i--) seg[i] = mrg(seg[2 * i], seg[2 * i + 1]);
}
Node query(int l, int r) {           // inclusive
    Node res = (Node){0, 0};
    if (l > r) return res;
    l += SZ; r += SZ + 1;
    while (l < r) {
        if (l & 1) res = mrg(res, seg[l++]);
        if (r & 1) res = mrg(res, seg[--r]);
        l >>= 1; r >>= 1;
    }
    return res;
}

int main() {
    int n, q;
    if (scanf("%d %d", &n, &q) != 2) return 0;
    vector<int> head(n + 1, -1);
    int m = 2 * (n - 1 > 0 ? n - 1 : 1);
    vector<int> nxt(m), dst(m);
    vector<ll> wt(m);
    int ec = 0;
    for (int i = 0; i < n - 1; i++) {
        int a, b; ll w;
        scanf("%d %d %lld", &a, &b, &w);
        dst[ec] = b; wt[ec] = w; nxt[ec] = head[a]; head[a] = ec++;
        dst[ec] = a; wt[ec] = w; nxt[ec] = head[b]; head[b] = ec++;
    }
    vector<int> par(n + 1, 0), dep(n + 1, 0), sz(n + 1, 1), heavy(n + 1, 0), order;
    vector<ll> pw(n + 1, 0);
    order.reserve(n);
    {
        vector<char> vis(n + 1, 0);
        vector<int> st;
        st.push_back(1); vis[1] = 1;
        while (!st.empty()) {
            int v = st.back(); st.pop_back();
            order.push_back(v);
            for (int e = head[v]; e != -1; e = nxt[e]) {
                int u = dst[e];
                if (!vis[u]) {
                    vis[u] = 1; par[u] = v; dep[u] = dep[v] + 1; pw[u] = wt[e];
                    st.push_back(u);
                }
            }
        }
    }
    for (int i = (int)order.size() - 1; i >= 1; i--) {
        int v = order[i];
        sz[par[v]] += sz[v];
        if (heavy[par[v]] == 0 || sz[v] > sz[heavy[par[v]]]) heavy[par[v]] = v;
    }
    vector<int> hd(n + 1, 0), pos(n + 1, 0);
    int timer = 0;
    {
        vector<int> st;
        st.push_back(1); hd[1] = 1;
        while (!st.empty()) {
            int c = st.back(); st.pop_back();
            for (int v = c; v; v = heavy[v]) {
                hd[v] = c;
                pos[v] = timer++;
                for (int e = head[v]; e != -1; e = nxt[e]) {
                    int u = dst[e];
                    if (u != par[v] && u != heavy[v]) st.push_back(u);
                }
            }
        }
    }
    vector<Node> base(n);
    for (int v = 1; v <= n; v++) {
        Node nd; nd.mx = (v == 1 ? 0 : pw[v]); nd.c = (v == 1 ? 0 : 1);
        base[pos[v]] = nd;
    }
    build(base);
    string out; out.reserve(q * 12);
    char buf[64];
    for (int i = 0; i < q; i++) {
        int u, v; scanf("%d %d", &u, &v);
        Node res = (Node){0, 0};
        while (hd[u] != hd[v]) {
            if (dep[hd[u]] < dep[hd[v]]) swap(u, v);
            res = mrg(res, query(pos[hd[u]], pos[u]));
            u = par[hd[u]];
        }
        if (u != v) {
            if (dep[u] > dep[v]) swap(u, v);
            res = mrg(res, query(pos[u] + 1, pos[v]));
        }
        int len = sprintf(buf, "%lld %d\n", res.mx, res.c);
        out.append(buf, len);
    }
    fputs(out.c_str(), stdout);
    return 0;
}
~~~
~~~case
201 100000 100000 rand wide
~~~
~~~case
207 100000 100000 chain wide
~~~
~~~case
211 100000 100000 deep tie
~~~
~~~case
217 99999 100000 broom few
~~~
~~~case
223 100000 100000 star same
~~~
~~~case
229 100000 100000 rand tie
~~~
