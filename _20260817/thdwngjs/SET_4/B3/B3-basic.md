---
title: 컨베이어 벨트의 다음 높은 부품
difficulty: SILVER
tags: 스택, 자료 구조, 구현
timeLimit: 3
memoryLimit: 512
isPublic: true
---
<!-- @description -->
어느 공장의 컨베이어 벨트 위에 부품 $N$개가 일렬로 실려 가고 있다. 벨트가 나아가는 방향을 기준으로 왼쪽부터 $1$번, $2$번, $\dots$, $N$번 위치라고 하고, $i$번 위치에 놓인 부품의 높이를 $a_i$라고 하자.

벨트 옆에 선 검수 로봇은 부품 하나하나에 대해 **자기보다 오른쪽에 있으면서 높이가 자기보다 큰 부품 중 가장 왼쪽에 있는 부품의 위치**를 기록한다. 즉 $i$번 부품에 대해서는 $j > i$ 이면서 $a_j > a_i$ 를 만족하는 가장 작은 $j$ 를 기록한다. 그런 부품이 하나도 없으면 $-1$ 을 기록한다.

높이가 자기와 정확히 같은 부품은 "자기보다 큰" 부품이 아니므로 기록 대상이 될 수 없다.

모든 부품에 대해 로봇이 기록하는 값을 차례대로 구하여라.

#### 예시
부품이 $8$개이고 높이가 왼쪽부터 순서대로 $3, 1, 4, 1, 5, 9, 2, 6$ 이라고 하자.

- $1$번 부품(높이 $3$): 오른쪽으로 훑으면 $2$번(높이 $1$)은 작고, $3$번(높이 $4$)이 처음으로 더 크다. → $3$
- $2$번 부품(높이 $1$): $3$번(높이 $4$)이 처음으로 더 크다. → $3$
- $3$번 부품(높이 $4$): $4$번(높이 $1$)은 작고, $5$번(높이 $5$)이 처음으로 더 크다. → $5$
- $4$번 부품(높이 $1$): $5$번(높이 $5$)이 처음으로 더 크다. → $5$
- $5$번 부품(높이 $5$): $6$번(높이 $9$)이 처음으로 더 크다. → $6$
- $6$번 부품(높이 $9$): 오른쪽에 $9$보다 높은 부품이 없다. → $-1$
- $7$번 부품(높이 $2$): $8$번(높이 $6$)이 처음으로 더 크다. → $8$
- $8$번 부품(높이 $6$): 오른쪽에 부품 자체가 없다. → $-1$

따라서 기록되는 값은 순서대로 `3 3 5 5 6 -1 8 -1` 이다.
<!-- @input -->
첫째 줄에 부품의 개수 $N$이 주어진다.

둘째 줄에 $N$개의 정수 $a_1, a_2, \dots, a_N$이 공백으로 구분되어 주어진다. $a_i$는 왼쪽에서 $i$번째 부품의 높이다.

$1 \le N \le 300{,}000$

$1 \le a_i \le 10^9$
<!-- @output -->
$N$개의 정수를 한 줄에 공백으로 구분하여 출력한다.

$i$번째로 출력하는 값은 $i$번 부품에 대해 로봇이 기록하는 위치이며, $j > i$ 이면서 $a_j > a_i$ 인 가장 작은 $j$이다. 그런 $j$가 없으면 $-1$을 출력한다.

출력하는 값은 위치 번호 아니면 $-1$이므로 항상 $-1$ 이상 $N$ 이하이다.
<!-- @testcases -->
~~~input sample
8
3 1 4 1 5 9 2 6
~~~
~~~output
3 3 5 5 6 -1 8 -1
~~~
~~~input
1
1000000000
~~~
~~~output
-1
~~~
~~~input
5
1 2 3 4 5
~~~
~~~output
2 3 4 5 -1
~~~
~~~input
5
5 4 3 2 1
~~~
~~~output
-1 -1 -1 -1 -1
~~~
~~~input
4
7 7 7 7
~~~
~~~output
-1 -1 -1 -1
~~~
~~~input
10
1000000000 1 1000000000 999999999 1 2 1 1000000000 1 1000000000
~~~
~~~output
-1 3 -1 8 6 8 8 -1 10 -1
~~~
<!-- @generator -->
생성기 stdin: `시드 N 모드`
모드 0 = 완전 랜덤, 1 = 단조 감소(스택이 끝까지 비지 않는 최악), 2 = 값 범위 1~3(중복 대량), 3 = 단조 증가.
~~~generator python3
import sys, random

