import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_14719_빗물 {
	
	/*
	 * BOJ_14719 : 빗물(Gold_5)
	 * 자료구조 및 알고리즘 : 구현, 시뮬레이션
	 * 2차원 공간에 각 구간별 블록의 높이가 주어질 때, 최대로 채울 수 잇는 빗물의 양을 구해야 한다.
	 * 
	 * O(H*W)가 되더라도 최대 25000회이므로 빗물의 높이를 1씩 올리면서 그 시점에 채워지는 빗물의 높이를 구한다.
	 */
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 2차원 공간의 세로와 가로 길이ㄴ
		StringTokenizer st = new StringTokenizer(br.readLine());
		int h = Integer.parseInt(st.nextToken());
		int w = Integer.parseInt(st.nextToken());
		
		// 각 구역의 블록 높이를 1차원 배열에 기록한다.
		// 추가로 굳이 확인할 필요가 없는 높이를 처음부터 안보는 게 좋지 않을까? -> 최저점과 최고점까지만 사용한다
		st = new StringTokenizer(br.readLine());
		int min = h, max = 0;
		int[] arr = new int[w];
		for(int i=0; i<w; i++) {
			int curH = Integer.parseInt(st.nextToken());
			arr[i] = curH;
			if(curH < min) min = curH;
			if(curH > max) max = curH;
		}
		
		// 높이 min+1부터 max까지 높이를 1씩 올리면서 빗물이 고이는 양을 기록한다.
		// 2차원 공간의 범위 밖은 높이가 0이므로 높이가 i 이상인 블록을 만날 때마다 그 블록위 가로 위치를 기억해야 한다.
		// 높이가 i 이상인 두 블록이 존재한다면 두 블록 사이의 거리를 통해 고일 수 있는 빗물의 양을 구한다.
		int total = 0;
		for(int i=min+1; i<=max; i++) {
			int line = 0, prev = -1;
			for(int j=0; j<w; j++) {
				if(arr[j] < i) continue;
				if(prev == -1) prev = j;
				else {
					line += j-prev-1;
					prev = j;
				}
			}
			
			total += line;
		}
		
		System.out.println(total);
	}

}
