package munger.aaron.file;

import munger.aaron.command.Command;

import java.io.File;

public class DirectoryProcessor {

    Command command;
    Extractor fileExtractor;

    public DirectoryProcessor(Command commandRunner, Extractor fileExtractor){
        this.command = commandRunner;
        this.fileExtractor = fileExtractor;
    }

    public void processFilesInDir(String path){
        File directory = new File(path);
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
        else{
            command.run(file.getPath());
        }
    }

}
