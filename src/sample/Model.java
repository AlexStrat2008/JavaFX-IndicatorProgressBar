package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {

    private StringProperty status;
    private StringProperty progress;

    public Model(String status, String surname) {
        this.status = new SimpleStringProperty(status);
        this.progress = new SimpleStringProperty(surname);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getProgress() {
        return progress.get();
    }

    public StringProperty progressProperty() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress.set(progress);
    }
}
