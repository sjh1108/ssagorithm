import java.io.*;
import java.util.*;

public class Main_16724_피리_부는_사나이 {
	
	/*
	 * BOJ_16724 : 피리 부는 사나이 (Gold_3)
	 * 자료구조 및 알고리즘 : 분리 집합 (Union-Find), DFS
	 *
	 * [문제 요약]
	 * - NxM 지도에 U, D, L, R 방향이 적혀 있다. 사람들은 이 방향대로만 이동한다.
	 * - 이동하다 보면 특정 구역(사이클)을 계속 맴돌게 된다.
	 * - 모든 사람이 안전하게 멈출 수 있도록 'SAFE ZONE'을 설치하려 한다.
	 * - 최소 몇 개의 SAFE ZONE을 설치해야 하는지 구하라.
	 *
	 * [핵심 아이디어]
	 * - 각 칸의 진출 차수(Out-degree)는 무조건 1이다. 
	 * 즉, 이동 경로를 따라가다 보면 반드시 어딘가의 사이클(Cycle)로 빨려 들어가게 된다.
	 * - 서로 연결된 칸들은 결국 같은 SAFE ZONE을 공유할 수 있다.
	 * - 따라서, "이동 경로로 연결된 모든 칸을 하나의 집합"으로 묶었을 때,
	 * 최종적으로 생성된 **집합(Component)의 개수**가 필요한 SAFE ZONE의 개수이다.
	 * - DFS로 사이클을 감지할 수도 있지만, 단순히 `union(현재, 다음)`을 수행하여 
	 * 집합을 합치는 방식(Union-Find)이 구현상 매우 직관적이다.
	 *
	 * [구현 메모]
	 * - 2차원 좌표 (r, c)를 1차원 인덱스 (r * C + c)로 변환하여 관리.
	 * - 모든 입력을 받은 후, 모든 칸에 대해 `union(current, next)` 수행.
	 * - 마지막에 `head[i] == i`인 노드(루트)의 개수를 센다.
	 *
	 * [시간 복잡도]
	 * - 칸의 개수 V = R * C.
	 * - Union-Find 연산은 거의 상수 시간(α(V))이므로, 전체 복잡도는 O(R * C).
	 */

	static int r, c;
	static int[] head;    // 각 칸(노드)의 부모를 저장하는 배열
	static char[][] map;  // 방향 정보 저장
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		
		// 맵 입력 및 parent 배열 초기화
		map = new char[r][c];
		head = new int[r * c]; // 2차원 좌표를 1차원으로 펼쳐서 관리
		
		for (int i = 0; i < r; i++) {
			String line = br.readLine();
			for (int j = 0; j < c; j++) {
				map[i][j] = line.charAt(j);
				// 초기 상태: 모든 칸이 자기 자신을 대표로 가짐
				head[i * c + j] = i * c + j; 
			}
		}
	}

	// 유니온 파인드의 Find 연산 (경로 압축 Path Compression 적용)
	static int find(int x) {
		if (head[x] == x) return x;
		return head[x] = find(head[x]);
	}

	// 유니온 파인드의 Union 연산
	static void union(int a, int b) {
		int ra = find(a);
		int rb = find(b);
		
		// 두 집합을 합침 (서로 다른 집합일 경우에만 부모 갱신)
		if (ra != rb) {
			head[rb] = ra; // rb의 부모를 ra로 설정하여 연결
		}
	}
	
	// 현재 좌표(y, x)와 방향 정보를 이용해 다음 칸의 1차원 인덱스 반환
	static int getNextIndex(int y, int x) {
		int ny = y, nx = x;
		char dir = map[y][x];

		if (dir == 'U') ny--;
		else if (dir == 'D') ny++;
		else if (dir == 'L') nx--;
		else if (dir == 'R') nx++;

		return ny * c + nx;
	}

	public static void main(String[] args) throws Exception {
		init();

		// 모든 칸에 대해 "현재 칸"과 "이동할 다음 칸"을 같은 집합으로 병합
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				int current = i * c + j;
				int next = getNextIndex(i, j);
				
				// 연결된 구간은 같은 Safe Zone을 쓰게 되므로 Union
				union(current, next);
			}
		}

		// 최종적으로 형성된 집합의 개수(루트 노드의 개수) 카운트
		int safeZoneCount = 0;
		for (int i = 0; i < r * c; i++) {
			// 자신이 루트인 경우 -> 하나의 독립된 컴포넌트(사이클 그룹)를 의미
			if (head[i] == i) {
				safeZoneCount++;
			}
		}

		System.out.println(safeZoneCount);
	}
}
