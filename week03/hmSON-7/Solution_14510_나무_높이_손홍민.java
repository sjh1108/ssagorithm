import java.io.*;
import java.util.*;
 
class Solution_14510_나무_높이_손홍민 {
     
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;
     
    static int n, maxH;
    static int[] arr;
     
    public static void main(String[] args) throws Exception {
        int t = Integer.parseInt(br.readLine());
        for(int i=1; i<=t; i++) {
            init();
            sb.append("#").append(i).append(" ");
            solve();
        }
        System.out.println(sb);
    }
     
    public static void init() throws Exception {
        maxH = 0;
        n = Integer.parseInt(br.readLine());
        arr = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            int x = Integer.parseInt(st.nextToken());
            arr[i] = x;
            if(x > maxH) maxH = x;
        }
    }
     
    public static void solve() {
        int cnt1 = 0, cnt2 = 0;
 
        for (int i = 0; i < n; i++) {
            int diff = maxH - arr[i];
            cnt2 += diff / 2;
            cnt1 += diff % 2;
        }
 
        int left = 0, right = 250000; // 넉넉하게
        int answer = -1;
 
        while (left <= right) {
            int mid = (left + right) / 2;
 
            int oddDays = (mid + 1) / 2;
            int evenDays = mid / 2;
 
            // 1짜리 작업은 반드시 홀수날에만 가능
            // 2짜리 작업은 짝수날이 부족하면 1짜리 작업 2개로 대체 가능
            int remCnt1 = Math.max(0, cnt1 - oddDays); // 1짜리 작업이 부족하면 안 됨
            int remCnt2 = Math.max(0, cnt2 - evenDays);
 
            if (remCnt1 == 0 && remCnt2 * 2 <= (oddDays - cnt1)) {
                // 남은 홀수 날로 2짜리 작업을 대체할 수 있다면 OK
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
 
        sb.append(answer).append("\n");
    }
 
}