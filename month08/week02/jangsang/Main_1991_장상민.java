package month08.week02.jangsang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_1991_���� {
	static class Node{
		char value;
		Node left;
		Node right;
		
		static List<Node> link = new ArrayList<>();
		
		Node(char value){
			this.value = value;
			link.add(this);
		}
		
		public void setLeft(Node left) {
			this.left = left;
		}
		public void setRight(Node right) {
			this.right = right;
		}
		
		public static Node getIfExist(char value) {
			for(Node n: link) {
				if(n.value == value) return n;
			}
			
			return new Node(value);
		}
		
		// @@@@@@@@@
		public static List<Node> preOrder(Node node){
			List<Node> list = new ArrayList<>();
			
			list.add(node);
			list.addAll(preOrder(node.left));
			list.addAll(preOrder(node.right));
			return list;
		}
		
		public static List<Node> inOrder(Node node){
			List<Node> list = new ArrayList<>();
			
			return list;
		}
		public static List<Node> postOrder(Node node){
			List<Node> list = new ArrayList<>();
			
			return list;
		}
		// @@@@@@@@@
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			char head = st.nextToken().charAt(0);
			char left = st.nextToken().charAt(0);
			char right = st.nextToken().charAt(0);
			
			Node h = Node.getIfExist(head);
			h.setLeft(new Node(left));
			h.setRight(new Node(right));
		}
		
		// ���� ��ȸ
		// ���� ��ȸ
		// ���� ��ȸ
	}
}
