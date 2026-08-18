---
title: 컨베이어 벨트 부품 검수
difficulty: GOLD
tags: 투 포인터, 슬라이딩 윈도우, 덱, 자료 구조
timeLimit: 5
memoryLimit: 512
isPublic: true
---
<!-- @description -->
공장 컨베이어 벨트 위에 부품 $N$개가 일렬로 놓여 흘러간다. 왼쪽부터 $i$번째 부품의 높이는 $a_i$다.

검수 로봇은 벨트 위에서 **연속한 구간**을 통째로 집어 하나의 상자에 담는다. 상자는 높이 차가 큰 부품이 섞이면 뚜껑이 닫히지 않으므로, 집어 든 구간 안에서 가장 높은 부품과 가장 낮은 부품의 높이 차가 $D$ 이하일 때만 포장할 수 있다.

즉 구간 $[l, r]$ $(1 \le l \le r \le N)$ 은

$$\max(a_l, a_{l+1}, \dots, a_r) - \min(a_l, a_{l+1}, \dots, a_r) \le D$$

일 때 포장 가능하다. 포장 가능한 구간이 모두 몇 개인지 구하여라. 길이가 1인 구간은 최댓값과 최솟값이 같아 차가 $0$ 이므로 언제나 포장 가능하고, 이것도 개수에 포함한다.

구간은 시작 위치와 끝 위치의 쌍으로 구별한다. 내용이 같은 수열이더라도 위치가 다르면 다른 구간이다.

답은 최대 $N(N+1)/2 = 45{,}000{,}150{,}000$ 까지 커지므로 **32비트 정수 범위를 넘는다.** 64비트 정수(자바 `long`, C++ `long long`)로 세야 한다.

#### 예시

$N = 8$, $D = 3$, 수열이 다음과 같다고 하자.

```
위치:  1  2  3  4  5  6  7  8
높이:  3  1  4  1  5  9  2  6
```

포장 가능한 구간은 모두 14개다.

- 길이 1인 구간 8개: $[1,1], [2,2], [3,3], [4,4], [5,5], [6,6], [7,7], [8,8]$ — 차가 모두 $0$
- 길이 2 이상인 구간 6개
  - $[1,2] = (3, 1)$ → 차 $2$
  - $[1,3] = (3, 1, 4)$ → 차 $3$
  - $[1,4] = (3, 1, 4, 1)$ → 차 $3$
  - $[2,3] = (1, 4)$ → 차 $3$
  - $[2,4] = (1, 4, 1)$ → 차 $3$
  - $[3,4] = (4, 1)$ → 차 $3$

$[1,5] = (3, 1, 4, 1, 5)$ 는 차가 $5 - 1 = 4$ 라서 세지 않고, $[5,6] = (5, 9)$ 도 차가 $4$ 라서 세지 않는다.
<!-- @input -->
첫째 줄에 부품의 개수 $N$과 허용 높이 차 $D$가 공백으로 구분되어 주어진다.

둘째 줄에 부품의 높이 $a_1, a_2, \dots, a_N$이 공백으로 구분되어 주어진다.

- $1 \le N \le 300{,}000$
- $0 \le D \le 2 \times 10^9$
- $-10^9 \le a_i \le 10^9$

$D$ 는 32비트 정수 범위를 넘을 수 있으므로 입력을 읽을 때부터 64비트 정수로 다루는 편이 안전하다.
<!-- @output -->
포장 가능한 구간의 개수를 한 줄에 출력한다.

값이 32비트 정수 범위를 넘을 수 있으므로 64비트 정수로 누적해야 한다.
<!-- @testcases -->
~~~input sample
8 3
3 1 4 1 5 9 2 6
~~~
~~~output
14
~~~
~~~input
1 0
-1000000000
~~~
~~~output
1
~~~
~~~input
10 0
5 5 5 3 3 7 7 7 7 1
~~~
~~~output
20
~~~
~~~input
6 2000000000
-1000000000 1000000000 -1000000000 1000000000 0 7
~~~
~~~output
21
~~~
~~~input
5 1999999999
-1000000000 1000000000 -1000000000 1000000000 -1000000000
~~~
~~~output
5
~~~
~~~input
10 4
1 2 3 4 5 6 7 8 9 10
~~~
~~~output
40
~~~
~~~input
12 3
9 9 7 7 5 5 3 3 1 1 1 1
~~~
~~~output
42
~~~
<!-- @generator -->
케이스 형식: `<시드> <N> <모드> <D>`
모드는 rand(완전 랜덤), same(전부 같은 값), narrow(값 범위 0~5), inc(단조 증가) 중 하나다.
~~~generator python3
import sys, random

tok = sys.stdin.read().split()
seed = int(tok[0]); n = int(tok[1]); mode = tok[2]; d = int(tok[3])
random.seed(seed)
LIM = 10 ** 9

if mode == 'rand':
    a = [random.randint(-LIM, LIM) for _ in range(n)]
elif mode == 'same':
    v = random.randint(-LIM, LIM)
    a = [v] * n
