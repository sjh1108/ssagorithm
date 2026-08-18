---
title: 고대 화폐의 금액
difficulty: SILVER
tags: 다이나믹 프로그래밍, 배낭 문제, 구현
timeLimit: 3
memoryLimit: 256
isPublic: true
---
<!-- @description -->
유적에서 고대 화폐의 거푸집 $M$개가 발굴됐다. $i$번째 거푸집으로는 액면가가 $v_i$인 동전을 찍어낼 수 있다. 거푸집은 몇 번이든 다시 쓸 수 있으므로, 각 종류의 동전을 **원하는 만큼 얼마든지** 쓸 수 있다.

동전 몇 개를 골라 액면가를 모두 더한 값을 그 조합의 **금액**이라고 한다. 동전을 하나도 고르지 않은 경우는 세지 않는다.

$1$ 이상 $S$ 이하의 정수 중에서 만들 수 있는 금액이 몇 가지인지, 그리고 그 금액들을 모두 더하면 얼마인지 구한다. 같은 금액을 여러 방법으로 만들 수 있어도 한 번만 센다.

액면가가 $S$보다 큰 거푸집도 주어질 수 있다. 그런 동전은 한 개만 써도 금액이 $S$를 넘으므로 아무 도움이 되지 않는다.

금액들의 총합은 $50$억을 넘을 수 있어 32비트 정수 범위를 벗어난다.

#### 예시

$M = 2$, $S = 12$이고 액면가가 $2$, $5$라고 하자.

- $1$은 만들 수 없다. $2$는 $2$ 하나로 만든다. $3$은 만들 수 없다.
- $4 = 2+2$, $5 = 5$, $6 = 2+2+2$, $7 = 2+5$, $8 = 2+2+2+2$, $9 = 2+2+5$, $10 = 5+5$, $11 = 2+2+2+5$, $12 = 2+5+5$.

만들 수 있는 금액은 $2, 4, 5, 6, 7, 8, 9, 10, 11, 12$의 $10$가지이고, 이들의 합은 $74$이다.
<!-- @input -->
첫째 줄에 거푸집의 수 $M$과 금액의 상한 $S$가 공백으로 구분되어 주어진다. ($1 \le M \le 100$, $1 \le S \le 100{,}000$)

둘째 줄에 액면가 $v_1, v_2, \dots, v_M$이 공백으로 구분되어 주어진다. ($1 \le v_i \le 10^9$) 액면가가 같은 거푸집이 여러 개 있을 수 있다.
<!-- @output -->
첫째 줄에 만들 수 있는 금액의 가짓수와 그 금액들의 총합을 공백으로 구분해 출력한다. 만들 수 있는 금액이 하나도 없으면 `0 0`을 출력한다.
<!-- @testcases -->
~~~input sample
2 12
2 5
~~~
~~~output
10 74
~~~
~~~input
1 1
1
~~~
~~~output
1 1
~~~
~~~input
2 10
100 1000000000
~~~
~~~output
0 0
~~~
~~~input
3 20
7 7 7
~~~
~~~output
2 21
~~~
~~~input
1 100000
1
~~~
~~~output
100000 5000050000
~~~
~~~input
2 30
3 5
~~~
~~~output
26 451
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
    if mode == "small":
        for _ in range(m):
            vals.append(rnd.randint(1, 30))
    elif mode == "big":
        for _ in range(m):
            if rnd.random() < 0.4:
                vals.append(rnd.randint(s + 1, LIM))
            else:
                vals.append(rnd.randint(s // 2, s))
    elif mode == "prime":
        base = [9973, 9967, 9949, 9941, 9931, 9929, 9923, 9907, 9901, 9887,
                99991, 99989, 99971, 99961, 99923, 99991, 49999, 49993, 49991, 33331]
        for _ in range(m):
            vals.append(rnd.choice(base))
    elif mode == "dup":
        v = rnd.randint(2, 7)
        vals = [v] * m
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
        step = v
        while step <= s:
            mask |= (mask << step) & full
            step <<= 1
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
// 독립 구현: 큰 정수 비트 시프트 대신 고전적인 bool 배열 DP 를 정방향으로 채운다.
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
        if (v[i] > s) continue;
        long long c = v[i];
        for (long long x = c; x <= s; x++)
            if (dp[x - c]) dp[x] = 1;
    }
    long long cnt = 0, tot = 0;
    for (long long x = 1; x <= s; x++) if (dp[x]) { cnt++; tot += x; }
    printf("%lld %lld\n", cnt, tot);
    return 0;
}
~~~
~~~case
301 100 100000 rand
~~~
~~~case
307 100 100000 small
~~~
~~~case
311 100 100000 big
~~~
~~~case
317 100 100000 prime
~~~
~~~case
323 60 99991 dup
~~~
~~~case
331 100 100000 sparse
~~~
~~~case
337 1 100000 rand
~~~
