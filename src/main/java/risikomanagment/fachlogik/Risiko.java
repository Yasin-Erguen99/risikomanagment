package risikomanagment.fachlogik;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public abstract class Risiko implements Comparable<Risiko>,Serializable {
    static final long serialVersionUID = 1L;
    private final int id;
    private static int anzahlRisiken = 0;
    private String bezeichnung;
    private float eintrittswahrscheinlichkeit;
    private float kosten_im_schadensfall;
    private LocalDate erstellungsdatum;

    Risiko(String bezeichnung, float eintrittswahrscheinlichkeit, float kosten_im_schadensfall) {
        this.id = anzahlRisiken++;
        this.bezeichnung = bezeichnung;
        this.eintrittswahrscheinlichkeit = eintrittswahrscheinlichkeit;
        this.kosten_im_schadensfall = kosten_im_schadensfall;
        this.erstellungsdatum = LocalDate.now();
    }
    public static void setAnzahlRisiken(int i){
        anzahlRisiken = i;
    }

    public int getId() {
        return this.id;
    }

    public String getBezeichnung() {
        return this.bezeichnung;
    }

    public void setBezeichnung(String bezeichnung){
        this.bezeichnung = bezeichnung;
    }

    public float getEintrittswahrscheinlichkeit() {
        return this.eintrittswahrscheinlichkeit;
    }

    public void setEintrittswahrscheinlichkeit(float eintrittswahrscheinlichkeit){
        this.eintrittswahrscheinlichkeit = eintrittswahrscheinlichkeit;
    }

    public float getKosten_im_schadensfall() {
        return this.kosten_im_schadensfall;
    }

    public void setKosten_im_schadensfall(float kosten_im_schadensfall){
        this.kosten_im_schadensfall = kosten_im_schadensfall;
    }

    public LocalDate getErstellungsdatum() {
        return erstellungsdatum;
    }

    public float berechneRisikowert() {
        return Risiko.berechneRisikowert(this.eintrittswahrscheinlichkeit, this.kosten_im_schadensfall);
    }

    public static float berechneRisikowert(float eintrittswahrscheinlichkeit , float kosten_im_schadensfall){
        return  eintrittswahrscheinlichkeit*kosten_im_schadensfall;
    }

    public abstract float ermittleRueckstellung();

    public abstract void druckeDaten(OutputStream out);

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung, eintrittswahrscheinlichkeit, kosten_im_schadensfall);
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
        Risiko risiko = (Risiko) other;
        if (this.getBezeichnung().equals(risiko.getBezeichnung())
                && this.getEintrittswahrscheinlichkeit() == risiko.getEintrittswahrscheinlichkeit()
                && this.getKosten_im_schadensfall() == risiko.getKosten_im_schadensfall())
            return true;
        return false;
    }

    @Override
    public int compareTo(Risiko o) {
        if (this.berechneRisikowert() > o.berechneRisikowert())
            return 1;
        if (this.berechneRisikowert() < o.berechneRisikowert())
            return -1;
        return 0;
    }
}