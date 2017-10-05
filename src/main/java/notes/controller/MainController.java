package notes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import notes.MainApp;
import notes.dao.ManagerBD;
import notes.model.Notes;

import java.io.IOException;

/**
 * Created by Alexander on 04.10.2017.
 */
public class MainController {
    @FXML
    private TableView<Notes> table;
    @FXML
    private TableColumn<Notes, String> columnDate;
    @FXML
    private TableColumn<Notes, String> columnTime;
    @FXML
    private TableColumn<Notes, String> columnNotes;


    @FXML
    private void initialize(){
        columnDate.setCellValueFactory(new PropertyValueFactory<Notes, String>("data"));
        columnTime.setCellValueFactory(new PropertyValueFactory<Notes, String>("time"));
        columnNotes.setCellValueFactory(new PropertyValueFactory<Notes, String>("text"));

        ObservableList<Notes> tableData = FXCollections.observableArrayList();
        table.setItems(tableData);
    }

    public void showDialog(ActionEvent actionEvent) {
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/edit.fxml"));
            stage.setTitle("Добавить заметку");
            stage.setMinHeight(172);
            stage.setMinWidth(434);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    // добавить запись в таблицу
    public void tableAddNotes(Notes notes){
        ObservableList<Notes> data = table.getItems(); // получаем данные
        data.add(notes); // добавляем строку
        table.refresh();
        ManagerBD.save(notes); // запись в бд
    }

    // удалить запись из таблицы
    public void deleteNotes(ActionEvent actionEvent) {
        int index = table.getSelectionModel().getSelectedIndex();
        Notes notes = table.getItems().get(index);
        table.getItems().remove(index);
        table.refresh();
        ManagerBD.delete(notes);
    }
}
