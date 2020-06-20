/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_gestionfichierihm;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import manipulationdefichiers_metier.Dossier;
import manipulationdefichiers_metier.Fichier;
import manipulationdefichiers_metier.IDossier;

/**
 *
 * DossierIhm est une classe représentant un dossier pour l'ihm.
 * Elle implémente les interfaces IDossier et IFichierDossierIhm.
 * 
 * @author Pierre-Nicolas
 */
public class DossierIhm implements IDossier, IFichierDossierIhm {
    /**
     * Le dossier décoré implémentant la même interface.
     */
    private Dossier dossier;

    /**
     * Retourner le dossier décoré implémentant la même interface.
     * @return Le dossier décoré implémentant la même interface.
     */
    public Dossier getDossier() {
        return this.dossier;
    }
    
    /**
     * Retourner le chemin du dossier.
     * @return Le chemin du dossier.
     */       
    @Override
    public String getCheminFichier() {
        return this.dossier.getCheminFichier();
    }

    /**
     * Modifier le chemin du dossier.
     * @param cheminFichier Le nouveau chemin du dossier.
     */    
    @Override
    public void setCheminFichier(String cheminFichier) {
        this.dossier.setCheminFichier(cheminFichier);
    }

    /**
     * Retourner le nom du dossier.
     * @return Le nom du dossier.
     */    
    @Override
    public String getNomFichier() {
        return this.dossier.getNomFichier();
    }

    /**
     * Modifier le nom du dossier.
     * @param nomFichier Le nouveau nom du dossier.
     */    
    @Override
    public void setNomFichier(String nomFichier) {
        this.dossier.setCheminFichier(nomFichier);
    }

    /**
     * Retourner le contenu du dossier.
     * @return Le contenu du dossier.
     */
    @Override
    public String getContenuFichier() {
        return this.dossier.getContenuFichier();
    }

    /**
     * Modifier le contenu du dossier.
     * @param contenuFichier Le nouveau contenu du dossier.
     */    
    @Override
    public void setContenuFichier(String contenuFichier) {
        this.dossier.setContenuFichier(contenuFichier);
    }
    
    /**
     * Retourner l'extension du dossier.
     * @return L'extension du dossier.
     */    
    @Override
    public String getExtensionFichier() {
        return this.dossier.getExtensionFichier();
    }
    
    /**
     * Modifier l'extension du dossier.
     * @param extensionFichier La nouvelle extension du dossier.
     */
    @Override
    public void setExtensionFichier(String extensionFichier) {
        this.dossier.setExtensionFichier(extensionFichier);
    }

    /**
     * Retourner un booléen indiquant si le fichier est un dossier.
     * @return Un booléen indiquant si le fichier est un dossier.
     */    
    @Override
    public boolean isDossier() {
        return this.dossier.isDossier();
    }

    /**
     * Retourner une chaine de caractère représentant un dossier.
     * @return la chaine de caractère représentant un dossier.
     */    
    @Override
    public String toString() {
        return this.dossier.toString();
    }
    
    /**
     * Retourner uniquement la liste de fichiers contenus dans le dossier.
     * @return La liste de fichiers contenus dans le dossier.
     */    
    @Override
    public ArrayList<Dossier> getListeDossiers() {
        return this.dossier.getListeDossiers();
    }

    /**
     * Retourne la liste de fichiers et de dossiers contenus dans le dossier.
     * @return La liste de fichiers et de dossiers contenus dans le dossier.
     */
    @Override
    public ArrayList<Fichier> getListeFichiers() {
        return this.dossier.getListeFichiers();
    }
    
    /**
     * Retourner uniquement la liste de dossiers contenus dans le dossier.
     * @return La liste de dossiers contenus dans le dossier.
     */ 
    @Override
    public ArrayList<Fichier> getListeFichiersNonDossiers() {
        return this.dossier.getListeFichiersNonDossiers();
    }
    
    /**
     * Ajouter un fichier dans la liste de fichiers.
     * @param fichier Le fichier à ajouter.
     */    
    @Override
    public void ajouterFichier(Fichier fichier) {
        this.dossier.ajouterFichier(fichier);
    }
    
    /**
     * Supprimer un fichier de la liste de fichiers.
     * @param fichier Le fichier à supprimer
     */
    @Override
    public void supprimerFichier(Fichier fichier) {
        this.dossier.supprimerFichier(fichier);
    }
    
    /**
     * Retourner l'icone représentant le dossier pour l'ihm.
     * @return L'icone représentant le dossier pour l'ihm.
     */    
    @Override
    public ImageIcon getIcone() {
        return this.getIconeDossierFerme();
    }
    
    /**
     * Constructeur DossierIhm prenant en paramètre le dossier à décorer des fonctionnalités spécifique à l'ihm.
     * @param dossier Le dossier à décorer des fonctionnalités spécifique à l'ihm.
     */
    public DossierIhm(Dossier dossier) {
        this.dossier = dossier;
    }
    
    /**
     * Retourner l'icone représentant un dossier ouvert pour l'ihm.
     * @return L'icone représentant un dossier ouvert pour l'ihm.
     */
    public ImageIcon getIconeDossierOuvert() {
        return new ImageIcon(this.getClass().getResource("/manipulationdefichiers_images/dossierOuvert.png"));
    }
    
    /**
     * Retourner l'icone représentant un dossier fermé pour l'ihm.
     * @return L'icone représentant un dossier fermé pour l'ihm.
     */    
    public ImageIcon getIconeDossierFerme() {
        return new ImageIcon(this.getClass().getResource("/manipulationdefichiers_images/dossierFerme.png"));
    }
 
}
