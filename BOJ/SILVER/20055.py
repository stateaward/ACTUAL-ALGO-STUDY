# -*- coding: utf-8 -*-
import sys

input = sys.stdin.readline

n, k = map(int, input().split())
# 내구도
array = list(map(int, input().split()))
robot = [False] * 2 * n

up = 0  # 올라가는 위치
down = n - 1  # 내려가는 위치
answer = 0  # 단계
cnt = 0

while True:
    # 1단계 벨트 한 칸 회전
    # print("1단계")
    answer += 1
    up -= 1
    if up < 0:
        up = 2 * n - 1
    if robot[down] == True:
        robot[down] = False
    down -= 1
    if down < 0:
        down = 2 * n - 1
    # 2단계 로봇 이동
    # print("2단계")
    move = up
    for i in range(n):
        if move < 0:
            move = 2 * n - 1
        if robot[move] == True and robot[move + 1] == False and array[move] > 0:
            robot[move] = False
            robot[move + 1] = True
            array[move] -= 1
        move -= 1
    robot[down] = False

    # 3단계 로봇 올리기
    # print("3단계")
    if robot[up] == False:
        robot[up] = True
        array[up] -= 1

    # 4단계 내구도 개수 세기
    # print("4단계")
    for i in range(2 * n):
        if array[i] == 0:
            cnt += 1
    if cnt >= k:
        break

print(answer)