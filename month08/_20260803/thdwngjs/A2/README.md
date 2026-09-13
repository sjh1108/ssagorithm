---
title: 해류 관측값 정정
difficulty: GOLD
tags: 세그먼트 트리, 펜윅 트리, 구간 합, 자료 구조
timeLimit: 3
memoryLimit: 512
isPublic: true
---
<!-- @description -->
해안선을 따라 관측소 $N$개가 1번부터 $N$번까지 일렬로 놓여 있다. $i$번 관측소가 기록한 해류 속도는 $A_i$ 이고, 남쪽으로 흐르면 음수, 북쪽으로 흐르면 양수다.

관측 장비는 종종 오작동한다. 그래서 연구소는 기록을 조회하는 도중에도 값을 계속 정정한다. 처리해야 할 작업은 $Q$개이고 두 종류다.

- `1 i x` — $i$번 관측소의 기록을 $x$ 로 **바꾼다**. 원래 값이 무엇이었든 정정 후의 값은 $x$ 가 된다.
- `2 l r` — $l$번부터 $r$번까지 관측소가 기록한 값의 **합**을 구한다. 이 시점까지 적용된 정정이 모두 반영된 값으로 계산한다.

작업은 주어진 순서대로 처리한다. 두 번째 종류의 작업마다 답을 출력한다.

#### 예시

$N = 5$, $A = [1, 2, 3, 4, 5]$ 로 시작한다고 하자.

| 작업 | 처리 후 $A$ | 출력 |
|---|---|---|
| `2 1 5` | $[1,2,3,4,5]$ | $1+2+3+4+5 = 15$ |
| `1 3 -10` | $[1,2,-10,4,5]$ | — |
| `2 1 5` | $[1,2,-10,4,5]$ | $1+2-10+4+5 = 2$ |
| `2 3 3` | $[1,2,-10,4,5]$ | $-10$ |
| `1 1 1000000000` | $[10^9,2,-10,4,5]$ | — |
| `2 1 2` | $[10^9,2,-10,4,5]$ | $10^9 + 2 = 1000000002$ |
<!-- @input -->
첫째 줄에 관측소의 수 $N$ 과 작업의 수 $Q$ 가 공백으로 구분되어 주어진다. ($1 \le N \le 120{,}000$, $1 \le Q \le 120{,}000$)

둘째 줄에 $A_1, A_2, \dots, A_N$ 이 공백으로 구분되어 주어진다. ($-10^9 \le A_i \le 10^9$)

셋째 줄부터 $Q$개의 줄에 걸쳐 작업이 한 줄에 하나씩 주어진다.

- 첫 번째 종류는 `1 i x` 형태다. ($1 \le i \le N$, $-10^9 \le x \le 10^9$)
- 두 번째 종류는 `2 l r` 형태다. ($1 \le l \le r \le N$)

두 번째 종류의 작업은 적어도 하나 주어진다. 구간 합은 32비트 정수 범위를 넘을 수 있다.
<!-- @output -->
두 번째 종류의 작업마다 구간 합을 한 줄에 하나씩, 입력에 주어진 순서대로 출력한다.
<!-- @testcases -->
~~~input sample
5 6
1 2 3 4 5
2 1 5
1 3 -10
2 1 5
2 3 3
1 1 1000000000
2 1 2
~~~
~~~output
15
2
-10
1000000002
~~~
~~~input
1 4
1000000000
2 1 1
1 1 -1000000000
2 1 1
2 1 1
~~~
~~~output
1000000000
-1000000000
-1000000000
~~~
~~~input
5 5
1000000000 1000000000 1000000000 1000000000 1000000000
2 1 5
1 3 -1000000000
2 1 5
1 1 1000000000
2 2 4
~~~
~~~output
5000000000
3000000000
1000000000
~~~
~~~input
3 6
1 2 3
1 2 100
1 2 -100
1 2 0
2 1 3
1 1 -1
2 1 3
~~~
~~~output
4
2
~~~
~~~input
4 4
0 0 0 0
2 1 4
1 4 -7
2 4 4
2 1 4
~~~
~~~output
0
-7
-7
~~~
<!-- @generator -->
~~~generator python3
import sys, random
tok = sys.stdin.read().split()
seed = int(tok[0]); n = int(tok[1]); q = int(tok[2]); mode = tok[3]
random.seed(seed)
if mode == 'rand':
    vals = [random.randint(-10**9, 10**9) for _ in range(n)]
