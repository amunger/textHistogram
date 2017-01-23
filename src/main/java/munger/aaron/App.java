package munger.aaron;

import munger.aaron.file.DirectoryProcessor;
import munger.aaron.file.FileCommand;
import munger.aaron.file.FileExtractor;
import munger.aaron.file.FileUtils;
import munger.aaron.words.Histogram;

import java.io.File;

public class App
{
    public static void main( String[] args ){
        Histogram histogram = new Histogram();
        FileCommand fileCommand = new FileCommand(histogram);
        DirectoryProcessor directoryProcessor = new DirectoryProcessor(fileCommand, new FileExtractor());
        directoryProcessor.processFilesInDir("./testDir");
        histogram.printHistogram();
        FileUtils.deleteDirectory(new File("./extractedWorking"));
    }
}
