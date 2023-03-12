import java.io.*;

public class DatFileReader {
    public void readDatFile(String fileName){
        File file = new File(fileName);
        BufferedReader br = null;
        try{
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            line = br.readLine();
            while(line != null){
                System.out.println(line);
                line = br.readLine();
            }
            System.out.println();
        } catch (FileNotFoundException e) {

            System.out.println("File not found: " + file.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file: " + file.toString());
        }
        finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("Unable to close file: " + file.toString());
            }catch (NullPointerException e){
//                file was never opened
            }

        }
    }
}
