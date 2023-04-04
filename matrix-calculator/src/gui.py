from tkinter import *

from tkinter import ttk, messagebox

from basic import *

from eigens import *

from determinantInverse import *

from system_of_equations import *





root = Tk()

root.geometry("700x400")

root.title("Matrix Operation Tool")



tabControl = ttk.Notebook(root)



def addition_():

    a = a1.get()

    b = b1.get()

    result = addition(a, b)

    result1_1.config(text=result)





def subtraction_():

    a = a2.get()

    b = b2.get()

    result = subtraction(a, b)

    result1_2.config(text=result)





def multiplication_():

    a = a3.get()

    b = b3.get()

    result = multiplication(a, b)

    result1_3.config(text=result)



eg = Eigen()



def getRows_():

    rowNum = row.get()

    result = eg.getRows(rowNum)

    resultRow.config(text=result)



def calEigen_():

    mat = matRow.get()

    result = eg.calculateGUI(mat)

    resultMat.config(text=result)

    if result == "Successful!":

        eigenvals = eg.getEigenvals()

        eigenvecs = eg.getEigenvecs()

        resultEigenVal.config(text="["+(str(eigenvals)[1:-1])+"]")

        resultEigenVec.config(text=(str(eigenvecs)[1:-1]))

    else:

        resultEigenVal.config(text="")

        resultEigenVec.config(text="")



di = DeterminantInverse()



def get_input_():

    row = row_num.get()

    result = di.get_input1(row)

    result_row.config(text=result)



def det_inverse_():

    mat = matrix.get()

    result = di.run_all(mat)

    if result == None:

        result_matrix.config(text="")

        result_det.config(text=di.det_result())

        result_inverse.config(text=str(di.inverse_result()))

    else:
        result_matrix.config(text=result)

        result_det.config(text="")

        result_inverse.config(text="")

def system_of_equations_():
    a= equ.get()
    b= equ1.get()
    c = equ2.get()
    result= systemOfEquations(a,b,c)
    resultOutcome.config(text=result)










# tab1----------------------------------------------------------



tab1 = Frame(tabControl)

tabControl.add(tab1, text="+,-, and *")



title0 = Label(tab1, text="Enter matrices line by line. Separate elements by COMMA. Separate lines by SEMICOLON.",

               fg='red')

title0.grid(column=0, row=0, columnspan=3)



# Addition

title1_1 = LabelFrame(tab1, text="Addition")

title1_1.grid(column=0, row=1, padx=10, pady=10)



label1_1_1 = Label(title1_1, text="Matrix 1=")

label1_1_1.pack()



a1 = StringVar()

entry1_1_1 = Entry(title1_1, textvariable=a1)

a1.set('')

entry1_1_1.pack()



label1_1_2 = Label(title1_1, text="Matrix 2=")

label1_1_2.pack()



b1 = StringVar()

entry1_1_2 = Entry(title1_1, textvariable=b1)

b1.set('')

entry1_1_2.pack()



butt1_1 = Button(title1_1, text="Start !", command=addition_)

butt1_1.pack()



result1_1 = Label(title1_1, text="")

result1_1.pack()



# Subtraction

title1_2 = LabelFrame(tab1, text="Subtraction")

title1_2.grid(column=1, row=1, padx=10, pady=10)



label1_2_1 = Label(title1_2, text="Matrix 1=")

label1_2_1.pack()



a2 = StringVar()

entry1_2_1 = Entry(title1_2, textvariable=a2)

a2.set('')

entry1_2_1.pack()



label1_2_2 = Label(title1_2, text="Matrix 2=")

label1_2_2.pack()



b2 = StringVar()

entry1_2_2 = Entry(title1_2, textvariable=b2)

b2.set('')

entry1_2_2.pack()



butt1_2 = Button(title1_2, text="Start !", command=subtraction_)

butt1_2.pack()



result1_2 = Label(title1_2, text="")

result1_2.pack()



# Multiplication

title1_3 = LabelFrame(tab1, text="Multiplication")

title1_3.grid(column=2, row=1, padx=10, pady=10)



label1_3_1 = Label(title1_3, text="Matrix 1=")

label1_3_1.pack()



a3 = StringVar()

entry1_3_1 = Entry(title1_3, textvariable=a3)

a3.set('')

entry1_3_1.pack()



label1_3_2 = Label(title1_3, text="Matrix 2=")

label1_3_2.pack()



b3 = StringVar()

entry1_3_2 = Entry(title1_3, textvariable=b3)

b3.set('')

entry1_3_2.pack()



butt1_3 = Button(title1_3, text="Start !", command=multiplication_)

butt1_3.pack()



result1_3 = Label(title1_3, text="")

result1_3.pack()



# tab2----------------------------------------------------------



tab2 = ttk.Frame(tabControl)

