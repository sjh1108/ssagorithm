import java.io.*;
import java.util.*;

public class Main_21944_문제_추천_시스템_Version_2 {

    /*
     * BOJ_21944 : 문제 추천 시스템 Version 2 (Gold_2)
     * 자료구조 및 알고리즘 : 트리셋, 해시맵
     *
     * [문제 요약]
     * - 문제 번호(P), 난이도(L), 알고리즘 분류(G)를 관리한다.
     * - 명령은 다음을 포함한다:
     *   - recommend G x : 분류 G 내에서 x=1(가장 어려운), x=-1(가장 쉬운) 문제 번호 출력 (동점 시 번호 큰/작은 규칙)
     *   - recommend2 x  : 전체 문제에서 x=1(가장 어려운), x=-1(가장 쉬운) 문제 번호 출력
     *   - recommend3 x L:
     *       x=1 -> 난이도 L 이상인 문제 중 "가장 쉬운" 문제 번호 출력 (동점 시 번호 작은)
     *       x=-1 -> 난이도 L 미만인 문제 중 "가장 어려운" 문제 번호 출력 (동점 시 번호 큰)
     *     없으면 -1 출력
     *   - add P L G : 문제 추가
     *   - solved P  : 문제 제거
     *
     * [핵심 아이디어]
     * - recommend3처럼 "특정 난이도 기준 이상/미만에서 조건에 맞는 1개를 빠르게 찾기"가 포함되므로
     *   단순 PriorityQueue만으로는 처리가 어렵고, 정렬된 상태에서 구간 경계 탐색이 가능한 TreeSet이 적합하다.
     * - 문제 번호로 삭제/조회도 필요하므로 HashMap(P -> Problem)을 함께 사용한다.
     * - TreeSet을
     *   - hard[algo] : 알고리즘 분류별 문제 집합
     *   - hard[0]    : 전체 문제 집합
     *   으로 유지하면, 분류별/전체 추천을 같은 로직으로 처리할 수 있다.
     *
     * [구현 메모]
     * - TreeSet 정렬 기준(Comparator):
     *   - 난이도 내림차순, 난이도 같으면 번호 내림차순
     *   - 이 기준에서
     *     - first() : 가장 어려운(난이도 최대, 번호 최대)
     *     - last()  : 가장 쉬운(난이도 최소, 번호 최소)
     * - recommend3 처리:
     *   - pivot = (idx=-1, diff=L) 형태의 임시 객체를 만들어 경계 탐색에 사용한다.
     *   - x=1 (L 이상 중 가장 쉬운):
     *     - 기준이 "내림차순"이므로, L 이상 구간에서 가장 쉬운 쪽은 경계에 가까운 원소가 된다.
     *     - floor(pivot)을 사용해 (난이도 >= L) 중 pivot 이하에서 가장 큰 원소를 찾는다.
     *   - x=-1 (L 미만 중 가장 어려운):
     *     - L 미만 중 가장 어려운은 "L 바로 아래"에 있는 원소가 된다.
     *     - higher(pivot)을 사용해 pivot보다 더 큰(내림차순 기준에서) 첫 원소를 찾는다.
     *   - 결과가 null이면 조건을 만족하는 문제가 없으므로 -1 출력.
     * - add/solved:
     *   - HashMap과 해당 분류 TreeSet, 그리고 전체 TreeSet(hard[0])에 동시에 반영한다.
     *
     * [시간 복잡도]
     * - TreeSet 삽입/삭제/탐색: O(log N)
     * - add/solved/recommend 계열: O(log N) (first/last는 O(1)이지만 균형 트리 특성상 접근은 상수)
     */

    static class Problem {
        // 문제 번호, 난이도, 알고리즘 분류
        int idx, diff, algo;

