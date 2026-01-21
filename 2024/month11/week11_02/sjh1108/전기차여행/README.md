# Logic

1. 다익 2회를 생각함
> 전염병
    * 전염병이 퍼지는 시간을 미리 계산하는 다익스트라
> 충전 및 이동
    * 충전과 이동을 고려하여 메모이제이션을 활용하는 다익스트라를 하면 되지 않을까?

2. 충전 및 이동
    * 이동간에 배터리 총량마다 시간을 기록하고, 시간을 기준으로 다익스트라 계산
3.  충전 완료시간 == 전염병 도착 시간인 경우에 PQ에서 넣지 않도록 / 계산하지 않도록


# Data Structure
* City, Road를 기록하는 자료구조가 필요하겠구나
* City의 충전량
-> City의 충전량은 배열로 저장
* City와 연결된 Road, 그리고 Road의 필요 배터리
* Road의 ID는 1,000,000,000이하
-> Road의 ID값은 Road의 전체 양에 비해서 너무 크니까 배열로 저장하기는 힘들겠구나
-> 연결된 도로는 ID를 저장하는 HashSet을 ArrayList를 통해서 관리하자
-> 도로는 ID를 기준으로 HashMap으로 관리하고

> 결과물
    ```java
        Class Road{}
        ArrayList<HashSet<Integer>> roadsFromCities;
        HashMap<Integer, Road> roadMap;
        int[] mCharge;
    ```

# Func 및 회고
1. add 메소드 (init 메소드 내부에서도 동작할 수 있도록 작성)
    ```java
    roadsFromCities.get(sCity).add(roadID);
    roads.add(new Road());
    ```
2. remove 메소드
    ```java
    roadsFromCities.get(sCity).remove(roadID);
    roads.remove(new Road());
    ```

3. cost 메소드
    > 바이러스 다익스트라

        * 그냥 평범한 다익스트라를 적용함

    ```java
        int[] virus = new int[N];
            Arrays.fill(virus, Integer.MAX_VALUE);

            Queue<int[]> virusQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));

            for(int i = 0; i < M; i++){
                virusQueue.add(new int[]{mCity[i], mTime[i]});
                virus[mCity[i]] = mTime[i];
            }

            while (!virusQueue.isEmpty()) {
                int[] curr = virusQueue.poll();
                int u = curr[0];
                int time = curr[1];

                if (virus[u] < time) continue;

                for (int mId : roadsFromCities.get(u)) {
                    Road road = roadMap.get(mId);
                    int v = road.eCity;
                    int nextTime = time + road.mTime;

                    if (virus[v] > nextTime) {
                        virus[v] = nextTime;
                        virusQueue.offer(new int[]{v, nextTime});
                    }
                }
            }
    ```
    > 충전 다익스트라

        * 다익스트라 내에서 두 가지 로직을 작성함
            * 해당 도시에서 충전을 하는 내용을 PQ에 삽입
            * 해당 도시에서 다른 도시로 향하는 간선을 탐색하여 PQ에 삽입
    ```java
        int[][] dist = new int[N][B + 1];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));

        dist[sCity][B] = 0;
        pq.offer(new int[]{sCity, 0, B});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int to = cur[0];
            int time = cur[1];
            int battery = cur[2];

            if (dist[to][battery] < time) continue;
            if (to == eCity) return time;

            for (int mId : roadsFromCities.get(to)) {
                Road road = roadMap.get(mId);
                
                if (battery >= road.mPower) {
                    int nextTo = road.eCity;
                    int nextTime = time + road.mTime;

                    if (virus[nextTo] <= nextTime) continue;

                    int nextBattery = battery - road.mPower;
                    if (dist[nextTo][nextBattery] > nextTime) {
                        for (int i = nextBattery; i >= 0; --i) {
                            if (dist[nextTo][i] < nextTime) break;
                            dist[nextTo][i] = nextTime;
                        }
                        pq.offer(new int[]{nextTo, nextTime, nextBattery});
                    }
                }
            }
            if (battery < B) {
                int nextTime = time + 1;

                if (virus[to] > nextTime) {
                    int nextBattery = battery + mCharge[to];
                    if (nextBattery > B) nextBattery = B;

                    if (dist[to][nextBattery] > nextTime) {
                        for (int i = nextBattery; i >= 0; --i) {
                            if (dist[to][i] < nextTime) break;
                            dist[to][i] = nextTime;
                        }
                        pq.offer(new int[]{to, nextTime, nextBattery});
                    }
                }
            }
        }
    ```
4. 회고 135 Line
```java
    /*
    * 이 부분을 위에 for문 안에 넣으려고 고민하다가 오래걸림
    * 넣으면 안되는걸 넣고자 하니 안되는거였음
    */
```