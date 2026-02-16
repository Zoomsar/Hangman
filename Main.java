package Hangman;

import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        /* HANGMAN!! HANGMAN!!
            Kui lose siis mängi loseclip, kui võit ja gallowspole
         */

        ArrayList<String> words = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\Hangman\\words.txt"))) {
            String line;
            while((line = reader.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Random random = new Random();
        String word = words.get(random.nextInt(words.size()));

        Scanner scanner = new Scanner(System.in);

        ArrayList<Character> wordState = new ArrayList<>();
        boolean running = true;
        int counter = 6;
        File gallow = new File("src\\Hangman\\hangman.wav");
        File doors = new File("src\\Hangman\\3doors.wav");


        for(int i = 0; i < word.length(); i++) {
            wordState.add('_');
        }

        System.out.println("Welcome to hangman...");

        while(running) {
            System.out.print(getHangman(counter));
            System.out.print("Word: ");
            for(char c : wordState) {
                System.out.print(c + " ");
            }
            System.out.println();

            System.out.print("Guess a letter: ");
            char response = scanner.next().toLowerCase().charAt(0);

            if(word.indexOf(response) >= 0) {
                System.out.println("CORRECT!");

                for(int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == response) {
                        wordState.set(i, response);
                    }
                }

                if(!wordState.contains('_')) {
                    System.out.print(getHangman(counter));
                    System.out.println("Game over! YOU WON!");
                    System.out.println("The word was: " + word);
                    try(AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(gallow)) {
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioStream1);
                        clip.start();

                    } catch (LineUnavailableException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedAudioFileException e) {
                        throw new RuntimeException(e);
                    }
                    Thread.sleep(18000);
                    running = false;
                }
            } else {
                counter --;

                System.out.printf("FALSE! %d tries left!\n", counter);
            }

            if (counter == 0) {
                System.out.print(getHangman(counter));
                System.out.println("Game over! You lose!");
                System.out.println("The word was: " + word);
                try(AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(doors)) {
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream2);
                    clip.start();

                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedAudioFileException e) {
                    throw new RuntimeException(e);
                }
                Thread.sleep(28000);
                running = false;
            }
        }
    scanner.close();

    }

    static String getHangman(int wrongGuess) {
        return switch(wrongGuess) {
            case 6 -> """
                    
                    
                    
                    """;
            case 5 -> """
                     o
                    
                    
                    """;
            case 4 -> """
                     o
                     |
                    
                    """;
            case 3 -> """
                     o
                    /|
                    
                    """;
            case 2 -> """
                     o
                    /|\\
                    
                    """;
            case 1 -> """
                     o
                    /|\\
                    /
                    """;
            case 0 -> """
                     o
                    /|\\
                    / \\
                    """;
            default -> "Error";
        };
    }


}
