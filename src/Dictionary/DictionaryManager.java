package Dictionary;

import java.io.*;
import java.util.Scanner;

public class DictionaryManager {
    static Scanner sc = new Scanner(System.in);
    public static Dictionary dictionary;

    public static void insertFormCommandline() {
        int n;
        n = Integer.parseInt(sc.nextLine());
        dictionary = new Dictionary(n);
        for (int i=0; i<n; i++) {
            dictionary.words.get(i).setWordTarget(sc.nextLine());
            dictionary.words.get(i).setWordExplain(sc.nextLine());
        }
    }

    public static void insertFromFile() {
        dictionary = new Dictionary();
        int n = 0;
        try {
            File f = new File("dictionary.txt");
            BufferedReader br;
            try (FileReader fr = new FileReader(f)) {
                br = new BufferedReader((fr));
                String s;
                for (int i = 0; (s = br.readLine()) != null; i++) {
                    String[] words = s.split("\\t", 2);
                    if (words.length == 2) dictionary.addWord(words[0], words[1]);
                    else {
                        dictionary.addWord("Error Reading", " ");
                    }
                }
                fr.close();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile() {
        dictionary.sortWord();
        try {
            FileWriter fw = new FileWriter("dictionary.txt");
            for (int i=0; i<dictionary.words.size(); i++) {
                fw.write(dictionary.words.get(i).getWordTarget()
                        + "\t" + dictionary.words.get(i).getWordExplain() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dictionaryLookup() {
        String s;
        int n = dictionary.words.size();
        boolean bl = false;
        System.out.println("Enter the word: ");
        s = sc.nextLine();

        for (int i=0; i<n; i++) {
            if (s.equalsIgnoreCase(dictionary.words.get(i).getWordTarget())) {
                System.out.println(dictionary.words.get(i).getWordExplain());
                bl = true;
            }
        }
        if (!bl) {
            System.out.println("Word is not exist");
        }
    }

    public static void editData() {
        int n = dictionary.words.size();
        boolean bl = false;
        System.out.println("Wanna edit word: ");
        String s = sc.nextLine();
        int m=-1;
        for(int i=0; i<n; i++) {
            if (s.equalsIgnoreCase(dictionary.words.get(i).getWordTarget())) {
                System.out.println("Do you want to edit the target or explain?\n1. target\n2.explain\nEnter 0 để thoát!\n");
                m = sc.nextInt();
                while (m!=1 && m!=2 && m!=0) {
                    System.out.println("Vui lòng nhập 1 hoặc 2\n1. target\n2.explain\nEnter 0 để thoát!\n");
                    m = sc.nextInt();
                }
                sc.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
                if (m==1) {
                    System.out.println("Muốn sửa thành gì?");
                    String temp = sc.nextLine();
                    dictionary.words.get(i).setWordTarget(temp);
                    bl = true;
                }
            }
            else if (m==2) {
                System.out.println("Muốn sửa thành gì?");
                String temp = sc.nextLine();
                dictionary.words.get(i).setWordTarget(temp);
                bl = true;
            }
            else break;
        }
        if (!bl && m==0) {
            System.out.println("Sửa thất bại");
        }
        else  if (!bl) {
            System.out.println("Không tìm thấy từ cần sửa");
        }
        else {
            System.out.println("Sửa thành công");
        }
    }

    public static void addData() {
        System.out.println("Từ muốn thêm: ");
        String target;
        target = sc.nextLine();
        System.out.println("Tư muốn thêm: ");
    }

    public static void dictionarySearching() {
        String s;
        boolean bl = false;
        System.out.println("Từ muốn tìm: ");
        s = sc.nextLine();
        int n = dictionary.words.size();

        for (int i=0; i<n; i++) {
            String temp = "";
            if (s.length()<=dictionary.words.get(i).getWordTarget().length()) {
                temp = dictionary.words.get(i).getWordTarget().substring(0, s.length());
            }
            if (temp.equalsIgnoreCase(s)) {
                int max = "|English".length();
                System.out.println(dictionary.words.get(i).getWordTarget());
                for (int j=dictionary.words.get(i).getWordTarget().length(); j<max; j++) {
                    System.out.println(" ");
                }
                System.out.println("\t\t\t" + dictionary.words.get(i).getWordExplain());
                bl = true;
            }
        }
        if (!bl) {
            System.out.println("Not Found!");
        }
    }
}
