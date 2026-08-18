---
title: 유물 창고의 금액
difficulty: GOLD
tags: 다이나믹 프로그래밍, 배낭 문제, 이진 분할
timeLimit: 4
memoryLimit: 256
isPublic: true
---
<!-- @description -->
유적 창고에서 고대 동전이 무더기로 발굴됐다. 동전은 $M$가지 종류이고, $i$번째 종류의 액면가는 $v_i$, 발굴된 개수는 $c_i$개다. 같은 종류의 동전은 서로 구별하지 않는다.

$i$번째 종류의 동전은 $0$개 이상 $c_i$개 이하로만 쓸 수 있다. 이렇게 고른 동전들의 액면가를 모두 더한 값을 그 조합의 **금액**이라고 한다. 동전을 하나도 고르지 않은 경우는 세지 않는다.

$1$ 이상 $S$ 이하의 정수 중에서 만들 수 있는 금액이 몇 가지인지, 그리고 그 금액들을 모두 더하면 얼마인지 구한다. 같은 금액을 여러 방법으로 만들 수 있어도 한 번만 센다.

발굴된 동전의 총 개수 $c_1 + c_2 + \dots + c_M$은 $10^{11}$에 이를 수 있으므로, 동전을 낱개로 모두 늘어놓는 방법으로는 풀 수 없다. 액면가가 $S$보다 큰 종류도 주어질 수 있으며, 그런 동전은 한 개만 써도 금액이 $S$를 넘으므로 아무 도움이 되지 않는다.

금액들의 총합은 $50$억을 넘을 수 있어 32비트 정수 범위를 벗어난다.

#### 예시

$M = 2$, $S = 12$이고 액면가 $2$짜리가 $3$개, 액면가 $5$짜리가 $1$개 발굴됐다고 하자.

- 액면가 $2$짜리만 쓰면 $2, 4, 6$을 만들 수 있다. $8$은 만들 수 없다. $2$짜리가 $3$개뿐이기 때문이다.
- 액면가 $5$짜리를 함께 쓰면 $5, 7, 9, 11$을 만들 수 있다. $5$짜리는 하나뿐이므로 $10 = 5+5$는 만들 수 없다.

만들 수 있는 금액은 $2, 4, 5, 6, 7, 9, 11$의 $7$가지이고, 이들의 합은 $44$이다. 개수 제한이 없었다면 $8, 10, 12$도 만들 수 있었을 것이다.
<!-- @input -->
첫째 줄에 동전 종류의 수 $M$과 금액의 상한 $S$가 공백으로 구분되어 주어진다. ($1 \le M \le 100$, $1 \le S \le 100{,}000$)

다음 $M$개의 줄에 $i$번째 종류의 액면가 $v_i$와 개수 $c_i$가 공백으로 구분되어 주어진다. ($1 \le v_i \le 10^9$, $1 \le c_i \le 10^9$) 액면가가 같은 종류가 여러 번 주어질 수 있으며, 그 경우 서로 다른 종류로 취급한다.
<!-- @output -->
첫째 줄에 만들 수 있는 금액의 가짓수와 그 금액들의 총합을 공백으로 구분해 출력한다. 만들 수 있는 금액이 하나도 없으면 `0 0`을 출력한다.
<!-- @testcases -->
~~~input sample
2 12
2 3
5 1
~~~
~~~output
7 44
~~~
~~~input
1 1
1 1
~~~
~~~output
1 1
~~~
~~~input
2 10
11 1000000000
1000000000 1000000000
~~~
~~~output
0 0
~~~
~~~input
1 100000
1 1000000000
~~~
~~~output
100000 5000050000
~~~
~~~input
1 20
3 2
~~~
~~~output
2 9
~~~
~~~input
2 100000
2 1000000000
100000 1
~~~
~~~output
50000 2500050000
~~~
~~~input
3 60
7 3
11 2
1 1
~~~
~~~output
22 506
~~~
<!-- @generator -->
~~~generator python3
import sys, random

def main():
    data = sys.stdin.read().split()
    seed = int(data[0]); m = int(data[1]); s = int(data[2])
    vmode = data[3] if len(data) > 3 else "rand"
    cmode = data[4] if len(data) > 4 else "mix"
    rnd = random.Random()
    rnd.seed(seed)
    LIM = 10 ** 9
    lines = ["%d %d" % (m, s)]
    for _ in range(m):
        if vmode == "small":
            v = rnd.randint(1, 30)
        elif vmode == "big":
            v = rnd.randint(s + 1, LIM) if rnd.random() < 0.35 else rnd.randint(s // 2, s)
        elif vmode == "dup":
            v = 6
        elif vmode == "sparse":
            v = rnd.randint(s // 3, s)
        elif vmode == "tinyval":
            v = rnd.randint(1, 4)
        else:
            v = rnd.randint(1, s)
        if cmode == "tiny":
            c = rnd.randint(1, 3)
        elif cmode == "huge":
            c = rnd.randint(10 ** 8, LIM)
        elif cmode == "one":
            c = 1
        else:
            r = rnd.random()
            if r < 0.35:
                c = rnd.randint(1, 3)
            elif r < 0.7:
                c = rnd.randint(1, 1000)
            else:
                c = rnd.randint(1, LIM)
        lines.append("%d %d" % (v, c))
    sys.stdout.write("\n".join(lines) + "\n")

main()
~~~
~~~solution python3
import sys

def main():
    data = sys.stdin.buffer.read().split()
    m = int(data[0]); s = int(data[1])
    full = (1 << (s + 1)) - 1
    mask = 1
    p = 2
    for _ in range(m):
        v = int(data[p]); c = int(data[p + 1]); p += 2
        if v > s:
            continue
        k = s // v
        if c < k:
            k = c
        step = 1
        while step <= k:
            mask |= (mask << (v * step)) & full
            k -= step
            step <<= 1
        if k > 0:
            mask |= (mask << (v * k)) & full
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
// 독립 구현: 개수를 이진 분할해 0/1 배낭으로 바꾸는 대신,
// "이 금액에 도달했을 때 현재 동전이 몇 개 남는가"를 기록하는 O(M*S) DP 로 푼다.
#include <bits/stdc++.h>
using namespace std;
int main() {
    int m; long long s;
    if (scanf("%d %lld", &m, &s) != 2) return 0;
    vector<char> dp(s + 1, 0);
    dp[0] = 1;
    vector<long long> rem(s + 1);
    for (int i = 0; i < m; i++) {
        long long v, c;
        scanf("%lld %lld", &v, &c);
        if (v > s) continue;
        for (long long x = 0; x <= s; x++) {
            if (dp[x]) { rem[x] = c; continue; }
            rem[x] = -1;
            if (x >= v && rem[x - v] > 0) {
                dp[x] = 1;
                rem[x] = rem[x - v] - 1;
            }
        }
    }
    long long cnt = 0, tot = 0;
    for (long long x = 1; x <= s; x++) if (dp[x]) { cnt++; tot += x; }
    printf("%lld %lld\n", cnt, tot);
    return 0;
}
~~~
~~~case
501 100 100000 rand mix
~~~
~~~case
507 100 100000 tinyval tiny
~~~
~~~case
511 100 100000 small huge
~~~
~~~case
517 100 100000 big mix
~~~
~~~case
523 100 99991 dup tiny
~~~
~~~case
531 100 100000 sparse one
~~~
~~~case
537 100 100000 rand huge
~~~
~~~case
541 1 100000 tinyval tiny
~~~
