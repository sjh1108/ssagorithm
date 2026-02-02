package 20260202.sjh1108.BOJ_1275;

import java.io.*;
import java.util.*;

// 백준 1275 - 커피숍2 (Segment Tree)
class Main {

    private static long[] num, tree;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 수의 개수
        int Q = Integer.parseInt(st.nextToken()); // 턴의 개수

        num = new long[N];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) num[i] = Integer.parseInt(st.nextToken());
        
        // 세그먼트 트리 생성
        makeTree(N);

        StringBuilder sb = new StringBuilder();
        while(Q-- > 0){
            st = new StringTokenizer(br.readLine());

            // x~y 구간 합 구하기 (인덱스는 1-based로 들어오므로 0-based로 변환)
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;

            // x가 y보다 클 수 있으므로 swap 처리
            if(x > y){
                int tmp = x;
                x = y;
                y = tmp;
            }

            // 구간 합 쿼리
            long sum = getSum(0, N-1, 1, x, y);
            sb.append(sum + "\n");

            // a번째 수를 b로 바꾸기
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken());

            // 변화량(dif) 계산 후 업데이트
            long dif = b - num[a];
            update(0, N-1, 1, a, dif);
            num[a] = b; // 원본 배열 갱신
        }

        System.out.println(sb);
    }

    // 세그먼트 트리 초기화 및 생성
    private static void makeTree(int N){
        // 트리의 높이(h) 및 배열 크기 계산
        // h = ceil(log2(N))
        int h = (int)Math.ceil(Math.log(N) / Math.log(2));

        // 트리 배열의 크기 = 2^(h+1) (약 4*N)
        int treeSize = (int)Math.pow(2, h+1);

        tree = new long[treeSize];

        // 트리 초기화 (루트 노드는 1번 인덱스)
        init(0, N-1, 1);
    }

    // 트리 초기화 재귀 함수
    // s: 시작 인덱스, e: 끝 인덱스, node: 현재 트리 노드 번호
    private static long init(int s, int e, int node){
        if(s==e){
            return tree[node] = num[s]; // 리프 노드
        }

        int mid = (s+e) / 2;

        // 왼쪽 자식(node*2) + 오른쪽 자식(node*2 + 1)
        return tree[node] = init(s, mid, node*2) + init(mid+1, e, node*2 + 1);
    }

    // 값 변경 함수
    // idx: 변경할 원본 배열의 인덱스, dif: 변화량
    private static void update(int s, int e, int node, int idx, long dif){
        // 범위 밖에 있는 경우
        if(s > idx || idx > e) return;

        // 범위 안에 있으면 변화량만큼 더해줌
        tree[node] += dif;

        // 리프 노드면 종료
        if(s == e) return;

        int mid = (s+e) / 2;
        update(s, mid, node*2, idx, dif);
        update(mid+1, e, node*2 + 1, idx, dif);
    }

    // 구간 합 구하기 함수
    // l~r: 구하고자 하는 구간
    private static long getSum(int s, int e, int node, int l, int r){
        // [s, e]가 [l, r]과 전혀 겹치지 않는 경우
        if(e < l || r < s) return 0;
        
        // [s, e]가 [l, r]에 완전히 포함되는 경우
        if(l <= s && e <= r) {
            return tree[node];
        }
        
        // 걸쳐있는 경우: 자식 노드로 분할
        int mid = (s+e)/2;
        return getSum(s, mid, node*2, l, r)+ getSum(mid+1, e, node*2+1, l, r);
    }
}