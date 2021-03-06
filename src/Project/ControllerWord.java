package Project;

import Dictionary.DictionaryManager;
import Dictionary.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerWord implements Initializable {
    @FXML
    private TextField textTarget;

    @FXML
    private TextField textExplain;

    @FXML
    private TextField wordEnter;

    @FXML
    private Label text;

    @FXML
    private TableView<Word> table;
    @FXML
    private TableColumn<Word, String> target;
    @FXML
    private TableColumn<Word, String> explain;

    private ObservableList<Word> wordList;


    public void lookup (ActionEvent activeEvent) {
        if (table.getItems().size()>0) {

            String wordEnter = table.getItems().get(0).getWordTarget();
            int n = DictionaryManager.dictionary.words.size();
            for (int i = 0; i < n; i++) {
                if (wordEnter.equalsIgnoreCase(DictionaryManager.dictionary.words.get(i).getWordTarget())) {
                    textTarget.setText(wordEnter);
                    textExplain.setText(DictionaryManager.dictionary.words.get(i).getWordExplain());
                    break;
                }
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wordList = FXCollections.observableArrayList();
        wordList.addAll(DictionaryManager.dictionary.words);
        target.setCellValueFactory(new PropertyValueFactory<>("wordTarget"));
        explain.setCellValueFactory(new PropertyValueFactory<>("wordExplain"));
        table.setItems(wordList);

        wordEnter.textProperty().addListener((obs, oldText, newText) -> {
            try {
                String s;
                boolean bl = false;
                wordList.clear();
                s = wordEnter.getText();
                int n = DictionaryManager.dictionary.words.size();
                for (int i = 0;i < n; i++){
                    String temp = "";
                    if (s.length()<=DictionaryManager.dictionary.words.get(i).getWordTarget().length()) temp = DictionaryManager.dictionary.words.get(i).getWordTarget().substring(0,s.length());
                    if (temp.equalsIgnoreCase(s)) {
                        wordList.add(DictionaryManager.dictionary.words.get(i));
                        table.setItems(wordList);
                    }
                }
            } catch (Exception ex) {
                System.out.println("wordSearch. changeWord. " + ex);
            }
        });


        table.setOnMouseClicked(e -> {
            Word wordTarget = table.getSelectionModel().getSelectedItem();
            if (wordTarget == null) {
                return;
            }
            textTarget.setText(wordTarget.getWordTarget());
            textExplain.setText(wordTarget.getWordExplain());

        });
    }

    public void delete(ActionEvent event) {
        Word selected = null;
        boolean bl = false;
        for (int i = 0; i<wordList.size();i++) if (wordList.get(i).getWordTarget().equalsIgnoreCase(textTarget.getText())) {
            selected = wordList.get(i);
            bl = true;
            break;
        }
        wordList.remove(selected);
        DictionaryManager.dictionary.words.remove(selected);
        DictionaryManager.writeToFile();
        if (!bl) text.setText("Kh??ng t??m th???y t??? b???n mu???n x??a");
        else text.setText("X??a t??? th??nh c??ng");
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Home.fxml"));
        Parent sampleParent = loader.load();
        Scene scene = new Scene(sampleParent);
        scene.getStylesheets().add(getClass().getResource("HomeCSS.css").toExternalForm());
        stage.setScene(scene);
    }

    public void add(ActionEvent actionEvent) {
        if (textTarget.getText().equalsIgnoreCase("") || textExplain.getText().equalsIgnoreCase("") ){
            text.setText("Nh???p ????? English v?? Vietnammes");
        }else {
            boolean bl = false;
            for (int i = 0; i < DictionaryManager.dictionary.words.size(); i++)
                if (textTarget.getText().equalsIgnoreCase(DictionaryManager.dictionary.words.get(i).getWordTarget()))
                    bl = true;
            if (!bl) {
                DictionaryManager.dictionary.addWord(textTarget.getText(), textExplain.getText());
                DictionaryManager.writeToFile();

                wordList = FXCollections.observableArrayList();
                wordList.addAll(DictionaryManager.dictionary.words);
                target.setCellValueFactory(new PropertyValueFactory<>("wordTarget"));
                explain.setCellValueFactory(new PropertyValueFactory<>("wordExplain"));
                table.setItems(wordList);
                text.setText("Th??m t??? th??nh c??ng");
            } else {
                text.setText("T??? ???? t???n t???i");
            }
        }
    }

    public void reset(ActionEvent actionEvent) {
        if (textTarget.getText().equalsIgnoreCase("") || textExplain.getText().equalsIgnoreCase("") ){
            text.setText("Nh???p ????? ti???ng Anh v?? ti???ng Vi???t");
        }else {
            boolean bl = false;
            for (int i = 0; i < DictionaryManager.dictionary.words.size(); i++)
                if (textTarget.getText().equalsIgnoreCase(DictionaryManager.dictionary.words.get(i).getWordTarget())) {
                    bl = true;
                    DictionaryManager.dictionary.words.get(i).setWordTarget(textTarget.getText());
                    DictionaryManager.dictionary.words.get(i).setWordExplain(textExplain.getText());
                    DictionaryManager.writeToFile();

                    wordList.clear();
                    wordList.addAll(DictionaryManager.dictionary.words);
                    target.setCellValueFactory(new PropertyValueFactory<>("wordTarget"));
                    explain.setCellValueFactory(new PropertyValueFactory<>("wordExplain"));
                    table.setItems(wordList);
                    text.setText("S???a t??? th??nh c??ng");
                }
            if(!bl) text.setText("Kh??ng t??m th???y t???");
        }
    }
}
