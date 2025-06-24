/**
 * @author pheli
 * @version 1.0
 * @created 20-juin-2025 14:55:23
 */
public class Employe {

	private String nom;
	private String prenom;

	public Employe(){

	}
	
	public Employe(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}

	public void finalize() throws Throwable {

	}
	
	// Getters et setters
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
}