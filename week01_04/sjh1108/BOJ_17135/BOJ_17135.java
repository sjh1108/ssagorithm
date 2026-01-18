package week01_04.sjh1108.BOJ_17135;

import java.io.*;
import java.util.*;

class Main {
    private static final int INF = 1024;

    private static int N, M, D;
    private static int ans;

    private static int[][] map, mep;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        map = new int[N+1][M];
        mep = new int[N+1][M];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            
            for(int j = 0; j < M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        comb(0, M, 3, new ArrayList<>());

        System.out.println(ans);
    }

    private static void comb(int start, int cnt, int r, List<Integer> list){
        if(r == 0){
            init();

            attack(list);

            return;
        }

        for(int i = start; i < cnt; i++){
            list.add(i);

            comb(i+1, cnt, r-1, list);

            list.remove(list.size()-1);
        }
    }

    private static void init(){
        for(int i = 0; i < N; i++){
            mep[i] = Arrays.copyOf(map[i], M);
        }
    }

    private static void attack(List<Integer> list){
        int res = 0;

        for(int turn = 0; turn < N; turn++){
            boolean[][] visited = new boolean[N][M];

            for(int k = 0; k < list.size(); k++){
                int location = list.get(k);

                int minD = INF;
                int minX = INF;
                int minY = INF;

                for(int i = 0; i < N; i++){
                    for(int j = 0; j < M; j++){
                        if(mep[i][j] == 1){
                            int d = getDistance(i, j, N, location);
                            if(minD >= d){
                                
                                if(minD > d){
                                    minD = d;
                                    minX = i;
                                    minY = j;
                                } else if(minY > j){
                                    minX = i;
                                    minY = j;
                                }
                            }
                        }
                    }
                }

                if(minD <= D){
                    visited[minX][minY] = true;
                }
            }

            for(int i = 0; i < N; i++){
                for(int j = 0; j < M; j++){
                    if(visited[i][j]) {
                        mep[i][j] = 0;
                        res++;
                    }
                }
            }

            for(int i = N-1; i > 0; i--){
                for(int j = 0; j < M; j++){
                    mep[i][j] = mep[i-1][j];
                }
            }
            Arrays.fill(mep[0], 0);
        }

        ans = Math.max(ans, res);
    }

    private static int getDistance(int r1, int c1, int r2, int c2){
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);   
    }
}