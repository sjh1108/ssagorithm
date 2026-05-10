package study;
import java.io.*;
import java.util.*;

public class oj_3_김회인 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());
        
        int[][] A = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        int layers = Math.min(N, M) / 2;
        
        for (int layer = 0; layer < layers; layer++) {
            // 현재 layer의 테두리 좌표를 시계방향으로 모음
            List<int[]> coords = new ArrayList<>();
            
            // 위쪽 행 (왼→오)
            for (int j = layer; j < M - layer; j++) {
                coords.add(new int[]{layer, j});
            }
            // 오른쪽 열 (위→아래), 모서리 제외
            for (int i = layer + 1; i < N - layer; i++) {
                coords.add(new int[]{i, M - 1 - layer});
            }
            // 아래쪽 행 (오→왼)
            for (int j = M - 2 - layer; j >= layer; j--) {
                coords.add(new int[]{N - 1 - layer, j});
            }
            // 왼쪽 열 (아래→위), 모서리 제외
            for (int i = N - 2 - layer; i > layer; i--) {
                coords.add(new int[]{i, layer});
            }
            
            int length = coords.size();
            
            // 현재 값들 추출
            int[] values = new int[length];
            for (int idx = 0; idx < length; idx++) {
                int[] pos = coords.get(idx);
                values[idx] = A[pos[0]][pos[1]];
            }
            
            // 반시계 방향 R번 회전
            // 시계방향으로 펼쳤으니 values[rotate]가 0번 자리로 와야 함
            int rotate = R % length;
            int[] rotated = new int[length];
            for (int idx = 0; idx < length; idx++) {
                rotated[idx] = values[(idx + rotate) % length];
            }
            
            // 다시 배열에 넣기
            for (int idx = 0; idx < length; idx++) {
                int[] pos = coords.get(idx);
                A[pos[0]][pos[1]] = rotated[idx];
            }
        }
        
        // 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(A[i][j]);
                if (j < M - 1) sb.append(' ');
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}