package main;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tail {
    @Option(name = "-o", metaVar = "outputFile", usage = "output file")
    private String output;

    @Option(name = "-c", metaVar = "charNumber", usage = "the number of chars")
    private Integer charN;

    @Option(name = "-n", metaVar = "stringNumber", usage = "the number of chars")
    private Integer stringN;


    @Argument(metaVar = "inputFiles", usage = "files we copy from")
    private List<String> files = new ArrayList<>();


    public static void main(String[] args) {
        if (args.length > 0) new Tail().launch(args);
    }

    public void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException exc) {
            System.err.println(exc.getMessage());
            System.err.println("java -jar recoder.jar -ie EncodingI -oe EncodingO InputName OutputName");
            parser.printUsage(System.err);
            return;
        }

        if (charN != null && stringN != null) {
            System.err.println("charN and stringN were entered at the same time");
        }

        if (charN != null) {
            if (output != null) {
                toFileLastChars(charN, files, output);
            } else {
                toConsoleLastChars(charN, files);
            }
        }


    }


    // Символы читаются в файл output.
    public void toFileLastChars(int num, List<String> files, String output) {

        try (BufferedWriter to = Files.newBufferedWriter(Paths.get(output))) {

            if (files.size() != 0) {
                for (int j = 0; j < files.size(); j++) {

                    RandomAccessFile from = new RandomAccessFile(new File(files.get(j)), "r");

                    if (files.size() > 1) {
                        to.write(new File(files.get(j)).getName());
                        to.newLine();
                    }

                    from.seek(from.length() - num);

                    for (int i = (int) from.length() - num; i < from.length(); i++) {
                        to.write((char) from.read());
                    }
                    to.newLine();
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
            System.out.println("File doesn't exist");

        } catch (IOException exc) {
            System.out.println("Problems with reading, writing or closing files.");
        }

    }

    //Символы читаются в консоль.
    public void toConsoleLastChars(int num, List<String> files) {

        try {
            if (files.size() != 0) {
                for (int j = 0; j < files.size(); j++) {

                    RandomAccessFile from = new RandomAccessFile(new File(files.get(j)), "r");

                    if (files.size() > 1) {
                        System.out.println(new File(files.get(j)).getName());
                    }

                    from.seek(from.length() - num);

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
        }

    }


}


