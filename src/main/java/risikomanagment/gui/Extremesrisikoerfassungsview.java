package risikomanagment.gui;

import javafx.stage.Stage;
import risikomanagment.fachlogik.Risiko;

public class Extremesrisikoerfassungsview extends Stage {
    private RisikoEingabeElemente risikoEingabeElemente;
    private String maßnahmenLabel;
    private String versicherungsbeitragLabel;
    private float versicherungsbeitrag;
    private String maßnahme;

    Extremesrisikoerfassungsview(Stage primaryStage, Risiko risiko) {
        maßnahmenLabel = "Maßnahmen:";
        versicherungsbeitragLabel = "Versicherungsbeitrag:";
        risikoEingabeElemente = new RisikoEingabeElemente(this);
        erstelleView();
        neuSetOnAction();
        risikoEingabeElemente.initStage("Extremes Risiko erfassung", this, primaryStage);
    }

    private void erstelleView() {
        risikoEingabeElemente.labelHinzufuegen("Bezeichnung:", "hallo hallo");
        risikoEingabeElemente.textfeldHinzufuegen(maßnahmenLabel);
        risikoEingabeElemente.textfeldHinzufuegen(versicherungsbeitragLabel);
        risikoEingabeElemente.neu_abbrechenButtonHinzufuegen();
    }

    public boolean istAbgebrochen() {
        return risikoEingabeElemente.istAbgebrochen();
    }

    private void setVersicherungsbeitrag() {
        this.versicherungsbeitrag = Float.parseFloat(risikoEingabeElemente.getTextByLabel(versicherungsbeitragLabel));
    }

    private void setMaßnahme() {
        this.maßnahme = risikoEingabeElemente.getTextByLabel(maßnahme);
    }

    public String getMaßnahme() {
        return this.maßnahme;
    }

    public float getVersicherungsbeitrag() {
        return this.versicherungsbeitrag;
    }

    private void neuSetOnAction() {
        risikoEingabeElemente.getNeuButton().setOnAction(e -> {
            setVersicherungsbeitrag();
            setMaßnahme();
            close();
        });

    }
}