import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.nio.AttributeType;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import com.google.gson.Gson;

public class Main {
    public static String FICHIER_SOURCE = "./data/data_100.txt";

    // 3.1 Echauffement 
    public static Graph<String, DefaultEdge> importer() throws Exception {
        Gson gson = new Gson();
        Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);

        FileReader f = new FileReader(FICHIER_SOURCE);
        BufferedReader b = new BufferedReader(f);

        String ligne = b.readLine();

        while (ligne != null) {
            GrapheJson donnees = gson.fromJson(ligne, GrapheJson.class);
            for (String acteur : donnees.cast){
                g.addVertex(acteur);
            }
            for (String acteur : donnees.cast) {
                for (String acteur2 : donnees.cast) {
                    if (!(acteur.equals(acteur2)))
                        g.addEdge(acteur, acteur2);
                }
            }
            ligne = b.readLine();
        }
        f.close();
        return g;
    }
    
    
    // Exporter  
    public static void exporter(Graph<String, DefaultEdge> G)throws IOException{
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<String, DefaultEdge>();
		exporter.setVertexAttributeProvider((x) -> Map.of("label", new DefaultAttribute<>(x, AttributeType.STRING)));
		exporter.exportGraph(G, new FileWriter("graph.dot"));
    }

    // 3.2 Collaborateurs en communs  
    
    public static Set<String> collaborateursCommuns(Graph<String, DefaultEdge> graph, String acteur1, String acteur2) {
    Set<String> union = new HashSet<>();
    
    if (!graph.containsVertex(acteur1)) {
        return union;
    } else if (!graph.containsVertex(acteur2)) {
        return union;
    } else {
        union.addAll(Graphs.neighborListOf(graph, acteur1));
        union.addAll(Graphs.neighborListOf(graph, acteur2));
        return union;
    }
}



    
    
    // 3.3 Collaborateurs proches  

    public static Set<String> collaborateursProches(Map<String, Set<String>> G, String u, int k) {
        if (!G.containsKey(u)) {
            System.out.println(u + " est un illustre inconnu");
            return null;
        }

        Set<String> collaborateurs = new HashSet<>();
        collaborateurs.add(u);

        for (int i = 1; i <= k; i++) {
            Set<String> collaborateursDirects = new HashSet<>();

            for (String c : collaborateurs) {
                Set<String> voisins = G.getOrDefault(c, new HashSet<>());

                for (String v : voisins) {
                    if (!collaborateurs.contains(v)) {
                        collaborateursDirects.add(v);
                    }
                }
            }

            collaborateurs.addAll(collaborateursDirects);
        }

        return collaborateurs;
    }

    // 3.4 Qui est au centre d'Hollywood ? 
    
    public static int chercherPlusDistanceK(Graph<String, DefaultEdge> graph, String u) {
    boolean finir=true;
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
                finir=false; 
            }
        }

        return maxDistance;}
    }

    // 3.5 Une petite famille
    
    public static int distanceMax(Graph<String, DefaultEdge> graph) {
    int distanceM = 0;

    for (String acteur : graph.vertexSet()) {
        Set<String> visites = new HashSet<>();
        List<String> sommetsAVisiter = new ArrayList<>();
        Map<String, Integer> distance = new HashMap<>();
        sommetsAVisiter.add(acteur);
        visites.add(acteur);
        distance.put(acteur, 0);
        int poseActu = 0;

        while (poseActu < sommetsAVisiter.size()) {
            String courant = sommetsAVisiter.get(poseActu);
            poseActu++;
            int distCourante = distance.get(courant);

            for (String voisin : Graphs.neighborListOf(graph, courant)) {
                if (!visites.contains(voisin)) {
                    visites.add(voisin);
                    sommetsAVisiter.add(voisin);
                    distance.put(voisin, distCourante + 1);

                    if (distCourante + 1 > distanceM) {
                        distanceM = distCourante + 1;
                    }
                }
            }
        }
    }

    return distanceM;
    }

    //bonus 2

    public static Graph<String, DefaultEdge> collaborateursProchesBonus(Map<String, Set<String>> G, String u, int k) {
    if (!G.containsKey(u)) {
        System.out.println(u + " est un illustre inconnu");
        return null;
    }

    Set<String> collaborateurs = new HashSet<>();
    collaborateurs.add(u);

    for (int i = 1; i <= k; i++) {
        Set<String> collaborateursDirects = new HashSet<>();

        for (String c : collaborateurs) {
            Set<String> voisins = G.getOrDefault(c, new HashSet<>());

            for (String v : voisins) {
                if (!collaborateurs.contains(v)) {
                    collaborateursDirects.add(v);
                }
            }
        }

        collaborateurs.addAll(collaborateursDirects);
    }

    Graph<String, DefaultEdge> sg = new SimpleGraph<>(DefaultEdge.class);
    for (String collab : collaborateurs) {
        for (String collab2 : collaborateurs) {
            if (!collab.equals(collab2)) {
                sg.addVertex(collab);
                sg.addVertex(collab2);
                sg.addEdge(collab, collab2);
            }
        }
    }

    return sg;
}




            
        
    
        
    

    //Paul je comprends pas ???? 

    // 3.3

    // public static Set<String> distanceK(Graph<String, DefaultEdge> graph, String u, int k) {
    //     Set<String> collaborateurs = new HashSet<>();

    //     if (!graph.containsVertex(u)) {
    //         System.out.println(u + " est un illustre inconnu");
    //         return null;
    //     }

    //     collaborateurs.add(u);

    //     for (int i = 1; i <= k; i++) {
    //         Set<String> collaborateurs_directs = new HashSet<>();
    //         for (String c : collaborateurs) {
    //             Set<String> voisins = Graphs.neighborSetOf(graph, c);
    //             for (String v : voisins) {
    //                 if (!collaborateurs.contains(v)) {
    //                     collaborateurs_directs.add(v);
    //                 }
    //             }
    //         }
    //         collaborateurs.addAll(collaborateurs_directs);
    //     }

    //     return collaborateurs;
    // }

    // 3.3 modification
    // public static int chercherDistanceK(Graph<String, DefaultEdge> graph, String u, String v) {

    //     if (!graph.containsVertex(u) || !graph.containsVertex(v)) {
    //         System.out.println(u + " est un illustre inconnu");
    //         return -1;
    //     }
    //     if (u.equals(v)) {
    //         return 0;
    //     }
    //     int distance=1;
    //     Set<String> passer = new HashSet<>();
    //     Set<String> tout=new HashSet<>();
    //     tout=graph.vertexSet();
    //     passer.add(u);
    //     while (passer.size()<tout.size() && !passer.contains(v)) {
    //         passer=distanceK(graph, u, distance);
    //         distance++;

            
    //     }
    //     return distance-1;



    // }


    public static void main(String args[]) throws Exception {
        Graph<String, DefaultEdge> g = importer();
        
    }
}
    