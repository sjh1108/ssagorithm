import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_14658 {
	
	// BOJ_14658 : 하늘에서 별똥별이 빗발친다(Gold_3)
	// 자료구조 및 알고리즘 : 브루트 포스
	
	// 구역의 가로, 세로 길이, 트램펄린의 한 변의 길이, 떨어지는 별똥별의 개수
	static int r, c, len, cnt;
	// 별똥별이 떨어지는 위치 좌표 배열
	static int[][] meteors;
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		len = Integer.parseInt(st.nextToken());
		cnt = Integer.parseInt(st.nextToken());
		
		meteors = new int[cnt][2];
		for(int i=0; i<cnt; i++) {
			st = new StringTokenizer(br.readLine());
			int y = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			
			meteors[i] = new int[]{y, x};
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		// 트램펄린으로 튕겨낼 수 있는 별똥별 최대 개수
		int maxCnt = 0;
		
		// 임의의 별똥별 2개를 선택하고 1번 별똥별의 y 좌표, 2번 별똥별의 x 좌표를 트램펄린의 한 꼭지점 위치로 지정
		// 두 별똥별 m0와 m1이 하나의 별똥별을 가리키고 있어도 됨
		for(int[] m0 : meteors) {
			for(int[] m1 : meteors) {
				int startR = m0[0];
				int startC = m1[1];
				
				// 위에서 지정한 꼭지점을 기준으로 트램펄린을 배치했을 때 튕겨낼 수 있는 별똥별 개수
				int curCnt = 0;
				// 트램펄린 위로 떨어지는 별똥별의 개수를 확인
				for(int[] target : meteors) {
					int tr = target[0];
					int tc = target[1];
					
					if(tr >= startR && tc >= startC && tr <= startR + len && tc <= startC + len) {
						curCnt++;
					}
				}
				
				// 최대값 갱신
				if(curCnt > maxCnt) maxCnt = curCnt;
			}
		}
		
		// 트램펄린으로 막을 수 없는 별똥별의 최소 개수를 구하는 것이 목표
		System.out.println(cnt - maxCnt);
	}

}
