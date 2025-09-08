package month08.week04.pkt;



 import java.io.*;
 import java.util.*;

 public class Main_11724_연결요소의개수_박기택_{
	
 	// 무방향
 	// 연결 요소의 개수 구하기
	
 	static int N;
 	static int M;
 	static ArrayList<Integer>[] A;
 	static boolean[] visited;
	
 	public static void main(String[] args) throws IOException{
		
 		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 		StringTokenizer st = new StringTokenizer(br.readLine());
		
 		N = Integer.parseInt(st.nextToken().trim());
 		M = Integer.parseInt(st.nextToken().trim());

 		A =  new ArrayList[N+1];
 		visited = new boolean[N+1]; 
				
 		for (int i = 1; i < N+1; i++) {
 			A[i] = new ArrayList<>();
 		}
		
 		// 간선 입력
 		for (int i = 1; i < M+1; i++) {
 			st = new StringTokenizer(br.readLine());
 			int s = Integer.parseInt(st.nextToken());
 			int e = Integer.parseInt(st.nextToken());
 			A[s].add(e); // 입력받은 순서대로 입력됨. 각 자리 인덱스에 맞게 배열[인덱스].add(값);
 			A[e].add(s); // 양쪽방향에 맞게
 		}
		
 		// 방문
 		int count = 0;
 		for(int i = 1; i < N+1; i++) {
 			if(!visited[i]) {
 				count++;
 				DFS(i);
 			}			
			
 		} System.out.println(count); // 결과값 출력 (← 원래 코드 유지)
 	}
	
 	// FIX 1: main 밖에 DFS 배치
 	static void DFS(int v) {
 		if(visited[v]) {
 			return;
 		}
 		visited[v] = true;
 		for(int i : A[v]) {
 			// FIX 2: v → i
 			if(!visited[i]) {
 				DFS(i);
 			}
 		}
 	}
 }































//
//import java.io.*;
//import java.util.*;
//
//
//
//    public class Main11724{
//
//        static int N;
//        static int M;
//        static ArrayList<Integer> arr;
//        static boolean[] visited;
//        static int count;
//
//        
//
//
//
//        public static void main(String[] args) throws IOException {
//
//            // 일단 적고나서 범위 잘못됐다고 생각되면 저 위 static 변수 옮기면 됨
//            // ki 재료ㅣ for문, if문이면 충분하다고 생각하기
//            // 정점의 수, 간선의 수
//            // 인접그래프, 방문 배열 카운트 활용<- 기억하는게 중요함
//            // 인접그래프를 입력받고,
//            // 방문처리 생각
//            // 최종적으로 연결된 그래프끼리 알려줘 몇 개인지?
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//            StringTokenizer st;
//
//            st = new StringTokenizer(br.readLine());
//            N = Integer.parseInt(st.nextToken().trim());
//            M = Integer.parseInt(st.nextToken().trim());
//
//            
//
//
//            arr =  new ArrayList[N+1]; // 배열 크기 미리 만들어두기. 아무리 ArrayList라도,,
//            // 간선 입력 전에 공허한 배열 만들어 두기
//            for(int i =1; i < N+1; i++) {
//                arr[i] = new ArrayList<>();
//            }
//
//            for (int i = 1; i < N+1; i++) {
//                st = new StringTokenizer(br.readLine());
//                int s = Integer.parseInt(st.nextToken().trim());
//                int e = Integer.parseInt(st.nextToken().trim());
//                arr[s].add(e);
//                arr[e].add(s);
//                // arr[i].add(s); <- 휴,, 오답.
//            }
//
//
//            count = 0;
//            visited = new boolean[N+1];
//            for (int i = 1; i = N+1; i++) {
//                if (!visited[i]) {
//                    count++;
//                    visited[i] = true;
//                    bfs(i);
//                }
//
//            }
//
//            System.out.println(count);
//
//        }
//
//
//
//        public static void bfs(int v) {
//            if()
//        }
//
//        // 와 ,, 진짜,, 알아도 짜기 힘드네 
//        // 자기 전 자고나서 여러번 또하고, 계속하고, 해야겠다
//        // 동시적으로 짜기 한번씩 점검.. 언제까지? 될때까지
//        // 우선 코드 이해가 최우선. 
//
//
//
//
//
//
//
//
//    
//
//}
//
//
//
//
//
//
//















































// import java.io.*;
// import java.util.*;

// public class Main11724{
	
// 	// 무방향
// 	// 연결 요소의 개수 구하기
	
// 	static int N;
// 	static int M;
// 	static ArrayList<Integer>[] A;
// 	static boolean[] visited;
	
// 	public static void main(String[] args) throws IOException{
// 		// TODO Auto-generated method stub
		
// 		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
// 		StringTokenizer st = new StringTokenizer(br.readLine());
		
// 		N = Integer.parseInt(st.nextToken().trim());
// 		M = Integer.parseInt(st.nextToken().trim());

// 		A =  new ArrayList[N+1];
// 		visited = new boolean[N+1]; 
				
// 		for (int i = 1; i < N+1; i++) {
// 			A[i] = new ArrayList<>();
// 		}
		
// 		// A[1] = [];
// 		// A[2] = [];
// 		// A[3] = []; ...
		
// 		//값을 추가할 예정 .. ki 3번 정도 
// 		for (int i = 1; i < M+1; i++) { // 간선 추가
// 			st = new StringTokenizer(br.readLine());
// 			int s = Integer.parseInt(st.nextToken());
// 			int e = Integer.parseInt(st.nextToken());
// 			A[s].add(e);
// 			A[e].add(s);
// 		}
// 		// A[1] = 2, A[2] = 1 이런식으로 감.
// 		// A[1] = {2, 5}
		
// 		// 방문
// 		int count = 0;
// 		for(int i = 1; i < N+1; i++) {
// 			// 여기에 DFS 구동 설치 + count
// 			if(!visited[i]) {
// 				count++;
// 				DFS(i);
// 			}			
// 			System.out.println(count); // 결과값 출력
// 		}
		
		
		
// 			}
			
			
// 		}

//         static void DFS(int v) {// 방문처리 기능 하나,, ki 기능이 차후 하나씩 추가되서 어려운거구나. 
// 			if(visited[v]) {
// 				return;
// 			}
// 			visited[v] = true;
// 			for(int i : A[v]) {
// 				if(i == false) {
// 					DFS(v);
// 				}
		

		
		
		
		
		
// 	}

// }