def main():
    seed, n, mode = map(int, sys.stdin.read().split())
    random.seed(seed)
    LIM = 10 ** 9
    if mode == 0:
        a = [random.randint(1, LIM) for _ in range(n)]
    elif mode == 1:
        cur = LIM
        a = []
        for _ in range(n):
            a.append(cur)
            cur -= random.randint(1, 3000)
    elif mode == 2:
        a = [random.randint(1, 3) for _ in range(n)]
    else:
        cur = 1
        a = []
        for _ in range(n):
            a.append(cur)
            cur += random.randint(1, 3000)
    out = [str(n), ' '.join(map(str, a))]
    sys.stdout.write('\n'.join(out) + '\n')

main()
~~~
~~~solution python3
import sys

def main():
    data = sys.stdin.buffer.read().split()
    n = int(data[0])
    a = list(map(int, data[1:1 + n]))
    ans = [-1] * n
    stack = []
    for i in range(n):
        cur = a[i]
        # 스택은 높이가 아래로 갈수록 큰 단조 감소 상태로 유지된다.
        # 현재 부품보다 낮은 부품들은 지금 위치가 곧 정답이므로 확정하고 꺼낸다.
        while stack and a[stack[-1]] < cur:
            ans[stack.pop()] = i + 1
        stack.append(i)
    sys.stdout.write(' '.join(map(str, ans)) + '\n')

main()
~~~
~~~validator cpp
// 모범답안(단조 스택)과 완전히 다른 접근: 오른쪽에서 왼쪽으로 훑으며
// 구간 최댓값 세그먼트 트리에 값을 넣고, "a_i 보다 큰 가장 왼쪽 위치"를
// 트리 내려가기(descent)로 O(log N) 에 찾는다. 스택을 쓰지 않는다.
#include <cstdio>
#include <cstdlib>
#include <vector>
using namespace std;

static char ibuf[1 << 16];
static int ipos = 0, ilen = 0;
static inline int gc() {
    if (ipos == ilen) {
        ilen = (int)fread(ibuf, 1, sizeof(ibuf), stdin);
        ipos = 0;
        if (ilen <= 0) return -1;
    }
    return (unsigned char)ibuf[ipos++];
}
static inline int readInt() {
    int c = gc();
    while (c != -1 && (c < '0' || c > '9') && c != '-') c = gc();
    int sgn = 1;
    if (c == '-') { sgn = -1; c = gc(); }
    long long x = 0;
    while (c >= '0' && c <= '9') { x = x * 10 + (c - '0'); c = gc(); }
    return (int)(x * sgn);
}

int main() {
    int n = readInt();
    vector<int> a(n + 1);
    for (int i = 1; i <= n; i++) a[i] = readInt();

    int sz = 1;
    while (sz < n) sz <<= 1;
    vector<int> tree(2 * sz, 0);   // 아직 넣지 않은 위치는 0 (a_i >= 1 이라 안전)

    vector<int> ans(n + 1, -1);
    for (int i = n; i >= 1; i--) {
        int v = a[i];
        // 이 시점에 트리에는 i+1..n 만 들어 있으므로 전체에서 찾아도 된다.
        if (tree[1] > v) {
            int node = 1;
            while (node < sz) {
                node <<= 1;
                if (tree[node] <= v) node |= 1;   // 왼쪽에 없으면 오른쪽으로
            }
            ans[i] = node - sz + 1;
        }
        int p = sz + i - 1;
        tree[p] = v;
        for (p >>= 1; p >= 1; p >>= 1) {
            int l = tree[p << 1], r = tree[(p << 1) | 1];
            tree[p] = l > r ? l : r;
        }
    }

    size_t cap = (size_t)n * 12 + 16;
    char *out = (char *)malloc(cap);
    size_t k = 0;
    char tmp[12];
    for (int i = 1; i <= n; i++) {
        if (i > 1) out[k++] = ' ';
        int v = ans[i];
        if (v < 0) { out[k++] = '-'; out[k++] = '1'; continue; }
        int t = 0;
        do { tmp[t++] = (char)('0' + v % 10); v /= 10; } while (v);
        while (t) out[k++] = tmp[--t];
    }
    out[k++] = '\n';
    fwrite(out, 1, k, stdout);
    return 0;
}
~~~
~~~case
1 300000 0
~~~
~~~case
2 300000 1
~~~
~~~case
3 300000 2
~~~
~~~case
4 300000 3
~~~
