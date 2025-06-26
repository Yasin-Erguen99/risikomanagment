package risikomanagment.gui;

import java.util.Map;
import java.util.HashMap;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RisikoEingabeElemente extends BorderPane {
    private Map<String, TextField> map = new HashMap<String, TextField>();
    private GridPane gridPane;
    private Stage stage;
    private boolean abgebrochen;
    private Button neu;

    RisikoEingabeElemente(Stage stage) {
        this.stage = stage;
        initGridPane();
    }

    private void initGridPane() {
        this.gridPane = new GridPane();
        gridPane.setGridLinesVisible(false);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(3);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10));
        this.setCenter(gridPane);
    }

    public void textfeldHinzufuegen(String labelName) {
        Label label = new Label(labelName);
        TextField textField = new TextField();
        GridPane.setHalignment(label, HPos.RIGHT);
        GridPane.setHgrow(textField, Priority.ALWAYS);
        gridPane.add(textField, 1, gridPane.getRowCount());
        gridPane.add(label, 0, gridPane.getRowCount() - 1);
        map.put(labelName, textField);
    }

    public String getTextByLabel(String labelName) {
        return map.get(labelName).getText();
    }
    public Button getNeuButton(){
        return neu;
    }
    public void neu_abbrechenButtonHinzufuegen() {
        HBox buttons = new HBox();
        neu = new Button("Neu");
        Button abbrechen = new Button("Abbrechen");
        abbrechen.setOnAction(e -> {
            abgebrochen = true;
            stage.close();
        });
        GridPane.setHalignment(buttons, HPos.CENTER);
        buttons.setSpacing(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(neu, abbrechen);
        gridPane.add(buttons, 0, gridPane.getRowCount(), gridPane.getColumnCount(), 1);
    }

    public void labelHinzufuegen(String labelName, String labelText) {
        Label label = new Label(labelName);
        Label text = new Label(labelText);
        GridPane.setHalignment(label, HPos.RIGHT);
        GridPane.setHgrow(text, Priority.ALWAYS);
        gridPane.add(text, 1, gridPane.getRowCount());
        gridPane.add(label, 0, gridPane.getRowCount() - 1);
    }

    public void initStage(String titel, Stage stage, Stage parent) {
        Scene scene = new Scene(this, 320, 150);
        stage.setTitle(titel);
        stage.setScene(scene);
        stage.initOwner(parent);
        stage.initModality(Modality.WINDOW_MODAL);
    }

    public boolean istAbgebrochen() {
        return abgebrochen;
    }

}
