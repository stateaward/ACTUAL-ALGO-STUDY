package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/* 풀이 생각
 * 1. 바이러스(2)인 수를 전부 virusList에 넣기
 * 2. nCm 의 형식으로 모든 경우에 대한 시간 계산
 * 3. m번 반복하며 BFS로 바이러스 확산 처리
 */

class Virus {
	int x;
	int y;
	int time;
	
	public Virus(int x, int y, int level) {
		this.x = x;
		this.y = y;
		this.time = level;
	}
}

public class BOJ17142_연구소3 {
	
	static int N, M;
	static ArrayList<Virus> virusList = new ArrayList<>();
	static int[] picked;
	static int[][] map;
	static boolean[][] isVisit;
	static int blank;
	
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	
	static int min = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws Exception {
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]); // 연구소 크기
		M = Integer.parseInt(input[1]); // 바이러스 개수
		
		map = new int[N][N];
		
		// ==============
		//  * 데이터 입력
		// ==============
		for (int i = 0; i < N; i++) {
			input = br.readLine().split(" ");
			for (int j = 0; j < N; j++) {
				int num = Integer.parseInt(input[j]);
				
				map[i][j] = num;
				
				if(num == 2) {
					virusList.add(new Virus(i,j,1));
				}else if(num == 0) {
					blank++;			// 비어있는 칸(바이러스가 퍼져야 할)의 갯수 
				}
			}
		}
		
		if(blank == 0) {
			System.out.println(0);
			return;
		}
		
		// 뽑은 조합을 저장하기 위한 배열
		picked = new int[M];
		
		combination(0, 0);
		
		if(min == Integer.MAX_VALUE) {
			System.out.println(-1);
			return;
		}
		
		System.out.println(min);
		
	}

	// cnt(현재까지 뽑은 수), cur(현재 가르키는 번호)
	private static void combination(int cnt, int cur) {
		if(cnt == M) {
			BFS();
			return;
		}
		
		for (int i = cur; i < virusList.size(); i++) {
			picked[cnt] = i;
			combination(cnt+1, i+1);
		}
	}

	private static void BFS() {
		Queue<Virus> q = new LinkedList<>();
		isVisit = new boolean[N][N];
		
		for (int i = 0; i < M; i++) {
			q.add(virusList.get(picked[i]));
			isVisit[virusList.get(picked[i]).x][virusList.get(picked[i]).y] = true;
		}
		
		int time = 0;
		int count = 0;
		
		while(!q.isEmpty()) {
			Virus v = q.poll();
			
			for (int k = 0; k < 4; k++) {
				int nx = v.x + dx[k];
				int ny = v.y + dy[k];
				
				
				// 이전에 있던 바이러스의 시간값을 받아오므로 계속 증가할 수 밖에 없음
				time = v.time;
				
				// 범위 밖 OR 이미 방문 = 아웃
				if(!(0 <= nx && nx < N && 0 <= ny && ny < N) || isVisit[nx][ny]) continue;
				
				// 빈칸이어서 확산한 경우)
				if(map[nx][ny] == 0) {
					isVisit[nx][ny] = true;
					count++;
					q.add(new Virus(nx,ny,v.time+1));
				}
				
				// 확산은 못하지만 지나갈 수 있는 경우)
				if(map[nx][ny] == 2) {
					isVisit[nx][ny] = true;
					q.add(new Virus(nx,ny,v.time+1));
				}
				
			}
			
			// 만약 이미 빈칸이 채워졌다면 사전에 종료시킴으로써 시간값의 계속 증가를 방지
			if(count == blank) {
				time++;	// 이경우, 새로 뽑은 시간값 할당이 안되므로, 임의로 1 증가
				break;
			}
		};
	
		
		if(count != blank) return;
		
		// time-1의 이유 : 마지막에 +1한 상태를 q에 날리기 때문
		min = Math.min(min, time-1);
	}

}
