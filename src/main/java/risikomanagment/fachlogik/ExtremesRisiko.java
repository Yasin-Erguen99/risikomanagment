package risikomanagment.fachlogik;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Objects;

public class ExtremesRisiko extends InakzeptablesRisko {
    static final long serialVersionUID = 1L;
    private float versicherungsbeitrag;

    public ExtremesRisiko(String bezeichnung, float eintrittswahrscheinlichkeit, float kosten_im_schadensfall,
            String massnahme, float versicherungsbeitrag) {
        super(bezeichnung, eintrittswahrscheinlichkeit, kosten_im_schadensfall, massnahme);
        this.versicherungsbeitrag = versicherungsbeitrag;

    }

    public void setVersicherungsbeitrag(float versicherungsbeitrag) {
        this.versicherungsbeitrag = versicherungsbeitrag;
    }

    @Override
    public float ermittleRueckstellung() {
        return versicherungsbeitrag;
    }

    @Override
    public void druckeDaten(OutputStream out) {
        PrintWriter pw = new PrintWriter(out, true);
        pw.printf(toString());
    }

    @Override
    public String toString() {
        String daten = String.format(
                "Id %d Extremes Risiko \"%s\" aus %tm/%tY; Versicherungsbeitrag %.2f;\nMaßnahme \"%s\"\n\n",
                getId(), getBezeichnung(), getErstellungsdatum(), getErstellungsdatum(), ermittleRueckstellung(),
                getMassnahme());
        return daten;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), versicherungsbeitrag);
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
        ExtremesRisiko risiko = (ExtremesRisiko) other;
        if (super.equals(other) && this.versicherungsbeitrag == risiko.versicherungsbeitrag)
            return true;
        return false;
    }
}