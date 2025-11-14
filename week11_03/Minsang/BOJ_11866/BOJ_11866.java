package week11_03.Minsang.BOJ_11866;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class BOJ_11866 {
    static int N, K;
    static Queue<Integer> q;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        q = new LinkedList<>();

        for(int i = 1; i <= N; i++) {
            q.add(i);
        }

        // 시작할 때  < 추가 시켜주고
        sb.append("<");
        // 제거하려는 K번째 전까지는 poll 한 뒤, 맨 뒤에 붙여줌
        while(!q.isEmpty()){
            for(int i = 1; i < K; i++) {
                q.add(q.poll());
            }

            // 큐 사이즈가 1일 때, 제거하고 > 추가
            if(q.size() == 1) {
                sb.append(q.poll() + ">");
            } else {
                // K번째 수는 제거하되, ,도 함께 추가
                sb.append(q.poll() + ", ");
            }
        }
        System.out.println(sb);
    }
}