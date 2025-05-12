import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import com.google.gson.Gson;

public class Main {
    public static String FICHIER_SOURCE = "./data/data_100.txt";

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

    public static void main(String args[]) throws Exception {
        Graph<String, DefaultEdge> g = importer();
        
    }
}