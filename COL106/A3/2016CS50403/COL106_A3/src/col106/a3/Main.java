package col106.a3;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        DuplicateBTree<String, String> tree = new BTree<>(n);
        while (true) {
            String command = s.next();
            try {
                switch (command) {
                    case "exit":
                        System.exit(0);
                    case "insert":
                        tree.insert(s.next(), s.next());
                        break;
                    case "delete":
                        tree.delete(s.next());
                        break;
                    case "print":
                        System.out.println(tree);
                        break;
                    case "height":
                        System.out.println(tree.height());
                        break;
                    case "empty":
                        System.out.println(tree.isEmpty());
                        break;
                    case "search":
                        System.out.println(tree.search(s.next()));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}