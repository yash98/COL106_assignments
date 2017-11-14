import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Puzzle {

    public HashMap<String, Node> Graph;
    public String[] moveArr;
    public HashSet<String> clear;

    private class Node {
        public Board state;
        public String str;
        public int[] moveTile;
        public String[] neighbours;
        public int pathLength;
        public Node previous;
        public String prevMove;

        public Node(String str, int pathLength, Node previous, String preMove) {
            this.state = new Board(str);
            this.str = str;
            this.moveTile = this.state.movesPossible();
            this.neighbours = this.state.neighbours();
            this.pathLength = pathLength;
            this.previous = previous;
            this.prevMove = preMove;
        }

        public Node(int dummyLength){
            this.pathLength = dummyLength;
        }

        public void reset() {
            this.previous = null;
            this.prevMove = "";
            this.pathLength = 2147483647;
        }

        public void set(Node previous, String prevMove, int pathLength) {
            this.previous = previous;
            this.prevMove = prevMove;
            this.pathLength = pathLength;
        }

        public int compareTo(Node n) {
            return this.pathLength - n.pathLength;
        }
    }

    private class Board {
        public int[][] board;
        public int[] gPosition;

        public Board(String str) {
            this.board = new int[3][3];
            String s;
            for (int i=0; i<3; i++) {
                for (int j=0; j<3; j++) {
                    if((s=str.substring(3*i+j,3*i+j+1)).equals("G")) {
                        this.gPosition = new int[]{i,j};
                        this.board[i][j] = -1;
                    } else {
                        this.board[i][j] = Integer.parseInt(s);
                    }
                }
            }
        }

        public String[] neighbours() {
            int x = this.gPosition[0];
            int y = this.gPosition[1];

            String[] calcNeighbours = new String[4];

            if (Puzzle.onBoard(x+1,y)) {
                int[][] newBoard = Puzzle.copyArray(this.board);
                calcNeighbours[0] = Puzzle.swapAndStringify(newBoard, x, y, x+1, y);
            } else {
                calcNeighbours[0] = "";
            }

            if (Puzzle.onBoard(x,y-1)) {
                int[][] newBoard = Puzzle.copyArray(this.board);
                calcNeighbours[1] = Puzzle.swapAndStringify(newBoard, x, y, x, y-1);
            } else {
                calcNeighbours[1] = "";
            }

            if (Puzzle.onBoard(x-1,y)) {
                int[][] newBoard = Puzzle.copyArray(this.board);
                calcNeighbours[2] = Puzzle.swapAndStringify(newBoard, x, y, x-1, y);
            } else {
                calcNeighbours[2] = "";
            }

            if (Puzzle.onBoard(x,y+1)) {
                int[][] newBoard = Puzzle.copyArray(this.board);
                calcNeighbours[3] = Puzzle.swapAndStringify(newBoard, x, y, x, y+1);
            } else {
                calcNeighbours[3] = "";
            }


            return calcNeighbours;
        }

        public int[] movesPossible() {
            int x = this.gPosition[0];
            int y = this.gPosition[1];

            int[] calcmoves = new int[4];

            if (Puzzle.onBoard(x+1,y)) {
                calcmoves[0] = this.board[x+1][y];
            } else {
                calcmoves[0] = -1;
            }

            if (Puzzle.onBoard(x,y-1)) {
                calcmoves[1] = this.board[x][y-1];
            } else {
                calcmoves[1] = -1;
            }

            if (Puzzle.onBoard(x-1,y)) {
                calcmoves[2] = this.board[x-1][y];
            } else {
                calcmoves[2] = -1;
            }

            if (Puzzle.onBoard(x,y+1)) {
                calcmoves[3] = this.board[x][y+1];
            } else {
                calcmoves[3] = -1;
            }


            return calcmoves;
        }

        public String toString() {
            int[][] arr = this.board;
            StringBuilder s = new StringBuilder("");

            int c;

            for (int i=0; i<2; i++) {
                for (int j=0; j<2; j++) {
                    if ((c = arr[i][j])==-1){
                        s.append("G");
                    } else {
                        s.append(Integer.toString(c));
                    }
                }
            }

            return s.toString();
        }

    }

    private class heap {
        ArrayList<Node> elements;

        public heap() {
            this.elements = new ArrayList<>();
            this.elements.add(null);
        }

        public Node findMin() {
            return this.elements.get(1);
        }

        public void deleteMin() {
            this.elements.set(1, new Node(2147483647));
            int place = this.percDown(1);
            this.elements.remove(place);
        }

        public Node getMin() {
            Node currentMin = findMin();
            this.deleteMin();
            return currentMin;
        }

        public int add(Node n) {
            this.elements.add(n);
            return this.percUp(this.elements.size());
        }

        public int percUp(int index) {
            int i = index;
            Node parent;
            Node self;
            while(i/2>0) {
                self = this.elements.get(i);
                parent = this.elements.get(i/2);
                if (parent.compareTo(self)>0) {
                    this.elements.set(i/2, self);
                    this.elements.set(i, parent);
                    i = i/2;
                } else {
                    break;
                }
            }
            return i;
        }

        public int percDown(int index) {
            int i = index;
            int min;
            Node minChild;
            Node current;
            Node child1;
            Node child2;
            while(2*i+1<this.elements.size()) {
                current = this.elements.get(i);
                child1 = this.elements.get(2*i);
                child2 = this.elements.get(2*i+1);
                if (child1.compareTo(child2)<=0) {
                    minChild = child1;
                    min = 2*i;
                } else {
                    minChild = child2;
                    min = 2*i+1;
                }
                if (current.compareTo(minChild)>0) {
                    this.elements.set(i, minChild);
                    this.elements.set(min, current);
                    i = min;
                } else {
                    break;
                }
            }
            return i;
        }

        public boolean isEmpty() {
            return elements.size()==1;
        }
    }

    public Puzzle() {
        this.Graph = new HashMap<>();
        this.moveArr = new String[]{"U","R","D","L"};
        this.clear = new HashSet<>();
    }


    public static boolean onBoard(int i, int j) {
        return (0<=i & i<=2 & 0<=j & j<=2);
    }

    public static int[][] copyArray(int[][] arr) {
        int[][] newArr = new int[3][3];
        for (int i=0; i<2; i++) {
            for (int j=0; j<2; j++) {
                newArr[i][j] = arr[i][j];
            }
        }
        return newArr;
    }

    public static String swapAndStringify(int[][] arr, int x1, int y1, int x2, int y2) {
        int a = arr[x1][y1];
        int b = arr[x2][y2];
        arr[x1][y1] = b;
        arr[x2][y2] = a;

        StringBuilder s = new StringBuilder("");

        int c;

        for (int i=0; i<2; i++) {
            for (int j=0; j<2; j++) {
                if ((c = arr[i][j])==-1){
                    s.append("G");
                } else {
                    s.append(Integer.toString(c));
                }
            }
        }

        return s.toString();
    }

    public void resetGraph(HashSet<String> hs) {
        for (String s: hs) {
            this.Graph.get(s).reset();
        }
    }

    public boolean djikstra(String start, String end, int[] cost) {
        Node init;
        Node current;
        HashSet<String> cloud = new HashSet<>();
        heap ordering = new heap();
        String[] neighbourSet;
        Node neighbourTemp;


        if (this.Graph.containsKey(start)) {
            init = this.Graph.get(start);
            init.pathLength = 0;
        } else {
            init = new Node(start, 0, null, "");
            this.Graph.put(start, init);
        }
        ordering.add(init);
        cloud.add(start);

        while(!ordering.isEmpty()) {
            current = ordering.getMin();
            if (current.str.equals(end)) {
                this.clear = cloud;
                return true;
            }
            cloud.add(current.str);
            neighbourSet = current.neighbours;
            for (int i=0; i<4; i++) {
                if (!neighbourSet[i].equals("") & !cloud.contains(neighbourSet[i])) {
                    if(this.Graph.containsKey(neighbourSet[i])) {
                        neighbourTemp = this.Graph.get(neighbourSet[i]);
                        if (neighbourTemp.pathLength>current.pathLength+current.moveTile[i]) {
                            neighbourTemp.set(current, Integer.toString(current.moveTile[i])+this.moveArr[i],
                                    current.pathLength+current.moveTile[i]);
                        }
                    } else {
                        neighbourTemp = new Node(neighbourSet[i], current.pathLength+current.moveTile[i],
                                current, Integer.toString(current.moveTile[i])+this.moveArr[i]);
                    }
                    ordering.add(neighbourTemp);
                }
            }
        }

        this.clear = cloud;
        return false;
    }

    public ArrayList<String> optimalPath(String start, String end, String[] stringCosts) {
        if (start.equals(end)) {
            return new ArrayList<String>(){{add("0");add("0");}};
        }

        int[] cost = new int[8];

        for (int i=0; i<8; i++) {
            cost[i] = Integer.parseInt(stringCosts[i]);
        }

        boolean isPath = this.djikstra(start, end, cost);

        if (isPath) {
            Node init = this.Graph.get(start);
            Node current = init;
            Node finish = this.Graph.get(end);
            int steps=0;
            ArrayList<String> answer = new ArrayList<>();
            answer.add(Integer.toString(finish.pathLength));

            while (!(current.previous==null)) {
                answer.add(1, current.prevMove);
                steps++;
                current = current.previous;
            }

            answer.add(0, Integer.toString(steps));

            this.resetGraph(this.clear);
            return answer;
        } else {
            this.resetGraph(this.clear);
            return new ArrayList<String>(){{add("-1");add("-1");}};
        }
    }

    public static void main(String args[]) {
        FileReader inputFile = null;
        BufferedReader input = null;
        Writer output = null;

        try {

            Puzzle eightPuzzle = new Puzzle();

            inputFile = new FileReader(args[0]);
            input = new BufferedReader(inputFile);
            output = new FileWriter(args[1]);

            int size = Integer.parseInt(input.readLine());

            String line;
            String start;
            String end;
            String[] splitted;
            ArrayList<String> path;
            for (int i=0; i<size; i++) {
                line = input.readLine();
                splitted = line.split("\\s");
                start = splitted[0];
                end = splitted[1];
                line = input.readLine();
                splitted = line.split("\\s");
                path = eightPuzzle.optimalPath(start, end, splitted);
                for (int j=0; j<path.size(); j++) {
                    output.write(path.get(i));
                    if (j==1 && j==path.size()-1) {
                        output.write("\n");
                        output.write("\n");
                    } else if (j==1 || j==path.size()-1) {
                        output.write("\n");
                    } else {
                        output.write(" ");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputFile != null)
                    inputFile.close();
                if (input != null)
                    input.close();
                if (output != null)
                    output.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
