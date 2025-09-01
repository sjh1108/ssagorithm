

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;

public class UserSolution_19340 {
	
HashMap<Integer, Department> deps;
	
	static class Department {
		int id, cnt, parent;
		List<Department> subList;
		
		public Department(int id, int cnt, int parent) {
			this.id = id;
			this.cnt = cnt;
			this.parent = parent;
			subList = new ArrayList<>();
		}
	}
	
	public void init(int N, int mId[], int mNum[]) {
		deps = new HashMap<>();
		Department root = new Department(0, 0, -1);
		for(int i=0; i<N; i++) {
			Department dep = new Department(mId[i], mNum[i], 0);
			deps.put(mId[i], dep);
			root.cnt += mNum[i];
			root.subList.add(dep);
		}
		deps.put(0, root);
	}

	public int add(int mId, int mNum, int mParent) {
		Department parent = deps.get(mParent);
		if(parent.subList.size() >= 3) return -1;
		
		Department newDep = new Department(mId, mNum, mParent);
		deps.put(mId, newDep);
		parent.subList.add(newDep);
		parent.cnt += mNum;
		int p = parent.parent;
		while(p != -1) {
			Department sup = deps.get(p);
			sup.cnt += mNum;
			p = sup.parent;
		}
		return parent.cnt;
	}

	public int remove(int mId) {
		if(!deps.containsKey(mId)) return -1;
		
		Department target = deps.get(mId);
		int total = target.cnt;
		Department parent = deps.get(target.parent);
		parent.subList.removeIf(d -> d.id == mId);
		
		parent.cnt -= total;
		int p = parent.parent;
		while(p != -1) {
			Department sup = deps.get(p);
			sup.cnt -= total;
			p = sup.parent;
		}
		
		removeSub(target);
		return total;
	}
	
	public void removeSub(Department dep) {
	    Deque<Department> st = new ArrayDeque<>();
	    st.push(dep);
	    while (!st.isEmpty()) {
	        Department cur = st.pop();
	        for (Department ch : cur.subList) st.push(ch);
	        deps.remove(cur.id);
	    }
	}

	public int distribute(int K) {
		// 모든 인원 총계를 가진 루트 노드 호출 및 각 그룹별 인원수 집계
		Department root = deps.get(0);
		int[] cntArr = new int[root.subList.size()];
		for(int i=0; i<root.subList.size(); i++) {
			cntArr[i] = root.subList.get(i).cnt;
		}
		
		// 만약 상품권 수가 인원수보다 많다면, 가장 인원수가 많은 그룹의 인원수 반환
		if(root.cnt <= K) {
			int max = 0;
			for(int i=0; i<root.subList.size(); i++) {
				int gCnt = cntArr[i];
				if(gCnt > max) max = gCnt;
			}
			return max;
		}
		
		// 그렇지 않다면, 매개변수 탐색으로 적절한 상한치를 구할 것
		int left = 1, right = K, ans = 0;
		while(left <= right) {
			int mid = (left + right) / 2;
	        
	        // long 타입으로 합계를 계산하여 잠재적인 오버플로우 방지
	        long sum = 0;
	        for (int gCnt : cntArr) {
	            sum += Math.min(gCnt, mid);
	        }

	        if (sum > K) {
	            // 필요한 상품권이 K보다 많으면, 상한선(mid)이 너무 높음
	            right = mid - 1;
	        } else {
	            // 필요한 상품권이 K 이하이면, mid는 유효한 상한선임
	            ans = mid;
	            left = mid + 1;
	        }
		}
		
		return ans;
	}

}
