#No import statements.

# constants
MAX_CONSTANTS = 10
CONNECTIVES = [">","^","v"]
PROPOSITIONS = ["p","q","r","s"]
PREDICATES = ["P","Q","R","S"]
VARIABLES = ["x","y","z","w"]
QUANTIFIERS = ["E","A"]
NEGATIONS = ["-p","-q","-r","-s","-P","-Q","-R","-S"]
CONSTANTS = ["a","b","c","d","e","f","g","h","i","j"]



# global variables
left_formula = ""
connective = ""
right_formula = ""



# auxilary functions
def reset_formulae():
    global left_formula
    left_formula = ""
    global connective
    connective = ""
    global right_formula
    right_formula = ""

def get_connective_index(fmla):
    length = len(fmla)
    index = 0
    bracket_count = 0
    while index != length and (fmla[index] not in CONNECTIVES or bracket_count != 0):
        if fmla[index] == "(":
            bracket_count += 1
        elif fmla[index] == ")":
            bracket_count -= 1
        index += 1
    return index

def is_fol(fmla):
    return fmla[0] in QUANTIFIERS

def is_neg(fmla):
    return fmla[0] == "-"

def is_bracket(token):
    return token == "("

def is_proposition(token):
    return token in PROPOSITIONS

def is_atom(fmla):
    return fmla[0] in PREDICATES

def equal_bracket(fmla): # incomplete binary connectives will have inequal brackets
    left = 0
    right = 0
    for token in fmla:
        if token == '(':
            left += 1
        elif token == ')':
            right += 1
    return left == right



# auxilary functions for parse() method
def empty_formulae():
    return left_formula == "" or right_formula == ""

def set_formulae(fmla):
    index = get_connective_index(fmla)
    if index < len(fmla):
        global left_formula
        left_formula = fmla[:index]
        global connective
        connective = fmla[index]
        global right_formula
        right_formula = fmla[index+1:]
    return

def binary_type(fmla):
    set_formulae(fmla[1:len(fmla)-1])
    if empty_formulae():
        return 0
    if is_fol(left_formula) or is_fol(right_formula):
        return 5
    else:
        return 8

def fol_type(fmla): # quantified formula starts with E or A without extra brackets
    if fmla[0] == "E":
        return 4
    elif fmla[0] == "A":
        return 3
    else:
        return 0

def neg_type(fmla):
    if is_fol(fmla) or is_atom(fmla): # first order formula comes first
        return 2
    elif is_bracket(fmla[0]): # binary connective comes first
        set_formulae(fmla[1:len(fmla)-1])
        if empty_formulae():
            return 0
        return 7
    elif is_proposition(fmla[0]) or is_neg(fmla):
        return 7
    else:
        return 0



# auxilary functions for sat() method
def get_formulae(fmla):
    index = get_connective_index(fmla)
    if index < len(fmla):
        left = fmla[:index]
        con = fmla[index]
        right = fmla[index+1:]
        return (left,con,right)
    return ("","","")

def remove_closed(tableau):
    index_list = []
    index = 0
    for branch in tableau:
        for formula in branch:
            if is_neg(formula):
                if formula[1] in PROPOSITIONS:
                    for fmla in branch:
                        if fmla[0] == formula[1]:
                            index_list.append(index)
                elif formula[1] in PREDICATES:
                    for fmla in branch:
                        if fmla == formula[1:]:
                            index_list.append(index)
        index += 1

    count = 0
    for idx in index_list:
        tableau.pop(idx-count)
        count += 1

    return tableau

def is_empty_tableau(tableau):
    return tableau == []

def get_fmla_index(branch):
    index = 0
    for formula in branch:
        if is_bracket(formula[0]) or is_fol(formula):
            break
        if is_neg(formula):
            if not formula[1] in PROPOSITIONS and not formula[1] in PREDICATES:
                break
        index += 1
    return index

def alpha_beta_exp(left,con,right):
    if con == "^":
        return [left,right]
    if con == "v":
        return [[left],[right]]
    if con == ">":
        return [["-"+left],[right]]
    return ["invalid"]

def delta_exp(variable, scope, remainder, constants):
    scope = scope.replace(variable, CONSTANTS[constants])
    return [(scope+remainder)], (constants+1)

def gamma_exp(variable, scope, remainder):
    index = 0
    result_list = []
    while index != 10:
        new_scope = scope.replace(variable, CONSTANTS[index])
        result_list.append(new_scope+remainder)
        index += 1
    return result_list

def negate_formulae(left,con,right):
    if con == "^":
        return "-"+left,"v","-"+right
    if con == "v":
        return "-"+left,"^","-"+right
    if con == ">":
        return left,"^","-"+right
    return "","",""

