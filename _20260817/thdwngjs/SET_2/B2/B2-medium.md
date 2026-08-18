---
title: 발굴된 동전의 금액
difficulty: GOLD
tags: 다이나믹 프로그래밍, 배낭 문제, 부분집합 합
timeLimit: 3
memoryLimit: 256
isPublic: true
---
<!-- @description -->
유적에서 고대 동전 $M$개가 발굴됐다. $i$번째 동전의 액면가는 $v_i$이다. 거푸집은 발굴되지 않았으므로 동전을 더 찍어낼 수 없다. 즉 **각 동전은 많아야 한 번만** 쓸 수 있다.

액면가가 같은 동전이 여러 개 발굴될 수 있고, 그런 동전들도 서로 다른 동전으로 친다. 예를 들어 액면가 $5$짜리 동전이 두 개 발굴됐다면 $10$을 만들 수 있다.

발굴된 동전 중 몇 개를 골라 액면가를 모두 더한 값을 그 조합의 **금액**이라고 한다. 하나도 고르지 않은 경우는 세지 않는다.

$1$ 이상 $S$ 이하의 정수 중에서 만들 수 있는 금액이 몇 가지인지, 그리고 그 금액들을 모두 더하면 얼마인지 구한다. 같은 금액을 여러 방법으로 만들 수 있어도 한 번만 센다.

액면가가 $S$보다 큰 동전도 주어질 수 있다. 그런 동전은 한 개만 써도 금액이 $S$를 넘으므로 아무 도움이 되지 않는다.

금액들의 총합은 $50$억을 넘을 수 있어 32비트 정수 범위를 벗어난다.

#### 예시

$M = 4$, $S = 12$이고 액면가가 $2$, $5$, $5$, $11$이라고 하자.

- $2$: 첫 동전 하나.
- $5$: 액면가 $5$짜리 하나.
- $7 = 2 + 5$.
- $10 = 5 + 5$. 액면가 $5$짜리 동전이 두 개이므로 가능하다.
- $11$: 마지막 동전 하나.
- $12 = 2 + 5 + 5$.

$2+11 = 13$은 $S$를 넘으므로 세지 않는다. 만들 수 있는 금액은 $2, 5, 7, 10, 11, 12$의 $6$가지이고, 이들의 합은 $47$이다.
<!-- @input -->
첫째 줄에 동전의 수 $M$과 금액의 상한 $S$가 공백으로 구분되어 주어진다. ($1 \le M \le 100$, $1 \le S \le 100{,}000$)

둘째 줄에 액면가 $v_1, v_2, \dots, v_M$이 공백으로 구분되어 주어진다. ($1 \le v_i \le 10^9$)
<!-- @output -->
첫째 줄에 만들 수 있는 금액의 가짓수와 그 금액들의 총합을 공백으로 구분해 출력한다. 만들 수 있는 금액이 하나도 없으면 `0 0`을 출력한다.
<!-- @testcases -->
~~~input sample
4 12
2 5 5 11
~~~
~~~output
6 47
~~~
~~~input
1 1
1
~~~
~~~output
1 1
~~~
~~~input
3 10
11 1000000000 999999999
~~~
~~~output
0 0
~~~
~~~input
3 20
3 3 3
~~~
~~~output
3 18
~~~
~~~input
17 100000
1 2 4 8 16 32 64 128 256 512 1024 2048 4096 8192 16384 32768 65536
~~~
~~~output
100000 5000050000
~~~
~~~input
2 12
5 7
~~~
~~~output
3 24
~~~
<!-- @generator -->
~~~generator python3
import sys, random

def main():
    data = sys.stdin.read().split()
    seed = int(data[0]); m = int(data[1]); s = int(data[2])
    mode = data[3] if len(data) > 3 else "rand"
    rnd = random.Random()
    rnd.seed(seed)
    LIM = 10 ** 9
    vals = []
    if mode == "pow2":
        for i in range(m):
            k = i % 17
            base = 1 << k
            if base > s:
                base = rnd.randint(1, s)
            vals.append(base)
    elif mode == "small":
        for _ in range(m):
            vals.append(rnd.randint(1, 30))
    elif mode == "big":
        for _ in range(m):
            if rnd.random() < 0.4:
                vals.append(rnd.randint(s + 1, LIM))
            else:
                vals.append(rnd.randint(s // 2, s))
    elif mode == "dup":
        v = rnd.randint(2, 7)
        vals = [v] * m
    elif mode == "half":
        for _ in range(m):
            vals.append(rnd.randint(1, max(1, s // m)))
    elif mode == "sparse":
        for _ in range(m):
            vals.append(rnd.randint(s // 3, s))
    else:
        for _ in range(m):
            vals.append(rnd.randint(1, s))
    out = ["%d %d" % (m, s), " ".join(map(str, vals))]
    sys.stdout.write("\n".join(out) + "\n")

main()
~~~
~~~solution python3
import sys

def main():
    data = sys.stdin.buffer.read().split()
    m = int(data[0]); s = int(data[1])
    coins = [int(x) for x in data[2:2 + m]]
    full = (1 << (s + 1)) - 1
    mask = 1
    for v in coins:
        if v > s:
            continue
        mask |= (mask << v) & full
    bits = bin(mask)[2:]
    ln = len(bits)
    cnt = 0
    tot = 0
    for i, ch in enumerate(bits):
        if ch == "1":
            amt = ln - 1 - i
            if amt >= 1:
                cnt += 1
                tot += amt
    sys.stdout.write("%d %d\n" % (cnt, tot))

main()
~~~
~~~validator cpp
// 독립 구현: 큰 정수 비트 시프트 대신 bool 배열 DP 를 역방향으로 채운다.
// (한 동전을 두 번 쓰지 않기 위해 큰 금액부터 갱신한다.)
#include <bits/stdc++.h>
using namespace std;
int main() {
    int m; long long s;
    if (scanf("%d %lld", &m, &s) != 2) return 0;
    vector<long long> v(m);
    for (int i = 0; i < m; i++) scanf("%lld", &v[i]);
    vector<char> dp(s + 1, 0);
    dp[0] = 1;
    for (int i = 0; i < m; i++) {
        long long c = v[i];
        if (c > s) continue;
        for (long long x = s; x >= c; x--)
            if (dp[x - c]) dp[x] = 1;
    }
    long long cnt = 0, tot = 0;
    for (long long x = 1; x <= s; x++) if (dp[x]) { cnt++; tot += x; }
    printf("%lld %lld\n", cnt, tot);
    return 0;
}
~~~
~~~case
401 100 100000 rand
~~~
~~~case
407 100 100000 pow2
~~~
~~~case
411 100 100000 big
~~~
~~~case
417 100 100000 small
~~~
~~~case
423 100 99991 dup
~~~
~~~case
431 100 100000 sparse
~~~
~~~case
437 100 100000 half
~~~
