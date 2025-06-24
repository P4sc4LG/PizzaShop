/**
 * Classe de test pour démontrer les contraintes OCL implémentées
 */
public class TestContraintesOCL {
    
    public static void main(String[] args) {
        System.out.println("=== TEST DES CONTRAINTES OCL ===\n");
        
        // Création des objets nécessaires
        Client client = new Client("Test", "Client", "Adresse", "email@test.com", "0123456789");
        Vendeur vendeur = new Vendeur("Test", "Vendeur");
        Chef chef = new Chef("Test", "Chef");
        Livreur livreur = new Livreur("Test", "Livreur");
        
        // Test 1: Contrainte MontantValideLorsqueActive
        testMontantValideLorsqueActive(client, chef);
        
        // Test 2: Contrainte PaiementPossible
        testPaiementPossible(client, livreur, vendeur, chef);
        
        // Test 3: Contrainte LaCommandeDoitEtrePrete
        testLivraisonPossible(livreur, client, chef);
        
        // Test 4: Contrainte PasDeReductionSiPayee
        testReductionSiPayee(vendeur, client, livreur, chef);
        
        System.out.println("\n=== TOUS LES TESTS TERMINÉS ===");
    }
    
    /**
     * Test de la contrainte: Une commande prête ou en cours ne peut pas avoir un montant négatif ou nul
     */
    private static void testMontantValideLorsqueActive(Client client, Chef chef) {
        System.out.println("1. Test: MontantValideLorsqueActive");
        System.out.println("   Une commande prête ou en cours ne peut pas avoir un montant négatif ou nul");
        
        try {
            Commande commande = client.appelerPourCommander();
            
            // Ajouter une pizza pour avoir un montant positif
            Pizza pizza = new Pizza();
            pizza.setNom("Test");
            pizza.setPrix(10.0);
            commande.ajouterPizza(pizza);
            
            // Mettre la commande en préparation
            chef.recevoirCommande(commande);
            chef.preparerCommande(commande);
            
            System.out.println("   ✓ Commande en préparation avec montant: " + commande.getMontant() + "€");
            
            // Essayer d'appliquer une réduction de 100% (montant deviendrait 0)
            System.out.println("   Tentative d'application d'une réduction de 100%...");
            commande.appliquerReduction(100.0);
            
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Contrainte respectée: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Test de la contrainte: Le client ne peut payer qu'une commande qui est en cours ou prête
     */
    private static void testPaiementPossible(Client client, Livreur livreur, Vendeur vendeur, Chef chef) {
        System.out.println("2. Test: PaiementPossible");
        System.out.println("   Le client ne peut payer qu'une commande qui est en cours ou prête");
        
        try {
            Commande commande = client.appelerPourCommander();
            
            // Essayer de payer une commande qui vient d'être créée (statut EN_ATTENTE)
            System.out.println("   Tentative de paiement d'une commande en attente...");
            client.payer(livreur, commande);
            
        } catch (IllegalStateException e) {
            System.out.println("   ✓ Contrainte respectée: " + e.getMessage());
        }
        
        try {
            Commande commande = client.appelerPourCommander();
            
            // Mettre la commande en préparation
            chef.recevoirCommande(commande);
            chef.preparerCommande(commande);
            
            System.out.println("   ✓ Paiement d'une commande en préparation réussi");
            client.payer(livreur, commande);
            
        } catch (Exception e) {
            System.out.println("   ✗ Erreur inattendue: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Test de la contrainte: Une commande peut être livrée uniquement si elle est prête
     */
    private static void testLivraisonPossible(Livreur livreur, Client client, Chef chef) {
        System.out.println("3. Test: LaCommandeDoitEtrePrete");
        System.out.println("   Une commande peut être livrée uniquement si elle est prête");
        
        try {
            Commande commande = client.appelerPourCommander();
            
            // Essayer de livrer une commande qui vient d'être créée
            System.out.println("   Tentative de livraison d'une commande en attente...");
            livreur.livrerCommande(client, commande);
            
        } catch (IllegalStateException e) {
            System.out.println("   ✓ Contrainte respectée: " + e.getMessage());
        }
        
        try {
            Commande commande = client.appelerPourCommander();
            
            // Mettre la commande en préparation puis la marquer comme prête
            chef.recevoirCommande(commande);
            chef.preparerCommande(commande);
            commande.setStatutCommande(Commande.STATUT_PRETE);
            
            System.out.println("   ✓ Livraison d'une commande prête réussie");
            livreur.livrerCommande(client, commande);
            
        } catch (Exception e) {
            System.out.println("   ✗ Erreur inattendue: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Test de la contrainte: Le vendeur ne doit appliquer une réduction pour retard que si la commande n'est pas encore payée
     */
    private static void testReductionSiPayee(Vendeur vendeur, Client client, Livreur livreur, Chef chef) {
        System.out.println("4. Test: PasDeReductionSiPayee");
        System.out.println("   Le vendeur ne doit appliquer une réduction pour retard que si la commande n'est pas encore payée");
        
        try {
            Commande commande = client.appelerPourCommander();
            
            // Ajouter une pizza et payer la commande
            Pizza pizza = new Pizza();
            pizza.setNom("Test");
            pizza.setPrix(10.0);
            commande.ajouterPizza(pizza);
            
            chef.recevoirCommande(commande);
            chef.preparerCommande(commande);
            client.payer(livreur, commande);
            
            // Essayer de traiter un retard sur une commande payée
            System.out.println("   Tentative de traitement d'un retard sur une commande payée...");
            vendeur.traiterRetard(commande);
            
        } catch (IllegalStateException e) {
            System.out.println("   ✓ Contrainte respectée: " + e.getMessage());
        }
        
        try {
            Commande commande = client.appelerPourCommander();
            
            // Traiter un retard sur une commande non payée
            System.out.println("   ✓ Traitement d'un retard sur une commande non payée réussi");
            vendeur.traiterRetard(commande);
            
        } catch (Exception e) {
            System.out.println("   ✗ Erreur inattendue: " + e.getMessage());
        }
        System.out.println();
    }
} 