package munger.aaron.file;

import munger.aaron.command.Command;

import java.io.IOException;
import java.nio.file.*;
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
        this.pathsExplored = new HashSet<>();
    }

    public void processFilesInDir(String path){
        logger.log(Level.INFO, "processing " + path);

        Path directory = Paths.get(path);
        if (!Files.isDirectory(directory)){
            logger.log(Level.WARNING, path + " does not map to a directory.");
            return;
        }

        iterateOverDirectory(path, directory);
    }

    private void iterateOverDirectory(String path, Path directory) {
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(directory);
            for (Path entry : stream) {
                processFile(entry);
            }
            stream.close();
        }
        catch(IOException e){
            logger.log(Level.SEVERE, "IO Exception thrown while streaming directory: " + path, e);
        }
    }

    private void processFile(Path file){
        Path path = getNormalizedPath(file);

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

    private Path getNormalizedPath(Path path) {
        Path realPath = resolveSymbolicLink(path);

        if (realPath != null){
            Path absolute = realPath.toAbsolutePath().normalize();
            if(!pathsExplored.contains(absolute.toString())){
                pathsExplored.add(absolute.toString());
                return absolute;
            }
        }
        return null;
    }

    private Path resolveSymbolicLink(Path path){
        if (Files.isSymbolicLink(path)){
            try {
                path = path.toRealPath();
            }
            catch (IOException e){
                logger.log(Level.WARNING, "Could not resolve real path for " + path.toString());
                return null;
            }
        }
        return path;
    }

}
