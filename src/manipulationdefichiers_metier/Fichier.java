/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_metier;

/**
 *
 * Fichier est la classe représentant un fichier.
 * 
 * @author Pierre-Nicolas
 */
public class Fichier implements IFichier {
    /**
     * Le chemin du fichier.
     */
    private String cheminFichier;
    /**
     * Le nom du fichier.
     */
    private String nomFichier;
    /**
     * Le contenu du fichier.
     */
    private String contenuFichier;
    /**
     * L'extension du fichier.
     */
    private String extensionFichier;
    
    /**
     * Retourner le chemin du fichier.
     * @return Le chemin du fichier.
     */    
    @Override
    public String getCheminFichier() {
        return this.cheminFichier;
    }

    /**
     * Modifier le chemin du fichier.
     * @param cheminFichier Le nouveau chemin du fichier.
     */
    @Override
    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    /**
     * Retourner le nom du fichier.
     * @return Le nom du fichier.
     */
    @Override
    public String getNomFichier() {
        return this.nomFichier;
    }

    /**
     * Modifier le nom du fichier.
     * @param nomFichier Le nouveau nom du fichier.
     */
    @Override
    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    /**
     * Retourner le contenu du fichier.
     * @return Le contenu du fichier.
     */
    @Override
    public String getContenuFichier() {
        return this.contenuFichier;
    }

    /**
     * Modifier le contenu du fichier.
     * @param contenuFichier Le nouveau contenu du fichier.
     */
    @Override
    public void setContenuFichier(String contenuFichier) {
        this.contenuFichier = contenuFichier;
    }
    
    /**
     * Retourner l'extension du fichier.
     * @return L'extension du fichier.
     */
    @Override
    public String getExtensionFichier() {
        return this.extensionFichier;
    }
    
    /**
     * Modifier l'extension du fichier.
     * @param extensionFichier La nouvelle extension du fichier.
     */
    @Override
    public void setExtensionFichier(String extensionFichier) {
        if (!extensionFichier.isEmpty()) {
            if (!extensionFichier.contains(".")) {
                extensionFichier = "." + extensionFichier;
            }
        }
        this.extensionFichier = extensionFichier;
    }

    /**
     * Retourner un booléen indiquant si le fichier est un dossier.
     * @return Un booléen indiquant si le fichier est un dossier.
     */    
    @Override
    public boolean isDossier() {
        return false;
    }
    
    /**
     * Constructeur Fichier prenant en paramètre le chemin, le nom et l'extension du fichier.
     * @param cheminFichier Le chemin du fichier.
     * @param nomFichier L enom du fichier.
     * @param extensionFichier L'extension du fichier.
     */
    public Fichier(String cheminFichier, String nomFichier, String extensionFichier) {
        this.cheminFichier = cheminFichier;
        this.nomFichier = nomFichier;
        this.extensionFichier = extensionFichier;
        this.contenuFichier = "";
    }
    
    /**
     * Constructeur Fichier sans paramètres.
     */
    public Fichier() {
        this.cheminFichier = "";
        this.nomFichier = "";
        this.contenuFichier = "";
        this.extensionFichier = "";
    }

    /**
     * Retourner une chaine de caractère représentant un fichier.
     * @return la chaine de caractère représentant un fichier.
     */    
    @Override
    public String toString() {
        return this.nomFichier + this.extensionFichier;
    }

}
