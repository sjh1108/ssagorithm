package feature05_01;

// [프로그래머스] 삼각 달팽이 Lv.2
class Solution {
    public int[] solution(int n) {
        int[][] tri = new int[n][n];
        int[] dr = {1, 0, -1};
        int[] dc = {0, 1, -1};
        int dir = 0;
        int r = 0;
        int c = 0;
        int total = n * (n + 1) / 2;

        for (int num = 1; num <= total; num++) {
            tri[r][c] = num;
            int nr = r + dr[dir];
            int nc = c + dc[dir];

            // 경계값 || n보다 크면 안되고, 인덱싱 차이 || 오른쪽으로 갈 때, nr을 nc가 넘기면 삼각형 벗어남 || tri 배열 0이 갱신되지 않아야 함
            if(nr < 0 || nc < 0 || n <= nr || nr < nc || tri[nr][nc] != 0) {
                dir = (dir + 1) % 3;
                nr = r + dr[dir];
                nc = c + dc[dir];
            }
            // if문 조건이 되는 경우 좌표를 새로 세팅해줘야 함.
            r = nr;
            c = nc;
        }

        int[] answer = new int[total];
        int index = 0;
        for (int i = 0; i < n; i++) { // 삼각형 순차 출력을 위한 for문 =  한 행씩 출력.
            for (int j = 0; j <= i; j++) {
                answer[index++] = tri[i][j];
            }
        }
        return answer;
    }
}

// [풀이전략]
// 삼각형을 그림에서 보는 식(이등변삼각형)으로 인지하기 보다 직각삼각형을 만들자. -> 컴퓨팅적 사고
// 블록이 세팅되는 방향은 아래 - 완성 - 오른쪽- 완성 - 왼쪽위 순서로 달팽이로 계속 돌아가서 새로운 배열 리턴
// 블록의 갯수는 n까지의 총합 = 등차수열의 합 공식 활용
// n(1부터 시작)과 nr(0부터 시작)은 인덱싱 범위의 차이가 존재
// 배열 밖, 삼각형 밖, tri 배열 초기값이 0이 아닌 num으로 지정된 경우 -> 방향 변경.

// [구현 순서]
// 기본값 세팅, tri배열의 초기값 0으로 지정
// 경계값 설정
// nrnc이용 만들고, 출력


// [실수]

// [코드 최적화]

// [이 문제에서 파생될 수 있는 문제 유형]
// 8방 탐색, 이등변 삼각형, 좌표 돌아감에 따라 위치변환