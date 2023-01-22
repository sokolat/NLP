
/**
*
*/
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        NaturalLangProcessor processor = new NaturalLangProcessor() ;
        processor.seedData("dataset2"); // data reading
        processor.executeQuery("query"); // query execution 
    } 
}

