package org.hbrs.se1.ws21.solutions.uebung2;

import java.util.ArrayList;
import java.util.List;

public class GenericContainer<E extends Member> {

    /*
     * Interne ArrayList zur Abspeicherung der Objekte
     */
    private final List<E> liste = new ArrayList<E>(); // key, value

    /*
     * Leerer Konstruktor
     */
    public GenericContainer() {
    }

    /*
     * Methode zum Hinzufuegen einer Member.
     * @throws ContainerException
     */
    public void addMember(E r) throws ContainerException {
        if (contains(r) == true) {
            ContainerException ex = new ContainerException();
            ex.addID(r.getID());
            throw ex;
        }
        liste.add(r);

    }

    /*
     * Methode zur Ueberpruefung, ob ein Member-Objekt in der Liste enthalten ist
     *
     */
    private boolean contains(E r) {
        Integer ID = r.getID();
        for (E rec : liste) {
            // wichtig: Check auf die Values innerhalb der Integer-Objekte!
            if ((rec).getID().intValue() == ID.intValue()) {
                return true;
            }
        }
        return false;

        // liste.contains(r), falls equals-Methode in Member ueberschrieben.
    }

    /*
     * Methode zum Loeschen einer Member
     *
     */
    public String deleteMember(Integer id) {
        Member rec = getMember(id);
        if (rec == null) return "Member nicht enthalten - ERROR";
        else {
            liste.remove(rec);
            return "Member mit der ID " + id + " konnte geloescht werden";
        }
    }

    /*
     * Methode zur Bestimmung der Anzahl der von Member-Objekten
     * Aufruf der Methode size() aus List
     *
     */
    public int getAnzahl() {
        return liste.size();
    }


    /*
     * Methode zur Ausgabe aller IDs der Member-Objekte
     *
     */
    public void dump() {
        System.out.println("Ausgabe aller Memberen: ");
        // Loesung mit For each:
        for (E p : liste) {
            System.out.println(p.toString());
        }

        // Loesung mit Iterator:
        // Iterator<Member> i = liste.iterator();
        //  while (  i.hasNext() ) {
        //	   Member p = i.next();
        //	   System.out.println("ID: " + p.getID() );
        //  }
    }

    /*
     * Interne Methode zur Ermittlung einer Member
     *
     */
    private Member getMember(Integer id) {
        for (E rec : liste) {
            if (id == (rec).getID().intValue()) {
                return rec;
            }
        }
        return null;
    }

}
