/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers;

import java.util.ArrayList;
import javax.swing.SwingWorker;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionAccesAuFichierRefuse;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionFichierExisteDeja;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionFichierExistePas;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionFichierInchiffrable;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionFichierIndechiffrable;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionGeneraleChiffrement;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionGeneraleFichier;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionPersonnalisee;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionTailleDuFichierTropImportante;
import manipulationdefichiers_gestionfichierihm.LigneNombreMotFichierIhm;
import manipulationdefichiers_metier.Dossier;
import manipulationdefichiers_metier.Fichier;
import manipulationdefichiers_metier.IAdaptateurGestionFichiers;
import manipulationdefichiers_metier.IVue;

/**
 * 
 * Controleur est la classe qui représente un controleur.
 * 
 * @author Pierre-Nicolas
 */
public class Controleur {
    /**
     * La liste contenant les vues.
     */
    private ArrayList<IVue> listeIVues;
    /**
     * Le iadaptateur de gestion de fichiers.
     */
    private IAdaptateurGestionFichiers iadaptateurGestionFichiers;
    
    /**
     * Constructeur prenant en paramètre le iadaptateur de gestion de fichiers.
     * @param iadaptateurGestionFichiers  Le iadaptateur de gestion de fichiers.
     */
    public Controleur(IAdaptateurGestionFichiers iadaptateurGestionFichiers) {
        this.iadaptateurGestionFichiers = iadaptateurGestionFichiers;
        this.listeIVues = new ArrayList();
    }
    
    /**
     * Insrire une ivue.
     * @param ivue La ivue à incrire.
     */
    public void inscrireVue(IVue ivue) {
        this.listeIVues.add(ivue);
    }
    
    /**
     * Résilier une ivue.
     * @param ivue La ivue à résilier.
     */
    public void resilierVue(IVue ivue) {
        this.listeIVues.remove(ivue);
    }
    
    /**
     * Notifier l'ajout d'un fichier.
     * @param fichier Le fichier ajouté.
     */
    protected void notifierAjoutFichier(Fichier fichier) {
        for (IVue ivue : this.listeIVues) {
            ivue.ajoutFichier(fichier);
        }
    }
    
    /**
     * Notifier la suppression d'un fichier.
     * @param fichier Le fichier supprimé.
     */
    protected void notifierSuppressionFichier(Fichier fichier) {
        for (IVue ivue : this.listeIVues) {
            ivue.suppressionFichier(fichier);
        }
    }
    
    /**
     * Notifier la modification du nom et de l'extension fichier.
     * @param fichier Le fichier modifié.
     */
    protected void notifierModificationNomExtensionFichier(Fichier fichier) {
        for (IVue ivue : this.listeIVues) {
            ivue.modificationNomExtensionFichier(fichier);
        }
    }
    
    /**
     * Notifier la modification du chemin d'un fichier.
     * @param fichier Le fichier modifiée.
     */
    protected void notifierModificationCheminFichier(Fichier fichier) {
        for (IVue ivue : this.listeIVues) {
            ivue.modificationCheminFichier(fichier);
        }
    }
    
    /**
     * Notifier la modification du contenu d'un fichier.
     * @param fichier Le fichier modifié.
     */
    protected void notifierModificationContenuFichier(Fichier fichier) {
        for (IVue ivue : this.listeIVues) {
            ivue.modificationContenuFichier(fichier);
        }
    }
    
    /**
     * Notifier une exception personnalisée à toutes les ivues.
     * @param exceptionPersonnalisee L'exception personnalisée throw.
     */
    protected  void notifierException(ExceptionPersonnalisee exceptionPersonnalisee) {
        for (IVue ivue : this.listeIVues) {
            ivue.afficherException(exceptionPersonnalisee);
        }
    }
    
    /**
     * Notifier qu'un traitement long va se produire à toutes les ivues.
     */
    protected void notifierAffichageAvantTraitementLong() {
        for (IVue ivue : this.listeIVues) {
            ivue.affichageAvantTraitementLong();
        }
    }
    
    /**
     * Notifier qu'un traitement long s'est produit à toutes les ivues.
     */
    protected void notifierAffichageApresTraitementLong() {
        for (IVue ivue : this.listeIVues) {
            ivue.affichageApresTraitementLong();
        }        
    }
    
