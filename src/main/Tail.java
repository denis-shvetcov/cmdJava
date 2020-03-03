package main;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Tail {
    @Option(name = "-o", metaVar = "outputFile", usage = "output file")
    private String output;

    @Option(name = "-c", metaVar = "charNumber", usage = "the number of chars")
    private int charN;

    @Option(name = "-n", metaVar = "stringNumber", usage = "the number of chars")
    private int stringN = 10;


//    private List<String> files = new ArrayList<>();
    @Argument
    private  String files;


    public static void main(String[] args) throws IOException {
        if (args.length>0) new Tail().launch(args);
    }

    public void launch(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException exc) {
            System.err.println(exc.getMessage());
            System.err.println("java -jar recoder.jar -ie EncodingI -oe EncodingO InputName OutputName");
            parser.printUsage(System.err);
            return;
        }

        if (output!=null) {
            System.out.println("got output");
        }

        if (Integer.valueOf(charN)!=null) {
            System.out.println(charN);
        }

        if (Integer.valueOf(stringN)!=null) {
            System.out.println(stringN);
        }

//       try (BufferedWriter buff= Files.newBufferedWriter(Paths.get(files))){
//           buff.write("sup dude");
//       }

        System.out.println(files);



    }

}


