/**
 * @author pheli
 * @version 1.0
 * @created 20-juin-2025 14:55:30
 */
public class Vendeur extends Employe {

	private Commande m_Commande;

	public Vendeur(){

	}
	
	public Vendeur(String nom, String prenom) {
		super(nom, prenom);
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
		commande.setChef(chef);
		chef.recevoirCommande(commande);
		System.out.println("Le vendeur " + this.getPrenom() + " alerte le chef pour la commande");
	}

	/**
	 * 
	 * @param livreur
	 * @param commande
	 */
	public void alerterLivreur(Livreur livreur, Commande commande){
		commande.setLivreur(livreur);
		livreur.recevoirAlerte(commande);
		System.out.println("Le vendeur " + this.getPrenom() + " alerte le livreur pour la livraison");
	}

	/**
	 * 
	 * @param commande
	 */
	public void enregistrerCommande(Commande commande){
		this.m_Commande = commande;
		commande.setStatutCommande(Commande.STATUT_ENREGISTREE);
		System.out.println("Commande enregistrée par le vendeur " + this.getPrenom());
	}

	/**
	 * 
	 * @param commande
	 */
	public void recevoirCommande(Commande commande){
		this.m_Commande = commande;
		commande.setStatutCommande(Commande.STATUT_RECUE);
		System.out.println("Commande reçue par le vendeur " + this.getPrenom());
	}

	/**
	 * 
	 * @param Commande
	 */
	public void traiterRetard(Commande Commande){
		// Contrainte OCL: PasDeReductionSiPayee
		if (Commande.isStatutPaiement()) {
			throw new IllegalStateException(
				"Impossible de traiter le retard. La commande est déjà payée (payee = " + 
				Commande.isStatutPaiement() + "). Une réduction ne peut pas être appliquée sur une commande déjà réglée."
			);
		}
		
		Commande.setStatutCommande(Commande.STATUT_EN_RETARD);
		System.out.println("Retard traité par le vendeur " + this.getPrenom());
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