/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_metier;

/**
 *
 * IFichier est une interface représentant les fonctionnalités d'un fichier.
 * 
 * @author Pierre-Nicolas
 */
public interface IFichier {

    /**
     * Retourner le chemin du fichier.
     * @return Le chemin du fichier.
     */
    String getCheminFichier();

    /**
     * Retourner le contenu du fichier.
     * @return Le contenu du fichier.
     */
    String getContenuFichier();

    /**
     * Retourner le nom du fichier.
     * @return Le nom du fichier.
     */
    String getNomFichier();
    
    /**
     * Retourner l'extension du fichier.
     * @return L'extension du fichier.
     */
    String getExtensionFichier();

    /**
     * Retourner un booléen indiquant si le fichier est un dossier.
     * @return Un booléen indiquant si le fichier est un dossier.
     */
    boolean isDossier();

    /**
     * Modifier le chemin du fichier.
     * @param cheminFichier Le nouveau chemin du fichier.
     */
    void setCheminFichier(String cheminFichier);

    /**
     * Modifier le contenu du fichier.
     * @param contenuFichier Le nouveau contenu du fichier.
     */
    void setContenuFichier(String contenuFichier);

    /**
     * Modifier le nom du fichier.
     * @param nomFichier Le nouveau nom du fichier.
     */
    void setNomFichier(String nomFichier);
    
    /**
     * Modifier l'extension du fichier.
     * @param extensionFichier La nouvelle extension du fichier.
     */
    void setExtensionFichier(String extensionFichier);

    /**
     * Retourner une chaine de caractère représentant un fichier.
     * @return la chaine de caractère représentant un fichier.
     */
    String toString();
    
}
