package main;

import java.io.*;
import java.util.List;

public class Smth {
    static public void readLastLetters(int num, List<String> files, String output) {

        try (FileOutputStream to = new FileOutputStream("C:\\Users\\Денис\\Desktop\\to.txt")) {
            for (int j = 0; j < files.size(); j++) {
                RandomAccessFile from = new RandomAccessFile(new File(files.get(j)), "r");

                from.seek(from.length() - num);

                for (int i = (int) from.length() - num; i < from.length(); i++) {

                    to.write((char) from.read());

                }

                from.close();

            }
        } catch (FileNotFoundException exc) {

            System.out.println("File doesn't exist");
        } catch (IOException exc) {
            System.out.println("Problems with reading, writing or closing files.");
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream os = System.out;
        os.write((char) 50);
        os.close();
    }
}


