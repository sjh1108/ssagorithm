import java.util.*;

class Solution {

    static final int LEN = 5;

    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    char[][] map = new char[LEN][LEN];

    public int[] solution(String[][] places) {
        int[] res = new int[LEN];

        for(int i=0; i<LEN; i++) {
            for(int j=0; j<LEN; j++) {
                for(int k=0; k<LEN; k++) {
                    map[j][k] = places[i][j].charAt(k);
                }
            }

            boolean flag = true;
            for(int j=0; j<LEN; j++) {
                for(int k=0; k<LEN; k++) {
                    if(map[j][k] != 'P') continue;
                    flag = check(j, k);

                    // 한 번이라도 맨해튼 거리 2 이하가 발견되면 규칙을 준수하지 않은 것
                    if(!flag) break;
                }
                if(!flag) break;
            }

            res[i] = flag ? 1 : 0;
        }

        return res;
    }

    boolean check(int sy, int sx) {
        // 'P' - 사람이 앉아 있는 위치
        // 'p' - 앉아있는 사람으로부터 맨해튼 거리 1에 해당하는 위치
        // 4방 탐색만 거쳐서 사람끼리 붙어있거나, 'p'가 겹치는 경우 맨해튼 거리 2로 판단
        for(int i=0; i<4; i++) {
            int y = sy + dy[i], x = sx + dx[i];
            if(y < 0 || x < 0 || y >= LEN || x >= LEN || map[y][x] == 'X') continue;
            if(map[y][x] == 'P' || map[y][x] == 'p') return false;
            map[y][x] = 'p';
        }

        return true;
    }

}