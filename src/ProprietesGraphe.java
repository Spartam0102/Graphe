
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.nio.AttributeType;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.csv.CSVFormat;
import org.jgrapht.nio.csv.CSVImporter;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.util.SupplierUtil;

public class ProprietesGraphe {

    public static void main(String[] args) {
        Graph<String, DefaultEdge> graph;

        graph = new SimpleGraph<>(DefaultEdge.class);

        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addVertex("f");
        graph.addVertex("i");
        graph.addVertex("g");
        graph.addVertex("h");

        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("b", "c");
        graph.addEdge("c", "d");
        graph.addEdge("f", "a");
        graph.addEdge("i", "a");

    }

    public static String getString(Graph<String, DefaultEdge> graph) {
        String s = "{";
        boolean start = true;
        for (String v : graph.vertexSet()) {
            if (start) {
                start = false;
            } else {
                s += ", ";
            }
            s += v;

        }
        s += "}, {";
        start = true;
        for (DefaultEdge e : graph.edgeSet()) {
            if (start) {
                start = false;
            } else {
                s += ", ";
            }

            s += graph.getEdgeSource(e);
            s += "--";
            s += graph.getEdgeTarget(e);
        }
        s += "}";
        return s;

    }

    public static int getDegreMax(Graph<String, DefaultEdge> graph) {
        int maxDegre = 0;
        for (String sommet : graph.vertexSet()) {
            int degre = graph.degreeOf(sommet);
            if (degre > maxDegre) {
                maxDegre = degre;
            }
        }
        return maxDegre;
    }

    public static boolean estTriangle(Graph<String, DefaultEdge> graph, String u, String v, String w) {
        return graph.containsEdge(u, v) && graph.containsEdge(u, w) && graph.containsEdge(w, v);
    }

    public static int getNbTriangles(Graph<String, DefaultEdge> graph) {
        int res = 0;
        for (String u : graph.vertexSet()) {
            for (String v : graph.vertexSet()) {
                for (String w : graph.vertexSet()) {
                    if (u != v && u != w && w != v && estTriangle(graph, u, v, w)) {
                        res++;
                    }

                }

            }
        }

        return res / 6;
    }

    public static boolean estChaine(Graph<String, DefaultEdge> graph, List<String> chaine) {
        for (int i = 0; i < chaine.size() - 1; i++) {
            String source = chaine.get(i);
            String target = chaine.get(i + 1);
            if (!graph.containsEdge(source, target)) {
                return false;
            }
        }
        return true;
    }

    public static Set<String> voisinages(Graph<String, DefaultEdge> graph, String u, String v) {

        Set<String> union = new HashSet<>();
        if (!graph.containsVertex(u)) {
            return union;
        } else if (!graph.containsVertex(v)) {
            return union;
        } else {
            union.addAll(Graphs.neighborListOf(graph, u));
            union.addAll(Graphs.neighborListOf(graph, v));
            return union;
        }

    }

    public static Set<String> napasvoisinages(Graph<String, DefaultEdge> graph, String u, String v) {
        Set<String> result = new HashSet<>();

        if (!graph.containsVertex(u)) {
            return result;
        }
        Set<String> voisinsU = new HashSet<>(Graphs.neighborListOf(graph, u));

        if (!graph.containsVertex(v)) {
            return voisinsU;
        }
        Set<String> voisinsV = new HashSet<>(Graphs.neighborListOf(graph, v));
        voisinsU.removeAll(voisinsV);
        return voisinsU;
    }

    public static boolean sontJumeaux(Graph<String, DefaultEdge> graph, String u, String v) {

        Set<String> voisinsU = new HashSet<>(Graphs.neighborListOf(graph, u));
        Set<String> voisinsV = new HashSet<>(Graphs.neighborListOf(graph, v));

        return voisinsU.equals(voisinsV);
    }

    public static Graph<String, DefaultEdge> sousGraphinduit(Graph<String, DefaultEdge> graph,
            List<String> sommetAMettre) {
        Graph<String, DefaultEdge> graphN;
        graphN = new SimpleGraph<>(DefaultEdge.class);
        for (String s : sommetAMettre) {
            graphN.addVertex(s);

        }
        for (int i = 0; i < sommetAMettre.size(); i++) {
            for (int j = i + 1; j < sommetAMettre.size(); j++) {
                String u = sommetAMettre.get(i);
                String v = sommetAMettre.get(j);
                if (graph.containsVertex(u) && graph.containsVertex(v) && graph.containsEdge(u, v)) {
                    graphN.addEdge(u, v);
                }
            }
        }

        return graphN;
    }

    public static Graph<String, DefaultEdge> supprimerAretes(Graph<String, DefaultEdge> graph,
            List<DefaultEdge> aretesASupprimer) {
        Graph<String, DefaultEdge> resultat = new SimpleGraph<>(DefaultEdge.class);

        for (String sommet : graph.vertexSet()) {
            resultat.addVertex(sommet);
        }

        for (DefaultEdge arete : graph.edgeSet()) {
            if (!aretesASupprimer.contains(arete)) {
                String source = graph.getEdgeSource(arete);
                String target = graph.getEdgeTarget(arete);
                resultat.addEdge(source, target);
            }
        }

        return resultat;
    }

