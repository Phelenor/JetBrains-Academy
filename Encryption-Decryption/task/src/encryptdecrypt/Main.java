package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    final static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        final Scanner reader = new Scanner(System.in);
        Scanner fileReader;
        FileWriter fileWriter;
        boolean in = false, out = false;
        File read = null, write = null;
        String mode = "enc", input = "";
        String alg = "shift";
        int key = 0;

        String print = "";

        // check for run configurations
        if (args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-mode")) {
                    mode = args[i + 1];
                } else if (args[i].equals("-key")) {
                    key = Integer.parseInt(args[i + 1]);
                } else if (args[i].equals("-data")) {
                    input = args[i + 1];
                } else if (args[i].equals("-out")) {
                    try {
                        write = new File(args[i + 1]);
                    } catch (Exception e) {
                        System.out.println("Error. Output file does not exist.");
                        return;
                    }
                    out = true;
                } else if (args[i].equals("-in")) {
                    try {
                        read = new File(args[i + 1]);
                    } catch (Exception e) {
                        System.out.println("Error. Input file does not exist.");
                        return;
                    }
                    in = true;
                } else if (args[i].equals("-alg")) {
                    alg = args[i + 1];
                }
            }
        }

        if (input.isEmpty() && in) {
            try {
                fileReader = new Scanner(read);
            } catch (Exception e) {
                System.out.println("Error. File not found.");
                return;
            }
            if (fileReader.hasNextLine()) {
                input = fileReader.nextLine();
            }
            fileReader.close();
        }

        switch (mode) {
            case "enc":
                if (alg.equals("unicode"))
                    print = (encrypt(input, key));
                else
                    print = (encryptAlphabet(input, key));
                break;
            case "dec":
                if (alg.equals("unicode"))
                    print = (decrypt(input, key));
                else
                    print = (decryptAlphabet(input, key));
                break;
        }

        if (out) {
            try {
                fileWriter = new FileWriter(write);
            } catch (Exception e) {
                System.out.println("Error. File not found.");
                return;
            }

            try {
                fileWriter.write(print);
                fileWriter.close();
            } catch (Exception e) {
                System.out.println("Error. FileWriter problem.");
            }
        }
    }

    public static String encrypt(String word, int key) {
        char[] encrypting = word.toCharArray();
        for (int j = 0; j < encrypting.length; j++) {
            encrypting[j] += key;
        }
        return new String(encrypting);
    }

    public static String decrypt(String word, int key) {
        char[] decrypting = word.toCharArray();
        for (int j = 0; j < decrypting.length; j++) {
            decrypting[j] -= key;
        }
        return new String(decrypting);
    }

    public static String encryptAlphabet(String word, int key) {
        char[] encrypting = word.toCharArray();
        for (int j = 0; j < encrypting.length; j++) {
            for (int i = 0; i < alphabet.length(); i++) {
                if (encrypting[j] == alphabet.charAt(i)) {
                    int index = (i + key) % alphabet.length();
                    encrypting[j] = alphabet.charAt(index);
                    break;
                }
            }
        }
        return new String(encrypting);
    }

    public static String decryptAlphabet(String word, int key) {
        char[] decrypting = word.toCharArray();
        for (int j = 0; j < decrypting.length; j++) {
            for (int i = 0; i < alphabet.length(); i++) {
                if (decrypting[j] == alphabet.charAt(i)) {
                    int index = (i - key);
                    if (index < 0) index += alphabet.length();
                    decrypting[j] = alphabet.charAt(index);
                    break;
                }
            }
        }
        return new String(decrypting);
    }
}

