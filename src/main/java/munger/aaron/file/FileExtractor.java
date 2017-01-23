package munger.aaron.file;

import java.io.*;
import java.util.Enumeration;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileExtractor implements Extractor {

    private static Logger logger = Logger.getLogger(FileCommand.class.getName());

    public String extract(File file){
        String newPath = createWorkingDir();

        try{
            extractFileToDir(file, newPath);
        }
        catch(IOException e){
            logger.log(Level.SEVERE, "Exception while unzipping file: " + file.getPath(), e);
        }
        return newPath;
    }

    private void extractFileToDir(File zipped, String destination) throws IOException{
        ZipFile zipFile = new ZipFile(zipped);

        Enumeration zipFileEntries = zipFile.entries();
        while (zipFileEntries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
            String currentEntry = entry.getName();
            File destFile = new File(destination, currentEntry);
            File destinationParent = destFile.getParentFile();

            destinationParent.mkdirs();

            if (!entry.isDirectory()) {
                extractFile(zipFile, entry, destFile);
            }
        }
    }

    private void extractFile(ZipFile zipFile, ZipEntry entry, File destFile) throws IOException {
        int BUFFER = 2048;
        BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));

        byte data[] = new byte[BUFFER];

        FileOutputStream fos = new FileOutputStream(destFile);
        BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

        // read and write until last byte is encountered
        int currentByte;
        while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
            dest.write(data, 0, currentByte);
        }
        dest.flush();
        dest.close();
        is.close();
    }

    private String createWorkingDir(){
        String newPath = "./extractedWorking/" + UUID.randomUUID() + "/";
        new File(newPath).mkdir();

        return newPath;
    }
}
