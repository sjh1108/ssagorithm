import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 브루트 포스, 재귀, 백트래킹
     *
     * 1X2 타일만 사용하여 2차원 평면 상의 '#'칸을 전부 채울 수 있는지, 그러한 경우의 수는 몇인지 검증해야 함
     * 평면의 최대 크기는 10X10이지만, '#'칸의 수는 1~40임을 보장
     * 평면을 좌측 상단부터 순회하면서 아직 채우지 않은 칸이 있는지 확인하고, 있다면 오른쪽과 아래 2방향에도 빈 칸이 있는지 각각 확인
     * 타일 설치가 가능하면 설치 후 재귀, 모든 빈 칸이 채워지면 경우의 수 카운트
     *
     * 예외 : '#'칸의 수가 짝수임을 보장하지 않으므로, '#'칸의 수가 홀수라면 즉시 0 반환
     */

    static int h, w, total = 0;
    static boolean[][] map; // '#' 또는 '.'만 주어지므로 boolean 타입 배열로 선언해도 됨
    static int[] dy = {0, 1}, dx = {1, 0};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        int blankCnt = 0; // '#'칸의 개수
        map = new boolean[h][w];
        for(int i=0; i<h; i++) {
            String line = br.readLine();
            for(int j=0; j<w; j++) {
                char ch = line.charAt(j);
                if(ch == '#') blankCnt++;

                map[i][j] = ch == '.';
            }
        }

        // 예외 처리 : '#'칸의 수가 홀수인 경우
        // 어떤 방식으로도 1X2 타일로 모든 칸을 채울 수 없으므로 0 출력 후 종료
        if(blankCnt % 2 != 0) {
            System.out.println("0");
            return;
        }

        backtrack(blankCnt);

        System.out.println(total);
    }

    // 백트래킹 메서드
    // 아직 채우지 않은 남은 칸의 수를 매개변수로 주어 이것이 0이 되면 경우의 수 카운트를 하도록 했음
    static void backtrack(int blank) {
        // 더 이상 남은 칸이 없으면 경우의 수 카운트하고 복귀
        if(blank == 0) {
            total++;
            return;
        }

        // 좌측 상단부터 평면 순회
        for(int i=0; i<h; i++) {
            for(int j=0; j<w; j++) {
                // 현재 칸이 방문 가능한 지 확인
                if(map[i][j]) continue;

                map[i][j] = true;
                // 현재 칸 기준 오른쪽과 아래쪽도 방문 가능한 지 확인
                // 가능하면 타일을 설치하고 재귀 호출
                for(int k=0; k<2; k++) {
                    int y = i + dy[k];
                    int x = j + dx[k];
                    if(y >= h || x >= w || map[y][x]) continue;

                    map[y][x] = true;
                    backtrack(blank - 2);
                    map[y][x] = false;
                }

                map[i][j] = false;

                // 여기서 루프를 탈출하지 않으면 이미 확인한 다른 경우를 다시 보게 됨
                // 타일 설치 순서가 다르다고 다른 조합으로 취급하지 않기 때문에 즉시 2차 반복문을 탈출하고 복귀
                return;
            }
        }
    }

}