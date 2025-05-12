import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.jgrapht.Graph;
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

    public static void exporter(Graph<String, DefaultEdge> graph, String fichier) throws IOException {
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<String, DefaultEdge>();
		exporter.setVertexAttributeProvider((x) -> Map.of("label", new DefaultAttribute<>(x, AttributeType.STRING)));
		exporter.exportGraph(graph, new FileWriter(fichier));
    }

    public static void main(String args[]) throws Exception {
        Graph<String, DefaultEdge> g = importer();
       
        
    }
}