package notes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import notes.MainApp;
import notes.model.Notes;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Alexander on 04.10.2017.
 */
public class DialogController {
    @FXML
    private DatePicker date_text;
    @FXML
    private TextField time_text;
    @FXML
    private TextField notes_text;

    @FXML
    private void initialize()
    {
        Date input = new Date();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        date_text.setValue(date);
        SimpleDateFormat datFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String time =  datFormat.format(input);
        time_text.setText(time);

    }

    // кнопка добавить
    public void addNotes(ActionEvent actionEvent) {
        String data = date_text.getValue().toString();
        String time = time_text.getText();
        String text = notes_text.getText();

        if (data.isEmpty() || time.isEmpty() || text.isEmpty())
            showErrorDialog("Ошибка", "Все поля должны быть заполнены");
        else if (text.length() > 100)
            showErrorDialog("Ошибка", "Текст заметки не должен привышать 100 символов");
        else
        {
            Notes notes = new Notes (data, time, text);
            MainApp.mainApp.getMainController().tableAddNotes(notes);
            close(actionEvent);
        }
    }


    // кнопка Отмена
    public void close(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void  showErrorDialog (String title, String text) {
        Alert alert = new Alert (Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText("");
        alert.showAndWait();
    }
}
