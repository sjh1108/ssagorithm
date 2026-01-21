package week01_04.sjh1108.BOJ_2873;

import java.io.*;
import java.util.*;

// 백준 2873 - 롤러코스터 (Greedy, 구현)
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int R = Integer.parseInt(st.nextToken()); // 행
        int C = Integer.parseInt(st.nextToken()); // 열

        StringBuilder sb = new StringBuilder();
        
        // Case 3: 행(R)과 열(C)이 모두 짝수인 경우
        // 모든 칸을 방문할 수 없음. (R*C가 짝수이므로 체스판의 검은칸/흰칸 개수가 다름 등 증명 가능)
        // (검은칸에서 시작해 흰칸으로 끝나야 하는데, 검은칸 개수 != 흰칸 개수인 경우 발생)
        // 결론적으로, 검은칸(i+j가 홀수인 칸) 중 하나를 제외해야 함.
        // 가장 기쁨(점수)이 작은 검은칸을 제외하고 나머지 모든 칸을 방문하는 경로를 생성해야 함.
        if(R % 2 == 0 && C % 2 == 0){
            int minX = -1; // 제외할 칸의 행
            int minY = -1; // 제외할 칸의 열
            int min = Integer.MAX_VALUE; // 최소 기쁨 값

            // 입력 받으면서 제외할 칸(minX, minY) 찾기
            for(int i = 0; i < R; i++){
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < C; j++) {
                    int value = Integer.parseInt(st.nextToken());
                    // (i+j)가 홀수인 칸(체스판의 검은 칸 위치)만 건너뛰는 것이 가능
                    // (시작점(0,0)은 짝수, 도착점(R-1,C-1)은 (홀수,홀수) -> 합 짝수.
                    //  전체 칸 수는 짝수. 모든 칸 방문 불가.)
                    if((i+j) % 2 == 1){
                        if(min > value){
                            min = value;
                            minX = i;
                            minY = j;
                        }
                    }
                }
            }

            // 제외할 칸을 피해서 경로 생성
            sb.append(solve(C, R, minY, minX));
        }
        else{
            // Case 1: 행(R)이 홀수인 경우
            // 'ㄹ'자 모양으로 모든 칸 방문 가능
            // (R, L, D) 패턴 반복
            if(R%2 == 1) { 
                sb.append(mapSizeIsNotOdds('R','L','D', C, R));
            }
            // Case 2: 열(C)이 홀수인 경우 (행은 짝수)
            // 세로 방향 'ㄹ'자 모양으로 모든 칸 방문 가능
            // (D, U, R) 패턴 반복
            else {
                sb.append(mapSizeIsNotOdds('D','U','R', R, C));
            }
        }

        // 마지막에 추가된 불필요한 이동 문자 하나 제거 후 출력
        // (mapSizeIsNotOdds 함수 등에서 마지막에 방향 전환 문자를 추가하므로)
        if (sb.length() > 0)
            System.out.println(sb.deleteCharAt(sb.length()-1));
        else
            System.out.println("");
    }

    /**
     * R, C 모두 짝수일 때, (ey, ex) 칸을 제외하고 경로를 생성하는 함수
     * @param x 전체 가로 길이 (C)
     * @param y 전체 세로 길이 (R)
     * @param ex 제외할 칸의 x좌표 (열)
     * @param ey 제외할 칸의 y좌표 (행)
     */
    private static StringBuilder solve(int x, int y, int ex, int ey){
        StringBuilder sb = new StringBuilder();

        // 1. 제외할 칸이 있는 2개의 행 블록 직전까지는 'ㄹ'자로 꽉 채워 방문
        // 2줄씩 묶어서 처리 (uy: 제외할 칸이 포함된 2줄 블록의 시작 행)
        int uy = ey / 2 * 2;
        sb.append(mapSizeIsNotOdds('R', 'L', 'D', x, uy));
        
        // 2. 제외할 칸이 포함된 2줄 블록 처리 (핵심)
        // 왼쪽에서부터 제외할 칸(ex) 직전까지는 (아래->위) 또는 (위->아래) 반복하며 오른쪽으로 이동
        for(int i = 0; i < ex; i++) {
            if(i%2 == 0) {
                sb.append("DR"); // 아래로 갔다가 오른쪽
            }else {
                sb.append("UR"); // 위로 갔다가 오른쪽
            }
        }
        
        // 제외할 칸(ex)을 건너뛰고, 그 뒤부터 끝까지 처리
        // (ex) 위치에서는 오른쪽으로만 이동해야 함 (위/아래 이동 X)
        // 제외할 칸 다음 열부터 끝까지 이동
        for(int i = ex; i < x-1; i++) {
            if(i%2 == 0) {
                sb.append("RD"); // 오른쪽 갔다가 아래
            }else {
                sb.append("RU"); // 오른쪽 갔다가 위
            }
        }
        
        // 3. 남은 행들(제외할 칸이 있는 2줄 블록 이후)은 다시 'ㄹ'자로 꽉 채워 방문
        // 현재 위치에서 아래로 한 칸 이동('D') 후 나머지 처리
        if (y - uy - 2 > 0) {
            sb.append("D");
            sb.append(mapSizeIsNotOdds('L', 'R', 'D', x, y-uy-2));
        }

        return sb;
    }

    /**
     * 행이나 열 중 하나가 홀수일 때, 전체를 'ㄹ'자로 방문하는 함수
     * @param f 정방향 이동 문자 (예: 'R')
     * @param s 역방향 이동 문자 (예: 'L')
     * @param b 줄바꿈 이동 문자 (예: 'D')
     * @param x 한 줄의 길이
     * @param y 줄의 개수
     */
    private static StringBuilder mapSizeIsNotOdds(char f, char s, char b, int x, int y){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < y; i++){
            for(int j = 0; j < x-1; j++){
                if(i%2 == 0)
                    sb.append(f); // 짝수 행: 정방향
                else
                    sb.append(s); // 홀수 행: 역방향
            }
            sb.append(b); // 줄 바꿈
        }

        return sb;
    }
}