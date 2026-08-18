---
title: 공통 상위 기지
difficulty: GOLD
tags: 트리, 최소 공통 조상, 희소 배열, 그래프 탐색
timeLimit: 6
memoryLimit: 512
isPublic: true
---
<!-- @description -->
남극 빙하 아래에 조사 기지 $N$개가 있다. 기지에는 $1$번부터 $N$번까지 번호가 붙어 있고, 얼음 터널 $N-1$개가 기지들을 잇는다. 터널만 이용해 어떤 기지에서 다른 어떤 기지로도 갈 수 있다. $1$번 기지가 본부다.

기지 $v$에서 본부까지 가는 경로는 하나뿐이다. 이 경로 위에 있는 기지(자기 자신 $v$도 포함한다)를 $v$의 **상위 기지**라고 하자. 기지 $u$와 기지 $v$의 상위 기지에 모두 속하는 기지 중에서 본부로부터 가장 멀리 떨어진 기지를 $u$와 $v$의 **공통 상위 기지**라고 한다.

두 기지의 상위 기지 목록에는 항상 본부가 들어 있으므로 공통 상위 기지는 반드시 존재하고, 유일하다.

질의 $Q$개가 주어진다. 각 질의마다 두 기지의 공통 상위 기지를 구한다.

#### 예시

기지가 $8$개이고 터널이 다음과 같다고 하자.

```
1 - 2 - 4
|   |
|   +- 5
|
+- 3 - 6 - 7
        |
        +- 8
```

- $u = 4$, $v = 5$: $4$의 상위 기지는 $\{4, 2, 1\}$, $5$의 상위 기지는 $\{5, 2, 1\}$이다. 공통은 $\{2, 1\}$이고 이 중 본부에서 더 먼 것은 $2$이다.
- $u = 4$, $v = 7$: 공통은 $\{1\}$뿐이므로 답은 $1$이다.
- $u = 7$, $v = 8$: 공통은 $\{6, 3, 1\}$이고 답은 $6$이다.
- $u = 6$, $v = 6$: 자기 자신도 상위 기지에 포함되므로 답은 $6$이다.
- $u = 5$, $v = 2$: $2$가 $5$의 상위 기지이므로 답은 $2$이다.
<!-- @input -->
첫째 줄에 기지의 수 $N$과 질의의 수 $Q$가 공백으로 구분되어 주어진다. ($2 \le N \le 100{,}000$, $1 \le Q \le 100{,}000$)

다음 $N-1$개의 줄에 터널이 잇는 두 기지의 번호 $a$, $b$가 주어진다. ($1 \le a, b \le N$, $a \ne b$) 같은 터널이 두 번 주어지지는 않으며, 주어진 터널로 모든 기지가 연결된다.

다음 $Q$개의 줄에 질의 $u$, $v$가 주어진다. ($1 \le u, v \le N$) $u$와 $v$가 같을 수도 있고, 한쪽이 다른 쪽의 상위 기지일 수도 있다.

터널이 주어지는 순서, 그리고 한 줄에서 두 기지가 적히는 순서는 정해져 있지 않다. 트리가 한 줄로 길게 이어진 모양이어서 깊이가 $100{,}000$에 이를 수도 있다.
<!-- @output -->
각 질의마다 한 줄씩, 두 기지의 공통 상위 기지의 번호를 출력한다. 질의가 주어진 순서대로 출력한다.
<!-- @testcases -->
~~~input sample
8 5
1 2
1 3
2 4
2 5
3 6
6 7
6 8
4 5
4 7
7 8
6 6
5 2
~~~
~~~output
2
1
6
6
2
~~~
~~~input
2 3
2 1
1 2
2 2
1 1
~~~
~~~output
1
2
1
~~~
~~~input
10 6
10 1
9 10
8 9
7 8
6 7
5 6
4 5
3 4
2 3
2 10
2 1
5 2
1 1
7 3
2 2
~~~
~~~output
10
1
5
1
7
2
~~~
~~~input
7 5
1 2
1 3
1 4
1 5
1 6
1 7
2 3
4 4
1 6
7 2
5 1
~~~
~~~output
1
4
1
1
1
~~~
~~~input
9 6
1 2
2 3
3 4
1 5
5 6
6 7
7 8
8 9
4 9
4 2
9 5
3 3
6 4
2 9
~~~
~~~output
1
2
5
3
1
1
~~~
~~~input
6 5
3 1
2 3
4 2
5 4
6 5
6 1
6 3
4 5
5 4
6 6
~~~
~~~output
1
3
4
4
6
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
    rnd = random.Random()
    rnd.seed(seed)
    par = build_par(n, typ, rnd)

    ch = [[] for _ in range(n + 1)]
    for i in range(2, n + 1):
        ch[par[i]].append(i)

    # ancestor-descendant pairs collected along the DFS stack
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
        r = rnd.random()
        if r < 0.06:
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
        edges.append((a, b))
    rnd.shuffle(edges)
    for a, b in edges:
        out.append("%d %d" % (a, b))
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
    nxt = [0] * (2 * n)
    dst = [0] * (2 * n)
    cnt = 1
    for _ in range(n - 1):
        a = int(data[p]); p += 1
        b = int(data[p]); p += 1
        cnt += 1; dst[cnt] = b; nxt[cnt] = head[a]; head[a] = cnt
        cnt += 1; dst[cnt] = a; nxt[cnt] = head[b]; head[b] = cnt

    par = [0] * (n + 1)
    dep = [0] * (n + 1)
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
                stack.append(u)
            e = nxt[e]

    LOG = 1
    while (1 << LOG) <= n:
        LOG += 1
    up = [par]
    for _ in range(1, LOG):
        prev = up[-1]
        up.append([prev[prev[v]] for v in range(n + 1)])

    out = []
    ap = out.append
    for _ in range(q):
        u = int(data[p]); p += 1
        v = int(data[p]); p += 1
        if dep[u] < dep[v]:
            u, v = v, u
        d = dep[u] - dep[v]
        k = 0
        while d:
            if d & 1:
                u = up[k][u]
            d >>= 1
            k += 1
        if u != v:
            for k in range(LOG - 1, -1, -1):
                uk = up[k]
                if uk[u] != uk[v]:
                    u = uk[u]
                    v = uk[v]
            u = par[u]
        ap(u)
    sys.stdout.write("\n".join(map(str, out)) + "\n")

