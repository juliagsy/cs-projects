import numpy as np

def addition(a, b):
    a = np.matrix(a)
    b = np.matrix(b)
    if np.size(a, 0) == np.size(b, 0) and np.size(a, 1) == np.size(b, 1):
        return a + b
    else:
        return "Check the size of the matrices."


def subtraction(a, b):
    a = np.matrix(a)
    b = np.matrix(b)
    if np.size(a, 0) == np.size(b, 0) and np.size(a, 1) == np.size(b, 1):
        return a - b
    else:
        return "Check the size of the matrices."


def multiplication(a, b):
    a = np.matrix(a)
    b = np.matrix(b)
    if np.size(a, 0) == np.size(b, 1) and np.size(a, 1) == np.size(b, 0):
        return a * b
    else:
        return "Check the size of the matrices."
