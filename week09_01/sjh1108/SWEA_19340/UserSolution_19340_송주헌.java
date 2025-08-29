package week09_01.sjh1108.SWEA_19340;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class UserSolution_19340_송주헌 {
    int total;
    HashMap<Integer, Department> departmentMap;

    static class Department{
        int ID;
        int cnt;

        Integer parentID;
        List<Integer> childrenID;

        public Department(int ID, int cnt, Integer parentID){
            this.ID = ID;
            this.cnt = cnt;
            this.parentID = parentID;

            childrenID = new ArrayList<>(3);
        }
    }

    Department[] group;

	public void init(int N, int mId[], int mNum[]) {
        total = 0;
        departmentMap = new HashMap<>();
        group = new Department[N];

        for(int i = 0; i < N; i++){
            Department cur = new Department(mId[i], mNum[i], null);
            group[i] = cur;

            departmentMap.put(mId[i], cur);
            total += mNum[i];
        }

		return;
	}

	public int add(int mId, int mNum, int mParent) {
        Department parent = departmentMap.get(mParent);
        if(parent.childrenID.size() == 3) return -1;

        Department tmp = new Department(mId, mNum, mParent);
        parent.childrenID.add(mId);
        departmentMap.put(mId, tmp);

        total += mNum;

        Department cur = parent;
        while (cur != null) {
            cur.cnt += mNum;
            cur = departmentMap.get(cur.parentID);
        }

		return parent.cnt;
	}

    private void removeChildren(int mId){
        Department tmp = departmentMap.get(mId);
        if(tmp == null) return;

        for(int childrenID: tmp.childrenID){
            removeChildren(childrenID);
        }

        departmentMap.remove(mId);
    }

	public int remove(int mId) {
        Department tmp = departmentMap.get(mId);
        if(tmp == null) return -1;

        int res = tmp.cnt;
        Integer parentId = tmp.parentID;

        removeChildren(mId);

        total -= res;

        if (parentId != null) {
            Department parent = departmentMap.get(parentId);
            if (parent != null) {
                parent.childrenID.remove(Integer.valueOf(mId));

                Department cur = parent;
                while (cur != null) {
                    cur.cnt -= res;
                    cur = departmentMap.get(cur.parentID);
                }
            }
        }
        
		return res;
	}

	public int distribute(int K) {
        if(total <= K){
            int maxCnt = 0;

            for (Department d : group) {
                if (d.cnt > maxCnt) {
                    maxCnt = d.cnt;
                }
            }
            
            return maxCnt;
        }

        // 이분 탐색
        List<Integer> cntList = new ArrayList<>();

        int max = 0;
        for (Department d : group) {
            cntList.add(d.cnt);

            if (d.cnt > max) {
                max = d.cnt;
            }
        }

        int low = 0;
        int high = max;
        int result = 0;

        while (low <= high) {
            int limit = low + (high - low) / 2;
            long needed = 0;

            for (int p : cntList) {
                needed += Math.min(p, limit);
            }

            if (needed <= K) {
                result = limit;
                low = limit + 1;
            } else {
                high = limit - 1;
            }
        }
        return result;
	}
}