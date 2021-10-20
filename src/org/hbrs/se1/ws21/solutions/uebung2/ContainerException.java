package org.hbrs.se1.ws21.solutions.uebung2;

/**
 * Checked Exception (always subclasses from class Exception)
 * Checked during compilation, must be catched in the calling client class
 * In comparision: unchecked exception (subclass of RuntimeException) do not need to be catched
 */
public class ContainerException extends Exception {
	
	// Eigentlich nicht notwendig, für den Fall einer Serialisierung 
	private static final long serialVersionUID = 2656959703434537144L;
	
	private Integer id;

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		System.out.println("Das Member-Objekt mit der ID " + this.id + " ist bereits vorhanden!"); 
	} 

	public void addID(Integer id) {
		this.id = id;
	}


}
