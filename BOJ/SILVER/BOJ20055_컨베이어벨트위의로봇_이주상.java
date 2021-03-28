package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ20055_컨베이어벨트위의로봇_이주상 {
	
	static int[][][] belt;
	static int N,K;
	static int check = 0;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		K = Integer.parseInt(input[1]);
		
		belt = new int[2][N][2];
		// ..[0] : 내구도 기록
		// ..[1] : 로봇 위치 기록
		
		// ================
		//    데이터 입력
		// ================
		input = br.readLine().split(" ");
		for (int i = 0, j = 2*N-1; i < N; i++, j--) {
			belt[0][i][0] = Integer.parseInt(input[i]);
			belt[1][i][0] = Integer.parseInt(input[j]);
		}
		
		int ans = 0;
		
		while(true) {
			ans++;
			
			rotate();
			move();
			robot();
			
			if(check >= K) {
				System.out.println(ans);
				break;
			}
		}
		

		
	}

	private static void move() {
		for (int i = N-2; i >= 0 ; i--) {
			if(belt[0][i][1] == 1) {	//로봇을 찾음
				
				if(belt[0][i+1][0] >= 1 && belt[0][i+1][1] == 0) {
					belt[0][i+1][1] = belt[0][i][1];
					belt[0][i][1] = 0;
					
					if(--belt[0][i+1][0] <= 0) {
						check++;
					}
				}
				
			}
		}
		
		if(belt[0][N-1][1] == 1) {
			belt[0][N-1][1] = 0;	// 로봇 내리기
		}
	}

	private static void rotate() {
		int up = belt[1][0][0];	// 위로 올라가는 값
		int down = belt[0][N-1][0];	// 밑으로 내려가는 값
		
		// 윗 줄 이동
		for (int i = N-2; i >= 0; i--) {
			belt[0][i+1][0] = belt[0][i][0];
			belt[0][i+1][1] = belt[0][i][1];
		}
		
		// 아래줄 이동
		for (int i = 0; i < N-1; i++) {
			belt[1][i][0] = belt[1][i+1][0];
		}
		
		belt[0][0][0] = up;
		belt[0][0][1] = 0;
		belt[1][N-1][0] = down;
		
		if(belt[0][N-1][1] == 1) {
			belt[0][N-1][1] = 0;	// 로봇 내리기
		}
	}

	private static void robot() {
		// 로봇 올리기 (내구도 1 이상, 해당칸에 로봇 X)
		if(belt[0][0][0] >= 1 && belt[0][0][1] == 0) {
			belt[0][0][1] = 1;	// 로봇 올리기
			
			if(--belt[0][0][0] <= 0) {
				check++;
			}
		}
	}

}
