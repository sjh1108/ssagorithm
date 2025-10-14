package algo.baek.b4673;

public class Main_4673_이용호 {
/*
 * d(75) = 75+7+5 = 87
 * d(87) = 87+8+7
 * 
 */
	public static boolean[] visited;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		visited = new boolean[10001];
		for(int i = 1; i < 10000; i++) {
			d(i);
		}
		for(int i = 1; i < 10000; i++) {
			if(!visited[i]) {
				System.out.println(i);
			}
		}
	}
	public static void d(int i) {
		if(i > 10000) return;
		//자리수 더하기
		int sum = i;
		int num = i;
		while(num != 0) {
			sum += num % 10;
			num /= 10;
		}
		if(sum <= 10000) {
//			System.out.println(sum);
			visited[sum] = true;
		}
		d(sum);
	}

}
