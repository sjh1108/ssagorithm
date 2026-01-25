import java.io.*;
import java.util.*;

public class Main_10429_QUENTO {

    /*
     * BOJ_10429 : QUENTO (Gold_4)
     * 자료구조 및 알고리즘 : DFS, 백트래킹, 비트마스킹
     *
     * [문제 요약]
     * - 3x3 크기의 게임판에 숫자와 연산자(+, -)가 번갈아 배치되어 있다.
     * - 목표 숫자(N)와 사용해야 할 숫자의 개수(M)가 주어진다.
     * - 상하좌우 스와이프를 통해 M개의 숫자를 사용하여 수식을 완성했을 때, 그 결과가 N이 되는 경로를 구하라.
     * - 가능한 경로가 있다면 1과 좌표들을 출력하고, 없으면 0을 출력한다.
     *
     * [핵심 아이디어]
     * - "스와이프"를 통해 경로를 이어나가는 방식이므로 DFS를 사용하여 모든 가능한 경로를 탐색한다.
     * - 수식은 [숫자 -> 연산자 -> 숫자] 순서여야 하므로, 반드시 숫자 칸(짝수 인덱스)에서 시작해야 한다.
     * - M개의 숫자를 쓰려면 사이사이에 M-1개의 연산자가 필요하므로, 탐색 깊이(경로 길이)는 2*M - 1이 된다.
     * - 3x3 그리드이므로 int형 변수 하나(비트마스킹)로 방문 처리를 효율적으로 수행할 수 있다.
     * - 답이 여러 개여도 하나만 출력하면 되므로, 정답을 찾으면 즉시 프로그램을 종료(System.exit)한다.
     *
     * [구현 메모]
     * - swipe[] : 현재 탐색 경로상의 노드 인덱스(y*3 + x)를 순서대로 저장.
     * - calc() : swipe 배열에 저장된 경로를 순회하며 단순 사칙연산 수행.
     * - visited : 비트마스크를 사용하여 (y*3 + x) 번째 비트로 방문 여부 체크.
     *
     * [시간 복잡도]
     * - 경로의 길이 L = 2*M - 1.
     * - 각 칸에서 최대 4방향으로 분기하므로 O(4^L)의 상한을 가지나, 방문 체크와 좁은 맵 특성상 실제 연산량은 훨씬 적다.
     */

    static int res, total;
    static int[] swipe; // 경로 저장 배열 (좌표를 0~8 정수로 변환하여 저장)
    static char[][] map = new char[3][3];
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        res = Integer.parseInt(st.nextToken());   // 목표 결과값
        total = Integer.parseInt(st.nextToken()); // 사용해야 하는 숫자의 개수

        // 숫자 M개 + 연산자 M-1개 = 총 길이 2M-1
        swipe = new int[total*2-1];

        for(int i=0; i<3; i++) {
            String line = br.readLine();
            for(int j=0; j<3; j++) {
                map[i][j] = line.charAt(j);
            }
        }
    }

    static void dfs(int y, int x, int cnt, int visited) {
        // 기저 조건: 정해진 길이만큼 경로를 만들었을 때
        if(cnt == total*2-1) {
            // 계산 결과가 목표값과 같은지 확인
            if(calc() != res) return;

            // 정답 출력
            StringBuilder sb = new StringBuilder();
            sb.append(1).append("\n");
            for (int num : swipe) {
                sb.append(num/3).append(" ").append(num%3).append("\n");
            }
            System.out.println(sb);

            // 답을 찾았으므로 프로그램 즉시 종료 (가지치기 효과)
            System.exit(0);
        }

        for(int i=0; i<4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            // 범위 벗어남 or 이미 방문한 칸(비트마스킹 확인) 체크
            if(ny < 0 || nx < 0 || ny >= 3 || nx >= 3 || (visited & (1 << (ny*3 + nx))) != 0) continue;

            swipe[cnt] = ny * 3 + nx; // 경로 기록
            dfs(ny, nx, cnt+1, visited | 1 << (ny * 3 + nx)); // 다음 단계 탐색 (비트마스크 갱신)
        }
    }

    // 현재까지의 경로(swipe)를 계산하는 함수
    static int calc() {
        int sum = 0, op = 1; // 첫 숫자는 양수로 시작 (op=1)

        for(int i=0; i<swipe.length; i++) {
            char ch = map[swipe[i] / 3][swipe[i] % 3];

            if(ch >= '0' && ch <= '9') {
                // 숫자일 경우 현재 설정된 연산자(부호)를 적용하여 합산
                sum += (ch - '0') * op;
            } else {
                // 연산자일 경우 다음 숫자의 부호 설정
                op = ch == '+' ? 1 : -1;
            }
        }

        return sum;
    }

    public static void main(String[] args) throws Exception {
        init();

        // 0~8번 칸 중 숫자가 있는 칸(짝수 인덱스)에서만 시작
        // (0,0)->0, (0,1)->1(x), (0,2)->2 ...
        for(int i=0; i<9; i+=2) {
            int y = i/3; int x = i%3;
            swipe[0] = i; // 시작점 기록
            dfs(y, x, 1, 1 << i); // 시작점 방문 처리 후 DFS 시작
        }

        // 모든 경우를 탐색해도 답이 없으면 0 출력
        System.out.println(0);
    }
}