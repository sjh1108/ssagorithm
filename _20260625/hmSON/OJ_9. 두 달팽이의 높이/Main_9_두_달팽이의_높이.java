import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] date = new int[k];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<k; i++) {
            date[i] = Integer.parseInt(st.nextToken());
        }

        long ha = n; // 달팽이 A의 현재 높이
        long t = 0; // 경과 일수
        int idx = 0; // 달팽이 A의 다음 등반일 포인터

        while(true) {
            long h = ha;
            boolean natural = ((t + h + m) % 2 == 0);
            boolean res = natural
                    ? (t >= Math.abs(h - m)) // 자연 패리티 경로(두 높이 차가 짝수인 경우)
                    : (t >= m + h + 1); // 바닥 이용 경로(두 높이 차가 홀수인 경우)

            // 두 달팽이가 같은 높이에 공존할 수 있으면 날짜 출력 및 종료
            if(res) {
                System.out.println(t);
                return;
            }

            // 다음 날로 진행 및 달팽이 A 위치 갱신
            t++;
            while(idx < k && date[idx] < t) idx++; // 달팽이 A의 등반 포인터 관리
            if(idx < k && date[idx] == t) {
                ha++; idx++; // 등반일인 경우
            } else if(ha > 1) ha--; // 등반일이 아닌 경우 미끄러짐(단, 이미 높이 1인 경우 바닥이므로 1 유지)
        }
    }

}