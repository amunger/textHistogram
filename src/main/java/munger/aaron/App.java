package munger.aaron;

import munger.aaron.file.DirectoryProcessor;
import munger.aaron.file.FileCommand;
import munger.aaron.file.RecursiveFileExtractor;
import munger.aaron.words.Histogram;

public class App
{
    public static void main( String[] args ){
        Histogram histogram = new Histogram();
        FileCommand fileCommand = new FileCommand(histogram);
        DirectoryProcessor directoryProcessor = new DirectoryProcessor(fileCommand, new RecursiveFileExtractor());
        directoryProcessor.processFilesInDir("./testDir");
        histogram.printHistogram();
    }
}
