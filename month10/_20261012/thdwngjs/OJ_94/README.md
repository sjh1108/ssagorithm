# 문제 해석
각 드론간의 거리 중 가장 가까운 거리
완탐은 5 * 10^9 = 10억-> 불가능함

# 풀이
## 틀린 접근
처음엔 #93과 유사한 문제처럼 보여서 가지치기로 해결하려고 했음
근데 #93에선 각 편대에 D 거리 제한이 있기에 가능했던 가지치기임

## 접근
### 그렇다면 답의 범위를 좁혀보자
답은 x를 기준으로 정렬하면 세가지 범위로 나뉜다.
  1. 왼쪽 절반 중 1 쌍
  2. 오른쪽 절반 중 1 쌍
  3. 왼쪽과 오른쪽에서 각각 1쌍
그렇다면 1과 2의 경우는 분할 - 정복으로 해결 가능하다, 각 범위를 반으로 계속 쪼갤 수 있기 때문 -> 쪼개서 해결할 수 있으면? 병합하며 3도 가능해진다.
### 범위가 좁혀졌다
각각의 범위에서 최소값보다 가까운 값이 있으면 그 경우를 구하면 된다.
각 절반에서 최소값을 구하면 min_left와 min_right가 나올텐데
그러면 min = min(min_left, min_right)일때, 가운데에서 min보다 멀어지지 않은 값 중에 y값이 min 이하로 차이나는 범위만 탐색하면 #93과 유사한 시간복잡도가 나온다는 것을 예상할 수 있다.

# 구현
## 기본 골조
```java
// 분할 정복 함수
long conquer(int lo, int hi){
    if(hi - lo <= 3){
        // 작은 범위 직접 비교
        return 범위_최소값;
    }
    int mid = (lo + hi) >>> 1;

    long minL = conquer(lo, mid);
    long minR =conquer(mid+1, hi);

    long min = Math.min(minL, minR);

    // 병합하며 최소값 비교
    // 중앙값에서 양옆으로 min 범위
    min = Math.min(min, 범위_최소값);
}
```

## 범위 비교
### 이 부분에서 더 줄일 수 있을 것처럼 보이지만 통과했으니 더 단축을 시도하진 않았음
```java
// min값 init
long min = Math.min(minLeft, minRight);

// 중앙값 추출
int mx = drone[mid][0];

// 중앙값에서 min 범위 내 값 list에 넣기
int s = lo, e = hi;
List<int[]> list = new ArrayList<>();
for(int i = s; i < e; i++){
    int x1 = drone[i][0];

    int gap = mx - x1;

    if(gap * gap < min){
    list.add(drone[i]);
    }
}

// list y기준 정렬
list.sort(Comparator.comparingInt(o -> o[1]));

// list에서 각 요소간 비교
int size = list.size();
for(int i = 0; i < size-1; i++){
    int[] o1 = list.get(i);

    int x1 = o1[0], y1 = o1[1];
    for(int j = i+1; j < size; j++){
        int[] o2 = list.get(j);

        int x2 = o2[0], y2 = o2[1];

        long y = y2 - y1;
        if(y * y > min) break;

        long x = x2 - x1;
        long dist = x*x + y*y;
        min = Math.min(dist, min);
    }
}
```