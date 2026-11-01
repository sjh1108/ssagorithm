import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : BFS, 구현, 시뮬레이션
     *
     * 코드 내에서는 편의를 위해 입력값으로 주어지는 펭귄 1, 2 대신 펭귄 0, 1로 설명
     * 얼음과 벽으로만 이루어진 판에 있는 두 펭귄 0, 1이 동시에 A, B에 멈출 때까지 판을 기울여야 함
     *
     * 두 펭귄이 함께 움직이므로 한 펭귄의 위치만으로는 상태를 구분할 수 없음
     * 같은 칸이라도 상대 펭귄의 위치에 따라 이후 전개가 달라지기 때문
     * -> 방문 처리의 단위를 "두 펭귄의 위치 쌍"으로 확장
     *
     * 또한 두 펭귄이 같은 줄에서 같은 방향으로 미끄러지면 뒤쪽 펭귄이 앞쪽에 막혀 멈춤
     * 107번 문제처럼 맵에 펭귄 위치를 기록하며 순차 이동시키는 방식은
     * 상태를 되돌려야 하는 BFS에서 관리가 복잡해짐
     * -> 맵 변경 없이 두 펭귄을 독립적으로 이동시킨 뒤,
     *    같은 칸에 도착한 경우에만 뒤쪽 펭귄을 한 칸 보정하는 방식으로 처리
     */

    static int r, c;
    static boolean[][] map;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        // 배열 차수가 4차까지 올라가는 것을 방지하기 위해 모든 좌표는 (y*c+x) 1차원값으로 관리
        // a, b: 각각 펭귄 0, 1의 목적지
        // p0, p1: 두 펭귄의 초기 위치
        int a = -1, b = -1, p0 = -1, p1 = -1;

        // A, B, 1, 2는 모두 얼음과 동일하게 취급되므로 벽 여부만 boolean으로 기록
        map = new boolean[r][c];
        for(int i=0; i<r; i++) {
            char[] line = br.readLine().toCharArray();
            for(int j=0; j<c; j++) {
                char ch = line[j];
                if(ch == 'A') {
                    a = i * c + j;
                } else if(ch == 'B') {
                    b = i * c + j;
                } else if(ch == '1') {
                    p0 = i * c + j;
                } else if(ch == '2') {
                    p1 = i * c + j;
                }

                // 벽은 false, 얼음은 true
                map[i][j] = ch != '#';
            }
        }

        // 방문 처리 배열 : [펭귄 0의 위치][펭귄 1의 위치]
        // 두 펭귄의 위치 조합 전체가 하나의 상태
        boolean[][] visited = new boolean[r*c][r*c];
        visited[p0][p1] = true;
        Queue<int[]> q = new ArrayDeque<>(); // { 펭귄 0 위치, 펭귄 1 위치, 기울인 횟수 }
        q.add(new int[]{p0, p1, 0});

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int curP0 = cur[0], curP1 = cur[1];

            for(int i=0; i<4; i++) {
                // 일단 두 펭귄의 충돌을 무시한 채 각각 벽까지 미끄러뜨림
                int dest0 = getDest(curP0, i);
                int dest1 = getDest(curP1, i);

                // 도착점이 같다면 실제로는 뒤쪽 펭귄이 앞쪽에 막혀 멈춰야 하는 상황
                if(dest0 == dest1) {
                    // 겹친 칸에서 진행 방향의 반대로 한 칸 되돌린 위치가 뒤쪽 펭귄의 실제 도착점
                    int correctionVal = (dest0/c - dy[i]) * c + (dest0%c - dx[i]);

                    // 진행 방향 벡터에 좌표를 투영하여 누가 앞쪽인지 판별
                    // 투영값이 클수록 진행 방향 기준 앞쪽에 위치
                    int proj0 = (curP0/c) * dy[i] + (curP0%c) * dx[i];
                    int proj1 = (curP1/c) * dy[i] + (curP1%c) * dx[i];

                    // 따라서 투영값이 작은 펭귄의 위치 보정
                    if(proj0 >= proj1) {
                        dest1 = correctionVal;
                    } else {
                        dest0 = correctionVal;
                    }
                }

                // 두 펭귄이 동시에 각자의 목적지에 멈춘 시점이 최소 횟수
                if(dest0 == a && dest1 == b) {
                    System.out.println(cur[2]+1);
                    return;
                }

                if(visited[dest0][dest1]) continue;
                visited[dest0][dest1] = true;
                q.add(new int[]{dest0, dest1, cur[2]+1});
            }
        }

        System.out.println(-1);
    }

    // 한 펭귄을 주어진 방향으로 벽에 막힐 때까지 미끄러뜨린 뒤 도착 좌표 반환
    // 다른 펭귄의 존재는 고려하지 않으며, 겹침 처리는 호출부에서 수행
    static int getDest(int curP, int dir) {
        int y = curP / c, x = curP % c;

        while(map[y+dy[dir]][x+dx[dir]]) {
            y += dy[dir];
            x += dx[dir];
        }

        return y * c + x;
    }

}