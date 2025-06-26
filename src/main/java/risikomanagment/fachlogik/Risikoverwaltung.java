package risikomanagment.fachlogik;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import risikomanagment.datenhaltung.IDao;
import risikomanagment.datenhaltung.PersistenzException;
import risikomanagment.datenhaltung.SerDao;

public class Risikoverwaltung {

    private List<Risiko> risiken;
    private static final float LIMIT = 10000f;
    private static final float KOSTENLIMIT = 1000000f;
    private final IDao dao;

    public Risikoverwaltung() {
        dao = new SerDao();
        risiken = new ArrayList<>();
    }

    public void aufnehmen(Risiko risiko) {
        risiken.add(risiko);
    }

    public void zeigeRisiken(OutputStream out) {
        Collections.sort(risiken);
        Iterator<Risiko> it = risiken.iterator();
        while (it.hasNext()) {
            it.next().druckeDaten(out);
        }
    }

    public void ladeRisiken() throws PersistenzException {

        risiken = dao.laden();

    }

    public void speichereListe() {
        try {
            dao.speichern(risiken);
        } catch (PersistenzException e) {
        }
    }

    // public void schreibeListeInDatei() {
    // String dateiname = null;
    // int eingabe = JOptionPane.YES_OPTION;
    // boolean isOk = false;
    // while (!isOk) {
    // dateiname = JOptionPane.showInputDialog("Dateiname");
    // if (dateiname.isEmpty()) {
    // eingabe = JOptionPane.showConfirmDialog(null, "Dateiname leer neuen
    // Dateinamen wälen?", "Hinweis",
    // JOptionPane.YES_NO_OPTION);
    // if (eingabe == JOptionPane.NO_OPTION || dateiname == null) {
    // return;
    // }
    // } else {
    // isOk = true;
    // }
    // // }
    // File datei = new File(dateiname);
    // try (FileOutputStream fos = new FileOutputStream(datei);) {
    // zeigeRisiken(fos);
    // } catch (FileNotFoundException e) {
    // e.printStackTrace();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    public Risiko sucheRisikoMitMaxRueckstellung() {
        Iterator<Risiko> it = risiken.iterator();
        Risiko risikoMitMaxRueckstellung = null;
        float rueckstellung = Float.NEGATIVE_INFINITY;
        while (it.hasNext()) {
            Risiko risiko = it.next();
            if (risiko.berechneRisikowert() > rueckstellung) {
                rueckstellung = risiko.ermittleRueckstellung();
                risikoMitMaxRueckstellung = risiko;
            }
        }
        return risikoMitMaxRueckstellung;
    }

    public Iterator<Risiko> iterator() {
        return risiken.iterator();
    }

    public float berechneSummeRueckstellung() {
        if (risiken.isEmpty()) {
            return 0.0f;
        }
        Iterator<Risiko> it = risiken.iterator();
        float summe = 0.0f;
        while (it.hasNext()) {
            summe += it.next().ermittleRueckstellung();
        }
        return summe;
    }

    public enum RisikoTyp {
        AKZEPTABEL,
        INAKZEPTABEL,
        EXTREM
    }

    public static RisikoTyp bestimmeRisikoTyp(float eintrittswahrscheinlichkeit,
            float kosten_im_schadensfall) {
        float risikowert = Risiko.berechneRisikowert(eintrittswahrscheinlichkeit, kosten_im_schadensfall);
        if (risikowert < LIMIT) {
            return RisikoTyp.AKZEPTABEL;
        } else {
            if (kosten_im_schadensfall < KOSTENLIMIT) {
                return RisikoTyp.INAKZEPTABEL;
            } else {
                return RisikoTyp.EXTREM;
            }
        }
    }

    public void erzeugeNeuesRisiko() {

        String beschreibung = JOptionPane.showInputDialog("Beschreibung");
        String eintrittswahrscheinlichkeitString = JOptionPane.showInputDialog("Eintrittswahrschinlichkeit");
        float eintrittswahrscheinlichkeit = Float.parseFloat(eintrittswahrscheinlichkeitString);
        String kosten_im_schadensfallString = JOptionPane.showInputDialog("Kosten im Schadensfall: ");
        float kosten_im_schadensfall = Float.parseFloat(kosten_im_schadensfallString);
        float risikowert = Risiko.berechneRisikowert(eintrittswahrscheinlichkeit, kosten_im_schadensfall);
        if (risikowert < LIMIT) {
            aufnehmen(
                    new AkzeptablesRisiko(beschreibung, eintrittswahrscheinlichkeit, kosten_im_schadensfall));
        } else {
            String massnahme = JOptionPane.showInputDialog("Massnahme: ");
            if (kosten_im_schadensfall < KOSTENLIMIT) {
                aufnehmen(new InakzeptablesRisko(beschreibung, eintrittswahrscheinlichkeit,
                        kosten_im_schadensfall, massnahme));
            } else {
                String versicherungsbeitragString = JOptionPane.showInputDialog("Versicherungsbeitrag");
                float versicherungsbeitrag = Float.parseFloat(versicherungsbeitragString);
                aufnehmen(new ExtremesRisiko(beschreibung, eintrittswahrscheinlichkeit,
                        kosten_im_schadensfall, massnahme, versicherungsbeitrag));
            }
        }
    }
}
