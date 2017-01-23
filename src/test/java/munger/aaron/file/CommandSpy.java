package munger.aaron.file;

import munger.aaron.command.Command;

import java.util.LinkedList;
import java.util.List;

public class CommandSpy implements Command {

    private List<String> linesSeen = new LinkedList<String>();

    public List<String> getStringsSeen(){
        return linesSeen;
    }

    public void run(String string){
        linesSeen.add(string);
    }
}
