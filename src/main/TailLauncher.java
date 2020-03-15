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


    public static void main(String[] args)   {
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


        Tail tail = new Tail(output,charN,stringN,files);

        tail.cutTail();



    }



}


