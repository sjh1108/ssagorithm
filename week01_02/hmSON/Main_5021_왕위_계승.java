package gold_3;

import java.io.*;
import java.util.*;

public class Main_5021 {

    /*
     * BOJ_5021 : 왕위 계승(Gold_3)
     * 자료구조 및 알고리즘 : 위상 정렬(Topological Sort), 해시맵(HashMap)
     *
     * [문제 요약]
     * - 유토피아의 초대 왕이 주어지고, 그 혈통을 100%(1.0)로 가정한다.
     * - 자식은 부모로부터 각각 혈통의 1/2씩을 물려받는다. (아버지 1/2 + 어머니 1/2)
     * - 가족 관계 정보(자식 부모1 부모2)가 주어졌을 때, 후보자들 중 가장 혈통 비율이 높은 사람을 찾는다.
     *
     * [핵심 아이디어]
     * - 사람이 '이름(문자열)'으로 주어지므로 HashMap을 사용하여 고유 Index(0, 1, 2...)를 부여한다.
     * - 부모 -> 자식 방향의 유향 그래프(DAG)를 구성한다. (부모의 혈통이 계산되어야 자식 계산 가능)
     * - 위상 정렬을 수행하며, 부모 노드를 방문할 때 연결된 자식 노드에게 자신의 혈통 * 0.5를 누적시킨다.
     * - 모든 부모(진입 차수)가 처리된 후에야 자식 노드가 큐에 들어가므로 올바른 계산이 가능하다.
     *
     * [구현 메모]
     * - nameToIdx : 이름(String)을 Key, 고유 번호(Integer)를 Value로 저장.
     * - blood[] : 각 사람의 혈통 수치를 저장 (초대 왕은 1.0).
     * - 후보자 목록에 있지만 가족 관계도에 없는 사람은 0.0으로 처리해야 한다.
     * - 배열 크기는 가족 정보 N줄에 대해 최대 3명씩 등장 가능하므로 N*3으로 넉넉히 잡는다.
     *
     * [시간 복잡도]
     * - N개의 줄에 대해 문자열 처리 및 맵핑, 간선 연결 수행.
     * - 모든 사람(V)과 관계(E)를 한 번씩 방문하므로 O(V + E).
     * - 여기서 V <= 3*N, E <= 2*N 이므로 충분히 빠르다.
     */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int rel, candidateCnt, n = 0; // rel: 관계 수, n: 등장한 총 인원 수 카운트
    static int[] prev; // 진입 차수 (부모의 수)
    static HashMap<String, Integer> nameToIdx = new HashMap<>(); // 이름 -> 인덱스 맵핑
    static double[] blood; // 혈통 비율 저장
    static List<Integer>[] graph; // 그래프 (부모 -> 자식)

    static void init() throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        rel = Integer.parseInt(st.nextToken());
        candidateCnt = Integer.parseInt(st.nextToken());

        // 최대 등장 가능한 사람 수는 입력 줄 수(rel) * 3명
        prev = new int[rel * 3];
        blood = new double[rel * 3];
        graph = new ArrayList[rel * 3];

        String founder = br.readLine();
        insertName(founder, n++); // 초대 왕 등록
        blood[0] = 1.0; // 초대 왕 혈통 1.0 설정

        for(int i=0; i<rel; i++) {
            st = new StringTokenizer(br.readLine());
            String child = st.nextToken();
            // 맵에 없는 이름이면 새로 등록
            if(!nameToIdx.containsKey(child)) insertName(child, n++);
            int cIdx = nameToIdx.get(child);

            // 부모 2명 입력 처리
            for(int j=0; j<2; j++) {
                String parent = st.nextToken();
                if(!nameToIdx.containsKey(parent)) insertName(parent, n++);

                int pIdx = nameToIdx.get(parent);
                graph[pIdx].add(cIdx); // 부모 -> 자식 방향 연결
                prev[cIdx]++; // 자식의 진입 차수 증가
            }
        }
    }

    // 이름과 인덱스를 매핑하고 그래프 초기화
    static void insertName(String name, int idx) {
        nameToIdx.put(name, idx);
        graph[idx] = new ArrayList<>();
    }

    static void topologicalSort() {
        Queue<Integer> q = new ArrayDeque<>();

        // 진입 차수가 0인(부모 정보가 없거나, 초대 왕인) 사람 큐에 삽입
        for(int i=0; i<n; i++) {
            if(prev[i] != 0) continue;
            q.add(i);
        }

        while (!q.isEmpty()) {
            int cur = q.poll();

            // 현재 사람(cur)의 자식(next)들에게 혈통 전파
            for(int next : graph[cur]) {
                blood[next] += blood[cur] / 2.0; // 부모 혈통의 절반을 물려받음
                prev[next]--; // 부모 한 명 탐색 완료

                // 두 부모 모두 탐색되었다면 큐에 삽입
                if(prev[next] != 0) continue;
                q.add(next);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        init();
        topologicalSort();

        double maxBlood = 0.0;
        String res = "";

        for(int i=0; i<candidateCnt; i++) {
            String candidate = br.readLine();

            // 관계도에 없는 사람이 후보로 나올 수 있음 (혈통 0)
            if(!nameToIdx.containsKey(candidate)) continue;

            int idx = nameToIdx.get(candidate);
            if(blood[idx] > maxBlood) { // 더 높은 혈통 비율 발견 시 갱신
                maxBlood = blood[idx];
                res = candidate;
            }
        }

        System.out.println(res);
    }

}