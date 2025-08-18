import java.io.*;
import java.util.*;

public class Main_2605_줄세우기_박기택 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());          
        ArrayList<Integer> arr = new ArrayList<>();    

        StringTokenizer st = new StringTokenizer(br.readLine()); // N개의 숫자한 줄 입력

        // i번째 학생(번호 i+1)이 뽑은 번호 n만큼 앞으로 끼어들기
        for (int i = 0; i < N; i++) {
            int n = Integer.parseInt(st.nextToken()); 
            arr.add(i - n, i + 1);                 
        }

        // 출력
        StringBuilder sb = new StringBuilder();
        for (int x : arr) sb.append(x).append(' ');
        System.out.println(sb.toString().trim());
    }
}


// <기존코드>
// // 브론즈 문제인데, 쉽게 풀리지 않는다. 딱딱한 브론즈 ㄸ

// import java.util.*;
// // import java.util.ArrayList;
// import java.io.*;

// // ArrayList를 활용해서 푸는 문제임
// // ArrayList를 활용해서 푸는 문제임
// // ArrayList의 장점: 크기 자동 조절, 인덱스 접근 빠름, 다양한 메서드 제공
// // ArrayList 매소드: add(), get(), set(), remove(), size(), contains(), clear()



// //static int N; <- 실수 조심 Main안에는 넣기.. 하하 ㅠ

// // The main method must be in a class named "Main".
// class Main {


//     static int N;
    
//     public static void main(String[] args) throws IOException {
//         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//         N = Integer.parseInt(br.readLine());
        
//         ArrayList<Integer> arr = new ArrayList<>(); // 선언시 뒤에 조심. 
//         //ArrayList는 제네릭 타입으로 반드시 참조형(Integer)만 쓸 수 있습니다. 원시 타입(int)은 불가능합니다.

//         StringTokenizer st;
//         // 학생 수만큼 입력받기. 
//         for (int i = 0; i < 5; i++) {
//             st = new StringTokenizer(br.readLine());
//             arr.add(i-N, i+1); // 인덱스번호, 들어갈 값
//         }



//         // 출력하기. 
//         for (int i : arr) {
//             System.out.print(i + " ");
//         }


//<기존코드2>
//         // 풀다가 잘못해서 일단 맘에 안들어서 던져버림. 
//         // StringTokenizer st;
//         // while(st.hasMoreTokens()) {
//         //     arr.add(st.nextToken());
//         // }
//         //     //arr.add(new StringTokenizer(br.readLine())); 불가함. 
//         //     //readLine()은 문자열이라서, 보통 StringTokenizer로 쪼개서 값 추출 후 리스트에 넣어야 함
//         //     //while (st.hasMoreTokens()) : hasMoreTokens() = "다음 단어 있냐?"
//         //     //arr.add(br.readLine()); // 줄 단위로 저장
//         // // [0, 1, 1, 3, 2]
//         // for (int i = 0; i < 5; i++) {
//         // }

     
//     }
// }