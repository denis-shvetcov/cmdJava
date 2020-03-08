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
            for (String str:allLines)
                result+=str+"\n";
        } catch (IOException exc) {
            System.out.println("IOException");
        }
        return result.substring(0,result.length()-1); // убирается лишний пробел
    }

    @Test
    void cutTail() {

        //написать, когда есть charN output один input  1
        //написать, когда есть charN output и несколько input   2
        //написать, когда есть stringN output и несколько input  3
        //написать, когда есть stringN output и один input  4
        //написать, когда есть stringN и charN одновременно   5
        //написать, когда нет stringN и charN , есть один inputs   6
        //написать, когда нет stringN и charN , есть несколько inputs   7
        //написать, когда есть charN output и нет inputs   8
        //написать, когда есть stringN output и нет inputs    9

        List<String> inputChars1 = new ArrayList<>();
        inputChars1.add("charFrom1.txt");

        List<String> inputChars2 = new ArrayList<>();
        inputChars2.add("charFrom1.txt");
        inputChars2.add("charFrom2.txt");

        List<String> inputStrings1 = new ArrayList<>();
        inputStrings1.add("from1.txt");

        List<String> inputStrings3 = new ArrayList<>();
        inputStrings3.add("from1.txt");
        inputStrings3.add("from2.txt");
        inputStrings3.add("from3.txt");



        Tail tail1 = new Tail("to.txt", 100, null, inputChars1);
        Tail tail2 = new Tail("to.txt", 80, null, inputChars2);

        Tail tail3 = new Tail("to.txt", null, 13, inputStrings1);
        Tail tail4 = new Tail("to.txt", null, 5, inputStrings3);

        Tail tail5 = new Tail("to.txt", 50, 15, inputChars2);

        Tail tail6 = new Tail("to.txt", null, null, inputStrings1);
        Tail tail7 = new Tail("to.txt", null, null, inputStrings3);


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


        tail1.cutTail();
        Assert.assertEquals(chars100,readFile("to.txt"));

        tail2.cutTail();
        Assert.assertEquals(chars80,readFile("to.txt"));

        tail3.cutTail();
        Assert.assertEquals(string13,readFile("to.txt"));

        tail4.cutTail();
        Assert.assertEquals(string5,readFile("to.txt"));

        tail5.cutTail();
        Assert.assertTrue(tail5.charNStringNtogether);

        tail6.cutTail();
        Assert.assertEquals(noCharNnoStringN1,readFile("to.txt"));

        tail7.cutTail();
        Assert.assertEquals(noCharNnoStringN3,readFile("to.txt"));


    }
}