package risikomanagment.fachlogik;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Objects;

public class InakzeptablesRisko extends Risiko {
    static final long serialVersionUID = 1L;
    private String massnahme;

    public InakzeptablesRisko(String bezeichnung, float eintrittswahrscheinlichkeit, float kosten_im_schadensfall,
            String massnahme) {
        super(bezeichnung, eintrittswahrscheinlichkeit, kosten_im_schadensfall);
        this.massnahme = massnahme;
    }

    public String getMassnahme() {
        return massnahme;
    }

    public void setMaßnahme(String massnahme) {
        this.massnahme = massnahme;
    }

    @Override
    public float ermittleRueckstellung() {
        return berechneRisikowert();
    }

    @Override
    public void druckeDaten(OutputStream out) {
        PrintWriter pw = new PrintWriter(out, true);
        pw.printf(toString());
    }

    @Override
    public String toString() {
        String daten = String.format(
                "Id %d Inakzeptables Risiko \"%s\" aus %tm/%tY; Risikowert %.2f;\nRueckstellung %.2f; Maßnahme \"%s\"\n\n",
                getId(), getBezeichnung(), getErstellungsdatum(), getErstellungsdatum(), berechneRisikowert(),
                ermittleRueckstellung(), getMassnahme());
        return daten;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.massnahme);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null)
            return false;
        if (this.getClass() != other.getClass())
            return false;
        InakzeptablesRisko risiko = (InakzeptablesRisko) other;
        if (super.equals(other) && this.getMassnahme().equals(risiko.getMassnahme()))
            return true;
        return false;
    }
}
