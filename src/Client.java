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
	
	public Client(String nom, String prenom, String adresse, String email, String telephone) {
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.email = email;
		this.telephone = telephone;
	}

	public void finalize() throws Throwable {

	}
	
	public Commande appelerPourCommander(){
		Commande commande = new Commande();
		commande.setClient(this);
		commande.setDescription("Commande de " + this.prenom + " " + this.nom);
		return commande;
	}

	/**
	 * 
	 * @param livreur
	 * @param commande
	 */
	public void payer(Livreur livreur, Commande commande){
		// Contrainte OCL: PaiementPossible
		if (!commande.peutEtrePayee()) {
			throw new IllegalStateException(
				"Impossible de payer la commande. Statut actuel: " + commande.getStatutCommande() + 
				". La commande doit être 'EN_PREPARATION' ou 'PRETE' pour être payée."
			);
		}
		
		commande.setStatutPaiement(true);
		livreur.enregistrerPaiement(commande);
		System.out.println("Paiement effectué par " + this.prenom + " " + this.nom);
	}

	/**
	 * 
	 * @param vendeur
	 * @param commande
	 */
	public void VerifierCommande(Vendeur vendeur, Commande commande){
		System.out.println("Vérification de la commande par " + this.prenom + " " + this.nom);
		System.out.println("Statut: " + commande.getStatutCommande());
		System.out.println("Montant: " + commande.getMontant() + "€");
	}

	/**
	 * 
	 * @param livreur
	 * @param commande
	 * @param moyenPaiement
	 */
	public void payer(Livreur livreur, Commande commande, String moyenPaiement){
		// Contrainte OCL: PaiementPossible
        if(commande.getStatutCommande() != Commande.STATUT_PRETE && commande.getStatutCommande() != Commande.STATUT_LIVREE){
            if (!commande.peutEtrePayee()) {
                throw new IllegalStateException(
                    "Impossible de payer la commande. Statut actuel: " + commande.getStatutCommande() + 
                    ". La commande doit être 'PRETE' ou 'LIVREE' pour être payée."
                );
            }
        }
		
		commande.setStatutPaiement(true);
		livreur.enregistrerPaiement(commande);
		System.out.println("Paiement effectué par " + this.prenom + " " + this.nom + " via " + moyenPaiement);
	}
	
	// Getters et setters
	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}