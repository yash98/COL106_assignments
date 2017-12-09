package col106.a3.ta;

import col106.a3.BTree;
import col106.a3.DuplicateBTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StressTest {
    public static void main(String argv[]) throws Exception {
        long startTime=System.currentTimeMillis();
        DuplicateBTree<Integer, Integer> graph = new BTree<>(4);
        int V = 1000;
        int E = 10000;
        ArrayList<ArrayList<Integer>> g = new ArrayList<>(V);
        Random r = new Random();
        for (int i = 0; i < V; i++)
            g.add(new ArrayList<>());
        for (int i = 0; i < E; i++) {
            int v1 = r.nextInt(V);
            int v2 = r.nextInt(V);
            if (v1 != v2) {
                g.get(v1).add(v2);
                graph.insert(v1, v2);
            }
        }
        for (int i = 0; i < V; i++) {
            List<Integer> neighbourhood = graph.search(i);
            neighbourhood.sort(Integer::compareTo);
            ArrayList<Integer> correctAnswer = g.get(i);
            correctAnswer.sort(Integer::compareTo);
            if (!neighbourhood.equals(correctAnswer)) {
                System.out.println("Incorrect search result for " + i);
                System.out.println(correctAnswer);
                System.out.println(neighbourhood);
            }
        }
        long time=System.currentTimeMillis()-startTime;
        System.out.println(graph);
        System.out.println("time: "+time+" millis");
    }
}
