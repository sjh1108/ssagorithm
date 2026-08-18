# 37. 가장 위험한 터널


## 문제 분석
남극 빙하 아래에 조사 기지 `N`개가 존재하며 트리 형태를 가지고 있습니다. 기지들을 잇는 각 터널은 위험도가 매겨져 있습니다.

서로 다른 두 기지를 잇는 경로는 하나 뿐이며, 경로 내 터널 중 위험도가 가장 큰 값을 **최대 위험도**, 경로 내 터널 중 "최대 위험도"를 가진 터널의 수를 **위험 구간 수**라고 합니다. 각 질의를 통해 두 기지의 번호가 주어지면 두 기지 간 경로의 **최대 위험도와 위험 구간 수**를 구해야 합니다.

트리 구조를 가진 기지이므로, 두 기지 사이를 이동할 때 반드시 **공통 상위 기지**를 지나야 합니다. 따라서 **LCA(최소 공통 조상)** 로직 활용하여 어떤 경로를 지나는 지 확인하는 동시에 질의 처리 단계에서의 경로 탐색 횟수를 줄이는 것이 목표입니다.


### 1. 경사 트리도 트리다
최악의 경우 모든 기지가 1자로 배치될 수 있습니다. 주어지는 기지의 수는 최대 `100,000`이고 질의의 수도 최대 `100,000`이므로 질의 처리 과정에서 1칸씩 이동하는 로직은 시간 초과를 피할 수 없습니다. 전처리를 하려 해도 이를 감당할 크기의 배열을 만들려 해도 메모리 초과를 피할 수 없을 것입니다.


### 2. LCA의 핵심 로직, 이진 점프
위에서 제시한 경로 탐색 및 전처리 문제를 해결하기 위해 **이진 점프**를 적용합니다. 각 기지를 기준으로 모든 상위 기지의 정보를 기록하는 대신 **1, 2, 4, ...번째 기지의 정보만 기록**하는 것을 목표로 합니다.

기지의 수는 `N`개이므로 트리의 최대 깊이 또한 경사 트리일 경우의 깊이인 `N`입니다. 알고 싶은 정보는 $2*0, 2*1, 2*2, ..., 2^k$번째 기지의 정보이므로 $2^k \ge N$을 만족하는 `k`값을 찾아야 합니다.
주어지는 `N`의 최대값은 `100,000`이므로 $log_2(100,000) = 16.60964...$이고, `k`의 최대값은 `17`입니다.

따라서 각 기지별 상위 기지 번호 기록 전처리를 `100,000 x 17` 크기의 배열만으로도 수행할 수 있게 됩니다. 

### 3. 하는 김에 최대 위험도와 위험 경로 수도 전처리하자
이 문제의 목적은 두 기지 간 경로 내 최대 위험도와 위험 경로 수를 구하는 것입니다. 이들 또한 탐색 횟수 절감을 위해 동일한 방식으로 이진 점프를 이용한 전처리를 수행하면 각 기지별로 1, 2, 4, ...번쨰 상위 기지까지의 최대 위험도와 위험 경로 수를 기록할 수 있습니다.

각각 전처리 배열을 따로 만든다고 해도 `100,000 x 17` 크기의 int 타입 배열 2개와 long 타입 배열 1개가 존재하게 되므로 메모리 초과가 발생하지 않을 것입니다.


## 설계 및 구현


### 1. 인접 리스트
```java
static class Node {
    int id;
    long risk;

    public Node(int id, long risk) {
        this.id = id;
        this.risk = risk;
    }
}
```
```java
static List<Node>[] graph;
```
```java
graph = new List[n];
for(int i=0; i<n; i++) graph[i] = new ArrayList<>();

for(int i=0; i<n-1; i++) {
    st = new StringTokenizer(br.readLine());
    int v1 = Integer.parseInt(st.nextToken()) - 1;
    int v2 = Integer.parseInt(st.nextToken()) - 1;
    long risk = Long.parseLong(st.nextToken());
    graph[v1].add(new Node(v2, risk));
    graph[v2].add(new Node(v1, risk));
}
```

각 기지간 연결 관계를 하나의 그래프로 정리해둘 필요가 있습니다. **각 노드가 목표 기지 번호와 그 경로의 위험도를 가지는 인접 리스트**를 생성하고, 이를 이용해 트리 탐색을 수행할 것입니다.


### 2. 전처리 테이블(상위 기지 번호, 최대 위험도, 위험 경로 수)
```java
static int[][] up, cnt;
static long[][] max;
static int[] depth;
```
```java
while((1 << k) < n) k++;
```
```java
up = new int[k+1][n];
max = new long[k+1][n];
cnt = new int[k+1][n];
depth = new int[n];
for(int i=0; i<=k; i++) Arrays.fill(up[i], -1);
Arrays.fill(depth, Integer.MAX_VALUE);
```

