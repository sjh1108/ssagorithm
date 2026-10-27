import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 구현, 시뮬레이션
     *
     * 방향에 따라 두 펭귄을 이동시키는 단순 시뮬레이션 문제
     * 먼저 처리하는 펭귄의 이동 경로에 다른 펭귄이 있는 경우를 어떻게 처리할 것인지가 주요 요점
     * 두 펭귄의 좌표를 알고 있다면, 판을 기울인 방향에서 가까운 펭귄부터 이동시킬 수 있음
     */

    static int[] idxOf = new int[26]; // URDL 입력을 방향 인덱스(0~3)로 변환
    static char[][] map;
    static int r, c;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1}, py = new int[2], px = new int[2];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        String line;
        map = new char[r][c];
        for(int i=0; i<r; i++) {
            line = br.readLine();
            for(int j=0; j<c; j++) {
                char ch = line.charAt(j);
                if(ch == '1') {
                    py[0] = i; px[0] = j;
                } else if(ch == '2') {
                    py[1] = i; px[1] = j;
                }

                map[i][j] = ch;
            }
        }

        // U(상) -> 0, R(우) -> 1, D(하) -> 2, L(좌) -> 3
        idxOf['R'-'A'] = 1;
        idxOf['D'-'A'] = 2;
        idxOf['L'-'A'] = 3;

        line = br.readLine();
        for(int i=0; i<t; i++) {
            int dir = idxOf[line.charAt(i) - 'A'];

            // 두 펭귄 좌표를 방향 벡터에 투영시키면 어느 펭귄을 먼저 이동시켜야 하는지 조건식 없이 판단할 수 있음
            // 한 쪽은 1 또는 -1이 곱해지고, 관련 없는 방향은 0이 곱해지면서 판이 기울어진 방향에 더 가까운 펭귄이 큰 값을 가짐
            int proj0 = py[0] * dy[dir] + px[0] * dx[dir];
            int proj1 = py[1] * dy[dir] + px[1] * dx[dir];
            if(proj0 >= proj1) {
                move(0, dir);
                move(1, dir);
            } else {
                move(1, dir);
                move(0, dir);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(py[0]+1).append(" ").append(px[0]+1).append("\n").append(py[1]+1).append(" ").append(px[1]+1);
        System.out.println(sb);
    }

    // 펭귄 이동 메서드
    static void move(int idx, int dir) {
        int y = py[idx], x = px[idx];

        // 벽에 닿기 전까지 이동(다른 펭귄도 벽으로 취급)
        while(map[y + dy[dir]][x + dx[dir]] == '.') {
            y += dy[dir];
            x += dx[dir];
        }

        // 이동 없음
        if(y == py[idx] && x == px[idx]) return;

        // 맵 정보 최신화 및 펭귄 위치 변경
        map[y][x] = map[py[idx]][px[idx]];
        map[py[idx]][px[idx]] = '.';
        py[idx] = y;
        px[idx] = x;
    }

}