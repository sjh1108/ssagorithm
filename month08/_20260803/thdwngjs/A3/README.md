---
title: 해류 관측소 구간 조회
difficulty: SILVER
tags: 누적 합, 구간 질의, 전처리
timeLimit: 2
memoryLimit: 256
isPublic: true
---
<!-- @description -->
해안선을 따라 관측소 $N$개가 1번부터 $N$번까지 일렬로 놓여 있다. $i$번 관측소가 오늘 기록한 해류 속도는 $A_i$ 이고, 해류가 남쪽으로 흐르면 음수, 북쪽으로 흐르면 양수로 기록된다. 값이 $0$ 이면 해류가 없었다는 뜻이다.

연구소는 관측 기록에 대해 $Q$개의 질의를 던진다. 질의는 두 종류다.

- `1 l r` — $l$번부터 $r$번까지 관측소가 기록한 값의 **합**을 구한다.
- `2 l r` — $l$번부터 $r$번까지 관측소 중 기록한 값이 **양수인 관측소의 개수**를 구한다.

관측 기록은 하루가 끝날 때까지 바뀌지 않는다. 즉 질의를 처리하는 동안 $A$ 는 변하지 않는다.

질의마다 답을 구해 출력한다.

#### 예시

$N = 6$, $A = [3, -2, 0, 7, -5, 4]$ 라고 하자.

- `1 1 6` → $3 + (-2) + 0 + 7 + (-5) + 4 = 7$
- `2 1 6` → 양수인 값은 $3$, $7$, $4$ 이므로 $3$
- `1 2 4` → $(-2) + 0 + 7 = 5$
- `2 2 4` → 양수인 값은 $7$ 하나뿐이므로 $1$

값이 $0$ 인 관측소는 양수가 아니므로 두 번째 종류의 질의에서 세지 않는다.
<!-- @input -->
첫째 줄에 관측소의 수 $N$ 과 질의의 수 $Q$ 가 공백으로 구분되어 주어진다. ($1 \le N \le 120{,}000$, $1 \le Q \le 120{,}000$)

둘째 줄에 $A_1, A_2, \dots, A_N$ 이 공백으로 구분되어 주어진다. ($-10^9 \le A_i \le 10^9$)

셋째 줄부터 $Q$개의 줄에 걸쳐 질의가 한 줄에 하나씩 주어진다. 각 줄은 `t l r` 형태이며 $t$ 는 질의의 종류($1$ 또는 $2$), $l$ 과 $r$ 은 구간의 양 끝이다. ($1 \le l \le r \le N$)

구간 합은 32비트 정수 범위를 넘을 수 있다.
<!-- @output -->
각 질의의 답을 입력에 주어진 순서대로 한 줄에 하나씩 출력한다.
<!-- @testcases -->
~~~input sample
6 5
3 -2 0 7 -5 4
1 1 6
2 1 6
1 2 4
2 2 4
1 4 4
~~~
~~~output
7
3
5
1
7
~~~
~~~input
1 2
-1000000000
1 1 1
2 1 1
~~~
~~~output
-1000000000
0
~~~
~~~input
5 3
1000000000 1000000000 1000000000 1000000000 1000000000
1 1 5
2 1 5
1 3 3
~~~
~~~output
5000000000
5
1000000000
~~~
~~~input
4 4
0 0 0 0
1 1 4
2 1 4
1 2 3
2 2 2
~~~
~~~output
0
0
0
0
~~~
~~~input
5 2
-1000000000 -1000000000 -1000000000 -1000000000 -1000000000
1 1 5
2 1 5
~~~
~~~output
-5000000000
0
~~~
~~~input
7 6
-5 5 -5 5 -5 5 -5
1 1 7
2 1 7
1 2 6
2 2 6
1 7 7
2 4 4
~~~
~~~output
-5
3
5
3
-5
1
~~~
<!-- @generator -->
~~~generator python3
import sys, random
tok = sys.stdin.read().split()
seed = int(tok[0]); n = int(tok[1]); q = int(tok[2]); mode = tok[3]
random.seed(seed)
if mode == 'rand':
    vals = [random.randint(-10**9, 10**9) for _ in range(n)]
elif mode == 'pos':
    vals = [10**9] * n
elif mode == 'neg':
    vals = [random.randint(-10**9, 0) for _ in range(n)]
else:
    vals = [random.choice([-1, 0, 1]) for _ in range(n)]
out = ['%d %d' % (n, q), ' '.join(map(str, vals))]
for _ in range(q):
    l = random.randint(1, n); r = random.randint(1, n)
    if l > r: l, r = r, l
    if mode == 'pos':
        l, r = 1, n
    out.append('%d %d %d' % (random.randint(1, 2), l, r))
sys.stdout.write('\n'.join(out) + '\n')
~~~
~~~solution python3
import sys
def main():
    d = sys.stdin.buffer.read().split()
    i = 0
    n = int(d[i]); i += 1
    q = int(d[i]); i += 1
    ps = [0] * (n + 1)
    pc = [0] * (n + 1)
    for k in range(1, n + 1):
        v = int(d[i]); i += 1
        ps[k] = ps[k - 1] + v
        pc[k] = pc[k - 1] + (1 if v > 0 else 0)
    out = []
    for _ in range(q):
        t = d[i]; i += 1
        l = int(d[i]); i += 1
        r = int(d[i]); i += 1
        if t == b'1':
            out.append(ps[r] - ps[l - 1])
        else:
            out.append(pc[r] - pc[l - 1])
    sys.stdout.write('\n'.join(map(str, out)) + '\n')
main()
~~~
~~~validator cpp
#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
int n;
vector<ll> bitS; vector<int> bitC;
void addS(int i, ll v){ for(; i<=n; i+= i&-i) bitS[i]+=v; }
ll qS(int i){ ll r=0; for(; i>0; i-= i&-i) r+=bitS[i]; return r; }
void addC(int i, int v){ for(; i<=n; i+= i&-i) bitC[i]+=v; }
int qC(int i){ int r=0; for(; i>0; i-= i&-i) r+=bitC[i]; return r; }
int main(){
    int q;
    if(scanf("%d %d",&n,&q)!=2) return 0;
    bitS.assign(n+1,0); bitC.assign(n+1,0);
    for(int i=1;i<=n;i++){ ll v; if(scanf("%lld",&v)!=1) return 0; addS(i,v); if(v>0) addC(i,1); }
    string out; out.reserve(1<<20); char buf[32];
    for(int t=0;t<q;t++){
        int ty,l,r; if(scanf("%d %d %d",&ty,&l,&r)!=3) return 0;
        ll ans = (ty==1) ? (qS(r)-qS(l-1)) : (ll)(qC(r)-qC(l-1));
        int len=sprintf(buf,"%lld\n",ans); out.append(buf,len);
    }
    fputs(out.c_str(),stdout);
}
~~~
~~~case
1 120000 120000 rand
~~~
~~~case
2 120000 120000 pos
~~~
~~~case
3 120000 120000 neg
~~~
~~~case
4 120000 120000 tiny
~~~
~~~case
5 2 120000 rand
~~~
