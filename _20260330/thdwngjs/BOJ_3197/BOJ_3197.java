package _20260330.thdwngjs.BOJ_3197;

import java.io.*;
import java.util.*;

class Main {
    private static int R, C;
    private static int[] L;

    private static char[][] map;
    private static boolean[][] visited;

    private static Queue<int[]> water, border;

    private static int[] dx = {0, -1, 0, 1};
    private static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        L = new int[4];
        map = new char[R][];

        water = new ArrayDeque<>();
        border = new ArrayDeque<>();

        int tmp = 0;
        for(int i = 0; i < R; i++){
            map[i] = br.readLine().toCharArray();

            for(int j = 0; j < C; j++){
                if(map[i][j] == 'L'){
                    L[tmp++] = i;
                    L[tmp++] = j;

                    map[i][j] = '.';
                }

                if(map[i][j] == '.'){
                    water.add(new int[]{i, j});
                }
            }
        }

        visited = new boolean[R][C];
        border.add(new int[]{L[0], L[1]});
        visited[L[0]][L[1]] = true;

        int day = 0;
        while(true){
            if(checkMovable()) break;
            melt();
            day++;
        }

        System.out.println(day);
    }

    private static boolean checkMovable(){
        int endX = L[2], endY = L[3];

        Queue<int[]> q = new ArrayDeque<>();

        while(!border.isEmpty()){
            int[] cur = border.poll();

            int x = cur[0], y = cur[1];
            if(x == endX && y == endY) return true;

            for(int d = 0; d < 4; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];

                if(!isIn(nx, ny) || visited[nx][ny]) continue;
                visited[nx][ny] = true;

                if(map[nx][ny] == '.'){
                    border.add(new int[]{nx, ny});
                } else if(map[nx][ny] == 'X'){
                    q.add(new int[]{nx, ny});
                }
            }
        }

        border = q;
        return false;
    }

    private static void melt(){
        int size = water.size();

        while(size-- > 0){
            int[] cur = water.poll();
            int x = cur[0], y = cur[1];

            for(int d = 0; d < 4; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];

                if(!isIn(nx, ny)) continue;

                if(map[nx][ny] == 'X'){
                    map[nx][ny] = '.';
                    water.add(new int[]{nx, ny});
                }
            }
        }
    }

    private static boolean isIn(int x, int y){
        return x >= 0 && x < R && y >= 0 && y < C;
    }
}
