package munger.aaron.file;

import munger.aaron.command.Command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectoryProcessor {

    private static Logger logger = Logger.getLogger(FileCommand.class.getName());

    private HashSet<String> pathsExplored;

    Command command;
    Extractor fileExtractor;

    public DirectoryProcessor(Command commandRunner, Extractor fileExtractor){
        this.command = commandRunner;
        this.fileExtractor = fileExtractor;
        this.pathsExplored = new HashSet<String>();
    }

    public void processFilesInDir(String path){
        logger.log(Level.INFO, "processing " + path);

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
        Path path = getPath(file);

        if (path != null){
            if (Files.isDirectory(path)) {
                processFilesInDir(path.toString());
            }
            else if (path.toString().endsWith(".zip")){
                String extractedDir = fileExtractor.extract(path.toFile());
                processFilesInDir(extractedDir);
            }
            else if (path.toString().endsWith(".txt")){
                command.run(path.toString());
            }
        }

    }

    private Path getPath(File file) {
        Path realPath = resolveSymbolicLink(file);

        if (realPath != null){
            Path absolute = realPath.toAbsolutePath().normalize();
            if(!pathsExplored.contains(absolute.toString())){
                pathsExplored.add(absolute.toString());
                return absolute;
            }
        }
        return null;
    }
    
    private Path resolveSymbolicLink(File file){
        Path path = file.toPath();
        if (Files.isSymbolicLink(path)){
            try {
                path = path.toRealPath();
            }
            catch (IOException e){
                logger.log(Level.WARNING, "Could not resolve real path for " + file.getPath());
                return null;
            }
        }
        return path;
    }

}
