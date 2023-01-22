
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map ;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
* NaturalLangProcessor is a class that provides two operations:
* Bigrams (A bigram is a segment of text consisting of two consecutive words that occur in a provided 
* text at least once) and TFIDF (This is a score that reflects the importance of a word in a document.)
* 
*
* @author      Nathan Bussière, Sidya Galakho
* @matricule   20218547, 20207299
*/
public final class NaturalLangProcessor {
    //----- inner MapEntry class -----
    protected static class Entry<K,V> {
        private K k; // for the key
        private V v; // for the value
        public Entry( K key, V value ) {
            this.k = key;
            this.v = value;
        }
        // getters
        public K getKey() { return this.k; }
        public V getValue() { return this.v; }
        public String toString() { return "<" + this.getKey() + ":" + this.getValue() + ">"; }
            
        } //----- end inner MapEntry class -----
    
    private Comparator<Entry<String,Double>> comp = new Comparator<Entry<String,Double>>() {
        @Override
        public int compare(Entry<String,Double> o1, Entry<String,Double> o2) {
            if (o1.getValue() < o2.getValue()) return 1 ;
            if (o1.getValue() > o2.getValue()) return -1 ; 
            return o1.getKey().compareTo(o2.getKey());
        }
    };

    private Map<String,Map<String,List<Integer>>> wordMap ;
    private Map<String,Integer> fileToSize ;
    private Map<String, List<Integer>> wordToPos ;
    private TextProcessor processor ;

    //Constructor
    public NaturalLangProcessor() throws FileNotFoundException {
        wordMap = new WordMap<>() ; //map of  each word and it's positions in each file.
        fileToSize = new HashMap<>() ; //file sizes for each file
        processor = new TextProcessor() ;
    }
    
    public void seedData( String dataset) throws IOException {
        File datasetDir  = new File(dataset) ;
        File[] files = datasetDir.listFiles() ;
        for (File file : files) {
            wordToPos = processor.process(dataset +"/" + file.getName());
            fileToSize.put(dataset +"/" +file.getName(),processor.getSize()) ;
            addToWordMap(wordToPos, dataset +"/" +file.getName());
        }
    }

    private String getBigram(String inputWord){
        Map<String,Map<Integer,String>> fileMap = processor.getFileMap() ;
        Map<String,Integer> wordToFreq = new HashMap<>(); // frequency of inputWord's nexts 
        if(!wordMap.containsKey(inputWord)){
            return "null";
        }
        for (String fileName : wordMap.get(inputWord).keySet()){ // list of all files containing inputWord
            for (int pos : wordMap.get(inputWord).get(fileName)){ // positions of inputWord in current file
                String word = fileMap.get(fileName).get(pos+1) ; // next of inputword in current file
                wordToFreq.put(word,wordToFreq.getOrDefault(word,0)+1) ; // update frequency of the next of inputWord
            }
        }
        // find maximum frequency if equality order lexicographically 
        Queue<Entry<String,Double>> freq = new PriorityQueue<>(comp) ;
        for (String word : wordToFreq.keySet()){
            freq.add(new Entry<String,Double>(word,wordToFreq.get(word)*1.0)) ;
        }
        return freq.peek().getKey();
    }


    public void executeQuery(String query) throws FileNotFoundException{
        Scanner reader = new Scanner(new File(query)) ;
        while (reader.hasNextLine()){
            String line = reader.nextLine() ;
            String[] words = line.split(" ") ;
            String word = words[words.length-1].toLowerCase() ;
            if (words[0].equals("search")) System.out.println(getTFIDF(word)) ;
            else { System.out.println(getBigram(word)) ;}
        }
        reader.close() ;
    }

    private String getTFIDF(String word) {  
        Queue<Entry<String,Double>> wordToTF = new PriorityQueue<>(comp) ;  
        if(!wordMap.containsKey(word)){
            return "null";
        }
        for (String fileName : wordMap.get(word).keySet()){
            int wordFreq = wordMap.get(word).get(fileName).size() ; // number of occurrences of word in current file
            int fileSize = fileToSize.get(fileName) ; // size of current file 
            // find maximum ratio if equality order lexicographically
            wordToTF.add(new Entry<String,Double>(fileName, wordFreq/(fileSize*1.0))) ;
        } 
        String[] output=wordToTF.peek().getKey().split("/");
        return  output[output.length-1];
    }

    private void addToWordMap(Map<String, List<Integer>> wordToPos2, String fileName){
        for (String word : wordToPos2.keySet()){
            if (!wordMap.containsKey(word)){
                wordMap.put(word,new FileMap<>()) ;
            }
            wordMap.get(word).put(fileName,wordToPos2.get(word)) ;
        }
    }
}
