/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_gestionfichierihm;

import javax.swing.ImageIcon;

/**
 *
 * IFichierDossierIhm est une interface représentant les fonctionnalités d'un fichier ihm ou d'un dossier ihm.
 * 
 * @author Pierre-Nicolas
 */
public interface IFichierDossierIhm {
    
    /**
     * Retourner l'icone représentant le fichier ou le dossier pour l'ihm.
     * @return L'icone représentant le fichier ou le dossier pour l'ihm.
     */
    ImageIcon getIcone();
    
}
