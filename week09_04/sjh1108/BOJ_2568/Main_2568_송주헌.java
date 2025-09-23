import java.io.*;
import java.util.*;

class Main {
    private static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 이제보니 이렇게 풀이 할 거였으면 지역변수로 해도 됐네요
        N = Integer.parseInt(br.readLine());

        StringTokenizer st;
        // 전깃줄 배열
        // 0번 인덱스는 전깃줄의 번호
        // 1번 인덱스는 전깃줄이 연결된 a 숫자
        // 2번 인덱스는 전깃줄이 연결된 b 숫자
        int[][] line = new int[N][3];
        for(int i = 0; i < N; i++){
            line[i][0] = i;

            st = new StringTokenizer(br.readLine());

            line[i][1] = Integer.parseInt(st.nextToken());
            line[i][2] = Integer.parseInt(st.nextToken());
        }

        // 전깃줄을 a 내림차순으로 정렬함
        Arrays.sort(line, Comparator.comparingInt(o -> o[1]));

        int[] LIS = new int[N];
        int[] tracking = new int[N];

        // a 내림차순으로 정렬되어 있기 때문에
        // b 기준으로 LIS 알고리즘 사용하면 겹치지 않는 선의 최소 개수 확인 가능
        int len = 0;
        for(int i = 0; i < N; i++){
            int b = line[i][2];

            int idx = Arrays.binarySearch(LIS, 0, len, b);
            if(idx < 0) idx = -idx - 1;
            
            LIS[idx] = b;
            tracking[i] = idx;

            if(idx == len) len++;
        }

        // 제거해야 하는 전깃줄은 겹치지 않는 선의 개수임
        System.out.println(N - len);

        int curLISIdx = len - 1;
        boolean[] isLIS = new boolean[N];
        for(int i = N-1; i >= 0; i--){
            if(tracking[i] == curLISIdx){
                isLIS[i] = true;
                curLISIdx--;
            }
            
            if(curLISIdx < 0) break;
        }
        
        // LIS 배열과 비교하여
        // LIS에 해당하는 전깃줄일 경우 pq에 추가
        // (제거해야하는 전깃줄은 오름차순으로 출력해야하기 때문에 PQ 사용)
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int i = 0; i < N; i++){
            if(!isLIS[i]){
                pq.add(line[i][1]);
            }
        }

        StringBuilder sb = new StringBuilder();
        while(!pq.isEmpty()){
            sb.append(pq.poll() + "\n");
        }
        System.out.print(sb);
    }
}