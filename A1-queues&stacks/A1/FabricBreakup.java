import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class FabricBreakup {
    integerNode tailOfShirts;
    int pilesize = 0;

    private class integerNode {
        public Integer rating;
        public integerNode prevmax;
        public int position;

        integerNode(int rat, integerNode pre, int pos) {
            this.rating = new Integer(rat);
            this.prevmax = pre;
            this.position = pos;
        }
    }

    public void fold (int rating) {
      if (tailOfShirts == null) {
        tailOfShirts = new integerNode(new Integer(rating), null, pilesize);
      } else {
        if (tailOfShirts.rating <= rating) {
          integerNode tempNode = new integerNode(rating, tailOfShirts, pilesize);
          tailOfShirts = tempNode;
        }
      }
      pilesize++;
    }

    public Integer party() {
      if (pilesize == 0) {
        return -1;
      } else {
        int temp = pilesize - tailOfShirts.position - 1;
        tailOfShirts = tailOfShirts.prevmax;
        pilesize = pilesize - temp - 1;
        return temp;
      }
    }

    public static void main(String[] args) {
      String numOfLine;
      String tempLine;
      FabricBreakup pile = new FabricBreakup();
      String inputFile = args[0];

      BufferedReader br = null;
		  FileReader fr = null;
      try {
        fr = new FileReader(inputFile);
			  br = new BufferedReader(fr);

        numOfLine = br.readLine();

        for (int i = 0; i < Integer.parseInt(numOfLine); i++) {
          tempLine = br.readLine();
          String[] arrOfLine = tempLine.split(" ");
          if (arrOfLine[1].equals("1")) {
            pile.fold(Integer.parseInt(arrOfLine[2]));
          } else {
            System.out.print(arrOfLine[0].toString() + " ");
            System.out.println(pile.party());
          }
        }
      } catch (IOException e) {
        ;
      } finally {
        ;
      }
    }
  }