tabControl.add(tab2, text="Determinant & Inverse")



title2_0 = Label(tab2, text="Enter the matrix line by line. Separate elements by COMMA. Separate lines by SEMICOLON.",

               fg='red')

title2_0.grid(column=0, row=0, columnspan=1)



title2_1 = ttk.LabelFrame(tab2, text="Matrix Input")

title2_1.grid(column=0, row=2, padx=10, pady=10)



label2 = Label(title2_1, text="Number of rows")

label2.pack()



row_num = StringVar()

entry2_1 = Entry(title2_1, textvariable=row_num)

row_num.set('')

entry2_1.pack()



butt2_1 = Button(title2_1, text="Yes", command=get_input_)

butt2_1.pack()



result_row = Label(title2_1, text="")

result_row.pack()



labelMatrix = Label(title2_1, text="Matrix:")

labelMatrix.pack()



matrix = StringVar()

entryMatRow = Entry(title2_1, textvariable=matrix)

matrix.set('')

entryMatRow.pack()



butt2_2 = Button(title2_1, text="Start!", command=det_inverse_)

butt2_2.pack()



result_matrix = Label(title2_1, text="")

result_matrix.pack()



label_det = Label(title2_1, text="Determinant:")

label_det.pack()



result_det = Label(title2_1, text="", wraplength=220)

result_det.pack()



label_inverse = Label(title2_1, text="Inverse of matrix:")

label_inverse.pack()



result_inverse = Label(title2_1, text="", wraplength=220)

result_inverse.pack()





# tab3----------------------------------------------------------



tab3 = Frame(tabControl)

tabControl.add(tab3, text="Eigenvalue & Eigenvector")



title3_0 = Label(tab3, text="Enter the matrix line by line. Separate elements by COMMA. Separate lines by SEMICOLON.",

               fg='red')

title3_0.grid(column=0, row=0, columnspan=1)



title3_0_1 = Label(tab3, text="Please enter a dimension between 1 and 10 (in single number form, eg. 2,3,4)",

               fg='red')

title3_0_1.grid(column=0, row=1, columnspan=1)



title3_1 = LabelFrame(tab3, text="Matrix Input")

title3_1.grid(column=0, row=2, padx=10, pady=10)



labelRow = Label(title3_1, text="Number of rows=")

labelRow.pack()



row = StringVar()

entryRow = Entry(title3_1, textvariable=row)

row.set('')

entryRow.pack()



butt3_1 = Button(title3_1, text="Go!", command=getRows_)

butt3_1.pack()



resultRow = Label(title3_1, text="")

resultRow.pack()



labelMatrix = Label(title3_1, text="Matrix=")

labelMatrix.pack()



matRow = StringVar()

entryMatRow = Entry(title3_1, textvariable=matRow)

matRow.set('')

entryMatRow.pack()



butt3_2 = Button(title3_1, text="Start!", command=calEigen_)

butt3_2.pack()



resultMat = Label(title3_1, text="")

resultMat.pack()



labelEigenVal = Label(title3_1, text="Eigenvalues:")

labelEigenVal.pack()



resultEigenVal = Label(title3_1, text="", wraplength=220)

resultEigenVal.pack()



labelEigenVec = Label(title3_1, text="Eigenvectors:")

labelEigenVec.pack()



resultEigenVec = Label(title3_1, text="", wraplength=220)

resultEigenVec.pack()



# tab4----------------------------------------------------------



tab4 = ttk.Frame(tabControl)

tabControl.add(tab4, text="System of Equations")

title4_0 = Label(tab4,text="type in 3 equations each with only 3 variables x,y,z, in the form of 3x+2y+z=8",fg='red')
title4_0.grid(column=0,row=0,columnspan=1)

title4_0_1 = Label(tab4,text="if there is no x or y or z, please add a 0 before eg. x+0y+z=10",fg='red')
title4_0_1.grid(column=0,row=1,columnspan=1)

title4_1 = ttk.LabelFrame(tab4, text="System of Equations")

title4_1.grid(column=0, row=2, padx=10, pady=10)

equ=StringVar()
entry4_1 = Entry(title4_1,textvariable=equ)
equ.set('')

entry4_1.pack()

equ1=StringVar()

entry4_2 = Entry(title4_1,textvariable=equ1)
equ1.set('')
entry4_2.pack()

equ2=StringVar()

entry4_3 = Entry(title4_1,textvariable=equ2)
equ2.set('')
entry4_3.pack()



butt4 = Button(title4_1, text="Start !",command=system_of_equations_)

butt4.pack()

labeloutcome= Label(title4_1, text="outcome:")

labeloutcome.pack()


resultOutcome = Label(title4_1,text="",wraplength=220)

resultOutcome.pack()



# -------------------------------------------------------------



tabControl.pack(expand=True, fill="both")



root.mainloop()

