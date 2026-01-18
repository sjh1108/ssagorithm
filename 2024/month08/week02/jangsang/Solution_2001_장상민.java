package month08.week02.jangsang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_2001_���� {
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine()); // testcase
		
		for(int test_case = 1; test_case <= T; test_case++)
		{ 	
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int N = Integer.parseInt(st.nextToken()); // �迭ũ��
			int M = Integer.parseInt(st.nextToken()); // �ĸ�ä ũ��
			
			int[][] fly = new int [N][N]; // �Է¹��� �迭
			
			// �迭�Է�
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                	fly[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            
            int maxFlys = 0;
            
            // �ĸ�ä ũ�⸸ŭ ���鼭 �հ� ���ϱ�
            for (int i = 0; i <= N-M; i++) {
				for (int j = 0; j <= N-M; j++) {
					int sum = 0;
                    for (int x = 0; x < M; x++) {
                        for (int y = 0; y < M; y++) {
                            sum += fly[i + x][j + y];
                        }
                    }
                    //Max�� ����
                    maxFlys = Math.max(maxFlys, sum);
				}
			}
            System.out.println("#" + test_case + " " + maxFlys);
			
			
		}
	}
}
