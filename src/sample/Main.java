package sample;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
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
        final int TOTAL_WORK = 1500;

        final ReadOnlyIntegerWrapper workDone = new ReadOnlyIntegerWrapper();

        final ProgressIndicatorBar bar = new ProgressIndicatorBar(
                workDone.getReadOnlyProperty(),
                TOTAL_WORK
        );

        workDone.set(0);

        new Thread(() -> {
            while(workDone.get() != TOTAL_WORK){
                workDone.set(workDone.get() + 1);
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        final Button resetButton = new Button("Reset");

        final VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 20px;");
        layout.getChildren().addAll(bar, resetButton);

        return layout;
    }
}

class ProgressIndicatorBar extends StackPane {
    final private ReadOnlyIntegerProperty workDone;
    final private double totalWork;

    final private ProgressBar bar = new ProgressBar();
    final private Text text = new Text();

    final private static int DEFAULT_LABEL_PADDING = 5;

    ProgressIndicatorBar(final ReadOnlyIntegerProperty workDone, final double totalWork) {
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
