
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import java.util.Properties;
/**
* TextProcessor is a class that processes a single file
*
* @author      Nathan Bussière, Sidya Galakho
* @matricule   20218547, 20207299
*/
public final class TextProcessor { 
    private int size ;
    private Map<String,Map<Integer,String>> fileMap ;
    public TextProcessor() throws FileNotFoundException {
        fileMap = new FileMap<>() ;
        size = 0 ;
    }

    protected   Map<String, List<Integer>> process(String input) throws IOException {
    	BufferedReader br = new BufferedReader(new FileReader(input));
		StringBuffer word = new StringBuffer();
        Map<String,List<Integer>> wordToPos = new HashMap<>(); // store word and all its positions in file
        int pos = 0 ;
        fileMap.put(input,new HashMap<>()) ; // store word and its position in each file
        String line;
        while ((line = br.readLine()) != null){
        	String newline = line.replaceAll("[^’'a-zA-Z0-9]", " ");
		    String finalline = newline.replaceAll("\\s+", " ").trim();
		    // set up pipeline properties
		    Properties props = new Properties();
		    // set the list of annotators to run
		    props.setProperty("annotators", "tokenize,pos,lemma");
		    // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
		    props.setProperty("coref.algorithm", "neural");
		    // build pipeline
		    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		    // create a document object
		    CoreDocument document = new CoreDocument(finalline);
		    // annnotate the document
		    pipeline.annotate(document);
		    //System.out.println(document.tokens());            

			for (CoreLabel tok : document.tokens()) {
				String str = tok.lemma();
		        if (!(str.contains("'s") || str.contains("’s"))) {
		        	word.append(str).append(" ");
		        }
			 } 
        }
        String str = String.valueOf(word);
		str = str.replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s+", " ").trim();
		String[] words = str.split(" ") ;
		for (String w : words) {
			w = w.toLowerCase() ; 
			if (!wordToPos.containsKey(w)) {
				wordToPos.put(w,new ArrayList<>()) ;
	        }
	        wordToPos.get(w).add(pos) ;
	        fileMap.get(input).put(pos,w) ;
	        ++pos ;
		}
        size=pos;
        br.close();
        return wordToPos ;
    }

    protected Map<String,Map<Integer,String>> getFileMap(){
        return fileMap ;
    }
    
    protected int getSize() {
        int temp = size ;
        size = 0 ; 
        return temp ;
    }
}
