# 문제 해석
A1과 유사한 문제지만
이 문제는 각 낱장에서 정렬이 제대로 안된 쌍의 개수를 연산하는 문제이다.
A1에선 N이 3000까지로 범위가 크지 않았기에 완전 탐색으로 해결할 수 있었다.
그러나 이 문제의 경우엔 N이 20만까지 커졌기에 범위가 넓어 완전 탐색으로 불가능하다.

# 풀이
## 완전 탐색
각 쌍을 전체 비교한다 NC2 -> O(N^2) 불가능

## 사고
그러면 각 쌍을 정렬하며 비교하는 것은?
결국에 정렬 알고리즘은 비교 연산이 필수적이기에 합리적이라고 생각된다.
그렇다면 어떤 알고리즘이 적합한가?
1. 버블, 비교
이 정렬은 O(N^2)이기에 결국 불가능하다
2. 퀵, 삽입
이 정렬은 이 문제에 대해선 적용하기 어렵다.
이유는 다음과 같다
* 퀵 : 원소를 미는 개수 = 구하고자 하는 쌍 수
    그러나 시간복잡도가 O(N + ans), 입력이 내림차순으로 주어진 경우 O(N^2)이 됨
* 삽입 : 스왑하는 과정에서 엇갈린 쌍 수를 비교하기 어려움, 
    구현은 가능하나 병합 정렬에 비해 코드 구현이 복잡해질 가능성
3. 병합 정렬
시간 복잡도는 O(NlogN) 보장
결국 각 원소끼리 1회 이상 비교하며, 이전에 비교한 내용을 이용할 수 있게 변형 가능

# 구현
## 병합 정렬
```java
private static void sort(int left, int right){
    if(left >= right) return;

    int mid = (left + right) / 2;
    sort(left, mid);
    sort(mid + 1, right);

    merge(left, mid, right);
}
```
이 부분은 변형이 없기에 이론만 따라가면 간단하다.

## 병합
```java
private static void merge(int left, int mid, int right){
    int i = left;
    int j = mid + 1;
    int k = left;

    while (i <= mid && j <= right) {
      if (arr[i] <= arr[j]) {
        tmp[k++] = arr[i++];
      } else {
        cnt += (mid - i + 1);
        tmp[k++] = arr[j++];
      }
    }

    while (i <= mid) tmp[k++] = arr[i++];
    while (j <= right) tmp[k++] = arr[j++];

    for (int idx = left; idx <= right; idx++) {
      arr[idx] = tmp[idx];
    }
}
```
이 부분에서 변형이 조금 들어갔다.
`cnt += (mid - i + 1);` 이 내용이 추가가 되었는데
오른쪽 부분집합에서 내용이 삽입이 될 때, 남은 왼쪽 부분 집합은 모두 엇갈린 쌍이기 때문에 cnt에 더해주면 된다는 뜻이다.
각 분할된 내용에서 엇갈린 쌍을 계속해서 연산해주면 결국 결과값을 연산할 수 있다.