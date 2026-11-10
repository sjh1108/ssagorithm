import java.util.*;

class Solution {
    public long solution(int n, int[] works) {
        long answer = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Collections.reverseOrder());

        for (int i : works) {
            pq.add(i);
        }

        while (n > 0 && pq.peek() != 0) {
            int i = pq.poll();
            i--;
            n--;

            pq.add(i);
        }

        while (!pq.isEmpty()) {
            int i = pq.poll();

            answer += i * i;
        }

        return answer;
    }
}
