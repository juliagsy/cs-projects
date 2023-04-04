import numpy


class DeterminantInverse:
    def __init__(self):
        self.dimension = None
        self.matrix = None
        self.inverse_matrix = None
        self.e_matrix = None
        self.determinant = None

    def get_input1(self, row):
        try:
            dimension = int(row)
        except:
            return "Invalid input! Please try again."
        if dimension < 2:
            return "Invalid input! Please try again."
        self.dimension = dimension
        return "The number of row is {}. Please input matrix.".format(dimension)

    def run_all(self, mat):
        self.init_matrix()
        result = self.matrix_input(mat)
        if result is not None:
            return result
        self.determinant = self.det(self.matrix)
        res = self.inverse_or_not(self.determinant)
        if type(res) == str:
            return res

    def det_result(self):
        return self.determinant

    def inverse_result(self):
        return self.e_matrix


    def init_matrix(self):
        self.matrix = numpy.zeros([self.dimension, self.dimension])
        self.e_matrix = numpy.zeros([self.dimension, self.dimension])
        for i in range(self.dimension):
            self.e_matrix[i][i] = 1

    def matrix_input(self, mat):
        try:
            matrix = mat.split(';')
        except:
            return "Please enter a valid matrix!"
        i = 0
        for row in matrix:
            index = 0
            try:
                row = row.split(',')
                try:
                    for e in row:
                        self.matrix[i][index] = int(e)
                        index = index + 1
                except:
                    return "Please enter valid real numbers!"
            except:
                return "Please enter a correctly formatted matrix!"
            i = i + 1
        if i != index:
            return "It is not a square matrix"
        if index != self.dimension:
            return "Incorrect number of rows or columns"
        if i != self.dimension:
            return "Incorrect number of rows."

    def det(self, matri):
        index = len(matri)
        if (index == 1):
            return matri[0][0]
        else:
            return self.sign(index, matri)

    def sign(self, num, matrix):
        result = 0
        for j in range(num):
            minor_matrix = [[row[z] for z in range(num) if z != j] for row in matrix[1:]]
            if (j % 2 == 0):
                result = result + matrix[0][j] * self.det(minor_matrix)
            else:
                result = result - matrix[0][j] * self.det(minor_matrix)
        return result

    def inverse_or_not(self, det):
        if (det != 0):
            return self.cal_inverse()
        return "The inverse of the matrix does not exist"

    def column_inverse(self, i):
        f1 = self.inverse_matrix[i][i]
        for j in range(self.dimension):
            self.inverse_matrix[i][j] = self.inverse_matrix[i][j] / f1
            self.e_matrix[i][j] = self.e_matrix[i][j] / f1

    def row_inverse(self, k):
        pos = []
        for p in range(self.dimension):
            pos.append(p)
        for i in pos[0:k]:
            f2 = self.inverse_matrix[i][k]
            for j in range(self.dimension):
                self.inverse_matrix[i][j] = self.inverse_matrix[i][j] - f2 * self.inverse_matrix[k][j]
                self.e_matrix[i][j] = self.e_matrix[i][j] - f2 * self.e_matrix[k][j]
        for i in pos[k + 1:]:
            f2 = self.inverse_matrix[i][k]
            for j in range(self.dimension):
                self.inverse_matrix[i][j] = self.inverse_matrix[i][j] - f2 * self.inverse_matrix[k][j]
                self.e_matrix[i][j] = self.e_matrix[i][j] - f2 * self.e_matrix[k][j]

    def cal_inverse(self):
        self.inverse_matrix = self.matrix
        for z in range(self.dimension):
            self.column_inverse(z)
            self.row_inverse(z)


'''
    def get_input(self):
        while True:
            dimension = eval(input("Enter the dimension of the matrix: "))
            if type(dimension) == int and dimension > 1:
                self.dimension = dimension
                self.init_matrix()
                self.matrix_input()
                break
            print("Invalid input! Please try again.")

    def init_matrix(self):
        self.matrix = numpy.zeros([self.dimension, self.dimension])
        self.e_matrix = numpy.zeros([self.dimension, self.dimension])
        for i in range(self.dimension):
            self.e_matrix[i][i] = 1

    def matrix_input(self):
        for i in range(self.dimension):
            while True:
                row = input("Enter elements separated by COMMA in the {} row of the matrix: ".format(i+1))
                row = row.split(',')
                index = -1
                try:
                    for e in row:
                        index = index + 1
                        self.matrix[i][index] = int(e)
                except:
                    print("Please enter valid real numbers!")
                    continue
                if index + 1 == self.dimension:
                    break

    def sign(self, num, matrix):
        result = 0
        for j in range(num):
            minor_matrix = [[row[z] for z in range(num) if z != j] for row in matrix[1:]]
            if (j % 2 == 0):
                result = result + matrix[0][j] * self.det(minor_matrix)
            else:
                result = result - matrix[0][j] * self.det(minor_matrix)
        return result

    def det(self, matri):
        index = len(matri)
        if (index == 1):
            return matri[0][0]
        else:
            return self.sign(index, matri)

    def inverse_or_not(self, det):
        if (det != 0):
            return self.cal_inverse()
        print("The inverse of the matrix does not exist")

    def column_inverse(self, i):
        f1 = self.inverse_matrix[i][i]
        for j in range(self.dimension):
            self.inverse_matrix[i][j] = self.inverse_matrix[i][j] / f1
            self.e_matrix[i][j] = self.e_matrix[i][j] / f1

    def row_inverse(self, k):
        pos = []
        for p in range(self.dimension):
            pos.append(p)
        for i in pos[0:k]:
            f2 = self.inverse_matrix[i][k]
            for j in range(self.dimension):
                self.inverse_matrix[i][j] = self.inverse_matrix[i][j] - f2 * self.inverse_matrix[k][j]
                self.e_matrix[i][j] = self.e_matrix[i][j] - f2 * self.e_matrix[k][j]
        for i in pos[k + 1:]:
            f2 = self.inverse_matrix[i][k]
            for j in range(self.dimension):
                self.inverse_matrix[i][j] = self.inverse_matrix[i][j] - f2 * self.inverse_matrix[k][j]
                self.e_matrix[i][j] = self.e_matrix[i][j] - f2 * self.e_matrix[k][j]

    def cal_inverse(self):
        self.inverse_matrix = self.matrix
        for z in range(self.dimension):
            self.column_inverse(z)
            self.row_inverse(z)

    def run(self):
        determinant = self.det(self.matrix)
        print(determinant)
        self.inverse_or_not(determinant)
        print(self.inverse_matrix)
'''
