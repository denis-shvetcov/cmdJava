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
            if (output != null) {
                toFileLastChars(charN, files, output);
            } else {
                toConsoleLastChars(charN, files);

            }
        }

        if (stringN != null) {
            if (output != null) {
                toFileLastStrings(stringN, files, output);
            } else {
                toConsoleLastStrings(stringN, files);
            }
        }

        if (stringN == null && charN == null) {
            if (output != null) {
                toFileLastStrings(10, files, output);
            } else {
                toConsoleLastStrings(10, files);
            }
        }

    }

    // Символы читаются в файл output.
    private void toFileLastChars(int num, List<String> files, String output) {

        try (BufferedWriter to = Files.newBufferedWriter(Paths.get(output))) {

            if (files.size()!=0) {
                String allLines;
                String line;
                BufferedReader from;
                for (int j = 0; j < files.size(); j++) {
                    allLines="";
                    from = Files.newBufferedReader(Paths.get(files.get(j)));

                    if (files.size() > 1) {
                        to.write(new File(files.get(j)).getName());
                        to.newLine();
                    }


                    while ((line = from.readLine()) != null) {
                        allLines+= line +"\n";
                    }
                    allLines = allLines.substring(0,allLines.length()-1);//убираем лишний перенос
                    to.write(allLines.substring(Math.max(allLines.length()-num,0)));
                    if (j!=files.size()-1) to.newLine(); // добавяляем строку. Если файл последний, то переноса нет.
                    from.close();
                }
            } else {
                //Если нет файлов, из которых мы берем символы, то читаем num символов из консоли
                Scanner scan = new Scanner(System.in);
                String str = scan.nextLine(); // читаем введенный текст
                scan.close();
                //если длина str больше, чем num, то записываем все символы, иначе записываем весь str
                to.write(str.substring(str.length() > num ? str.length() - num : 0));
            }

        } catch (FileNotFoundException exc) {
            System.out.println(exc.getMessage());
            System.out.println("File doesn't exist");

        } catch (IOException exc) {
            System.out.println(exc.getMessage());
            System.out.println("Problems with reading, writing or closing files.");
        }

    }

    //Символы читаются в консоль.
    private void toConsoleLastChars(int num, List<String> files) {

        try {
            if (files.size()!=0) {
                for (int j = 0; j < files.size(); j++) {

                    RandomAccessFile from = new RandomAccessFile(new File(files.get(j)), "r");

                    if (files.size() > 1) {
                        System.out.println(new File(files.get(j)).getName());
                    }

                    from.seek(from.length() - num > 0 ? from.length() - num : 0);

                    for (int i = (int) from.length() - num; i < from.length(); i++) {
                        System.out.print((char) from.read());
                    }
                    System.out.println();
                    from.close();
                }
            } else {
                //Если нет файлов, из которых мы берем символы, то читаем num символов из консоли
                Scanner scan = new Scanner(System.in);
                String str = scan.nextLine(); // читаем введенный текст
                scan.close();
                //если длина str больше, чем num, то записываем все символы, иначе записываем весь str
                System.out.println(str.substring(str.length() > num ? str.length() - num : 0));
            }

        } catch (IOException exc) {
            System.out.println("Problems with reading, writing or closing files.");
            System.out.println(exc.getMessage());
        }

    }

    private void toFileLastStrings(int num, List<String> files, String output) {

        try (BufferedWriter to = Files.newBufferedWriter(Paths.get(output))) {

            if (files.size()!=0) {
                List<String> allLines;
                String line;
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
                        if (i!= allLines.size()-1 || j!=files.size()-1) to.newLine();
                    }

                    from.close();
                }
            } else {
                //Если нет файлов, из которых мы берем символы, то читаем num строк из консоли
                int countLines = 0;
                Scanner scan = new Scanner(System.in);
                while (countLines < num) {
                    to.write(scan.nextLine());
                    to.newLine();
                    countLines++;
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

    private void toConsoleLastStrings(int num, List<String> files) {

        try {
            List<String> allLines;
            if (files.size()!=0) {
                String line;
                BufferedReader from;
                for (int j = 0; j < files.size(); j++) {
                    allLines = new ArrayList<>();
                    from = Files.newBufferedReader(Paths.get(files.get(j)));

                    if (files.size() > 1) {
                        System.out.println(new File(files.get(j)).getName());
                    }

                    allLines = Files.readAllLines(Paths.get(output));

                    for (int i = Math.max(allLines.size() - num, 0);
                         i < allLines.size(); i++) {
                        System.out.println(allLines.get(i));
                    }
                    from.close();
                }
            } else {
                //Если нет файлов, из которых мы берем символы, то читаем num строк из консоли
                allLines = new ArrayList<>();
                Scanner scan = new Scanner(System.in);
                while (allLines.size() < num) {
                    allLines.add(scan.nextLine());
                }
                for (String str : allLines)
                    System.out.println(str);
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
