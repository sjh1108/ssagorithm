---
title: 관측소 통신망 개통
difficulty: SILVER
tags: 유니온 파인드, 분리 집합, 그래프, 자료 구조
timeLimit: 4
memoryLimit: 256
isPublic: true
---
<!-- @description -->
산맥 능선을 따라 기상 관측소 $N$개가 세워졌다. 관측소에는 $1$번부터 $N$번까지 번호가 붙어 있다. 개통 전이라 관측소들은 서로 아무 연결도 없이 하나씩 따로 떨어져 있다.

이제 공사팀이 통신 케이블 $M$개를 **입력에 주어진 순서대로 하나씩** 설치한다. $i$번째로 설치하는 케이블은 관측소 $u_i$와 $v_i$를 직접 잇는다.

이미 설치된 케이블만 타고 이동해서 관측소 $a$에서 관측소 $b$로 신호를 보낼 수 있으면 두 관측소는 **같은 무리**에 속한다고 한다. 신호는 케이블을 양방향으로 오갈 수 있고 중간 관측소를 몇 번이든 거쳐 갈 수 있다. 아직 아무 케이블도 없는 처음에는 무리가 $N$개다.

케이블을 하나 설치할 때마다 무리의 개수는 그대로이거나 $1$만큼 줄어든다. 다음 두 경우에는 무리 개수가 줄지 않는다.

- 케이블이 잇는 두 관측소가 이미 같은 무리에 속해 있는 경우
- $u_i = v_i$인 경우. 관측소를 자기 자신에게 잇는 케이블도 공사 계획에 들어 있을 수 있는데, 이런 케이블은 아무것도 합치지 못한다.

또한 같은 관측소 쌍을 잇는 케이블이 여러 번 등장할 수도 있다.

$N$개의 관측소 전체가 **처음으로** 하나의 무리가 되는 순간, 그때 설치한 케이블의 번호를 구한다. 케이블 $M$개를 모두 설치하고도 하나의 무리가 되지 못하면 $-1$을 구한다.

#### 예시

관측소가 $6$개이고 케이블 $7$개를 다음 순서로 설치한다고 하자.

```
1: 1-2   2: 1-3   3: 2-3   4: 3-4   5: 4-5   6: 5-6   7: 2-5
```

케이블을 하나씩 설치할 때 무리가 변하는 모습은 다음과 같다.

| 케이블 번호 | 잇는 관측소 | 합쳐졌나 | 설치 직후의 무리 | 무리 개수 |
|---|---|---|---|---|
| (설치 전) | | | {1} {2} {3} {4} {5} {6} | 6 |
| 1 | 1 2 | O | {1,2} {3} {4} {5} {6} | 5 |
| 2 | 1 3 | O | {1,2,3} {4} {5} {6} | 4 |
| 3 | 2 3 | X | {1,2,3} {4} {5} {6} | 4 |
| 4 | 3 4 | O | {1,2,3,4} {5} {6} | 3 |
| 5 | 4 5 | O | {1,2,3,4,5} {6} | 2 |
| 6 | 5 6 | O | {1,2,3,4,5,6} | 1 |
| 7 | 2 5 | X | {1,2,3,4,5,6} | 1 |

$3$번 케이블은 이미 같은 무리인 $2$번과 $3$번을 이어 아무 변화도 없다. 무리 개수가 처음으로 $1$이 되는 시점은 $6$번 케이블을 설치했을 때이므로 답은 $6$이다. 그 뒤의 $7$번 케이블은 답에 영향을 주지 않는다.
<!-- @input -->
첫째 줄에 관측소의 수 $N$과 케이블의 수 $M$이 공백으로 구분되어 주어진다. ($2 \le N \le 200{,}000$, $1 \le M \le 300{,}000$)

다음 $M$개의 줄에 케이블이 잇는 두 관측소의 번호 $u_i$, $v_i$가 공백으로 구분되어 주어진다. ($1 \le u_i, v_i \le N$)

