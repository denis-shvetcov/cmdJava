package main;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TailTest {
    private String readFile(String file) {
        String result = "";
        try {
            List<String> allLines = Files.readAllLines(Paths.get(file));
            for (String str : allLines)
                result += str + "\n";
        } catch (IOException exc) {
            System.out.println("IOException");
        }
        return result.substring(0, result.length() - 1); // убирается лишний пробел
    }

    @Test
    void cutTail() {


        String chars100 = "2009 and recorded in a communal environment\n" +
                "involving numerous contributing musicians and producers.";
        String chars80 = "charFrom1.txt\n" +
                " a communal environment\n" +
                "involving numerous contributing musicians and producers.\n" +
                "charFrom2.txt\n" +
                " hop,\n" +
                "soul, baroque pop, electro, indie rock, synth-pop, industrial, and gospel.";
        String string13 = "8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20";
        String string5 = "from1.txt\n16\n17\n18\n19\n20\nfrom2.txt\n16\n17\n18\n19\n20\nfrom3.txt\n16\n17\n18\n19\n20";

        String noCharNnoStringN1 = "11\n12\n13\n14\n15\n16\n17\n18\n19\n20";
        String noCharNnoStringN3 = "from1.txt\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20\nfrom2.txt\n11\n12\n" +
                "13\n14\n15\n16\n17\n18\n19\n20\nfrom3.txt\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20";

        TailLauncher launcher = new TailLauncher();



        String[] args1 = new String[] {"-c","100","-o","to.txt","charFrom1.txt"};
        String[] args2 = new String[] {"-c","80","-o","to.txt","charFrom1.txt","charFrom2.txt"};
        String[] args3 = new String[] {"-n","13","-o","to.txt","from1.txt"};
        String[] args4 = new String[] {"-n","5","-o","to.txt","from1.txt","from2.txt","from3.txt"};
        String[] args5 = new String[] {"-o","to.txt","from1.txt"};
        String[] args6 = new String[] {"-o","to.txt","from1.txt","from2.txt","from3.txt"};

        launcher.main(args1);
        Assert.assertEquals(chars100, readFile("to.txt"));

        launcher.main(args2);
        Assert.assertEquals(chars80, readFile("to.txt"));

        launcher.main(args3);
        Assert.assertEquals(string13, readFile("to.txt"));

        launcher.main(args4);
        Assert.assertEquals(string5, readFile("to.txt"));

        launcher.main(args5);
        Assert.assertEquals(noCharNnoStringN1, readFile("to.txt"));

        launcher.main(args6);
        Assert.assertEquals(noCharNnoStringN3, readFile("to.txt"));

    }
}