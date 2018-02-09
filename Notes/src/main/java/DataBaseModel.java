public class DataBaseModel {

    private int id;
    private String note;
    private String date;

    DataBaseModel(int id, String note, String date) {
        this.id = id;
        this.note = note;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }
}