package main;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



class TailTest {
    private final String sep = System.lineSeparator();

    private String readFile(String file) {
        try {
            return String.join(sep, Files.readAllLines(Paths.get(file)));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return "";
    }

    @Test
    void cutTail() {

        String chars100 = " 2009 and recorded in a communal environment" + sep +
                "involving numerous contributing musicians and producers.";

        String chars80 = "charFrom1.txt" + sep + "n a communal environment" + sep +
                "involving numerous contributing musicians and producers." + sep + "charFrom2.txt" + sep +
                "p hop," + sep + "soul, baroque pop, electro, indie rock, synth-pop, industrial, and gospel.";

        String string13 = String.join(sep, "8", "9", "10", "11", "12", "13", "14",
                "15", "16", "17", "18", "19", "20");

        String string5 = String.join(sep, "from1.txt", "16", "17", "18", "19",
                "20", "from2.txt", "16", "17", "18", "19", "20", "from3.txt", "16", "17", "18", "19", "20");

        String noCharNnoStringN1 = String.join(sep, "11", "12", "13", "14", "15", "16", "17", "18", "19", "20");
        String noCharNnoStringN3 = String.join(sep, "from1.txt", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "from2.txt",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "from3.txt", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20");


        String[] args1 = new String[]{"-c", "100", "-o", "to.txt", "charFrom1.txt"};
        String[] args2 = new String[]{"-c", "80", "-o", "to.txt", "charFrom1.txt", "charFrom2.txt"};
        String[] args3 = new String[]{"-n", "13", "-o", "to.txt", "from1.txt"};
        String[] args4 = new String[]{"-n", "5", "-o", "to.txt", "from1.txt", "from2.txt", "from3.txt"};
        String[] args5 = new String[]{"-o", "to.txt", "from1.txt"};
        String[] args6 = new String[]{"-o", "to.txt", "from1.txt", "from2.txt", "from3.txt"};

        TailLauncher.main(args1);
        Assert.assertEquals(chars100, readFile("to.txt"));

        TailLauncher.main(args2);
        Assert.assertEquals(chars80, readFile("to.txt"));

        TailLauncher.main(args3);
        Assert.assertEquals(string13, readFile("to.txt"));

        TailLauncher.main(args4);
        Assert.assertEquals(string5, readFile("to.txt"));

        TailLauncher.main(args5);
        Assert.assertEquals(noCharNnoStringN1, readFile("to.txt"));

        TailLauncher.main(args6);
        Assert.assertEquals(noCharNnoStringN3, readFile("to.txt"));

    }
}