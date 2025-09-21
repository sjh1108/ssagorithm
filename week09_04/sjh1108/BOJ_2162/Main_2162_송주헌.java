package week09_04.sjh1108.BOJ_2162;

import java.io.*;
import java.util.*;

class Main {
    /*
     * 전형적인 세그먼트 트리 문제
     * 
     * 각 구간에서의 최솟값과 최댓값을 저장하는 세그먼트 트리 생성
     * 
     * 특정 구간에서의 최솟값과 최댓값을 계산하는 get 함수 생성
     */
    private static int N;
    private static int[] arr;
    private static int[] min, max;

    private static void init(){
        int k = (int)Math.ceil(Math.log(N) / Math.log(2)) + 1;
        int size = (int)Math.pow(2, k);

        min = new int[size];
        max = new int[size];

        initMin(1, N, 1);
        initMax(1, N, 1);
    }

    private static int initMin(int start, int end, int node){
        if(start == end) return min[node] = arr[start];

        int mid = (start + end) / 2;
        return min[node] = Math.min(initMin(start, mid, node*2), initMin(mid+1, end, node*2 + 1));
    }
    
    private static int initMax(int start, int end, int node){
        if(start == end) return max[node] = arr[start];

        int mid = (start + end) / 2;
        return max[node] = Math.max(initMax(start, mid, node*2), initMax(mid+1, end, node*2 + 1));
    }

    private static int getMin(int start, int end, int left, int right, int node){
        if(start > right || end < left) return 1_000_000_000;
        if(start >= left && end <= right){
            return min[node];
        }

        int mid = (start + end) / 2;
        return Math.min(getMin(start, mid, left, right, node*2), getMin(mid+1, end, left, right, node*2 + 1));
    }

    private static int getMax(int start, int end, int left, int right, int node){
        if(start > right || end < left) return 0;
        if(start >= left && end <= right){
            return max[node];
        }

        int mid = (start + end) / 2;
        return Math.max(getMax(start, mid, left, right, node*2), getMax(mid+1, end, left, right, node*2 + 1));
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        arr = new int[N+1];
        for(int i = 1; i <= N; i++){
            arr[i] = Integer.parseInt(br.readLine());
        }

        init();

        StringBuilder sb = new StringBuilder();
        while(M-- > 0){
            st = new StringTokenizer(br.readLine());

            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            int min = getMin(1, N, left, right, 1);
            int max = getMax(1, N, left, right, 1);

            sb.append(min + " " + max + "\n");
        }
        System.out.println(sb);
    }
}