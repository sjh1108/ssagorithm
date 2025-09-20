import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2042_손홍민 {
	
	static int n, p;
	static long[] arr, tree;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		arr = new long[n];
		for(int i=0; i<n; i++) {
			arr[i] = Long.parseLong(br.readLine());
		}
		build();
		
		StringBuilder sb = new StringBuilder();
		// cmd 1 : 배열의 a번째 값을 b로 변경
		// cmd 2 : a~b까지의 구간합 반환
		for(int i=0; i<m+k; i++) {
			st = new StringTokenizer(br.readLine());
			int cmd = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			long b = Long.parseLong(st.nextToken());
			
			if(cmd == 1) {
				change(a-1, b);
			} else {
				sb.append(prefixSum(a-1, (int)b-1)).append("\n");
			}
		}
		
		System.out.println(sb);
	}
	
	// 세그먼트 트리 빌드 메서드
	// n값 이상인 가장 작은 제곱수 p를 구하고, tree 배열의 크기는 2 * p로 설정
	// 이후 p 이상인 인덱스(리프노드)에 기존 배열의 원소를, p 미만인 인덱스에 두 서브노드의 합을 입력
	static void build() {
		p = 1;
		while (p < n) p = p << 1;
		tree = new long[2 * p];
		
		for(int i=0; i<n; i++) {
			tree[p+i] = arr[i];
		}
		
		for(int i=p-1; i>0; --i) {
			tree[i] = tree[i << 1] + tree[i << 1 | 1];
		}
	}
	
	// 원소 변경 및 구간합 갱신 메서드
	// a번째 인덱스의 값을 변경하려면 세그트리 배열의 p+a번째 인덱스로 찾아가야 함
	// 값 변경 후 상위 노드로 한 칸씩 올라가면서 구간합 갱신
	static void change(int key, long value) {
		int target = p + key;
		tree[target] = value;
		while(true) {
			target >>= 1;
			if(target == 0) return;
			tree[target] = tree[target << 1] + tree[target << 1 | 1];
		}
	}
	
	// 리프 노드부터 시작하여 올라오면서 구간합 연산
	static long prefixSum(int start, int end) {
		long res = 0;
		
		start += p; end += p;
        while (start <= end) {
        	// 자신이 오른쪽 자식인 경우
            if ((start & 1) == 1) res += tree[start++];
            // 자신이 왼쪽 자식인 경우
            if ((end & 1) == 0) res += tree[end--];
            start >>= 1; end >>= 1;
        }
        return res;
	}

}