케이블은 주어진 순서대로 $1$번부터 $M$번까지 번호가 붙으며, 그 순서대로 설치된다. $u_i = v_i$일 수 있고, 같은 관측소 쌍이 여러 줄에 걸쳐 반복해서 주어질 수도 있다.
<!-- @output -->
모든 관측소가 처음으로 하나의 무리가 되는 순간에 설치한 케이블의 번호를 한 줄에 출력한다. 케이블을 전부 설치해도 하나의 무리가 되지 않으면 $-1$을 출력한다.
<!-- @testcases -->
~~~input sample
6 7
1 2
1 3
2 3
3 4
4 5
5 6
2 5
~~~
~~~output
6
~~~
~~~input
2 1
1 2
~~~
~~~output
1
~~~
~~~input
4 4
1 2
3 4
1 1
2 2
~~~
~~~output
-1
~~~
~~~input
3 7
1 1
2 2
1 2
2 1
3 3
1 2
2 3
~~~
~~~output
7
~~~
~~~input
4 9
1 2
2 3
3 4
1 3
2 4
1 4
1 1
4 4
2 2
~~~
~~~output
3
~~~
~~~input
5 4
1 2
1 3
1 4
1 5
~~~
~~~output
4
~~~
~~~input
7 6
3 4
6 7
1 2
5 6
2 3
4 5
~~~
~~~output
6
~~~
<!-- @generator -->
생성기 stdin: `시드 N M 유형`
유형은 rand(랜덤 연결 그래프), chain(사슬을 무작위 순서로), disconnect(끝까지 연결 안 됨), earlydup(앞쪽에서 이미 연결되고 뒤는 전부 중복·자기 루프).
~~~generator python3
import sys, random

def main():
    data = sys.stdin.read().split()
    seed = int(data[0]); n = int(data[1]); m = int(data[2])
    typ = data[3] if len(data) > 3 else "rand"
    random.seed(seed)

    # 정점 번호를 무작위로 섞어 "번호가 작을수록 먼저 합쳐진다" 같은 규칙성을 없앤다
    lab = list(range(n + 1))
    tail = lab[1:]
    random.shuffle(tail)
    lab[1:] = tail

    edges = []
    if typ == "chain":
        # 사슬 간선 n-1 개를 무작위 순서로 설치 -> 답은 항상 마지막 케이블
        for i in range(2, n + 1):
            edges.append((lab[i - 1], lab[i]))
        random.shuffle(edges)
    elif typ == "disconnect":
        # lab[n] 을 고립시켜 어떤 케이블을 다 설치해도 하나가 되지 못하게 한다
        for i in range(2, n):
            p = random.randint(1, i - 1)
            edges.append((lab[p], lab[i]))
        while len(edges) < m:
            edges.append((lab[random.randint(1, n - 1)], lab[random.randint(1, n - 1)]))
        random.shuffle(edges)
    elif typ == "earlydup":
        # 신장 트리 간선이 앞쪽에 몰려 있고 나머지는 전부 중복 간선·자기 루프
        tree = []
        for i in range(2, n + 1):
            p = random.randint(1, i - 1)
            tree.append((lab[p], lab[i]))
        random.shuffle(tree)
        edges = tree[:]
        while len(edges) < m:
            if random.random() < 0.2:
                x = lab[random.randint(1, n)]
                edges.append((x, x))
            else:
                edges.append(tree[random.randrange(len(tree))])
    else:
        # 랜덤 신장 트리 + 랜덤 여분 간선(자기 루프 포함)을 전부 섞는다
        tree = []
        for i in range(2, n + 1):
            p = random.randint(1, i - 1)
            tree.append((lab[p], lab[i]))
        edges = tree[:]
        while len(edges) < m:
            if random.random() < 0.02:
                x = lab[random.randint(1, n)]
                edges.append((x, x))
            else:
                edges.append((lab[random.randint(1, n)], lab[random.randint(1, n)]))
        random.shuffle(edges)

    out = [str(n) + " " + str(len(edges))]
    for u, v in edges:
        if random.random() < 0.5:
            u, v = v, u
        out.append(str(u) + " " + str(v))
    sys.stdout.write("\n".join(out) + "\n")

main()
~~~
~~~solution python3
import sys

