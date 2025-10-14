package practice;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_15651 {
	
	static int N, M;
    static int[] arr;
    static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[M];

		dfs(0);
		System.out.print(sb);
		
		
	}

	private static void dfs(int depth) {
		
		if(depth == M) { // 걸려서 출력하는 거 만들면 됨. arr에서 가져와서.
			for (int num : arr) {
				sb.append(num).append(" ");
			}
			sb.append("\n");
			return;
		}
		
		// 어차피 그냥 하면 오름차순되고, 중복허용 안하기 위해 i=1부터 시작하고, 계속 올라감. 리턴되면 다시 그 전껄로 가고.
		// 15652랑 다른 부분 i=1부터인지, 아니면 start인지, 그냥 return 후에 어디로 다시 하위호출되는지인데. 하위호출 생각하면 화가 남.
		// 그냥 N과 M시리즈 밀고, 어느 꼴에서 어떻게 바뀌나 DFS 좀 암기해야겠음.
		for (int i = 1; i <= N; i++) {
			arr[depth] = i;
			dfs(depth + 1);
		}
	}
}
