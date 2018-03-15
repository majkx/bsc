package com.company;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.TimerTask;
import java.util.Timer;

public class Main extends TimerTask {
    static List<Record> records = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        if (args.length != 0) {
            for (int i = 0; i < args.length; i++) parseFile(args[i]);
        }

        TimerTask timerTask = new Main();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 60 * 1000);

        while (true) {

            System.out.println("Please enter record (ex: USD 50) or filename (ex: test.txt) : ");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            addRecord(input);
        }
    }

    public static void printAll() {
        System.out.println("\n --- ACTUAL BALANCE ---");
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).GetRecordValue() != 0) records.get(i).GetInfo();
        }
        System.out.println("");
        System.out.println("Please enter record (ex: USD 50) or filename (ex: test.txt) : ");
    }

    public static void parseFile(String filename) throws IOException {
        File f = new File(filename);
        if (f.exists()) {
            System.out.println("Parsing file " + filename + "......");
            Scanner input = new Scanner(f);
            while (input.hasNextLine()) {
                addRecord(input.nextLine());
            }
        } else System.out.println("File " + filename + " not found !");
    }


    public static void addRecord(String input) throws IOException {
        if (input.matches("([^\\s]+(\\.(?i)(txt|rtf))$)")) {
           parseFile(input);
        } else {

            boolean found = false;
            String strin = input.replaceAll("[^A-Za-z]", "").toUpperCase();
            if (!strin.equals("QUIT")) {
                String value = input.replaceAll("[^0-9?-]", "");

                if (!value.matches(".*\\d.*")) {
                    System.out.println("ERROR ! Wrong value inserted !");
                    System.exit(-1);
                }

                int val = Integer.parseInt(value);

                if (records.size() == 0) {
                    records.add(new Record(strin, val));
                    //System.out.println("Adding first record...");
                } else {

                    for (int i = 0; i < records.size(); i++) {
                        if (records.get(i).GetRecordName().equals(strin)) {
                            records.get(i).SetValue(val);
                            //System.out.println("Went through List, updated value and Breaks");
                            found = true;
                            break;
                        }
                    }

                    if (found == false) {
                        //System.out.println("Did not found record before, creating new one");
                        records.add(new Record(strin, val));
                    }
                }
            } else {
                System.exit(0);
            }
        }
    }

    @Override
    public void run() {
        printAll();
    }
}