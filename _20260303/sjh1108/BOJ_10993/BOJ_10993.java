package _20260303.sjh1108.BOJ_10993;

import java.util.*;

class Main {
    // 최대 크기(2^10-1, 2*(2^10-1)-1)에 맞춰 미리 할당
    private static char[][] map = new char[1023][2045];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.close();

        // n단 별의 전체 높이/너비
        int height = (1 << n) - 1;
        int width = 2 * height - 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = ' ';
            }
        }

        // dir == true: 위로 그리는 삼각형, false: 아래로 그리는 삼각형
        boolean dir = false;
        if (n % 2 == 0) dir = true;
        
        // 시작 좌표(꼭짓점)
        int x = 0;
        if (dir) x = (1 << n) - 2;
        int y = (1 << n) - 2;
        
        drawStar(n, x, y, dir);
        
        StringBuilder sb = new StringBuilder();
        
        if (dir) {
            // 위로 그릴 때는 줄마다 출력 너비가 1씩 감소
            int currentWidth = width;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < currentWidth; j++) {
                    sb.append(map[i][j]);
                }
                currentWidth--;
                sb.append("\n");
            }
        } else {
            // 아래로 그릴 때는 줄마다 출력 너비가 1씩 증가
            int currentY = y;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j <= currentY; j++) {
                    sb.append(map[i][j]);
                }
                currentY++;
                sb.append("\n");
            }
        }

        System.out.print(sb);
    }

    private static void drawStar(int n, int x, int y, boolean dir) {
        // 현재 단계의 꼭짓점 표시
        map[x][y] = '*';
        if (n == 1) return;

        int height = (1 << n) - 1;
        int left = y, right = y;
        for (int i = 1; i < height; i++) {
            // 방향에 따라 위/아래로 이동하며 양 옆 변을 찍는다
            if (dir) x--;
            else x++;

            left--; right++;
            map[x][left] = '*';
            map[x][right] = '*';
        }

        // 밑변 채우기
        for (int j = left + 1; j < right; j++) {
            map[x][j] = '*';
        }

        // 다음 단계(안쪽 삼각형)로 재귀
        if (dir) x++;
        else x--;

        drawStar(n - 1, x, y, !dir);
    }
}
