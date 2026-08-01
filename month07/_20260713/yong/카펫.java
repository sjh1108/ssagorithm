
/*
가로 * 세로 = bronw + yellow되는 조합 찾아야함
가로 >= 세로
*/
class Solution {
    public int[] solution(int brown, int yellow) {
        int[] answer = {};
        int total = brown + yellow;
        for(int h = 3; h <= total; h++){
            // 높이랑 나눠서 안떨어지면 continue
            if(total % h != 0) continue;
            // 카펫 가로 길이 후보
            int w = total / h;
            // 세로가 더 크면 continue
            if(h > w) continue;
            if((w-2) * (h-2) == yellow) {
                return new int[] {w,h};
            }
        }
        
        return answer;
    }
}