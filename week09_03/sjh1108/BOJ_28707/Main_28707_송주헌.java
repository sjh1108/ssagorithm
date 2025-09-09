package week09_03.sjh1108.BOJ_28707;

import java.io.*;
import java.util.*;

/*
 * 길이가 $N$인 양의 정수로 이루어진 배열 $A$가 주어집니다. 
 * 
 * 이 배열을 비내림차순, 즉 정렬하기 위해서
 * 다음과 같은 $M$가지 조작을
 * 순서와 횟수에 상관 없이 원하는 만큼 할 수 있습니다.
 * 
 * $A$의 $l_i$번째 수와 $r_i$번째 수를 바꿉니다.
 * 비용은 $c_i$가 듭니다. (1 <= i <= M)
 * 
 * $A$를 비내림차순으로 정렬하기 위해 필요한 비용 총합의 최솟값을 출력하세요.
 */

/*
 * 풀이
 * 1. BFS + 다익스트라
 *  - 배열을 문자열로 변환하여 HashMap에 저장
 *  - 명령어를 수행한 결과 배열이 HashMap에 없다면 추가하고 Queue에 넣음
 *      - 이미 있다면 기존 비용과 새로 계산된 비용 비교
 *      - 새로 계산된 비용이 더 작다면 갱신하고 Queue에 넣음
 */

public class Main_28707_송주헌{
    private static HashMap<String, Integer> map = new HashMap<>();
    private static List<Command> commands = new ArrayList<>();

    private static class Command{
        int x, y, cost;

        public Command(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        // 명령어 실행 메서드
        // 두 인덱스의 값을 바꾸는 메서드
        public int[] execute(int[] arr){
            int temp = arr[x];
            arr[x] = arr[y];
            arr[y] = temp;
            return arr;
        }
    }

    private static String arrayToString(int[] arr){
        StringBuilder sb = new StringBuilder();
        for(int num : arr){
            sb.append(num);
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 입력 파트
        // 원본 배열과 비내림차순(정렬된) 배열 생성
        int[] original = new int[N];
        int[] answer = new int[N];
        for (int i = 0; i < N; i++) {
            answer[i] = original[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(answer);

        // 원본 배열의 비용은 0
        map.put(arrayToString(original), 0);

        // 명령어 입력 파트
        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int cost = Integer.parseInt(st.nextToken());
            commands.add(new Command(x, y, cost));
        }
        Collections.sort(commands, Comparator.comparingInt(o -> o.cost));

        // 원본 배열부터 Queue에 넣고 시작
        Queue<int[]> q = new ArrayDeque<>();
        q.add(original);

        while(!q.isEmpty()){
            // Queue에서 배열 하나 꺼내기
            int[] cur = q.poll();

            // 모든 명령어에 대해 수행
            for(Command cmd: commands){
                // 명령어 수행 결과 배열 nxt
                int[] nxt = cmd.execute(cur.clone());

                // 수행 결과값을 Key로 변환함
                String key = arrayToString(nxt);

                // 해쉬맵에 Key값이 없다면 map에 추가하고 Queue에 넣음
                if(!map.containsKey(key)){
                    map.put(key, map.get(arrayToString(cur)) + cmd.cost);
                    q.add(nxt);
                } 
                // 이미 Key값이 있다면, 기존 비용과 새로 계산된 비용 비교
                // 새로 계산된 비용이 더 작다면 갱신하고 Queue에 넣음
                else{
                    int newCost = map.get(arrayToString(cur)) + cmd.cost;
                    if(map.get(key) > newCost){
                        map.put(key, newCost);
                        q.add(nxt);
                    }
                }
            }
        }

        // 정렬된 배열의 Key값으로 비용 출력
        String ansKey = arrayToString(answer);
        // 없다면 -1 출력
        System.out.println(map.getOrDefault(ansKey, -1));
    }
}