import java.io.*;
import java.util.*;

class Main {

    static int[] parent;
    static int[] hand;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // 기존 시간 초과 코드
        // HashSet<Integer> hand = new HashSet<>();
        // for(int i = 0; i < K; i++) {
        //     int next = Integer.parseInt(st.nextToken());

        //     System.out.println(hand.higher(next));
        //     hand.remove(hand.higher(next));
        // }

        hand = new int[M];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < M; i++) 
            hand[i] = Integer.parseInt(st.nextToken());
        
        Arrays.sort(hand);

        parent = new int[M + 1];
        for(int i = 1; i <= M; i++) 
            parent[i] = i;
        
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < K; i++) {
            int next = Integer.parseInt(st.nextToken());

            int idx = binarySearch(next);

            int canIdx = find(idx);

            sb.append(hand[canIdx] + "\n");

            // 제출한 카드 다음으로 낼 수 있는 카드를 유니온
            union(canIdx, canIdx + 1);
        }

        System.out.println(sb);
    }

    static int find(int x) {
        if(x == parent[x]) return x;
        return parent[x] = find(parent[x]);
    }

    static void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        parent[rootX] = rootY;
    }

    static int binarySearch(int target) {
        int left = 0;
        int right = hand.length;

        while(left < right) {
            int mid = (left + right) / 2;
            
            if(hand[mid] > target)  right = mid;
            else    left = mid + 1;
        }

        return right;
    }
}