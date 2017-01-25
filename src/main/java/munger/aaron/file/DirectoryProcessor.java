package munger.aaron.file;

import munger.aaron.command.Command;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectoryProcessor {

    private static Logger logger = Logger.getLogger(FileCommand.class.getName());

    Command command;
    Extractor fileExtractor;

    public DirectoryProcessor(Command commandRunner, Extractor fileExtractor){
        this.command = commandRunner;
        this.fileExtractor = fileExtractor;
    }

    public void processFilesInDir(String path){
        File directory = new File(path);
        if (!directory.isDirectory()){
            logger.log(Level.WARNING, path + " does not map to a directory.");
            return;
        }

        File[] files = directory.listFiles();
        if (files!= null){
            for (File file : files) {
                processFile(file);
            }
        }
    }

    private void processFile(File file){
        if (file.isDirectory()) {
            processFilesInDir(file.getPath());
        }
        else if (file.getName().endsWith(".zip")){
            String extractedDir = fileExtractor.extract(file);
            processFilesInDir(extractedDir);
        }
        else if (file.getName().endsWith(".txt")){
            command.run(file.getPath());
        }
    }

}
