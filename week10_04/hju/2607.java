import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        String og = "";
        int[] ogArr = new int[26];

        int ans = 0;
        for(int i = 0; i < N; i++) {
            if(i == 0) {
                og = br.readLine();

                for(int c = 0; c < og.length(); c++) 
                    ogArr[og.charAt(c) - 'A']++;

                continue;
            }

            String target = br.readLine();
            int[] tArr = new int[26];

            if(target.length() < og.length() - 1 || target.length() > og.length() + 1)  continue;

            for(int c = 0; c < target.length(); c++) 
                    tArr[target.charAt(c) - 'A']++;
            
            int res = 0;
            for(int j = 0; j < 26; j++) 
                res += Math.abs(ogArr[j] - tArr[j]);
            
            if(res >= 0 && res < 3)    ans++;
        }

        System.out.println(ans);
    }
}