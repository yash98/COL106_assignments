public class test {
    public static void main(String[] args) {
        LinkedListImage img1 = new LinkedListImage("textsp.txt");
        LinkedListImage img2 = new LinkedListImage("all1.txt");
        LinkedListImage img = new LinkedListImage("textsp.txt");
        try {
            System.out.println(img1.toStringCompressedLn());
            img1 = new LinkedListImage("textsp.txt");
            img1.performAnd(img2);
            System.out.println(img1.toStringCompressedLn());
            img1 = new LinkedListImage("textsp.txt");
            img1.performOr(img2);
            System.out.println(img1.toStringCompressedLn());
            img1 = new LinkedListImage("textsp.txt");
            img1.performXor(img2);
            System.out.println("-----------------------------------------------------------");
            img.invert();
            System.out.println((img1.toStringCompressed()).equals(img.toStringCompressed()));
            System.out.println(img1.toStringCompressed());
            System.out.println(img.toStringCompressed());
            System.out.println("-----------------------------------------------------------");
            img1 = new LinkedListImage("textsp.txt");
            img2.invert();
            img1.performAnd(img2);
            System.out.println(img1.toStringCompressedLn());
            img1 = new LinkedListImage("textsp.txt");
            img1.performOr(img2);
            System.out.println(img1.toStringCompressedLn());
            img1 = new LinkedListImage("textsp.txt");
            img1.performXor(img2);
            System.out.println("-----------------------------------------------------------");
            img.invert();
            System.out.println((img1.toStringCompressed()).equals(img.toStringCompressed()));
            System.out.println(img1.toStringCompressed());
            System.out.println(img.toStringCompressed());
            System.out.println("-----------------------------------------------------------");
            System.out.println(img1.toStringCompressedLn());
            img1 = new LinkedListImage("textsp.txt");
        } catch (CompressedImageInterface.BoundsMismatchException b) {
            System.err.println(b);
        }
        // LinkedListImage img1 = new LinkedListImage("textsp.txt");
        // LinkedListImage img2 = new LinkedListImage("input2.txt");
        // LinkedListImage img3 = new LinkedListImage("textsp.txt");
        // int c = 0;
        // LinkedListImage img = img2;
        // // System.out.println("-----------------------------------------------------------");
        // while (true) {
        //     if (c%3==0) {
        //         img = img2;
        //     } else if (c%3==1) {
        //         img = img3;
        //     } else if (c%3==2) {
        //         if (c%7 == 0) {
        //             img = img2;
        //         } else {
        //             img = img3;
        //         }
        //     }
        //     try {
        //     img1.performAnd(img);
        //     img1.invert();
        //     img1.performOr(img);
        //     img1.performXor(img);
        //     System.out.println(c);
        //     System.out.println(img1.toStringCompressedLn());
        //     System.out.println("-----------------------------------------------------------");
        //     } catch (CompressedImageInterface.BoundsMismatchException b) {
        //         System.err.println(b);
        //     }
        //     c += 1;
        // }
    }
}