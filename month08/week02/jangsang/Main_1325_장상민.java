package month08.week02.jangsang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1325_���� {
	static Queue<int[]> queue = new ArrayDeque<>();
	static ArrayList<Integer> comMap;
	static int[] maxHacked;
	static int N, M;
	
	private static void bsf(String a, String b) {
		
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		//��ǻ�� ��ȣ
		//�ŷ��ϴ� ���� ��
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		//arrayList�ʱ�ȭ
		//maxHacked�ʱ�ȭ
		
		// �ŷ��ϴ� ���� �Է¹ް� 
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
		}
		//bsf
		
	}
}
