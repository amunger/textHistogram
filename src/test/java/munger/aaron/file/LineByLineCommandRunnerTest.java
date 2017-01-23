package munger.aaron.file;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class LineByLineCommandRunnerTest {

    CommandSpy spy;
    FileCommand commandRunner;

    @Before
    public void initialize(){
        spy = new CommandSpy();
        commandRunner = new FileCommand(spy);
    }

    @Test
    public void testGivenMissingFile_WillNotRunAnyCommands()
    {
        commandRunner.run("./testDir/missing.txt");
        List<String> linesSeen = spy.getStringsSeen();
        assertEquals(linesSeen.size(), 0);
    }

    @Test
    public void testGivenEmptyTextFile_ShouldNotSeeAnyLines() throws IOException {
        commandRunner.run("./testDir/emptyFile.txt");
        List<String> linesSeen = spy.getStringsSeen();
        assertEquals(0,linesSeen.size());
    }

    @Test
    public void testGivenTextFile_BufferShouldReadData() throws IOException {
        commandRunner.run("./testDir/test1.txt");
        List<String> linesSeen = spy.getStringsSeen();
        assertEquals(linesSeen.size(), 1);
        assertNotEquals(linesSeen.get(0), "");
    }

}
