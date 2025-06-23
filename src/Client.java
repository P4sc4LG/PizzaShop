/**
 * @author pheli
 * @version 1.0
 * @created 20-juin-2025 14:55:20
 */
public class Client {

	private String adresse;
	private String email;
	private String nom;
	private String prenom;
	private String telephone;

	public Client(){

	}

	public void finalize() throws Throwable {

	}
	public Commande appelerPourCommander(){
		return null;
	}

	/**
	 * 
	 * @param livreur
	 * @param commande
	 */
	public void payer(Livreur livreur, Commande commande){

	}

	/**
	 * 
	 * @param vendeur
	 * @param commande
	 */
	public void VerifierCommande(Vendeur vendeur, Commande commande){

	}

	/**
	 * 
	 * @param livreur
	 * @param commande
	 * @param moyenPaiement
	 */
	public void payer(Livreur livreur, Commande commande, String moyenPaiement){

	}
}