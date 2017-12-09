import java.io.*;
import java.util.ArrayList;
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
        public int pathCost;
        public Node previous;
        public String prevMove;
        public int steps;

        public Node(String str, int pathCost, Node previous, String preMove, int steps) {
            this.state = new Board(str);
            this.str = str;
            Object[] arr = this.state.neighboursAndMoves();
            this.moveTile = (int[]) arr[1];
            this.neighbours = (String[]) arr[0];
            this.pathCost = pathCost;
            this.previous = previous;
            this.prevMove = preMove;
            this.steps = steps;
        }

        public Node(int dummyCost, int dummySteps){
            this.pathCost = dummyCost;
            this.steps = dummySteps;
        }

        public void reset() {
            this.previous = null;
            this.prevMove = "";
            this.pathCost = 2147483647;
            this.steps = 2147483647;
        }

        public void set(Node previous, String prevMove, int pathCost, int steps) {
            this.previous = previous;
            this.prevMove = prevMove;
            this.pathCost = pathCost;
            this.steps = steps;
        }

        public int compareTo(Node n) {
            int i;
            if ((i = this.pathCost - n.pathCost)==0) {
                return this.steps - n.steps;
            } else {
                return i;
            }
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

        public Object[] neighboursAndMoves() {
            int x = this.gPosition[0];
            int y = this.gPosition[1];

            String[] calcNeighbours = new String[4];
            int[] calcmoves = new int[4];

            if (Puzzle.onBoard(x+1,y)) {
                int[][] newBoard = Puzzle.copyArray(this.board);
                calcNeighbours[0] = Puzzle.swapAndStringify(newBoard, x, y, x+1, y);
                calcmoves[0] = this.board[x+1][y];
            } else {
                calcNeighbours[0] = "";
                calcmoves[0] = -1;
            }

            if (Puzzle.onBoard(x,y-1)) {
                int[][] newBoard = Puzzle.copyArray(this.board);
                calcNeighbours[1] = Puzzle.swapAndStringify(newBoard, x, y, x, y-1);
                calcmoves[1] = this.board[x][y-1];
            } else {
                calcNeighbours[1] = "";
                calcmoves[1] = -1;

            }

            if (Puzzle.onBoard(x-1,y)) {
                int[][] newBoard = Puzzle.copyArray(this.board);
                calcNeighbours[2] = Puzzle.swapAndStringify(newBoard, x, y, x-1, y);
                calcmoves[2] = this.board[x-1][y];
            } else {
                calcNeighbours[2] = "";
                calcmoves[2] = -1;
            }

            if (Puzzle.onBoard(x,y+1)) {
                int[][] newBoard = Puzzle.copyArray(this.board);
                calcNeighbours[3] = Puzzle.swapAndStringify(newBoard, x, y, x, y+1);
                calcmoves[3] = this.board[x][y+1];
            } else {
                calcNeighbours[3] = "";
                calcmoves[3] = -1;
            }

            Object[] combinedArr = new Object[2];
            combinedArr[0] = calcNeighbours;
            combinedArr[1] = calcmoves;

            return combinedArr;
        }

        public String toString() {
            int[][] arr = this.board;
            StringBuilder s = new StringBuilder("");

            int c;

            for (int i=0; i<3; i++) {
                for (int j=0; j<3; j++) {
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
        HashMap<String, Integer> inHeap;

        public heap() {
            this.elements = new ArrayList<>();
            this.elements.add(null);
            this.inHeap = new HashMap<>();
        }

        public Node findMin() {
            return this.elements.get(1);
        }

        public void deleteMin() {
            Node first = this.elements.get(1);
            Node last = this.elements.get(elements.size()-1);
            this.elements.set(1, last);
            this.elements.remove(elements.size()-1);
            if (!this.isEmpty()) {
                int place = this.percDown(1);
            }
        }

        public Node getMin() {
            Node currentMin = findMin();
            this.deleteMin();
            inHeap.remove(currentMin.str);
            return currentMin;
        }

        public int add(Node n) {
            this.elements.add(n);
            this.inHeap.put(n.str, elements.size()-1);
            return this.percUp(this.elements.size()-1);
        }

        public int percUp(int index) {
            int i = index;
            Node parent;
            Node main = this.elements.get(i);
            while(i/2>0) {
                parent = this.elements.get(i/2);
                if (parent.compareTo(main)>0) {
                    this.elements.set(i, parent);
                    this.inHeap.put(parent.str, i);
                    i = i/2;
                } else {
                    break;
                }
            }
            this.elements.set(i, main);
            this.inHeap.put(main.str, i);
            return i;
        }

        public int percDown(int index) {
            int i = index;
            int min;
            Node minChild;
            Node main = this.elements.get(i);
            Node child1;
            Node child2;
            while(2*i+1<this.elements.size()) {
                child1 = this.elements.get(2*i);
                child2 = this.elements.get(2*i+1);
                if (child1.compareTo(child2)<=0) {
                    minChild = child1;
                    min = 2*i;
                } else {
                    minChild = child2;
                    min = 2*i+1;
                }
                if (main.compareTo(minChild)>0) {
                    this.elements.set(i, minChild);
                    this.inHeap.put(minChild.str, i);
                    i = min;
                } else {
                    break;
                }
            }
            this.elements.set(i, main);
            this.inHeap.put(main.str, i);
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
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
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

        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
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
        this.clear.clear();
    }

    public boolean djikstra(String start, String end, int[] cost) {
        Node init;
        Node current;
        HashSet<String> cloud = new HashSet<>();
        heap ordering = new heap();
        HashSet<String> toClear = new HashSet<>();
        String[] neighbourSet;
        Node neighbourTemp;

        boolean updated=false;

        if (this.Graph.containsKey(start)) {
            init = this.Graph.get(start);
            init.pathCost = 0;
            init.steps = 0;
        } else {
            init = new Node(start, 0, null, "", 0);
            this.Graph.put(start, init);
        }
        ordering.add(init);
        cloud.add(start);
        toClear.add(start);

        while(!ordering.isEmpty()) {

            current = ordering.getMin();

            cloud.add(current.str);

            if (current.str.equals(end)) {
                this.clear = toClear;
                return true;
            }

            neighbourSet = current.neighbours;
            for (int i=0; i<4; i++) {
                if (!neighbourSet[i].equals("") & !cloud.contains(neighbourSet[i])) {
                    if(this.Graph.containsKey(neighbourSet[i])) {
                        neighbourTemp = this.Graph.get(neighbourSet[i]);

                        if (neighbourTemp.pathCost>current.pathCost+cost[current.moveTile[i]-1]) {
                            neighbourTemp.set(current, Integer.toString(current.moveTile[i])+this.moveArr[i],
                                    current.pathCost+cost[current.moveTile[i]-1], current.steps+1);
                            updated=true;

                        } else if ((neighbourTemp.pathCost==current.pathCost+cost[current.moveTile[i]-1]) &
                                (neighbourTemp.steps>current.steps+1)) {
                            neighbourTemp.set(current, Integer.toString(current.moveTile[i])+this.moveArr[i],
                                    current.pathCost+cost[current.moveTile[i]-1], current.steps+1);
                            updated=true;

                        }
                    } else {
                        neighbourTemp = new Node(neighbourSet[i], current.pathCost+cost[current.moveTile[i]-1],
                                current, Integer.toString(current.moveTile[i])+this.moveArr[i], current.steps+1);
                        this.Graph.put(neighbourSet[i], neighbourTemp);
                        updated=true;
                    }

                    if (updated) {
                        if (!ordering.inHeap.containsKey(neighbourTemp.str)) {
                            ordering.add(neighbourTemp);
                            toClear.add(neighbourTemp.str);
                        } else {
                            ordering.percUp(ordering.inHeap.get(neighbourTemp.str));
                        }
                        updated = false;
                    }
                }
            }
        }
        this.clear = toClear;
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

        boolean isPath;

        isPath = this.djikstra(start, end, cost);

        if (isPath) {

            Node finish = this.Graph.get(end);
            Node current = finish;
            ArrayList<String> answer = new ArrayList<>();
            answer.add(Integer.toString(finish.steps));
            answer.add(Integer.toString(finish.pathCost));

            while (!(current.previous==null)) {
                answer.add(2, current.prevMove);
                current = current.previous;
            }

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

//        long start_time = System.currentTimeMillis();

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
                    output.write(path.get(j));
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
//            long end_time = System.currentTimeMillis();
//            System.out.println(end_time-start_time);
        }
    }
}
