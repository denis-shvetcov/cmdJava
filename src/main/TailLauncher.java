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

public class TailLauncher {

    @Option(name = "-o", metaVar = "outputFile", usage = "output file")
    private String output;

    @Option(name = "-c", metaVar = "charNumber", usage = "the number of chars")
    private Integer charN;

    @Option(name = "-n", metaVar = "stringNumber", usage = "the number of chars")
    private Integer stringN;

    @Argument(metaVar = "inputFiles", usage = "the files we copy from")
    private List<String> files = new ArrayList<>();


    public static void main(String[] args) {
        try {
            if (args.length > 0) new TailLauncher().launch(args);
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }

    private void launch(String[] args) throws IOException {

        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException exc) {
            System.err.println(exc.getMessage());
            parser.printUsage(System.err);
            return;
        }

        Tail tail;

        if (charN != null && stringN != null) {
            System.err.println("Error: charN and stringN were entered at the same time");
            return;
        } else if (charN != null)
            tail = new Tail(charN, false);
        else if (stringN != null)
            tail = new Tail(stringN, true);
        else
            tail = new Tail(10, true);


        try (BufferedWriter to = output != null ? Files.newBufferedWriter(Paths.get(output)) :
                new BufferedWriter(new OutputStreamWriter(System.out))) {
            for (int i = 0; i < files.size(); i++) {
                if (files.size() > 1) {
                    to.write(new File(files.get(i)).getName());
                    to.newLine();
                }
                tail.cutTail(Files.newBufferedReader(Paths.get(files.get(i))), to);
                to.newLine();
            }
            if (files.size() == 0) tail.cutTail(new BufferedReader(new InputStreamReader(System.in)), to);

        }
    }


}


