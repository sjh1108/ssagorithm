---
title: 연구소 회랑 답사
difficulty: SILVER
tags: 트리, 그래프 탐색, DFS, BFS
timeLimit: 2
memoryLimit: 512
isPublic: true
---
<!-- @description -->
지하 연구소는 방 $N$개와 회랑 $N-1$개로 이루어져 있다. 방에는 1번부터 $N$번까지 번호가 붙어 있고, 각 회랑은 서로 다른 두 방을 잇는다. 어느 방에서 출발하든 회랑만 따라가면 다른 모든 방에 갈 수 있고, 같은 회랑을 두 번 지나지 않고 같은 방으로 되돌아오는 길은 없다.

$j$번째 회랑을 한 번 지나는 데는 $w_j$ 분이 걸린다.

출입구는 1번 방에 있다. 1번 방에서 출발해 다른 방으로 갈 때 걸리는 최소 시간은, 두 방을 잇는 유일한 회랑 경로의 소요 시간을 모두 더한 값이다.

1번 방에서 **가장 오래 걸리는 방**까지의 소요 시간과 그 방의 번호를 구한다. 그런 방이 여러 개면 번호가 가장 작은 방을 답으로 한다.

#### 예시

방이 5개이고 회랑이 다음과 같다고 하자.

```
1 - 2 (3분)
1 - 3 (1분)
3 - 4 (4분)
3 - 5 (2분)
```

1번 방에서 각 방까지 걸리는 시간은 1번 $0$분, 2번 $3$분, 3번 $1$분, 4번 $1 + 4 = 5$분, 5번 $1 + 2 = 3$분이다. 가장 오래 걸리는 방은 4번이고 $5$분이 걸린다.
<!-- @input -->
첫째 줄에 방의 개수 $N$ 이 주어진다. ($1 \le N \le 120{,}000$)

둘째 줄부터 $N-1$개의 줄에 걸쳐 회랑의 정보가 `u v w` 형태로 주어진다. $u$번 방과 $v$번 방을 잇는 회랑을 지나는 데 $w$ 분이 걸린다는 뜻이다. ($1 \le u, v \le N$, $u \ne v$, $1 \le w \le 10^9$)

$N = 1$ 이면 회랑 정보가 한 줄도 주어지지 않는다.

주어지는 회랑은 항상 트리를 이룬다. 소요 시간의 합은 32비트 정수 범위를 넘을 수 있다.
<!-- @output -->
1번 방에서 가장 오래 걸리는 방까지의 소요 시간과 그 방의 번호를 공백으로 구분해 한 줄에 출력한다. 그런 방이 여럿이면 번호가 가장 작은 방을 출력한다.

$N = 1$ 이면 1번 방 자신이 답이므로 `0 1` 을 출력한다.
<!-- @testcases -->
~~~input sample
5
1 2 3
1 3 1
3 4 4
3 5 2
~~~
~~~output
5 4
~~~
~~~input
1
~~~
~~~output
0 1
~~~
~~~input
2
2 1 1000000000
~~~
~~~output
1000000000 2
~~~
~~~input
5
1 2 1000000000
2 3 1000000000
3 4 1000000000
4 5 1000000000
~~~
~~~output
4000000000 5
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
5 2
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
10 7
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
        sys.stdout.write('0 1\n'); return
    deg = [0] * (n + 1)
    us = [0] * (n - 1); vs = [0] * (n - 1); ws = [0] * (n - 1)
    p = 1
    for i in range(n - 1):
        a = int(d[p]); b = int(d[p + 1]); w = int(d[p + 2]); p += 3
        us[i] = a; vs[i] = b; ws[i] = w
        deg[a] += 1; deg[b] += 1
    start = [0] * (n + 2)
    for v in range(1, n + 1):
        start[v + 1] = start[v] + deg[v]
    pos = start[:]
    adjv = [0] * (2 * (n - 1))
    adjw = [0] * (2 * (n - 1))
    for i in range(n - 1):
        a = us[i]; b = vs[i]; w = ws[i]
        adjv[pos[a]] = b; adjw[pos[a]] = w; pos[a] += 1
        adjv[pos[b]] = a; adjw[pos[b]] = w; pos[b] += 1
    NEG = -1
    dist = [NEG] * (n + 1)
    dist[1] = 0
    stack = [1]
    while stack:
        v = stack.pop()
        dv = dist[v]
        for j in range(start[v], start[v + 1]):
            u = adjv[j]
            if dist[u] == NEG and u != 1:
                dist[u] = dv + adjw[j]
                stack.append(u)
    best = -1; bi = 1
    for v in range(1, n + 1):
        if dist[v] > best:
            best = dist[v]; bi = v
    sys.stdout.write('%d %d\n' % (best, bi))
main()
~~~
~~~validator cpp
#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
int main(){
    int n; if(scanf("%d",&n)!=1) return 0;
    vector<vector<pair<int,int>>> g(n+1);
    for(int i=0;i<n-1;i++){ int a,b,w; if(scanf("%d %d %d",&a,&b,&w)!=3) return 0; g[a].push_back({b,w}); g[b].push_back({a,w}); }
    const ll INF = LLONG_MAX/4;
    vector<ll> dist(n+1, INF);
    priority_queue<pair<ll,int>, vector<pair<ll,int>>, greater<pair<ll,int>>> pq;
    dist[1]=0; pq.push({0,1});
    while(!pq.empty()){
        pair<ll,int> top=pq.top(); pq.pop();
        ll d=top.first; int v=top.second;
        if(d>dist[v]) continue;
        for(size_t k=0;k<g[v].size();k++){ int u=g[v][k].first, w=g[v][k].second;
            if(d+w<dist[u]){ dist[u]=d+w; pq.push(make_pair(dist[u],u)); } }
    }
    ll best=-1; int bi=1;
    for(int v=1;v<=n;v++) if(dist[v]>best){ best=dist[v]; bi=v; }
    printf("%lld %d\n",best,bi);
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
6 2 shallow 1000000000
~~~
