import java.util.*;
import java.io.*;

public class 관측소_통신망_개통 {
    static int N;
    static int M;
    static int[] parent;

    // 전부 연결되었는지 확인
    public static boolean isAllConnected(){
        int first = find(1);

        for(int i = 2; i <= N; i++){
            if(find(i) != first) {
                return false;
            }
        }

        return true;
    }

    // 유니온 파인드
    public static void union(int v1, int v2){
        int v1root = find(v1);
        int v2root = find(v2);

        if(v1root != v2root){
            parent[v2root] = v1root;
        }

        return;
    }

    public static int find(int v){
        if(parent[v] == v){
            return v;
        }
        else{
            return parent[v] = find(parent[v]);
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        parent = new int[N+1];
        for(int i = 1; i <= N; i++){
            parent[i] = i;
            // System.out.println(i + "parent " + parent[i]);
        }

        int result = -1;

        // 케이블 입력
        for(int i = 1; i <= M; i ++){
            StringTokenizer line = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(line.nextToken());
            int v2 = Integer.parseInt(line.nextToken());

            union(v1, v2);

            if(isAllConnected()){
                result = i;
                break;
            }
            
        }
        System.out.println(result);
    }
}
