import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    // 자료구조 및 알고리즘 : DFS, 트리 DP
    // 트리 형태의 조직도를 가진 회사에서 각 팀별 최소 1인을 선발하되, 이로 인해 발생하는 매출액 손실을 최소화해야 함
    // 각 직원별 선발시의 손실액 최소값과 미선발시의 손실액 최소값을 기록하는 DP 배열 2개 생성

    // 모든 노드는 다음 규칙에 따라 손실액을 계산한다
    // 1. 각 팀원 노드의 선발/ 미선발시 손실액을 비교하여 더 작은 값을 찾고 그 값들을 전부 합한다
    // 2. 이와 별개로 (선발시 손실액 - 미선발시 손실액)이 가장 작은 팀원의 직원 번호와 그 값을 별도로 기록한다
    // 3. 각 팀장 노드의 선발시 손실액 = (본인 매출액 + 1번 과정에서 구한 각 팀원별 손실액 합)
    // 4. 미선발시 손실액은 2번 과정의 결과에 따라 결정된다.
    // 4-1. 손실액 차 최소값 == INF -> 본인 밑에 팀원이 없어서 기본값이 그대로 남은 것. 리프 노드이므로 0으로 기록
    // 4-2. 손실액 차 최소값 < 0 -> 이미 확정적으로 선발된 팀원이 존재함. 1번 과정의 값 그대로 기록
    // 4-3. 그 외 -> 손실액 차가 제일 작은 팀원을 강제 선발해야 함. (1번 과정의 값 + 손실액 차) 기록

    // sales: 각 직원별 매출액, links: 각 직원간 관계(0-팀장, 1-팀원)
    public int solution(int[] sales, int[][] links) {
        final long INF = (long)1e12; // 기본값
        int len = sales.length; // 직원 수
        List<Integer>[] graph = new ArrayList[len]; // 조직도 인접 리스트
        for(int i=0; i<len; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int[] link : links) {
            int v1 = link[0] - 1, v2 = link[1] - 1;
            graph[v1].add(v2); // 문제에서 links 배열의 팀장-팀원 관계를 설명하고 있으므로 단방향만 등록
        }

        // DP 배열(각각 참석/ 미참석시 손실액)
        // long타입인 이유 -> 반환 값 자체는 int 범위임을 보장하고 있으나, 풀이 중 계산되는 값에 대해서는 보장하지 않고 있음
        long[] attended = new long[len], notAttended = new long[len];
        int[] stack = new int[len]; // DFS용 스택
        boolean[] visited = new boolean[len]; // 방문 여부 처리
        stack[0] = 0; // 직원 번호 0은 CEO(루트 노드)
        int top = 1; // 스택 포인터

        // 스택 DFS를 이용한 트리 DP 수행
        while(top > 0) {
            int cur = stack[top-1];
            if(!visited[cur]) { // 진입 시점 : 하위 노드 stack에 추가 후 현재 노드 방문 처리
                for(int next : graph[cur]) stack[top++] = next;
                visited[cur] = true;
            } else { // 탈출 시점 : 현재 노드의 손실액 계산 후 상위 노드로 탈출
                // 과정 1 : 각 팀원(하위 노드)별 선발/미선발 손실액 중 더 작은 값을 합하기
                // 과정 2 : 팀원 중 선발/미선발 손실액 차이가 제일 작은 팀원의 손실액 차이 기록
                long sum = 0, minDiff = INF;
                for(int next : graph[cur]) {
                    // 손실액 차 최소값 갱신
                    long curDiff = attended[next] - notAttended[next];
                    if(curDiff < minDiff) minDiff = curDiff;

                    // 선발/미선발 손실액 중 더 작은 쪽을 합계에 추가
                    sum += curDiff < 0 ? attended[next] : notAttended[next];
                }

                // 과정 3 : 팀장(현재 노드) 선발시 손실액 = sum + 본인 매출액
                attended[cur] = sum + sales[cur];

                // 과정 4 : 팀장 미선발시 손실액
                // 1. minDiff == INF -> 팀원(하위 노드) 없음. 리프 노드이므로 손실액 == 0
                // 2. minDiff < 0 -> 팀원 중 최소 1명이 확정 선발 상태임. 손실액 == sum
                // 3. minDiff >= 0 -> 선발시 추가 손실액이 제일 작은 팀원 강제 선발. 손실액 == sum + minDiff
                notAttended[cur] = minDiff == INF ? 0 :
                        sum + (minDiff < 0 ? 0 : minDiff);

                // 현재 노드에서 탈출
                top--;
            }
        }

        // CEO(루트 노드)의 선발/미선발 손실액 중 더 작은 값이 곧 회사 전체의 최소 손실액임
        // (int) 강제 형변환 : DP 배열은 long 타입 선언이 필요하지만, 반환값 자체는 int를 요구하고 있음
        return (int)Math.min(attended[0], notAttended[0]);
    }

}