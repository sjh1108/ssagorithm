package Rank4.gold_4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_14938 {
	
	static int v, e, dist;
	static int[] items;
	static int[][] map;
	
	public static void main(String[] args) throws Exception {
		init();
		floydWarshall();
		System.out.println(getMax());
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 지역, 수색 범위, 길 수
		v = Integer.parseInt(st.nextToken());
		dist = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		items = new int[v];
		map = new int[v][v];
		for(int i=0; i<v; i++) {
			// 최대 수색 범위 +1을 초기값으로 지정. 맵의 정보가 해당 값이면 길이 없는 것으로 판단
			Arrays.fill(map[i], dist+1);
			// 자기 자신은 0으로 지정
			map[i][i] = 0;
		}
		
		// 각 지역별 아이템 수
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<v; i++) {
			items[i] = Integer.parseInt(st.nextToken());
		}
		
		// 각 길을 구성하는 두 지역과 이동거리
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken())-1;
			int v2 = Integer.parseInt(st.nextToken())-1;
			int cost = Integer.parseInt(st.nextToken());
			if(cost > dist) continue;
			map[v1][v2] = cost;
			map[v2][v1] = cost;
		}
	}

	// 플로이드 워셜
	// 특정 위치를 경유지로 하는 경우의 이동 거리를 기록하고 최단 거리 갱신
	// 이동 거리가 dist 이상인 경우 이동 불가능한 경로인 것으로 취급
	static void floydWarshall() {
		for(int i=0; i<v; i++) {
			for(int j=0; j<v; j++) {
				// 두 인덱스가 같거나, 최대 이동거리 이상인 경우 무시
				if(i == j || map[j][i] > dist) continue;
				for(int k=0; k<v; k++) {
					map[j][k] = Math.min(map[j][k], map[j][i] + map[i][k]);
				}
			}
		}
	}
	
	// 각 지역을 시작점으로 하는 경우에 대한 아이템 개수 합계 반환
	static int getMax() {
		int max = 0;
		for(int i=0; i<v; i++) {
			int cnt = 0;
			for(int j=0; j<v; j++) {
				// 수색 범위 외 : 무시
				if(map[i][j] > dist) continue;
				cnt += items[j];
			}
			
			if(cnt > max) max = cnt;
		}
		
		return max;
	}

}