def main():
    data = sys.stdin.buffer.read().split()
    n = int(data[0]); m = int(data[1])
    parent = list(range(n + 1))
    size = [1] * (n + 1)
    comp = n          # 남은 무리 개수. 실제로 합쳐질 때만 1 줄어든다
    ans = -1
    idx = 2
    for i in range(1, m + 1):
        u = int(data[idx]); v = int(data[idx + 1]); idx += 2
        # find(u) — 경로 압축
        r = u
        while parent[r] != r:
            r = parent[r]
        while parent[u] != r:
            parent[u], u = r, parent[u]
        ru = r
        # find(v) — 경로 압축
        r = v
        while parent[r] != r:
            r = parent[r]
        while parent[v] != r:
            parent[v], v = r, parent[v]
        rv = r
        if ru != rv:
            # union by size — 큰 쪽에 작은 쪽을 붙인다
            if size[ru] < size[rv]:
                ru, rv = rv, ru
            parent[rv] = ru
            size[ru] += size[rv]
            comp -= 1
            if comp == 1:
                ans = i
                break
    sys.stdout.write(str(ans) + "\n")

main()
~~~
~~~validator cpp
// 독립 구현: 유니온 파인드를 전혀 쓰지 않는다.
// "앞에서 k 개의 케이블만 설치했을 때 전부 연결되는가" 는 k 에 대해 단조이므로
// 간선 번호에 대해 이분 탐색하고, 판정은 매번 인접 리스트를 새로 만들어 반복문 BFS 로 한다.
// 시간복잡도 O((N+M) log M), 재귀 없음.
#include <cstdio>
#include <vector>
using namespace std;

static int N, M;
static vector<int> EU, EV;
static vector<int> headArr, nxtArr, dstArr, que;
static vector<char> vis;

static bool connectedWithFirst(int k) {
    for (int i = 0; i <= N; i++) headArr[i] = -1;
    int c = 0;
    for (int i = 0; i < k; i++) {
        int u = EU[i], v = EV[i];
        dstArr[c] = v; nxtArr[c] = headArr[u]; headArr[u] = c; c++;
        dstArr[c] = u; nxtArr[c] = headArr[v]; headArr[v] = c; c++;
    }
    for (int i = 0; i <= N; i++) vis[i] = 0;
    int qh = 0, qt = 0, cnt = 0;
    que[qt++] = 1; vis[1] = 1; cnt = 1;
    while (qh < qt) {
        int v = que[qh++];
        for (int e = headArr[v]; e != -1; e = nxtArr[e]) {
            int u = dstArr[e];
            if (!vis[u]) { vis[u] = 1; cnt++; que[qt++] = u; }
        }
    }
    return cnt == N;
}

static char buf[1 << 16];
static int blen = 0, bptr = 0;
static int gc() {
    if (bptr == blen) { blen = (int)fread(buf, 1, sizeof(buf), stdin); bptr = 0; if (blen <= 0) return -1; }
    return buf[bptr++];
}
static int readInt() {
    int c = gc();
    while (c == ' ' || c == '\n' || c == '\r' || c == '\t') c = gc();
    int x = 0;
    while (c >= '0' && c <= '9') { x = x * 10 + (c - '0'); c = gc(); }
    return x;
}

int main() {
    N = readInt(); M = readInt();
    EU.resize(M); EV.resize(M);
    for (int i = 0; i < M; i++) { EU[i] = readInt(); EV[i] = readInt(); }
    headArr.assign(N + 1, -1);
    nxtArr.assign(2 * M, -1);
    dstArr.assign(2 * M, 0);
    que.assign(N + 1, 0);
    vis.assign(N + 1, 0);

    if (!connectedWithFirst(M)) { printf("-1\n"); return 0; }
    int lo = 1, hi = M;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (connectedWithFirst(mid)) hi = mid; else lo = mid + 1;
    }
    printf("%d\n", lo);
    return 0;
}
~~~
~~~case
1 200000 300000 rand
~~~
~~~case
7 200000 199999 chain
~~~
~~~case
13 200000 300000 disconnect
~~~
~~~case
23 50000 300000 earlydup
~~~
