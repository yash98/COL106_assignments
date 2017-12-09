public interface CompressedImageInterface {

    // public void CompressedImageInterface(String filename);

    // public void CompressedImageInterface(boolean[][] grid, int width, int height);

    public boolean getPixelValue(int x, int y) throws PixelOutOfBoundException;

    public void setPixelValue(int x, int y, boolean val) throws PixelOutOfBoundException;

    public int[] numberOfBlackPixels();
    
    public void invert();
    
    public void performAnd(CompressedImageInterface img) throws BoundsMismatchException;
    
    public void performOr(CompressedImageInterface img) throws BoundsMismatchException;
    
    public void performXor(CompressedImageInterface img) throws BoundsMismatchException;
    
    public String toStringUnCompressed();
    
    public String toStringCompressed();

    class PixelOutOfBoundException extends Exception{  
        PixelOutOfBoundException(String s){  
          super(s);  
        }
    }

    class BoundsMismatchException extends Exception{
        BoundsMismatchException(String s){
            super(s);
        }
    }
}


