import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Test;

import java.util.*;

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
    public void testCollaborateursProchesGraph() {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addVertex("e");

        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("b", "d");
        graph.addEdge("d", "e");

        Set<String> expected = Set.of("a", "b", "c", "d");
        Set<String> result = Main.collaborateursProches(graph, "a", 2);

        assertEquals(expected, result);
    }

    @Test
    public void testCollaborateursProchesBonus() {
        Map<String, Set<String>> graph = new HashMap<>();

        graph.put("a", Set.of("b", "c"));
        graph.put("b", Set.of("a", "c", "d"));
        graph.put("c", Set.of("a", "b"));
        graph.put("d", Set.of("b", "e"));
        graph.put("e", Set.of("d"));

        Graph<String, DefaultEdge> subGraph = Main.collaborateursProchesBonus(graph, "a", 2);

        Set<String> expectedVertices = Set.of("a", "b", "c", "d");
        assertTrue(subGraph.vertexSet().containsAll(expectedVertices));
        assertEquals(expectedVertices.size(), subGraph.vertexSet().size());
    }

    @Test
    public void testDistanceMax() {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");

        graph.addEdge("1", "2");
        graph.addEdge("2", "3");
        graph.addEdge("3", "4");

        int distance = Main.distanceMax(graph);
        assertEquals(3, distance);
    }

    @Test
    public void testTrouverCentreGroupe() {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");

        graph.addEdge("a", "b");
        graph.addEdge("b", "c");
        graph.addEdge("c", "d");

        Set<String> groupe = Set.of("a", "c", "d");

        String centre = Main.trouverCentreGroupe(graph, groupe);

       
        assertTrue(Set.of("b", "c").contains(centre));
    }
    
    @Test
    public void testImporter() throws Exception {
        Graph<String, DefaultEdge> graph = Main.importer();

        assertNotNull(graph);
        assertTrue(graph.vertexSet().size() > 0);
        assertTrue(graph.edgeSet().size() > 0);

        int distMax = Main.distanceMax(graph);
        assertTrue(distMax <= 6);

        double distMoy1 = Main.distanceMoy(graph, "[[Al Pacino]]");
        double distMoy2 = Main.distanceMoy(graph, "[[John Randolph (actor)|John Randolph]]");
        double distMoy3 = Main.distanceMoy(graph, "[[Robert Redford]]");
        double distMoy4 = Main.distanceMoy(graph, "[[George Clooney]]");

        System.out.println(distMoy1);
        System.out.println(distMoy2);
        System.out.println(distMoy3);
        System.out.println(distMoy4);

        System.out.println(Main.chercherPlusDistanceK(graph, "[[Al Pacino]]"));
        System.out.println(Main.chercherPlusDistanceK(graph, "[[John Randolph (actor)|John Randolph]]"));
        System.out.println(Main.chercherPlusDistanceK(graph, "[[Robert Redford]]"));
        System.out.println(Main.chercherPlusDistanceK(graph, "[[George Clooney]]"));
    }

    @Test
    public void testDIstanceMoy(){
        Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("C", "D");

        System.out.println(Main.distanceMoy(g, "A"));
        System.out.println(Main.distanceMoy(g, "B"));
        System.out.println(Main.distanceMoy(g, "C"));
        System.out.println(Main.distanceMoy(g, "D"));
        System.out.println(Main.minimiseVal(g));
    }

}
