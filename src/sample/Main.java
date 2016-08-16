package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
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

        TableView<Model> table = new TableView();
        TableColumn<Model, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableColumn<Model, String> progressColumn = new TableColumn<>("Progress");
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        table.getColumns().addAll(statusColumn, progressColumn);

        int max = 160;

        ObservableList<Model> models = FXCollections.observableArrayList();
        models.setAll(
                new Model("Before", "0/" + max),
                new Model("Before", "0/" + max),
                new Model("Before", "0/" + max)
        );
        table.getItems().setAll(models);

        new Thread(() -> {
            for (int i = 0; i < models.size(); i++) {
                models.get(i).setStatus("Progress..");
                for (int j = 0; j <= 160; j++) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String progress = j + "/" + max;
                    models.get(i).setProgress(progress);
                }
                models.get(i).setStatus("Done..");
            }
        }).start();

        final VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 20px;");
        layout.getChildren().addAll(table);

        return layout;
    }
}
