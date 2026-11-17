import java.util.*;
class Solution {
    class Node {
        String word;
        int count;
        
        Node(String word, int count){
            this.word = word;
            this.count = count;
        }
    }
    public int solution(String begin, String target, String[] words) {
        Queue<Node> q = new ArrayDeque<>();
        boolean[] visited = new boolean[words.length];
        
        q.offer(new Node(begin,0));
        
        while(!q.isEmpty()){
            Node cur = q.poll();
            
            String curWord = cur.word;
            if(curWord.equals(target)){
                return cur.count;
            }
            
            for(int i = 0; i < words.length; i++){
                if(visited[i]) continue;
                // 한 번에 한 개의 알파벳만 바뀌는지 체크
                if(!canChange(curWord, words[i])) continue;
                
                visited[i] = true;
                q.offer(new Node(words[i], cur.count + 1));
            }
            
        }
        return 0;
        
    }
    // 하나의 알파벳만 다를때만 true 반환
    public boolean canChange(String a, String b){
        int diffCnt = 0;
        
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) != b.charAt(i)) diffCnt++;
        }
        
        return diffCnt == 1;
        
    }
    
}