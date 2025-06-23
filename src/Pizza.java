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
}