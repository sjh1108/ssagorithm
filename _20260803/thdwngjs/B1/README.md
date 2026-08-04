---
title: 모든 방의 대피 시간
difficulty: GOLD
tags: 트리, 트리 DP, 리루팅, 트리의 지름
timeLimit: 4
memoryLimit: 512
isPublic: true
---
<!-- @description -->
지하 연구소는 방 $N$개와 회랑 $N-1$개로 이루어져 있다. 방에는 1번부터 $N$번까지 번호가 붙어 있고, 각 회랑은 서로 다른 두 방을 잇는다. 어느 방에서 출발하든 회랑만 따라가면 다른 모든 방에 갈 수 있고, 같은 회랑을 두 번 지나지 않고 같은 방으로 되돌아오는 길은 없다. $j$번째 회랑을 한 번 지나는 데는 $w_j$ 분이 걸린다.

두 방 사이의 소요 시간은 그 둘을 잇는 유일한 회랑 경로의 소요 시간 합이다.

안전 규정은 이렇게 정해져 있다. 어떤 방에서 사고가 나면 **연구소의 모든 인원이 그 방에 모여야** 하고, 그때까지 걸리는 시간은 그 방에서 가장 멀리 떨어진 방에 있던 사람이 도착하는 시간이다.

모든 방 $v$ 에 대해, $v$번 방에서 가장 멀리 떨어진 방까지의 소요 시간을 구한다.

#### 예시

방이 5개이고 회랑이 다음과 같다고 하자.

```
1 - 2 (3분)
1 - 3 (1분)
3 - 4 (4분)
3 - 5 (2분)
```

방마다 다른 모든 방까지의 소요 시간을 재보면 다음과 같다.

| 출발 방 | 1번 | 2번 | 3번 | 4번 | 5번 | 최댓값 |
|---|---|---|---|---|---|---|
| 1 | 0 | 3 | 1 | 5 | 3 | $5$ |
| 2 | 3 | 0 | 4 | 8 | 6 | $8$ |
| 3 | 1 | 4 | 0 | 4 | 2 | $4$ |
| 4 | 5 | 8 | 4 | 0 | 6 | $8$ |
| 5 | 3 | 6 | 2 | 6 | 0 | $6$ |

따라서 답은 차례대로 $5, 8, 4, 8, 6$ 이다.
<!-- @input -->
첫째 줄에 방의 개수 $N$ 이 주어진다. ($1 \le N \le 120{,}000$)

둘째 줄부터 $N-1$개의 줄에 걸쳐 회랑의 정보가 `u v w` 형태로 주어진다. $u$번 방과 $v$번 방을 잇는 회랑을 지나는 데 $w$ 분이 걸린다는 뜻이다. ($1 \le u, v \le N$, $u \ne v$, $1 \le w \le 10^9$)

$N = 1$ 이면 회랑 정보가 한 줄도 주어지지 않는다.

주어지는 회랑은 항상 트리를 이룬다. 답은 32비트 정수 범위를 넘을 수 있다.
<!-- @output -->
$N$개의 줄에 걸쳐, $v = 1, 2, \dots, N$ 순서대로 $v$번 방에서 가장 멀리 떨어진 방까지의 소요 시간을 한 줄에 하나씩 출력한다.

$N = 1$ 이면 $0$ 한 줄을 출력한다.
<!-- @testcases -->
~~~input sample
5
1 2 3
1 3 1
3 4 4
3 5 2
~~~
~~~output
5
8
4
8
6
~~~
~~~input
1
~~~
~~~output
0
~~~
~~~input
2
2 1 1000000000
~~~
~~~output
1000000000
1000000000
~~~
~~~input
5
1 2 1000000000
2 3 1000000000
3 4 1000000000
4 5 1000000000
~~~
~~~output
4000000000
3000000000
2000000000
3000000000
4000000000
~~~
~~~input
6
1 2 5
1 3 5
1 4 5
1 5 5
1 6 5
~~~
~~~output
5
10
10
10
10
10
~~~
~~~input
7
4 2 2
2 1 3
1 3 3
3 6 2
6 5 4
5 7 1
~~~
~~~output
10
13
8
15
14
10
15
~~~
<!-- @generator -->
~~~generator python3
import sys, random
tok = sys.stdin.read().split()
seed = int(tok[0]); n = int(tok[1]); mode = tok[2]
wmax = int(tok[3]) if len(tok) > 3 else 10**6
random.seed(seed)
par = [0] * (n + 1)
if mode == 'path':
    for v in range(2, n + 1): par[v] = v - 1
elif mode == 'star':
    for v in range(2, n + 1): par[v] = 1
