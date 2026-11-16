import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : LIS(최장 증가 부분 수열), 이분 탐색
     *
     * 각 인형을 마지막으로 고르는 순증가 부분 수열의 최대 길이를 구해야 함
     * 원래 순서를 유지해야 하므로 정렬 불가
     * N이 최대 200,000이라 O(N^2) DP 방식은 약 200억 회로 불가능 -> 이분 탐색 방식 O(N log N)
     *
     * lis[len] : 길이 len+1인 순증가 수열 중 끝값의 최솟값
     * 인형을 순서대로 보며 lower bound 위치에 덮어쓰는데,
     * 그 위치(0-based)가 곧 "이 인형 앞에 이어 붙일 수 있는 최대 길이"이므로 idx+1이 f_i
     */

    static int[] lis;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int len = 0; // 현재까지 만든 가장 긴 수열의 길이
        lis = new int[n];
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i=0; i<n; i++) {
            int h = Integer.parseInt(st.nextToken());
            int idx = binarySearch(0, len, h);

            sb.append(idx+1).append(" "); // 이 인형으로 끝나는 최장 길이
            lis[idx] = h;                 // 같은 길이의 끝값을 더 작게 갱신하거나 맨 뒤에 추가
            if(idx == len) len++;
        }

        System.out.println(sb);
    }

    // [lo, hi) 범위에서 val 이상인 첫 위치 반환 (lower bound)
    // 순증가이므로 같은 키도 이어 붙일 수 없는 위치로 취급
    static int binarySearch(int lo, int hi, int val) {
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(val > lis[mid]) lo = mid + 1;
            else hi = mid;
        }

        return lo;
    }

}