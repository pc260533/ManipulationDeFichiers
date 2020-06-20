/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_metier;

import manipulationdefichiers_exceptionspersonnaliees.ExceptionPersonnalisee;

/**
 *
 * IVue est une interface représentant les fonctionnalités d'une vue.
 * 
 * @author Pierre-Nicolas
 */
public interface IVue {
    
    /**
     * Action à exécuter après l'ajout d'un fichier.
     * @param fichier Le fichier ajouté.
     */
    void ajoutFichier(Fichier fichier);
    
    /**
     * Action à exécuter après la suppression d'un fichier.
     * @param fichier Le fichier supprimé.
     */
    void suppressionFichier(Fichier fichier);
    
    /**
     * Action à exécuter après la modification du nom et de l'extensinon d'un fichier.
     * @param fichier Le fichier modifié.
     */
    void modificationNomExtensionFichier(Fichier fichier);
    
    /**
     * Action à exécuter après la modification du chemin d'un fichier.
     * @param fichier Le fichier modifié.
     */
    void modificationCheminFichier(Fichier fichier);
        
    /**
     * Action à exécuter après la modification du contenu d'un fichier.
     * @param fichier Le fichier modifié.
     */
    void modificationContenuFichier(Fichier fichier);
    
    /**
     * Action à exécuter après qu'une exception personnalisée a été throw.
     * @param exceptionPersonnalisee l'exception personnalisée throw.
     */
    void afficherException(ExceptionPersonnalisee exceptionPersonnalisee);
    
    /**
     * Action à exécuter avant un traitement long.
     */
    void affichageAvantTraitementLong();
    
    /**
     * Action à exécuter après un traitement long.
     */
    void affichageApresTraitementLong();
    
}
