import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Anagram {

    hashTable table;

    private class hashTable {
        public ArrayList<ArrayList<charSet>> ht;

        public hashTable() {
            ht = new ArrayList<>(100003);
            for (int i=0; i<100003; i++) {
                ht.add(new ArrayList<charSet>());
            }
        }

        public void insert(String s) {
            String sortedString = Anagram.sorted(s);
            int hash = Anagram.hashFunction(sortedString);
            if (this.ht.get(hash).isEmpty()) {
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

    public static boolean isNew1(String numeric, String sortedString) {
        boolean pass1 = false;
        boolean pass2;
        for (int i=0; i<numeric.length(); i++) {
            if (i==0) {
                if (numeric.charAt(i)!='0') {
                    return false;
                }
            } else {
                if (numeric.charAt(i)!='0') {
                    pass1 = numeric.charAt(i)=='1';
                }
            }
        }
        ArrayList<String> nums = new ArrayList<>();
        String s = "";
        boolean p = false;
        for (int i=0; i<numeric.length()-1; i++) {
            if (sortedString.charAt(i)==sortedString.charAt(i+1)) {
                p = true;
                s.concat(sortedString.substring(i,i+1));
            } else {
                if (p) {
                    s.concat(sortedString.substring(i,i+1));
                    nums.add(s);
                }
                p = false;
            }
        }
        if (p) {
            nums.add(s);
        }
        pass2 = Anagram.isNewMod(nums);
        return pass2&pass1;
    }

    public static boolean isNewMod(ArrayList<String> nums) {
        boolean pass = true;
        if (nums.isEmpty()) {
            return true;
        }
        char c;
        for (String n: nums) {
            for (int i=0; i<n.length(); i++) {
                if (i==0) {
                    if (n.charAt(i)!='0') {
                        return false;
                    }
                } else {
                    if (n.charAt(i)!='0') {
                        pass = pass && n.charAt(i)=='1';
                    }
                }
            }
        }
        return pass;
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

    public static ArrayList<String> permuteLists(ArrayList<ArrayList<String>> arrayOfWordLists) {
        ArrayList<String> returnList = new ArrayList<>();
        ArrayList<String> recurseList;
        ArrayList<ArrayList<String>> subList;
        ArrayList<String> currentList;
        if (arrayOfWordLists.size() == 1) {
            return arrayOfWordLists.get(0);
        } else {
            for (int k = 0; k < arrayOfWordLists.size(); k++) {
                currentList = arrayOfWordLists.get(k);
                subList = new ArrayList<>();
                for (int l = 0; l < arrayOfWordLists.size(); l++) {
                    if (l!=k) {
                        subList.add(arrayOfWordLists.get(l));
                    }
                }
                recurseList = Anagram.permuteLists(subList);
                for (int i = 0; i < currentList.size(); i++) {
                    for (int j = 0; j < recurseList.size(); j++) {
                        returnList.add(currentList.get(i) + " " + recurseList.get(j));
                    }
                }
            }
            return returnList;
        }
    }


    public ArrayList<String> getAnagram(String sortedString, String numeric) {
        ArrayList<String> returnList = new ArrayList<>();
        ArrayList<ArrayList<String>> listOfWordLists = new ArrayList<>();
        StringBuilder g0 = new StringBuilder();
        StringBuilder g1 = new StringBuilder();
        StringBuilder g2 = new StringBuilder();
        String gpnum;
        int numOfGroups;

        for (int i=0; i<numeric.length(); i++) {
            gpnum = numeric.substring(i,i+1);
            if (gpnum.equals("0")) {
                g0.append(sortedString.charAt(i));
            } else if (gpnum.equals("1")) {
                g1.append(sortedString.charAt(i));
            } else if (gpnum.equals("2")) {
                g2.append(sortedString.charAt(i));
            }
        }


        ArrayList<String> glist0 = this.table.getWordList(g0.toString(), true);
        listOfWordLists.add(glist0);
        numOfGroups = 1;
        if (glist0.isEmpty()) {
            return returnList;
        }
        if (!g1.toString().equals("")) {
            numOfGroups = 2;
            ArrayList<String> glist1 = this.table.getWordList(g1.toString(), true);
            if (glist1.isEmpty()) {
                return returnList;
            }
            listOfWordLists.add(glist1);
        }
        if (!g2.toString().equals("")) {
            numOfGroups = 3;
            ArrayList<String> glist2 = this.table.getWordList(g2.toString(), true);
            if (glist2.isEmpty()) {
                return returnList;
            }
            listOfWordLists.add(glist2);
        }

        returnList = Anagram.permuteLists(listOfWordLists);
//        returnList = Anagram.useLists(listOfWordLists);
        return returnList;
    }

    public static ArrayList<String> useLists(ArrayList<ArrayList<String>> arrayOfWordLists) {
        ArrayList<String> returnList = new ArrayList<>();
        ArrayList<String> wl1;
        ArrayList<String> wl2;
        ArrayList<String> wl3;
        if (arrayOfWordLists.size()>=1) {
            wl1 = arrayOfWordLists.get(0);
            for (String w1 : wl1) {
                if (arrayOfWordLists.size() >= 2) {
                    wl2 = arrayOfWordLists.get(1);
                    for (String w2 : wl2) {
                        if (arrayOfWordLists.size() >= 3) {
                            wl3 = arrayOfWordLists.get(2);
                            for (String w3 : wl3) {
                                returnList.add(w1 + " " + w2 + " " + w3);
                            }
                        } else {
                            returnList.add(w1 + " " + w2);
                        }
                    }
                } else {
                    return arrayOfWordLists.get(0);
                }
            }
        }
        return returnList;
    }

    public ArrayList<String> getAllAnagrams(String s) {
        ArrayList<String> returnList = new ArrayList<>();
        ArrayList<String> anagramList;
        String sortedString = Anagram.sorted(s);
        String numeric;
        String zeroes;
        for (int i=0; i<Math.pow(3,sortedString.length()); i++) {
            numeric = Integer.toString(i,3);
            zeroes = "";
            for (int j=0; j<sortedString.length()-numeric.length();j++) {
                zeroes+="0";
            }
            numeric = zeroes + numeric;
            if (Anagram.isNew1(numeric, sortedString)) {
                returnList.addAll(this.getAnagram(sortedString, numeric));
            }
//            anagramList = this.getAnagram(sortedString, numeric);
//            Collections.sort(anagramList);
//            anagramList = Anagram.removeDuplicates(anagramList);
//            returnList.addAll(anagramList);
        }
        Collections.sort(returnList);
//        returnList = Anagram.removeDuplicates(returnList);
        return returnList;
    }

    public static ArrayList<String> removeDuplicates(ArrayList<String> sortedList) {
        // from sorted list
        if (sortedList.isEmpty()) {
            return sortedList;
        } else {
            Iterator<String> it = sortedList.listIterator();
            String previous = "()";
            String current;
            while (it.hasNext()) {
                current = it.next();
                if (current.equals(previous)) {
                    it.remove();
                }
                previous = current;
            }
            return sortedList;
        }
    }

    public Anagram() {
        table = new hashTable();
    }

    public static void main(String[] args) {
        int n = 0;
        long start_time = System.currentTimeMillis();
        Anagram anagram = new Anagram();
        FileReader vocabFile = null;
        BufferedReader vocabulary = null;
        FileReader inputFile = null;
        BufferedReader input = null;
        Writer wr = null;
        try {
            wr = new FileWriter("E:\\1\\Assignments\\COL106\\A4\\src\\output.txt");
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
                wl = anagram.getAllAnagrams(currentWord);
                if (wl.isEmpty()) {
                    System.out.println("-1");
//                    wr.write("-1"+"\n");
                } else {
                    for (int j=0; j<wl.size(); j++) {
                        System.out.println(wl.get(j));
//                        wr.write(wl.get(j)+"\n");
                    }
                    System.out.println("-1");
//                    System.out.println(wl.size());
//                    n++;
//                    System.out.println(n);
                }
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
                if (wr != null)
                    wr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            long end_time = System.currentTimeMillis();
            System.out.println(end_time-start_time);
        }
    }
}
