package main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tail {
    public boolean charNStringNtogether = false;
    private String output;
    private Integer charN;
    private Integer stringN;
    private List<String> files = new ArrayList<>();

    Tail(String output, Integer charN, Integer stringN, List<String> files) {
        this.output = output;
        this.charN = charN;
        this.stringN = stringN;
        this.files = files;
    }

    public void cutTail() {

        if (charN != null && stringN != null) {
            charNStringNtogether = true;
            System.err.println("Error: charN and stringN were entered at the same time");
            return;
        }

        if (charN != null) {
            lastChars(charN, files, output);
        }

        if (stringN != null) {
            lastStrings(stringN, files, output);
        }

        if (stringN == null && charN == null) {
            lastStrings(10, files, output);
        }

    }

    // Символы читаются в файл output.
    private void lastChars(int num, List<String> files, String output) {

        try (BufferedWriter to = output != null ? Files.newBufferedWriter(Paths.get(output)) :
                new BufferedWriter(new OutputStreamWriter(System.out))) {

            String allLines;
            String line;

            if (files.size() != 0) {
                BufferedReader from;
                for (int j = 0; j < files.size(); j++) {
                    allLines = "";
                    from = Files.newBufferedReader(Paths.get(files.get(j)));

                    if (files.size() > 1) {
                        to.write(new File(files.get(j)).getName());
                        to.newLine();
                    }

                    while ((line = from.readLine()) != null) {
                        allLines += line + "\n";
                    }
                    allLines = allLines.substring(0, allLines.length() - 1);//убираем лишний перенос
                    to.write(allLines.substring(Math.max(allLines.length() - num, 0)));
                    if (j != files.size() - 1) to.newLine(); // добавяляем строку. Если файл последний, то переноса нет.
                    from.close();
                }
            } else {
                //Если нет файлов, из которых мы берем символы, то читаем num символов из консоли
                Scanner scan = new Scanner(System.in);
                allLines= "";
                System.out.println("Write \"stop\" to close InputStream");
                while (!(line=scan.nextLine()).equals("stop")) allLines+=line + "\n";
                to.write(allLines.substring(allLines.length()-num-1,allLines.length()-1)); // убираем лишний перенос
                scan.close();

            }

        } catch (FileNotFoundException exc) {
            System.out.println(exc.getMessage());
            System.out.println("File doesn't exist");

        } catch (IOException exc) {
            System.out.println(exc.getMessage());
            System.out.println("Problems with reading, writing or closing files.");
        }

    }


    private void lastStrings(int num, List<String> files, String output) {

        try (BufferedWriter to = output != null ? Files.newBufferedWriter(Paths.get(output)) :
                new BufferedWriter(new OutputStreamWriter(System.out))) {
            List<String> allLines;
            String line;
            if (files.size() != 0) {
                BufferedReader from;
                for (int j = 0; j < files.size(); j++) {

                    from = Files.newBufferedReader(Paths.get(files.get(j)));

                    if (files.size() > 1) {
                        to.write(new File(files.get(j)).getName());
                        to.newLine();
                    }

                    allLines = Files.readAllLines(Paths.get(files.get(j)));

                    for (int i = Math.max(allLines.size() - num, 0);
                         i < allLines.size(); i++) {
                        to.write(allLines.get(i));
                        //если последний файл и последняя строка, то перенос не нужен
                        if (i != allLines.size() - 1 || j != files.size() - 1) to.newLine();
                    }

                    from.close();
                }
            } else {
                //Если нет файлов, из которых мы берем символы, то читаем num строк из консоли
                Scanner scan = new Scanner(System.in);
                allLines = new ArrayList<>();

                System.out.println("Write \"stop\" to close InputStream");
                while (!(line = scan.nextLine()).equals("stop"))
                    allLines.add(line);

                for (int i = Math.max(allLines.size() - num, 0);
                     i < allLines.size(); i++) {
                    to.write(allLines.get(i));
                    if (i != allLines.size() - 1) to.newLine();
                }
                scan.close();
            }

        } catch (FileNotFoundException exc) {
            System.out.println("File doesn't exist");
            System.out.println(exc.getMessage());

        } catch (IOException exc) {
            System.out.println("Problems with reading, writing or closing files.");
            System.out.println(exc.getMessage());
        }
    }
}



