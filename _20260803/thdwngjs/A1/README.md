---
title: 가장 강한 해류 구간
difficulty: GOLD
tags: 세그먼트 트리, 분할 정복, 최대 부분합, 자료 구조
timeLimit: 7
memoryLimit: 512
isPublic: true
---
<!-- @description -->
해안선을 따라 관측소 $N$개가 1번부터 $N$번까지 일렬로 놓여 있다. $i$번 관측소가 기록한 해류 속도는 $A_i$ 이고, 북쪽으로 흐르면 양수, 남쪽으로 흐르면 음수다.

연구소는 "북쪽으로 가장 강하게 흐르는 구역"을 찾으려고 한다. 어떤 **연속한** 관측소 구간의 기록을 모두 더한 값이 클수록 그 구역의 북향 해류가 강하다고 본다.

처리해야 할 작업은 $Q$개이고 두 종류다.

- `1 i x` — $i$번 관측소의 기록을 $x$ 로 **바꾼다**.
- `2 l r` — $l$번부터 $r$번까지의 구간 안에 완전히 들어가는 **연속한 구간** $[p, q]$ ($l \le p \le q \le r$) 중에서 $A_p + A_{p+1} + \dots + A_q$ 가 가장 큰 값을 구한다.

구간 $[p, q]$ 는 **비어 있을 수 없다.** 즉 관측소를 적어도 하나는 골라야 한다. 그래서 $[l, r]$ 안의 기록이 모두 음수라면 답도 음수가 된다.

작업은 주어진 순서대로 처리한다. 두 번째 종류의 작업마다 답을 출력한다.

#### 예시

$N = 8$, $A = [1, -3, 4, -1, 2, 1, -5, 4]$ 로 시작한다고 하자.

| 작업 | 처리 후 $A$ | 답 | 이유 |
|---|---|---|---|
| `2 1 8` | 그대로 | $6$ | $[3, 6]$ 구간, $4-1+2+1 = 6$ |
| `2 2 5` | 그대로 | $5$ | $[3, 5]$ 구간, $4-1+2 = 5$ |
| `1 2 5` | $[1,5,4,-1,2,1,-5,4]$ | — | 2번 관측소가 $5$ 로 바뀐다 |
| `2 1 8` | 그대로 | $12$ | $[1, 6]$ 구간, $1+5+4-1+2+1 = 12$ |
| `2 7 7` | 그대로 | $-5$ | 고를 수 있는 구간이 $[7,7]$ 뿐이다 |
| `2 3 6` | 그대로 | $6$ | $[3, 6]$ 구간, $4-1+2+1 = 6$ |
<!-- @input -->
첫째 줄에 관측소의 수 $N$ 과 작업의 수 $Q$ 가 공백으로 구분되어 주어진다. ($1 \le N \le 100{,}000$, $1 \le Q \le 100{,}000$)

둘째 줄에 $A_1, A_2, \dots, A_N$ 이 공백으로 구분되어 주어진다. ($-10^9 \le A_i \le 10^9$)

셋째 줄부터 $Q$개의 줄에 걸쳐 작업이 한 줄에 하나씩 주어진다.

- 첫 번째 종류는 `1 i x` 형태다. ($1 \le i \le N$, $-10^9 \le x \le 10^9$)
- 두 번째 종류는 `2 l r` 형태다. ($1 \le l \le r \le N$)

