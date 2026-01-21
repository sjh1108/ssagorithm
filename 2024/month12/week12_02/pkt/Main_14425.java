package study1207;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

//문자열 집합_실버4 Main_14425
// 해시셋 사용 강점: 시간복잡도가 O(1)이고, 중복을 제거해줌. <- 해시 알고리즘 덕분. 
// ArrayList였다면 리스트 전체를 다 뒤져야 해서 O(N)
public class Main_14425 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        // 대조용 문자열 저장용 틀
        Set<String> stringSet = new HashSet<>();

        // 대조용 문자열 저장
        for (int i = 0; i < N; i++) {
            stringSet.add(br.readLine());
        }

        int count = 0;
       
        // 대조용 문장열 틀에 있는지 확인하고, 있으면 하나씩 더해줌. 
        for (int i = 0; i < M; i++) {
            String str = br.readLine();
            if (stringSet.contains(str)) {
                count++;
            }
        }

        System.out.println(count);
    }
}

