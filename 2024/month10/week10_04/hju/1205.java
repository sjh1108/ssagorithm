import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N, newScore, P;

        N = Integer.parseInt(st.nextToken());
        newScore = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());

        int[][] sList = new int[N][2];

        if(N == 0) {
            if(P != 0)  System.out.println(1);
            else        System.out.println(-1);
            return;
        }

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++)
            sList[i][0] = Integer.parseInt(st.nextToken());
        
        int idx = 1;
        for(int i = 0; i < N; i++) {

            if(idx > P) {
                idx = -1;
                break;
            }

            // 현재 점수가 태수의 점수보다 작을 경우
            if(newScore > sList[i][0]) {
                // 앞 점수가 태수의 점수와 같다면 동일 등수
                if(i != 0 && newScore == sList[i - 1][0])   idx = sList[i - 1][1];
                break;
            }
            
            // 현재 점수가 앞 점수와 같다면 앞 등수와 동일
            if(i != 0 && sList[i - 1][0] == sList[i][0])  sList[i][1] = sList[i - 1][1];
            // 아니라면 idx 로 등수 설정
            else    sList[i][1] = idx;

            idx++;
        }

        // idx(카운팅한 점수의 개수)가 P 보다 크다면 랭킹에 올라갈 수 없음
        if(idx > P) {
            System.out.println(-1);
            return;
        }
        // P를 넘지 않은 상태에서 리스트의 마지막 점수와 태수의 점수가 같은 경우 
        if(newScore == sList[N - 1][0])     idx = sList[N - 1][1];
        System.out.println(idx);
    }
}