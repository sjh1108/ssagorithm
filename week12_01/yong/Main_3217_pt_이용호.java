package baek_week12_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
 * 1 ~ 100000 메모리
 * free 구간을 저장하고,
 * malloc(size) → 처음 등장하는 size 이상 구간에서 할당
 * free(var) → 해당 구간 free + 양옆 구간과 병합
 * print(var) → var의 시작주소 출력(없으면 0)
 */
public class Main_3217_pt_이용호 {
	
	// 변수에 할당된 메모리 정보
	public static class Node {
		int startIdx;
		int size;
		public Node(int startIdx, int size) {
			this.startIdx = startIdx;
			this.size = size;
		}
	}
	
	public static class Seg implements Comparable<Seg> {
		int l, r;  // free 구간 [l, r]
		Seg(int l, int r) { this.l = l; this.r = r; }
		public int compareTo(Seg o) { return this.l - o.l; }
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		// free 구간 저장: 시작 인덱스 기준 정렬
		TreeSet<Seg> free = new TreeSet<>();
		free.add(new Seg(1, 100000));

		// var → Node(start,size)
		HashMap<String, Node> memory = new HashMap<>();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			String var;

			switch (line.substring(4, 5)) {

			/* ------------------------
			 * free(var);
			 * ------------------------ */
			case "(":
				var = line.substring(5, 9);

				Node now = memory.get(var);
				// 이미 0이면 아무 일 없음
				if (now == null || now.startIdx == 0) break;

				int l = now.startIdx;
				int r = now.startIdx + now.size - 1;

				Seg newSeg = new Seg(l, r);

				// 왼쪽 병합 가능?
				Seg lower = free.lower(newSeg);
				if (lower != null && lower.r + 1 == l) {
					newSeg.l = lower.l;
					free.remove(lower);
				}

				// 오른쪽 병합 가능?
				Seg higher = free.higher(newSeg);
				if (higher != null && r + 1 == higher.l) {
					newSeg.r = higher.r;
					free.remove(higher);
				}

				// 최종 병합된 free 구간 저장
				free.add(newSeg);

				// 변수 초기화
				memory.put(var, new Node(0, 0));
				break;

			/* ------------------------
			 * var = malloc(size);
			 * ------------------------ */
			case "=":
				var = line.substring(0, 4);
				int size = Integer.parseInt(line.substring(12, line.length() - 2));

				Seg chosen = null;

				// 네 스타일 유지: free 구간 for-each 순회
				for (Seg s : free) {
					int len = s.r - s.l + 1;
					if (len >= size) {
						chosen = s;
						break;
					}
				}

				if (chosen == null) {
					// 할당 불가
					memory.put(var, new Node(0, 0));
					break;
				}

				int start = chosen.l;
				int end = start + size - 1;

				// free 구간 삭제
				free.remove(chosen);

				// 오른쪽 남은 구간 재등록
				if (end < chosen.r) {
					free.add(new Seg(end + 1, chosen.r));
				}

				// 변수 저장
				memory.put(var, new Node(start, size));
				break;

			/* ------------------------
			 * print(var);
			 * ------------------------ */
			case "t":
				var = line.substring(6, line.length() - 2);
				Node info = memory.get(var);

				if (info == null || info.startIdx == 0)
					sb.append("0\n");
				else
					sb.append(info.startIdx).append("\n");

				break;
			}
		}

		System.out.println(sb);
	}
}