    public static Graph<String, DefaultEdge> supprimerAretesParSommets(Graph<String, DefaultEdge> graph,
            List<String[]> aretesASupprimer) {
        Graph<String, DefaultEdge> resultat = new SimpleGraph<>(DefaultEdge.class);

        for (String sommet : graph.vertexSet()) {
            resultat.addVertex(sommet);
        }

        Set<String> aretesToRemove = new HashSet<>();
        for (String[] arete : aretesASupprimer) {
            if (arete.length == 2) {
                aretesToRemove.add(arete[0] + "-" + arete[1]);
                aretesToRemove.add(arete[1] + "-" + arete[0]);
            }
        }

        for (DefaultEdge arete : graph.edgeSet()) {
            String source = graph.getEdgeSource(arete);
            String target = graph.getEdgeTarget(arete);
            String areteStr = source + "-" + target;

            if (!aretesToRemove.contains(areteStr)) {
                resultat.addEdge(source, target);
            }
        }

        return resultat;
    }

    public static boolean estChaineElementaire(List<String> chaine) {
        if (chaine == null || chaine.isEmpty()) {
            return true;
        }

        Set<String> sommetsVus = new HashSet<>();
        for (String sommet : chaine) {
            if (sommetsVus.contains(sommet)) {
                return false;
            }
            sommetsVus.add(sommet);
        }
        return true;
    }

    public static List<String> extraireChaineElementaire(List<String> chaine) {
        if (chaine == null || chaine.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> chaineElementaire = new ArrayList<>();
        Set<String> sommetsVus = new HashSet<>();

        for (String sommet : chaine) {
            if (!sommetsVus.contains(sommet)) {
                chaineElementaire.add(sommet);
                sommetsVus.add(sommet);
            }
        }

        return chaineElementaire;
    }

    public static int chercherPlusDistanceK(Graph<String, DefaultEdge> graph, String u) {
        boolean finir = true;
        if (!graph.containsVertex(u)) {
            System.out.println(u + " est un illustre inconnu");
            return -1;
        } else {
            int maxDistance = 0;
            Set<String> visites = new HashSet<>();
            Set<String> niveauActuel = new HashSet<>();
            niveauActuel.add(u);
            visites.add(u);

            while (!niveauActuel.isEmpty() && finir) {
                Set<String> niveauSuivant = new HashSet<>();

                for (String sommet : niveauActuel) {
                    Set<String> voisins = Graphs.neighborSetOf(graph, sommet);

                    for (String voisin : voisins) {
                        if (!visites.contains(voisin)) {
                            niveauSuivant.add(voisin);
                            visites.add(voisin);
                        }
                    }
                }

                if (!niveauSuivant.isEmpty()) {
                    maxDistance++;
                    niveauActuel = niveauSuivant;
                } else {
                    finir = false;
                }
            }

            return maxDistance;
        }
    }

    // 3.3

    public static Set<String> distanceK(Graph<String, DefaultEdge> graph, String u, int k) {
        Set<String> collaborateurs = new HashSet<>();

        if (!graph.containsVertex(u)) {
            System.out.println(u + " est un illustre inconnu");
            return null;
        }

        collaborateurs.add(u);

        for (int i = 1; i <= k; i++) {
            Set<String> collaborateurs_directs = new HashSet<>();
            for (String c : collaborateurs) {
                Set<String> voisins = Graphs.neighborSetOf(graph, c);
                for (String v : voisins) {
                    if (!collaborateurs.contains(v)) {
                        collaborateurs_directs.add(v);
                    }
                }
            }
            collaborateurs.addAll(collaborateurs_directs);
        }

        return collaborateurs;
    }

    public static int distance(Graph<String, DefaultEdge> graph, String u, String v) {
        if (!graph.containsVertex(u) || !graph.containsVertex(v)) {
            System.out.println("Un des sommets est inconnu dans le graphe");
            return -1;
        }

        List<String> niveauActuel = new ArrayList<>();
        List<String> dejaVus = new ArrayList<>();
        niveauActuel.add(u);
        dejaVus.add(u);

        int distance = 0;

        while (!niveauActuel.isEmpty()) {
            List<String> niveauSuivant = new ArrayList<>();

            for (String sommet : niveauActuel) {
                if (sommet.equals(v)) {
                    return distance;
                }

                for (String voisin : Graphs.neighborListOf(graph, sommet)) {
                    if (!dejaVus.contains(voisin)) {
                        dejaVus.add(voisin);
                        niveauSuivant.add(voisin);
                    }
                }
            }

            niveauActuel = niveauSuivant;
            distance++;
        }

        return -1;

    }

    public static double distanceMoyenne(Graph<String, DefaultEdge> graph, String u) {
        double somme = 0;
        int nbsommet = 0;
        for (String v : graph.vertexSet()) {
            if (!v.equals(u)) {
                somme += distance(graph, u, v);
                nbsommet++;
            }
        }
        if (somme == 0) {
            return 0;
        } else {

            double res=somme / nbsommet;
            return res;
        }
    }

public static String distanceMoyennePlusPetite(Graph<String, DefaultEdge> graph) {
    Double min = null;
    String sommet=null;


    for (String v : graph.vertexSet()) {
        double actuel = distanceMoyenne(graph, v);
        if (min == null || actuel < min) {
            min = actuel;
            sommet=v;
        }
    }

    return sommet;
}


}
