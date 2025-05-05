import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import com.google.gson.Gson;

public class Main {
    public static String FICHIER_SOURCE = "./data/data_100.txt";

    public static Graph<String, DefaultEdge> importer() throws IOException {
        Gson gson = new Gson();
        Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);

        FileReader f = new FileReader(FICHIER_SOURCE);
        BufferedReader b = new BufferedReader(f);

        String ligne = b.readLine();

        while (ligne != null) {
            GrapheJson donnees = gson.fromJson(ligne, GrapheJson.class);
            
            ligne = b.readLine();
        }

        f.close();
        return g;
    }

    public static void main(String args[]) throws IOException {
        Graph<String, DefaultEdge> g = importer();
        
    }
}