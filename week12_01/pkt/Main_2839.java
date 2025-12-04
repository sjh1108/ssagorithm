package week12_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//설탕 배달 실버4
public class Main_2839 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        int count = 0; // 봉지 개수

        while (true) {
            // 먼저 5로 딱 나누어 떨어지면, 몫을 더하고 끝 .. 원하는 값 나올 떄까지 무한 반복할거기 때문에 count가 바뀜. 
            if (N % 5 == 0) {
                System.out.println(N / 5 + count);
                break;
            }
      
            // 안 나눠 떨어짐. 
            else if (N < 0) {
                System.out.println(-1);
                break;
            }
            
            N -= 3; // 3 빼주기. 앞에 5로 해주고, 여기서 3으로 빼주는데 안나눠 떨어지면 -1 튀어 나옴. 
            count++;
        }
    }
}


// 의문: 왜 N / 3을 바로 사용하지 않는가?
// 만약 5로 나누어떨어지지 않을 때 남은 $N$을 무조건 N / 3로 처리한다면, 최소 봉지 개수라는 조건이 깨짐


//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;

// worst code: 아래는 또 다른 풀이법을 봤는데, 직관적이지 않은 듯해서 워스트가 아닌가 하는 개인적인 생각. 

//public class Main {
//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        int N = Integer.parseInt(br.readLine());
//
//        // 예외 처리: 4와 7은 3과 5로 만들 수 없는 유일한 숫자들
//        if (N == 4 || N == 7) {
//            System.out.println(-1);
//            return;
//        }
//
//        // 5로 나눈 몫과 나머지
//        int quotient = N / 5;
//        int remainder = N % 5;
//
//        // 나머지에 따른 케이스 분류
//        if (remainder == 0) {
//            System.out.println(quotient);
//        } 
//        else if (remainder == 1 || remainder == 3) {
//            // 나머지가 1이거나 3이면 봉지가 1개 더 필요함
//            System.out.println(quotient + 1);
//        } 
//        else if (remainder == 2 || remainder == 4) {
//            // 나머지가 2이거나 4면 봉지가 2개 더 필요함
//            System.out.println(quotient + 2);
//        }
//    }
//}

