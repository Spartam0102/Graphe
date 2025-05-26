

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;
import org.junit.Test;

/**
 * Unit test for simple ProprietesGraphe.
 */
public class ProprietesGrapheTest {
    private Graph<String, DefaultEdge> graph;

    /**
     * Rigorous Test :-)
     */

    private Graph<String, DefaultEdge> creation() {

        graph = new SimpleGraph<>(DefaultEdge.class);

        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addVertex("e");
        graph.addVertex("f");
        graph.addVertex("i");
        graph.addVertex("g");
        graph.addVertex("h");

        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("b", "c");
        graph.addEdge("c", "d");
        graph.addEdge("d", "e");
        graph.addEdge("f", "a");
        graph.addEdge("i", "a");

        

        return graph;

    }

    private Graph<String, DefaultEdge> creation2() {

        graph = new SimpleGraph<>(DefaultEdge.class);

        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addVertex("e");
        graph.addVertex("f");
        graph.addVertex("g");
        graph.addVertex("h");
        graph.addVertex("i");
        graph.addVertex("p");

        graph.addEdge("a", "b");
        graph.addEdge("b", "c");
        graph.addEdge("c", "d");
        graph.addEdge("d", "e");
        graph.addEdge("e", "f");
        graph.addEdge("f", "g");

        graph.addEdge("c", "h");
        graph.addEdge("i", "h");
        graph.addEdge("i", "p");
        graph.addEdge("e", "p");




        return graph;

    }

    @Test
    public void testvoisinages() {
        graph = creation();

        Set<String> realite = new HashSet<>();
        realite.add("a");
        realite.add("b");
        realite.add("c");
        realite.add("i");
        realite.add("f");

        assertEquals(ProprietesGraphe.voisinages(graph, "a", "b"), realite);
    }

    @Test
    public void testnapasvoisinages() {
        graph = creation();

        Set<String> realite = new HashSet<>();
        realite.add("b");
        realite.add("f");
        realite.add("i");

        assertEquals(ProprietesGraphe.napasvoisinages(graph, "a", "b"), realite);
    }
    @Test
    public void testsontJumeaux() {
        graph = creation();



        assertEquals(ProprietesGraphe.sontJumeaux(graph, "a", "b"), false);
        assertEquals(ProprietesGraphe.sontJumeaux(graph, "f", "i"), true);
    }
    @Test
    public void testsousGraphinduit() {
        graph = creation();
        List<String>sommetA=Arrays.asList("a","b","c");



		Graph<String, DefaultEdge> graphtest;

		graphtest = new SimpleGraph<>(DefaultEdge.class);

		graphtest.addVertex("a");
		graphtest.addVertex("b");
		graphtest.addVertex("c");


		graphtest.addEdge("a", "b");
        graphtest.addEdge("b", "c");
        graphtest.addEdge("c", "a");

        Graph<String, DefaultEdge> graphResultat=ProprietesGraphe.sousGraphinduit(graph,sommetA);


        System.out.println(graphResultat.edgeSet().size());
        System.out.println(graphResultat.vertexSet().size());

        assertEquals(graphResultat.edgeSet().size(), graphtest.edgeSet().size());


    }

    @Test
    public void testChaineElementaire(){
        List<String>sommetA=Arrays.asList("a","b","c");
        List<String>sommetB=Arrays.asList("a","b","a");
        assertEquals(ProprietesGraphe.estChaineElementaire(sommetA), true);
        assertEquals(ProprietesGraphe.estChaineElementaire(sommetB), false);
    }

    @Test
    public void testextraireChaineElementaire(){
        List<String>sommetA=Arrays.asList("a","b","c","d","a","w","x");
        List<String>sommetAR=Arrays.asList("a","b","c","d","w","x");
        List<String>sommetB=Arrays.asList("a","w","x");
        List<String>sommetBR=Arrays.asList("a","w","x");
        assertEquals(ProprietesGraphe.extraireChaineElementaire(sommetA), sommetAR);
        assertEquals(ProprietesGraphe.extraireChaineElementaire(sommetB), sommetBR);
    }

    @Test
    public void testgetString(){
        graph = creation();
        System.out.println(ProprietesGraphe.getString(graph));
    }

    @Test
    public void testchercherPlusDistanceK(){
        graph = creation2();
        System.out.println(ProprietesGraphe.chercherPlusDistanceK(graph,"p"));
    }

    @Test
    public void testdistance(){
        graph = creation2();

        System.out.println(ProprietesGraphe.distance(graph,"d","h"));
    }

    @Test
    public void testdistanceMoyenne(){
        graph = creation2();

        System.out.println(ProprietesGraphe.distanceMoyenne(graph,"a"));
    }
    @Test
    public void testdistanceMoyennePlusPetite(){
        graph = creation2();

        System.out.println(ProprietesGraphe.distanceMoyennePlusPetite(graph));
    }

}
