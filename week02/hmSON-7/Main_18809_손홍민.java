package gold_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main_18809 {

    // BOJ_18809 Gaaaaaaaaaarden
    static int r, c, green, red, maxFlower = 0, greenBit = 0, redBit = 0;
    static char[][] map, visitedColor;
    static int[][] visitedTime;
    static int[] dy = {-1, 1, 0, 0}, dx = {0, 0, -1, 1};
    static List<int[]> startList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        init(); seedGreen(0, 0);
        System.out.println(maxFlower);
    }

    public static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        green = Integer.parseInt(st.nextToken());
        red = Integer.parseInt(st.nextToken());
        map = new char[r][c];
        for(int i=0; i<r; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<c; j++) {
                char ch = st.nextToken().charAt(0);
                map[i][j] = ch;
                if(ch == '2') startList.add(new int[]{i, j});
            }
        }
    }

    public static void seedGreen(int start, int cnt) {
        if(cnt == green) {
            seedRed(0, 0);
            return;
        }

        for(int i=start; i<=(startList.size() - green + cnt); i++) {
            greenBit = greenBit | (1<<i);
            seedGreen(i+1, cnt+1);
            greenBit = greenBit & ~(1<<i);
        }
    }

    public static void seedRed(int start, int cnt) {
        if(cnt == red) {
            spread();
            return;
        }

        for(int i=start; i<=(startList.size() - red + cnt); i++) {
            if((greenBit & (1<<i)) != 0) continue;
            redBit = redBit | (1<<i);
            seedRed(i+1, cnt+1);
            redBit = redBit & ~(1<<i);
        }
    }

    public static void spread() {
        Queue<int[]> q = new ArrayDeque<>();
        visitedColor = new char[r][c];
        visitedTime = new int[r][c];
        for(int i=0; i<r; i++) {
            Arrays.fill(visitedColor[i], 'b');
            Arrays.fill(visitedTime[i], -1);
        }
        for(int i=0; i<startList.size(); i++) {
            if((greenBit & (1<<i)) != 0) {
                int[] seed = startList.get(i);
                q.add(seed);
                visitedTime[seed[0]][seed[1]] = 0;
                visitedColor[seed[0]][seed[1]] = 'g';
            }
        }
        for(int i=0; i<startList.size(); i++) {
            if((redBit & (1<<i)) != 0) {
                int[] seed = startList.get(i);
                q.add(seed);
                visitedTime[seed[0]][seed[1]] = 0;
                visitedColor[seed[0]][seed[1]] = 'r';
            }
        }
        int flowerCnt = 0;
        while(!q.isEmpty()) {
            int[] seed = q.poll();
            int y = seed[0], x = seed[1];
            if(visitedColor[y][x] == 'f') continue;
            for(int i=0; i<4; i++) {
                int ny = y+dy[i], nx = x+dx[i];
                // 배열 범위 밖이거나 호수, 꽃 -> 무시
                if(!isIn(ny, nx) || visitedColor[ny][nx] == 'f') continue;
                // 빈 공간 -> 씨앗 심고 색 및 시간 정보 등록
                if(visitedColor[ny][nx] == 'b') {
                    q.add(new int[]{ny, nx});
                    visitedColor[ny][nx] = visitedColor[y][x];
                    visitedTime[ny][nx] = visitedTime[y][x]+1;
                    continue;
                }
                // 두 씨앗의 색은 다르고, 같은 시간대에 심어진 씨앗이라면
                if(visitedColor[ny][nx] != visitedColor[y][x] && visitedTime[ny][nx] == visitedTime[y][x]+1) {
                    visitedColor[ny][nx] = 'f';
                    flowerCnt++;
                }
            }
        }

        if(flowerCnt > maxFlower) maxFlower = flowerCnt;
    }

    public static boolean isIn(int y, int x) {
        return y >= 0 && x >= 0 && y < r && x < c && map[y][x] != '0';
    }

}
