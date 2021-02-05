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
            fileWrite();
            spellChecker.showExitMessage();
        }
        catch(FileNotFoundException e){
            System.out.println("Something went wrong: FileNotFoundException was thrown");
        }
    }

    public void fileWrite() throws IOException {
        File correctedFile = new File("correctedSentence.txt");
        if(correctedFile.createNewFile()){
            System.out.println("File 'correctedSentence.txt' has been created");
        }

        FileWriter myWriter = new FileWriter("correctedSentence.txt");

        for(int i = 0; i < stringArrayText.size(); i++){
            myWriter.write(stringArrayText.get(i));
            myWriter.write(" ");
        }
        myWriter.close();
    }

    public void correction() throws IOException {
        if(wrongWordsStore.size() != 0){
            System.out.println("Words have been found that don't match. Proceeding to correct them...");
            addingCorrection();
            System.out.println("\nCorrect sentence has been stored in 'correctedSentence.txt'\n");
        }
    }

    public void addingCorrection() throws IOException {
        String chosenReplacement;
        for(int i = 0; i < wrongWordsStore.size(); i++){
            chosenReplacement = wordSuggestion.correct(wrongWordsStore.get(i));
            checkIfCorrectionFound(chosenReplacement, i);
        }
    }

    public void checkIfCorrectionFound(String chosenReplacement, int i) throws IOException {
        if (chosenReplacement.equals("Sorry but no possible corrections found!")){
            System.out.println("\nNo possible corrections found for " + wrongWordsStore.get(i));
        }
        else{
            System.out.println("\nWord suggestion for " + wrongWordsStore.get(i) + ": " + chosenReplacement);
            String wordReplacementChoice = askForChoice();
            actingOnChoice(wordReplacementChoice, chosenReplacement, i);
        }
    }

    public void actingOnChoice(String wordReplacementChoice, String chosenReplacement, int i) throws IOException {
        if(wordReplacementChoice.equalsIgnoreCase("Yes")) {
            int indexOfWrongWord = stringArrayText.indexOf(wrongWordsStore.get(i));
            stringArrayText.set(indexOfWrongWord, chosenReplacement);
        }
        else{
            String userWordSuggestion = takingUserWordSuggestion();
            int indexOfWrongWord = stringArrayText.indexOf(wrongWordsStore.get(i));
            stringArrayText.set(indexOfWrongWord, userWordSuggestion);
        }
    }

    public String askForChoice(){
        System.out.println("Would you like to accept the word suggestion? Enter \"Yes\" or \"No\"");
        input = new Scanner(System.in);
        return input.nextLine();
    }
    
    public String takingUserWordSuggestion() throws IOException {
        System.out.println("Would you like to add your own word suggestion? Enter \"Yes\" or \"No\"");
        input = new Scanner(System.in);
        String userChoice = input.nextLine();
        String userWordCorrection;
        if (userChoice.equals("Yes")){
            System.out.print("Please enter your word suggestion: ");
            userWordCorrection = input.nextLine();
            return userWordCorrection;
        }
        else{
            endOfProgram();
        }
        return "";
    }

    public String doingUserAction(String userChoice) throws IOException {
        if (userChoice.equals("Yes")){
            System.out.print("Please enter your word suggestion: ");
            String userWordCorrection = input.nextLine();
            return userWordCorrection;
        }
        else{
            endOfProgram();
        }
        return "";
    }
    
    public void endOfProgram() throws IOException {
        storingUncorrectedSentence();
        System.out.println("\nIncorrect words have NOT been corrected! Incorrect sentence has been stored in 'uncorrectedSentence.txt'\n");
        showExitMessage();
        System.exit(0);
    }

    public void storingUncorrectedSentence() throws IOException {
        File uncorrectedFile = new File("uncorrectedSentence.txt");
        if(uncorrectedFile.createNewFile()){
            System.out.println("File 'uncorrectedSentence.txt' has been created");
        }
        writeToUncorrectedFile();
    }

    public void writeToUncorrectedFile() throws IOException {
        FileWriter myWriter = new FileWriter("uncorrectedSentence.txt");
        for(int i = 0; i < stringArrayText.size(); i++){
            myWriter.write(stringArrayText.get(i));
            myWriter.write(" ");
        }
        myWriter.close();
    }
}