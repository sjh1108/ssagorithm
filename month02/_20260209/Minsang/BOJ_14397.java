package algorithm;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 구하고자 하는 것 : 바다, 땅이 맞닿아 있는 변 개수 구하기
// . : 바다, # : 땅

class Main_14397 {
	static int N, M;
	// 이동방향 6가지
	static int[] dx = {-1, -1, 0, 0, 1, 1};
    static int[] dyEven = {-1, 0, -1, 1, -1, 0}; // 행이 짝수일 때
    static int[] dyOdd = {0, 1, -1, 1, 0, 1}; // 행이 홀수일 때
    
    static char[][] arr;
    static int length = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new char[N][M];

        // 2차원 배열에 문자열 저장
        for(int i = 0; i < N; i++){
            String s = br.readLine();
            for(int j = 0; j < M; j++){
                arr[i][j]=s.charAt(j);
            }
        }

        // 바다 : .
        for(int i = 0; i < N; i++){
            for(int j = 0;j < M; j++){
                if(arr[i][j] == '.'){
                    beachLength(i, j, N, M);
                }
            }
        }

        System.out.println(length);
    }

    public static void beachLength(int x, int y, int N, int M){
    	
    	// y 이동이 다르기 때문에 필요함
    	// 현재 행이 짝수인지, 홀수인지 판별
        int[] dy;
        if(x % 2 == 0) {
        	dy = dyEven; 
        } else {
        	dy = dyOdd;
        }
        
        for(int i = 0; i < 6; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < N && ny >= 0 && ny < M && arr[nx][ny] == '#') {
                length++;
            }
        }
    }
}