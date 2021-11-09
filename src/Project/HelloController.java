package Project;

import Dictionary.DictionaryManager;
import Dictionary.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class HelloController {

    @FXML
    ListView<String> WorList;
    @FXML
    private TextField WordEnter;
    @FXML
    private Label WordTarget;
    @FXML
    private Label WordExplain;

    public void lookup(ActionEvent activeEvent) {
        String wordEnter = WordEnter.getText();
        int n = DictionaryManager.dictionary.words.size();
        for (int i = 0; i < n; i++) {
            if (wordEnter.equalsIgnoreCase(DictionaryManager.dictionary.words.get(i).getWordTarget())) {
                WordTarget.setText(wordEnter);
                WordExplain.setText(DictionaryManager.dictionary.words.get(i).getWordExplain());
                break;
            }
        }
    }



    public void initialize() {

        for (int j = 0;j < DictionaryManager.dictionary.words.size(); j++) WorList.getItems().add(DictionaryManager.dictionary.words.get(j).getWordTarget());
        WordEnter.textProperty().addListener((obs, oldText, newText) -> {
            try {
                String s;
                boolean bl = false;
                WorList.getItems().clear();
                s = WordEnter.getText();
                int n = DictionaryManager.dictionary.words.size();
                for (int i = 0;i < n; i++){
                    String temp = "";
                    if (s.length()<=DictionaryManager.dictionary.words.get(i).getWordTarget().length()) temp = DictionaryManager.dictionary.words.get(i).getWordTarget().substring(0,s.length());
                    if (temp.equalsIgnoreCase(s)) {
                        WorList.getItems().add(DictionaryManager.dictionary.words.get(i).getWordTarget());
                        bl = true;
                    }
                }

                if (!bl) WorList.getItems().add("Không tìm thấy từ muốn tra");

                if (WordEnter.getText().equalsIgnoreCase(WorList.getItems().get(0))){
                    WordTarget.setText(WorList.getItems().get(0));
                    for (int j = 0; j < n; j++) {
                        if (WorList.getItems().get(0).equalsIgnoreCase(DictionaryManager.dictionary.words.get(j).getWordTarget())) {
                            WordExplain.setText(DictionaryManager.dictionary.words.get(j).getWordExplain());
                            break;
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println("wordSearch. changeWord. " + ex);
            }
        });

        WorList.setOnMouseClicked(e -> {
            String wordTarget = WorList.getSelectionModel().getSelectedItem();
            if (wordTarget == null) {
                return;
            }
            for (Word w: DictionaryManager.dictionary.words) {
                if (w.getWordTarget().equals(wordTarget)) {
                    WordTarget.setText(wordTarget);
                    WordExplain.setText(w.getWordExplain());
                    break;
                }
            }


        });
    }

    public void addAndDelete (ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ControllerWord.fxml"));
        Parent studentViewParent = loader.load();
        Scene scene = new Scene(studentViewParent);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Home.css")).toExternalForm());

        stage.setScene(scene);



    }
}