import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;


public class WordGraph {
	WordStat words;
	Graph graph;
	
	public WordGraph(String fileName) throws IOException{
		words = new WordStat(fileName);
		graph = new Graph(words.getLength()); 
		for (int i = words.getWords().length - 1; i > -1; i--){
			graph.addNode(words.getWords()[i], words.getCounts()[i]);
		}
		//Check indices
		//Check compareTo return values
		for(int i = 0; i < graph.getNodes(); i++){
			for(int j = 0; j < words.getPairs().length; j++){
				String word = graph.getVertex(i).getId();
				if(word.compareTo(words.getPairs()[j].substring(0, word.length())) == 0){
					for(int k = 0; k < graph.getNodes(); k++){
						if(graph.getVertex(k).getId().compareTo(words.getPairs()[j].substring(word.length() + 1, words.getPairs()[j].length())) == 0){
							graph.addEdge(i, k, words.getPairCounts()[k]);
						}
					}
				}
			}
		}
		
	}
	//Returns number of nodes in graph
	public int numNodes(){
		return graph.getNodes();
	}
	
	//Returns number of edges in graph
	public int numEdges(){
		return graph.getEdges();
	}
	
	//Returns count of word w
	public int wordCount(String w){
		return graph.getWordCount(w);
	}
	
	//Returns indegree of node w
	public int inDegree(String w){
		return graph.getInDegree(w);
	}
	
	//Returns outdegree of node w
	public int outDegree(String w){
		return graph.getOutDegree(w);
	}
	
	//Returns string array of all words that proceed w
	public String[] prevWords(String w){
		List<String> list = new ArrayList<String>();
		int index = 0;
		for (int i = 0; i < graph.getNodes(); i++){
			if(graph.getVertex(i).getId().compareTo(w) == 0){
				index = i;
			}
		}
		for(int i = 0; i < graph.getNodes(); i++){
			for(int j = 0; j < graph.getVertex(i).getNumEdges(); j++){
				if(graph.getVertex(i).getEdge(j).getEndNode() == index){
					list.add(graph.getVertex(i).getId());
				}
			}
		}
		return list.toArray(new String[list.size()]);
	}
	
	//Returns string array of all words that proceed w
	public String[] nextWords(String w){
		List<String> list = new ArrayList<String>();
		int index = 0;
		for (int i = 0; i < graph.getNodes(); i++){
			if(graph.getVertex(i).getId().compareTo(w) == 0){
				index = i;
			}
		}
		for(int i = 0; i < graph.getVertex(index).getNumEdges(); i++){
			System.out.println(i);
			//System.out.println(graph.getVertex(index).getEdge(i).getEndNode());
			list.add(graph.getVertex(graph.getVertex(index).getEdge(i).getEndNode()).getId());
		}
		return list.toArray(new String[list.size()]);
	}
	
	//Returns cost of word sequence that is input
	public double wordSeqCount(String[] wordSeq){
		for(int i = 0; i < wordSeq.length; i++){
			wordSeq[i] = wordSeq[i].toLowerCase().replaceAll("\\p{P}", "").replaceAll("\\s", "");
		}
		double cost = Math.log((double)(graph.getNodes())/(double)(graph.getWordCount(wordSeq[0])));
		for(int i = 0; i < wordSeq.length - 1; i++){
			if(!graph.search(wordSeq[i])){
				throw new IllegalArgumentException();
			}
			else{
				cost += Math.log((double)(graph.getWordCount(wordSeq[i]))/(double)(graph.getEdgeCount(graph.getIndex(wordSeq[i]),graph.getIndex(wordSeq[i+1]))));
			}
		}
		return cost;
	}
	
	
	public String generatePhrase(String startWord, String endWord, int N){
		graph.compDijkstra(startWord);
		return graph.getShortest(graph.getVertex(endWord));
	}
	
	
	//Extra Credit
	public String generatePhrase(String startWord, String endWord, int N, int r){
		return "huh";
	}
	
	
	
	public static void main(String[] args) throws IOException{
		WordGraph wg = new WordGraph("test1.txt");
		String endNodes = "End nodes for \"" + wg.graph.getVertex(0).getId() + "\": ";
		for(int i = 0; i < wg.graph.getVertex(0).getNumEdges() ; i++){
			endNodes = endNodes.concat(wg.graph.getVertex(0).getEdge(i).getEndVertex().getId() + " ");
		}
		int numNodes = wg.numNodes();
		int numEdges = wg.numEdges();
		int wordCount = wg.wordCount("hello");
		int inDegree = wg.inDegree("hello");
		int outDegree = wg.outDegree("my");
		String output = "Number of nodes: " + numNodes + "\nNumber of Edges: " + numEdges + "\nWord Count of \"Hello\": " + wordCount;
		String output1 = "In Degree of \"hello\": " + inDegree + "\nOut Degree of \"my\": " + outDegree;
		String[] testtest = wg.prevWords("my");
		String output2 = "Previous words of \"my\": ";
		for(String s:testtest){
			output2 = output2.concat(s + " ");
		}
		String[] blah = wg.nextWords("my");
		String output3 = "Next words of \"my\": ";
		for(String s:blah){
			output3.concat(s + " ");
		}
		String[] testestest = {"hello", "my", "name"};
		System.out.println(wg.wordSeqCount(testestest));
		String output4 = "Word Sequence Count of \"hello my name\": " + wg.wordSeqCount(testestest);
		System.out.println(wg.outDegree("my"));
		System.out.println(wg.generatePhrase("hello", "name", 2));
		 BufferedWriter writer = null;
	        try {
	            String Log = "test.txt";
	            File logFile = new File(Log);

	            System.out.println(logFile.getCanonicalPath());

	            writer = new BufferedWriter(new FileWriter(logFile));
	            writer.write(output + "\n" + output1 + "\n" + output2 + "\n" + output3 + "\n" + output4);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                writer.close();
	            } catch (Exception e) {
	            }
	        }
	        System.out.println(output);
	        System.out.println(output1);
	        System.out.println(output2);
	        System.out.println(output3);
	        System.out.println(output4);
	}
}
