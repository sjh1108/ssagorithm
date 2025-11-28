package week11_02.sjh1108.BOJ_12094;

import java.io.*;
import java.util.*;

// 백준 12094 - 2048 (Hard)
// 2048 (Easy) (12100번)과 동일한 로직에, 이동 횟수가 5번 -> 10번으로
// 가지치기(Pruning)가 필수적인 문제입니다.
class Main {
    private static int N; // 보드의 크기 (N x N)

    // 최대 10번 이동시켜서 만들 수 있는 가장 큰 블록의 값 (전역 변수)
    private static int max = 0;

    /**
     * 주어진 맵(map)을 순회하며 최대값을 찾아 'max' 변수를 갱신합니다.
     * @param map 최대값을 찾을 맵
     */
    private static void getMax(int[][] map) {
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                // 현재까지의 최대값(max)과 현재 칸의 값(map[i][j])을 비교하여 더 큰 값으로 갱신
                max = Math.max(max, map[i][j]);
            }
        }
    }

    /**
     * 맵을 시계 방향으로 90도 회전시킨 *새로운* 맵을 반환합니다.
     * @param map 회전시킬 원본 맵
     * @return 90도 회전된 새 맵
     */
    private static int[][] rotate(int[][] map) {
        // 회전된 맵을 저장할 새 2차원 배열
        int[][] rotated = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 회전 공식: 원본 (i, j) -> 새 맵 (j, N-1-i)
                rotated[j][N - 1 - i] = map[i][j];
            }
        }

        // 새로 생성된 회전된 맵 반환
        return rotated;
    }

    /**
     * 맵을 '위쪽'으로 밀어서 블록을 합칩니다. (2048의 'Up' 동작)
     * (이 함수는 맵 자체를 수정합니다.)
     * @param map '위쪽'으로 밀 맵
     */
    private static void pressMap(int[][] map){
        // 각 *열*(i)에 대해 개별적으로 수행
        for(int i = 0; i < N; i++){
            // 현재 i번째 열을 임시로 저장하고 처리할 1차원 배열
            int[] t = new int[N];

            // 임시 배열 t에서, 블록이 채워질 다음 위치를 가리키는 인덱스
            int idx = 0;
            
            // 현재 i번째 *열*의 모든 *행*(j)을 위에서부터(0) 아래로(N-1) 순회
            for(int j = 0; j < N; j++){
                // 1. 현재 칸(j, i)이 비어있으면(0) 무시
                if(map[j][i] == 0) continue;

                // 2. 임시 배열의 'idx' 위치가 비어있다면 (즉, 이 열에서 처음 만나는 숫자이거나, 합쳐진 후 다음 칸이라면)
                if(t[idx] == 0){
                    // 현재 칸의 숫자를 임시 배열 'idx' 위치에 그대로 복사
                    t[idx] = map[j][i];
                }
                // 3. 임시 배열 'idx' 위치의 숫자와 현재 칸의 숫자가 *같다면* (합칠 수 있다면)
                else if(t[idx] == map[j][i]){
                    t[idx] *= 2; // 임시 배열 'idx' 위치의 숫자를 2배로 (합침)
                    idx++; // 다음 숫자는 다음 칸(idx+1)에 채워야 하므로 인덱스 증가
                }
                // 4. 임시 배열 'idx' 위치의 숫자와 현재 칸의 숫자가 *다르다면* (합칠 수 없다면)
                else{
                    idx++; // 현재 칸의 숫자를 저장할 다음 위치로 인덱스 증가
                    t[idx] = map[j][i]; // 임시 배열의 새 위치(idx)에 현재 칸의 숫자 복사
                }
            }

            // 합치기 및 밀기 작업이 완료된 임시 배열(t)의 내용을
            for(int j = 0; j < N; j++){
                // 원래 맵(map)의 i번째 열에 다시 덮어쓰기
                map[j][i] = t[j];
            }
        }
    }

    /**
     * 백트래킹(DFS)으로 모든 이동 경우의 수(최대 10번)를 탐색합니다.
     * @param map 현재 맵 상태
     * @param depth 현재까지 이동한 횟수
     */
    private static void dfs(int[][] map, int depth){
        
        // [기저 조건] 10번 이동을 완료했다면
        if(depth == 10) {
            // 현재 맵 상태에서 최대값을 찾아 'max' 변수 갱신
            getMax(map);
            return; // 탐색 종료
        }
        
        // [가지치기 1] (BOJ 12094의 핵심)
        // 현재 맵(map)의 최대값(curMax)을 계산
        int curMax = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(curMax < map[i][j]) curMax = map[i][j];
            }
        }
        
        // (1 << (10-depth))는 2^(10-depth)와 같음
        // 'curMax'가 남은 이동 횟수(10-depth) 동안 매번 합쳐져서 2배가 된다고 *가정*해도
        // (즉, 만들 수 있는 '이론상' 최대값이)
        // 'max'(지금까지 찾은 전역 최대값)보다 작거나 같다면
        if(curMax * (1 << (10-depth)) <= max){
            return; // 더 이상 탐색해도 'max'를 갱신할 수 없으므로 이 경로는 버림 (탐색 중단)
        }
        
        // [가지치기 2] (이 코드에는 없지만, 이전 맵과 현재 맵이 동일하면 탐색 중단하는 로직도 가능)

        // 4방향(상, 하, 좌, 우) 이동을 시도
        // 이 코드는 'Up' 동작(pressMap)만 구현하고, 맵을 4번 회전시키는 방식으로 4방향을 처리
        for (int i = 0; i < 4; i++) {
            // 1. 맵을 시계방향 90도 회전 (이 'newMap'은 다음 재귀로 넘어갈 *새* 맵)
            //    (원본 'map'은 이 dfs 호출 내에서 참조용으로 사용됨)
            int[][] newMap = rotate(map);

            // 2. 회전된 맵을 '위쪽'으로 민다 (pressMap)
            //    (i=0일 때 M0->M1, press(M1) = 원본 기준 'Left' 이동과 동일)
            pressMap(newMap);
            
            // 3. 이동이 완료된 새 맵(newMap)으로, depth를 1 증가시켜 재귀 호출
            dfs(newMap, depth + 1);

            // 4. 다음 for 루프(i=1)를 위해 *현재* 'map' 변수를 90도 회전시켜 둠.
            //    (i=1일 때 M1->M2, press(M2) = 원본 기준 'Down' 이동)
            //    (i=2일 때 M2->M3, press(M3) = 원본 기준 'Right' 이동)
            //    (i=3일 때 M3->M0, press(M0) = 원본 기준 'Up' 이동)
            map = rotate(map); // 이 'map'은 현재 dfs 함수의 *지역 변수* map임
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        N = Integer.parseInt(br.readLine()); // 맵 크기 N 입력
        
        // [예외 처리] N=1일 때
        if(N == 1){
            // 이동 불가, 입력값이 즉시 답
            System.out.print(Integer.parseInt(br.readLine()));
            return;
        }
        
        int[][] map = new int[N][N]; // 맵 생성
        
        StringTokenizer st;
        // 맵 초기 상태 입력
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                // 초기 최대값도 미리 계산
                max = Math.max(max, map[i][j]); 
            }
        }

        // 깊이 0(이동 0번) 상태로 DFS 시작
        dfs(map, 0);

        // 최종적으로 찾은 최대값 출력
        System.out.println(max);
    }
}