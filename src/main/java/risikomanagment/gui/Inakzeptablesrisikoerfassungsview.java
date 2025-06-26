package risikomanagment.gui;

import javafx.stage.Stage;
import risikomanagment.fachlogik.Risiko;

public class Inakzeptablesrisikoerfassungsview extends Stage {
    private RisikoEingabeElemente risikoEingabeElemente;
    private Risiko risiko;
    private String maßnahmeLabel;
    private String maßnahme;

    Inakzeptablesrisikoerfassungsview(Stage primaryStage, Risiko risiko) {
        this.risiko = risiko;
        maßnahmeLabel = "Maßnahmen:";
        risikoEingabeElemente = new RisikoEingabeElemente(this);
        erstelleView();
        neuSetOnAction();
        risikoEingabeElemente.initStage("Inakzeptables Risiko erfassung", this, primaryStage);
    }

    private void erstelleView() {
        risikoEingabeElemente.labelHinzufuegen("Bezeichnung:", risiko.getBezeichnung());
        risikoEingabeElemente.textfeldHinzufuegen(maßnahmeLabel);
        risikoEingabeElemente.neu_abbrechenButtonHinzufuegen();
    }

    public boolean istAbgebrochen() {
        return risikoEingabeElemente.istAbgebrochen();
    }

    private void setMaßnahme() {
        this.maßnahme = risikoEingabeElemente.getTextByLabel(maßnahmeLabel);
    }

    public String getMaßnahme() {
        return this.maßnahme;
    }

    private void neuSetOnAction() {
        risikoEingabeElemente.getNeuButton().setOnAction(e -> {
            setMaßnahme();
            close();
        });

    }
}