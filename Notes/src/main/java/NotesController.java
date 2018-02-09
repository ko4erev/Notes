
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class NotesController {

    @FXML
    private TextArea textArea;

    @FXML
    private Button buttonOk;


    // Нажатие кнопки
    public void noteClick(ActionEvent actionEvent) throws Exception {

        // Ограничение по максимальному количеству символов
        String text = textArea.getText();
        final int maxLength = 100;
        if (text.length() > maxLength) {
            char[] noteChar = new char[maxLength];
            text.getChars(0, maxLength, noteChar, 0);
            text = new String(noteChar);
        }
        else {
            text = textArea.getText();
        }

        // Текст заметки
        final String note = text;

        // Текущие дата/время
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat myDate = new SimpleDateFormat("dd.MM.yyyy   HH:mm", Locale.ENGLISH);
        final String date = myDate.format(calendar.getTime());

        // Добавление в БД (в отдельном потоке)
        new DataBaseConnect() {
            @Override
            public void run() {
                try {
                    insert(note, date);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        .start();

        // Закрытие текущего окна
        Stage stage = (Stage) buttonOk.getScene().getWindow();
        stage.close();

        // Чтобы успели записаться данных в БД
        Thread.sleep(500L);

        // Открытие main окна
        Stage mainStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        mainStage.setTitle("Заметки");
        mainStage.setScene(new Scene(root, 500, 500));
        mainStage.setResizable(false);
        mainStage.show();
    }

}