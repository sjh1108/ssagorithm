import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_17471_게리맨더링_손홍민 {

	// 선거구 수, 최소 차이
	static int n, minDiff;
	// 지역 인구수, 지역 링크 인접행렬용 비트마스킹 배열
	static int[] po, link;
	
	public static void main(String[] args) throws Exception {
		init();
		comb(0, 0, 0);
		// 여전히 minDiff 값이 안변했다면 선거구 획정 불가 -> -1 출력, 아니라면 minDiff 출력
		System.out.println(minDiff == Integer.MAX_VALUE ?  -1 : minDiff);
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		po = new int[n];
		link = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			po[i] = Integer.parseInt(st.nextToken());
		}
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			int cnt = Integer.parseInt(st.nextToken());
			for(int j=0; j<cnt; j++) {
				int near = Integer.parseInt(st.nextToken()) - 1;
				link[i] |= 1 << near;
			}
		}
		// 항상 최대값으로 초기화
		minDiff = Integer.MAX_VALUE;
	}

	// 알고리즘 1 : 조합론
	// 각 선거구별로 선택하는 경우와 선택하지 않는 경우로 분기
	static void comb(int idx, int cnt, int bit) {

		// 조합 공식에 따라 nCr을 구하면 반대쪽은 반드시 nC(n-r)이므로 필요 이상의 탐색은 하지 않음
		// 추가로 범위 초과 방지
		if(cnt > (n+1)/2 || idx >= n) return;

		// 일단 현재 선거구를 포함한 상태로 지역 연결 정보 확인
		// 만약 bfs 결과가 -1이면 선택한 선거구는 연결되어 있지 않음
		// 아니라면 인구수 총합이 반환됨
		bit |= 1<<idx;
		int curPo = bfs(cnt+1, idx, bit);
		if(curPo != -1) {
			int remain = 0;
			// 반대측 선거구도 지역 연결 정보 확인
			// 마찬가지로 bfs 결과가 -1이면 선택한 선거구는 연결되어 있지 않음
			for(int i=0; i<n; i++) {
				if((bit & (1<<i)) != 0) continue;
				remain = bfs(n - (cnt+1), i, ~bit);
				break;
			}
			// 두 그래프 모두 연결된 상태라면 인구 최소 차이 갱신
			if(remain != -1) {
				minDiff = Math.min(minDiff, Math.abs(curPo - remain));
			}
		}
		// 해당 선거구를 포함한 상태로 다음 선거구 탐색
		comb(idx+1, cnt+1, bit);

		// 해당 선거구를 포함하지 않은 상태로 다음 선거구 탐색
		bit &= ~(1<<idx);
		comb(idx+1, cnt, bit);
	}

	// 알고리즘 2 : BFS
	// 시작 위치와 선택한 선거구 정보를 받은 상태로 BFS 진행
	// 만약 탐색하면서 찾은 선거구 수와 받은 정보가 일치하지 않다면 이는 하나로 연결된 선거구가 아니라는 뜻
	// 연결된 상태 : 전체 인구 수 반환, 연결되지 않은 상태 : -1 반환
	static int bfs(int cnt, int start, int bit) {
		Queue<Integer> q = new ArrayDeque<>();
		q.add(start);
		int totalPo = 0;
		bit &= ~(1<<start);
		while(!q.isEmpty()) {
			int cur = q.poll();
			totalPo += po[cur];
			cnt--;
			int nextList = link[cur];
			for(int i=0; i<n; i++) {
				if((nextList & (1<<i)) == 0 || (bit & (1<<i)) == 0) continue;
				bit &= ~(1<<i);
				q.add(i);
			}
		}
		
		return cnt == 0 ? totalPo : -1;
	}
	
}
