import numpy as np
from numpy.linalg import det
import re


def systemOfEquations(a,b,c):
    try:
        lt = []
        d = {}
        st=a.split("=")
        st1=b.split("=")
        st2=c.split("=")
        r = re.compile('(-?\d?)[xyz]')
        aa = re.findall(r, st[0])
        bb = re.findall(r, st1[0])
        cc = re.findall(r, st2[0])

        for j in range(3):
                    if aa[j] == '':
                        aa[j] = 1
                    if aa[j] == '-':
                        aa[j] = -1
                    if bb[j] == '':
                        bb[j] = 1
                    if bb[j] == '-':
                        bb[j] = -1
                    if cc[j] == '':
                        cc[j] = 1
                    if cc[j] == '-':
                        cc[j] = -1
        d = {
            'x': int(aa[0]),
            'y': int(aa[1]),
            'z': int(aa[2]),
            'sum':int(st[1])
        }
        lt.append(d)
        e = {
            'x': int(bb[0]),
            'y': int(bb[1]),
            'z': int(bb[2]),
            'sum':int(st1[1])
        }
        lt.append(e)
        f = {
            'x': int(cc[0]),
            'y': int(cc[1]),
            'z': int(cc[2]),
            'sum':int(st2[1])
        }
        lt.append(f)

        matrix=np.array([[lt[0]['x'],lt[0]['y'],lt[0]['z']],[lt[1]['x'],lt[1]['y'],lt[1]['z']],[lt[2]['x'],lt[2]['y'],lt[2]['z']]])


        if np.isclose(np.linalg.det(matrix),0):
            return"the matrix is singular"
        else:
            t = lt[0]['x'] / lt[1]['x']
            for i in lt[0]:
                lt[1][i] = lt[1][i] * t - lt[0][i]

            t1 = lt[0]['x'] / lt[2]['x']
            for i in lt[0]:
               lt[2][i] = lt[2][i] * t1 - lt[0][i]

            t2 = lt[1]['y'] / lt[2]['y']

            for i in lt[0]:
                lt[2][i] = lt[2][i] * t2 - lt[1][i]


            z = lt[2]['sum'] / lt[2]['z']
            y = (lt[1]['sum'] - lt[1]['z'] * z) / lt[1]['y']
            x = (lt[0]['sum'] - lt[0]['z'] * z - lt[0]['y'] * y)/lt[0]['x']
            return "the unique solution is",x,y,z

    except IndexError:
        return "please enter 3 variables equations according to format"
