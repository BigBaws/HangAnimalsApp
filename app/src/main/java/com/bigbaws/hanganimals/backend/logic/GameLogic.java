package com.bigbaws.hanganimals.backend.logic;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class GameLogic extends AppCompatActivity {

    private ArrayList<String> allWords = new ArrayList<String>();
    private String word;
    private ArrayList<String> usedLetters = new ArrayList<String>();
    private String viewWord;
    private int wrongs;
    private boolean lastCorrect;
    private boolean gameWon;
    private boolean gameLost;

    InputStream instream;

    public ArrayList<String> getUsedLetters() {
        return usedLetters;
    }
    public String getViewWord() {
        return viewWord;
    }
    public String getWord() {
        return word;
    }
    public int getWrongs() {
        return wrongs;
    }
    public boolean isLastCorrect() {
        return lastCorrect;
    }
    public boolean isGameWon() {
        return gameWon;
    }
    public boolean isGameLost() {
        return gameLost;
    }
    public boolean isGameOver() {
        return gameLost || gameWon;
    }


    public GameLogic() {

    }

    public void nulstil() {
        usedLetters.clear();
        wrongs = 0;
        gameWon = false;
        gameLost = false;
        word = allWords.get(new Random().nextInt(allWords.size()));
        updateWord();
    }


    private void updateWord() {
        viewWord = "";
        gameWon = true;
        for (int n = 0; n < word.length(); n++) {
            String bogstav = word.substring(n, n + 1);
            if (usedLetters.contains(bogstav)) {
                viewWord = viewWord + bogstav;
            } else {
                viewWord = viewWord + "*";
                gameWon = false;
            }
        }
    }

    public void gætBogstav(String bogstav) {
        if (bogstav.length() != 1) return;
        System.out.println("You have guessed: " + bogstav);
        if (usedLetters.contains(bogstav)) return;
        if (gameWon || gameLost) return;

        usedLetters.add(bogstav);

        if (word.contains(bogstav)) {
            lastCorrect = true;
            System.out.println("The Letter was correct: " + bogstav);
        } else {
            lastCorrect = false;
            System.out.println("The Letter was incorrect: " + bogstav);
            wrongs = wrongs + 1;
            if (wrongs >= 6) {
                gameLost = true;
            }
        }
        updateWord();
    }

    public static String hentUrl(String url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null) {
            sb.append(linje + "\n");
            linje = br.readLine();
        }
        return sb.toString();
    }

    public void hentOrdFraDr() throws Exception {
        String data = hentUrl("http://dr.dk");

        data = data.substring(data.indexOf("<body")).
//                replaceAll("<.+?>", " ").toUpperCase().replaceAll("[^a-zæøå]", " ");
                replaceAll("<.+?>", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");

        allWords.clear();
        allWords.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

        System.out.println("Possible Words " + allWords);
        nulstil();
    }

    public void setSecretWord(String secretWord) {
        allWords.clear();
        allWords.add(secretWord);

        System.out.println("Possible Words " + allWords);
        nulstil();
    }

//        InputStream in = this.getAssets().open("http://bigbaws.com/hangmanwords.txt");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//        String line = reader.readLine();

//        if (line != null){
//            allWords.clear();
//            allWords.add(line);
//        } else {
//            reader.close();
//            in.close();
//        }

//        String data = readFile("http://bigbaws.com/hangmanwords.txt");
//        System.out.println("data = " + data);
//
//        data = data.replaceAll("<.+?>", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");
//        System.out.println("data = " + data);
//        allWords.clear();
//        allWords.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

//        System.out.println("Possible words = " + allWords);
//        nulstil();
//    }

}
