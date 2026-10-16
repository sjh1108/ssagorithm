import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 브루트 포스, 재귀, 백트래킹
     *
     * 길이가 1~5인 타일들을 이용해 길이 L을 정확하게 채워야 함
     * 각 타일의 길이가 주어지는 게 아닌, 길이별 타일 수가 주어지는 형태이므로 DP보다는 재귀를 유도하는 형태에 가까움
     * 복도의 최대 길이가 20, 타일 종류가 5개로 제한되므로 재귀 + 백트래킹으로 충분히 해결할 수 있음
     *
     * {1, 3}, {3, 1}처럼 타일의 배치 순서가 다르면 다른 경우로 인정함
     */

    static int n, total = 0;
    static int[] tiles;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        tiles = new int[6];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=1; i<=5; i++) {
            tiles[i] = Integer.parseInt(st.nextToken());
        }

        backtrack(0);

        System.out.println(total);
    }

    static void backtrack(int len) {
        // 현재까지 선택한 타일의 총 길이가 len 이상이면
        if(len >= n) {
            // 길이가 정확히 len인지 확인. 일치하면 경우의 수 카운트
            if(len == n) total++;
            // 별개로 더 타일을 추가할 필요는 없으므로 복귀
            return;
        }

        for(int i=1; i<=5; i++) {
            if(tiles[i] == 0) continue; // 해당 길이의 타일이 없음
            tiles[i]--; // 타일 사용
            backtrack(len + i);
            tiles[i]++; // 사용한 타일 반납
        }
    }

}