package org.hbrs.se1.ws21.uebung4.prototype;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
 * Klasse zum Management sowie zur Eingabe unnd Ausgabe von Mitarbeitern (engl. Employees).
 * Die Anwendung wird über dies Klasse auch gestartet (main-Methode hier vorhanden)
 *
 * erstellt von Julius P., H-BRS 2021, Version 1.1
 *
 * Strategie für die Wiederverwendung (Reuse):
 * - Anlegen der Klasse Employee
 * - Anpassen des Generic in der List-Klasse (ALT: Member, NEU: Employee)
 * - Anpassen der Methodennamen
 *
 * (Was ist ihre Strategie zur Wiederverwendung?)
 * Klasse Employe implementiert Interface Member (Employee implements Member)
 * Vorteil: Wiederverwendung von Member, ID verwenden; Strenge Implementierung gegen Interface
 * Nachteil: Viele Casts notwendig, um auf die anderen Methoden zuzugreifen
 * Alternative: Container mit Generic entwickeln (z.B. Container<E>))
 * 
 */

public class Container {
	 
	// Interne ArrayList zur Abspeicherung der Objekte vom Type Employee
	private List<Employee> liste = null;
	
	// Statische Klassen-Variable, um die Referenz
	// auf das einzige Container-Objekt abzuspeichern
	// Diese Variante sei thread-safe, so hat Hr. P. es gehört... stimmt das? --> JA!
	// Nachteil: ggf. hoher Speicherbedarf, da Singleton zu Programmstart schon erzeugt
	private static Container instance = new Container();
	
	// URL der Datei, in der die Objekte gespeichert werden 
	final static String LOCATION = "allemployees1.ser";

	/**
	 * Liefert ein Singleton zurück.
	 * @return
	 */
	public static Container getInstance() {
		return instance;
	}
	
	/**
	 * Vorschriftsmäßiges Ueberschreiben des Konstruktors (private) gemaess Singleton-Pattern (oder?)
	 * Nun auf private gesetzt! Vorher ohne Access Qualifier (--> dann package-private)
	 */
	private Container(){
		liste = new ArrayList<Employee>();
	}
	
	/**
	 * Start-Methoden zum Starten des Programms 
	 * (hier koennen ggf. weitere Initialisierungsarbeiten gemacht werden spaeter)
	 */
	public static void main (String[] args) throws Exception {
		Container con = Container.getInstance();
		con.startEingabe(); 
	}
	
	/*
	 * Diese Methode realisiert eine Eingabe ueber einen Scanner
	 * Alle Exceptions werden an den aufrufenden Context (hier: main) weitergegeben (throws)
	 * Das entlastet den Entwickler zur Entwicklungszeit und den Endanwender zur Laufzeit
	 */
	public void startEingabe() throws ContainerException, Exception {
	
		String strInput = null;
		
		// Initialisierung des Eingabe-View
		Scanner scanner = new Scanner( System.in );

		// Ausgabe eines Texts zur Begruessung
		System.out.println("Employee-Tool V1.1 by Julius P. (dedicated to all my friends)");

		while ( true ) {

			System.out.print( "> "  );

			strInput = scanner.nextLine();
		
			// Extrahiert ein Array aus der Eingabe
			String[] strings = strInput.split(" ");

			// 	Falls 'help' eingegeben wurde, werden alle Befehle ausgedruckt
			if ( strings[0].equals("help") ) {
				System.out.println("Folgende Befehle stehen zur Verfuegung: help, dump....");
			}
			// Auswahl der bisher implementierten Befehle:
			if ( strings[0].equals("dump") ) {
				startAusgabe();
			}
			// Auswahl der bisher implementierten Befehle:
			if ( strings[0].equals("enter") ) {
				// Daten einlesen ...
				// this.addEmployee( new Employee( data ) ) um das Objekt in die Liste einzufügen.
			}
								
			if (  strings[0].equals("store")  ) {
				// Beispiel-Code
				Employee employee = new Employee();
				employee.setPid(2);
				this.addEmployee( employee );
				this.store();
			}


		} // Ende der Schleife
	}

