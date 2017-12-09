import random
import sys

def build_random_grid():
    grid = [0, 1, 2, 3, 4, 5, 6, 7, 8]
    random.shuffle(grid)
    return grid

def try_up(grid): return 0 not in grid[:3]
def try_down(grid): return 0 not in grid[6:]
def try_left(grid): return 0 not in grid[0::3]
def try_right(grid): return 0 not in grid[2::3]

def swap(grid, zpos, other, char): 
    grid[zpos], grid[other] = grid[other], grid[zpos] 
    return str(grid[zpos]) + char

def move_up(grid, zpos): return swap(grid, zpos, zpos - 3, 'D')
def move_down(grid, zpos): return swap(grid, zpos, zpos + 3, 'U')
def move_left(grid, zpos): return swap(grid, zpos, zpos - 1, 'R')
def move_right(grid, zpos): return swap(grid, zpos, zpos + 1, 'L')

def perform_random_move(grid, move=None):
    """ shows the direction of motion of zero """
    zpos = grid.index(0)
    move_functions = [move_up, move_down, move_left, move_right]
    test_functions = [try_up, try_down, try_left, try_right]
    valid_functions = [move_functions[i] for i in [0, 1, 2, 3] if test_functions[i](grid)]
    randnum = random.randint(0, len(valid_functions) - 1)
    return grid, valid_functions[randnum](grid, zpos) 

def build_state_string(grid):
    return ''.join([str(key) if key > 0 else 'G' for key in grid])

def verify_path(start_str, end_str, moves):
    def build_board_from_string(string):
        return [int(char) if char != 'G' else 0 for char in string]

    def perform_move(grid, move):
        key, direction = int(move[0]), move[1]
        check_function = {'D': try_up, 'U': try_down, 'R': try_left, 'L': try_right}
        move_functions = {'D': move_up, 'U': move_down, 'R': move_left, 'L': move_right}
        allowed_functions = { motion: move_functions[motion] for motion in 'ULDR' if check_function[motion] } 
        state_string = build_state_string(grid)

        if direction not in allowed_functions:
            print("DIRECTION ERROR: Can't perform the move {} on {}".format(move, build_state_string(grid)))
            exit(0)
        if move != allowed_functions[direction](grid, grid.index(0)):
            print("POSITION ERROR: Can't perform the move {} on {}".format(move, state_string))
            exit(0)

    grid = build_board_from_string(start_str)
    for move in moves:
        perform_move(grid, move)
    final_str = build_state_string(grid)
    if final_str != end_str:
        print("FINAL DESTINATION ERROR: After performing the moves, you reach {}, instead of {}".format(final_str, end_str))
        exit(0)

def verify_cost(output_cost, path, cost_fn):
    correct_cost = sum([cost_fn[int(move[0])] for move in path])
    if correct_cost != output_cost:
        print("COST ERROR: Cost of the output path is {}, but you reported {}".format(correct_cost, output_cost))
        exit(0)


def verify_pathlen(pathlen, path):
    if pathlen != len(path): 
        print("PATH LENGTH ERROR: reported #moves is {} but {} moves found.".format(pathlen, len(path))) 
        exit(0)

def handle_inconsistent(pathlen, cost, path):
    if not (pathlen == -1 and cost == -1 and path == []):
        print("NOT REACHABLE CASE: Presentation error in while marking it as not reachable.")
        exit(0)
    return True

def verify_solution(input_start_str, input_end_str, input_cost_fn, output_pathlen, output_cost, output_path):
    if output_pathlen < 0 or output_cost < 0:
        if handle_inconsistent(output_pathlen, output_cost, output_path): return
    verify_pathlen(output_pathlen, output_path)
    verify_path(input_start_str, input_end_str, output_path)
    verify_cost(output_cost, output_path, input_cost_fn)


def parse_input_testcase(line1, line2, t):
    start_str, end_str = [string.strip() for string in line1.split()]
    costfn = {position + 1: int(number.strip()) for position, number in enumerate(line2.split())}
    if len(costfn) != 8 or len(start_str) != 9 or len(end_str) != 9:
        print("INPUT ERROR : for testcase {} ".format(t))
    return start_str, end_str, costfn

def parse_inputfile(filename):
    with open(filename) as f:
        inputs = [line.strip() for line in list(f)]
    T = int(inputs[0])
    inputs = [parse_input_testcase(inputs[2*i + 1], inputs[2*i + 2], 1 + i) for i in range(T)]
    return T, inputs

def parse_output_testcase(line1, line2, t):
    pathlen, cost = [int(token.strip()) for token in line1.split()]
    moves = [token.strip() for token in line2.split()]
    return pathlen, cost, moves

def parse_outputfile(filename, T):
    with open(filename) as f:
        lines = [line.strip() for line in list(f)]
    lines = lines[0: 2 * T]
    outputs = [parse_output_testcase(lines[2 * i], lines[2 * i + 1], i+1) for i in range(T)]
    return outputs

def format_checker(inputfile, outputfile):
    T, inputs = parse_inputfile(inputfile)
    outputs = parse_outputfile(outputfile, T)

    for testcase in range(T):
        print("Starting to verify testcase {}".format(testcase))
        start_str, end_str, costfn = inputs[testcase]
        pathlen, cost, moves = outputs[testcase]
        verify_solution(start_str, end_str, costfn, pathlen, cost, moves)
        print("...Done")

def random_moves(moves):
    grid = build_random_grid()
    start_state = build_state_string(grid)
    path, move = [], None
    for _ in range(moves):
        grid, move = perform_random_move(grid, move)
        path.append(move)
    end_state = build_state_string(grid)
    print('{} {}'.format(start_state, end_state))
    print(' '.join(path))


random.seed(None)
MODE = "FORMAT_CHECKER"
# MODE = "RANDOM_MOVES"
if __name__ == '__main__':
    if MODE == "FORMAT_CHECKER":
        format_checker(sys.argv[1], sys.argv[2])
    else:
        random_moves(int(sys.argv[1]))
