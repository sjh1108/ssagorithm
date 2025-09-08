package month08.week04.jangsun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//0보다 크거나 같고, 99보다 작거나 같은 
//정수가 주어질 때 다음과 같은 연산을 할 수 있다. 
//먼저 주어진 수가 10보다 작다면 앞에 0을 붙여 두 자리 수로 만들고, 
//각 자리의 숫자를 더한다. 
//그 다음, 주어진 수의 가장 오른쪽 자리 수와 
//앞에서 구한 합의 가장 오른쪽 자리 수를 이어 붙이면 새로운 수를 만들 수 있다. 다음 예를 보자.
//
//26부터 시작한다. 2+6 = 8이다. 새로운 수는 68이다.
//6+8 = 14이다. 새로운 수는 84이다. 
//8+4 = 12이다. 새로운 수는 42이다. 
//4+2 = 6이다. 새로운 수는 26이다.
//
//위의 예는 4번만에 원래 수로 돌아올 수 있다. 따라서 26의 사이클의 길이는 4이다.
//
//N이 주어졌을 때, N의 사이클의 길이를 구하는 프로그램을 작성하시오.

public class BOJ1110_더하기사이클 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int num=Integer.parseInt(br.readLine());
		int count =0;
		int result =num;
		
		while(true) {
			count++;
			int numOne = num%10;
			int numTenth = num/10;
			
			num = (numOne*10)+(numOne+numTenth)%10;
			
			if(num == result) {
				break;
			}
			
		}
		System.out.println(count);
		
		

//		System.out.println("numten : " + numTenth);

//		System.out.println("numone: " + numOne);
//		int count =0;
//		int firstNum =num;
//		
//		while(true) {
////			System.out.println("while start");
//			int numOne = num%10;
//			int numTenth = num/10;
//			if(num<10) {
//				num = numOne*10;
//				count++;
//				System.out.println("num<10 : " + num);
//			}else {
//				int newNumOne = (numOne+numTenth)%10;
//				num = numOne*10+newNumOne;
//				count++;
//				System.out.println("num >10 :" + num);
//			}
//			System.out.println("count : " + count);
//			if(firstNum ==num) {
//				System.out.println("같다");
//				break;
//			}
//		}
//		System.out.println("final count" + count);
		
//		System.out.println("numlength : " + num.length());
//		char[] numArr = new char[2];
//		for(int i=0; i<num.length(); i++) {
//			numArr[i] = num.charAt(i);
//		}
//		for(int i=0; i<num.length(); i++) {
//			System.out.print(numArr[i]);
//			System.out.println();
//			System.out.println("i : " + i);
//		}
//		int count =0;
//		int result =0;
//		System.out.println(firstNum);
////		System.out.println(numArr[0]);
////		while(firstNum==result)
//		if(numArr.length==1) {
//			int num0 = numArr[0]-'0';
//			result = num0*10;
//			numArr = new char[numArr.length+1];
//			for(int i=0; i<num.length(); i++) {
//				numArr[i] = num.charAt(i);
//			}
//			for(int i=0; i<num.length(); i++) {
//				System.out.print("while 안에 출력 :" +numArr[i]);
//			}
//			count++;
//		}else {
//			int num0 = numArr[0]-'0';
//			int num1 = numArr[1]='0';
//			int newNum = (num0+num1)%10;
//			result = num1*10+newNum;
//		}
		
		
	}

}
