# Create a matrix A of size m x n with random integers between 0 and 100.

from random import randint


def create_matrix(m, n, min_value=0, max_value=100):
    return [[randint(min_value, max_value) for _ in range(n)] for _ in range(m)]


def reverse_rows(matrix):
    return [row[::-1] for row in matrix]


def reverse_columns(matrix):
    return matrix[::-1]


def print_matrix(matrix):
    for row in matrix:
        print(" ".join(f"{value:3d}" for value in row))


def main():
    matrix_a = create_matrix(3, 3)

    print("Matrix A:")
    print_matrix(matrix_a)

    print("\nRows reversed:")
    print_matrix(reverse_rows(matrix_a))

    print("\nColumns reversed:")
    print_matrix(reverse_columns(matrix_a))


if __name__ == "__main__":
    main()

