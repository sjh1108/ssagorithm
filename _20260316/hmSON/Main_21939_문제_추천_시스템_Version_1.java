import java.io.*;
import java.util.*;

public class Main_21939_문제_추천_시스템_Version_1 {

    /*
     * BOJ_21939 : 문제 추천 시스템 Version 1 (Gold_4)
     * 자료구조 및 알고리즘 : 우선순위 큐, 해시를 사용한 집합과 맵
     *
     * [문제 요약]
     * - 문제 번호(P)와 난이도(L)를 관리한다.
     * - 명령은 3가지:
     *   1) recommend x : x=1이면 가장 어려운 문제 번호, x=-1이면 가장 쉬운 문제 번호 출력
     *      (난이도 같으면 어려운 쪽은 번호 큰 것, 쉬운 쪽은 번호 작은 것)
     *   2) add P L : 문제 P를 난이도 L로 추가
     *   3) solved P : 문제 P를 제거
     *
     * [핵심 아이디어]
     * - "가장 쉬운/어려운 문제를 즉시 조회"하려면 정렬 구조가 필요하므로 PriorityQueue 2개가 적합하다.
     *   - hard: 난이도 내림차순, 난이도 같으면 번호 내림차순 (최고 난이도 추천)
     *   - easy: 난이도 오름차순, 난이도 같으면 번호 오름차순 (최저 난이도 추천)
     * - solved(P)는 임의 원소 삭제이므로 PriorityQueue에서 직접 삭제가 비효율적이다.
     *   -> HashMap으로 문제 번호 -> Problem 객체를 즉시 찾고,
     *      객체에 removed 플래그를 두어 "lazy deletion(지연 삭제)"로 처리한다.
     * - recommend 요청 시, 큐의 top이 removed라면 유효한 원소가 나올 때까지 poll로 제거한다.
     *
     * [구현 메모]
     * - problems(HashMap): 현재 관리 중인 문제를 번호로 빠르게 찾기 위한 구조
     * - solved 시점:
     *   - HashMap에서 제거하고, 해당 객체의 removed=true로 표시
     *   - 우선순위 큐에는 남아있더라도 recommend 시점에 걸러진다.
     * - add 시점:
     *   - 새로운 Problem 객체를 만들고 HashMap 및 두 PQ에 모두 삽입
     *
     * [시간 복잡도]
     * - add: O(log N)
     * - recommend: 평균 O(log N) (removed 원소가 쌓인 경우 추가 poll이 발생할 수 있음)
     * - solved: O(1) (HashMap 조회 후 플래그 처리)
     */

    static class Problem {
        int idx, diff;
        boolean removed = false; // 지연 삭제 플래그

        public Problem(int idx, int diff) {
            this.idx = idx;
            this.diff = diff;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    // 문제 번호 -> Problem 객체 (현재 유효한 문제만 유지)
    static HashMap<Integer, gold_4.Main_21939.Problem> problems = new HashMap<>();

    // 가장 어려운 문제 추천용 (diff 내림차순, idx 내림차순)
    static PriorityQueue<gold_4.Main_21939.Problem> hard = new PriorityQueue<>((a, b) -> {
        if(a.diff == b.diff) return b.idx - a.idx;
        return b.diff - a.diff;
    });

    // 가장 쉬운 문제 추천용 (diff 오름차순, idx 오름차순)
    static PriorityQueue<gold_4.Main_21939.Problem> easy = new PriorityQueue<>((a, b) -> {
        if(a.diff == b.diff) return a.idx - b.idx;
        return a.diff - b.diff;
    });

    static void init() throws Exception {
        int n = Integer.parseInt(br.readLine());
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken());
            int diff = Integer.parseInt(st.nextToken());
            add(idx, diff);
        }
    }

    static void recommend(boolean isHard) {
        // lazy deletion: 큐의 top이 removed면 유효한 원소가 나올 때까지 제거
        if(isHard) {
            while(!hard.isEmpty() && hard.peek().removed) hard.poll();
            sb.append(hard.peek().idx);
        }
        else {
            while(!easy.isEmpty() && easy.peek().removed) easy.poll();
            sb.append(easy.peek().idx);
        }
        sb.append("\n");
    }

    static void add(int idx, int diff) {
        // 신규 문제 객체 생성
        gold_4.Main_21939.Problem problem = new gold_4.Main_21939.Problem(idx, diff);
        problems.put(idx, problem);
        hard.add(problem);
        easy.add(problem);
    }

    static void remove(int idx) {
        // PQ에서는 직접 삭제하지 않고 removed 플래그만 세팅 (지연 삭제)
        gold_4.Main_21939.Problem target = problems.remove(idx);
        target.removed = true;
    }

    public static void main(String[] args) throws Exception {
        init();

        int cmdCnt = Integer.parseInt(br.readLine());
        for(int i = 0; i<cmdCnt; i++) {
            st = new StringTokenizer(br.readLine());
            String cmd = st.nextToken();

            if(cmd.equals("recommend")) {
                boolean isHard = "1".equals(st.nextToken());
                recommend(isHard);
            } else if(cmd.equals("add")) {
                int idx = Integer.parseInt(st.nextToken());
                int diff = Integer.parseInt(st.nextToken());
                add(idx, diff);
            } else {
                int idx = Integer.parseInt(st.nextToken());
                remove(idx);
            }
        }

        System.out.println(sb);
    }

}