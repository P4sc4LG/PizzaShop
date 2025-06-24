/**
 * @author pheli
 * @version 1.0
 * @created 20-juin-2025 14:55:17
 */
public class Chef extends Employe {

	public Chef(){

	}
	
	public Chef(String nom, String prenom) {
		super(nom, prenom);
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	/**
	 * 
	 * @param Commande
	 */
	public void preparerCommande(Commande Commande){
		Commande.setStatutCommande(Commande.STATUT_EN_PREPARATION);
		System.out.println("Le chef " + this.getPrenom() + " prépare la commande");
	}

	/**
	 * 
	 * @param Commande
	 */
	public void recevoirCommande(Commande Commande){
		Commande.setStatutCommande(Commande.STATUT_RECUE_PAR_CHEF);
		System.out.println("Le chef " + this.getPrenom() + " reçoit la commande");
	}
}