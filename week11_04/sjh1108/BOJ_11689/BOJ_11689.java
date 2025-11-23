package week11_04.sjh1108.BOJ_11689;

import java.io.*;

class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		long n = Long.parseLong(br.readLine());
		long p = n;
		for(long i=2; i <= Math.sqrt(n); i++) {
			if(n%i==0)
				p = p/i*(i-1);
            
			while(n%i==0)
				n/=i;
		}
		if(n!=1) {
			p = p/n*(n-1);
		}
		System.out.println(p);
	}
}