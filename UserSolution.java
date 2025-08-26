import java.util.HashMap;
import java.util.Objects;
import java.util.TreeSet;

class UserSolution {

    static class Student implements Comparable<Student>{
        int mId, mGrade, mGender, mScore;

        public Student(int mId, int mGrade, int mGender, int mScore){
            this.mId = mId;
            this.mGrade = mGrade;
            this.mGender = mGender;
            this.mScore = mScore;
        }
        @Override
        public int compareTo(Student student){
            if(this.mScore != student.mScore){
                return Integer.compare(student.mScore, this.mScore);
            }

            return Integer.compare(student.mId, this.mId);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(mId, mScore);
        }
    }

    private static HashMap<Integer, Student> studentMap;
    private static TreeSet<Student>[][] studenTreeSets;
    
	public void init() {
        studentMap = new HashMap<>();
        
        studenTreeSets = new TreeSet[4][2];
        for(int i = 1; i < 4; i++){
            for(int j = 0; j < 2; j++){
                studenTreeSets[i][j] = new TreeSet<>();
            }
        }
		return;
	}

    /*
     * 학생 ID가 mId이고, 학년이 mGrade, 성별이 mGender인 학생의 점수 mScore를 추가한다.
     * 성별의 경우, 남성은 “male”, 여성은 “female”로 주어지고, ‘＼0’ 문자로 끝나는 문자열이다.
     * mGrade학년 mGender인 학생 중에서 점수가 가장 높은 학생의 ID를 반환한다.
     * 점수가 가장 높은 학생이 여러 명이라면, 그 중에서 ID가 가장 큰 값을 반환한다.
     * 
     * 시스템에 이미 점수가 기록되어 있는 학생의 ID는 입력으로 주어지지 않는다. 하지만 기록이 삭제된 후에 다시 추가될 수는 있다.
     * 
     * Parameters
     *  mId: 학생의 ID ( 1 ≤ mId ≤ 1,000,000,000 )
     *  mGrade: 학생의 학년 ( 1 ≤ mGrade ≤ 3 )
     *  mGender: 학생의 성별 ( ‘＼0’ 문자로 끝나는 “male” 또는 “female” 문자열 )
     *  mScore: 학생의 점수 ( 0 ≤ mScore ≤ 300,000 )
     * 
     * Returns
     *  mGrade학년 mGender인 학생 중에서 점수가 가장 높은 학생의 ID를 반환한다.
     */
	public int add(int mId, int mGrade, char mGender[], int mScore) {
        int gender = mGender[0] == 'm' ? 0 : 1;
        Student addStudent = new Student(mId, mGrade, gender, mScore);

        studentMap.put(mId, addStudent);
        studenTreeSets[mGrade][gender].add(addStudent);
        
		return studenTreeSets[mGrade][gender].first().mId;
	}

    /*
     * 학생 ID가 mId인 학생의 기록을 삭제한다.
     * 시스템에 mId 학생의 점수가 기록되어 있지 않다면, 0을 반환한다.
     * 삭제 후에 mId 학생의 학년과 성별이 동일한 학생 중에서 점수가 가장 낮은 학생의 ID를 반환한다.
     * 점수가 가장 낮은 학생이 여러 명이라면, 그 중에서 ID가 가장 작은 값을 반환한다.
     * 삭제 후에, 학년과 성별이 동일한 학생이 없다면, 0을 반환한다.
     * 
     * Parameters
     *  mId: 학생의 ID ( 1 ≤ mId ≤ 1,000,000,000 )
     * 
     * Returns
     *  삭제 후에 mId 학생의 학년과 성별이 동일한 학생 중에서 점수가 가장 낮은 학생의 ID를 반환한다.
     *  mId 학생이 없거나, mId 학생의 학년과 성별이 동일한 학생이 없는 경우에는 0을 반환한다.
     */
	public int remove(int mId) {
        Student removeStudent = studentMap.getOrDefault(mId, null);

        if(Objects.isNull(removeStudent)){
            return 0;
        }

        TreeSet<Student> studenTreeSet = studenTreeSets[removeStudent.mGrade][removeStudent.mGender];

        studenTreeSet.remove(removeStudent);
        studentMap.remove(mId);

        int res = 0;

        if(!studenTreeSet.isEmpty()){
            res = studenTreeSet.last().mId;
        }

		return res;
	}

    /*
     * mGrade 학년 집합과 mGender 성별 집합에 속하면서, 점수가 mScore 이상인 학생 중에서 점수가 가장 낮은 학생의 ID를 반환한다.
     * 점수가 가장 낮은 학생이 여러 명이라면, 그 중에서 ID가 가장 작은 값을 반환한다.
     * mGradeCnt 개의 학년이 mGrade 배열로 주어진다. 예를 들어, 1학년과 3학년이라면 {1, 3}로 주어진다.
     * mGenderCnt 개의 성별이 mGender 배열로 주어진다. 예를 들어, 남성이라면 {“male”}로 주어지고, 남성과 여성이라면 {“male”, “female”}로 주어진다.
     * 
     * 점수가 mScore 이상인 학생이 없다면, 0을 반환한다.
     * 
     * Parameters
     *   mGradeCnt: mGrade 배열의 원소의 개수 ( 1 ≤ mGradeCnt ≤ 3 )
     *   mGrade: 학년 배열 ( 1 ≤ mGrade[i] ≤ 3 )
     *   mGenderCnt: mGender 배열의 원소의 개수 ( 1 ≤ mGenderCnt ≤ 2 )
     *   mGender: 성별 배열 ( mGender[i] = ‘＼0’ 문자로 끝나는 “male” 또는 “female” 문자열 )
     *   mScore: 학생의 점수 ( 0 ≤ mScore ≤ 300,000 )
     * 
     * Returns
     *   mGrade 집합과 mGender 집합에 속하면서, 점수가 mScore 이상인 학생 중에서 점수가 가장 낮은 학생의 ID를 반환한다.
     *   점수가 mScore 이상인 학생이 없는 경우에는 0을 반환한다.
     */
	public int query(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
        Student last = null;
        Student searchKey = new Student(0, 0, 0, mScore);

        for(int grade = 0; grade < mGradeCnt; grade++){
            for(int gender = 0; gender < mGenderCnt; gender++){
                int gnd = mGender[gender][0] == 'm' ? 0 : 1;

                TreeSet<Student> groupSet = studenTreeSets[mGrade[grade]][gnd];

                Student search = groupSet.floor(searchKey);

                if(search != null){
                    if(last == null || last.compareTo(search) < 0)
                    last = search;
                }
            }
        }

        if(last == null)
            return 0;
		return last.mId;
	}
}