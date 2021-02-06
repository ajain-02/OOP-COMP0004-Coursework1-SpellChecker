import java.util.*;

public class StringArray{
    private String[] stringLibrary;

    // Declares a private counter for the number of elements added to the StringArray
    private int countofElements = 0;

    public StringArray(){
        this.stringLibrary = new String[100];
    }

    public StringArray(StringArray a){
        this.stringLibrary = new String[a.size()];
        for(int index = 0; index < a.size(); index++){
            add(a.get(index));
        }
    }

    public int size(){
        return countofElements;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public String get(int index){
        return this.stringLibrary[index];
    }

    public void set(int index, String s){
        if (index<0){
            throw new ArithmeticException("Can't have a negative Index");
        }
        else {
            for (int currentIndex = 0; currentIndex < size(); currentIndex++) {
                if (currentIndex == index) {
                    this.stringLibrary[currentIndex] = s;
                }
            }
        }
    }

    public void add(String s){
        if (countofElements == this.stringLibrary.length){
            this.stringLibrary = Arrays.copyOf(this.stringLibrary, (this.stringLibrary.length + 50));
        }
        this.stringLibrary[countofElements] = s;
        // Incrementing the counter for the number of elements by 1
        countofElements++;
    }

    public void insertInMiddle(int index, String s){
        String stringToInsert;
        // Make the StringArray bigger by 1, to allow the new element to be added
        this.stringLibrary = Arrays.copyOf(this.stringLibrary, this.stringLibrary.length + 1);
        // initially set to value parameter so the first iteration, the value is replaced by it
        stringToInsert = s;
        // Shift all elements to the right, starting at index
        for(int i = index; i < this.stringLibrary.length; i++){
            String temp = stringToInsert;
            stringToInsert = this.stringLibrary[i];
            this.stringLibrary[i] = temp;
        }
    }

    public void insert(int index, String s){
        if (index<0){
            throw new ArithmeticException("Can't have a negative Index");
        }
        else if (isEmpty()){
            add(s);
        }
        else{
            insertInMiddle(index, s);
        }
    }

    public void remove(int index){
        if (index<0){
            throw new ArithmeticException("Can't have a negative Index");
        }
        // To remove this element, we simply "shift" all elements after it. This means that we're going to iterate through
        // all the elements after 40 and simply "move" them one place to the left.
        if (size() - 1 - index >= 0)
            System.arraycopy(this.stringLibrary, index + 1, this.stringLibrary, index, size() - 1 - index);
        this.stringLibrary = Arrays.copyOf(this.stringLibrary, size());
        countofElements--;
    }

    public boolean contains(String s){
        for(int index = 0; index < size(); index++) {
            if(s.compareToIgnoreCase(this.stringLibrary[index])==0){
                return true;
            }
        }
        return false;
    }

    public boolean containsMatchingCase(String s){
        for(int index = 0; index < size(); index++) {
            if(s.compareTo(this.stringLibrary[index])==0){
                return true;
            }
        }
        return false;
    }

    public int indexOf(String s){
        for(int index = 0; index < size(); index++){
            if(this.stringLibrary[index].equals(s)){
                return index;
            }
        }
        return -1;
    }

    public int indexOfMatchingCase(String s){
        for(int index = 0; index < size(); index++){
            if(containsMatchingCase(s)){
                return index;
            }
        }
        return -1;
    }
}