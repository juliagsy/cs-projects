import numpy
import copy

class Eigen():
    def __init__(self):
        self.matrix = None
        self.copyMatrix = None
        self.dimension = None
        self.eigenvalues = None
        self.eigenvectors = None

    def isSquare(self):
        rowNum = 0
        for row in self.matrix:
            rowNum += 1
        if rowNum != self.dimension:
            return False
        for row in self.matrix:
            columnNum = 0
            for column in row:
                columnNum += 1
            if columnNum != rowNum:
                return False
        return True

    def addLambda(self): # lambda represents eigenvalue
        index = 0
        while index < self.dimension:
            val = self.copyMatrix[index][index]
            self.copyMatrix[index][index] = [-1,val]
            index += 1

    def crossProduct(self,matrix):
        first = numpy.polymul(matrix[0][0],matrix[1][1])
        second = numpy.polymul(matrix[1][0],matrix[0][1])
        result = numpy.polysub(first,second)
        return result.tolist()

    def checkMat(self,column):
        result = True
        for row in self.copyMatrix[column:]:
            if row[column] != 0:
                if self.copyMatrix.index(row) != column:
                    self.switchRow(column,self.copyMatrix.index(row))
                    result = False
                    break
        return result

    def switchRow(self,targetRow,switchRow):
        buffer = self.copyMatrix[targetRow]
        self.copyMatrix[targetRow] = self.copyMatrix[switchRow]
        self.copyMatrix[switchRow] = buffer

    def findDet(self,matrix,dimension): #determinant finder
        if dimension == 2:
            return self.crossProduct(matrix)
        else:
            columnNow = 0
            determinant = 0
            while columnNow < dimension:
                first = matrix[0][columnNow]
                detMat = []
                row = 1
                while row < dimension:
                    column = 0
                    rowMat = []
                    while column < dimension:
                        if column == columnNow:
                            column += 1
                            continue
                        rowMat.append(matrix[row][column]) # take parts of matrix responsible for next steps
                        column += 1
                    detMat.append(rowMat)
                    row += 1
                second = self.findDet(detMat,dimension-1)
                factor = numpy.polymul(first,second)
                if columnNow % 2 == 0:
                    determinant = numpy.polyadd(determinant,factor)
                else:
                    determinant = numpy.polysub(determinant,factor)
                columnNow += 1
        return determinant.tolist()

    def minusEigenval(self,val): # deduct the factor before Gaussian elmination phase
        self.copyMatrix = copy.deepcopy(self.matrix)
        index = 0
        while index < self.dimension:
            self.copyMatrix[index][index] -= val
            index += 1

    def gaussianTriangle(self):
        times = 0
        while times != self.dimension - 1:
            rowTarget = times + 1
            columnTarget = times
            original = self.copyMatrix[times][times] # reference element for arithmetic operations (always on the diagonal)
            zeros = self.checkMat(times)
            if zeros is True: # True means whole column is 0, no need to perform Gaussian
                times += 1
                continue
            while rowTarget < self.dimension:
                target = self.copyMatrix[rowTarget][columnTarget] # target to become 0
                factor = target/original
                trackIndex = 0
                while trackIndex < self.dimension: # parse through elements of the same row for arithmetic operations
                    self.copyMatrix[rowTarget][trackIndex] -= (self.copyMatrix[times][trackIndex] * factor)
                    trackIndex += 1
                rowTarget += 1
            times += 1
        self.checkDiagonal() # ensure diagonals are 1

    def checkDiagonal(self):
        index = 0
        while index < self.dimension - 1: # last element on the diagonal is definitely 0
            current = self.copyMatrix[index][index]
            if current == 0:
                index += 1
                continue
            if current != 1:
                factor = 1/current
                column = index
                while column < self.dimension:
                    self.copyMatrix[index][column] *= factor
                    column += 1
            index += 1
        self.gaussianVector()

    def gaussianVector(self): # make elements except the ones on diagonal and last column 0 for easier vector computation
        row = self.dimension - 3 # start with third last row
        while row >= 0:
            column = self.dimension - 2 # always start with second last column
            while column > row: # only look at elements between diagonal and last elements
                current = self.copyMatrix[row][column]
                if current != 0:
                    factorRow = column
                    factorCol = column
                    factor = current
                    while factorCol < self.dimension:
                        self.copyMatrix[row][factorCol] -= (self.copyMatrix[factorRow][factorCol] * factor)
                        factorCol += 1
                column -= 1
            row -= 1

    def getVector(self): # copyMatrix could now be extracted for eigenvector answer
        answer = []
        column = self.dimension - 1
        row = 0
        while row < self.dimension - 1:
            try:
                answer.append(round((-self.copyMatrix[row][column]),3))
            except:
                answer.append(-self.copyMatrix[row][column])
            row += 1
        answer.append(1)
        return answer

    def eigenValue(self,polycoeff): # factorise equations to get values
        newVal = []
        vals = numpy.roots(polycoeff)
        vals = vals.tolist()
        for val in vals:
            try:
                newVal.append(round(val,3))
            except:
                newVal.append(val)
        return newVal

    def eigenVector(self,eigenvals):
        eigenvecs = []
        for val in eigenvals:
            self.minusEigenval(val) # update copyMatrix for next phase
            self.gaussianTriangle()
            vector = self.getVector()
            eigenvecs.append(vector)
        return eigenvecs

    def calculate(self):
        self.addLambda() # operating on copyMatrix to retain original
        determinant = self.findDet(self.copyMatrix,self.dimension)
        self.eigenvalues = self.eigenValue(determinant)
        self.eigenvectors = self.eigenVector(self.eigenvalues)

    def getRows(self,rowStr):
        try:
            selection = int(rowStr)
        except:
            return ("Please enter single number only!")
        if selection < 1 or selection > 10 :
            return ("Dimension is not within limit! Please enter number between 1 and 10!")
        self.dimension = selection
        return ("Please input matrix below.")

    def getMatrix(self,matrixStr):
        self.matrix = []
        newrow = []
        result = ""
        try:
            matrix = matrixStr.split(';')
        except:
            return "Please enter a valid matrix!"
        for row in matrix:
            newrow = []
            try:
                row = row.split(',')
                try:
                    for elem in row:
                        newrow.append(int(elem))
                    self.matrix.append(newrow)
                except:
                    result = "Please enter valid real numbers!"
                    break
            except:
                result = "Please enter a correctly formatted matrix!"
                break
        return result

    def calculateGUI(self,matrixStr):
        result = self.getMatrix(matrixStr)
        if result != "":
            return result
        self.copyMatrix = copy.deepcopy(self.matrix)
        check = self.isSquare()
        if check is False:
            result = "Please enter a square matrix with the stated dimension!"
            return result
        self.calculate()
        return "Successful!"

    def getEigenvals(self):
        return self.eigenvalues

    def getEigenvecs(self):
        return self.eigenvectors
