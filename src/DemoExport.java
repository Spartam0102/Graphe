import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.nio.AttributeType;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.csv.CSVFormat;
import org.jgrapht.nio.csv.CSVImporter;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.util.SupplierUtil;

public class DemoExport {

	public static Graph<String, DefaultEdge> loadHeroes() {
		Graph<String, DefaultEdge> graph = new DefaultUndirectedGraph<>(SupplierUtil.createStringSupplier(1), SupplierUtil.DEFAULT_EDGE_SUPPLIER, false);
		return graph;
	}

	public static void main(String[] args) throws IOException {
		Graph<String, DefaultEdge> graph;
		
		graph = loadHeroes();
	


		DOTExporter<String, DefaultEdge> exporter = new DOTExporter<String, DefaultEdge>();
		exporter.setVertexAttributeProvider((x) -> Map.of("label", new DefaultAttribute<>(x, AttributeType.STRING)));
		exporter.exportGraph(graph, new FileWriter("graph.dot"));
	}

}