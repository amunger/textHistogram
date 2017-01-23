package munger.aaron.file;


import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FileExtractorTest {
    FileExtractor fileExtractor = new FileExtractor();

    @Test
    public void testGivenZipFile_extractsToDirectory(){
        String extractedPath = fileExtractor.extract(new File("./testDir/dirWithZip/test.zip"));
        File extractedDir = new File(extractedPath);
        assertTrue(extractedDir.isDirectory());

        FileUtils.deleteDirectory(extractedDir);
    }
}
