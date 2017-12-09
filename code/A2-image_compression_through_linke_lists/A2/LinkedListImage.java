import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class LinkedListImage implements CompressedImageInterface {
    Node[] arr;
    int height, width;

    private class Node {
        public int begin;
        public int end ;
        public Node prev;
        public Node next;

        public Node (int begin, int end, Node prev, Node next) {
            this.begin = begin;
            this.end = end;
            this.prev = prev;
            this.next = next;
        }

    }

    public void boolConv(boolean[][] grid, int width, int height) {
        Node currentNode;
        this.width = width;
        this.height = height;
        this.arr = new Node[height];
		for (int i=0; i<height; i++) {
            currentNode = new Node(-1, -1, null, null);
            this.arr[i] = currentNode;
            int last = -1;
            for (int j=0; j<width; j++) {
                if (last == 1) {
                    if (!grid[i][j]) {
                        currentNode.next = new Node(-1, -1, null, null);
                        currentNode.next.prev = currentNode;
                        currentNode = currentNode.next;
                        currentNode.begin = j;
                        last = 0;
                    }
                }
                else if (last == 0) {
                    if (grid[i][j]) {
                        currentNode.end = j-1;
                        last = 1;
                    }
                } else {
                    if (!grid[i][j]) {
                        currentNode.begin = j;
                        last = 0;
                    }
                }
            }
            if (currentNode.end == -1) {
                if (currentNode.begin != -1) {
                    currentNode.end = width-1;
                } else if (currentNode.begin == -1) {
                    this.arr[i] = null;
                }
            }
        }
    }

    public LinkedListImage(boolean[][] grid, int width, int height)
    {
        boolConv(grid, width, height);
    }

    public LinkedListImage(String filename)
	{
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String[] size = br.readLine().split(" ");
            int width = Integer.parseInt(size[0]);
            int height = Integer.parseInt(size[1]);
            boolean[][] tempgrid = new boolean[height][width];
            for (int i=0; i<height; i++) {
                String[] row = br.readLine().split(" ");
                for (int j=0; j<width; j++) {
                    if (row[j].equals("1")) {
                        tempgrid[i][j] = true;
                    } else {
                        tempgrid[i][j] = false;
                    }
                }
            }
            boolConv(tempgrid, width, height);
            br.close();
        } catch (IOException i) {
            System.err.println(i);
            System.out.println("invalid file name");
        } catch (ArrayIndexOutOfBoundsException a) {
            System.err.println(a);
            System.out.println("out of bound");
        }
    }

    public boolean getPixelValue(int x, int y) throws PixelOutOfBoundException
    {
		if ((x > this.height-1) || (y > this.width-1) || (x < 0) || (y < 0)) {
            throw new PixelOutOfBoundException("outofbound");
        } else {
            Node currentNode = this.arr[x];
            while ( currentNode != null) {
                if ((currentNode.begin <= y) && (y <= currentNode.end)) {
                    return false;
                } else if (currentNode.begin >= y) {
                    return true;
                }
                currentNode = currentNode.next;
            }
            return true;
        }
    }

    public void setPixelValue(int x, int y, boolean val) throws PixelOutOfBoundException
    {
        boolean notDone = true;
		if ((x > this.height-1) || (y > this.width-1) || (x < 0) || (y < 0)) {
            throw new PixelOutOfBoundException("outofbound");
        } else {
            Node currentNode = this.arr[x];
            Node prevNode = null;
            while (notDone &&  currentNode != null) {

                if (val) {
                    // setting 1
                    if ((currentNode.begin < y) && (y < currentNode.end)) {
                        // split condition
                        Node firstNode = new Node(currentNode.begin, y-1, currentNode.prev, null);
                        Node secondNode = new Node(y+1, currentNode.end, firstNode, currentNode.next);
                        firstNode.next = secondNode;
                        if (currentNode.prev == null) {
                            this.arr[x] = firstNode;
                        }
                        notDone = false;
                    } else if (currentNode.begin == y && currentNode.end != y) {
                        // begin condition
                        currentNode.begin = y+1;
                        notDone = false;
                    } else if (y == currentNode.end && currentNode.end != y) {
                        // end condtion
                        currentNode.end = y-1;
                        notDone = false;
                    } else if (y == currentNode.end && currentNode.end == y) {
                        // deletion of 1 element node
                        if (currentNode.prev != null) {
                            if (currentNode.next != null) {
                                currentNode.prev.next = currentNode.next;
                                currentNode.next.prev = currentNode.prev;
                            } else {
                                currentNode.prev.next = null;
                            }
                        } else {
                            if (currentNode.next != null) {
                                this.arr[x] = currentNode.next;
                            } else {
                                this.arr[x] = null;
                            }
                        }
                        notDone = false;
                    }
                } else {
                     // setting 0
                     if ((currentNode.prev != null) && (currentNode.prev.end == y-1) && (y+1 == currentNode.begin)) {
                        // join condition , 1st part of conditioin checks so no exception is raised in conditonal itself
                        // prevents 1st element entering this loop
                        Node prevOfPrev;
                        Node next;
                        // gives values corresponding to second
                        if ( currentNode.prev.prev != null) {
                            prevOfPrev = currentNode.prev.prev;
                        } else {
                            prevOfPrev = null;
                        }
                        if ( currentNode.next != null) {
                            // gives value corresponding to last
                            next = currentNode.next;
                        } else {
                            next = null;
                        }
                        Node jointNode = new Node(currentNode.prev.begin, currentNode.end, prevOfPrev, next);
                        if ( currentNode.prev.prev != null) {
                            // if 2nd 
                            currentNode.prev.prev.next = jointNode;
                        } else {
                            this.arr[x] = jointNode;
                        }
                        if ( currentNode.next != null) {
                            // if last
                            currentNode.next.prev = jointNode;
                        }
                        notDone = false;
                    } else if (currentNode.begin == y+1) {
                        // begin condition
                        currentNode.begin = y;
                        notDone = false;
                    } else if ((currentNode.prev != null) && (y-1 == currentNode.prev.end)) {
                        // end condtion
                        currentNode.prev.end = y;
                        notDone = false;
                    } else if (y < currentNode.begin) {
                        // new node creation
                        Node newNode = new Node(y, y, null,currentNode);
                        if (currentNode.prev != null) {
                            newNode.prev = currentNode.prev;
                            currentNode.prev.next = newNode;
                        }
                        currentNode.prev = newNode;
                        notDone = false;
                    }
                }
                prevNode = currentNode;
                currentNode = currentNode.next;
            }
            if (notDone) {
                if (!val) {
                // only case left is insertion 0 after final node or and end of final
                    if (prevNode != null) {
                        if (prevNode.end == y-1) {
                            prevNode.end = y;
                        } else {
                            Node newNode = new Node(y, y, prevNode, null);
                            prevNode.next = newNode;
                        }
                    } else {
                        // no element in list
                        arr[x] = new Node (y, y, null, null);
                    }   
                }
            }
        }
    }

    public int[] numberOfBlackPixels()
    {
        int[] countArray = new int[this.height];
		for (int i = 0; i < height; i++) {
            Node currentNode = this.arr[i];
            while ( currentNode != null) {
                countArray[i] += currentNode.end - currentNode.begin +1;
                currentNode =  currentNode.next;
            }
        }
        return countArray;
    }
    
    public void invert()
    {
        Node headOfInv;
		for (int i = 0; i < this.height; i++) {
            Node currentNode = this.arr[i];
            if (currentNode != null) {
                headOfInv = new Node(0, currentNode.begin, null, null);
            } else {
                headOfInv = new Node(0, this.width-1, null, null);
            }
            Node invNode = headOfInv;
            while ( currentNode != null) {
                if (currentNode.begin == 0) {
                    headOfInv.begin = currentNode.end+1;
                    headOfInv.end = -1;
                } else {
                    invNode.end = currentNode.begin-1;
                    Node tempNode = new Node(currentNode.end+1, -1, invNode, null);
                    invNode.next = tempNode;
                    invNode = tempNode;
                }
                currentNode = currentNode.next;
            }
            if (invNode.end == -1) {
                if (invNode.begin>this.width-1) {
                    if (invNode.prev == null) {
                        headOfInv = null;
                    } else {
                        invNode.prev.next = null;
                    }
                } else {
                    invNode.end = this.width-1;
                }
            }
            this.arr[i] = headOfInv;
        }
    }
    
    public void performAnd(CompressedImageInterface img) throws BoundsMismatchException
    {
        boolean run1 = false;
        if (run1 && (img instanceof LinkedListImage)) {
            System.out.println("1 running");
            LinkedListImage image = (LinkedListImage) img;
            if ((image.height != this.height) || (image.width != this.width)) {
                throw new BoundsMismatchException("mismatch");
            }
            for (int x = 0; x < this.height; x++) {
                Node nodeOfThis = this.arr[x];
                Node nodeOfImage = image.arr[x];
                Node currentSaveNode = new Node(-1, -1, null, null);
                this.arr[x] = currentSaveNode;
                while (nodeOfImage != null && nodeOfThis != null) {
                    if (nodeOfImage.begin < nodeOfThis.begin) {
                        // 2b.....1b..
                        if (nodeOfImage.end < nodeOfThis.end) {
                            // 2e...1e..
                            if (nodeOfImage.end > nodeOfThis.begin) {
                                // 2b 00000 1b 00000 2e 0000 1e
                                Node tempSaveNode = new Node(nodeOfImage.begin, nodeOfThis.end, currentSaveNode, null);
                                currentSaveNode = tempSaveNode;
                                nodeOfImage = nodeOfImage.next;
                                nodeOfThis = nodeOfThis.next;
                            } else {
                                // 2b 0000 2e 11(dont transverse-->)11 1b 0000 1e
                                Node tempSaveNode = new Node(nodeOfImage.begin, nodeOfImage.end, currentSaveNode, null);
                                currentSaveNode = tempSaveNode;
                                nodeOfImage = nodeOfImage.next;
                            }
                        } else {
                            // 1e...2e..
                            // 2b 00000 1b 000 1e 0000 2e
                            Node tempSaveNode = new Node(nodeOfImage.begin, nodeOfImage.end, currentSaveNode, null);
                            currentSaveNode = tempSaveNode;
                            nodeOfImage = nodeOfImage.next;
                            nodeOfThis = nodeOfThis.next;
                        }
                    } else {
                        // 1b....2b..
                        if (nodeOfImage.end < nodeOfThis.end) {
                            // 2e.....1e
                            // 1b 000 2b 000 2e 0000 1e
                            Node tempSaveNode = new Node(nodeOfThis.begin, nodeOfThis.end, currentSaveNode, null);
                            currentSaveNode = tempSaveNode;
                            nodeOfImage = nodeOfImage.next;
                            nodeOfThis = nodeOfThis.next;
                        } else {
                            // 1e.....2e
                            if (nodeOfThis.end > nodeOfImage.begin) {
                                // 1b 0000 2b 000 1e 0000 2e
                                Node tempSaveNode = new Node(nodeOfThis.begin, nodeOfImage.end, currentSaveNode, null);
                                currentSaveNode = tempSaveNode;
                                nodeOfImage = nodeOfImage.next;
                                nodeOfThis = nodeOfThis.next;
                            } else {
                                // 1b 00 1e 11(dont tranverse -->)11  2b 000 2e
                                Node tempSaveNode = new Node(nodeOfThis.begin, nodeOfThis.end, currentSaveNode, null);
                                currentSaveNode = tempSaveNode;
                                nodeOfThis = nodeOfThis.next;
                            }
                        }
                    }
                }
                // concatanate remaining of either
                if (!(nodeOfImage == null && nodeOfThis == null)) {
                    if (nodeOfImage == null) {
                        currentSaveNode.next = nodeOfThis;
                    } else {
                        currentSaveNode.next = nodeOfImage;
                    }
                }
            }
        } else {
            String s = img.toStringCompressed();
            s = s.substring(0, s.indexOf(','));
            String[] hw = s.split(" ");
            if ((Integer.parseInt(hw[0]) != this.height) || (Integer.parseInt(hw[1]) != this.width)) {
                throw new BoundsMismatchException("mismatch");
            }
            try {
                Node currentNode;
                for (int i=0; i<this.height; i++) {
                    currentNode = new Node(-1, -1, null, null);
                    Node headOfNew = currentNode;
                    int last = -1;
                    for (int j=0; j<this.width; j++) {
                        if (last == 1) {
                            if (!(this.getPixelValue(i, j) && img.getPixelValue(i, j))) {
                                currentNode.next = new Node(-1, -1, null, null);
                                currentNode.next.prev = currentNode;
                                currentNode = currentNode.next;
                                currentNode.begin = j;
                                last = 0;
                            }
                        }
                        else if (last == 0) {
                            if (this.getPixelValue(i, j) && img.getPixelValue(i, j)) {
                                currentNode.end = j-1;
                                last = 1;
                            }
                        } else {
                            if (!(this.getPixelValue(i, j) && img.getPixelValue(i, j))) {
                                currentNode.begin = j;
                                last = 0;
                            }
                        }
                    }
                    if (currentNode.end == -1) {
                        if (currentNode.begin != -1) {
                            currentNode.end = width-1;
                        } else if (currentNode.begin == -1) {
                            headOfNew = null;
                        }
                    }
                    this.arr[i] = headOfNew;
                }
            } catch (PixelOutOfBoundException p) {
                System.out.println(p);
                throw new BoundsMismatchException("mismatch");
            }
        }
    }
    
    public void performOr(CompressedImageInterface img) throws BoundsMismatchException
    {
        String s = img.toStringCompressed();
        s = s.substring(0, s.indexOf(','));
        String[] hw = s.split(" ");
        if ((Integer.parseInt(hw[0]) != this.height) || (Integer.parseInt(hw[1]) != this.width)) {
            throw new BoundsMismatchException("mismatch");
        }
        Node currentNode;
        try {
            for (int i=0; i<this.height; i++) {
                currentNode = new Node(-1, -1, null, null);
                Node headOfNew = currentNode;
                int last = -1;
                for (int j=0; j<this.width; j++) {
                    if (last == 1) {
                        if (!(this.getPixelValue(i, j) || img.getPixelValue(i, j))) {
                            currentNode.next = new Node(-1, -1, null, null);
                            currentNode.next.prev = currentNode;
                            currentNode = currentNode.next;
                            currentNode.begin = j;
                            last = 0;
                        }
                    }
                    else if (last == 0) {
                        if (this.getPixelValue(i, j) || img.getPixelValue(i, j)) {
                            currentNode.end = j-1;
                            last = 1;
                        }
                    } else {
                        if (!(this.getPixelValue(i, j) || img.getPixelValue(i, j))) {
                            currentNode.begin = j;
                            last = 0;
                        }
                    }
                }
                if (currentNode.end == -1) {
                    if (currentNode.begin != -1) {
                        currentNode.end = width-1;
                    } else if (currentNode.begin == -1) {
                        headOfNew = null;
                    }
                }
                this.arr[i] = headOfNew;
            }
        } catch (PixelOutOfBoundException p) {
            System.out.println(p);
            throw new BoundsMismatchException("mismatch");
        }
    }
    
    public void performXor(CompressedImageInterface img) throws BoundsMismatchException
    {
        String s = img.toStringCompressed();
        s = s.substring(0, s.indexOf(','));
        String[] hw = s.split(" ");
        if ((Integer.parseInt(hw[0]) != this.height) || (Integer.parseInt(hw[1]) != this.width)) {
            throw new BoundsMismatchException("mismatch");
        }
        try {
            Node currentNode;
            for (int i=0; i<this.height; i++) {
                currentNode = new Node(-1, -1, null, null);
                Node headOfNew = currentNode;
                int last = -1;
                for (int j=0; j<this.width; j++) {
                    if (last == 1) {
                        if (!(this.getPixelValue(i, j) ^ img.getPixelValue(i, j))) {
                            currentNode.next = new Node(-1, -1, null, null);
                            currentNode.next.prev = currentNode;
                            currentNode = currentNode.next;
                            currentNode.begin = j;
                            last = 0;
                        }
                    }
                    else if (last == 0) {
                        if (this.getPixelValue(i, j) ^ img.getPixelValue(i, j)) {
                            currentNode.end = j-1;
                            last = 1;
                        }
                    } else {
                        if (!(this.getPixelValue(i, j) ^ img.getPixelValue(i, j))) {
                            currentNode.begin = j;
                            last = 0;
                        }
                    }
                }
                if (currentNode.end == -1) {
                    if (currentNode.begin != -1) {
                        currentNode.end = width-1;
                    } else if (currentNode.begin == -1) {
                        headOfNew = null;
                    }
                }
                this.arr[i] = headOfNew;
            }
        } catch (PixelOutOfBoundException p) {
            System.out.println(p);
            throw new BoundsMismatchException("mismatch");
        }
    }
    
    public String toStringUnCompressed()
    {
        String s = Integer.toString(this.height) + " " + Integer.toString(this.width) + ", ";
        for (int i = 0; i < this.height; i++) {
            Node currentNode = this.arr[i];
            if (currentNode != null) {
                for (int j = 0; j < currentNode.begin; j++) {
                    s += "1 ";
                }
                while ( currentNode != null) {
                    for (int j = 0; j < currentNode.end-currentNode.begin+1; j++) {
                        s += "0 ";
                    }
                    try {
                        for (int j = 0; j < currentNode.next.begin-currentNode.end-1; j++) {
                            s += "1 ";
                        }
                    } catch (NullPointerException npe) {
                        for (int j = 0; j < this.width-currentNode.end-1; j++) {
                            s += "1 ";
                        }
                    }
                    currentNode = currentNode.next;
                }
            } else {
                for (int j = 0; j < this.width; j++) {
                    s += "1 ";
                }
            }
            s = s.substring(0, s.length()-1);
            s += ", ";
        }
        s = s.substring(0, s.length()-2);
        return s;
    }

    public String toStringUnCompressedLn()
    {
        String s = Integer.toString(this.height) + " " + Integer.toString(this.width) + "\n";
        for (int i = 0; i < this.height; i++) {
            Node currentNode = this.arr[i];
            if (currentNode != null) {
                for (int j = 0; j < currentNode.begin; j++) {
                    s += "1 ";
                }
                while ( currentNode != null) {
                    for (int j = 0; j < currentNode.end-currentNode.begin+1; j++) {
                        s += "0 ";
                    }
                    try {
                        for (int j = 0; j < currentNode.next.begin-currentNode.end-1; j++) {
                            s += "1 ";
                        }
                    } catch (NullPointerException npe) {
                        for (int j = 0; j < this.width-currentNode.end-1; j++) {
                            s += "1 ";
                        }
                    }
                    currentNode = currentNode.next;
                }
            } else {
                for (int j = 0; j < this.width; j++) {
                    s += "1 ";
                }
            }
            s += "\n";
        }
        return s;
    }
    
    public String toStringCompressed()
    {
        String s = Integer.toString(this.height) + " " + Integer.toString(this.width) + ",";
        for (int i = 0; i < this.height; i++) {
            Node currentNode = this.arr[i];
            while ( currentNode != null) {
                s += " " + Integer.toString(currentNode.begin) + " " + Integer.toString(currentNode.end);
                currentNode = currentNode.next;
            }
            s += " -1,";
        }
        s = s.substring(0, s.length()-1);
        return s;
    }

    public String toStringCompressedLn()
    {
        String s = Integer.toString(this.height) + " " + Integer.toString(this.width) + "\n";
        for (int i = 0; i < this.height; i++) {
            Node currentNode = this.arr[i];
            while ( currentNode != null) {
                s += " " + Integer.toString(currentNode.begin) + " " + Integer.toString(currentNode.end);
                currentNode = currentNode.next;
            }
            s += " -1\n";
        }
        return s;
    }

    public static void main(String[] args) {
    	// testing all methods here :
    	boolean success = true;

    	// check constructor from file
    	CompressedImageInterface img1 = new LinkedListImage("sampleInputFile.txt");

    	// check toStringCompressed
    	String img1_compressed = img1.toStringCompressed();
    	String img_ans = "16 16, -1, 5 7 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1, 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1";
    	success = success && (img_ans.equals(img1_compressed));

    	if (!success)
    	{
    		System.out.println("Constructor (file) or toStringCompressed ERROR");
    		return;
    	}

    	// check getPixelValue
    	boolean[][] grid = new boolean[16][16];
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			grid[i][j] = img1.getPixelValue(i, j);                
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	// check constructor from grid
    	CompressedImageInterface img2 = new LinkedListImage(grid, 16, 16);
    	String img2_compressed = img2.toStringCompressed();
    	success = success && (img2_compressed.equals(img_ans));

    	if (!success)
    	{
    		System.out.println("Constructor (array) or toStringCompressed ERROR");
    		return;
    	}

    	// check Xor
        try
        {
            img1.performXor(img2);  
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
    	for (int i = 0; i < 16; i++) {
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			success = success && (!img1.getPixelValue(i,j));                
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        }

    	if (!success)
    	{
    		System.out.println("performXor or getPixelValue ERROR");
    		return;
    	}
    	// check setPixelValue
    	for (int i = 0; i < 16; i++)
        {
            try
            {
    	    	img1.setPixelValue(i, 0, true);            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }

    	// check numberOfBlackPixels
    	int[] img1_black = img1.numberOfBlackPixels();
    	success = success && (img1_black.length == 16);
    	for (int i = 0; i < 16 && success; i++)
    		success = success && (img1_black[i] == 15);
    	if (!success)
    	{
    		System.out.println("setPixelValue or numberOfBlackPixels ERROR");
    		return;
    	}

    	// check invert
        img1.invert();
        for (int i = 0; i < 16; i++)
        {
            try
            {
                success = success && !(img1.getPixelValue(i, 0));            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        if (!success)
        {
            System.out.println("invert or getPixelValue ERROR");
            return;
        }

    	// check Or
        try
        {
            img1.performOr(img2);        
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && img1.getPixelValue(i,j);
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performOr or getPixelValue ERROR");
            return;
        }

        // check And
        try
        {
            img1.performAnd(img2);   
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && (img1.getPixelValue(i,j) == img2.getPixelValue(i,j));             
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performAnd or getPixelValue ERROR");
            return;
        }

    	// check toStringUnCompressed
        String img_ans_uncomp = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
        success = success && (img1.toStringUnCompressed().equals(img_ans_uncomp)) && (img2.toStringUnCompressed().equals(img_ans_uncomp));

        if (!success)
        {
            System.out.println("toStringUnCompressed ERROR");
            return;
        }
        else
            System.out.println("ALL TESTS SUCCESSFUL! YAYY!");
    }
}