elif mode == 'cater':
    spine = max(1, n // 2)
    for v in range(2, n + 1):
        par[v] = v - 1 if v <= spine else random.randint(1, spine)
elif mode == 'bin':
    for v in range(2, n + 1): par[v] = v // 2
elif mode == 'shallow':
    for v in range(2, n + 1): par[v] = random.randint(max(1, v - 3), v - 1)
else:
    for v in range(2, n + 1): par[v] = random.randint(1, v - 1)
perm = list(range(1, n + 1))
random.shuffle(perm)
lab = [0] * (n + 1)
for i, v in enumerate(perm): lab[i + 1] = v
edges = []
for v in range(2, n + 1):
    w = random.randint(1, wmax)
    a, b = lab[v], lab[par[v]]
    if random.random() < 0.5: a, b = b, a
    edges.append((a, b, w))
random.shuffle(edges)
out = [str(n)]
for a, b, w in edges: out.append('%d %d %d' % (a, b, w))
sys.stdout.write('\n'.join(out) + '\n')
~~~
~~~solution python3
import sys
def main():
    d = sys.stdin.buffer.read().split()
    n = int(d[0])
    if n == 1:
        sys.stdout.write('0\n'); return
    m = n - 1
    deg = [0] * (n + 2)
    us = [0] * m; vs = [0] * m; ws = [0] * m
    p = 1
    for i in range(m):
        a = int(d[p]); b = int(d[p + 1]); w = int(d[p + 2]); p += 3
        us[i] = a; vs[i] = b; ws[i] = w
        deg[a] += 1; deg[b] += 1
    start = [0] * (n + 2)
    for v in range(1, n + 1):
        start[v + 1] = start[v] + deg[v]
    pos = start[:]
    adjv = [0] * (2 * m); adjw = [0] * (2 * m)
    for i in range(m):
        a = us[i]; b = vs[i]; w = ws[i]
        adjv[pos[a]] = b; adjw[pos[a]] = w; pos[a] += 1
        adjv[pos[b]] = a; adjw[pos[b]] = w; pos[b] += 1

    par = [0] * (n + 1)
    order = [0] * n
    cnt = 0
    stack = [1]
    par[1] = 0
    visited = bytearray(n + 1)
    visited[1] = 1
    while stack:
        v = stack.pop()
        order[cnt] = v; cnt += 1
        for j in range(start[v], start[v + 1]):
            u = adjv[j]
            if not visited[u]:
                visited[u] = 1
                par[u] = v
                stack.append(u)

    down = [0] * (n + 1)
    for i in range(n - 1, -1, -1):
        v = order[i]
        b = 0
        pv = par[v]
        for j in range(start[v], start[v + 1]):
            u = adjv[j]
            if u != pv:
                c = down[u] + adjw[j]
                if c > b: b = c
        down[v] = b

    up = [0] * (n + 1)
    for i in range(n):
        v = order[i]
        pv = par[v]
        b1 = 0; b2 = 0; i1 = 0
        for j in range(start[v], start[v + 1]):
            u = adjv[j]
            if u != pv:
                c = down[u] + adjw[j]
                if c > b1:
                    b2 = b1; b1 = c; i1 = u
                elif c > b2:
                    b2 = c
        uv = up[v]
        for j in range(start[v], start[v + 1]):
            u = adjv[j]
            if u != pv:
                alt = b2 if u == i1 else b1
                t = uv if uv > alt else alt
                up[u] = t + adjw[j]

    out = []
    for v in range(1, n + 1):
        a = down[v]; b = up[v]
        out.append(a if a > b else b)
    sys.stdout.write('\n'.join(map(str, out)) + '\n')
main()
~~~
~~~validator cpp
#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
int n;
vector<int> hd, nxt, to_, wt;
void bfs(int src, vector<ll>& dist){
    dist.assign(n+1,-1); dist[src]=0;
    vector<int> st; st.push_back(src);
    while(!st.empty()){ int v=st.back(); st.pop_back();
        for(int e=hd[v]; e!=-1; e=nxt[e]){ int u=to_[e]; if(dist[u]<0){ dist[u]=dist[v]+wt[e]; st.push_back(u);} } }
}
int main(){
    if(scanf("%d",&n)!=1) return 0;
    hd.assign(n+1,-1);
    for(int i=0;i<n-1;i++){ int a,b,w; if(scanf("%d %d %d",&a,&b,&w)!=3) return 0;
        to_.push_back(b); wt.push_back(w); nxt.push_back(hd[a]); hd[a]=(int)to_.size()-1;
        to_.push_back(a); wt.push_back(w); nxt.push_back(hd[b]); hd[b]=(int)to_.size()-1; }
    vector<ll> d0,dA,dB;
    bfs(1,d0);
    int A=1; for(int v=1;v<=n;v++) if(d0[v]>d0[A]) A=v;
    bfs(A,dA);
    int B=A; for(int v=1;v<=n;v++) if(dA[v]>dA[B]) B=v;
    bfs(B,dB);
    string out; out.reserve(1<<21); char buf[32];
    for(int v=1;v<=n;v++){ ll e=max(dA[v],dB[v]); int len=sprintf(buf,"%lld\n",e); out.append(buf,len); }
    fputs(out.c_str(),stdout);
}
~~~
~~~case
1 120000 rand 1000000000
~~~
~~~case
2 120000 path 1000000000
~~~
~~~case
3 120000 star 1000000000
~~~
~~~case
4 120000 cater 1000000000
~~~
~~~case
5 120000 bin 1000000
~~~
~~~case
6 120000 shallow 1000000000
~~~
