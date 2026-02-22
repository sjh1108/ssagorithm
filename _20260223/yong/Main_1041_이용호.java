package baek_week02_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1041_이용호 {
/*
 * 주사위 
 * N x N x N
 * 
 * 주사위 구조 신경써야 할듯(N = 3 이상)
 * 정육면체 주사위 바깥 라인 끝쪽은 3면 연결
 * 끝쪽 제외 바깥라인은 2면 연결
 * 
 * 가운데는 1면(최소값)
 * 
 * 주사위 3면 최소값, 2면 최소값 구하기
 * 3면 최소값 A F 비교 더 작은거 + BD | DE | EC | CB
 * 2면 최소값 BC,CE,ED,DB 최소 조합 구하고 A랑 F 비교해보기
 */
	static int[] nums = new int[6]; // A(0), B(1), C(2), D(3), E(4), F(5)
	// 바닥면과 닿을 수
	// bigestInThree: 3면 최소값중 가장 큰수,bigestInTwo: 2면 최소값중 가장 큰수
	// sixFace: 주사위 6면 총합
	// sixFaceMin: 주사위에서 가장 작은 수
	// sixFaceMax: 주사위에서 가장 큰 수
	static long biggestInThree, biggestInTwo, sixFace, sixFaceMin, sixFaceMax;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		sixFaceMin = Integer.MAX_VALUE; // 주사위에서 가장 작은 수
		sixFaceMax = Integer.MIN_VALUE; // 주사위에서 가장 큰 수
		for(int i = 0; i < 6; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
			if(nums[i] < sixFaceMin) {
				sixFaceMin = nums[i];
			}
			if(nums[i] > sixFaceMax) {
				sixFaceMax = nums[i];
			}
			// 6면 총합
			sixFace += nums[i];
		}
		
		long threeFace = calThree();
		long twoFace = calTwo();
		long result = 0;
		if(N == 1) {
			// 바닥과 닿는면 제외 합 계산
			sixFace -= sixFaceMax; // 주사위에서 가장 큰 수 빼기
			result = sixFace;
		}
		else if(N == 2) {
			// 3면 8개 합 계산
			result = threeFace * 8;
			result -= biggestInThree * 4; // 바닥과 닿는 면 빼기
		}
		else {
			// 3면 8개 합 계산
			result = threeFace * 8;
			result -= biggestInThree * 4;
			// 2면 짜리 합
			long twoFaceNum = (N - 2) * 12; // 주사위 테두리 선 12개(선마다 2개는 3면 주사위)
			result += twoFace * twoFaceNum;
			result -= biggestInTwo * 4 * (N-2); // 바닥면 빼기
			// 일반 면 계산
			long oneFaceNum = (N-2L) * (N-2L) * 5L; // 테두리를 제외한 가로 * 테두리를 제외한 세로 * 바닥면 제외 5면 
			result += sixFaceMin * oneFaceNum;
			
		}
		
		System.out.println(result);
	}
	// A(0), B(1), C(2), D(3), E(4), F(5)
	// 2면 최소값 BC,CE,ED,DB 최소 조합 구하고 A랑 F 비교해보기
	private static long calTwo() {
		// BC
		int min1 = nums[1];
		int min2 = nums[2];
		long minSum = min1 + min2;
		// CE
		if(nums[2] + nums[4] < minSum) {
			min1 = nums[2];
			min2 = nums[4];
			minSum = min1 + min2;
		}
		// ED
		if(nums[3] + nums[4] < minSum) {
			min1 = nums[3];
			min2 = nums[4];
			minSum = min1 + min2;
		}
		// DB
		if(nums[1] + nums[3] < minSum) {
			min1 = nums[1];
			min2 = nums[3];
			minSum = min1 + min2;
		}
		// A와 F중 더 작은 수
		int AorF = nums[0] > nums[5] ? nums[5] : nums[0];
		// 기존 min1 min2 보다 AorF가 더 작으면 교체 후 minSum갱신
		// min2가 더 크게 고정
		if(min1 > min2) {
			int temp = min2;
			min2 = min1;
			min1 = temp;
		}
		// A와F를 고려한 다른 최소값 조합이 있으면 값 갱신
		if(AorF < min2) {
			min2 = AorF;
			minSum = min1 + min2;
		}
		biggestInTwo = min1 < min2 ? min2 : min1;
		return minSum;
	}
	//3면 최소값 계산
	private static long calThree() {
		// BC
		int min1 = nums[1];
		int min2 = nums[2];
		long minSum = min1 + min2;
		// CE
		if(nums[2] + nums[4] < minSum) {
			min1 = nums[2];
			min2 = nums[4];
			minSum = min1 + min2;
		}
		// ED
		if(nums[3] + nums[4] < minSum) {
			min1 = nums[3];
			min2 = nums[4];
			minSum = min1 + min2;
		}
		// DB
		if(nums[1] + nums[3] < minSum) {
			min1 = nums[1];
			min2 = nums[3];
			minSum = min1 + min2;
		}
		biggestInThree = min1 < min2 ? min2 : min1;
		// min(A,F)
		int AorF = nums[0] > nums[5] ? nums[5] : nums[0];
		biggestInThree = biggestInThree < AorF ? AorF : biggestInThree;
		return AorF + minSum;
	}

}
