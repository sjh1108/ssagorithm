public class Main_15649_N과 M(1)_박기택 {
    static int N, M; // 1부터 N까지 자연수 중에서 중복 없이 M개를 고른 수열 3 1 -> 1 2 3
	static int[] arr; //  현재 뽑은 수를 저장
	static boolean[] visited; // 방문처리
	static StringBuilder sb = new StringBuilder(); // 차후 공간을 위해서 
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		// 이 다음 코드? 배열들 저장, 백트래킹 호출 설정, sb출력(자동 toString)
		arr = new int[M]; // 0부터 M-1까지
		visited = new boolean[N+1]; // 0부터 N까지이지만, 우리는 1부터 N까지만 
		
		backtrack(0); // 0번째 자리부터 시작 
		
		System.out.print(sb); // 출력 가능

	}
	
	
	// backtrack 매서드 활용
	static void backtrack(int depth) { // 0
		
		// if문 
		if (depth == M) { // 4 2 ,,, M = 2
		// 만약 M과 depth가 같다면 리턴
			// for문 sb활용해서 더함
            for (int i = 0; i < M; i++) {               
                sb.append(arr[i]);                      
                if (i < M - 1) sb.append(' ');         // 마지막엔 공백 X
            }
            sb.append("\n");
            return;// 리턴. 
		}
		
		// for문: 방문 처리
		for (int i = 1; i <= N; i++) { // N = 4 ,, 이 값 이용해서 1 2 / 1 3 이렇게 만듦
			if(!visited[i]) {
				visited[i] = true;
			//방문
            // 애초에 true면 실행금지
			
			// 현재 자기에 i저장
			arr[depth] = i;
			
			// 다음 자리 뽑으러 이동
			backtrack(depth + 1);
			
			// 돌아와서 사용 표시 해제 (백트래킹)
			visited[i] = false;
            } //if(!visited[i])꺼 ..  
            // 배열 arr 0부터 M-1까지
            // 배열 visited 0부터 N까지이지만, 우리는 1부터 N까지만 쓸거임 
            // 값 depth  0 1 2 .. M까지 
            // 그 전꺼 저장되어 있는 상태로 1 2, 1 3 나와야 함.
		}
	}

}
