package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

public class Tail {

    private int num;
    private boolean byLines;

    Tail(int num, boolean byLines) {
        this.num = num;
        this.byLines = byLines;
    }

    public void cutTail(BufferedReader reader, BufferedWriter writer) throws IOException{
        if (byLines)
            lastStrings(reader, writer);
        else
            lastChars(reader, writer);
    }

    private void lastChars(BufferedReader from, BufferedWriter to) throws IOException {
            Deque<Character> list = new LinkedList<>();
            int letter;
            while ((letter = from.read()) > 0) {
                if (list.size() == num)
                    list.pollFirst();
                list.addLast((char) letter);
            }
            for (int c : list)
                to.write(c);
    }

    private void lastStrings(BufferedReader from, BufferedWriter to) throws IOException{
            Deque<String> list = new LinkedList<>();
            String line;
            while ((line = from.readLine()) != null) {
                list.addLast(line);
                if (list.size() > num) list.pollFirst();
            }
            while (list.size() != 0) {
                to.write(list.pollFirst());
                if (list.size() != 0) to.newLine(); // удаляется лишняя строка, перенос добавляется в лаунчере
            }
    }
}