트리 탐색 및 전처리에 앞서 필요한 테이블을 미리 초기화합니다. 상기한 대로 이진 점프에 필요한 `k`값은 최악의 경우인 **경사 트리**를 전제로 잡습니다.

각 기지의 깊이, 상위 기지 번호, 최대 위험도, 위험 경로 수를 전처리할 테이블이 각각 필요합니다.


### 3. BFS를 이용한 트리 탐색
```java
static void bfs() {
    Queue<Integer> q = new ArrayDeque<>();
    q.add(0);
    depth[0] = 0;

    while(!q.isEmpty()) {
        int cur = q.poll();
        int d = depth[cur];

        for(Node next : graph[cur]) {
            if(depth[next.id] <= d) continue;
            depth[next.id] = d + 1;
            up[0][next.id] = cur;
            max[0][next.id] = next.risk;
            cnt[0][next.id] = 1;
            q.add(next.id);
        }
    }
}
```

BFS를 이용하여 임의의 기지 한 곳을 루트 노드로 잡고 트리를 탐색합니다.(해당 문제에서는 어떤 기지를 루트 노드로 잡아도 결과가 변하지 않습니다.)

동시에 각 기지의 깊이 및 $2^0$(1)번째 상위 기지의 번호, 경로의 위험도를 기록합니다. 경로의 수는 어차피 1개이므로 동일하게 기록합니다.


### 4. 이진 점프를 이용한 전처리
```java
 static void buildTable() {
    for(int i=1; i<=k; i++) {
        for(int j=0; j<n; j++) {
            int mid = up[i-1][j];
            if(mid != -1 && up[i-1][mid] != -1) {
                up[i][j] = up[i-1][mid];

                long a = max[i-1][j];
                long b = max[i-1][mid];

                if(a == b) {
                    max[i][j] = a;
                    cnt[i][j] = cnt[i-1][j] + cnt[i-1][mid];
                } else if(a > b) {
                    max[i][j] = a;
                    cnt[i][j] = cnt[i-1][j];
                } else {
                    max[i][j] = b;
                    cnt[i][j] = cnt[i-1][mid];
                }
            }
        }
    }
}
```

이진 점프 로직은 다음 구상으로부터 시작됩니다.
> 어떤 정점 a의 x번째 상위 기지가 b이고, 정점 b의 x번쨰 상위 기지가 c라면, 정점 a의 2x번째 상위 기지는 c이다.

이를 그대로 로직으로 적용한 결과이며, 본부 기지보다 위로 이진 점프한 경우 초기 값인 -1을 유지합니다. 동시에 상위 기지로 이동하는 경로에 대한 최대 위험도 및 위험 경로 수도 다음 기준에 따라 전처리합니다.

"기지 A-B의 최대 위험도 ra와 기지 B-C의 최대 위험도 rb가 있다."
- 1. ra == rb : 두 경로의 최대 위험도가 동일하므로 A-C 경로의 위험 경로 수는 두 경로의 위험 경로 수의 합산값이다.
- 2. ra != rb : A-C 경로의 최대 위험도와 위험 경로 수는 두 경로 중 최대 위험도가 높은 쪽의 수치를 그대로 따른다.


### 5. LCA(최소 공통 조상)
```java
static int curCnt;
static long curMax;

...

static void lca(int u, int v) {
    if(depth[u] < depth[v]) {
        int temp = u; u = v; v = temp;
    }

    curMax = 0; curCnt = 0;
    int diff = depth[u] - depth[v];
    for(int i=0; i<=k; i++) {
        if(((diff >> i) & 1) == 1) {
            merge(max[i][u], cnt[i][u]);
            u = up[i][u];
        }
    }

    if(u == v) return;

    for(int i=k; i>=0; i--) {
        if(up[i][u] != up[i][v]) {
            merge(max[i][u], cnt[i][u]);
            u = up[i][u];
            merge(max[i][v], cnt[i][v]);
            v = up[i][v];
        }
    }

    merge(max[0][u], cnt[0][u]);
    merge(max[0][v], cnt[0][v]);
}
```

두 기지 간의 경로는 반드시 **공통 상위 기지**를 지나야 합니다. 따라서 두 기지의 **최소 공통 조상**을 구하는 동시에 그 과정에서 지나가는 경로들의 최대 위험도와 위험 경로 수를 확인하고 **질의에서 요구하는 두 기지 간 경로의 최대 위험도 및 위험 경로 수를 갱신**해야 합니다.

갱신 로직은 다음과 같습니다.
```java
private static void merge(long m, int c) {
    if(m > curMax) {
        curMax = m; curCnt = c;
    } else if(m == curMax) curCnt += c;
}
```

현재 지나간 경로의 최대 위험도가...
1. max > curMax : 현재까지 기록된 지정 경로 내 최대 위험도보다 크다면 최대 위험도, 위험 경로 수 모두 덮어쓰기
2. max == curMax : 현재까지 기록된 지정 경로 내 최대 위험도와 동일하다면 위험 경로 수만 합산