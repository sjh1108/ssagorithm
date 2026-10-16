import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 브루트 포스, 재귀, 백트래킹
     *
     * k*k(1 <= k <= 5) 크기의 정사각 타일의 개수와 개당 비용이 각각 주어지고 h*w 크기의 평면 내 '#'칸을 타일로 전부 채워야 함
     * 모든 '#'칸을 덮을 수 있다면 비용의 최소값을 구해야 함
     * 타일 수 고갈로 모든 '#'칸을 채우지 못하는 경우가 주어질 수 있음
     *
     * 정사각형 타일을 설치할 조건이 모두 충족되면 해당 칸을 전부 채우고 다음 재귀를 호출하는 형태로 구성
     * 또, 남은 빈 칸이 더 이상 없다면 그 때의 총 타일 비용과 최소값을 비교
     */

    static int h, w, n, min = Integer.MAX_VALUE;
    static boolean[][] map; // '#' 또는 '.'만 주어지므로 boolean 타입 배열로 선언해도 됨
    static int[] tileCnt, tileCost; // 각 크기별 타일의 개수와 비용

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        n = Math.min(5, Math.min(h, w)); // 맵 크기에 따라 큰 타일이 필요 없을 수 있음

        tileCnt = new int[n+1];
        tileCost = new int[n+1];

        st = new StringTokenizer(br.readLine());
        for(int i=1; i<=n; i++) {
            tileCnt[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for(int i=1; i<=n; i++) {
            tileCost[i] = Integer.parseInt(st.nextToken());
        }


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

        backtrack(blankCnt, 0);

        // 검증 이후에도 min이 초기값 그대로라면 모든 '#'칸을 채운 경우가 없으므로 -1 반환
        System.out.println(min == Integer.MAX_VALUE ? -1 : min);
    }

    // 백트래킹 메서드
    // 아직 채우지 않은 남은 칸의 수를 매개변수로 주어 이것이 0이 되면 총 비용과 최소값 비교 가능
    static void backtrack(int blankCnt, int totalCost) {
        // 더 이상 남은 칸이 없으면 최소값 갱신하고 복귀
        if(blankCnt == 0) {
            if(totalCost < min) min = totalCost;
            return;
        }

        for(int i=0; i<h; i++) {
            for(int j=0; j<w; j++) {
                // 현재 칸이 방문 가능한 지 확인
                if(map[i][j]) continue;

                // 현재 칸이 좌측 상단 모서리인 정사각형 타일 설치가 가능한 지 확인
                // 가능하면 타일을 설치하고 재귀 호출
                for(int k=1; k<=n; k++) {
                    // 현재 크기의 타일이 없음 -> 다른 크기 타일 확인
                    if(tileCnt[k] == 0) continue;
                    // 현재 크기 타일 설치 불가 -> 더 큰 타일들은 볼 필요도 없음
                    if(!checkSquare(i, j, k)) break;

                    tileCnt[k]--; // 타일 사용
                    reverse(i, j, k); // 타일 칸 전부 뒤집기

                    backtrack(blankCnt - k*k, totalCost + tileCost[k]);

                    tileCnt[k]++; // 사용한 타일 반납
                    reverse(i, j, k); // 타일 제거 상태로 뒤집기
                }

                // 여기서 루프를 탈출하지 않으면 이미 확인한 다른 경우를 다시 보게 됨
                // 타일 설치 순서가 다르다고 다른 조합으로 취급하지 않기 때문에 즉시 2차 반복문을 탈출하고 복귀
                return;
            }
        }
    }

    // 평면 뒤집기 메서드
    // 지정된 크기의 정사각형 타일 설치/제거 수행
    static void reverse(int y, int x, int len) {
        for(int i=y; i<y+len; i++) {
            for(int j=x; j<x+len; j++) {
                map[i][j] = !map[i][j];
            }
        }
    }

    // 타일 설치 가능 여부 검증 메서드
    // 지정된 칸 {y, x}가 좌측 상단 모서리이고 길이가 len인 정사각형 구간이 전부 빈 칸인지 확인
    static boolean checkSquare(int y, int x, int len) {
        // 해당 범위가 평면을 벗어나면 즉시 false 반환
        if(y+len-1 >= h || x+len-1 >= w) return false;

        for(int i=y; i<y+len; i++) {
            for(int j=x; j<x+len; j++) {
                if(map[i][j]) return false;
            }
        }

        return true;
    }

}