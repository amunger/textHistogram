package munger.aaron.file;

import munger.aaron.command.Command;

import java.io.File;

public class DirectoryProcessor {

    Command command;
    FileExtractor fileExtractor;

    public DirectoryProcessor(Command commandRunner, FileExtractor fileExtractor){
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
        else if (fileIsZipped(file)){
            String extractedDir = fileExtractor.extract(file);
            processFilesInDir(extractedDir);
            deleteDirectory(new File(extractedDir));
        }
        else{
            command.run(file.getPath());
        }
    }

    private static void deleteDirectory(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) {
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteDirectory(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    private static boolean fileIsZipped(File file){
        return false;
    }

}
