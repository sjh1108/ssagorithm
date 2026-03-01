package _20260303.sjh1108.BOJ_10993;

import java.util.*;

class Main {
    private static char[][] map = new char[1023][2045];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.close();

        int height = (1 << n) - 1;
        int width = 2 * height - 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = ' ';
            }
        }

        boolean dir = false;
        if (n % 2 == 0) dir = true;
        
        int x = 0;
        if (dir) x = (1 << n) - 2;
        int y = (1 << n) - 2;
        
        drawStar(n, x, y, dir);
        
        StringBuilder sb = new StringBuilder();
        
        if (dir) {
            int currentWidth = width;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < currentWidth; j++) {
                    sb.append(map[i][j]);
                }
                currentWidth--;
                sb.append("\n");
            }
        } else {
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
        map[x][y] = '*';
        if (n == 1) return;

        int height = (1 << n) - 1;
        int left = y, right = y;
        for (int i = 1; i < height; i++) {
            if (dir) x--;
            else x++;

            left--; right++;
            map[x][left] = '*';
            map[x][right] = '*';
        }

        for (int j = left + 1; j < right; j++) {
            map[x][j] = '*';
        }

        if (dir) x++;
        else x--;

        drawStar(n - 1, x, y, !dir);
    }
}