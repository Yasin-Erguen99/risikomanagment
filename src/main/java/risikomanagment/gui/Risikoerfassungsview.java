package risikomanagment.gui;

import javafx.stage.Stage;

public class Risikoerfassungsview extends Stage {
    private RisikoEingabeElemente risikoEingabeElemente;
    private String bezeichnungLabel;
    private String eintrittswahrscheinlichkeitLabel;
    private String kosten_im_schadensfallLabel;
    private String bezeichnung;
    private float eintrittswahrscheinlichkeit;
    private float kosten_im_schadensfall;

    Risikoerfassungsview(Stage primaryStage) {
        bezeichnungLabel = "Bezeichnung:";
        eintrittswahrscheinlichkeitLabel = "Eintrittswahrscheinlichkeit:";
        kosten_im_schadensfallLabel = "Kosten im Schadensfall:";
        risikoEingabeElemente = new RisikoEingabeElemente(this);
        erstelleView();
        risikoEingabeElemente.initStage("Risiko erfassung", this, primaryStage);
    }

    private void erstelleView() {
        risikoEingabeElemente.textfeldHinzufuegen(bezeichnungLabel);
        risikoEingabeElemente.textfeldHinzufuegen(eintrittswahrscheinlichkeitLabel);
        risikoEingabeElemente.textfeldHinzufuegen(kosten_im_schadensfallLabel);
        risikoEingabeElemente.neu_abbrechenButtonHinzufuegen();
        neuSetOnAction();
    }

    private void setBezeichnung() {
        this.bezeichnung = risikoEingabeElemente.getTextByLabel(this.bezeichnungLabel);
    }

    private void setEintrittswahrscheinlichkeit() {
        try {
            this.eintrittswahrscheinlichkeit = Float
                    .parseFloat(risikoEingabeElemente.getTextByLabel(eintrittswahrscheinlichkeitLabel));
        } catch (NumberFormatException e) {
        }
    }

    private void setKostenImSchadensfall() {
        try {
            this.kosten_im_schadensfall = Float
                    .parseFloat(risikoEingabeElemente.getTextByLabel(kosten_im_schadensfallLabel));
        } catch (NumberFormatException e) {
        }

    }

    public String getBezeichnung() {
        return this.bezeichnung;
    }

    public float getKostenImSchadensfall() {
        return this.kosten_im_schadensfall;
    }

    public float getEintrittswahrscheinlichkeit() {
        return this.eintrittswahrscheinlichkeit;
    }

    private void neuSetOnAction() {
        risikoEingabeElemente.getNeuButton().setOnAction(e -> {
            setBezeichnung();
            setKostenImSchadensfall();
            setEintrittswahrscheinlichkeit();
            close();
        });

    }

    public boolean istAbgebrochen() {
        return risikoEingabeElemente.istAbgebrochen();
    }
}