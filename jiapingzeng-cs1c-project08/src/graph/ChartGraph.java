package graph;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Display chart with selectable data options and configurable x range
 */
public class ChartGraph extends Application {

    @Override
    public void start(Stage stage) {
        DataModel model = new DataModel("resources/data.csv");
        ArrayList<Integer> sortedKeys = new ArrayList<>(model.getData().keySet());
        Collections.sort(sortedKeys);

        BorderPane pane = new BorderPane();
        GraphView graphView = new GraphView(model);
        graphView.update();
        pane.setCenter(graphView);

        VBox vbox = new VBox();
        vbox.setStyle("-fx-border-color: gray; -fx-background-color: lightgray;");

        Text text = new Text("Select a set of data: ");
        text.setFont(new Font(15));
        vbox.getChildren().add(text);
        VBox.setMargin(text, new Insets(5, 5, 5, 5));

        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("All");
        for (int key : sortedKeys) {
            list.add("Array with size " + key);
        }
        ComboBox<String> comboBox = new ComboBox<>(list);
        comboBox.getSelectionModel().selectFirst();
        vbox.getChildren().add(comboBox);
        VBox.setMargin(comboBox, new Insets(5, 5, 5, 5));

        Label minLable = new Label("Min x:");
        VBox.setMargin(minLable, new Insets(5, 5, 0, 5));
        vbox.getChildren().add(minLable);

        TextField minField = new TextField();
        minField.setText(0 + "");
        VBox.setMargin(minField, new Insets(0, 5, 5, 5));
        vbox.getChildren().add(minField);

        Label maxLable = new Label("Max x:");
        VBox.setMargin(maxLable, new Insets(5, 5, 0, 5));
        vbox.getChildren().add(maxLable);

        TextField maxField = new TextField();
        maxField.setText(300 + "");
        VBox.setMargin(maxField, new Insets(0, 5, 5, 5));
        vbox.getChildren().add(maxField);

        Button button = new Button("Update Chart");
        button.setOnAction((ActionEvent e) -> {
            graphView.clearGraph();
            String selection = comboBox.getSelectionModel().selectedItemProperty().getValue();
            int xMin = Integer.parseInt(minField.getText());
            int xMax = Integer.parseInt(maxField.getText());
            if (selection == "All") {
                graphView.update(xMin, xMax);
            } else {
                graphView.update(Integer.parseInt(selection.substring(16)), xMin, xMax);
            }
        });
        vbox.getChildren().add(button);
        VBox.setMargin(button, new Insets(5, 5, 5, 5));

        pane.setRight(vbox);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Recursion Limit vs. Time");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
