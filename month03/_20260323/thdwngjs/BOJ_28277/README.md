# [Gold I] 뭉쳐야 산다 - 28277 

[문제 링크](https://www.acmicpc.net/problem/28277) 

### 성능 요약

메모리: 404356 KB, 시간: 1536 ms

### 분류

자료 구조, 집합과 맵, 트리를 사용한 집합과 맵, 작은 집합에서 큰 집합으로 합치는 테크닉

### 제출 일자

2026년 3월 17일 16:45:56

### 문제 설명

<p><var>N</var>개의 집합 <var>S<sub>1</sub></var>, <var>S<sub>2</sub></var>, …, <var>S<sub>N</sub></var>이 주어질 때 다음 명령들을 <var>Q</var>개 처리해 보자.</p>

<ul>
	<li><span style="color:#e74c3c;"><code>1 a b</code></span>: 집합 <var>S<sub>a</sub></var>를 <var>S<sub>a</sub></var> ∪ <var>S<sub>b</sub></var>로 바꾸고, <var>S<sub>b</sub></var>는 공집합으로 바꾼다. (1 ≤ <var>a</var>, <var>b</var> ≤ N; <var>a</var> ≠ <var>b</var>)</li>
	<li><span style="color:#e74c3c;"><code>2 a</code></span>: 집합 <var>S<sub>a</sub></var>의 크기를 출력한다. (1 ≤ <var>a</var> ≤ <var>N</var>)</li>
</ul>

### 입력 

 <p>첫 번째 줄에 <var>N</var>과 <var>Q</var>가 주어진다. (1 ≤ <var>N</var>, <var>Q</var> ≤ 500,000)</p>

<p>다음 <var>N</var>개 줄의 <var>i</var> 번째 줄에는 집합 <var>S<sub>i</sub></var>의 정보가 주어진다.</p>

<p>각 줄에는 <var>S<sub>i</sub></var>의 크기 <var>n<sub>i</sub></var>가 먼저 주어지고, 이어서 <var>S<sub>i</sub></var>의 원소 <var>s<sub>ij</sub></var>가 주어진다. (1 ≤ ∑ <var>n<sub>i</sub></var> ≤ 500,000; 1 ≤ <var>s<sub>ij</sub></var> ≤ 10<sup>9</sup>; 모든 <var>k</var> ≠ <var>j</var>에 대해 <var>s<sub>ij</sub></var> ≠ <var>s<sub>ik</sub></var>)</p>

<p>다음 <var>Q</var>개 줄에는 위에서 설명한 명령이 한 줄에 하나씩 주어진다.</p>

<p>입력되는 모든 수는 정수이고, 명령 <span style="color:#e74c3c;"><code>2 a</code></span>는 하나 이상 주어진다.</p>

### 출력 

 <p>명령 <span style="color:#e74c3c;"><code>2 a</code></span>가 주어질 때마다 각 줄에 명령의 결과를 출력한다.</p>