        public Problem(int idx, int diff, int algo) {
            this.idx = idx;
            this.diff = diff;
            this.algo = algo;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    // 문제 번호로 즉시 문제 객체를 반환받기 위해 해시맵 관리
    static HashMap<Integer, gold_2.Main_21944.Problem> problems = new HashMap<>();
    // 각 알고리즘별 문제를 난이도 높은 순으로 정렬된 상태의 리스트로 관리
    // 0번 트리셋은 알고리즘 분류와 관계 없이 모든 문제를 관리, 그 외에는 알고리즘 분류별 문제 관리
    static TreeSet<gold_2.Main_21944.Problem>[] hard = new TreeSet[101];

    static void init() throws Exception {
        for(int i=0; i<=100; i++) {
            // 정렬 : (1) 난이도 높은 순(내림차순), (2) 문제 번호 높은 순(내림차순)
            hard[i] = new TreeSet<>((a, b) -> {
                if(a.diff == b.diff) return b.idx - a.idx;
                return b.diff - a.diff;
            });
        }

        int n = Integer.parseInt(br.readLine());
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken());
            int diff = Integer.parseInt(st.nextToken());
            int algo = Integer.parseInt(st.nextToken());
            add(idx, diff, algo);
        }
    }

    // 문제 추가 메서드
    static void add(int idx, int diff, int algo) {
        gold_2.Main_21944.Problem problem = new gold_2.Main_21944.Problem(idx, diff, algo);
        problems.put(idx, problem);
        hard[algo].add(problem);
        hard[0].add(problem);
    }

    // 문제 삭제 메서드(solve)
    static void remove(int idx) {
        gold_2.Main_21944.Problem target = problems.remove(idx);
        hard[target.algo].remove(target);
        hard[0].remove(target);
    }

    public static void main(String[] args) throws Exception {
        init();

        int cmdCnt = Integer.parseInt(br.readLine());
        for(int i = 0; i<cmdCnt; i++) {
            st = new StringTokenizer(br.readLine());
            String cmd = st.nextToken();

            if(cmd.startsWith("recommend")) {
                // 전체 문제 중 가장 어려운/ 가장 쉬운 문제 반환
                if(cmd.endsWith("2")) {
                    boolean isHard = "1".equals(st.nextToken());
                    // 가장 어려운 -> first(), 가장 쉬운 -> last()
                    sb.append(isHard ? hard[0].first().idx : hard[0].last().idx).append("\n");
                }
                // 전체 문제 중 난이도 ref 이상인 문제 중 제일 쉬운/ 난이도 ref 미만인 문제 중 제일 어려운 문제 반환
                else if(cmd.endsWith("3")) {
                    boolean isHard = "1".equals(st.nextToken());
                    int ref = Integer.parseInt(st.nextToken());

                    // recommend3용 경계 탐색 기준(난이도 ref 주변)을 만들기 위한 pivot
                    gold_2.Main_21944.Problem pivot = new gold_2.Main_21944.Problem(-1, ref, -1);
                    // 난이도 ref 이상인 문제 중 가장 쉬운 -> floor(), 난이도 ref 미만인 문제 중 가장 어려운 -> higher()
                    gold_2.Main_21944.Problem p = isHard ? hard[0].floor(pivot) : hard[0].higher(pivot);
                    sb.append(p == null ? -1 : p.idx).append("\n");
                }
                // 지정된 알고리즘 분류에서 가장 어려운/ 가장 쉬운 문제 반환
                else {
                    int algo = Integer.parseInt(st.nextToken());
                    boolean isHard = "1".equals(st.nextToken());
                    sb.append(isHard ? hard[algo].first().idx : hard[algo].last().idx).append("\n");
                }
            } else if(cmd.equals("add")) {
                int idx = Integer.parseInt(st.nextToken());
                int diff = Integer.parseInt(st.nextToken());
                int algo = Integer.parseInt(st.nextToken());
                add(idx, diff, algo);
            } else {
                int idx = Integer.parseInt(st.nextToken());
                remove(idx);
            }
        }

        System.out.println(sb);
    }

}