---
title: 검사창에 잡힌 최저 높이
difficulty: GOLD
tags: 슬라이딩 윈도우, 덱, 자료 구조, 단조 자료구조
timeLimit: 4
memoryLimit: 512
isPublic: true
---
<!-- @description -->
공장 컨베이어 벨트 위에 부품 $N$개가 왼쪽부터 한 줄로 놓여 있다. 왼쪽에서 $i$번째 부품의 높이는 $a_i$이다. 부품 높이는 벨트 표면을 기준으로 재므로, 표면보다 낮게 파인 부품은 높이가 음수로 기록된다.

벨트 위쪽에는 **연속한 부품 $K$개**를 한 번에 들여다보는 검사창이 달려 있다. 검사창은 그 안에 들어온 $K$개 부품 중 **가장 낮은 높이 하나**를 기록한다.

검사창은 벨트의 가장 왼쪽에서 출발한다. 처음에는 $1$번부터 $K$번 부품을 본다. 그다음에는 오른쪽으로 한 칸 움직여 $2$번부터 $K+1$번 부품을 보고, 또 한 칸 움직여 $3$번부터 $K+2$번 부품을 본다. 이런 식으로 검사창의 오른쪽 끝이 $N$번 부품에 닿을 때까지 한 칸씩 움직인다. 따라서 기록은 모두 $N-K+1$개 남는다.

남은 기록 $N-K+1$개를 모두 더한 값을 구한다.

기록의 합은 절댓값이 $3 \times 10^{14}$ 가까이 커질 수 있어 32비트 정수 범위를 벗어난다. 64비트 정수를 써야 한다.

#### 예시

$N = 8$, $K = 3$이고 높이가 $3, 1, 4, 1, 5, 9, 2, 6$이라고 하자. 검사창이 보는 구간은 $8 - 3 + 1 = 6$개다.

| 검사 순서 | 보는 부품 번호 | 그 구간의 높이 | 기록(최저 높이) |
|---|---|---|---|
| 1 | 1 ~ 3 | 3, 1, 4 | 1 |
| 2 | 2 ~ 4 | 1, 4, 1 | 1 |
| 3 | 3 ~ 5 | 4, 1, 5 | 1 |
| 4 | 4 ~ 6 | 1, 5, 9 | 1 |
| 5 | 5 ~ 7 | 5, 9, 2 | 2 |
| 6 | 6 ~ 8 | 9, 2, 6 | 2 |

기록은 $1, 1, 1, 1, 2, 2$이고 이들의 합은 $8$이다.
<!-- @input -->
첫째 줄에 부품의 개수 $N$과 검사창의 너비 $K$가 공백으로 구분되어 주어진다. ($1 \le K \le N \le 300{,}000$)

둘째 줄에 부품의 높이 $a_1, a_2, \dots, a_N$이 공백으로 구분되어 주어진다. ($-10^9 \le a_i \le 10^9$)
<!-- @output -->
첫째 줄에 검사창이 남긴 기록 $N-K+1$개의 합을 출력한다. 값이 32비트 정수 범위를 벗어날 수 있다.
<!-- @testcases -->
~~~input sample
8 3
3 1 4 1 5 9 2 6
~~~
~~~output
8
~~~
~~~input
1 1
-1000000000
~~~
~~~output
-1000000000
~~~
~~~input
5 1
-1000000000 5 -7 1000000000 0
~~~
~~~output
-2
~~~
~~~input
6 6
7 -3 12 -3 0 8
~~~
~~~output
-3
~~~
~~~input
7 4
1000000000 1000000000 1000000000 1000000000 1000000000 1000000000 1000000000
~~~
~~~output
4000000000
~~~
~~~input
12 3
-1000000000 -1000000000 -1000000000 -1000000000 -1000000000 -1000000000 -1000000000 -1000000000 -1000000000 -1000000000 -1000000000 -1000000000
~~~
~~~output
-10000000000
~~~
~~~input
10 3
1 1 2 2 3 3 4 4 5 5
~~~
~~~output
20
~~~
~~~input
10 3
5 5 4 4 3 3 2 2 1 1
~~~
~~~output
20
~~~
<!-- @generator -->
케이스 파라미터: `시드 N K 모드`
모드 rand=완전 랜덤, asc=오름차순(덱이 K까지 자라는 최악), desc=내림차순(매 단계 덱 뒤쪽을 전부 버림), narrow=값 범위 -3~3(동점 대량)
~~~generator python3
import sys, random

def main():
    data = sys.stdin.read().split()
    seed = int(data[0]); n = int(data[1]); k = int(data[2])
    mode = data[3] if len(data) > 3 else "rand"
    rnd = random.Random()
    rnd.seed(seed)
    LIM = 10 ** 9
    if mode == "asc":
        a = [rnd.randint(-LIM, LIM) for _ in range(n)]
        a.sort()
    elif mode == "desc":
        a = [rnd.randint(-LIM, LIM) for _ in range(n)]
        a.sort(reverse=True)
    elif mode == "narrow":
        a = [rnd.randint(-3, 3) for _ in range(n)]
    else:
        a = [rnd.randint(-LIM, LIM) for _ in range(n)]
    out = ["%d %d" % (n, k), " ".join(map(str, a))]
    sys.stdout.write("\n".join(out) + "\n")

main()
~~~
~~~solution python3
import sys
from collections import deque

def main():
    data = sys.stdin.buffer.read().split()
    n = int(data[0]); k = int(data[1])
    a = list(map(int, data[2:2 + n]))
    dq = deque()          # 값이 증가하도록 유지되는 인덱스 덱: 맨 앞이 현재 창의 최솟값
    total = 0
    for i in range(n):
        v = a[i]
        while dq and a[dq[-1]] >= v:
            dq.pop()      # 뒤에 더 작은 v 가 들어오므로 앞의 큰 값은 영원히 최솟값이 될 수 없다
        dq.append(i)
        if dq[0] <= i - k:
            dq.popleft()  # 창 밖으로 나간 인덱스는 한 단계에 많아야 하나
        if i >= k - 1:
            total += a[dq[0]]
    sys.stdout.write(str(total) + "\n")

main()
~~~
~~~validator cpp
// 독립 구현: 덱을 쓰지 않고 희소 배열(sparse table)로 각 구간 최솟값을 O(1) 에 조회한다.
#include <bits/stdc++.h>
using namespace std;
int main() {
    int n, k;
    if (scanf("%d %d", &n, &k) != 2) return 0;
    vector<int> a(n);
    for (int i = 0; i < n; i++) scanf("%d", &a[i]);
    int LOG = 1;
    while ((1 << LOG) <= n) LOG++;
    vector<vector<int>> sp(LOG, vector<int>(n));
    sp[0] = a;
    for (int j = 1; j < LOG; j++) {
        int len = 1 << j;
        if (len > n) break;
        for (int i = 0; i + len <= n; i++)
            sp[j][i] = min(sp[j - 1][i], sp[j - 1][i + (len >> 1)]);
    }
    vector<int> lg(n + 1, 0);
    for (int i = 2; i <= n; i++) lg[i] = lg[i / 2] + 1;
    int j = lg[k];
    long long total = 0;
    for (int i = 0; i + k <= n; i++)
        total += min(sp[j][i], sp[j][i + k - (1 << j)]);
    printf("%lld\n", total);
    return 0;
}
~~~
~~~case
1001 300000 150000 rand
~~~
~~~case
1009 300000 1 rand
~~~
~~~case
1013 300000 300000 rand
~~~
~~~case
1019 300000 1000 desc
~~~
~~~case
1031 300000 150000 asc
~~~
~~~case
1039 300000 777 narrow
~~~
