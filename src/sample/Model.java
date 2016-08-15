package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {

    private StringProperty status;
    private DoubleProperty progress;

    public Model() {
        status = new SimpleStringProperty();
        progress = new SimpleDoubleProperty();
    }

    public Model(String status, double surname) {
        this.status = new SimpleStringProperty(status);
        this.progress = new SimpleDoubleProperty(surname);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public double getProgress() {
        return progress.get();
    }

    public void setProgress(double surname) {
        this.progress.set(surname);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public DoubleProperty progressProperty() {
        return progress;
    }
}
