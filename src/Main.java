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

    public static Graph<String, DefaultEdge> importer() throws Exception {
        Gson gson = new Gson();
        Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        FileReader f = new FileReader(FICHIER_SOURCE);
        BufferedReader b = new BufferedReader(f);
        String ligne = b.readLine();

        while (ligne != null) {
            GrapheJson donnees = gson.fromJson(ligne, GrapheJson.class);
            for (String acteur : donnees.cast) {
                g.addVertex(acteur);
            }
            for (int i = 0; i < donnees.cast.size(); i++) {
                for (int j = i + 1; j < donnees.cast.size(); j++) {
                    String acteur1 = donnees.cast.get(i);
                    String acteur2 = donnees.cast.get(j);
                    g.addEdge(acteur1, acteur2);
                 }
            }
            ligne = b.readLine();
        }
        f.close();
        return g;
    }

    public static void main(String args[]) throws Exception {
        Graph<String, DefaultEdge> g = importer();

    }
}