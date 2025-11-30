package study;


import java.io.*;
import java.util.*;

class Main_3217_김회인 {
	/*
	 * 시작 할때 기본 아이디어
	 * 몇개 받을지 결정

		malloc인지
		print인지 결정
		charat 5해서 --이면 malloc
		아니면 print이거나 free인지 체크
		
		linkedlist 직접 구현해서
		
		malloc은 등록
		list에서 빈자리 있으면 바로 등록
		있다면 hashmap에 등록 + linkedlist에 추가
		
		print
		hashmap에 등록 된걸로 추가
		
		free
		hashmap 제거
		linkedlist 제거
	 */
   static class Node
   {
      int index;
      int size;
      
      Node previous;
      Node next;
      
      Node(int index, int size, Node previous, Node next)
      {
         this.index = index;
         this.size = size;
         this.previous = previous;
         this.next = next;
      }
      
      @Override
    public boolean equals(Object obj) {
    	return this.index == ((Node)obj).index;
    }
   }
   
   static class Linkedlist // 링크드 리스트 직접 구현 속도 문제도 있고 이전 B형 이슈 있어서 직접 구현함
   {
      private Node head;
      private Node tail;
      
      
      public Linkedlist() {
         head = null;
      }
      
      public void insertHead(Node node)
      {
    	  node.next = head;
    	  if(head != null) head.previous = node;
    	  this.head = node;
    	  if(tail == null) tail = node;
      }
      
      public void insertTail(Node preNode, Node data)
      {
    	  data.previous = preNode;
    	  data.next = null;
    	  preNode.next = data;
    	  tail = data;
      }
      
      public void insertNode(Node preNode, Node data)
      {
    	 data.next = preNode.next;
         data.previous = preNode;
         if (preNode.next != null) preNode.next.previous = data;
         preNode.next = data;
         if(preNode == tail) tail = data;
      } 
      
      public void deleteNode(Node data)
      {
          if (data == null) return;
          if (head == null) return;

          // 1) head 삭제
          if (data == head) {
              head = head.next;
             // System.out.println("head");
              if (head != null) {
                  head.previous = null;
                  if(data == tail) tail = head;
              }
              return;
          }

          // 2) tail 삭제
          else if (data.next == null) {
        	  //System.out.println("tail");
        	  if(data.previous != null) data.previous.next = null;
              tail = data.previous;
              return;
          }

          // 3) 중간 노드 삭제
          //System.out.println("middle");
          else {
        	  if(data.previous != null)data.previous.next = data.next;
        	  if(data.next != null)data.next.previous = data.previous;
          }
          
          data.next = data.previous = null;
      }

   }
   
   static Linkedlist list;					// 만든 링크드 리스트 선언
   static HashMap<String, Node> hash;		// 삭제 편하게 해줄 hashmap 선언
 
    public static void main(String[] args) throws IOException {		// 할당 및 입출력 부분
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        
        list = new Linkedlist();
        hash = new HashMap<>();
        
        int n = Integer.parseInt(br.readLine());
        
        for(int i=0; i<n; i++)
        {
           String s = br.readLine();
           
           //System.out.println("s.substring(0,4) : " + s.substring(0,4));
           if(s.charAt(4) == '=') malloc(s);
           if(s.substring(0,5).equals("print")) print(s);
           if(s.substring(0,5).equals("free(")) free(s);
        }
        
        
    }
    
    static public void malloc(String s)		// 
    {
       int start= s.indexOf("(") + 1;		// 기본 문장에서 데이터 추출
       int end = s.indexOf(")");
       int value = Integer.parseInt(s.substring(start,end));
       Node node = new Node(1, value, null, null);
       String name = s.substring(0, 4);
       
       if(list.head == null) {	// head가 null 이면 head 채워주기
          
          list.insertHead(node);
          hash.put(name, node);
          return;
       }
       if(list.head.index - 1>= value)	// head 앞에 공간이 있다면? 그곳에 채워주고 채워준 노드가 헤드가 된다
       {
    	   node.index = 1;
    	   list.insertHead(node);
    	   hash.put(name, node);
    	   return;   
       }

       Node temp = list.head;
          
          
      while(temp != null)		// 들어갈 자리를 찾기 위한 while
      {
         if(temp.next == null) {
        	 if(100000 - (temp.index + temp.size - 1) >= value)
        	 {
        		 node.index = temp.index + temp.size;
        		 //hash.put(name, node);
        		 list.insertTail(temp,node);
        		 //break;
        	 }
        	 else
        	 {
        		 node.index = 0;
        	 }
        	 hash.put(name, node);
        	 break;
         }else {
        	 if(temp.next.index - (temp.index + temp.size) >= value)
        	 {
        		 node.index = temp.index + temp.size;           
        		 hash.put(name, node);
        		 list.insertNode(temp,node);
        		 break;
        	 }
        	 
         }
         
         temp = temp.next;
          
       }
    }
    
    static public void print(String s)		//출력 부분
    {
       int start = s.indexOf("(")+1;
       int end = s.indexOf(")");
       String val = s.substring(start, end);
       
       // 코드 방식이 바뀌어서 없어도 되긴 하는데 혹시 모를 안정성 문제로 일단 두었습니다.
       if(hash.containsKey(val)) System.out.println(hash.get(val).index);	
       else System.out.println("0");
    }
    
    static public void free(String s)		// 해제 부분
    {
       int start= s.indexOf("(") +1;
       int end = s.indexOf(")");
       String val = s.substring(start,end);
       
       if(!hash.containsKey(val)) return;
       
       list.deleteNode(hash.get(val));
       hash.remove(val);

    }
    
}