import java.io.*;
import java.util.StringTokenizer;

public class Solution_1486_장훈이의_높은_선반_손홍민 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;
    //
    static int n, b, min;
    static int[] arr;
    public static void main(String[] args) throws Exception {
        int t = Integer.parseInt(br.readLine());
        for(int i=1; i<=t; i++) {
            read(); solve(i);
        }
        System.out.println(sb);
    }

    private static void read() throws Exception {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        arr = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        min = Integer.MAX_VALUE;
    }

    private static void solve(int idx) {
        sb.append("#").append(idx).append(" ");
        bt(0, 0);
        sb.append(min - b).append("\n");
    }

    // 백트래킹을 이용한 부분집합 생성 및 비교
    private static void bt(int sum, int idx) {
        // 키 합계가 선반 높이를 초과하면 최소 높이 갱신하고 리턴
        if(sum >= b) {
            min = Math.min(sum, min);
            return;
        }

        for(int i=idx; i<n; i++) {
            bt(sum + arr[i], i+1);
        }
    }
}
