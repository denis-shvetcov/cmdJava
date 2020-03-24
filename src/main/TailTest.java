package main;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


class TailTest {

    private boolean compareChars(String expected, String real) {
        try(BufferedReader expRead = Files.newBufferedReader(Paths.get(expected));
            BufferedReader realRead = Files.newBufferedReader(Paths.get(real))) {
            if (expRead.read()!=realRead.read())
                return false;
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return true;
    }

    private boolean compareLines(String expected, String real) {
        try {
            return Files.readAllLines(Paths.get(expected)).equals(Files.readAllLines(Paths.get(real)));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return false;
    }

    @Test
    void cutTail() {


        String[] args1 = new String[]{"-c", "100", "-o", "to.txt", "charFrom1.txt"};
        String[] args2 = new String[]{"-c", "80", "-o", "to.txt", "charFrom1.txt", "charFrom2.txt"};
        String[] args3 = new String[]{"-n", "13", "-o", "to.txt", "from1.txt"};
        String[] args4 = new String[]{"-n", "5", "-o", "to.txt", "from1.txt", "from2.txt", "from3.txt"};
        String[] args5 = new String[]{"-o", "to.txt", "from1.txt"};
        String[] args6 = new String[]{"-o", "to.txt", "from1.txt", "from2.txt", "from3.txt"};

        TailLauncher.main(args1);
        Assert.assertTrue(compareChars("testExpected1.txt", "to.txt"));

        TailLauncher.main(args2);
        Assert.assertTrue(compareChars("testExpected2.txt", "to.txt"));

        TailLauncher.main(args3);
        Assert.assertTrue(compareLines("testExpected3.txt", "to.txt"));


        TailLauncher.main(args4);
        Assert.assertTrue(compareLines("testExpected4.txt", "to.txt"));

        TailLauncher.main(args5);
        Assert.assertTrue(compareLines("testExpected5.txt", "to.txt"));

        TailLauncher.main(args6);
        Assert.assertTrue(compareLines("testExpected6.txt", "to.txt"));

    }
}