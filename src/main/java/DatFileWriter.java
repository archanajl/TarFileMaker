import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DatFileWriter {

    public void writeDatFile(String datFileName, String data) throws FileNotFoundException {
        try (OutputStream out = new FileOutputStream(datFileName)){
            byte[] strToBytes = data.getBytes();
            out.write(strToBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
