package week10_02.sjh1108.BOJ_1931;
import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[][] arr = new int[N][2];
        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
        }

        // 회의가 먼저 끝나는 순으로 정렬
        // 끝나는 시간이 같다면 먼저 시작하는 순으로 정렬
        Arrays.sort(arr, (o1, o2) -> {
            if(o1[1] == o2[1]) return o1[0] - o2[0];
            return o1[1] - o2[1];
        });

        int count = 0;
        int endTime = 0;
        // 끝나는 순으로 정렬되었기 때문에 먼저 끝나는 회의부터 배정함
        for(int i = 0; i < N; i++){
            if(endTime <= arr[i][0]){
                endTime = arr[i][1];
                count++;
            }
        }

        System.out.println(count);
    }
}

/*
 * 증명 참고
 * https://lomuto.tistory.com/14
 * https://velog.io/@junttang/BOJ-1931-%ED%9A%8C%EC%9D%98%EC%8B%A4-%EB%B0%B0%EC%A0%95-%ED%95%B4%EA%B2%B0-%EC%A0%84%EB%9E%B5-C
 * https://st-lab.tistory.com/145
 * https://loosie.tistory.com/475
 */