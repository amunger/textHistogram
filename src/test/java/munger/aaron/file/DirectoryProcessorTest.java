package munger.aaron.file;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DirectoryProcessorTest {

    Extractor fileExtractor = new FileExtractorStub();
    CommandSpy command;
    DirectoryProcessor directoryProcessor;

    @Before
    public void initialize(){
        command = new CommandSpy();
        directoryProcessor = new DirectoryProcessor(command, fileExtractor);
    }

    @Test
    public void testGivenEmptyDirectory_noFilesProcessed(){
        directoryProcessor.processFilesInDir("./testDir/emptyDir");
        List<String> files = command.getStringsSeen();
        assertEquals(0, files.size());
    }

    @Test
    public void testGivenDirectoryWithOneFile_willProcessOneFile(){
        directoryProcessor.processFilesInDir("./testDir/oneFileWithin");
        List<String> files = command.getStringsSeen();
        assertEquals(1, files.size());
    }

    @Test
    public void testGivenSameDirectoryTwice_willProcessOnce(){
        directoryProcessor.processFilesInDir("./testDir/oneFileWithin");
        directoryProcessor.processFilesInDir("./testDir/oneFileWithin");
        List<String> files = command.getStringsSeen();
        assertEquals(1, files.size());
    }
}
