package sample;

import javax.mail.MessagingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Agence {


    public String nom = "ImmoESI";
    private Admin admin = new Admin("chems", "0000");

    public ArrayList<Bien> biens = new ArrayList<>();
    public ArrayList<Proprietaire> proprietaires = new ArrayList<>();
    public TreeSet<Bien> treeBiens = new TreeSet<>();
    private Set<Bien> bienArchives = new HashSet<>();


    public void menu() throws IOException {

        Scanner sc = new Scanner(System.in);// for strings
        char choix;

        do{
            System.out.println(CYAN+"************ MENU *************"+RESET);
            System.out.println("1 - Acces publique. ");
            System.out.println("2 - Acces Administrateur (se connecter) .");
            System.out.println(RED+"0 - Quitter."+RESET);
            choix = sc.next().charAt(0);

            switch (choix){
                case '1':
                    menuPublique();
                    break;
                case '2':
                    if (admin.identification())

                    {
                        System.out.println("Acc\u00E8s autoris\u00E9");
                        menuAdministrateur();
                    }
                    else System.out.println("Acc\u00E8s refus\u00E9");
                    break;
            }

        }while(choix!='0');

    }

    public void menuPublique() {

        System.out.println(GREEN+"******* MENU - Publique *******"+RESET);
        Scanner sc = new Scanner(System.in);// for strings
        char choix;

        do {
            System.out.println("1 - Afficher les annonces.");

            System.out.println("2 - Afficher la liste des prix du m\u00B2 par wilaya");
            System.out.println("3 - Envoyer un message a l'agence");
            System.out.println("4 - Afficher la liste des propri\u00e9taires.");

            System.out.println(RED+"0 - Quitter."+RESET);
            choix = sc.next().charAt(0);

            switch(choix){
                case '1':afficherAnnoces();
                break;
                case '2':Wilaya.afficherListePrix();
                break;
                case '3':envoyerMessage();
                break;
                case '4':afficherListeProprietaires();
                break;

            }
        }while(choix!='0');
    }


    public void menuAdministrateur() throws IOException {
        Scanner sc = new Scanner(System.in);// for strings
        char choix;

        do {
            System.out.println(YELLOW+"******* MENU - Admin *******"+RESET);
            System.out.println("1 - Gestion Biens");
            System.out.println("2 - Afficher les annonces.");
            System.out.println("3 - Recherche par un critere(ou plusieurs)");
            System.out.println("4 - Afficher la liste des prix du m\u00B2 par wilaya");
            System.out.println("5 - Afficher la liste des propri\u00e9taires.");
            System.out.println("6 - Afficher les messages envoyes.");
            System.out.println(RED+"0 - Quitter."+RESET);
            choix = sc.next().charAt(0);

            switch(choix){
                case '1': gestionBiens();
                    break;
                case '2': afficherAnnoces();
                    break;
                case '3': choixCriteres();
                    break;
                case '4': Wilaya.afficherListePrix();
                    break;
                case '5': afficherListeProprietaires();
                    break;
                case '6': afficherMessages();
            }

        }while(choix!='0');
    }

    public void ajouterBien() throws IOException {
        /* declarations */
        char choix;
        Scanner sc = new Scanner(System.in);
        Proprietaire prop=null;
        try{
            do {

                System.out.println("Choix de proprietaire: ");
                System.out.println("1 - Propri\u00E9taire existant ");
                System.out.println("2 - Cr\u00E9er un nouveau propri\u00E9taire");

                System.out.print("Choix: ");
                choix = sc.next().charAt(0);

                switch (choix){
                    case '1':prop = selectProp();
                        break;
                    case '2':prop =  new Proprietaire();
                        proprietaires.add(prop);
                        break;
                    default: System.out.println("choix 1 ou 2");
                        break;
                }


            }while(choix != '1' && choix != '2');


            Transaction tr=typeTransaction(); // choix de transaction

            String typeBien = typeBien(); // selection de type de bien : appart , maison, terrrain

            switch (typeBien){
                case "Appartement": ajouterAppart(tr, prop);
                    break;
                case "Maison":ajouterMaison(tr, prop);
                    break;
                case "Terrain":ajouterTerrain(tr, prop);
                    break;
            }
        }catch (InputMismatchException | NegativeException e){
            System.out.println("ATTENTION ! \n Erreur a la saisie !!! ");
        }
        

    }

    public void ajouterAppart(Transaction tr, Proprietaire prop) throws InputMismatchException{
    try{
        Appartement appart = new Appartement(tr, prop);
        treeBiens.add(appart);
        prop.ajouterBien(appart);
    }catch (NegativeException e){
        System.out.println("Entree negative");
    }catch (InputMismatchException e){
        System.out.println("entree fausse");
    }

    }

    public void ajouterMaison(Transaction tr, Proprietaire prop)throws InputMismatchException{

            //instancier la maison

        try{
            Maison maison = new Maison(tr, prop);
            treeBiens.add(maison);
            prop.ajouterBien(maison);
        }catch (NegativeException e){
            System.out.println("Entree negative");
        }catch (InputMismatchException e){
            System.out.println("entree fausse");
        }



    }

    public void ajouterTerrain(Transaction tr, Proprietaire prop) throws InputMismatchException, NegativeException {
        try{
            Terrain ter = new Terrain(tr, prop);
            treeBiens.add(ter);
            prop.ajouterBien(ter);
        }catch (NegativeException e){
            System.out.println("Entree negative");
        }catch (InputMismatchException e){
            System.out.println("entree fausse");
        }

    }

    public void afficherAnnoces() {
        /** affichage simple des annonces*/

        Scanner sc = new Scanner(System.in);
        char choix;

        do {

            System.out.println(RED+"*** Annonces ***"+RESET);
            System.out.println("1 - Afficher toutes les annonces.");
            System.out.println("2 - Afficher les bien d'un proprietaire");
            System.out.println(RED+"0 - Quitter."+RESET);
            choix = sc.next().charAt(0);


            switch (choix){
                case '1':afficherAnnonceCourtes();
                         afficherAnnonceDetails();
                         break;
                case '2':
                    Proprietaire prop = selectProp();
                    prop.afficher_biens();
                    break;

            }

        } while(choix!='0');




    }


    public void afficherAnnonceCourtes(){
        int i = 1;
        if(!(treeBiens.isEmpty())){
            for (Bien bi:treeBiens) {
                System.out.println(GREEN+"--------------------------"+RESET);
                System.out.print(i+" ");
                bi.afficherCourt();
                i++;
            }
        }else System.out.println(RED+"Il n'y a aucun bien \u00E0 afficher."+RESET);
    }
    public void afficherAnnonceDetails(){
        char select;
        Scanner sc = new Scanner(System.in);



        System.out.println("Vous voulez un affichage en detail ?");
        System.out.println("1. OUI");
        System.out.println("2. NON");
        System.out.println(RED+"0. Quitter"+RESET);

        select = sc.next().charAt(0);
        try {
            switch (select){
                case '1': afficherAnnonceCourtes();
                    System.out.println("Donner le Numero du bien");
                    int sel = sc.nextInt();
                    //biens.get(sel-1).afficher();
                    Bien obj = (Bien) treeBiens.toArray()[sel-1];
                    obj.afficher();
                    break;
            }
        }catch (InputMismatchException e){
            System.out.println("Erreur de saisie");
        }catch (IndexOutOfBoundsException e){
            System.out.println("Erreur d'indice");
        }

    }


    // gestion des bien

    public void suppprimerBien() {
        Scanner sc = new Scanner(System.in);

        if (!(treeBiens.isEmpty())){
            afficherAnnonceCourtes();
            System.out.println("Selectioner selon le matricule du bien:");
            int select = sc.nextInt();
            //Bien bien = biens.get(select-1);
            Bien obj = (Bien) treeBiens.toArray()[select-1];
            treeBiens.remove(obj);
            //bien.prop.supprimerBien(obj);
            obj.prop.supprimerBien(obj);

        }else {
            System.out.println("Liste de biens vide");
        }

    }

    public void archiverBien() {


        Scanner sc = new Scanner(System.in);

        if (!(treeBiens.isEmpty())){
            afficherAnnonceCourtes();
            System.out.println("Selectioner selon le matricule du bien:");
            int select = sc.nextInt();
            //Bien bien = biens.get(select-1);
            Bien obj = (Bien) treeBiens.toArray()[select-1];
            //biens.remove(bien);
            treeBiens.remove(obj);
            //bien.prop.supprimerBien(bien);
            obj.prop.supprimerBien(obj);
            //bienArchives.add(bien);
            bienArchives.add(obj);

        }else {
            System.out.println("Liste de biens vide");
        }
    }

    public void modifierBien(){

        Scanner sc = new Scanner(System.in);
        char choix = 0;

        afficherAnnonceCourtes();
        System.out.println("Selectioner selon le matricule du bien:");
        int select = sc.nextInt();
        //Bien bien = biens.get(select-1);
        Bien obj = (Bien) treeBiens.toArray()[select-1];

        System.out.println("Quel champ voulez-vous modifier ?");
        System.out.println("1. Adresse");
        System.out.println("2. Superficie");
        System.out.println("3. Proprietaire");
        System.out.println("4. Transaction");
        System.out.println("5. Wilaya");
        System.out.println("6. Description");
        System.out.println("0. Quitter");


        do {
            choix = sc.next().charAt(0);
        }while (choix<'0' || choix>'6');
            switch (choix){

                case '1':
                    System.out.print("Nouvelle valeur:");
                    obj.addresse = sc.nextLine();
                    break;
                case '2':
                    System.out.print("Nouvelle valeur");
                    obj.setSuperficie(sc.nextFloat());
                    break;
                case '3':
                    System.out.println("Nouvelle valeur:");
                    afficherListeProprietaires();
                    System.out.println("Donner le code du nouveau propprietaire");
                    obj.setProp(proprietaires.get(sc.nextInt()));
                    break;
                case '4':
                    System.out.println("Nouvelle valeur:");
                    obj.setTransaction(typeTransaction());
                    break;
                case '5':
                    System.out.println("Nouvlle valeur:(code wilaya)");
                    obj.setWilaya(Wilaya.codeToWilaya(sc.nextInt()));
                    break;
                case '6':
                    System.out.println("Nouvelle description:");
                    obj.setDescription(sc.nextLine());
                    break;
            }

    }

    public void gestionBiens() throws IOException {
        Scanner sc = new Scanner(System.in);

        char choix;
        do{
            System.out.println("1 - Ajouter un Bien - Cr\u00E9er Annonce");
            System.out.println("2 - Suprimer un Bien");
            System.out.println("3 - Archiver un Bien");
            System.out.println("4 - Modifier un Bien");
            System.out.println(RED+"0 - Quitter."+RESET);
            choix = sc.next().charAt(0);
            switch (choix){
                case '1':ajouterBien();
                break;
                case '2':suppprimerBien();
                break;
                case '3':archiverBien();
                break;
                case '4':modifierBien();
                break;
            }
        }while(choix != '0');

    }


    /** Gestion des fichier */
    public ArrayList<Proprietaire> ouvrirFicherProprietaires(String filename)
    /** cette procedure sert à charger les données depuis le fichier "filename" des proprétaires

     */

    {
        Path pathToFile = Paths.get(filename);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.UTF_8)) {

            // read the first line from the text file
            String line = br.readLine();
            line = br.readLine();


            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(";");

                Proprietaire prop = new Proprietaire(Integer.parseInt(attributes[0]),attributes[1], attributes[2],  attributes[3],  attributes[4], attributes[5]);
                // adding un proprietaire into ArrayList
                proprietaires.add(prop);
                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return proprietaires;

    }


    public void chargerFicherProprietaire() throws IOException {

        FileWriter csvWriter = new FileWriter("proprietaires.csv");

        csvWriter.append("matricule;nom;prenom;adrmail;telephone;adresse;\n");

        for (Proprietaire prop:proprietaires
             ) {
            csvWriter.append(String.valueOf(prop.getMatricule())+";");
            csvWriter.append(prop.getNom()+";");
            csvWriter.append(prop.getPrenom()+";");
            csvWriter.append(prop.getadrMail()+";");
            csvWriter.append(prop.getTel()+";");
            csvWriter.append(prop.getAdresse()+";");
            csvWriter.append("\n");

        }

        csvWriter.flush();
        csvWriter.close();

    }

    public void afficherListeProprietaires() {
        System.out.println("***************** Les Propri\u00e9taires  *****************");

        int i =1;
        for (Proprietaire prop:proprietaires) {
            System.out.print(i+") ");i++;
            prop.afficher();
            System.out.println("-------------------------------------");
        }
    }

    public Transaction typeTransaction() throws InputMismatchException{
        char choix;
        Scanner lect = new Scanner(System.in);
        System.out.println("Type de Transaction:");
        System.out.println("1 - Vente");
        System.out.println("2 - Location");
        System.out.println("3 - Echange");

        choix = lect.next().charAt(0);

        switch (choix) {
            case '1': return Transaction.vente;
            case '2': return Transaction.location;
            case '3': return Transaction.echange;
            default: throw new InputMismatchException();
        }
    }

    public String typeBien() throws InputMismatchException{
        System.out.println("Quel est le type de votre bien:");
        Scanner sc = new Scanner(System.in);// for strings
        char choix;

        System.out.println("1 - Appartement");
        System.out.println("2 - Maison");
        System.out.println("3 - Terrain");

        choix = sc.next().charAt(0);

        switch(choix){
            case '1':return "Appartement";
            case '2': return "Maison";
            case '3': return "Terrain";
            default: throw new InputMismatchException();
        }

    }

    public Proprietaire selectProp(){
        Scanner sc = new Scanner(System.in);
        afficherListeProprietaires();

        System.out.println("Selectionnez un Proprietaire existant.");

        char choix = sc.next().charAt(0);
        return proprietaires.get(Integer.parseInt(String.valueOf(choix))-1);

    }

    private void envoyerMessage() {
        Scanner sc = new Scanner(System.in);
        try{
            afficherAnnonceCourtes();
            System.out.println("Selectioner selon le matricule du bien:");
            int select = sc.nextInt();
            //Bien bien = biens.get(select-1);
            Bien obj = (Bien) treeBiens.toArray()[select - 1];
            Message2 message = new Message2(obj);

        }catch(MessagingException e){
            System.out.println("Erreur, Message non envoy\u00E9");
        }
    }

    public void afficherMessages(){
        for (Message2 mess:Message2.messages
             ) {mess.afficher(); }
    }


    public boolean textToBool(String rep){
        rep.toLowerCase();
        return rep == "oui" || rep == "o";
    }
    public boolean textToBool(char rep){
        return rep == 'o' || rep == 'O';
    }

    public void declarationsBiens(){
        Bien b1 = new Appartement(1, "31 cite afak afak", 120, proprietaires.get(1), Transaction.vente,
                4000000, false, "","2019-01-01", Wilaya.codeToWilaya(1),
                "Photo", 4, false,1,  false, false);

        Bien b2 = new Maison(2, "wilaya3", 200, proprietaires.get(0), Transaction.vente, 10000000,
                true, "avec jardin", "2020-01-02", Wilaya.codeToWilaya(3), "photo",
                3,true, 1, true, 20, 20);

        Bien b3 = new Terrain(3, "hydra", 500, proprietaires.get(0), Transaction.vente,
                20000000, true, "zone calme", "2019-01-03", Wilaya.codeToWilaya(3),
                "photo", StatutJuridique.livret, 3 );

        Bien b4 = new Appartement(4, "said hamdine", 100, proprietaires.get(1), Transaction.location,
                40000, true, "","2019-01-04", Wilaya.codeToWilaya(3),
                "Photo", 3, true,1,  true, true);

        Bien b5 = new Maison(5, "zouaghi", 160, proprietaires.get(2), Transaction.location, 150000,
                true, "avec jardin", "2019-04-02", Wilaya.codeToWilaya(2), "photo",
                3,false, 1, false, 10, 0);

        Bien b6 = new Appartement(6, "centre ville", 50, proprietaires.get(1),
                Transaction.location, 60000, true, "","2019-06-04",
                Wilaya.codeToWilaya(3), "Photo", 1, false,6,  false, false);

        Bien b7 = new Terrain(7, "babezouar", 650, proprietaires.get(0), Transaction.echange,
                18000000, true, "zone industrielle", "2019-08-26", Wilaya.codeToWilaya(1),
                "photo", StatutJuridique.livret, 1);

        Bien b8 = new Maison(8, "El Biar", 200, proprietaires.get(2), Transaction.echange, 14000000,
                true, "avec jardin", "2020-01-06", Wilaya.codeToWilaya(2), "photo",
                3,false, 1, true, 0, 0);

        treeBiens.add(b1);
        treeBiens.add(b2);
        treeBiens.add(b3);
        treeBiens.add(b4);
        treeBiens.add(b5);
        treeBiens.add(b6);
        treeBiens.add(b7);
        treeBiens.add(b8);

    }

    public void choixCriteres(){


        boolean criteres[] = new boolean[10];
        Scanner sc = new Scanner(System.in);
        int nbCriteres=0, cpt;
        double prixMin = 0;
        float superficieMin = 0;
        Transaction tr = null;
        String tpBien = null;
        Wilaya wilaya = null;


        for(int i = 0; i<criteres.length;i++){
            criteres[i] = false;
        }

        try {
            System.out.println("Donner le nombre de criteres : ");
            nbCriteres = sc.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Erreur de saisie");
        }

        /** COMMENTAIRES sur le fonctionnement de cette methode
         *  le tableau de boolean
         *  premiere case : (critere de prix min)true : veut dire on prend l critere
         *  deuxieme case : (superficie min)
         *  troisieme case: pour la wilaya
         */
        cpt = 0;


        while(cpt < nbCriteres){
            System.out.println("Liste des criteres:");
            System.out.println("1. Prix min");
            System.out.println("2. Superficie min");
            System.out.println("3. Wilaya");
            System.out.println("4. Type de Bien");
            System.out.println("5. Transaction");
            System.out.print("\nActiver critere N"+(cpt+1)+"> ");
            cpt++;
            int choix = sc.nextInt();

            switch (choix){
                case 1: criteres[0]= true;
                    System.out.print("Donner le prix minimal:");
                    prixMin = sc.nextDouble();
                break;
                case 2: criteres[1] = true;
                    System.out.print("Donner la superficie minimal:");
                    superficieMin = sc.nextFloat();
                break;
                case 3: criteres[2] = true;
                    System.out.println("Donner le matricule de la wilaya:");
                    int wil = sc.nextInt();
                    wilaya = Wilaya.codeToWilaya(wil); // on transforme le matricule en wilaya (objet)
                    break;
                case 4:criteres[3] = true;
                    tpBien = typeBien();
                    break;
                case 5: criteres[4] = true;

                    tr = typeTransaction();
                    break;
            }
        }


        ArrayList<Bien> biensValides = new ArrayList<>();
        //biensValides.addAll(biens);
        biensValides.addAll(treeBiens);
        cpt = 0;
        for(Bien bien : treeBiens){
            int i =0;cpt = 0;
                while((i < criteres.length) && (cpt < nbCriteres)){
                    if (criteres[i] == true){
                        cpt++;
                        switch (i){
                            case 0: if (bien.calculPrix(bien.transaction, true) < prixMin)
                                biensValides.remove(bien);
                                break;
                            case 1: if(bien.superficie < superficieMin)
                                biensValides.remove(bien);
                                break;
                            case 2: if(!(bien.wilaya.getNom().equals(wilaya.getNom())))
                                biensValides.remove(bien);
                                break;
                            case 3: if(!(bien.getClass().getSimpleName().equals(tpBien)))
                                biensValides.remove(bien);
                                break;
                            case 4: if(!(bien.transaction.equals(tr)))
                                biensValides.remove(bien);
                                break;
                        }
                    }
                    i++;
                }
        }

        for (Bien bien: biensValides) {
            System.out.println("------------");
            bien.afficherCourt();
        }

    }

    // colors
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

}
