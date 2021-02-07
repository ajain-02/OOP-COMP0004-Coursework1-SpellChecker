package myspellchecker;

import java.io.*;
import java.util.Scanner;

public class MainMethodsForMainToRun extends SpellChecker{
    private final SpellChecker spellChecker;
    private StringArray wrongWordsStore;
    private StringArray stringArrayText;
    private final SuggestSpelling wordSuggestion;

    public MainMethodsForMainToRun() {
        spellChecker = new SpellChecker();
        wrongWordsStore = new StringArray();
        stringArrayText = new StringArray();
        wordSuggestion = new SuggestSpelling("words");
    }

    public void mainMethods() throws IOException {
        try{
            spellChecker.readingAndStoring();
            String inputText = spellChecker.inputMethod();
            stringArrayText = spellChecker.addingTextToStringArray(inputText);
            wrongWordsStore = spellChecker.comparison();
            spellChecker.print();
            correction();
            writeToCorrectedFile();
            spellChecker.showExitMessage();
        }
        catch(FileNotFoundException e){
            System.out.println("Something went wrong: FileNotFoundException was thrown");
        }
    }

    public void writeToCorrectedFile() throws IOException {
        File correctedFile = new File("outputSentence.txt");
        if(correctedFile.createNewFile()){
            System.out.println("File 'outputSentence.txt' has been created");
        }
        FileWriter myWriter = new FileWriter("outputSentence.txt");
        for(int i = 0; i < stringArrayText.size(); i++){
            myWriter.write(stringArrayText.get(i));
            myWriter.write(" ");
        }
        myWriter.close();
    }

    public void correction() {
        if(wrongWordsStore.size() != 0){
            System.out.println("Words have been found that don't match. Proceeding to correct them...");
            addingCorrection();
            System.out.println("\nCorrect sentence has been stored in 'outputSentence.txt'\n");
        }
    }

    public void addingCorrection() {
        String chosenReplacement;
        for(int i = 0; i < wrongWordsStore.size(); i++){
            chosenReplacement = wordSuggestion.correct(wrongWordsStore.get(i));
            checkIfCorrectionFound(chosenReplacement, i);
        }
    }

    public void checkIfCorrectionFound(String chosenReplacement, int i) {
        if (chosenReplacement.equals("Sorry but no possible corrections found!")){
            System.out.println("\nNo possible corrections found for " + wrongWordsStore.get(i));
            takingUserWordSuggestion(i);
        }
        else{
            System.out.println("\nWord suggestion for " + wrongWordsStore.get(i) + ": " + chosenReplacement);
            String wordReplacementChoice = askForChoice();
            acceptDenyWordSuggestion(wordReplacementChoice, chosenReplacement, i);
        }
    }

    public void acceptDenyWordSuggestion(String wordReplacementChoice, String chosenReplacement, int i) {
        if(wordReplacementChoice.equalsIgnoreCase("Yes")) {
            int indexOfWrongWord = stringArrayText.indexOf(wrongWordsStore.get(i));
            stringArrayText.set(indexOfWrongWord, chosenReplacement);
        }
        else{
            takingUserWordSuggestion(i);
        }
    }

    public String askForChoice(){
        System.out.println("Would you like to accept the word suggestion? Enter \"Yes\" or \"No\"");
        input = new Scanner(System.in);
        return input.nextLine();
    }
    
    public void takingUserWordSuggestion(int i) {
        System.out.println("Would you like to add your own word suggestion? Enter \"Yes\" or \"No\"");
        input = new Scanner(System.in);
        String userChoice = input.nextLine();
        String userWordCorrection;
        int indexOfWrongWord = stringArrayText.indexOf(wrongWordsStore.get(i));
        replaceText(userChoice, indexOfWrongWord, i);
    }

    public void replaceText(String userChoice, int indexOfWrongWord, int i){
        if (userChoice.equals("Yes")){
            System.out.print("Please enter your word suggestion: ");
            String userWordCorrection = input.nextLine();
            stringArrayText.set(indexOfWrongWord, userWordCorrection);
        }
        else{
            stringArrayText.set(indexOfWrongWord, wrongWordsStore.get(i));
        }
    }
}