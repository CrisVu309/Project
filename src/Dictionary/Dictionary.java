package Dictionary;

import java.util.ArrayList;

public class Dictionary {
    public ArrayList<Word> words = new ArrayList<Word>();

    Dictionary(int n) {
        for (int i=0; i<n; i++) {
            words.add(new Word());
        }
    }

    public  Dictionary() {

    }

    public void addWord(String target, String explain) {
        words.add(new Word());
        words.get(words.size()-1).setWordTarget(target);
        words.get(words.size()-1).setWordExplain(explain);
    }

    public void sortWord() {
        for (int i=0; i<words.size(); i++) {
            for (int j=i; j<words.size(); j++) {
                if (words.get(j).getWordTarget().compareTo(words.get(i).getWordTarget())>0) {
                    Word temp = words.get(i);
                    words.set(i, words.get(j));
                    words.set(j, temp);
                }
            }
        }
    }


}