두 번째 종류의 작업은 적어도 하나 주어진다. 답은 32비트 정수 범위를 넘을 수 있다.
<!-- @output -->
두 번째 종류의 작업마다 답을 한 줄에 하나씩, 입력에 주어진 순서대로 출력한다.
<!-- @testcases -->
~~~input sample
8 6
1 -3 4 -1 2 1 -5 4
2 1 8
2 2 5
1 2 5
2 1 8
2 7 7
2 3 6
~~~
~~~output
6
5
12
-5
6
~~~
~~~input
5 3
-3 -1 -4 -1 -5
2 1 5
2 3 4
2 5 5
~~~
~~~output
-1
-1
-5
~~~
~~~input
1 3
-1000000000
2 1 1
1 1 1000000000
2 1 1
~~~
~~~output
-1000000000
1000000000
~~~
~~~input
5 4
1000000000 1000000000 1000000000 1000000000 1000000000
2 1 5
1 3 -1
2 1 5
2 1 3
~~~
~~~output
5000000000
3999999999
2000000000
~~~
~~~input
4 3
0 0 0 0
2 1 4
1 2 -5
2 1 4
~~~
~~~output
0
0
~~~
~~~input
7 5
2 -3 2 -3 2 -3 2
2 1 7
2 2 6
1 4 10
2 1 7
2 3 5
~~~
~~~output
2
2
14
14
~~~
<!-- @generator -->
~~~generator python3
import sys, random
tok = sys.stdin.read().split()
seed = int(tok[0]); n = int(tok[1]); q = int(tok[2]); mode = tok[3]
random.seed(seed)
if mode == 'rand':
    vals = [random.randint(-10**9, 10**9) for _ in range(n)]
elif mode == 'neg':
    vals = [random.randint(-10**9, -1) for _ in range(n)]
elif mode == 'pos':
    vals = [random.randint(1, 10**9) for _ in range(n)]
elif mode == 'small':
    vals = [random.randint(-3, 3) for _ in range(n)]
else:
    vals = [random.choice([-10**9, 10**9]) for _ in range(n)]
out = []
out.append('%d %d' % (n, q))
out.append(' '.join(map(str, vals)))
qcnt = 0
for k in range(q):
    force_query = (k == q - 1 and qcnt == 0)
    if not force_query and random.randint(1, 100) <= 40:
        i = random.randint(1, n)
        if mode == 'neg':
            x = random.randint(-10**9, -1)
        elif mode == 'pos':
            x = random.randint(1, 10**9)
        elif mode == 'small':
            x = random.randint(-3, 3)
        else:
            x = random.randint(-10**9, 10**9)
        out.append('1 %d %d' % (i, x))
    else:
        qcnt += 1
        l = random.randint(1, n); r = random.randint(1, n)
        if l > r: l, r = r, l
        out.append('2 %d %d' % (l, r))
sys.stdout.write('\n'.join(out) + '\n')
~~~
~~~solution python3
import sys
def main():
    data = sys.stdin.buffer.read().split()
    idx = 0
    n = int(data[idx]); idx += 1
    q = int(data[idx]); idx += 1
    NEG = -(1 << 60)
    size = 1
    while size < n:
        size <<= 1
    S = [0] * (2 * size)
    P = [NEG] * (2 * size)
    F = [NEG] * (2 * size)
    B = [NEG] * (2 * size)
    for i in range(n):
        v = int(data[idx]); idx += 1
        j = size + i
        S[j] = v; P[j] = v; F[j] = v; B[j] = v
    for i in range(size - 1, 0, -1):
        l = 2 * i; r = l + 1
        sl = S[l]; sr = S[r]
        S[i] = sl + sr
        pr = P[r]; pl = P[l]
        P[i] = pl if pl > sl + pr else sl + pr
        fl = F[l]; fr = F[r]
        F[i] = fr if fr > sr + fl else sr + fl
        bb = B[l]
        if B[r] > bb: bb = B[r]
        t = fl + pr
        if t > bb: bb = t
        B[i] = bb
    out = []
    for _ in range(q):
        t = data[idx]; idx += 1
        a = int(data[idx]); idx += 1
        b = int(data[idx]); idx += 1
        if t == b'1':
            j = size + a - 1
            S[j] = b; P[j] = b; F[j] = b; B[j] = b
            j >>= 1
            while j:
                l = 2 * j; r = l + 1
                sl = S[l]; sr = S[r]
                S[j] = sl + sr
                pl = P[l]; pr = P[r]
                P[j] = pl if pl > sl + pr else sl + pr
                fl = F[l]; fr = F[r]
                F[j] = fr if fr > sr + fl else sr + fl
                bb = B[l]
                if B[r] > bb: bb = B[r]
                tt = fl + pr
                if tt > bb: bb = tt
                B[j] = bb
                j >>= 1
        else:
            lo = size + a - 1
            hi = size + b
            Ls, Lp, Lf, Lb = 0, NEG, NEG, NEG
            Rs, Rp, Rf, Rb = 0, NEG, NEG, NEG
            while lo < hi:
                if lo & 1:
                    ns = S[lo]; np_ = P[lo]; nf = F[lo]; nb = B[lo]
                    s2 = Ls + ns
                    p2 = Lp if Lp > Ls + np_ else Ls + np_
                    f2 = nf if nf > ns + Lf else ns + Lf
                    b2 = Lb if Lb > nb else nb
                    tt = Lf + np_
                    if tt > b2: b2 = tt
                    Ls, Lp, Lf, Lb = s2, p2, f2, b2
                    lo += 1
                if hi & 1:
                    hi -= 1
                    ns = S[hi]; np_ = P[hi]; nf = F[hi]; nb = B[hi]
                    s2 = ns + Rs
                    p2 = np_ if np_ > ns + Rp else ns + Rp
                    f2 = Rf if Rf > Rs + nf else Rs + nf
                    b2 = nb if nb > Rb else Rb
                    tt = nf + Rp
                    if tt > b2: b2 = tt
                    Rs, Rp, Rf, Rb = s2, p2, f2, b2
                lo >>= 1; hi >>= 1
            ans = Lb if Lb > Rb else Rb
            tt = Lf + Rp
            if tt > ans: ans = tt
            out.append(ans)
    sys.stdout.write('\n'.join(map(str, out)) + '\n')
