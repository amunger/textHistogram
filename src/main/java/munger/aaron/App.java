package munger.aaron;

import munger.aaron.file.DirectoryProcessor;
import munger.aaron.file.FileCommand;
import munger.aaron.file.FileExtractor;
import munger.aaron.file.FileUtils;
import munger.aaron.words.Histogram;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{
    private static Logger logger = Logger.getLogger(FileCommand.class.getName());

    public static void main( String[] args ){
        Histogram histogram = new Histogram();
        FileCommand fileCommand = new FileCommand(histogram);
        DirectoryProcessor directoryProcessor = new DirectoryProcessor(fileCommand, new FileExtractor());

        String directory = getInput(args);
        directoryProcessor.processFilesInDir(directory);
        histogram.printHistogram();
        FileUtils.deleteDirectory(new File("./extractedWorking"));
    }

    private static String getInput(String[] cmdLineArg){
        if (cmdLineArg != null && cmdLineArg.length > 0 && !cmdLineArg[0].isEmpty()){
            logger.log(Level.INFO, "Command line argument provided, processing directory: " + cmdLineArg[0]);
            return cmdLineArg[0];
        }
        else{
            System.out.println("Please provide a directory path to search");
            return System.console().readLine();
        }
    }
}
