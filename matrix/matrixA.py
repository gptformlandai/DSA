#code to create a matrix A of size m x n with random integer whole numbers between 0 and 100.

from random import random


def create_matrix(m, n):
    matrix_A = []
    for i in range(m):
        row = []
        for j in range(n):
            row.append(j)
        matrix_A.append(row)
    return matrix_A
# Example usage:
m = 3  # number of rows
n = 3  # number of columns
matrix_A = create_matrix(m, n)
print("Matrix A:")
for i in matrix_A:
    for j in i:
        print(j)

#reverse each row of the matrix A
def reverse_matrix(matrix):
    reversed_matrix = []
    for row in matrix:
        reversed_row = row[::-1]  # Reverse the row using slicing
        reversed_matrix.append(reversed_row)
    return reversed_matrix

#reverse each column of the matrix A
def reverse_columns(matrix):
    reversed_matrix = []
    for i in range(len(matrix[0])):  # Iterate over columns
        reversed_column = [row[i] for row in matrix][::-1]  # Reverse the column
        reversed_matrix.append(reversed_column)
    return reversed_matrix


