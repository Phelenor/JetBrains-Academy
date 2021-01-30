package readability;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        TextAnalyzer analyzer = new TextAnalyzer();

        String input = analyzer.readFile(args[0]);
        analyzer.analyze(input);

        System.out.println("The text is:\n" + input + "\n");
        System.out.printf("Words: %d" +
                "\nSentences: %d" +
                "\nCharacters: %d" +
                "\nSyllables: %d" +
                "\nPolysyllables: %d\n", analyzer.getWords(), analyzer.getSentences(), analyzer.getCharacters(), analyzer.getSyllables(), analyzer.getPolysyllables());
        System.out.print("Enter the score you want to calculate (ART, FK, SMOG, CL, ALL): ");
        analyzer.getScore(reader.next());
        System.out.println();


    }

}
