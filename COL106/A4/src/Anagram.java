import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

public class Anagram {

    hashTable table;

    private class hashTable {
        public ArrayList<ArrayList<charSet>> ht = new ArrayList<>(100003);

        public void insert(String s) {
            String sortedString = Anagram.sorted(s);
            int hash = Anagram.hashFunction(sortedString);
            if (this.ht.get(hash)==null || this.ht.get(hash).isEmpty()) {
                ArrayList<charSet> al = new ArrayList<>();
                ArrayList<String> sl = new ArrayList<String>();
                sl.add(s);
                al.add(new charSet(sortedString, sl));
                this.ht.set(hash, al);
            } else {
                ArrayList<charSet> place = this.ht.get(hash);
                int size = place.size();
                for (int i=0; i <= size; i++) {
                    if (i == size) {
                        ArrayList<String> sl = new ArrayList<>();
                        sl.add(s);
                        charSet cs = new charSet(sortedString, sl);
                        place.add(cs);
                    } else {
                        if (place.get(i).set.equals(sortedString)) {
                            place.get(i).words.add(s);
                            break;
                        }
                    }
                }
            }
        }

        public ArrayList<String> getWordList(String s, boolean isSorted) {
            String sortedString;
            int hash;
            if (isSorted) {
                sortedString = s;
                hash = Anagram.hashFunction(sortedString);
            } else {
                sortedString = Anagram.sorted(s);
                hash = Anagram.hashFunction(sortedString);
            }
            ArrayList<String> wl = new ArrayList<>();
            ArrayList<charSet> place = this.ht.get(hash);
            int size = place.size();
            for (int i=0; i<size; i++) {
                if (place.get(i).set.equals(sortedString)) {
                    wl = place.get(i).words;
                    break;
                }
            }
            return  wl;
        }

    }

    private class charSet {
        public String set;
        public ArrayList<String> words;

        public charSet(String set, ArrayList<String> words) {
            this.set = set;
            this.words = words;
        }
    }

    public static String sorted(String s) {
        char[] charArray = s.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }

    public static int hashFunction(String sortedString) {
        // considered sorted
        int hashValue = 0;
        for (int i=0; i < sortedString.length(); i++) {
            hashValue = ((37*hashValue) + Anagram.encode(sortedString.charAt(i)))%100003;
        }
        return hashValue;
    }

    public static int encode(char c) {
        int i = (int) (c);
        if (i==39) {
            i = 11;
        }else if (96<i) {
            i -= 85;
        } else if (48<=i & i<=57) {
            i -= 48;
        }
        return i;
    }

    public static boolean isNew(String numeric) {
        for (int i=0; i<numeric.length(); i++) {
            if (i==0) {
                if (numeric.charAt(i)!='0') {
                    return false;
                }
            } else {
                if (numeric.charAt(i)!='0') {
                    return numeric.charAt(i)=='1';
                }
            }
        }
        return true;
    }

    public ArrayList<String> getAnagrams(String s) {
        String sortedString = Anagram.sorted(s);
        for (int i=0; i<Math.pow(3,sortedString.length()); i++) {
            if (Anagram.isNew(Integer.toString(2,3))) {

            }
        }
    }

    public static void main(String[] args) {
        Anagram anagram = new Anagram();
        FileReader vocabFile = null;
        BufferedReader vocabulary = null;
        FileReader inputFile = null;
        BufferedReader input = null;
        try {
            vocabFile = new FileReader(args[0]);
            vocabulary = new BufferedReader(vocabFile);
            inputFile = new FileReader(args[1]);
            input = new BufferedReader(inputFile);

            String currentWord = vocabulary.readLine();

            while ((currentWord = vocabulary.readLine()) != null) {
                anagram.table.insert(currentWord);
            }

            int inputSize = Integer.parseInt(input.readLine());
            ArrayList<String> wl;

            for (int i=0; i<inputSize; i++) {
                currentWord = input.readLine();
                wl =
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (vocabFile != null)
                    vocabFile.close();
                if (vocabulary != null)
                    vocabulary.close();
                if (inputFile != null)
                    inputFile.close();
                if (input != null)
                    input.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
