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
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                command.run(line);
            }
        }
        catch(FileNotFoundException e){
            logger.log(Level.WARNING,"File not found exception thrown for " + file.getPath());
        }
        catch(IOException e){
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
