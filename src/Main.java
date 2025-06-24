import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== PIZZA SHOP - Système de Commande ===\n");
        
        // Création des employés
        Vendeur vendeur = new Vendeur("Dupont", "Marie");
        Chef chef = new Chef("Martin", "Pierre");
        Livreur livreur = new Livreur("Bernard", "Jean");
        
        // Création d'un client
        Client client = new Client("Durand", "Sophie", "123 Rue de la Paix, Paris", "sophie.durand@email.com", "0123456789");
        
        // Simulation du processus de commande
        System.out.println("1. Le client " + client.getPrenom() + " " + client.getNom() + " appelle pour commander");
        Commande commande = client.appelerPourCommander();
        
        // Le vendeur reçoit la commande
        System.out.println("\n2. Le vendeur reçoit la commande");
        vendeur.recevoirCommande(commande);
        
        // Création des pizzas
        System.out.println("\n3. Ajout des pizzas à la commande");
        Pizza margherita = new Pizza();
        margherita.setNom("Margherita");
        margherita.setIngredient("Tomate, mozzarella, basilic");
        margherita.setPrix(12.50);
        
        Pizza pepperoni = new Pizza();
        pepperoni.setNom("Pepperoni");
        pepperoni.setIngredient("Tomate, mozzarella, pepperoni");
        pepperoni.setPrix(14.00);
        
        // Ajout des pizzas à la commande
        commande.ajouterPizza(margherita);
        commande.ajouterPizza(pepperoni);
        
        // Enregistrement de la commande
        System.out.println("\n4. Enregistrement de la commande");
        vendeur.enregistrerCommande(commande);
        
        // Affichage du détail de la commande
        System.out.println("\n=== Détail de la commande ===");
        System.out.println("Client: " + commande.getClient().getPrenom() + " " + commande.getClient().getNom());
        System.out.println("Date: " + commande.getDateCommande());
        System.out.println("Statut: " + commande.getStatutCommande());
        System.out.println("Pizzas commandées:");
        for (Pizza pizza : commande.getPizzas()) {
            System.out.println("  - " + pizza.getNom() + " (" + pizza.getIngredient() + ") : " + pizza.getPrix() + "€");
        }
        System.out.println("Montant total: " + commande.getMontant() + "€");
        
        // Le vendeur alerte le chef
        System.out.println("\n5. Le vendeur alerte le chef");
        vendeur.alerterChef(chef, commande);
        
        // Le chef prépare la commande
        System.out.println("\n6. Le chef prépare la commande");
        chef.preparerCommande(commande);
        
        // Le vendeur alerte le livreur
        System.out.println("\n7. Le vendeur alerte le livreur");
        vendeur.alerterLivreur(livreur, commande);
        
        // Le client vérifie sa commande
        System.out.println("\n8. Le client vérifie sa commande");
        client.VerifierCommande(vendeur, commande);
        
        // Le livreur livre la commande - contrainte le livreur ne peut livrer que si la commande est PRETE
        System.out.println("\n9. Le livreur livre la commande");
        livreur.livrerCommande(client, commande);
        
        // Le client paie - contrainte paiment possible seulement si la commande est PRETE ou LIVREE
        System.out.println("\n10. Le client paie");
        client.payer(livreur, commande, "Carte bancaire");
        
        System.out.println("\n=== Commande terminée avec succès ! ===");
        
        // Test des violations de contraintes OCL
        
        
        //System.out.println("\n=== TEST DES VIOLATIONS DE CONTRAINTES OCL ===");
        //testViolationsContraintes(client, vendeur, chef, livreur);
        
    }
    
    /**
     * Méthode qui teste les violations des contraintes OCL
     */
    private static void testViolationsContraintes(Client client, Vendeur vendeur, Chef chef, Livreur livreur) {
        System.out.println("\n--- Test 1: Violation de la contrainte MontantValideLorsqueActive ---");
        try {
            Commande commandeTest = client.appelerPourCommander();
            Pizza pizzaTest = new Pizza();
            pizzaTest.setNom("Test");
            pizzaTest.setPrix(10.0);
            commandeTest.ajouterPizza(pizzaTest);
            
            // Mettre la commande en préparation (active)
            chef.recevoirCommande(commandeTest);
            chef.preparerCommande(commandeTest);
            
            System.out.println("Tentative d'application d'une réduction de 100% sur une commande active...");
            commandeTest.appliquerReduction(100.0); // Devrait échouer
            
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Contrainte respectée: " + e.getMessage());
        }
        
        System.out.println("\n--- Test 2: Violation de la contrainte PaiementPossible ---");
        try {
            Commande commandeTest = client.appelerPourCommander();
            System.out.println("Tentative de paiement d'une commande en attente...");
            client.payer(livreur, commandeTest); // Devrait échouer
            
        } catch (IllegalStateException e) {
            System.out.println("✓ Contrainte respectée: " + e.getMessage());
        }
        
        System.out.println("\n--- Test 3: Violation de la contrainte LaCommandeDoitEtrePrete ---");
        try {
            Commande commandeTest = client.appelerPourCommander();
            System.out.println("Tentative de livraison d'une commande en attente...");
            livreur.livrerCommande(client, commandeTest); // Devrait échouer
            
        } catch (IllegalStateException e) {
            System.out.println("✓ Contrainte respectée: " + e.getMessage());
        }
        
        System.out.println("\n--- Test 4: Violation de la contrainte PasDeReductionSiPayee ---");
        try {
            Commande commandeTest = client.appelerPourCommander();
            Pizza pizzaTest = new Pizza();
            pizzaTest.setNom("Test");
            pizzaTest.setPrix(10.0);
            commandeTest.ajouterPizza(pizzaTest);
            
            // Payer la commande d'abord
            chef.recevoirCommande(commandeTest);
            chef.preparerCommande(commandeTest);
            client.payer(livreur, commandeTest);
            
            System.out.println("Tentative de traitement d'un retard sur une commande payée...");
            vendeur.traiterRetard(commandeTest); // Devrait échouer
            
        } catch (IllegalStateException e) {
            System.out.println("✓ Contrainte respectée: " + e.getMessage());
        }
    }
    
    /**
     * Méthode qui tente de créer une commande avec un montant négatif (violation de contrainte)
     */
    public static void creerCommandeMontantNegatif(Client client, Chef chef) {
        System.out.println("\n--- Test 5: Tentative de création d'une commande avec montant négatif ---");
        try {
            Commande commandeTest = client.appelerPourCommander();
            
            // Mettre la commande en préparation
            chef.recevoirCommande(commandeTest);
            chef.preparerCommande(commandeTest);
            
            System.out.println("Tentative de définition d'un montant négatif sur une commande active...");
            commandeTest.setMontant(-10.0); // Devrait échouer
            
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Contrainte respectée: " + e.getMessage());
        }
    }
    
    /**
     * Méthode qui tente de payer une commande livrée (violation de contrainte selon votre modification)
     */
    public static void payerCommandeLivree(Client client, Livreur livreur, Chef chef) {
        System.out.println("\n--- Test 6: Tentative de paiement d'une commande livrée ---");
        try {
            Commande commandeTest = client.appelerPourCommander();
            Pizza pizzaTest = new Pizza();
            pizzaTest.setNom("Test");
            pizzaTest.setPrix(10.0);
            commandeTest.ajouterPizza(pizzaTest);
            
            // Préparer et livrer la commande
            chef.recevoirCommande(commandeTest);
            chef.preparerCommande(commandeTest);
            commandeTest.setStatutCommande(Commande.STATUT_PRETE);
            livreur.livrerCommande(client, commandeTest);
            
            System.out.println("Tentative de paiement d'une commande déjà livrée...");
            client.payer(livreur, commandeTest); // Devrait échouer selon votre contrainte modifiée
            
        } catch (IllegalStateException e) {
            System.out.println("✓ Contrainte respectée: " + e.getMessage());
        }
    }
    
    /**
     * Méthode qui tente de livrer une commande en livraison (violation de contrainte selon votre modification)
     */
    public static void livrerCommandeEnLivraison(Livreur livreur, Client client, Chef chef) {
        System.out.println("\n--- Test 7: Tentative de livraison d'une commande en livraison ---");
        try {
            Commande commandeTest = client.appelerPourCommander();
            Pizza pizzaTest = new Pizza();
            pizzaTest.setNom("Test");
            pizzaTest.setPrix(10.0);
            commandeTest.ajouterPizza(pizzaTest);
            
            // Préparer la commande et la mettre en livraison
            chef.recevoirCommande(commandeTest);
            chef.preparerCommande(commandeTest);
            commandeTest.setStatutCommande(Commande.STATUT_EN_LIVRAISON);
            
            System.out.println("Tentative de livraison d'une commande déjà en livraison...");
            livreur.livrerCommande(client, commandeTest); // Devrait échouer selon votre contrainte modifiée
            
        } catch (IllegalStateException e) {
            System.out.println("✓ Contrainte respectée: " + e.getMessage());
        }
    }
}