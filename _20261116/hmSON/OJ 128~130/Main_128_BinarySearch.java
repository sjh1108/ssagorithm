import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : LIS(최장 증가 부분 수열), 정렬, 이분 탐색
     *
     * 너비와 높이가 모두 엄격히 증가하는 인형의 최대 개수 -> 2차원 LIS
     * 고르는 순서가 자유로우므로 너비 기준으로 정렬하면 너비 증가는 순서로 보장되고, 높이만 남음
     *
     * 너비가 같은 인형은 서로 포갤 수 없으므로 같은 수열에 들어가면 안 됨
     * -> 너비가 같으면 높이 내림차순으로 정렬
     * -> 같은 너비끼리는 높이가 감소하므로 높이만 보는 증가 수열로 이어질 수 없음
     *
     * lisHeight[len] : 길이 len+1인 포개기 중 가장 바깥 인형의 높이 최솟값
     * 끝값이 작을수록 이후 더 많은 인형을 이어 붙일 수 있으므로 이분 탐색으로 덮어쓰며 갱신
     *
     * 시간 복잡도 : O(N log N)
     */

    static int[] lisHeight;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] dolls = new int[n][2]; // { 너비, 높이 }
        StringTokenizer st;
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            dolls[i][0] = Integer.parseInt(st.nextToken());
            dolls[i][1] = Integer.parseInt(st.nextToken());
        }

        // 너비 오름차순, 너비가 같으면 높이 내림차순
        Arrays.sort(dolls, (a, b) ->
                a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(b[1], a[1])
        );

        lisHeight = new int[n];
        int len = 0; // 현재까지 만든 가장 많이 포갠 인형 개수

        for(int[] doll : dolls) {
            // 모든 값보다 크면 len이 반환되어 맨 뒤에 붙고, 아니면 해당 자리를 더 작은 높이로 덮어씀
            int idx = binarySearch(0, len, doll[1]);
            lisHeight[idx] = doll[1];

            if(idx == len) len++;
        }

        System.out.println(len);
    }

    // [lo, hi) 범위에서 높이 h 이상인 첫 위치 반환 (lower bound)
    // 순증가이므로 같은 높이도 이어 붙일 수 없는 위치로 취급
    static int binarySearch(int lo, int hi, int h) {
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(h > lisHeight[mid]) lo = mid + 1; // mid 뒤에 포갤 수 있음 -> 오른쪽으로
            else hi = mid;                       // mid가 답일 수 있음 -> mid 포함해 왼쪽으로
        }

        return lo;
    }

}