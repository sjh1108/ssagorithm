import java.io.*;
import java.util.*;

public class Main_27945_슬슬_가지를_먹지_않으면_죽는다 {

    /*
     * BOJ_27945 : 슬슬 가지를 먹지 않으면 죽는다 (Gold_3)
     * 자료구조 및 알고리즘 : 최소 스패닝 트리, 크루스칼, Union Find, 정렬
     *
     * [문제 요약]
     * - 정점 V개와 간선 정보가 주어진다.
     * - 각 간선에는 "며칠째에 사용할 수 있는지"를 의미하는 날짜(date)가 붙어 있다.
     * - 1일차부터 시작해서, 날짜가 정확히 1, 2, 3, ... 순서대로 이어지는 간선들을 사용하며
     *   사이클 없이 정점들을 연결해 나가야 한다.
     * - 어느 날짜에서든
     *   1) 해당 날짜의 간선이 존재하지 않거나
     *   2) 그 간선을 추가하면 사이클이 생기면
     *   그 날짜에 더 이상 진행할 수 없으므로 그 날짜를 출력한다.
     * - 끝까지 V-1개의 간선을 추가해 모든 정점을 연결하면 다음 날짜인 V를 출력하게 된다.
     *
     * [핵심 아이디어]
     * - 문제 조건상 "사이클 없이 간선을 하나씩 골라 전체를 연결"해야 하므로
     *   본질적으로 MST를 구성하는 과정과 같다.
     * - 다만 일반적인 MST처럼 "가중치가 작은 간선 선택"이 아니라,
     *   날짜가 1일부터 순서대로 정확히 이어져야 한다는 강한 제약이 있다.
     * - 따라서 간선을 날짜 기준으로 정렬한 뒤,
     *   현재 날짜 d에 해당하는 간선을 하나씩 보면서 크루스칼처럼 Union Find로 사이클 여부를 판정하면 된다.
     * - 날짜 d의 간선이 없거나, union이 실패(사이클 발생)하는 순간 정답은 d이다.
     *
     * [구현 메모]
     * - d는 현재 진행해야 하는 날짜를 의미하며 1부터 시작한다.
     * - edges는 date 기준 오름차순 정렬한다.
     * - 날짜가 V 이상인 간선은 저장하지 않는다.
     *   - 최대로 진행해도 간선은 V-1개까지만 선택 가능하므로,
     *     V일 이상 간선은 정답 판정에 직접 필요하지 않다.
     * - 메인 루프:
     *   - edges.get(d-1)이 정확히 날짜 d의 간선인지 확인
     *   - 아니면 해당 날짜 간선이 없는 것이므로 종료
     *   - 있다면 union 시도
     *   - union 실패면 사이클이므로 종료
     *   - 성공하면 d++
     * - 최종적으로 출력되는 d는 "처음으로 진행하지 못한 날짜"이다.
     *
     * [시간 복잡도]
     * - 간선 정렬: O(E log E)
     * - Union Find 처리: O(E α(V))
     * - 총: O(E log E)
     */

    static class Edge implements Comparable<Edge> {
        int from, to, date;

        public Edge(int from, int to, int date) {
            this.from = from;
            this.to = to;
            this.date = date;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.date, o.date);
        }
    }

    static int v, d = 1;
    static List<Edge> edges = new ArrayList<>();
    static int[] head;

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        head = new int[v];
        for(int i=0; i<v; i++) head[i] = i;

        for(int i=0; i<e; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;
            int date = Integer.parseInt(st.nextToken());

            // 아무리 잘 진행돼도 V일차 전에 종료되므로 date >= V 인 간선은 저장할 필요가 없다.
            if(date >= v) continue;

            edges.add(new Edge(from, to, date));
        }

        // 1일차 간선조차 없다면 바로 1일차에 종료
        if(edges.isEmpty()) {
            System.out.print(1);
            System.exit(0);
        }

        Collections.sort(edges);
    }

    static int find(int x) {
        if(x == head[x]) return x;
        return head[x] = find(head[x]);
    }

    static boolean union(int a, int b) {
        int ra = find(a), rb = find(b);
        if(ra == rb) return false;

        if(ra < rb) head[rb] = ra;
        else head[ra] = rb;
        return true;
    }

    public static void main(String[] args) throws Exception {
        init();

        // 날짜 1, 2, 3, ... 순서대로 정확히 이어지는 간선을 하나씩 사용
        while(d < v) {
            Edge edge = edges.get(d-1);

            // 현재 날짜 d에 해당하는 간선이 없으면 종료
            if(edge.date != d) break;

            // 해당 간선을 추가했는데 사이클이 생기면 종료
            boolean res = union(edge.from, edge.to);
            if(!res) break;

            d++;
        }

        System.out.println(d);
    }

}