package algostudy.baek.b17135;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main_17135_이용호 {
/*
 * N * M 격자판 각판에 포함된 적의수는 최대 하나
 * 격자판 아래는 성이있고 지키기위해 궁수 3명을 배치하려고 한다
 * 궁수는 성이 있는 칸에 배치 가능(N+1), 한성에 한명만 가능
 * 모든 궁수는 동시공격, 거리 D이하중 가장 가까운 적 공격(거리 같으면 가장 왼쪽을 공격)
 * 같은적이 동시에 공격받을수도있음
 * 공격 끝나면 적은 성쪽으로 한칸 이동 성도착시 게임에서 제외
 * 모든 적 제외되면 끝난다
 * 격자판 상태 주어졌을때 제거할수 있는 적의 최대수 계산
 * D = |r1-r2| + |c1-c2|
 *  
 *  궁수 좌표위치 조합으로 선정
 *  궁수한테 공격좌표 받아놓고 한번에 공격 후 제거
 *  이후 남은 적들 한칸 이동
 *  게임 종료 체크
 */

    static int N, M, D;
    static int[][] origin;
    static int answer = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        origin = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                origin[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 궁수 3명의 열 선택 조합
        int[] archers = new int[3];
        comb(0, 0, archers); // 0 ~ M-1까지 조합
        System.out.println(answer);
    }

    // 궁수 열 위치 조합
    static void comb(int idx, int start, int[] archers) {
        if (idx == 3) {//3명 골랐으면 시뮬레이션 진행 후 최대 제거수 갱신
            answer = Math.max(answer, simulate(archers));
            return;
        }
        for (int i = start; i < M; i++) {
            archers[idx] = i;
            comb(idx + 1, i + 1, archers);
        }
    }

    // 주어진 궁수 열 위치로 최대 처치 수 계산
    static int simulate(int[] archers) {
        int[][] board = copyBoard(origin); //초기 저장 보드 불러오기
        int kills = 0;

        // 적이 모두 사라질 때까지 N번 라운드 반복
        for (int round = 0; round < N; round++) {
            // 궁수 타겟 선정
            boolean[][] toKill = new boolean[N][M];
            for (int a = 0; a < 3; a++) {
                int ar = N;          
                int ac = archers[a]; //조합에서 받아온 궁수 열 인덱스
                int tr = -1, tc = -1;
                int bestDist = Integer.MAX_VALUE;

                //보드 내 모든 적을 보며 최적 타겟 선정
                for (int r = 0; r < N; r++) {
                    for (int c = 0; c < M; c++) {
                        if (board[r][c] != 1) continue;
                        int dist = Math.abs(ar - r) + Math.abs(ac - c);
                        if (dist > D) continue; // 거리 D 이상이면 패스

                        if (dist < bestDist) { //현재 몬스터 거리가 가장 최적이면
                            bestDist = dist;
                            tr = r; tc = c;
                        } else if (dist == bestDist) { //최적 거리 같으면 왼쪽 우선
                            if (c < tc) { tr = r; tc = c; }
                        }
                    }
                }
                if (tr != -1) toKill[tr][tc] = true; //타겟on
            }

            // 동시 제거
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    if (toKill[r][c] && board[r][c] == 1) {
                        board[r][c] = 0;
                        kills++;
                    }
                }
            }

            // 적 이동 (한 칸 아래), 마지막 행은 성에 닿아 사라짐
            for (int r = N - 1; r >= 1; r--) {
                board[r] = board[r - 1];
            }
            board[0] = new int[M]; // 맨 윗줄은 빈 줄로
        }
        return kills;
    }

    static int[][] copyBoard(int[][] src) {
        int[][] dst = new int[N][M];
        for (int i = 0; i < N; i++) dst[i] = Arrays.copyOf(src[i], M);
        return dst;
    }
}
