package risikomanagment.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import risikomanagment.datenhaltung.PersistenzException;
import risikomanagment.fachlogik.AkzeptablesRisiko;
import risikomanagment.fachlogik.ExtremesRisiko;
import risikomanagment.fachlogik.InakzeptablesRisko;
import risikomanagment.fachlogik.Risikoverwaltung;

public class App extends Application {
    private BorderPane bp;
    private Stage stage;
    private Risikoverwaltung risikoverwaltung;
    private MenuItem neuesRisikoErstellen;

    @Override
    public void start(Stage stage) {
        this.risikoverwaltung = new Risikoverwaltung();
        this.stage = stage;
        bp = new BorderPane();
        initMenuBar();
        initStage();
        stage.show();
        // Risikoerfassungsview risikoerfassungsview = new Risikoerfassungsview(stage,
        // null);
        // risikoerfassungsview.show();

        // Extremesrisikoerfassungsview extremesrisikoerfassungsview = new
        // Extremesrisikoerfassungsview(stage, null);
        // extremesrisikoerfassungsview.show();

        // Inakzeptablesrisikoerfassungsview inakzeptablesrisikoerfassungsview = new
        // Inakzeptablesrisikoerfassungsview(
        // stage, null);
        // inakzeptablesrisikoerfassungsview.show();
    }

    private void initStage() {
        Scene szene = new Scene(bp, 600, 500);
        stage.setTitle("Risikomangement");
        stage.setScene(szene);
    }

    private void initMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu datei = new Menu("Datei");
        Menu risiko = new Menu("Risiko");
        Menu anzeige = new Menu("Anzeige");
        MenuItem laden = new MenuItem("Laden");
        MenuItem speichern = new MenuItem("Speichern");
        MenuItem risikoListeInDatei = new MenuItem("Risikoliste in Datei");
        MenuItem beenden = new MenuItem("Beenden");
        neuesRisikoErstellen = new MenuItem("Neues Risiko");
        MenuItem risikoMitMaximalerRückstellung = new MenuItem("Risiko mit maximaler Rückstellung");
        MenuItem summeAllerRückstellungen = new MenuItem("Summe aller Rückstellungen");
        datei.getItems().addAll(laden, speichern, new SeparatorMenuItem(), risikoListeInDatei, new SeparatorMenuItem(),
                beenden);
        risiko.getItems().add(neuesRisikoErstellen);
        anzeige.getItems().addAll(risikoMitMaximalerRückstellung, summeAllerRückstellungen);
        menuBar.getMenus().addAll(datei, risiko, anzeige);
        bp.setTop(menuBar);

        neuesRisikoErstellen.setOnAction(e -> {
            erzeugeRisiko();
        });

        laden.setOnAction(e->{
            try {
                risikoverwaltung.ladeRisiken();
            } catch (PersistenzException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            risikoverwaltung.zeigeRisiken(System.out);
        });

        speichern.setOnAction(e->{
            risikoverwaltung.speichereListe();
        });

    }

    private void erzeugeRisiko() {
        Risikoerfassungsview risikoerfassungsview = new Risikoerfassungsview(stage);
        Inakzeptablesrisikoerfassungsview inakzeptablesrisikoerfassungsview = null;
        Extremesrisikoerfassungsview extremesrisikoerfassungsview = null;
        risikoerfassungsview.showAndWait();
        if (!risikoerfassungsview.istAbgebrochen()) {
            switch (Risikoverwaltung.bestimmeRisikoTyp(risikoerfassungsview.getEintrittswahrscheinlichkeit(),
                    risikoerfassungsview.getKostenImSchadensfall())) {
                case AKZEPTABEL:
                    AkzeptablesRisiko neuesAkzeptableRisiko = new AkzeptablesRisiko(
                            risikoerfassungsview.getBezeichnung(),
                            risikoerfassungsview.getEintrittswahrscheinlichkeit(),
                            risikoerfassungsview.getKostenImSchadensfall());
                    risikoverwaltung.aufnehmen(neuesAkzeptableRisiko);
                    break;
                case INAKZEPTABEL:
                    InakzeptablesRisko neuesInakzeptableRisiko = new InakzeptablesRisko(
                            risikoerfassungsview.getBezeichnung(),
                            risikoerfassungsview.getEintrittswahrscheinlichkeit(),
                            risikoerfassungsview.getKostenImSchadensfall(), null);
                    inakzeptablesrisikoerfassungsview = new Inakzeptablesrisikoerfassungsview(stage,
                            neuesInakzeptableRisiko);
                    inakzeptablesrisikoerfassungsview.showAndWait();
                    if (!inakzeptablesrisikoerfassungsview.istAbgebrochen()) {
                        neuesInakzeptableRisiko.setMaßnahme(inakzeptablesrisikoerfassungsview.getMaßnahme());
                        risikoverwaltung.aufnehmen(neuesInakzeptableRisiko);
                    }
                    break;
                case EXTREM:
                    ExtremesRisiko neuesExtremesRisiko = new ExtremesRisiko(risikoerfassungsview.getBezeichnung(),
                            risikoerfassungsview.getEintrittswahrscheinlichkeit(),
                            risikoerfassungsview.getKostenImSchadensfall(), null, 0.0f);
                    extremesrisikoerfassungsview = new Extremesrisikoerfassungsview(stage, neuesExtremesRisiko);
                    extremesrisikoerfassungsview.showAndWait();
                    if (!extremesrisikoerfassungsview.istAbgebrochen()) {
                        neuesExtremesRisiko.setMaßnahme(extremesrisikoerfassungsview.getMaßnahme());
                        neuesExtremesRisiko
                                .setVersicherungsbeitrag(extremesrisikoerfassungsview.getVersicherungsbeitrag());
                        risikoverwaltung.aufnehmen(neuesExtremesRisiko);
                    }
                    break;
                default:
                    break;
            }
        }

        risikoverwaltung.zeigeRisiken(System.out);
    }

}
