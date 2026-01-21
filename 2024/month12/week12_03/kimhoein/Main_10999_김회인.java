package study;
import java.io.*;
import java.util.*;

public class Main_10999_김회인 {
    
	/*
	 * 세그트리
	 * lazy 업데이트 방식 이용해주면 문제가 풀린다.
	 * 세그 트리를 알아야하고
	 * lazy 업데이트의 경우에는 update 값을 부모에 미리 저장을 해두었다가
	 * 만약 업데이트 할 상황이 생가면 자식에 lazy 값을 전달해주고
	 * 본인 값만 업데이트 해서 tree의 모든 자식에게 업데이트를 진행하지 않는다.
	 * 이를 이용해 시간을 최적화 하는 방식이다.
	 */
    static long[] tree;
    //static int n;
    static long[] lazy;
    
    static long build(int node,int start, int end, long[] arr) {
        if(start == end) {
            //System.out.println("arr[node] : " + arr[node]);
            return tree[node] = arr[start];
        }
        
        int mid = (start + end)/2;
        long leftSum = build(node*2, start, mid, arr);
        long rightSum = build(node*2+1, mid + 1, end, arr);
        tree[node] = leftSum + rightSum;
        return tree[node];
    }
    
    static long query(int node, int start, int end, int left, int right) {
    	progate(node, start, end);
    	
    	if(right < start || end < left) return 0;
        if(left <= start && end <= right) return tree[node];
        
        int mid = (start + end) / 2;
        long leftSum = query(node * 2, start, mid, left, right);
        long rightSum = query(node * 2 + 1, mid + 1, end, left, right);
        return leftSum + rightSum;
    }
    
    static void progate(int node, int start, int end) {
    	
    	
    	if(lazy[node] != 0) {
    		tree[node] += lazy[node] * (end - start + 1);
    		if(start != end) {
    			lazy[node * 2] += lazy[node];
    			lazy[node * 2 + 1] += lazy[node];
    		}
    		lazy[node] = 0;
    	}
    }
    
    static void update(int node, int start, int end, int left, int right, long val) {
        progate(node, start, end);
        
    	if(right < start || left > end) return;
    	if(left <= start && end <= right) {
    	    lazy[node] += val;
    	    progate(node, start, end);
    	    return;
    	}

        int mid = (start+ end)/2;
        update(node *2, start, mid,left, right, val);
        update(node *2 + 1, mid + 1, end, left, right, val);
        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N,M,K;
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        long array[] = new long[N];
        tree = new long[N*4];
        lazy = new long[N*4];
        
        for(int i=0; i<N; i++) {
            array[i] = Long.parseLong(br.readLine());
        }
        
        build(1,0,N-1,array);
        
        //System.out.println("tree[1] :  " + tree[1]);
        for(int i=0; i<M+K; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            
            if(a == 1) {
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                long d = Long.parseLong(st.nextToken());
                
                update(1, 0, N-1, b-1, c-1, d);
            }else {
                
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                
                long num = query(1, 0, N-1, b-1, c-1);
                bw.write(Long.toString(num) + "\n");
            }
        }
        
        //bw.write(longeger.toString(dp[n]));
        bw.flush();
        bw.close();
        br.close();
    }
    
}
