import javafx.collections.ObservableList;

import java.sql.*;

public class DataBaseConnect extends Thread {

    final private String URL = "jdbc:mysql://localhost:3306/notes";
    final private String USER = "root";
    final private String PASSWORD = "root";


    // Выборка всех данных
    public void selectAll(ObservableList<DataBaseModel> observableList) throws SQLException {

        // Соединение с БД
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement();

        // Создать таблицу, если нет
        statement.execute("CREATE TABLE IF NOT EXISTS notes (" +
                "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "note VARCHAR(100), " +
                "date VARCHAR(20) NOT NULL);");

        // Выборка данных
        ResultSet resultSet = statement.executeQuery("SELECT * FROM notes");

        // Добавление данных в коллекцию
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String note = resultSet.getString("note");
            String date = resultSet.getString("date");

            observableList.add(new DataBaseModel(id, note, date));
        }

        // Закрытие соединения
        statement.close();
        connection.close();
    }


    // Добавление данных
    public void insert(String note, String date) throws Exception{

        // Соединение с БД
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        // Добавление в БД
        String insert = "INSERT INTO notes (note, date) VALUES (?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setString(1, note);
        preparedStatement.setString(2, date);
        preparedStatement.execute();

        // Закрытие соединения
        preparedStatement.close();
        connection.close();
    }

}
