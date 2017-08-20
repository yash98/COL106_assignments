import java.util.*;
import java.io.*;

public class FabricBreakup {
    public int maxArrSize = 1;
    public int[] arrOfShirts = new int[1];
    public int rear = 0;
    public int favShirtIndex = 0;

    public void fold (int rating) {
        try {
            arrOfShirts[rear] = rating;
            if (rating >= arrOfShirts[favShirtIndex]) {
                favShirtIndex = rear;
            }
            rear++;
        } catch (ArrayIndexOutOfBoundsException e) {
            int[] tempArr;            
            maxArrSize *= 2;
            tempArr = new int[maxArrSize];
            for (int i = 0; i < arrOfShirts.length; i++) {
                tempArr[i] = arrOfShirts[i];
            }
            arrOfShirts = tempArr;
            fold(rating);
        }
    }

    public int party() {
        int newRear = favShirtIndex;
        int tempRear = rear;
        int maxRating = 0;
        for (int i = 0; i < newRear; i++) {
            if (arrOfShirts[i] >= maxRating) {
                favShirtIndex = i;
            }
        }
        rear = newRear;
        if (rear == 0) {
            return -1;
        } else {
            return (tempRear-newRear);
        }
    }

    public static void main(String[] args) {
        FabricBreakup pile = new FabricBreakup();

    }
}
