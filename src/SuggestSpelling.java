import java.io.*;
import java.util.*;
import java.util.regex.*;

public class SuggestSpelling {
    // Declares a private HashMap in which the probabilities of the words will be stored
    private final HashMap<String, Integer> wordsProbabilitySet = new HashMap<>();

    // This constructor will initialise a BufferedReader variable and use regex to match words
    public SuggestSpelling(String file) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            Pattern patternMatch = Pattern.compile("\\w+");
            // Reading the dictionary and updating the probabilistic values accordingly
            matchingWordsWithRegex(in, patternMatch);
            in.close();
        }
        catch (IOException e){
            System.out.println("Something went wrong: IOException was thrown");
        }
    }

    // This method will see if any of the words in the dictionary match with the word that was
    // incorrectly spelt, if it does then it will add the probabilities of the word
    public void matchingWordsWithRegex(BufferedReader in, Pattern patternMatch) throws IOException {
        for (String temp = ""; temp != null; temp = in.readLine()) {
            temp = temp.toLowerCase();
            Matcher wordMatcher = patternMatch.matcher(temp);
            addingProbabilities(wordMatcher);
        }
    }

    // If the word is already in the HashMap, then increase count of it by 1
    // If not, then set its count to 1
    public void addingProbabilities(Matcher wordMatcher){
        while (wordMatcher.find()){
            // This will serve as an indicator to probability of a word
            String temp = wordMatcher.group();
            if (wordsProbabilitySet.containsKey(temp)){
                wordsProbabilitySet.put(temp, wordsProbabilitySet.get(temp) + 1);
            }
            else{
                wordsProbabilitySet.put(temp, 1);
            }
        }
    }

    // This method will add together the different possibilities of the word, by reading from the "words" file
    private StringArray edits(String word) {
        StringArray possibleCorrectedWords = new StringArray();
        firstWordPicker(word, possibleCorrectedWords);
        secondWordPicker(word, possibleCorrectedWords);
        return possibleCorrectedWords;
    }

    // This method will take the first substring of the word to be added to the StringArray
    // of possible correctedWords
    public void firstWordPicker(String word, StringArray possibleCorrectedWords){
        /*for (int i = 0; i < word.length(); ++i) {
            int count = 0;
            char characterToAdd = 'a';
            while(count < 26){
                possibleCorrectedWords.add(word.substring(0, i) + characterToAdd + word.substring(i + 1));
                ++count;
                ++characterToAdd;
            }
        }*/

        for (int i = 0; i < word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                possibleCorrectedWords.add(word.substring(0, i) + c + word.substring(i + 1));
            }
        }
    }

    // This method will take the second substring of the word to be added to the StringArray
    // of possible correctedWords
    public void secondWordPicker(String word, StringArray possibleCorrectedWords){
        /*for (int i = 0; i < word.length(); ++i) {
            int count = 0;
            char characterToAdd = 'a';
            while(count < 26){
                possibleCorrectedWords.add(word.substring(0, i) + characterToAdd + word.substring(i));
                ++count;
                ++characterToAdd;
            }
        }*/
        for (int i = 0; i <= word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                possibleCorrectedWords.add(word.substring(0, i) + c + word.substring(i));
            }
        }
    }

    // Method that compares input to dictionary words and checks for corrections on the others
    public HashMap<Integer, String> addition(String word) {
        StringArray possibleCorrections = edits(word);
        HashMap<Integer, String> collectionOfCandidates = new HashMap<>();

        setStringInPotentialWordsHashMap(possibleCorrections, collectionOfCandidates);
        return collectionOfCandidates;
    }

    // This method sets the String in the StringArray into the potentialWords HashMap
    public void setStringInPotentialWordsHashMap(StringArray possibleCorrections, HashMap<Integer, String> collectionOfCandidates){
        for(int index = 0; index < possibleCorrections.size(); index++){ // Iterating through the list of all possible corrections to the word.
            String s = possibleCorrections.get(index);
            if (wordsProbabilitySet.containsKey(s)){
                collectionOfCandidates.put(wordsProbabilitySet.get(s), s);
            }
        }
    }

    // This method returns the word that has the highest probability out of all the words
    public String correct(String word){
        HashMap<Integer, String> collectionOfCandidates = addition(word);
        if (collectionOfCandidates.size() > 0) {
            Set<Integer> valuesOfWords = collectionOfCandidates.keySet();
            Integer maximumProbability = Collections.max(valuesOfWords);
            String stringWithHighestProbability = collectionOfCandidates.get(maximumProbability);
            return stringWithHighestProbability;
        }
        else{
            return "Sorry but no possible corrections found!";
        }
    }
}