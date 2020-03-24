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

    public void cutTail(BufferedReader reader, BufferedWriter writer) {
        if (byLines)
            lastStrings(reader, writer);
        else
            lastChars(reader, writer);
    }

    private void lastChars(BufferedReader from, BufferedWriter to) {

        try {
            Deque<Character> list = new LinkedList<>();
            int currentLength = 0; // размер списка без переносов
            int letter;
            while ((letter = from.read()) > 0) {
                if (currentLength == num) {
                    while (System.lineSeparator().contains(list.peekFirst().toString()))
                        list.pollFirst(); //убираем символы переноса \r и \n
                    list.pollFirst();
                    currentLength--;
                }
                list.addLast((char) letter);
                if (!System.lineSeparator().contains(list.peekLast().toString()))
                    currentLength++;
            }

            for (int c : list)
                to.write(c);

        } catch (
                IOException exc) {
            System.out.println(exc.getMessage());
            System.out.println("Problems with reading, writing or closing files.");

        }
    }

    private void lastStrings(BufferedReader from, BufferedWriter to) {

        try {
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
        } catch (IOException exc) {
            System.out.println("Problems with reading, writing or closing files.");
            System.out.println(exc.getMessage());
        }
    }
}