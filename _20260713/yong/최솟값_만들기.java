import java.util.*;
/*
각 배열 정렬
A의 첫 원소 * B의 마지막 원소 vs A의 마지막 원소 * B의 첫 원소
*/
class Solution
{
    public int solution(int []A, int []B)
    {
        int answer = 0;
        boolean[] useA = new boolean[A.length];
        
        Arrays.sort(A);
        Arrays.sort(B);
        
        Deque<Integer> aq = new ArrayDeque<>();
        Deque<Integer> bq = new ArrayDeque<>();
        
        for(int i = 0; i < A.length; i++){
            aq.offer(A[i]);
            bq.offer(B[i]);
        }
        for(int i = 0; i < A.length; i++){
            int FA = aq.peekFirst();
            int LB = bq.peekLast();
            
            int LA = aq.peekLast();
            int FB = bq.peekFirst();
            
            int FALB = FA * LB;
            int LAFB = LA * FB;
            
            if(FALB > LAFB){
                answer += LAFB;
                aq.removeLast();
                bq.removeFirst();
            }
            else if(FALB <= LAFB){
                answer += FALB;
                aq.removeFirst();
                bq.removeLast();
            }
        }
        return answer;
    }
}