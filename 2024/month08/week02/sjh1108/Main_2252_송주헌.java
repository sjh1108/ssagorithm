package month08.week02.sjh1108;
import java.io.*;
import java.util.*;

public class Main_2252_송주헌 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<List<Integer>> list = new ArrayList<>(N+1);

        // list 초기화
        for(int i = 0; i <= N; i++){
            list.add(new ArrayList<>());
        }
        
        // 우선순위 입력
        while(M-- > 0){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            list.get(a).add(b);
        }

        // 방문한 노드
        boolean[] visited = new boolean[N+1];

        // 줄 세우기
        int[] line = new int[N];
        int cnt = 0;
        for(int i = 1; i <= N; i++){
            if(list.get(i).size() == 0){
                line[cnt++] = i;
                visited[i] = true;
            }
        }

        // list에 노드가 없을때까지
        while(cnt < N){
            
            for(int i = 1; i <= N; i++){
                if(visited[i]) continue;

                List<Integer> currentList = list.get(i);
                int size = currentList.size();
                if(size == 0){
                    visited[i] = true;
                    line[cnt++] = i;
                    continue;
                }

                if(size == 1){
                    if(visited[currentList.get(0)]){
                        line[cnt++] = i;
                        visited[i] = true;
                        currentList.remove(0);
                    }
                } else{
                    for(int j = 0; j < size; j++){
                        if(visited[currentList.get(j)]){
                            currentList.remove(j--);
                            --size;
                        }
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < N; i++){
            int x = line[--cnt];
            sb.append(x).append(" ");
        }

        System.out.println(sb);
    }
}