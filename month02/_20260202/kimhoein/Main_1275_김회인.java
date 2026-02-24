package study;

import java.io.*;
import java.util.*;

public class Main_1275_김회인{
	/*
	 * 특정 구간에 대한 합을 구하는 것
	 * 그리고 특정 node에 대한 값을 바꿔서 특정 구간에 대한 합을 빠르게 업데이트 해야할때
	 * segtree 알고리즘이 주로 쓰인다.
	 */
	static int N;
	static long tree[];
	
	static long build(int node, int start, int end,long arr[])
	{
		if(start == end) {
			//System.out.println("start : " + start + " node : " + node);
			return tree[node] = arr[start];
		}
		
		int mid = (start + end)/2;
		
		long leftSum = build(node *2, start, mid, arr);
		long rightSum = build(node *2+1, mid+1, end, arr);
		tree[node] = leftSum + rightSum;
		
		return tree[node];
	}
	
	static long querry(int node, int start, int end, int left, int right) {
		
		if(end < left || right < start) return 0;
		if(left <= start && end <= right) return tree[node];
		
		int mid = (start + end)/2;
		long leftSum = querry(node*2, start, mid, left, right);
		long rightSum = querry(node*2+1,mid+1,end, left,right);
		
		return leftSum + rightSum;
	}
	
	static void update(int node, int start, int end, int idx, long val) {
		if(idx < start || idx > end) return;
		if(start == end) {
			tree[node] = val;
			return;
		}
		int mid = (start + end)/2;
		update(node *2, start, mid, idx,val);
		update(node *2+1, mid+1, end, idx,val);
		
		tree[node] = tree[node*2] + tree[node *2+1];
	}
	
	public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	
    	N = Integer.parseInt(st.nextToken());
    	int Q = Integer.parseInt(st.nextToken());
    	long[] arr = new long[N+1];
    	tree = new long[N*4];
    	
    	st = new StringTokenizer(br.readLine());
    	
    	for(int i=1; i<=N; i++) {
    		arr[i] = Integer.parseInt(st.nextToken());
    	}
    	
    	build(1,1,N,arr);
    	
    	for(int i=0; i<Q; i++) {
    		st = new StringTokenizer(br.readLine());
    		
    		int a = Integer.parseInt(st.nextToken());
    		int b = Integer.parseInt(st.nextToken());
    		int c = Integer.parseInt(st.nextToken());
    		int d = Integer.parseInt(st.nextToken());
    		
    		
    		// a가 b보다 큰 경우 바꿔주는 로직
    		if(a > b) {
    			int temp = b;
    			b = a;
    			a = temp;
    		}
    		
    		long ans = querry(1,1,N,a,b);
    		update(1,1,N,c,d);
    		bw.write(Long.toString(ans) + "\n");
    		
    	}
    	
    	//bw.write(Integer.toString(total));
    	bw.flush();
    	bw.close();
    	br.close();
    }
	

}
