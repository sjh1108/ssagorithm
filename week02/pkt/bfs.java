package swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;



public class Main{
	// 재료넣기
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringTokenizer st;
	static int n, m, ans =0, max_ans = 0;
	static int[][] map;
	static int[][] visited;
	
	
	// main 설정(입력 + 출력)
	public static void main(String[] Args) throws IOException {
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		// 세부, map에 이차원 배열 입력받기
		map = new int[n][m];
		visited = new int[n][m];
		for (int i = 0 ; i < n ; i++) {
	        st = new StringTokenizer(br.readLine());
	        for (int j = 0 ; j < m ; j++) {
	            map[i][j] = Integer.parseInt(st.nextToken());
	        }
	    }
		
		// 세부, 미방문과 그림이 그려져 있으면, bfs로 가
		for (int i = 0 ; i < n ; i++) {      
			for (int j = 0 ; j < m; j++) {          
				if (visited[i][j] == 0 && map[i][j] == 1) {
					bfs(i, j);
				}     
			}
		}		
		
		// 정답 출력
	    System.out.println(ans);
	    System.out.println(max_ans);		
	}
	
	// bfs 선언 : 2번은 봐야 함. 
	// static으로 선언된 전역 변수이기 때문에, void 함수 안에서도 자유롭게 사용되고, 값도 변경 가능합니다.
	static void bfs(int i, int j) {
		
		ans++; // 1  or + 1
		Queue<int[]> q = new LinkedList<>(); // LinkedList는 큐처럼 쓰기에 딱 좋은 이유 = 앞/뒤 삽입, 삭제 O(1)
		q.add(new int[] {i, j}); // 큐에 삽입
		visited[i][j] = 1; // 삽입한 것의 인덱스 방문처리 1 : 기본값은 0이었음
		
		// 그림의 크기를 확인하는 변수
		int count = 1;
		
		int[] dx = {0, 0, 1, -1};
		int[] dy = {1, -1, 0, 0};
		
		// 큐가 비어질 때까지
		while(!q.isEmpty()) {
			int[] p = q.poll();
			int x = p[0];
			int y = p[1];
			
			// 4방 탐색
			for(int k = 0; k < 4; k++) {
				int nx = x + dx[k];
				int ny = y + dy[k];		
				
				// 범위 내에 있고, 방문하지 않으며, 그림이 있으면
				if (nx >= 0 && nx < n && ny >= 0 && ny < m && visited[nx][ny] == 0 && map[nx][ny] == 1) {
					visited[nx][ny] = 1;
					
					// 그림크기 키우고
					count++;
					
					// 다음 방문을 위해 큐에 넣는다.
					q.add(new int[] {nx, ny});
					
				}
			}
			
			// 현재 그림의 크기, MAX 비교 갱신
			
			max_ans = Math.max(count, max_ans);
		}	
	}
}

//논리구조 확인 암기. 


// 지유코드 모방. 
//package sub1;
//
//
//import java.io.*;
//import java.util.*;
//
//class Main {
//    static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
//    static StringTokenizer str;
//    static int n, m, ans = 0, max_ans = 0;
//    static int[][] paper;
//    static int[][] visited;
//
//// 디버깅용 visited 출력 함수
//static void print2darr() {
//    for (int i = 0 ; i < n ; i ++) {
//        for (int j = 0 ; j < m ;j++) {
//            System.out.print(visited[i][j]);
//        }
//        System.out.println("");
//    }
//    System.out.println("---");
//}
//
//
//// 들어온 애랑 이어져있는 그림의 크기를 확인하자
//static void bfs(int i, int j) {
//
//    // 일단 그림의 개수 하나 추가
//    ans++;
//    Queue<int[]> q = new LinkedList<>();
//    // 첫 시작점 큐에 넣어주기
//    q.add(new int[] {i, j});
//    visited[i][j] = 1;
//
//    // 그림의 크기를 확인하는 변수
//    int count = 1;
//
//    int[] dx = {0, 0, 1, -1};
//    int[] dy = {1, -1, 0, 0};
//
//    // 큐가 빌 때까지
//    while(!q.isEmpty()) {
//       int[] p = q.poll();
//       int x = p[0];
//       int y = p[1];
//        
//		 // 4방 탐색
//       for (int k = 0 ; k < 4; k++) {
//        int nx = x + dx[k];
//        int ny = y + dy[k];
//
//        // 범위 내에 있고, 방문하지 않았으며, 그림이 그려져 있으면
//        if (nx >= 0 && nx < n && ny >= 0 && ny < m && visited[nx][ny] == 0 && paper[nx][ny] == 1) {
//            // 방문하고
//            visited[nx][ny] = 1;
//
//            // 그림 크기 키우고
//            count++;
//
//            // 다음 방문을 위해 큐에 넣는다.
//            q.add(new int[] {nx, ny});
//        }
//       }
//
//    }
//
//    // 현재 그림의 크기랑 MAX값을 비교해서 갱신한다. FOR 가장 큰 그림. 
//    max_ans = Math.max(count, max_ans);
//
//    
//}
//
//public static void main(String[] args) throws IOException {
//    str = new StringTokenizer(buffer.readLine());
//    n = Integer.parseInt(str.nextToken());
//    m = Integer.parseInt(str.nextToken());
//    
//
//    paper = new int[n][m];
//    visited = new int[n][m];
//
//    for (int i = 0 ; i < n ; i++) {
//        str = new StringTokenizer(buffer.readLine());
//        for (int j = 0 ; j < m ; j++) {
//            paper[i][j] = Integer.parseInt(str.nextToken());
//        }
//    }
//
//    for (int i = 0 ; i < n ; i++) {
//        for (int j = 0 ; j < m; j++) {
//            // 만약 방문하지 않은 그림이고 그림이 그려져있으면
//            if (visited[i][j] == 0 && paper[i][j] == 1) {
//                // 연결 되어있는 영역을 확인한다.
//                bfs(i, j);
//
//            }
//        }
//    }
//
//    // 정답 출력
//    System.out.println(ans);
//    System.out.println(max_ans);
//}
//}
//
