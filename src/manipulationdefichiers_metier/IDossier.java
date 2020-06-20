/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_metier;

import java.util.ArrayList;

/**
 *
 * IDossier est une interface représentant les fonctionnalités d'un dossier.
 * Elle hérite de IFichier.
 * 
 * @author Pierre-Nicolas
 */
public interface IDossier extends IFichier {
    
    /**
     * Retourner uniquement la liste de dossiers contenus dans le dossier.
     * @return La liste de dossiers contenus dans le dossier.
     */
    ArrayList<Dossier> getListeDossiers();
    
    /**
     * Retourner la liste de fichiers et de dossiers contenus dans le dossier.
     * @return La liste de fichiers et de dossiers contenus dans le dossier.
     */
    ArrayList<Fichier> getListeFichiers();
    
    /**
     * Retourner uniquement la liste de fichiers contenus dans le dossier.
     * @return La liste de fichiers contenus dans le dossier.
     */
    ArrayList<Fichier> getListeFichiersNonDossiers();

    /**
     * Ajouter un fichier dans la liste de fichiers.
     * @param fichier Le fichier à ajouter.
     */
    void ajouterFichier(Fichier fichier);
    
    /**
     * Supprimer un fichier de la liste de fichiers.
     * @param fichier Le fichier à supprimer
     */
    void supprimerFichier(Fichier fichier);
    
}
