import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        Map<Path, Integer> datFilesListMap = writeDatFilesAndGetDatList();

        // The Tar file to be created
        Path outputPath = Paths.get("src/main/resources/sample.tar");

        // Create the zip file
        createTarGzipFiles(datFilesListMap, outputPath);
    }


    public static void createTarGzipFiles(Map<Path, Integer> pathsMap, Path output)
            throws IOException {
        String newFileName;
        Path datFilePath;
        try (OutputStream outputStream = Files.newOutputStream(output);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
             GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(bufferedOutputStream);
             TarArchiveOutputStream archiveOutputStream = new TarArchiveOutputStream(gzOut)) {

            for (Map.Entry<Path, Integer> datFile : pathsMap.entrySet()) {
                datFilePath = datFile.getKey();
                if (!Files.isRegularFile(datFilePath)) {
                    throw new IOException("Support only file!");
                }
                System.out.println(datFilePath);
                for ( int num = 0; num <= datFile.getValue(); num++ ) {
                    newFileName = FileNameUtils.getBaseName(datFilePath.getFileName().toString())
                            + num
                            + "."
                            + FilenameUtils.getExtension(datFilePath.getFileName().toString());
                    System.out.println(newFileName);
                    TarArchiveEntry tarEntry = new TarArchiveEntry(
                            datFilePath.toFile(),
                            newFileName);

                    archiveOutputStream.putArchiveEntry(tarEntry);
                    Files.copy(datFilePath, archiveOutputStream);

                    archiveOutputStream.closeArchiveEntry();
                }
            }
            archiveOutputStream.finish();
        }
    }

    public static Map<Path, Integer> writeDatFilesAndGetDatList() throws FileNotFoundException {
        Map<Path, Integer> datFilesListMap = new HashMap<>();
        // Create Dat file with fileName specified and data required
        DatFileWriter datFileWriter = new DatFileWriter();
        datFileWriter.writeDatFile("src/main/resources/sample.dat", buildDatFileData());
        datFileWriter.writeDatFile("src/main/resources/test.dat", buildDatFileData());

        // To display the Dat file created
        DatFileReader datFileReader = new DatFileReader();
        datFileReader.readDatFile("src/main/resources/sample.dat");
        
        // Add the dat file path and the number of times to be repeated to the map
        datFilesListMap.put(Paths.get("src/main/resources/sample.dat"), 5);
        datFilesListMap.put(Paths.get("src/main/resources/test.dat"), 5);
        return datFilesListMap;
    }

    public static String buildDatFileData() {
        String str = "4430220322034526750.0";
        str += "\n4440220322034526751.0";
        return str;
    }
}
