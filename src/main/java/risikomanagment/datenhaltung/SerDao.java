package risikomanagment.datenhaltung;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import risikomanagment.fachlogik.Risiko;

public class SerDao implements IDao {

    @Override
    public void speichern(List<Risiko> liste) throws PersistenzException {
        File datei = new File("Risiken");
        try (FileOutputStream fos = new FileOutputStream(datei); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(liste);

        } catch (FileNotFoundException e) {
            throw new PersistenzException();
        } catch (IOException e) {
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Risiko> laden() throws PersistenzException {
        List<Risiko> liste = null;
        try (FileInputStream fis = new FileInputStream(new File("Risiken")); 
        ObjectInputStream ois = new ObjectInputStream(fis)) {
            liste = (List<Risiko>) ois.readObject();
            Risiko.setAnzahlRisiken(liste.size());
        } catch (FileNotFoundException e) {
            throw new PersistenzException();
        } catch (IOException | ClassNotFoundException e) {

        }
        return liste;
    }

}
