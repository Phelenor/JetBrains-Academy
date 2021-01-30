package readability;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TextAnalyzer {
    private int words;
    private int sentences;
    private int characters;

    public int getWords() {
        return words;
    }

    public int getSentences() {
        return sentences;
    }

    public int getCharacters() {
        return characters;
    }

    public int getSyllables() {
        return syllables;
    }

    public int getPolysyllables() {
        return polysyllables;
    }

    private int syllables;
    private int polysyllables;

    public void getScore(String method) {
        switch (method) {
            case "ART":
                double score = automatedReadability();
                System.out.format("\nAutomated Readability Index: %.2f (about %d year olds).", score, ageGroup((int) Math.round(score)));
            case "FK":
                score = FleschKincaid();
                System.out.format("\nFlesch-Kincaid readability tests:: %.2f (about %d year olds).", score, ageGroup((int) Math.round(score)));
            case "SMOG":
                score = SMOG();
                System.out.format("\nSimple Measure of Gobbledygook: %.2f (about %d year olds).", score, ageGroup((int) Math.round(score)));
            case "CL":
                score = ColemanLiau();
                System.out.format("\nColeman-Liau index: %.2f (about %d year olds).", score, ageGroup((int) Math.round(score)));
            case "all":
                int age = 0;
                score = automatedReadability();
                int tempAge = ageGroup((int) Math.round(score)); age += tempAge;
                System.out.format("\nAutomated Readability Index: %.2f (about %d year olds).\n", score, tempAge);
                score = FleschKincaid();
                tempAge = ageGroup((int) Math.round(score)); age += tempAge;
                System.out.format("Flesch–Kincaid readability tests: %.2f (about %d year olds).\n", score, tempAge);
                score = SMOG();
                tempAge = ageGroup((int) Math.round(score)); age += tempAge;
                System.out.format("Simple Measure of Gobbledygook: %.2f (about %d year olds).\n", score, tempAge);
                score = ColemanLiau();
                tempAge = ageGroup((int) Math.round(score)); age += tempAge;
                System.out.format("Coleman–Liau index: %.2f (about %d year olds).\n", score, tempAge);
                System.out.format("\nThis text should be understood in average by %.2f year olds.", age/4.0);

        }
    }

    public double FleschKincaid() {
        return 0.39 * (words / (1.0 *sentences)) + 11.8 * (syllables / (1.0 * words)) - 15.59;
    }

    public double SMOG() {
        return 1.043 * Math.sqrt(polysyllables * (30.0 / sentences)) + 3.1291;
    }

    public double ColemanLiau() {
        return 0.0588 * (characters * 100.0 / words) - 0.296 * (sentences * 100.0 / words) - 15.8;
    }

    public double automatedReadability() {
        return 4.71 * (characters * 1.0 / words) + 0.5 * (words * 1.0 / sentences) - 21.43;
    }

    public String readFile(String fileName) {
        File input = new File(fileName);
        Scanner fileReader = null;
        StringBuilder str = new StringBuilder();

        try {
            fileReader = new Scanner(input);
        } catch (Exception e) {
            System.out.println("The file given does not exist.");
            System.exit(0);
        }

        while (fileReader.hasNextLine()) {
            str.append(fileReader.nextLine());
        }
        fileReader.close();

        return str.toString();
    }

    public void countSyllables(String word) {
        char[] wordArray = word.toCharArray();
        String vowels = "aeiouy";
        boolean lastWasVowel = false;
        int cnt = 0;

        for (char c : wordArray) {
            if (vowels.contains(String.valueOf(c))) {
                if (!(c == wordArray[wordArray.length - 1] && c == 'e') && !lastWasVowel) {
                    cnt++;
                    lastWasVowel = true;
                    continue;
                }
            }
            lastWasVowel = false;
        }
        if (cnt > 2) polysyllables++;

        if (cnt == 0) syllables++;
        else syllables += cnt;
    }

    public void analyze(String text) {
        ArrayList<String> sentencesList = new ArrayList<>(Arrays.asList(text.split("[.!?][ ]*")));
        sentences = sentencesList.size();
        if (String.valueOf(text.toCharArray()[text.length() - 1]).matches("[.!?]")) {
            characters += sentences;
        } else {
            characters += sentences - 1;
        }

        for (String sentence : sentencesList) {

            String[] wordsArray = sentence.split(" ");
            words += wordsArray.length;

            for (String word : wordsArray) {
                characters += word.length();
                countSyllables(word.trim());
            }
        }
    }

    public static int ageGroup(int score) {
        if(score <= 0) return 1;

        switch (score) {
            case 1:
                return 6;
            case 2:
                return 7;
            case 3:
                return 9;
            case 4:
                return 10;
            case 5:
                return 11;
            case 6:
                return 12;
            case 7:
                return 13;
            case 8:
                return 14;
            case 9:
                return 15;
            case 10:
                return 16;
            case 11:
                return 17;
            case 12:
                return 18;
            default:
                return 24;
        }
    }
}
