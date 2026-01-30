package week01_04.sjh1108.BOJ_17135;

import java.io.*;
import java.util.*;

// 백준 17135 - 캐슬 디펜스 (Simulation, Brute-force, Combination)
class Main {
    private static final int INF = 1024;

    private static int N, M, D; // N: 행, M: 열, D: 궁수 사정거리
    private static int ans; // 최대 제거 가능한 적의 수

    private static int[][] map, mep; // map: 초기 상태, mep: 시뮬레이션용 복사본

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        map = new int[N+1][M];
        mep = new int[N+1][M];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            
            for(int j = 0; j < M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 궁수 3명을 배치할 수 있는 모든 조합을 생성하여 시뮬레이션 수행
        comb(0, M, 3, new ArrayList<>());

        System.out.println(ans);
    }

    // 궁수 위치 조합 생성 (0 ~ M-1 열 중 3개 선택)
    private static void comb(int start, int cnt, int r, List<Integer> list){
        if(r == 0){
            // 맵 초기화 (원본 맵 보존을 위해 mep에 복사)
            init();

            // 선택된 궁수 위치(list)로 공격 시뮬레이션 시작
            attack(list);

            return;
        }

        for(int i = start; i < cnt; i++){
            list.add(i);

            comb(i+1, cnt, r-1, list);

            list.remove(list.size()-1);
        }
    }

    // 시뮬레이션용 맵 초기화
    private static void init(){
        for(int i = 0; i < N; i++){
            mep[i] = Arrays.copyOf(map[i], M);
        }
    }

    // 공격 시뮬레이션
    private static void attack(List<Integer> list){
        int res = 0; // 제거한 적의 수

        // 총 N번의 턴 진행 (적이 한 칸씩 내려오므로 N번이면 모든 적이 사라짐)
        for(int turn = 0; turn < N; turn++){
            boolean[][] visited = new boolean[N][M]; // 이번 턴에 공격받은 적 표시

            // 각 궁수에 대해 타겟 탐색
            for(int k = 0; k < list.size(); k++){
                int location = list.get(k); // 궁수의 열 위치 (행은 항상 N)

                int minD = INF; // 가장 가까운 거리
                int minX = INF; // 타겟 행
                int minY = INF; // 타겟 열

                // 맵 전체 탐색하여 사정거리 내의 적 중 가장 가깝고 왼쪽인 적 찾기
                for(int i = 0; i < N; i++){
                    for(int j = 0; j < M; j++){
                        // 적이 있는 경우
                        if(mep[i][j] == 1){
                            int d = getDistance(i, j, N, location); // 거리 계산
                            
                            // 사정거리 D 이내이고, 현재까지 찾은 최단 거리보다 작거나 같으면
                            if(minD >= d){
                                // 더 가까운 적 발견
                                if(minD > d){
                                    minD = d;
                                    minX = i;
                                    minY = j;
                                } 
                                // 거리가 같다면 더 왼쪽에 있는 적 선택
                                else if(minY > j){
                                    minX = i;
                                    minY = j;
                                }
                            }
                        }
                    }
                }

                // 타겟을 찾았고 사정거리 내라면 공격 대상(visited)으로 표시
                // (바로 0으로 만들지 않는 이유는 여러 궁수가 같은 적을 공격할 수 있기 때문)
                if(minD <= D){
                    visited[minX][minY] = true;
                }
            }

            // 공격 받은 적 제거 및 카운트
            for(int i = 0; i < N; i++){
                for(int j = 0; j < M; j++){
                    if(visited[i][j]) {
                        // 아직 제거되지 않은 적이라면 카운트 증가
                        if(mep[i][j] == 1) {
                            mep[i][j] = 0;
                            res++;
                        }
                    }
                }
            }

            // 적 이동 (아래로 한 칸씩 이동)
            // 아래 행부터 처리해야 덮어쓰기 문제 없음
            for(int i = N-1; i > 0; i--){
                for(int j = 0; j < M; j++){
                    mep[i][j] = mep[i-1][j];
                }
            }
            // 맨 윗줄은 0으로 채움
            Arrays.fill(mep[0], 0);
        }

        // 최대 제거 수 갱신
        ans = Math.max(ans, res);
    }

    // 맨해튼 거리 계산 함수 (|r1-r2| + |c1-c2|)
    private static int getDistance(int r1, int c1, int r2, int c2){
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);   
    }
}