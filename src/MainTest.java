import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainTest {

    @Test
    public void testCollaborateursCommuns() {
    Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

    graph.addVertex("a");
    graph.addVertex("b");
    graph.addVertex("c");
    graph.addVertex("d");

    graph.addEdge("a", "c");
    graph.addEdge("b", "c");
    graph.addEdge("a", "d");
    graph.addEdge("b", "d");

    // "a" a collaboré avec "c" et "d"
    // "b" a collaboré avec "c" et "d"
    // Ils ont donc "c" et "d" en commun
    Set<String> expected = Set.of("c", "d");
    Set<String> result = Main.collaborateursCommuns(graph, "a", "b");

    assertEquals(expected, result);
}

    
    @Test
    public void testChercherPlusDistanceK() {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addVertex("e");

        graph.addEdge("a", "b");
        graph.addEdge("b", "c");
        graph.addEdge("c", "d");
        graph.addEdge("d", "e");

        int distance = Main.chercherPlusDistanceK(graph, "a");

        assertEquals(4, distance); // a -> b -> c -> d -> e = 4 étapes
    }

    @Test
    public void testCollaborateursProches() {
        // Création d'un graphe sous forme de dictionnaire (Map<String, Set<String>>)
        Map<String, Set<String>> graph = new HashMap<>();

        graph.put("a", Set.of("b", "c"));
        graph.put("b", Set.of("a", "c", "d"));
        graph.put("c", Set.of("a", "b"));
        graph.put("d", Set.of("b", "e"));
        graph.put("e", Set.of("d"));

        Set<String> expected = Set.of("a", "b", "c", "d");
        Set<String> result = Main.collaborateursProches(graph, "a", 2);

        assertEquals(expected, result);
    }

    @Test
    public void testCollaborateursProchesSingleHop() {
        Map<String, Set<String>> graph = new HashMap<>();

        graph.put("x", Set.of("y"));
        graph.put("y", Set.of("x", "z"));
        graph.put("z", Set.of("y"));

        Set<String> expected = Set.of("x", "y");
        Set<String> result = Main.collaborateursProches(graph, "x", 1);

        assertEquals(expected, result);
    }

}
