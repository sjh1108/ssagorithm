---
title: 상위 기지 판정
difficulty: SILVER
tags: 트리, 그래프 탐색, DFS, 오일러 경로
timeLimit: 2
memoryLimit: 256
isPublic: true
---
<!-- @description -->
남극 빙하 아래에 조사 기지 $N$개가 있다. 기지에는 $1$번부터 $N$번까지 번호가 붙어 있고, 얼음 터널 $N-1$개가 기지들을 잇는다. 터널만 이용해 어떤 기지에서 다른 어떤 기지로도 갈 수 있다. $1$번 기지가 본부다.

터널의 개수가 $N-1$개이고 전부 연결되어 있으므로, 기지 $v$에서 본부까지 가는 경로는 하나뿐이다. 이 경로 위에 있는 기지 중 $v$ 자신을 뺀 나머지를 $v$의 **상위 기지**라고 한다. 본부는 자기 자신을 뺀 모든 기지의 상위 기지이고, 어떤 기지도 자기 자신의 상위 기지가 아니다.

관제실이 질의 $Q$개를 보낸다. 각 질의는 기지 번호 두 개 $u$, $v$로 이루어지며, $u$가 $v$의 상위 기지인지 답해야 한다.

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

- $u = 1$, $v = 7$: $7$에서 본부까지의 경로는 $7 \to 6 \to 3 \to 1$이고 여기에 $1$이 있으므로 `YES`.
- $u = 2$, $v = 5$: $5 \to 2 \to 1$ 위에 $2$가 있으므로 `YES`.
- $u = 3$, $v = 4$: $4 \to 2 \to 1$ 위에 $3$이 없으므로 `NO`.
- $u = 6$, $v = 6$: 자기 자신은 상위 기지가 아니므로 `NO`.
- $u = 7$, $v = 6$: $6 \to 3 \to 1$ 위에 $7$이 없으므로 `NO`. 방향이 반대이면 답이 달라진다.
- $u = 3$, $v = 8$: $8 \to 6 \to 3 \to 1$ 위에 $3$이 있으므로 `YES`.
<!-- @input -->
첫째 줄에 기지의 수 $N$과 질의의 수 $Q$가 공백으로 구분되어 주어진다. ($2 \le N \le 100{,}000$, $1 \le Q \le 100{,}000$)

다음 $N-1$개의 줄에 터널이 잇는 두 기지의 번호 $a$, $b$가 주어진다. ($1 \le a, b \le N$, $a \ne b$) 같은 터널이 두 번 주어지지는 않으며, 주어진 터널로 모든 기지가 연결된다.

다음 $Q$개의 줄에 질의 $u$, $v$가 주어진다. ($1 \le u, v \le N$) $u$와 $v$가 같을 수도 있다.

터널이 주어지는 순서, 그리고 한 줄에서 두 기지가 적히는 순서는 정해져 있지 않다. 트리가 한 줄로 길게 이어진 모양이어서 깊이가 $100{,}000$에 이를 수도 있다.
<!-- @output -->
각 질의마다 한 줄씩, $u$가 $v$의 상위 기지이면 `YES`를, 아니면 `NO`를 출력한다. 질의가 주어진 순서대로 출력한다.
<!-- @testcases -->
~~~input sample
8 6
1 2
1 3
2 4
2 5
3 6
6 7
6 8
1 7
2 5
3 4
6 6
7 6
3 8
~~~
~~~output
YES
YES
NO
NO
NO
YES
~~~
~~~input
2 3
1 2
1 2
2 1
1 1
~~~
~~~output
YES
NO
NO
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
1 2
1 3
2 3
10 2
3 10
5 5
~~~
~~~output
YES
YES
NO
YES
NO
NO
~~~
~~~input
7 5
1 2
1 3
1 4
1 5
1 6
1 7
1 4
4 1
2 3
7 7
1 2
~~~
~~~output
YES
NO
NO
NO
YES
~~~
~~~input
6 6
2 1
3 2
4 2
5 4
6 5
1 6
2 6
4 6
6 4
3 5
5 3
~~~
~~~output
YES
YES
YES
NO
NO
NO
~~~
~~~input
5 5
1 2
2 3
3 4
4 5
1 1
2 2
3 3
4 4
5 5
~~~
~~~output
NO
NO
NO
NO
NO
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
        if rnd.random() < 0.25:
            qs.append((b, a))          # reversed: almost always NO
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

    tin = [0] * (n + 1)
    tout = [0] * (n + 1)
    timer = 0
    it = head[:]
    stack = [1]
    tin[1] = timer = 1
    par = [0] * (n + 1)
    while stack:
        v = stack[-1]
        e = it[v]
        adv = False
        while e:
            u = dst[e]
            e = nxt[e]
            if u != par[v] and tin[u] == 0:
                it[v] = e
                par[u] = v
                timer += 1
                tin[u] = timer
                stack.append(u)
                adv = True
                break
        if adv:
            continue
        it[v] = 0
        timer += 1
        tout[v] = timer
        stack.pop()

    res = []
    for _ in range(q):
        u = int(data[p]); p += 1
        v = int(data[p]); p += 1
        if u != v and tin[u] < tin[v] and tout[v] < tout[u]:
            res.append("YES")
        else:
            res.append("NO")
    sys.stdout.write("\n".join(res) + "\n")

main()
~~~
~~~validator cpp
// 독립 구현: 오일러 방문시각 대신 희소 배열(binary lifting)로
// v 를 depth[v]-depth[u] 만큼 위로 올려 u 와 같아지는지 확인한다.
#include <bits/stdc++.h>
using namespace std;

int main() {
    int n, q;
    if (scanf("%d %d", &n, &q) != 2) return 0;
    vector<vector<int>> g(n + 1);
    for (int i = 0; i < n - 1; i++) {
        int a, b; scanf("%d %d", &a, &b);
        g[a].push_back(b);
        g[b].push_back(a);
    }
    int LOG = 1;
    while ((1 << LOG) < n) LOG++;
    LOG++;
    vector<vector<int>> up(LOG, vector<int>(n + 1, 0));
    vector<int> dep(n + 1, 0), order;
    order.reserve(n);
    vector<char> vis(n + 1, 0);
    vector<int> st;
    st.push_back(1); vis[1] = 1; up[0][1] = 0; dep[1] = 0;
    while (!st.empty()) {
        int v = st.back(); st.pop_back();
        order.push_back(v);
        for (int u : g[v]) if (!vis[u]) {
            vis[u] = 1; up[0][u] = v; dep[u] = dep[v] + 1;
            st.push_back(u);
        }
    }
    for (int k = 1; k < LOG; k++)
        for (int v = 1; v <= n; v++)
            up[k][v] = up[k - 1][up[k - 1][v]];
    string out;
    out.reserve(q * 4);
    for (int i = 0; i < q; i++) {
        int u, v; scanf("%d %d", &u, &v);
        bool ok = false;
        if (u != v && dep[u] < dep[v]) {
            int w = v, d = dep[v] - dep[u];
            for (int k = 0; k < LOG; k++) if (d >> k & 1) w = up[k][w];
            ok = (w == u);
        }
        out += ok ? "YES\n" : "NO\n";
    }
    fputs(out.c_str(), stdout);
    return 0;
}
~~~
~~~case
1 100000 100000 rand
~~~
~~~case
7 100000 100000 chain
~~~
~~~case
13 100000 100000 deep
~~~
~~~case
21 99999 100000 broom
~~~
~~~case
31 100000 100000 star
~~~
