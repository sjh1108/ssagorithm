# #97 대상의 보물 자루 문제 풀이

## 문제 해석 및 접근
1. 냅색 문제 같다. 배낭에 물건을 담을지 안담을지 정하는 문제
2. 근데 냅색으로 접근할 수 없다. 범위는 2^36으로 무게를 저장하는 배열을 사용할 수 없기 때문
3. 그럼 백트래킹으로 접근해볼까? -> 실패함  
    정확한 계산 없이 간단하게 완탐 + 무게 넘으면 탐색하지 않는 조건으로 접근해봤다. 결과는 예상대로 실패
4. 그러면 반으로 나눠서 접근해볼까? <- 이 부분에서 접근이 오래 걸림  
    보물의 절반씩 나눠서 부분 집합으로 각 절반의 경우의 수를 모두 연산하고, 각 부분 집합끼리 비교하자  
    이때 비교는 [#96](https://oj-oui2.vercel.app/problems/96)에서 활용한 범위에서의 최대값을 활용하면 좋아 보인다.

## 구현
### 부분 집합 연산
각 절반에서의 부분 집합을 연산한다.  
아래 코드는 왼쪽 절반의 부분 집합을 연산하는 내용이다.  
**입력**
```java
nl = N / 2;
nr = N - nl;

arr = new long[nl][2];
for(int i = 0; i < nl; i++){
    st = new StringTokenizer(br.readLine());
    arr[i][0] = Long.parseLong(st.nextToken());
    arr[i][1] = Long.parseLong(st.nextToken());
}
hw = new long[1 << nl];
hv = new long[1 << nl];
cnt = 0;
dfs(0, nl, 0L, 0L);
```
**부분 집합 생성**
```java
private static void dfs(int idx, int end, long sw, long sv){
    if(idx == end){
        hw[cnt] = sw;
        hv[cnt] = sv;

        cnt++;
        return;
    }

    dfs(idx+1, end, sw, sv);
    dfs(idx+1, end, sw + arr[idx][0], sv + arr[idx][1]);
}
```
### 오른쪽 절반 정렬
왼쪽 절반과의 비교를 할 때 효율을 위해서 정렬을 선택했다.  
[#96](https://oj-oui2.vercel.app/problems/96)에서 활용한 범위에서의 최대값을 활용하기 위함이다.
```java
Integer[] order = new Integer[cnt];
for(int i = 0; i < cnt; i++){
    order[i] = i;
}
Arrays.sort(order, (o1, o2) -> Long.compare(rw[o1], rw[o2]));
```

### 이분 탐색
왼쪽 절반과 오른쪽 절반의 비교를 위해서 이분 탐색을 활용했다.  
이유라기 보다는 감인데, 두 개의 정렬된 배열을 활용하는 것 보다는  
이분 탐색을 활용하는 것이 더 정합해보였다.
```java
for(int i = 0; i < len; i++){
    // 이미 lw가 제한보다 높은 경우는 탐색하지 않음
    if(lw[i] > W) continue;

    long cap = W - lw[i];
    
    int lo = 0, hi = cnt-1;

    // 정렬된 내용을 활용한 범위 내 최대값 활용
    while(lo < hi){
        int mid = (lo + hi + 1) >> 1;
        if(rw[order[mid]] <= cap)
            lo = mid;
        else
            hi = mid -1;
    }

    ans = Math.max(ans, lv[i] + max[lo]);
}
```

## 구현 실수
### 배열 초기화 실수
`hw`, `hv` 두 배열에 `dfs()` 함수를 통해 부분 집합을 연산해  
`lw`, `lv` 같은 배열에 저장했는데  
`rw`와 `rv` 배열에 저장하는 과정에서 `hw`와 `hv` 배열을 초기화하지 않아 오답이 발생했다.  

**기존 코드**
```java
/* 이 부분을 작성하지 않았음
*  hw = new long[1 << nr];
*  hv = new long[1 << nr];
*/
dfs(0, nr, 0L, 0L);
long[] rw = hw, rv = hv;
```
**의도된 코드 (현재 코드)**
```java
hw = new long[1 << nr];
hv = new long[1 << nr];
dfs(0, nr, 0L, 0L);
long[] rw = hw, rv = hv;
```
### 정렬 실수
배열 복사의 편의성을 위해서 무게와 가치 배열을 따로 작성했다.  
```java
// 기존 배열
int[] order
```
이로 인해 일반적인 정렬은 하지 못하고, 정렬하는 인덱스 배열을 사용했는데
일반 Primitive 타입인 `int[]` 배열은 `Arrays.sort()` 함수를 활용할 때 `Comparator` 클래스 함수를 사용하지 못한다는 것을 간과했다.
```java
Arrays.sort(order, (o1, o2) -> Long.compare(rw[o1], rw[o2]));
```
따라서  참조형인 `Integer`타입으로 변경해서 작성했다.
```java
// 변경
Integer[] order
```

### 인덱스 실수
이분 탐색 과정에서 실수를 했다.
인덱스만 정렬하는 것에 익숙하지 않아 인덱스를 사용하지 않는 실수를 범했다.

**기존 오답 코드**
```java
// 정렬된 내용을 활용한 범위 내 최대값 활용
while(lo < hi){
    int mid = (lo + hi + 1) >> 1;
    // 이 부분
    if(rw[mid] <= cap)
        lo = mid;
    else
        hi = mid -1;
}
```
**의도된 코드**
```java
// 정렬된 내용을 활용한 범위 내 최대값 활용
while(lo < hi){
    int mid = (lo + hi + 1) >> 1;
    if(rw[order[mid]] <= cap)
        lo = mid;
    else
        hi = mid -1;
}
```