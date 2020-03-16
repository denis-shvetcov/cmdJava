package main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tail {

    private int num;
    private boolean byLines;

    Tail(int num, boolean byLines) {
        this.num = num;
        this.byLines = byLines;
    }

    public void cutTail(String input, String output, BufferedWriter writer) {
        if (byLines)
            lastStrings(input, output, writer);
        else
            lastChars(input, output, writer);
    }

    // Символы читаются в файл output.
    private void lastChars(String input, String output, BufferedWriter to) {

        try {
            String allLines;
            String line;
            if (input != null) {
                BufferedReader from = Files.newBufferedReader(Paths.get(input));
                allLines = "";

                while ((line = from.readLine()) != null) {
                    allLines += line + "\n";
                }
                allLines = allLines.substring(0, allLines.length() - 1);//убираем лишний перенос
                to.write(allLines.substring(Math.max(allLines.length() - num, 0)));
                from.close();
            } else {
                //Если нет файлов, из которых мы берем символы, то читаем num символов из консоли
                Scanner scan = new Scanner(System.in);
                allLines = "";
                System.out.println("Write \"stop\" to close InputStream");
                while (!(line = scan.nextLine()).equals("stop")) allLines += line + "\n";
                to.write(allLines.substring(allLines.length() - num)); // убираем лишний перенос
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

    private void lastStrings(String input, String output, BufferedWriter to) {

        try {
            List<String> allLines;
            String line;
            if (input != null) {
                allLines = Files.readAllLines(Paths.get(input));
                for (int i = Math.max(allLines.size() - num, 0); i < allLines.size(); i++) {
                    to.write(allLines.get(i));
                    //если последний файл и последняя строка, то перенос не нужен
                    if (i != allLines.size() - 1) to.newLine();
                }
            } else {
                //Если нет файлов, из которых мы берем символы, то читаем num строк из консоли
                Scanner scan = new Scanner(System.in);
                allLines = new ArrayList<>();

                System.out.println("Write \"stop\" to close InputStream");
                while (!(line = scan.nextLine()).equals("stop"))
                    allLines.add(line);

                for (int i = Math.max(allLines.size() - num, 0); i < allLines.size(); i++) {
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