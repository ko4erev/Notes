import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;


public class MainController {

    @FXML
    private Button buttonAdd;

    @FXML
    TableView<DataBaseModel> tableView;

    @FXML
    private TableColumn<DataBaseModel, String> idColumn;

    @FXML
    private TableColumn<DataBaseModel, String> noteColumn;

    @FXML
    private TableColumn<DataBaseModel, String> dateColumn;

    private ObservableList<DataBaseModel> observableList = FXCollections.observableArrayList();


    @FXML
    public void initialize() throws Exception {

        // Создание таблицы/Выборка данных (в отдельном потоке)
        new DataBaseConnect() {
            @Override
            public void run() {
                try {
                    selectAll(observableList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        .start();

        // Добавление данных
        idColumn.setCellValueFactory(new PropertyValueFactory<DataBaseModel, String>("id"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<DataBaseModel, String>("note"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<DataBaseModel, String>("date"));

        // Добавление данных в таблицу
        tableView.setItems(observableList);
    }

    // Срабатывание кнопки
    public void mainClick(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("note.fxml"));
        stage.setTitle("Добавление заметки");
        stage.setScene(new Scene(root, 320, 300));
        stage.setResizable(false);
        stage.show();

        // Закрытие текущего окна
        Stage mainStage = (Stage) buttonAdd.getScene().getWindow();
        mainStage.close();
    }


}
