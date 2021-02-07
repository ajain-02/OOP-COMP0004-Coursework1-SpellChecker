package myspellchecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SpellChecker {
    private final StringArray dictionaryLibrary;
    private final StringArray inputText;
    private StringArray wordsThatDoNotMatch;
    public Scanner input;

    public SpellChecker() {
        dictionaryLibrary = new StringArray();
        inputText = new StringArray();
        input = new Scanner (System.in);
    }

    public void readingAndStoring() throws FileNotFoundException {
        File wordsLibrary = new File("words");
        input = new Scanner(wordsLibrary);

        while(input.hasNextLine()){
            String words = input.nextLine();
            dictionaryLibrary.add(words);
        }
    }

    public String inputMethod() throws FileNotFoundException {
        String stringToCheck;
        showTitle();
        int choiceOfInput = askForTerminalOrFile();
        stringToCheck = takeInputFromTerminalOrFile(choiceOfInput);
        System.out.println();
        stringToCheck = stringToCheck.replace(".", "");
        stringToCheck = stringToCheck.replace(",", "");
        return stringToCheck;
    }

    public String takeInputFromTerminalOrFile(int choiceOfInput) throws FileNotFoundException {
        String stringToCheck = "";
        if (choiceOfInput == 1){
            stringToCheck = takeInputFromTerminal();
        }
        else if (choiceOfInput == 2){
            stringToCheck = takeInputFromFile();
        }
        else{
            System.out.println("You can only provide an input through Terminal or File!!!");
            System.exit(1);
        }
        return stringToCheck;
    }

    public StringArray addingTextToStringArray(String inputTextInString){
        for (String s : inputTextInString.split(" ")) {
            inputText.add(s);
        }
        return inputText;
    }

    public StringArray comparison(){
        wordsThatDoNotMatch = new StringArray();
        for(int index = 0; index < inputText.size(); index++){
            String stringToCheck = inputText.get(index);
            if (!dictionaryLibrary.contains(stringToCheck)){
                wordsThatDoNotMatch.add(stringToCheck);
            }
        }
        return wordsThatDoNotMatch;
    }

    public void print(){
        if(wordsThatDoNotMatch.isEmpty()){
            System.out.println("Good news! There are no incorrect words! Correct sentence has been stored in 'outputSentence.txt'");
        }
        else {
            System.out.print("Words that are invalid: [");
            gettingWordsThatDontMatch();
            System.out.println("]");
        }
    }

    public void gettingWordsThatDontMatch(){
        for (int i = 0; i < wordsThatDoNotMatch.size(); i++) {
            if (i == (wordsThatDoNotMatch.size() - 1)) {
                System.out.print(wordsThatDoNotMatch.get(i));
            } else {
                System.out.print(wordsThatDoNotMatch.get(i) + ", ");
            }
        }
    }

    public void showTitle(){
        System.out.println("------------------------------");
        System.out.println("WELCOME TO \"SPELLING CHECKER\"");
        System.out.println("------------------------------");
    }

    public int askForTerminalOrFile(){
        System.out.println("Please select one of the following options:");
        System.out.println("1) Enter Sentence in the Terminal");
        System.out.println("2) Read Sentence from a File\n");
        System.out.print("Option Number Selected: ");
        input = new Scanner(System.in);
        return input.nextInt();
    }

    public String takeInputFromTerminal(){
        String stringInput;
        System.out.println("\nPlease enter your sentence to check: ");
        input = new Scanner (System.in);
        stringInput = input.nextLine();
        return stringInput;
    }

    public String takeInputFromFile() throws FileNotFoundException {
        StringBuilder stringFileInput = new StringBuilder();
        File inputSentence = new File("inputSentence.txt");
        input = new Scanner(inputSentence);
        System.out.println("\nSentence to be checked is being read from 'inputSentence.txt'... ");
        while(input.hasNextLine()){
            String words = input.nextLine();
            stringFileInput.append(words);
        }
        return stringFileInput.toString();
    }

    public void showExitMessage(){
        System.out.println("----------------------------------------");
        System.out.println("THANK YOU FOR USING \"SPELLING CHECKER\"!");
        System.out.println("----------------------------------------");
    }
}