elif mode == 'narrow':
    a = [random.randint(0, 5) for _ in range(n)]
elif mode == 'inc':
    step = (2 * LIM) // max(n - 1, 1)
    cur = -LIM
    a = []
    for _ in range(n):
        a.append(cur)
        cur += random.randint(0, step)
else:
    raise ValueError('unknown mode')

out = [str(n) + ' ' + str(d), ' '.join(map(str, a))]
sys.stdout.write('\n'.join(out) + '\n')
~~~
모범답안: 투 포인터 + 단조 덱 2개로 O(N).
~~~solution python3
import sys
from collections import deque

def main():
    data = sys.stdin.buffer.read().split()
    n = int(data[0]); d = int(data[1])
    a = list(map(int, data[2:2 + n]))
    maxq = deque()   # 값이 감소하는 인덱스 덱: 앞이 구간 최댓값
    minq = deque()   # 값이 증가하는 인덱스 덱: 앞이 구간 최솟값
    l = 0
    ans = 0
    for r in range(n):
        v = a[r]
        while maxq and a[maxq[-1]] <= v:
            maxq.pop()
        maxq.append(r)
        while minq and a[minq[-1]] >= v:
            minq.pop()
        minq.append(r)
        # r 이 커질 때 최소 l 은 되돌아가지 않으므로 l 의 총 이동 횟수는 O(N)
        while a[maxq[0]] - a[minq[0]] > d:
            if maxq[0] == l:
                maxq.popleft()
            if minq[0] == l:
                minq.popleft()
            l += 1
        ans += r - l + 1
    sys.stdout.write(str(ans) + "\n")

main()
~~~
검증기: 덱과 투 포인터를 쓰지 않는 독립 구현.
희소 배열로 구간 최대/최소를 O(1)에 얻고, 각 l 마다 가능한 최대 r 을 이분 탐색으로 찾는다. O(N log N).
~~~validator cpp
#include <bits/stdc++.h>
using namespace std;

static int n;
static long long D;
static vector<int> a;
static vector<vector<int> > spMax, spMin;
static vector<int> lg;

int qMax(int l, int r) { int k = lg[r - l + 1]; return max(spMax[k][l], spMax[k][r - (1 << k) + 1]); }
int qMin(int l, int r) { int k = lg[r - l + 1]; return min(spMin[k][l], spMin[k][r - (1 << k) + 1]); }

int main() {
    {
        static char buf[1 << 16];
        size_t len = 0, pos = 0;
        auto gc = [&]() -> int {
            if (pos == len) { len = fread(buf, 1, sizeof(buf), stdin); pos = 0; if (len == 0) return -1; }
            return buf[pos++];
        };
        auto readInt = [&]() -> long long {
            int c = gc();
            while (c != -1 && (c < '0' || c > '9') && c != '-') c = gc();
            bool neg = false;
            if (c == '-') { neg = true; c = gc(); }
            long long x = 0;
            while (c >= '0' && c <= '9') { x = x * 10 + (c - '0'); c = gc(); }
            return neg ? -x : x;
        };
        n = (int)readInt();
        D = readInt();
        a.resize(n);
        for (int i = 0; i < n; i++) a[i] = (int)readInt();
    }

    lg.assign(n + 1, 0);
    for (int i = 2; i <= n; i++) lg[i] = lg[i >> 1] + 1;
    int K = lg[n] + 1;
    spMax.assign(K, vector<int>());
    spMin.assign(K, vector<int>());
    spMax[0] = a; spMin[0] = a;
    for (int k = 1; k < K; k++) {
        int sz = n - (1 << k) + 1;
        spMax[k].resize(max(sz, 0));
        spMin[k].resize(max(sz, 0));
        for (int i = 0; i < sz; i++) {
            spMax[k][i] = max(spMax[k - 1][i], spMax[k - 1][i + (1 << (k - 1))]);
            spMin[k][i] = min(spMin[k - 1][i], spMin[k - 1][i + (1 << (k - 1))]);
        }
    }

    long long ans = 0;
    for (int l = 0; l < n; l++) {
        // 구간을 오른쪽으로 늘릴수록 최대-최소는 절대 줄지 않으므로 이분 탐색이 성립한다
        int lo = l, hi = n - 1, best = l;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if ((long long)qMax(l, mid) - (long long)qMin(l, mid) <= D) { best = mid; lo = mid + 1; }
            else hi = mid - 1;
        }
        ans += (long long)(best - l + 1);
    }
    printf("%lld\n", ans);
    return 0;
}
~~~
최대 크기 완전 랜덤
~~~case
1 300000 rand 100000000
~~~
전부 같은 값 — 답이 45,000,150,000 으로 32비트를 크게 넘는다
~~~case
7 300000 same 123456789
~~~
값 범위가 0~5 로 매우 좁아 덱이 자주 뒤집힌다
~~~case
13 300000 narrow 2
~~~
단조 증가 — 창이 끊임없이 미끄러진다
~~~case
29 300000 inc 5000000
~~~
