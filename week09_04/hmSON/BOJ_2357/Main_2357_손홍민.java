import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2357_손홍민 {
	
	static int n, p;
	static int[] arr;
	
	// 세그먼트 트리. 각 정점의 0번은 최소값, 1번은 최대값 기록
	static int[][] tree;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		arr = new int[n];
		for(int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		build();
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			// 순서 보장이 안되어 있으므로 a를 작은 수로 재정렬
			if (a > b) { 
				int t = a; a = b; b = t; 
			}
			
			sb.append(getMin(a, b) + " " + getMax(a, b)).append("\n");
		}
		
		System.out.println(sb);
	}
	
	// 세그먼트 트리 빌드
	// 세그먼트 트리를 만들기 위해 필요한 최소한의 정점 수는 n 이상이면서 가장 작은 제곱수에 2를 곱한 값이다.
	// 이후 n ~ 2*n-1번 정점은 리프 노드이므로 최소값과 최대값 모두 입력값들을 삽입한다.
	// 이후 리프노드그 아닌 칸들을 순회하면서 자식 노드들의 값을 확인 후 최소값과 최대값을 등록한다.
	static void build() {
		p = 1;
		while (p < n) p = p << 1;
		tree = new int[2 * p][2];
		for(int i=0; i<2*p; i++) {
		    tree[i][0] = Integer.MAX_VALUE;
		    tree[i][1] = Integer.MIN_VALUE;
		}
		
		for(int i=0; i<n; i++) {
	        tree[p + i][0] = arr[i];
	        tree[p + i][1] = arr[i];
	    }
		
		for(int i=p-1; i>0; --i) {
			tree[i][0] = Math.min(tree[i << 1][0], tree[i << 1 | 1][0]);
			tree[i][1] = Math.max(tree[i << 1][1], tree[i << 1 | 1][1]);
		}
	}
	
	// 특정 구간의 최대값을 세그먼트 트리로 반환
	static int getMax(int start, int end) {
		int res = 0;
		
		start += p; end += p;
        while (start <= end) {
            if ((start & 1) == 1) res = Math.max(res, tree[start++][1]);
            if ((end & 1) == 0) res = Math.max(res, tree[end--][1]);
            start >>= 1; end >>= 1;
        }
        return res;
	}
	
	// 특정 구간의 최소값을 세그먼트 트리로 반환
	static int getMin(int start, int end) {
		int res = Integer.MAX_VALUE;
		
		start += p; end += p;
        while (start <= end) {
            if ((start & 1) == 1) res = Math.min(res, tree[start++][0]);
            if ((end & 1) == 0) res = Math.min(res, tree[end--][0]);
            start >>= 1; end >>= 1;
        }
        return res;
	}

}