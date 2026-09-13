import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 단조 자료구조, 덱, 슬라이딩 윈도우
     * 윈도우 크기가 고정된 슬라이딩 윈도우 문제이지만, 투포인터 기반 로직으로는 해당 문제를 해결할 수 없음
     * 기존 윈도우의 최소값이 제거되었을 때 그 다음으로 작은 값이 새 윈도우 내에서 어디에 있는지 찾아야 함
     * 구간 내 최소값을 즉시 알 수 있으면서도 기존 최소값이 빠졌을 때 그 다음 최소값이 무엇인지 바로 파악할 수 있어야 함
     *
     * 덱을 이용한 슬라이딩 윈도우
     * - 덱의 각 항목은 부품의 입력 순서(인덱스)와 높이를 가짐
     * - 덱의 내부는 반드시 높이 오름차순으로 관리(처음 값은 반드시 윈도우 내 최소값)
     * - 1. 덱의 처음 값(최소값)이 윈도우 범위를 벗어날 차례가 되면 제거 -> removeFirst()
     * - 2. 새 부품 높이가 주어지면 높이가 더 높거나 같은 기존 부품을 제거 -> removeLast()
     * - 3. 새 부품의 인덱스와 높이를 덱의 끝에 등록 -> addLast();
     * - 4. 매 턴마다 덱의 처음 값(최소값)을 합산
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken()); // 윈도우 크기

        Deque<int[]> deq = new ArrayDeque<>(); // 각 항목은 { 인덱스, 높이 }
        st = new StringTokenizer(br.readLine());

        // 초기화
        // k 크기 윈도우가 전부 채워지기 전에는 최소값을 합산하지 않아 별도로 처리했음
        for(int i=0; i<k; i++) {
            int val = Integer.parseInt(st.nextToken());
            while(!deq.isEmpty() && deq.peekLast()[1] >= val) deq.removeLast();
            deq.addLast(new int[] {i, val});
        }
        long total = deq.peekFirst()[1]; // 첫 윈도우의 최소값 합산

        // 덱을 이용한 슬라이딩 윈도우 과정
        for(int i=k; i<n; i++) {
            // i : 현재 턴, 최소값의 인덱스가 윈도우 범위를 벗어나면 해당 값을 제거
            if(deq.peekFirst()[0] + k <= i) deq.removeFirst();

            int val = Integer.parseInt(st.nextToken());
            while(!deq.isEmpty() && deq.peekLast()[1] >= val) deq.removeLast();
            deq.addLast(new int[] {i, val});

            // 매 턴마다 최소값 합산
            total += deq.peekFirst()[1];
        }

        System.out.println(total);
    }

}