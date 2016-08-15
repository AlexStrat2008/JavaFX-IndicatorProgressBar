package sample;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        stage.setScene(new Scene(createResettableProgressIndicatorBar()));
        stage.show();
    }

    private VBox createResettableProgressIndicatorBar() {

        TableView table = new TableView();
        TableColumn<Model, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<Model, String>("status"));
        TableColumn<TestTask, Double> progressColumn = new TableColumn<>("Progress");
        progressColumn.setCellValueFactory(new PropertyValueFactory<TestTask, Double>("progress"));
        progressColumn.setCellFactory(ProgressBarTableCell.<TestTask> forTableColumn());
        table.getColumns().addAll(statusColumn, progressColumn);

        ObservableList<Model> tasks = FXCollections.observableArrayList();

        Model model = new Model("done..", 0, 16);
        tasks.addAll(model);
        table.getItems().addAll(tasks);

        DoubleProperty progress = tasks.get(0).progressProperty();

        progress.set(1);

        final VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 20px;");
        layout.getChildren().addAll(table);

        return layout;
    }

    static class TestTask extends Task<Void> {

        DoubleProperty progress;
        public static final int NUM_ITERATIONS = 100;

        TestTask(DoubleProperty progress) {
            this.progress = progress;
        }

        @Override
        protected Void call() throws Exception {
            while(progress.get() != 16){
                progress.set(progress.get() + 1);
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }
}

class ProgressIndicatorBar extends StackPane {
    final private DoubleProperty workDone;
    final private double totalWork;

    final private ProgressBar bar = new ProgressBar();
    final private Text text = new Text();

    final private static int DEFAULT_LABEL_PADDING = 5;

    ProgressIndicatorBar(DoubleProperty workDone, double totalWork) {
        this.workDone = workDone;
        this.totalWork = totalWork;

        workDone.addListener((observableValue, number, number2) -> {
            syncProgress();
        });

        bar.setMaxWidth(Double.MAX_VALUE);

        getChildren().setAll(bar, text);
    }

    private void syncProgress() {
        if (workDone == null || totalWork == 0) {
            text.setText("");
            bar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        } else {
            text.setText(Math.round(workDone.get()) + "/" + (int)Math.round(totalWork));
            bar.setProgress(workDone.get() / totalWork);
        }

        bar.setMinHeight(text.getBoundsInLocal().getHeight() + DEFAULT_LABEL_PADDING * 2);
        bar.setMinWidth(text.getBoundsInLocal().getWidth() + DEFAULT_LABEL_PADDING * 2);
    }
}
