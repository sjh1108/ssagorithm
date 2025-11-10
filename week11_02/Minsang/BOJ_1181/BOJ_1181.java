package week11_02.Minsang.BOJ_1181;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

class BOJ_1181 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        String[] arr = new String[N];

        for(int i = 0; i < N; i++) {
            arr[i] = br.readLine();
        }

        // 길이 순으로 정렬, 길이가 같으면 사전 순으로 정렬
        Arrays.sort(arr, new Comparator<String>(){
            @Override
            public int compare(String o1, String o2){
                // 길이가 같다면 사전 순으로 정렬
                if(o1.length() == o2.length()){
                    return o1.compareTo(o2);
                }
                // 길이 순서대로 정렬
                return o1.length() - o2.length();
            }
        });

        System.out.println(arr[0]);
        for(int i = 1; i < N; i++){
            // 중복된 단어는 출력하지 않음
            if(arr[i].equals(arr[i-1])){
                continue;
            } else {
                System.out.println(arr[i]);
            }
        }
    }
    
}
