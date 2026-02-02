package algostudy.baek.week02_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_2064_이용호 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// 옥텟 -> 8비트 4개 -> 32비트 int로 저장
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		
		int[] ips = new int[n]; 
		for(int i = 0; i < n; i++) {
			String x = br.readLine();
			ips[i] = ipToInt(x);
		}
		
		// 비교했을 때 달라지는 비트들 OR로 누적
		int first = ips[0];
		 
		int diff = 0;
		for(int i = 1; i < n; i++) {
			diff |= (first ^ ips[i]);
		}
		
		int mask;
		if(diff == 0){ // 모두 완전히 동일
			mask = 0xFFFFFFFF;
		}else {
			 // diff의 최상위 1비트 위치 찾기
			int msb = 31 - Integer.numberOfLeadingZeros(diff);
			int k = msb + 1;  // 하위 k비트 0
			mask = (int) (0xFFFFFFFFL << k); // 상위는 1
		}
		int network = first & mask;

        System.out.println(intToIp(network));
        System.out.println(intToIp(mask));

	}
	static int ipToInt(String s) {
        String[] p = s.split("\\."); // '.'은 정규식에서 특수문자라서 \\.
        int a = Integer.parseInt(p[0]);
        int b = Integer.parseInt(p[1]);
        int c = Integer.parseInt(p[2]);
        int d = Integer.parseInt(p[3]);

        // 각 옥텟을 해당 위치로 밀어넣고 OR로 합치기
        return (a << 24) | (b << 16) | (c << 8) | d;
    }
	static String intToIp(int x) {
        int a = (x >>> 24) & 255;
        int b = (x >>> 16) & 255;
        int c = (x >>> 8) & 255;
        int d = x & 255;
        return a + "." + b + "." + c + "." + d;
    }

}