main()
~~~
~~~validator cpp
// 독립 구현: 희소 배열로 한 칸씩 올라가는 대신,
// 오일러 경로를 펼친 뒤 깊이에 대한 구간 최솟값(sparse table)으로 LCA 를 구한다.
#include <bits/stdc++.h>
using namespace std;

int main() {
    int n, q;
    if (scanf("%d %d", &n, &q) != 2) return 0;
    vector<int> head(n + 1, -1), nxt(2 * (n > 1 ? n - 1 : 1)), dst(2 * (n > 1 ? n - 1 : 1));
    int ec = 0;
    for (int i = 0; i < n - 1; i++) {
        int a, b; scanf("%d %d", &a, &b);
        dst[ec] = b; nxt[ec] = head[a]; head[a] = ec++;
        dst[ec] = a; nxt[ec] = head[b]; head[b] = ec++;
    }
    vector<int> first(n + 1, -1), euler;
    euler.reserve(2 * n);
    vector<int> dep(n + 1, 0), par(n + 1, 0), it(head);
    vector<char> vis(n + 1, 0);
    vector<int> st;
    st.push_back(1); vis[1] = 1;
    first[1] = 0; euler.push_back(1);
    while (!st.empty()) {
        int v = st.back();
        int e = it[v];
        bool moved = false;
        while (e != -1) {
            int u = dst[e];
            e = nxt[e];
            if (!vis[u]) {
                it[v] = e;
                vis[u] = 1; par[u] = v; dep[u] = dep[v] + 1;
                first[u] = (int)euler.size();
                euler.push_back(u);
                st.push_back(u);
                moved = true;
                break;
            }
        }
        if (moved) continue;
        it[v] = -1;
        st.pop_back();
        if (!st.empty()) euler.push_back(st.back());
    }
    int m = (int)euler.size();
    int LOG = 1;
    while ((1 << LOG) <= m) LOG++;
    vector<vector<int>> sp(LOG, vector<int>(m));
    for (int i = 0; i < m; i++) sp[0][i] = euler[i];
    for (int k = 1; k < LOG; k++) {
        int len = 1 << k;
        for (int i = 0; i + len <= m; i++) {
            int a = sp[k - 1][i], b = sp[k - 1][i + (len >> 1)];
            sp[k][i] = (dep[a] <= dep[b]) ? a : b;
        }
    }
    vector<int> lg(m + 1, 0);
    for (int i = 2; i <= m; i++) lg[i] = lg[i >> 1] + 1;
    string out;
    out.reserve(q * 7);
    char buf[16];
    for (int i = 0; i < q; i++) {
        int u, v; scanf("%d %d", &u, &v);
        int l = first[u], r = first[v];
        if (l > r) swap(l, r);
        int k = lg[r - l + 1];
        int a = sp[k][l], b = sp[k][r - (1 << k) + 1];
        int res = (dep[a] <= dep[b]) ? a : b;
        int len = sprintf(buf, "%d\n", res);
        out.append(buf, len);
    }
    fputs(out.c_str(), stdout);
    return 0;
}
~~~
~~~case
101 100000 100000 rand
~~~
~~~case
107 100000 100000 chain
~~~
~~~case
113 100000 100000 deep
~~~
~~~case
121 99999 100000 broom
~~~
~~~case
131 100000 100000 star
~~~
