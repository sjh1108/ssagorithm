package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
	/*
	세그 먼트 트리인데 큰것 만 모으는 트리 하나
	작은것만 모으는 트리 하나
	이거를 하나로 모아서 쓰는것도 있기는 한데
	공간 복잡도 면이나 시간 복잡도 면이나 의미가 있나 싶어서 그걸로 풀지 않았음
	*/
	static long tree_min[];
	static long tree_max[];
	
	static long build_min(int node, int start,int end,int arr[]) {
		
		if(start == end) {
			return tree_min[node] = arr[start];
		}
		
		int mid = (start + end)/2;
		
		long left = build_min(node*2, start, mid, arr);
		long right = build_min(node*2+1, mid + 1, end, arr);
		
		tree_min[node] = Math.min(left, right);
		return tree_min[node];
	}
	
	static long build_max(int node, int start, int end, int arr[]) {
		if(start == end) {
			return tree_max[node] = arr[start];
		}
		
		int mid = (start+end)/2;
		
		long left = build_max(node*2, start, mid, arr);
		long right = build_max(node*2+1, mid + 1, end, arr);
		
		tree_max[node] = Math.max(left, right);
		return tree_max[node];
	}
	
	static long querry_min(int node, int start, int end, int left, int right) {
		if(end < left || right < start) return Long.MAX_VALUE;
		
		if(left <= start && end <= right) return tree_min[node];
		
		int mid = (start + end )/ 2;
		
		long left_min = querry_min(node*2, start, mid , left, right);
		long right_min = querry_min(node*2+1,mid+1,end,left,right);
	
		return Math.min(left_min, right_min);
	}
	
	static long querry_max(int node, int start, int end, int left, int right) {
		
		if(end < left || right < start) return Long.MIN_VALUE;
		
		if(left <= start && end <= right) return tree_max[node];
		
		int mid = (start + end)/2;
		
		long left_max  = querry_max(node*2, start, mid, left, right);
		long right_max = querry_max(node*2+1, mid+1, end, left, right);
		
		return Math.max(left_max, right_max);
	}
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        int[] arr = new int[N+1];
        tree_min = new long[4*N];
        tree_max = new long[4*N];
        
        for(int i=1; i<= N; i++) {
        	arr[i] = Integer.parseInt(br.readLine());
        }
        
        
        build_max(1, 1, N,arr);
        build_min(1,1,N,arr);
        
        
        for(int i=0; i< M; i++) {
        	st = new StringTokenizer(br.readLine());
        	
        	int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	
        	
        	long min = querry_min(1, 1, N, a, b);
        	long max = querry_max(1, 1, N, a, b);
        	
        	bw.write(Long.toString(min) +  " " + Long.toString(max) + "\n");
        }
       
        //bw.write(Long.toString(count));
        bw.flush();
        bw.close();
        
        
    }
}