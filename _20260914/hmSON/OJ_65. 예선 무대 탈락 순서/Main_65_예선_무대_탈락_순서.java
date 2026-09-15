import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 시뮬레이션, 큐
     * 전형적인 요세푸스 문제
     * 큐를 만들어 1~N까지 저장하고, K-1번 회전시킨 후 K번째 참가자를 탈락시키고 스트링빌더에 등록
     * K의 입력값이 최대 10^18이므로 매 턴마다 현재 인원수로 나눈 뒤 나머지 값만큼만 회전
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long k = Long.parseLong(st.nextToken());

        // 요세푸스용 큐, 각 참가자의 번호 등록
        Queue<Integer> q = new ArrayDeque<>();
        for(int i=1; i<=n; i++) q.add(i);

        StringBuilder sb = new StringBuilder();
        while(q.size() > 1) {
            // 실질적으로 필요한 회전 횟수만 다뤄야 함
            // 결과가 0이면 n회로 변경
            long rotateCnt = k % n;
            if(rotateCnt == 0) rotateCnt = n;

            // rotateCnt-1번째 참가자까지는 패스
            for(int i=0; i<rotateCnt-1; i++) {
                q.add(q.poll());
            }

            // rotateCnt번째 참가자 탈락
            int eliminated = q.poll();
            sb.append(eliminated).append(" ");
            n--;
        }

        // 최종 생존자도 등록
        sb.append(q.poll());
        System.out.println(sb);
    }

}