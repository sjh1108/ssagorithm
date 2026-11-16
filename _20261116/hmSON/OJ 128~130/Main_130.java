import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : LIS(최장 증가 부분 수열), 정렬, 이분 탐색
     *
     * 가장 깊은 포개기의 깊이 L과, 깊이 L인 포개기 중 하나 이상에 들어가는 인형의 개수와 번호 모두 구해야 함
     *
     * 너비 오름차순(같으면 높이 내림차순)으로 정렬하면 높이만으로 LIS를 구할 수 있음
     * 각 인형마다 두 값을 구함
     * - asc  : 이 인형을 가장 바깥에 둘 때, 안쪽으로 포갤 수 있는 최대 개수
     * - desc : 이 인형을 가장 안쪽에 둘 때, 바깥쪽으로 포갤 수 있는 최대 개수
     * -> 두 값을 이은 길이가 L이면 이 인형은 깊이 L인 포개기에 들어감
     *
     * desc는 같은 정렬 배열을 뒤에서부터 훑으며 높이가 감소하는 LIS를 구하면 됨
     * 높이에 -1을 곱해 넣으면 "높이 감소"가 "증가"로 바뀌어 같은 이분 탐색을 그대로 사용 가능
     * 뒤에서 훑으면 같은 너비끼리 높이가 증가하는 순서가 되어, 서로 이어지지 않는 구조도 유지됨
     */

    static int[] lis;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 정렬 후에도 인형의 고유 번호를 결과로 기록해야 하므로 번호를 함께 저장
        int[][] dolls = new int[n][3]; // { 번호, 너비, 높이 }
        StringTokenizer st;
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            dolls[i][0] = i;
            dolls[i][1] = Integer.parseInt(st.nextToken());
            dolls[i][2] = Integer.parseInt(st.nextToken());
        }

        // 너비 오름차순, 너비가 같으면 높이 내림차순
        Arrays.sort(dolls, (a, b) ->
                a[1] != b[1] ? Integer.compare(a[1], b[1]) : Integer.compare(b[2], a[2])
        );

        // 각 방향마다 포갤 수 있는 최대 개수 기록
        // 원래 번호 기준으로 기록. 값은 0-based 위치
        int[] ascLis = new int[n];
        int[] descLis = new int[n];
        lis = new int[n]; // 두 방향 모두 재사용

        // 정방향 : 안쪽으로 포갤 수 있는 최대 개수
        int len = 0;
        for(int i=0; i<n; i++) {
            int idx = binarySearch(0, len, dolls[i][2]);
            lis[idx] = dolls[i][2];
            ascLis[dolls[i][0]] = idx;

            if(idx == len) len++;
        }

        // 역방향 : 바깥쪽으로 포갤 수 있는 최대 개수
        // 높이를 음수로 바꿔 넣어 감소 수열을 증가 수열로 처리
        // 같은 인형 묶음을 반대로 읽는 것이므로 len은 정방향과 동일하게 L이 됨
        len = 0;
        for(int i=n-1; i>=0; i--) {
            int idx = binarySearch(0, len, dolls[i][2] * -1);
            lis[idx] = dolls[i][2] * -1;
            descLis[dolls[i][0]] = idx;

            if(idx == len) len++;
        }

        // (asc + 1) + (desc + 1) - 1 == L
        // 원래 번호 순서로 훑으므로 출력은 자연히 오름차순
        int cnt = 0;
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<n; i++) {
            if(ascLis[i] + descLis[i] == len - 1) {
                cnt++;
                sb.append(i+1).append(" ");
            }
        }

        System.out.println(len + "\n" + cnt + "\n" + sb);
    }

    // [lo, hi) 범위에서 h 이상인 첫 위치 반환 (lower bound)
    // 순증가이므로 같은 높이도 이어 붙일 수 없는 위치로 취급
    static int binarySearch(int lo, int hi, int h) {
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(h > lis[mid]) lo = mid + 1;
            else hi = mid;
        }

        return lo;
    }

}