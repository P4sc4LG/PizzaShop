import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pheli
 * @version 1.0
 * @created 23-juin-2025 16:14:05
 */
public class Commande {

	// Constantes pour les statuts de commande
	public static final String STATUT_EN_ATTENTE = "EN_ATTENTE";
	public static final String STATUT_RECUE = "RECUE";
	public static final String STATUT_ENREGISTREE = "ENREGISTREE";
	public static final String STATUT_RECUE_PAR_CHEF = "RECUE_PAR_CHEF";
	public static final String STATUT_EN_PREPARATION = "EN_PREPARATION";
	public static final String STATUT_PRETE = "PRETE";
	public static final String STATUT_EN_LIVRAISON = "EN_LIVRAISON";
	public static final String STATUT_LIVREE = "LIVREE";
	public static final String STATUT_EN_RETARD = "EN_RETARD";

	private String description;
	private LocalDateTime dateCommande;
	private double montant;
	private String statutCommande;
	private boolean statutPaiement;
	private Chef m_Chef;
	private Client m_Client;
	private Livreur m_Livreur;
	private List<Pizza> pizzas;

	public Commande(){
		this.dateCommande = LocalDateTime.now();
		this.statutCommande = STATUT_EN_ATTENTE;
		this.statutPaiement = false;
		this.pizzas = new ArrayList<>();
		this.montant = 0.0;
	}

	public void finalize() throws Throwable {

	}
	
	/**
	 * Applique une réduction sur le montant de la commande
	 * Contrainte OCL: Le montant doit rester positif pour les commandes actives
	 * @param pourcentage
	 * @throws IllegalArgumentException si la réduction rend le montant négatif ou nul
	 */
	public void appliquerReduction(double pourcentage){
		if (pourcentage > 0 && pourcentage <= 100) {
			double nouveauMontant = this.montant * (1 - pourcentage / 100);
			
			// Contrainte OCL: MontantValideLorsqueActive
			if (isCommandeActive() && nouveauMontant <= 0) {
				throw new IllegalArgumentException(
					"Impossible d'appliquer une réduction de " + pourcentage + 
					"% car le montant deviendrait " + nouveauMontant + 
					". Le montant doit rester positif pour les commandes actives."
				);
			}
			
			this.montant = nouveauMontant;
		}
	}
	
	/**
	 * Vérifie si la commande est active (prête ou en cours)
	 * @return true si la commande est active
	 */
	public boolean isCommandeActive() {
		return STATUT_PRETE.equals(statutCommande) || 
			   STATUT_EN_PREPARATION.equals(statutCommande) ||
			   STATUT_EN_LIVRAISON.equals(statutCommande);
	}
	
	/**
	 * Vérifie si la commande peut être livrée (doit être prête)
	 * @return true si la commande peut être livrée
	 */
	public boolean peutEtreLivree() {
		return STATUT_PRETE.equals(statutCommande);
	}
	
	/**
	 * Vérifie si la commande peut être payée (doit être en cours ou prête)
	 * @return true si la commande peut être payée
	 */
	public boolean peutEtrePayee() {
		return STATUT_EN_PREPARATION.equals(statutCommande) || 
			   STATUT_PRETE.equals(statutCommande);
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
	
	// Getters et setters pour les nouveaux attributs
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(LocalDateTime dateCommande) {
		this.dateCommande = dateCommande;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		// Contrainte OCL: MontantValideLorsqueActive
		if (isCommandeActive() && montant <= 0) {
			throw new IllegalArgumentException(
				"Le montant ne peut pas être négatif ou nul (" + montant + 
				") pour une commande active (statut: " + statutCommande + ")"
			);
		}
		this.montant = montant;
	}

	public String getStatutCommande() {
		return statutCommande;
	}

	public void setStatutCommande(String statutCommande) {
		this.statutCommande = statutCommande;
	}

	public boolean isStatutPaiement() {
		return statutPaiement;
	}

	public void setStatutPaiement(boolean statutPaiement) {
		this.statutPaiement = statutPaiement;
	}
	
	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	
	public void ajouterPizza(Pizza pizza) {
		this.pizzas.add(pizza);
		this.montant += pizza.getPrix();
	}
	
	public void calculerMontant() {
		this.montant = 0.0;
		for (Pizza pizza : pizzas) {
			this.montant += pizza.getPrix();
		}
	}
}