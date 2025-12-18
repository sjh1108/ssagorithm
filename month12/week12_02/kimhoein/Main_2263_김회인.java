package study;


import java.io.*;
import java.util.*;

public class Main_2263_김회인 {
	/*
	 * 이진트리의 인오더 포스트오더 프리오더가 뭔지 몰라서 애먹었다
	 * 중위 후위 전위 순회였다...
	 * poststart postend로 나누는거에 상당히 애먹었다
	 * 기본적인 접근은 dp에 비해서 역으로 쉬운데
	 * 구현을 생각하는데 상당히 애먹은 문제
	 * inorder로 루트를 찾고
	 * postorder로 좌우 나눠서 재귀
	 * 반복
	 * 
	 */
	static int n;
	static int[] inorder, postorder,index;
	static StringBuilder sb = new StringBuilder();
	
    public static void main(String[] args) throws Exception {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st;// = new StringTokenizer(br.readLine());
    	
    	n = Integer.parseInt(br.readLine());
    	inorder = new int[n];
    	postorder = new int[n];
    	index = new int[n + 1];		// 인덱스를 만들어서 바로 inorder에서 루트를 찾느게 핵심
    	
    	st =new StringTokenizer(br.readLine());
    	for(int i=0; i<n;i++) {
    		inorder[i] = Integer.parseInt(st.nextToken());
    		index[inorder[i]] = i;
    	}
    	
    	st = new StringTokenizer(br.readLine());
    	
    	for(int i=0; i<n; i++) {
    		postorder[i] = Integer.parseInt(st.nextToken());
    	}
    	
    	preorder(0, n-1, 0, n-1);
    	System.out.println(sb.toString());
    	
    }
    
    static void preorder(int inStart, int inEnd, int postStart, int postEnd) {
    	if(inStart > inEnd || postStart > postEnd) return;		// 범위 밖 체크
    	
    	int root = postorder[postEnd];
    	sb.append(root).append(" ");
    	
    	int rootIndex = index[root];
    	
    	int leftSzie = rootIndex - inStart;
    	
    	preorder(inStart, rootIndex - 1, postStart, postStart + leftSzie - 1);
    	
    	preorder(rootIndex + 1, inEnd, postStart + leftSzie, postEnd - 1);
    	
    	
    }
}
