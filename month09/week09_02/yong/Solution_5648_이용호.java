package swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Solution_5648_이용호 {
    static int[] dx = {0, 0, -1, 1}; // 상, 하, 좌, 우 (좌표계)
    static int[] dy = {1, -1, 0, 0};
    static atom[] atomArr;

    static class atom {
        int x, y, dir, k;
        boolean isExist;

        public atom(int x, int y, int dir, int k, boolean isExist) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.k = k;
            this.isExist = isExist;
        }

        public void move() {
            int nx = this.x + dx[dir];
            int ny = this.y + dy[dir];
            if (nx < 0 || ny < 0 || nx >= 4001 || ny >= 4001) {
                this.isExist = false;
            } else {
                this.x = nx;
                this.y = ny;
            }
        }
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
            atomArr = new atom[N];

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = (Integer.parseInt(st.nextToken()) + 1000) * 2;
                int y = (Integer.parseInt(st.nextToken()) + 1000) * 2;
                int dir = Integer.parseInt(st.nextToken());
                int k = Integer.parseInt(st.nextToken());
                atomArr[i] = new atom(x, y, dir, k, true);
            }

            int ksum = 0;
            while (!allStop(atomArr)) {
                Map<String, Integer> atomMap = new HashMap<>();
                Map<String, Integer> atomCnt = new HashMap<>();

                for (atom a : atomArr) {
                    if (!a.isExist) continue;
                    a.move();
                    if (!a.isExist) continue;

                    String key = a.x + "," + a.y;
                    atomMap.put(key, atomMap.getOrDefault(key, 0) + a.k);
                    atomCnt.put(key, atomCnt.getOrDefault(key, 0) + 1);
                }

                for (String key : atomCnt.keySet()) {
                    if (atomCnt.get(key) > 1) {
                        ksum += atomMap.get(key);
                        for (atom a : atomArr) {
                            if (a.isExist && (a.x + "," + a.y).equals(key)) {
                                a.isExist = false;
                            }
                        }
                    }
                }
            }
            System.out.println("#" + tc + " " + ksum);
        }
    }

    static boolean allStop(atom[] list) {
        for (atom a : list) {
            if (a.isExist) return false;
        }
        return true;
    }
}
