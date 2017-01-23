package munger.aaron.file;

import munger.aaron.command.Command;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileCommand implements Command {

    private static Logger logger = Logger.getLogger(FileCommand.class.getName());
    Command command;

    public FileCommand(Command command){
        this.command = command;
    }

    public void run(String filepath){
        File file = new File(filepath);
        FileReader fileReader = null;
        BufferedReader reader = null;

        try{
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                command.run(line);
            }
            reader.close();
        }
        catch(FileNotFoundException e){
            logger.log(Level.WARNING,"File not found exception thrown for " + file.getPath());
        }
        catch(IOException e){
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        finally {
            closeReader(fileReader);
            closeReader(reader);
        }
    }

    private void closeReader(Reader reader){
        try{
            if (reader != null){
                reader.close();
            }
        }
        catch (IOException e){
            logger.log(Level.WARNING, e.getMessage(), e);
        }

    }

}