    /**
     * Ajouter un fichier au dossier.
     * @param fichier Le fichier à ajouter.
     * @param dossier Le dossier.
     */
    public void ajouterFichier(Fichier fichier, Dossier dossier) {
        this.notifierAffichageAvantTraitementLong();
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    fichier.setContenuFichier(fichier.getContenuFichier().replaceAll("(?!\\r)\\n", "\r\n"));
                    fichier.setCheminFichier(Controleur.this.iadaptateurGestionFichiers.concatenerCheminParentAvecFichier(dossier.getCheminFichier(), fichier.getNomFichier() + fichier.getExtensionFichier()));
                    Controleur.this.iadaptateurGestionFichiers.ajouterFicher(fichier);
                    dossier.ajouterFichier(fichier);
                    Controleur.this.notifierAjoutFichier(fichier);
                }
                catch (ExceptionAccesAuFichierRefuse | ExceptionGeneraleFichier | ExceptionFichierExisteDeja exception) {
                    Controleur.this.notifierException(exception);
                }
                return null;
            }
            
            @Override
            protected void done() {
                notifierAffichageApresTraitementLong();
            }
        };
        swingWorker.execute();
    }
    
    /**
     * Supprimer un fichier d'un dossier.
     * @param fichier Le fichier à supprimer.
     * @param dossier Le dossier.
     */
    public void supprimerFichier(Fichier fichier, Dossier dossier) {
        this.notifierAffichageAvantTraitementLong();
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    Controleur.this.iadaptateurGestionFichiers.supprimerFichier(fichier);
                    dossier.supprimerFichier(fichier);
                    Controleur.this.notifierSuppressionFichier(fichier);
                }
                catch (ExceptionFichierExistePas | ExceptionGeneraleFichier | ExceptionAccesAuFichierRefuse exception) {
                    Controleur.this.notifierException(exception);
                }                
                return null;
            }
            
            @Override
            protected void done() {
                notifierAffichageApresTraitementLong();
            }
        };
        swingWorker.execute();
    }
    
    /**
     * Modifier le nom et l'extension d'un fihcier.
     * @param fichier Le fichier à modifier.
     * @param nomFichier Le nouveau nom du fichier.
     * @param extensionFichier La nouvelle extension du fichier.
     */
    public void modifierNomExtensionFichier(Fichier fichier, String nomFichier, String extensionFichier) {
        this.notifierAffichageAvantTraitementLong();
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {       
                try {
                    Controleur.this.iadaptateurGestionFichiers.modifierNomFichier(fichier, nomFichier + Controleur.this.iadaptateurGestionFichiers.getExtensionCorrect(extensionFichier));
                    String cheminParent = Controleur.this.iadaptateurGestionFichiers.getCheminParentFichier(fichier);
                    String nouveauCheminFichier = Controleur.this.iadaptateurGestionFichiers.concatenerCheminParentAvecFichier(cheminParent, nomFichier + Controleur.this.iadaptateurGestionFichiers.getExtensionCorrect(extensionFichier));
                    fichier.setCheminFichier(nouveauCheminFichier);
                    fichier.setNomFichier(nomFichier);
                    fichier.setExtensionFichier(extensionFichier);
                    Controleur.this.notifierModificationNomExtensionFichier(fichier);
                }
                catch (ExceptionGeneraleFichier | ExceptionFichierExisteDeja | ExceptionAccesAuFichierRefuse exception) {
                    Controleur.this.notifierException(exception);
                }
                return null;
            }
            
            @Override
            protected void done() {
                notifierAffichageApresTraitementLong();
            }
        };
        swingWorker.execute();
    }
    
    /**
     * Modifier le chemin d'un fichier.
     * @param fichier Le fichier à modifier.
     * @param cheminFichier Le nouveau chemin du fichier.
     */
    public void modifierCheminFichier(Fichier fichier, String cheminFichier) {
        this.notifierAffichageAvantTraitementLong();
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {       
                try {
                    Controleur.this.iadaptateurGestionFichiers.modifierCheminFichier(fichier, cheminFichier);
                    String nouvauNomFichier = Controleur.this.iadaptateurGestionFichiers.getNomFichierAvecChemin(cheminFichier);
                    fichier.setNomFichier(nouvauNomFichier);
                    fichier.setCheminFichier(cheminFichier);
                    Controleur.this.notifierModificationCheminFichier(fichier);
                }
                catch (ExceptionGeneraleFichier | ExceptionFichierExisteDeja | ExceptionAccesAuFichierRefuse exception) {
                    Controleur.this.notifierException(exception);
                }
                return null;
            }
            
            @Override
            protected void done() {
                notifierAffichageApresTraitementLong();
            }
        };
        swingWorker.execute();
    }
    
    /**
     * Modifier me contenu d'un fichier.
     * @param fichier Le fichier à modfier.
     * @param contenuFichier Le nouveau contenu du fichier.
     */
    public void modifierContenuFichier(Fichier fichier, String contenuFichier) {
        this.notifierAffichageAvantTraitementLong();
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {       
                try {
                    Controleur.this.iadaptateurGestionFichiers.modifierContenuFichier(fichier, contenuFichier);
                    fichier.setContenuFichier(contenuFichier);
                    Controleur.this.notifierModificationContenuFichier(fichier);
                }
                catch (ExceptionAccesAuFichierRefuse | ExceptionGeneraleFichier exception) {
                    Controleur.this.notifierException(exception);
                }
                return null;
            }
            
            @Override
            protected void done() {
                notifierAffichageApresTraitementLong();
            }
        };
        swingWorker.execute();
    }
    
    /**
     * Dupliquer un fichier dans un dossier.
     * @param fichier Le fichier à dupliquer.
     * @param dossier Le dossier contenant le nouveau fichier.
     */
    public void dupliquerFichier(Fichier fichier, Dossier dossier) {
        this.notifierAffichageAvantTraitementLong();
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {       
                try {
                    Fichier nouveauFichier = new Fichier();
                    nouveauFichier.setContenuFichier(fichier.getContenuFichier());
                    nouveauFichier.setNomFichier(fichier.getNomFichier() + "_Copie");
                    nouveauFichier.setExtensionFichier(fichier.getExtensionFichier());
                    String cheminParent = dossier.getCheminFichier();
                    String nouveauCheminFichier = Controleur.this.iadaptateurGestionFichiers.concatenerCheminParentAvecFichier(cheminParent, nouveauFichier.getNomFichier() + nouveauFichier.getExtensionFichier());
                    nouveauFichier.setCheminFichier(nouveauCheminFichier);
                    Controleur.this.iadaptateurGestionFichiers.ajouterFicher(nouveauFichier);
                    dossier.ajouterFichier(nouveauFichier);
                    Controleur.this.notifierAjoutFichier(nouveauFichier);
                }
                catch (ExceptionAccesAuFichierRefuse | ExceptionGeneraleFichier | ExceptionFichierExisteDeja exception) {
                    Controleur.this.notifierException(exception);
                }
                return null;
            }
            
            @Override
            protected void done() {
                notifierAffichageApresTraitementLong();
            }
        };
        swingWorker.execute();
    }
    
    /**
     * Chifffer le fichier dans le dossier.
     * @param fichier Le fichier à chiffrer.
     * @param dossier Le dossier contenant le fichier chiffré.
     */
    public void chiffrerFichier(Fichier fichier, Dossier dossier) {
        this.notifierAffichageAvantTraitementLong();
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {       
                try {
                    Fichier fichierAChiffrer = new Fichier();
                    fichierAChiffrer.setContenuFichier("");
                    fichierAChiffrer.setNomFichier(fichier.getNomFichier() + "_Chiffre");
                    fichierAChiffrer.setExtensionFichier(fichier.getExtensionFichier());
                    String cheminParent = dossier.getCheminFichier();
                    String nouveauCheminFichier = Controleur.this.iadaptateurGestionFichiers.concatenerCheminParentAvecFichier(cheminParent, fichierAChiffrer.getNomFichier() + fichierAChiffrer.getExtensionFichier());
                    fichierAChiffrer.setCheminFichier(nouveauCheminFichier);
                    Controleur.this.iadaptateurGestionFichiers.chiffrerFichier(fichier, fichierAChiffrer);
                    dossier.ajouterFichier(fichierAChiffrer);
                    Controleur.this.notifierAjoutFichier(fichierAChiffrer);
                }
                catch (ExceptionGeneraleChiffrement | ExceptionFichierInchiffrable | ExceptionGeneraleFichier | ExceptionAccesAuFichierRefuse | ExceptionFichierExisteDeja exception) {
                    Controleur.this.notifierException(exception);
                }
                return null;
            }
            
            @Override
            protected void done() {
                notifierAffichageApresTraitementLong();
            }
        };
        swingWorker.execute();
    }
    
    /**
     * Déchiffrer un fichier.
     * @param fichier Le fichier à déchiffrer.
     * @param dossier Le dossier contenant le fichier déchiffré.
     */
    public void dechiffrerFichier(Fichier fichier, Dossier dossier) {
        this.notifierAffichageAvantTraitementLong();
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {       
                try {
                    Fichier fichierADechiffrer = new Fichier();
                    fichierADechiffrer.setContenuFichier("");
                    fichierADechiffrer.setNomFichier(fichier.getNomFichier() + "_Dechiffre");
                    fichierADechiffrer.setExtensionFichier(fichier.getExtensionFichier());
                    String cheminParent = dossier.getCheminFichier();
                    String nouveauCheminFichier = Controleur.this.iadaptateurGestionFichiers.concatenerCheminParentAvecFichier(cheminParent, fichierADechiffrer.getNomFichier() + fichierADechiffrer.getExtensionFichier());
                    fichierADechiffrer.setCheminFichier(nouveauCheminFichier);
                    Controleur.this.iadaptateurGestionFichiers.dechiffrerFichier(fichier, fichierADechiffrer);
                    dossier.ajouterFichier(fichierADechiffrer);
                    Controleur.this.notifierAjoutFichier(fichierADechiffrer);
                }
                catch (ExceptionGeneraleChiffrement | ExceptionFichierIndechiffrable | ExceptionGeneraleFichier | ExceptionAccesAuFichierRefuse | ExceptionFichierExisteDeja exception) {
                    Controleur.this.notifierException(exception);
                }
                return null;
            }
            
            @Override
            protected void done() {
                notifierAffichageApresTraitementLong();
            }
        };
        swingWorker.execute();
    }
    
    /**
     * Concatener la liste de fichiers dans le dossier.
     * @param listeFichierAConcatener La liste de fichier à concatener.
     * @param dossier Le dossier contenant le fichier concaténé.
     * @param nomFichierConcatene Le nom du fichier concaténé.
     */
    public void concatenerListeFichier(ArrayList<Fichier> listeFichierAConcatener, Dossier dossier, String nomFichierConcatene) {
        this.notifierAffichageAvantTraitementLong();
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {       
                try {
                    Fichier fichierConcatene = new Fichier();
                    fichierConcatene.setContenuFichier("");
                    if (nomFichierConcatene.isEmpty()) {
                        fichierConcatene.setNomFichier(listeFichierAConcatener.get(0).getNomFichier()+ "_Concatene");
                    }
                    else {
                        fichierConcatene.setNomFichier(nomFichierConcatene);
                    }
                    fichierConcatene.setExtensionFichier(listeFichierAConcatener.get(0).getExtensionFichier());
                    String cheminParent = dossier.getCheminFichier();
                    String nouveauCheminFichier = Controleur.this.iadaptateurGestionFichiers.concatenerCheminParentAvecFichier(cheminParent, fichierConcatene.getNomFichier() + fichierConcatene.getExtensionFichier());
                    fichierConcatene.setCheminFichier(nouveauCheminFichier);
                    for (Fichier fichierAConcatene : listeFichierAConcatener) {
                        fichierConcatene.setContenuFichier(fichierConcatene.getContenuFichier() + fichierAConcatene.getContenuFichier());
                    }
                    Controleur.this.iadaptateurGestionFichiers.ajouterFicher(fichierConcatene);
                    dossier.ajouterFichier(fichierConcatene);
                    Controleur.this.notifierAjoutFichier(fichierConcatene);
                } 
                catch (ExceptionAccesAuFichierRefuse | ExceptionGeneraleFichier | ExceptionFichierExisteDeja exception) {
                    Controleur.this.notifierException(exception);
                }
                return null;
            }
            
            @Override
            protected void done() {
                notifierAffichageApresTraitementLong();
            }
        };
        swingWorker.execute();
    }
    
    /**
     * Lister toutes les occurences d'un mot dans un fichier.
     * @param fichier Le fichier.
     * @param mot Le mot à lister.
     * @return La liste d'indexs associés au nombre de mot par ligne.
     */
    public ArrayList<LigneNombreMotFichierIhm> listerOccurencesMot(Fichier fichier, String mot) {
        ArrayList<LigneNombreMotFichierIhm> res = new ArrayList();
        String[] listeLignesFichier = fichier.getContenuFichier().split(System.getProperty("line.separator"));
        for (int i = 0; i < listeLignesFichier.length; i++) {
            int nombreMotsDansLaLigne = (listeLignesFichier[i].split(mot, -1).length) - 1;
            if (nombreMotsDansLaLigne > 0) {
                res.add(new LigneNombreMotFichierIhm(i, nombreMotsDansLaLigne));
            }
        }
        return res;
    }
    
    /**
     * Charger un fichier avec son chemin.
     * @param cheminFichier Le chemin du fichier à charger.
     * @return Le fichier chargé.
     */
    public Fichier chargerFichier(String cheminFichier) {
        Fichier fichier = null;
        try {
            fichier = new Fichier();
            fichier.setCheminFichier(cheminFichier);
            this.iadaptateurGestionFichiers.chargerFichier(fichier);
        }
        catch (ExceptionTailleDuFichierTropImportante | ExceptionGeneraleFichier | ExceptionAccesAuFichierRefuse exception) {
            fichier = null;
            this.notifierException(exception);
        }
        return fichier;
    }
    
}
