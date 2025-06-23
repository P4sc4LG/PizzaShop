import java.time.LocalDateTime;

/**
 * @author pheli
 * @version 1.0
 * @created 23-juin-2025 16:14:05
 */
public class Commande {

	private String description;
	private LocalDateTime LocalDateTime;
	private double montant;
	private String statutCommande;
	private boolean statutPaiement;
	private Chef m_Chef;
	private Client m_Client;
	private Livreur m_Livreur;

	public Commande(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param pourcentage
	 */
	public void appliquerReduction(double pourcentage){

	}

	public Chef getChef(){
		return m_Chef;
	}

	public Client getClient(){
		return m_Client;
	}

	public Livreur getLivreur(){
		return m_Livreur;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setChef(Chef newVal){
		m_Chef = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setClient(Client newVal){
		m_Client = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setLivreur(Livreur newVal){
		m_Livreur = newVal;
	}
}