/**
 * @author pheli
 * @version 1.0
 * @created 20-juin-2025 14:55:28
 */
public class Pizza {

	private String ingredient;
	private String nom;
	private double Prix;
	private Commande m_Commande;

	public Pizza(){

	}

	public void finalize() throws Throwable {

	}

	public Commande getCommande(){
		return m_Commande;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setCommande(Commande newVal){
		m_Commande = newVal;
	}
	
	// Getters et setters pour les attributs
	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getPrix() {
		return Prix;
	}

	public void setPrix(double prix) {
		Prix = prix;
	}
}