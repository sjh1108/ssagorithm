package study1207;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

// 단어정렬, 실버5
public class Main_1181 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        
        int N = Integer.parseInt(br.readLine());
        String[] arr = new String[N];

        for (int i = 0; i < N; i++) {
            arr[i] = br.readLine();
        }

        
        Arrays.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                // 길이가 같으면 사전 순 정렬
                if (s1.length() == s2.length()) {
                    return s1.compareTo(s2);
                }
                // 그 외에는 길이 순 정렬
                else {
                    return s1.length() - s2.length();
                }
            }
        });

        StringBuilder sb = new StringBuilder();
        
        
        // 첫 단어는 기본으로 저장(중복일 일이 없음)
        sb.append(arr[0]).append('\n');
        
        for (int i = 1; i < N; i++) { // 1부터 N-1까지만 점검하면 됨. 
            // 정렬되어 있으니, 바로 앞의 단어와 다를 경우에만 저장 -> 중복 제거
            if (!arr[i].equals(arr[i - 1])) { // 뒤단어 앞단어
                sb.append(arr[i]).append('\n');
            }
        }
        
        System.out.println(sb);
    }
}

