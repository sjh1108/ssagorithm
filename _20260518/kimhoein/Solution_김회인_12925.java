package study;
class Solution {
    public int solution(String s) {
        int answer = 0;
        
       char c = s.charAt(0);

       
       if(c == '-')
       {
    	   answer = Integer.parseInt(s.substring(1));
           answer*=-1;
       }
       else
       {
    	   answer = Integer.parseInt(s);
       }
        
        return answer;
    }
}