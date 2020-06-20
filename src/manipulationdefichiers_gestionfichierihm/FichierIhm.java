/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_gestionfichierihm;

import javax.swing.ImageIcon;
import manipulationdefichiers_metier.Fichier;
import manipulationdefichiers_metier.IFichier;

/**
 *
 * FichierIhm est une classe représentant un fichier pour l'ihm.
 * Elle implémente les interfaces IFichier et IFichierDossierIhm.
 * 
 * @author Pierre-Nicolas
 */
public class FichierIhm implements IFichier, IFichierDossierIhm {
    /**
     * Le fichier décoré implémentant la même interface.
     */
    private Fichier fichier;

    /**
     * Retourner le fichier décoré implémentant la même interface.
     * @return Le fichier décoré implémentant la même interface.
     */
    public Fichier getFichier() {
        return this.fichier;
    }
    
    /**
     * Retourner le chemin du fichier.
     * @return Le chemin du fichier.
     */ 
    @Override
    public String getCheminFichier() {
        return this.fichier.getCheminFichier();
    }
    
    /**
     * Modifier le chemin du fichier.
     * @param cheminFichier Le nouveau chemin du fichier.
     */    
    @Override
    public void setCheminFichier(String cheminFichier) {
        this.fichier.setCheminFichier(cheminFichier);
    }

    /**
     * Retourner le nom du fichier.
     * @return Le nom du fichier.
     */    
    @Override
    public String getNomFichier() {
        return this.fichier.getNomFichier();
    }

    /**
     * Modifier le nom du fichier.
     * @param nomFichier Le nouveau nom du fichier.
     */    
    @Override
    public void setNomFichier(String nomFichier) {
        this.fichier.setNomFichier(nomFichier);
    }

    /**
     * Retourner le contenu du fichier.
     * @return Le contenu du fichier.
     */
    @Override
    public String getContenuFichier() {
        return this.fichier.getContenuFichier();
    }

    /**
     * Modifier le contenu du fichier.
     * @param contenuFichier Le nouveau contenu du fichier.
     */    
    @Override
    public void setContenuFichier(String contenuFichier) {
        this.fichier.setContenuFichier(contenuFichier);
    }
    
    /**
     * Retourner l'extension du fichier.
     * @return L'extension du fichier.
     */    
    @Override
    public String getExtensionFichier() {
        return this.fichier.getExtensionFichier();
    }
    
    /**
     * Modifier l'extension du fichier.
     * @param extensionFichier La nouvelle extension du fichier.
     */
    @Override
    public void setExtensionFichier(String extensionFichier) {
        this.fichier.setExtensionFichier(extensionFichier);
    }

    /**
     * Retourner un booléen indiquant si le fichier est un dossier.
     * @return Un booléen indiquant si le fichier est un dossier.
     */
    @Override
    public boolean isDossier() {
        return this.fichier.isDossier();
    }

    /**
     * Retourner une chaine de caractère représentant un fichier.
     * @return la chaine de caractère représentant un fichier.
     */     
    @Override
    public String toString() {
        return this.fichier.toString();
    }
    
    /**
     * Retourner l'icone représentant le fichier pour l'ihm.
     * @return L'icone représentant le fichier pour l'ihm.
     */
    @Override
    public ImageIcon getIcone() {
        return new ImageIcon(this.getClass().getResource("/manipulationdefichiers_images/fichier.png"));
    }
    
    /**
     * Constructeur FichierIhm prenant en paramètre le fichier à décorer des fonctionnalités spécifique à l'ihm.
     * @param fichier Le fichier à décorer des fonctionnalités spécifique à l'ihm.
     */
    public FichierIhm(Fichier fichier) {
        this.fichier = fichier;
    }
    
}
