import math
from collections import deque

# Question 1 Bubble Sort
def bubble_sort(arr):
    n = len(arr)
    arr = arr[:]  # copy list

    for i in range(n):
        for j in range(0, n-i-1):
            if arr[j] > arr[j+1]:
                arr[j], arr[j+1] = arr[j+1], arr[j]

    return arr


# Question 2 Factorial (Recursion)
def factorial(n):
    if n == 0 or n == 1:
        return 1
    return n * factorial(n-1)


# Question 3 Greedy Coin
def min_coins(amount):
    coins = [25,10,5,1]
    used = []

    for coin in coins:
        while amount >= coin:
            amount -= coin
            used.append(coin)

    return used


# Question 4 Binary Tree
class Node:
    def __init__(self, val, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


def dfs_search(root, target):
    if root is None:
        return False
    if root.val == target:
        return True
    return dfs_search(root.left, target) or dfs_search(root.right, target)


# Question 6 Linked List
class ListNode:
    def __init__(self, val):
        self.val = val
        self.next = None


# Question 7 Distance
def l1(p, q):
    return abs(p[0] - q[0]) + abs(p[1] - q[1])

def l2(p, q):
    return math.sqrt((p[0]-q[0])**2 + (p[1]-q[1])**2)


# main test
if __name__ == "__main__":

    # Q1 Sorting
    arr = [5,1,4,2,8]
    print(bubble_sort(arr))

    # Q2 Factorial
    print(factorial(5))

    # Q3 Greedy Coin
    coins = min_coins(63)
    print("Number of coins:", len(coins))
    print("Coins used:", coins)

    # Q4 Tree
    root = Node(5,
            Node(3, Node(2), Node(4)),
            Node(8, None, Node(7)))

    if dfs_search(root, 7):
        print("Found")
    else:
        print("Not Found")

    # Q5 Stack
    stack = []
    stack.append(10)
    stack.append(20)
    stack.pop()
    stack.append(30)
    print("Stack:", stack)

    # Queue
    queue = deque()
    queue.append(10)
    queue.append(20)
    queue.popleft()
    queue.append(30)
    print("Queue:", list(queue))

    # Q6 Linked List
    head = ListNode(1)
    head.next = ListNode(2)
    head.next.next = ListNode(3)
    head.next.next.next = ListNode(4)

    curr = head
    while curr:
        print(curr.val, end=" ")
        curr = curr.next
    print()

    # Q7 Nearest Neighbor
    A = (1,1)
    B = (4,4)
    C = (6,1)
    P = (3,2)

    print("L1 distances:")
    print("A:", l1(P,A))
    print("B:", l1(P,B))
    print("C:", l1(P,C))

    print("L2 distances:")
    print("A:", round(l2(P,A),3))
    print("B:", round(l2(P,B),3))
    print("C:", round(l2(P,C),3))

    print("Nearest under both L1 and L2: A")