elif mode == 'max':
    vals = [10**9] * n
elif mode == 'zero':
    vals = [0] * n
else:
    vals = [random.randint(-5, 5) for _ in range(n)]
out = ['%d %d' % (n, q), ' '.join(map(str, vals))]
qcnt = 0
for k in range(q):
    force_query = (k == q - 1 and qcnt == 0)
    if not force_query and random.randint(1, 100) <= 50:
        i = random.randint(1, n)
        if mode == 'max':
            x = 10**9
        elif mode == 'small':
            x = random.randint(-5, 5)
        else:
            x = random.randint(-10**9, 10**9)
        out.append('1 %d %d' % (i, x))
    else:
        qcnt += 1
        l = random.randint(1, n); r = random.randint(1, n)
        if l > r: l, r = r, l
        if mode == 'max': l, r = 1, n
        out.append('2 %d %d' % (l, r))
sys.stdout.write('\n'.join(out) + '\n')
~~~
~~~solution python3
import sys
def main():
    d = sys.stdin.buffer.read().split()
    i = 0
    n = int(d[i]); i += 1
    q = int(d[i]); i += 1
    a = [0] * (n + 1)
    tree = [0] * (n + 1)
    for k in range(1, n + 1):
        v = int(d[i]); i += 1
        a[k] = v
        tree[k] += v
        j = k + (k & -k)
        if j <= n:
            tree[j] += tree[k]
    out = []
    for _ in range(q):
        t = d[i]; i += 1
        x = int(d[i]); i += 1
        y = int(d[i]); i += 1
        if t == b'1':
            diff = y - a[x]
            a[x] = y
            k = x
            while k <= n:
                tree[k] += diff
                k += k & -k
        else:
            s = 0
            k = y
            while k > 0:
                s += tree[k]
                k -= k & -k
            k = x - 1
            while k > 0:
                s -= tree[k]
                k -= k & -k
            out.append(s)
    sys.stdout.write('\n'.join(map(str, out)) + '\n')
main()
~~~
~~~validator cpp
#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
int main(){
    int n,q;
    if(scanf("%d %d",&n,&q)!=2) return 0;
    int sz=1; while(sz<n) sz<<=1;
    vector<ll> t(2*sz,0);
    for(int i=0;i<n;i++){ ll v; if(scanf("%lld",&v)!=1) return 0; t[sz+i]=v; }
    for(int i=sz-1;i>=1;i--) t[i]=t[2*i]+t[2*i+1];
    string out; out.reserve(1<<20); char buf[32];
    for(int k=0;k<q;k++){
        int ty; ll x,y; if(scanf("%d %lld %lld",&ty,&x,&y)!=3) return 0;
        if(ty==1){ int j=sz+(int)x-1; t[j]=y; for(j>>=1;j;j>>=1) t[j]=t[2*j]+t[2*j+1]; }
        else{ int lo=sz+(int)x-1, hi=sz+(int)y; ll s=0;
            while(lo<hi){ if(lo&1) s+=t[lo++]; if(hi&1) s+=t[--hi]; lo>>=1; hi>>=1; }
            int len=sprintf(buf,"%lld\n",s); out.append(buf,len); }
    }
    fputs(out.c_str(),stdout);
}
~~~
~~~case
1 120000 120000 rand
~~~
~~~case
2 120000 120000 max
~~~
~~~case
3 120000 120000 zero
~~~
~~~case
4 120000 120000 small
~~~
~~~case
5 2 120000 rand
~~~