main()
~~~
~~~validator cpp
#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
static const ll NEG = LLONG_MIN / 4;
struct Node { ll s, p, f, b; };
static inline Node mrg(const Node& a, const Node& c) {
    Node r;
    r.s = a.s + c.s;
    r.p = max(a.p, a.s + c.p);
    r.f = max(c.f, c.s + a.f);
    r.b = max(max(a.b, c.b), a.f + c.p);
    return r;
}
int main() {
    int n, q;
    if (scanf("%d %d", &n, &q) != 2) return 0;
    vector<ll> a(n + 1);
    for (int i = 1; i <= n; i++) if(scanf("%lld", &a[i])!=1) return 0;
    int B = max(1, (int)(sqrt((double)n)));
    int nb = (n + B - 1) / B;
    vector<Node> blk(nb);
    auto rebuild = [&](int b) {
        int lo = b * B + 1, hi = min(n, (b + 1) * B);
        Node acc = {0, NEG, NEG, NEG};
        for (int i = lo; i <= hi; i++) {
            Node e = {a[i], a[i], a[i], a[i]};
            acc = mrg(acc, e);
        }
        blk[b] = acc;
    };
    for (int b = 0; b < nb; b++) rebuild(b);
    string out;
    out.reserve(1 << 20);
    char buf[32];
    for (int t = 0; t < q; t++) {
        int ty; long long x, y;
        if(scanf("%d %lld %lld", &ty, &x, &y)!=3) return 0;
        if (ty == 1) {
            int i = (int)x;
            a[i] = y;
            rebuild((i - 1) / B);
        } else {
            int l = (int)x, r = (int)y;
            Node acc = {0, NEG, NEG, NEG};
            int i = l;
            while (i <= r) {
                int b = (i - 1) / B;
                int blo = b * B + 1, bhi = min(n, (b + 1) * B);
                if (i == blo && bhi <= r) {
                    acc = mrg(acc, blk[b]);
                    i = bhi + 1;
                } else {
                    Node e = {a[i], a[i], a[i], a[i]};
                    acc = mrg(acc, e);
                    i++;
                }
            }
            int len = sprintf(buf, "%lld\n", acc.b);
            out.append(buf, len);
        }
    }
    fputs(out.c_str(), stdout);
    return 0;
}
~~~
~~~case
1 100000 100000 rand
~~~
~~~case
2 100000 100000 neg
~~~
~~~case
3 100000 100000 pos
~~~
~~~case
4 100000 100000 small
~~~
~~~case
5 100000 100000 extreme
~~~
~~~case
6 2 100000 rand
~~~
