/**
 * @author pheli
 * @version 1.0
 * @created 20-juin-2025 14:55:30
 */
public class Vendeur extends Employe {

	private Commande m_Commande;

	public Vendeur(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	/**
	 * 
	 * @param chef
	 * @param commande
	 */
	public void alerterChef(Chef chef, Commande commande){

	}

	/**
	 * 
	 * @param livreur
	 * @param commande
	 */
	public void alerterLivreur(Livreur livreur, Commande commande){

	}

	/**
	 * 
	 * @param commande
	 */
	public void enregistrerCommande(Commande commande){

	}

	/**
	 * 
	 * @param commande
	 */
	public void recevoirCommande(Commande commande){

	}

	/**
	 * 
	 * @param Commande
	 */
	public void traiterRetard(Commande Commande){

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