package risikomanagment.datenhaltung;

import java.util.List;

import risikomanagment.fachlogik.Risiko;

public interface IDao {
void speichern(List<Risiko> liste) throws PersistenzException;
List<Risiko> laden() throws PersistenzException;
}