	/**
	 * Diese Methode realisiert die Ausgabe.
	 */
	public void startAusgabe() {

		// Hier möchte Herr P. die Liste mit einem eigenen Sortieralgorithmus sortieren und dann
		// ausgeben. Allerdings weiss der Student hier nicht weiter

		// [Sortierung ausgelassen]
		Collections.sort( this.liste );

		// Klassische Ausgabe ueber eine For-Each-Schleife
		for (Employee employee : liste) {
			System.out.println(employee.toString());
		}

		// [Variante mit forEach-Methode / Streams (--> Kapitel 9, Lösung Übung Nr. 2)?
		//  Gerne auch mit Beachtung der neuen US1
		// (Filterung Abteilung = "ein Wert (z.B. Marketing)"
		List<String> listeMitNamen = this.liste.stream()
				.filter( employee -> employee.getAbteilung().equals("Marketing") )
				.filter (employee ->  employee.getPid() > 100 )
				.map( employee -> employee.getName() )
				.collect(Collectors.toList()); // reduce

	}

	/*
	 * Methode zum Speichern der Liste. Es wird die komplette Liste
	 * inklusive ihrer gespeicherten Employee-Objekte gespeichert.
	 * 
	 */
	private void store() throws ContainerException {
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream( Container.LOCATION );
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject( this.liste );
			System.out.println( this.size() + " Employee wurden erfolgreich gespeichert!");
		}
		catch (IOException e) {
			e.printStackTrace();
		  //  Delegation in den aufrufendem Context
		  // (Anwendung Pattern "Chain Of Responsibility)
		  throw new ContainerException("Fehler beim Abspeichern");
		}
	}

	/*
	 * Methode zum Laden der Liste. Es wird die komplette Liste
	 * inklusive ihrer gespeicherten Employee-Objekte geladen.
	 * 
	 */
	public void load() {
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		try {
		  fis = new FileInputStream( Container.LOCATION );
		  ois = new ObjectInputStream(fis);
		  
		  // Auslesen der Liste
		  Object obj = ois.readObject();
		  if (obj instanceof List<?>) {
			  this.liste = (List) obj;
		  }
		  System.out.println("Es wurden " + this.size() + " Mitarbeiter erfolgreich reingeladen!");
		}
		catch (IOException e) {
			System.out.println("LOG (für Admin): Datei konnte nicht gefunden werden!");
		}
		catch (ClassNotFoundException e) {
			System.out.println("LOG (für Admin): Liste konnte nicht extrahiert werden (ClassNotFound)!");
		}
		finally {
		  if (ois != null) try { ois.close(); } catch (IOException e) {}
		  if (fis != null) try { fis.close(); } catch (IOException e) {}
		}
	}

	/**
	 * Methode zum Hinzufügen eines Mitarbeiters unter Wahrung der Schlüsseleigenschaft
	 * @param employee
	 * @throws ContainerException
	 */
	public void addEmployee ( Employee employee ) throws ContainerException {
		if ( contains(employee) == true ) {
			ContainerException ex = new ContainerException("ID bereits vorhanden!");
			throw ex;
		}
		liste.add(employee);
	}

	/**
	 * Prüft, ob ein Employee bereits vorhanden ist
	 * @param employee
	 * @return
	 */
	private boolean contains(Employee employee) {
		int ID = employee.getPid();
		for ( Employee emp : liste) {
			if ( emp.getPid() == ID ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Ermittlung der Anzahl von internen Employee-Objekten
	 * @return
	 */
	public int size() {
		return liste.size();
	}

	/**
	 * Methode zur Rückgabe der aktuellen Liste mit Stories
	 * Findet aktuell keine Anwendung bei Hr. P.
	 * @return
	 */
	public List<Employee> getCurrentList() {
		return this.liste;
	}

	/**
	 * Liefert einen bestimmten Employee zurück
	 * @param id
	 * @return
	 */
	private Employee getEmployee(int id) {
		for ( Employee employee : liste) {
			if (id == employee.getPid() ){
				return employee;
			}
		}
		return null;
	}
}
