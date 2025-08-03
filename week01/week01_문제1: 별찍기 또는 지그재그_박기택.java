import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st;

		char ch = br.readLine().charAt(0);

		// 1. 첫번쨰 구현.
		if (ch == '*') {

			// 한쪽에 공백으로 밀고, 그 옆에 별을 넣으면 좋을 듯.
			// for문 활용안 암기 + 다양케이스 확인 20개 이내 , fixed. 
			// 공백을 늘렸다고 왜 됨?
			for (int i = 0; i < 4; i++) {

				for (int j = 0; j < i ; j++) {
					System.out.print("  "); //와,, 파이썬이랑 다르네,, '*' + ' ' 이거 안됨. 
				}
				// System.out.println("*".repeat(i)); // java11이상에서 가능 repeat()
			System.out.println();
			}
		}





		// 2번째 구현
        if (ch == '1') {

			int[][] arr = new int[5][5];
			int n = 1;

			for (int i = 0; i < 5; i++) {

				if(i % 2 == 0) {
					for (int j = 0; j < 5; j++) {
					arr[i][j] = n;
					//System.out.printf("%-3d", arr[i][j]); // 안에 포멧팅 문자 주의 -%3d 이런거 노.
					n++;
				}//System.out.println();
			} else {
				for (int j = 4; j >= 0; j--) {
					arr[i][j] = n;
					//System.out.printf("%-3d", arr[i][j]); // 안에 포멧팅 문자 주의 -%3d 이런거 노.
					n++;
				}//System.out.println(); // 1행이 바로 출력되게 해서 순서대로 나옴

			}

			}




			//  세팅한 배열을 출력.
			for(int k = 0; k < 5; k++) {
				for (int l = 0; l < 5; l++) {
					System.out.printf("%-3d", arr[k][l]);
				} System.out.println();
			}









			// 숫자만으론 단순 나열은 되는데, 2열과 4열에서  반전정령을 꾀하기 힘들 것 같아서 치움. 
			// for (int i = 1; i <= 25; i++) {
			// 	System.out.printf("%-3d", i);
			// 	if (i % 5 == 0) {
			// 		System.out.println();
			// 	}
			// }





		}
	}
}














// 사실 배열까지 필요무 ㅜㅜ 
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;

// public class Test1_박기택 {

//     public static void main(String[] args) throws IOException {
//         try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
//             char ch = br.readLine().charAt(0);

//             if (ch == '*') {
//                 for (int i = 0; i < 4; i++) {
//                     for (int j = 0; j < i; j++) {
//                         System.out.print("  ");
//                     }
//                     System.out.println("*");
//                 }
//             }

//             if (ch == '1') {
//                 int n = 1;
//                 for (int i = 0; i < 5; i++) {
//                     if (i % 2 == 0) {
//                         for (int j = 0; j < 5; j++) {
//                             System.out.printf("%-3d", n++);
//                         }
//                     } else {
//                         for (int j = 4; j >= 0; j--) {
//                             System.out.printf("%-3d", n++);
//                         }
//                     }
//                     System.out.println();
//                 }
//             }
//         }
//     }
// }





































