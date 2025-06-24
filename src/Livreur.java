/**
 * @author pheli
 * @version 1.0
 * @created 20-juin-2025 14:55:25
 */
public class Livreur extends Employe {

	public Livreur(){

	}
	
	public Livreur(String nom, String prenom) {
		super(nom, prenom);
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	/**
	 * 
	 * @param commande
	 */
	public void enregistrerPaiement(Commande commande){
		commande.setStatutPaiement(true);
		System.out.println("Paiement enregistré par le livreur " + this.getPrenom());
	}

	/**
	 * 
	 * @param client
	 * @param commande
	 */
	public void livrerCommande(Client client, Commande commande){
		// Contrainte OCL: LaCommandeDoitEtrePrete
        if(commande.getStatutCommande() != Commande.STATUT_EN_LIVRAISON){
            if (!commande.peutEtreLivree()) {
                throw new IllegalStateException(
                    "Impossible de livrer la commande. Statut actuel: " + commande.getStatutCommande() + 
                    ". La commande doit être 'PRETE' pour être livrée."
                );
            }
        }
		
		commande.setStatutCommande(Commande.STATUT_LIVREE);
		System.out.println("Commande livrée par " + this.getPrenom() + " à " + client.getPrenom() + " " + client.getNom());
	}

	/**
	 * 
	 * @param commande
	 */
	public void recevoirAlerte(Commande commande){
		commande.setStatutCommande(Commande.STATUT_EN_LIVRAISON);
		System.out.println("Alerte reçue par le livreur " + this.getPrenom() + " pour la livraison");
	}
}