package risikomanagment.fachlogik;

import java.io.OutputStream;
import java.io.PrintWriter;

public class AkzeptablesRisiko extends Risiko {

    public AkzeptablesRisiko(String bezeichnung, float eintrittswahrscheinlichkeit, float kosten_im_schadensfall) {
        super(bezeichnung, eintrittswahrscheinlichkeit, kosten_im_schadensfall);
    }

    @Override
    public float ermittleRueckstellung() {
        return 0;
    }

    @Override
    public void druckeDaten(OutputStream out) {
        PrintWriter pw = new PrintWriter(out, true);
        pw.printf(toString());
    }

    @Override
    public String toString() {
        String daten = String.format(
                "Id %d Akzeptables Risiko \"%s\" aus %02d\\%d; Risikowert %.2f; Rueckstellung %.2f\n\n", getId(),
                getBezeichnung(), getErstellungsdatum().getMonthValue(), getErstellungsdatum().getYear(), berechneRisikowert(),
                ermittleRueckstellung());
        return daten;
    }
}
