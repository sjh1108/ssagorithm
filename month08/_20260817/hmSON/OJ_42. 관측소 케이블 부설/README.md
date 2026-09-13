# 42. 관측소 케이블 부설


## 문제 분석
관측소 `N`개와 후보 케이블 `M`개가 주어지며, 각 후보 케이블은 **연결해야 할 두 관측소의 번호, 부설 비용**이 정해져 있습니다. 후보 케이블들만 활용하여 **모든 관측소를 연결할 때 발생하는 총 부설 비용을 최소화**하는 것이 이 문제의 목적입니다.

임의의 두 관측소 간에 몇 번의 다른 관측소를 거치든 상관 없이 연결만 가능하면 됩니다. 따라서 비용이 작은 케이블부터 확인하며 사이클을 이루지 않도록 `N-1`개의 케이블을 선택하는 **"MST(최소 신장 트리)"**를 적용하여 이 문제를 해결할 수 있을 것입니다.

그 중에서도 케이블 전체를 비용 오름차순으로 정렬한 뒤 비용이 작은 케이블부터 선택하고, **유니온 파인드**를 이용해 두 관측소를 하나의 그룹으로 연결하여 **사이클 탐지 및 그룹 연결**을 수행하는 **크루스칼 알고리즘**으로 이 문제를 해결하려 하였습니다.


## 설계 및 구현


### 1. 케이블 상태 정의 : Edge 클래스
```java
static class Edge implements Comparable<Edge> {
    int v1, v2;
    long cost;

    public Edge(int v1, int v2, long cost) {
        this.v1 = v1;
        this.v2 = v2;
        this.cost = cost;
    }

    @Override
    public int compareTo(Edge o) {
        return Long.compare(this.cost, o.cost);
    }
}
```
```java
List<Edge> edges = new ArrayList<>();
for(int i=0; i<e; i++) {
    st = new StringTokenizer(br.readLine());
    int v1 = Integer.parseInt(st.nextToken()) - 1;
    int v2 = Integer.parseInt(st.nextToken()) - 1;
    long cost = Long.parseLong(st.nextToken());
    edges.add(new Edge(v1, v2, cost));
}
edges.sort(Comparator.naturalOrder());
```

후보 케이블의 정보를 `Edge` 클래스로 관리합니다. 각 객체는 후보 케이블이 연결해야 할 두 관측소의 번호와 부설 비용을 가집니다. 또한 정렬 기준을 `cost`(부설 비용)으로 재정의하여 기본 정렬시 비용 오름차순으로 후보 케이블을 정렬하게 됩니다.


### 2. 관측소 그룹 상태 정의 : int 타입 head 배열
```java
static int[] head;
```
```java
head = new int[v];
for(int i=0; i<v; i++) head[i] = i;
```

유니온 파인드 로직에 필요한 그룹별 대표 번호 관리 배열입니다. **각 그룹의 초기 대표 번호는 자기 자신**입니다.


### 3. 유니온 파인드
```java
static int find(int x) {
    if(head[x] == x) return x;
    return head[x] = find(head[x]);
}

static boolean union(int a, int b) {
    int ra = find(a), rb = find(b);
    if(ra == rb) return false;

    v--;
    if(ra > rb) head[ra] = rb;
    else head[rb] = ra;
    return true;
}
```

`find` 메서드는 주어진 관측소가 속한 그룹의 대표 관측소 번호를 반환합니다. 동시에 재귀 횟수가 늘어나는 것을 방지하기 위해 경로 압축을 동시에 수행합니다.

`union` 메서드는 두 관측소가 하나의 그룹에 속한 상태인지 판단하고, 그렇지 않다면 두 그룹을 하나로 합칩니다. 이 과정에서 트리의 깊이가 커지는 것을 조금이라고 방지하고자 두 대표 관측소 번호 중 더 작은 쪽이 합쳐진 그룹의 대표 관측소가 됩니다.


### 4. MST(최소 신장 트리)
```java
long total = 0;
for(Edge edge : edges) {
    boolean check = union(edge.v1, edge.v2);
    if(check) total += edge.cost;

    if(v == 1) {
        System.out.println(total);
        return;
    }
}
System.out.println(-1);
```

`check`는 부설하려는 케이블이 사이클을 발생시키는 지 확인하는 플래그입니다. 만약 `false`를 반환할 경우 이미 두 관측소 `v1`, `v2`는 이미 한 그룹에 속한 상태임을 의미합니다. 케이블을 부설한 경우, 그 비용을 `total`에 합산합니다.

`union` 메서드에서 그룹 병합시 `v`값을 디카운트하고 있었습니다. 케이블을 부설한 이후 `v == 1`이 되면 총 `v-1`개의 케이블을 부설하여 **모든 관측소를 연결**하였다는 의미입니다.

그러한 경우 그 시점까지 발생한 총 비용을 출력 후 코드를 종료하고, 모든 케이블을 확인하고도 모든 관측소를 연결하지 못한 경우 `-1`을 출력합니다.