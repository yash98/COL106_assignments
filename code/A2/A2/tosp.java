public class tosp {
    public static void main (String[] args) {
        LinkedListImage img1 = new LinkedListImage("textsp.txt");;
        if (args.length == 0) {
            for (int x = 0; x < img1.height; x++) {
                for (int y = 0; y < img1.width; y++) {
                    System.out.println("-----------------------------------------------------------");
                    img1 = new LinkedListImage("textsp.txt");
                    System.out.println(img1.toStringCompressedLn());
                    
                    System.out.println(Integer.toString(x) + "," + Integer.toString(y) + "," + "true");
                    try {
                    img1.setPixelValue(x, y, true);
                    } catch (CompressedImageInterface.PixelOutOfBoundException p) {
                        System.err.println(p);
                    }
                    System.out.println(img1.toStringCompressedLn());
                    
                    System.out.println("-----------------------------------------------------------");
                    img1 = new LinkedListImage("textsp.txt");
                    System.out.println(img1.toStringCompressedLn());
                    
                    System.out.println(Integer.toString(x) + "," + Integer.toString(y) + "," + "false");
                    try {
                        img1.setPixelValue(x, y, false);
                    } catch (CompressedImageInterface.PixelOutOfBoundException p) {
                        System.err.println(p);
                    }
                    System.out.println(img1.toStringCompressedLn());
                    
                }
            }
         } else if (args.length == 2) {
            int x = Integer.parseInt(args[0]);
            boolean b;
            if (args[1].equals("0")) {
                b = false;
            } else {
                b = true;
            }
            for (int y = 0; y < img1.width; y++) {
                System.out.println("-----------------------------------------------------------");
                img1 = new LinkedListImage("textsp.txt");
                System.out.println(img1.toStringCompressedLn());
                
                System.out.println(Integer.toString(x) + "," + Integer.toString(y) + "," + b);
                try {
                img1.setPixelValue(x, y, b);
                } catch (CompressedImageInterface.PixelOutOfBoundException p) {
                    System.err.println(p);
                }
                System.out.println(img1.toStringCompressedLn());
                
            }
        } else if (args.length == 3) {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            boolean b;
            if (args[2].equals("0")) {
                b = false;
            } else {
                b = true;
            }
            System.out.println("-----------------------------------------------------------");
            img1 = new LinkedListImage("textsp.txt");
            System.out.println(img1.toStringCompressedLn());
            
            System.out.println(Integer.toString(x) + "," + Integer.toString(y) + "," + b);
            try {
            img1.setPixelValue(x, y, b);
            } catch (CompressedImageInterface.PixelOutOfBoundException p) {
                System.err.println(p);
            }
            System.out.println(img1.toStringCompressedLn());
                
        } else if (args.length == 4) {
            for (int x = Integer.parseInt(args[0]); x < Integer.parseInt(args[1])+1; x++) {
                for (int y = Integer.parseInt(args[2]); y < Integer.parseInt(args[4])+1; y++) {
                    System.out.println("-----------------------------------------------------------");
                    img1 = new LinkedListImage("textsp.txt");
                    System.out.println(img1.toStringCompressedLn());
                    
                    System.out.println(Integer.toString(x) + "," + Integer.toString(y) + "," + "true");
                    try {
                    img1.setPixelValue(x, y, true);
                    } catch (CompressedImageInterface.PixelOutOfBoundException p) {
                        System.err.println(p);
                    }
                    System.out.println(img1.toStringCompressedLn());
                    
                    System.out.println("-----------------------------------------------------------");
                    img1 = new LinkedListImage("textsp.txt");
                    System.out.println(img1.toStringCompressedLn());
                    
                    System.out.println(Integer.toString(x) + "," + Integer.toString(y) + "," + "false");
                    try {
                        img1.setPixelValue(x, y, false);
                    } catch (CompressedImageInterface.PixelOutOfBoundException p) {
                        System.err.println(p);
                    }
                    System.out.println(img1.toStringCompressedLn());
                    
                }
            }
        }
    }
}