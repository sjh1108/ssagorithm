import java.io.*;
import java.util.*;
/*
동시 확산 
마나 확산 배열을 따로 만들어서
각 초마다 계산 한 후에 교체하면 될거 같음
*/
public class 마나_확산 {

    static int N, M, T;
    static long[][] map;

    static int[][] dt = {{-1,0}, {0,1}, {1,0}, {0,-1}}; // 상, 우, 하, 좌
    public static void spread(){
        // 확산 결과 저장 배열
        long[][] next = new long[N][M];
        
        for(int r = 0; r < N; r++){
            for(int c = 0; c < M; c++){
                long v = map[r][c];

                // 확산할 마나 양
                long q = v / 5;
                int cnt = 0;

                // 인접 확산
                for(int d = 0; d < 4; d++){
                    int nr = r + dt[d][0];
                    int nc = c + dt[d][1];
                    
                    // 밖이면 확산 x
                    if(nr < 0 || nc < 0 || nr >= N || nc >= M) continue;

                    next[nr][nc] += q;
                    cnt++;
                }

                // 남는 마나
                next[r][c] += v - q * cnt; // 다른곳에서 마나 들어올수도 있으니까 += 으로 해줘야함
            }
        }
        // 1초 확산 종료
        map = next;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        map = new long[N][M];

        for(int i = 0; i < N; i ++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                map[i][j] = Long.parseLong(st.nextToken());
            }
        }

        // T초 동안 확산
        for(int i = 0; i < T; i++){
            spread();
        }

        // 스트링 빌드 씁니다 ㅎㅎ;;
        StringBuilder sb = new StringBuilder();
        for(int r = 0; r < N; r++){
            for(int c = 0; c < M; c++){
                sb.append(map[r][c]);
                if(c != M - 1){
                    sb.append(' ');
                }
            }
            sb.append('\n');
        }

        System.out.print(sb);
        
    }
}
