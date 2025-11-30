package week12_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_10989 {
    public static void main(String[] args) throws IOException {
        // memory issue so we must not use Arrays.sort. memory explode.
    	// 메모리 이슈, 함수 사용 노
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        int[] cnt = new int[10001];

        // num is index number  = sequence. for sorting contain 
        // num은 (정렬할) 입력 숫자를 나타내며, cnt 배열의 인덱스로 사용됩니다.
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(br.readLine());
            cnt[num]++;
        }

       
        for (int i = 1; i < 10001; i++) {
            // i means num =  if we have same i ex. 3 3 3
        	//i는 실제 숫자를 의미합니다. 예를 들어 i=3일 때, 3이 몇 번 나왔는지 확인합니다.
            while (cnt[i] > 0) {
                sb.append(i).append('\n');
                cnt[i]--; // you have to print same i whenever ending same i not exists in input txt
                // 해당 숫자를 한 번 출력했으므로 카운트를 1 감소시킵니다.
                // cnt[i]가 0이 될 때까지 반복하여 중복된 숫자도 모두 출력합니다.
            }
        }

        System.out.print(sb); // for memory 
    }
}

// 아이패드로 푸는 이슈로 인해 한글이 깨져서 되도 않는 영어 주석을 달음. 죄송합니다.  
// 메모리 크기 때문에 Arrays.sort 안됨. 그래서 구현 방안을 떠올려야 했던 문제. 
