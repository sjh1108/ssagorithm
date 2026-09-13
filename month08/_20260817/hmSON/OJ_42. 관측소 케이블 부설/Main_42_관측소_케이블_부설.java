import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 정렬, 유니온 파인드, MST(최소 스패닝 트리)
     * 각 후보 케이블은 각각의 부설 비용을 가지고 있고, 최소 비용으로 모든 관측소를 연결해야 함
     * MST를 이용하여 연결 순서에 관계 없이 부설 비용이 작은 케이블부터 설치
     * 이미 연결된 상태인 두 관측소 간 추가 케이블을 설치할 필요가 없음
     *
     * 크루스칼 알고리즘을 이용해 MST 구현
     * - head[] 배열은 각 관측소의 대표 번호 기록(초기값은 자기 자신)
     * - 케이블을 연결할 두 관측소의 대표 번호 확인
     * - 1. 두 관측소의 대표 번호가 같으면 두 관측소는 이미 한 그룹이므로 해당 케이블은 설치하지 않음
     * - 2. 두 관측소의 대표 번호가 다르면 케이블을 설치하고 대표 번호를 둘 중 더 작은 값으로 통일
     * - 이 과정을 반복하여 N-1개의 케이블을 설치하면 총 비용 출력
     * - 위 과정을 거치고도 N-1개의 케이블을 설치하지 못했다면 -1 출력
     */

    // 후보 케이블 클래스. 정렬시 부설 비용 오름차순 정렬
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

    static int v;
    static int[] head; // 각 관측소의 대표 번호

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        head = new int[v];
        for(int i=0; i<v; i++) head[i] = i; // 유니온 파인드에서 head[]의 초기값은 항상 자기 자신

        // 후보 케이블 클래스 저장 및 비용 오름차순 정렬
        List<Edge> edges = new ArrayList<>();
        for(int i=0; i<e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken()) - 1;
            int v2 = Integer.parseInt(st.nextToken()) - 1;
            long cost = Long.parseLong(st.nextToken());
            edges.add(new Edge(v1, v2, cost));
        }
        edges.sort(Comparator.naturalOrder());

        // 비용이 낮은 케이블부터 설치 여부 확인
        // 케이블 설치시 설치 비용을 total에 합산
        long total = 0;
        for(Edge edge : edges) {
            boolean check = union(edge.v1, edge.v2);
            if(check) total += edge.cost;

            // 케이블 설치할 때마다 v값을 디카운트했으므로 v == 1이면 모든 관측소 연결
            if(v == 1) {
                System.out.println(total);
                return;
            }
        }

        // 모든 관측소를 연결할 방법이 없음
        System.out.println(-1);
    }

    // 유니온 파인드의 대표 번호 반환 메서드
    static int find(int x) {
        if(head[x] == x) return x;
        return head[x] = find(head[x]);
    }

    // 유니온 파인드의 그룹 병합 메서드
    static boolean union(int a, int b) {
        int ra = find(a), rb = find(b); // 두 관측소의 대표 번호
        if(ra == rb) return false; // 이미 두 관측소는 동일 그룹

        v--; // 케이블 부설이 가능하므로 남은 그룹 수 디카운트
        if(ra > rb) head[ra] = rb;
        else head[rb] = ra;
        return true;
    }

}