def get_scope(formula):
    formula_length = len(formula)
    variable = formula[1]
    index = 3
    if formula[2] == "-":
        index = 4

    if formula[index-1] == "(":
        bracket_count = 1
        while index < formula_length and bracket_count != 0:
            letter = formula[index]
            if letter == ")":
                bracket_count -= 1
            elif letter == "(":
                bracket_count += 1
            index += 1
        if index != formula_length:
            return variable, formula[2:index], formula[index:]
        return variable, formula[2:formula_length], ""

    if formula[index-1] in QUANTIFIERS:
        return variable, formula[2:], ""

    while index < formula_length:
        if formula[index] in CONNECTIVES:
            break
        index += 1
    if index != formula_length:
        return variable, formula[2:index], formula[index:]
    return variable, formula[2:], ""

def analyse_fmla(formula, constants):
    if is_neg(formula):
        if is_neg(formula[1:]):
            return [formula[2:]], constants
        if is_bracket(formula[1]):
            left, con, right = get_formulae(formula[2:len(formula)-1])
            left, con, right = negate_formulae(left,con,right)
            return alpha_beta_exp(left,con,right), constants
        if is_fol(formula[1:]):
            variable, scope, remainder = get_scope(formula[1:3]+"-"+formula[3:])
            if formula[1] == "A":
                return delta_exp(variable, scope, remainder, constants)
            return gamma_exp(variable, scope, remainder), constants # check!
    elif is_bracket(formula[0]):
        left, con, right = get_formulae(formula[1:len(formula)-1])
        return alpha_beta_exp(left,con,right), constants
    elif is_fol(formula):
        variable, scope, remainder = get_scope(formula)
        if formula[0] == "E":
            return delta_exp(variable, scope, remainder, constants)
        return gamma_exp(variable, scope, remainder), constants
    return ["invalid"], constants

def is_simplest(list):
    return False not in list



# Parse a formula, consult parseOutputs for return values.
def parse(fmla):
    reset_formulae()
    if not equal_bracket(fmla):
        return 0
    if is_bracket(fmla[0]): # binary connectives
        return binary_type(fmla)
    if is_fol(fmla):
        return fol_type(fmla)
    if is_neg(fmla):
        return neg_type(fmla[1:])
    if is_atom(fmla):
        return 1
    if is_proposition(fmla):
        return 6
    return 0 # does not match any of the above, not a formula



# Return the LHS of a binary connective formula
def lhs(fmla):
    return left_formula

# Return the connective symbol of a binary connective formula
def con(fmla):
    return connective

# Return the RHS symbol of a binary connective formula
def rhs(fmla):
    return right_formula



# You may choose to represent a theory as a set or a list
def theory(fmla): #initialise a theory with a single formula in it
    return [fmla]



#check for satisfiability
def sat(tableau):
    result = 2
    constant_num = 0

    while True:
        tableau = remove_closed(tableau)
        if is_empty_tableau(tableau):
            result = 0
            break

        simplest_list = []
        for branch in tableau:
            fmla_index = get_fmla_index(branch)
            if fmla_index == len(branch):
                simplest_list.append(True)
                continue

            result_list, constant_num = analyse_fmla(branch.pop(fmla_index), constant_num)
            if constant_num == MAX_CONSTANTS:
                simplest_list.append(False)
                result = 2
                break
            elif type(result_list[0]) == list:
                tableau.remove(branch)
                for result_fmla in result_list:
                    tableau.append(branch+result_fmla)
                simplest_list.append(False)
                break
            else:
                for result_fmla in result_list:
                    branch.append(result_fmla)
                simplest_list.append(False)

        if constant_num == MAX_CONSTANTS:
            break

        if is_simplest(simplest_list):
            result = 1
            break
    return result



#DO NOT MODIFY THE CODE BELOW
f = open('input.txt')

parseOutputs = ['not a formula',
                'an atom',
                'a negation of a first order logic formula',
                'a universally quantified formula',
                'an existentially quantified formula',
                'a binary connective first order formula',
                'a proposition',
                'a negation of a propositional formula',
                'a binary connective propositional formula']

satOutput = ['is not satisfiable', 'is satisfiable', 'may or may not be satisfiable']



firstline = f.readline()

PARSE = False
if 'PARSE' in firstline:
    PARSE = True

SAT = False
if 'SAT' in firstline:
    SAT = True

for line in f:
    if line[-1] == '\n':
        line = line[:-1]
    parsed = parse(line)

    if PARSE:
        output = "%s is %s." % (line, parseOutputs[parsed])
        if parsed in [5,8]:
            output += " Its left hand side is %s, its connective is %s, and its right hand side is %s." % (lhs(line), con(line) ,rhs(line))
        print(output)

    if SAT:
        if parsed:
            tableau = [theory(line)]
            print('%s %s.' % (line, satOutput[sat(tableau)]))
        else:
            print('%s is not a formula.' % line)
