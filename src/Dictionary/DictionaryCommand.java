package Dictionary;

import static Dictionary.DictionaryManager.*;
import static Dictionary.DictionaryManager.insertFromFile;

public class DictionaryCommand {
    void  showAllWords(){
        int n = dictionary.words.size();

        int max = "|English".length();
        for(int i=0; i<n; i++)
            if (dictionary.words.get(i).getWordTarget().length() > max) {
                max = dictionary.words.get(i).getWordTarget().length();
                System.out.print("No\t|English");
            }
        for (int i = "|English".length(); i < max; ++i) System.out.print(" ");
        System.out.println("\t\t\t|Vietnamese ");

        for(int i=0; i<n ; i++) {
            System.out.print((i+1) + "\t" + dictionary.words.get(i).getWordTarget());
            for(int j = dictionary.words.get(i).getWordTarget().length(); j<max ;++j) {
                System.out.print(" ");
            }
            System.out.println("\t\t\t" + dictionary.words.get(i).getWordExplain());
        }
    }
    public void dictionaryBasic(){
        insertFromFile();
        showAllWords();
    }
    public void dictionaryAdvanced() {
        insertFromFile();
        showAllWords();
        dictionaryLookup();
        dictionarySearching();
        editData();
    }
}
