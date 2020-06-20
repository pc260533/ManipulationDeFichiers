/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_metier;

import manipulationdefichiers_exceptionspersonnaliees.ExceptionAccesAuFichierRefuse;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionFichierExisteDeja;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionFichierExistePas;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionFichierInchiffrable;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionFichierIndechiffrable;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionGeneraleChiffrement;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionGeneraleFichier;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionTailleDuFichierTropImportante;

/**
 *
 * IAdaptateurGestionFichiers est une interface représentant les fonctionnalités d'un adaptateur de gestion de fichiers.
 * 
 * @author Pierre-Nicolas
 */
public interface IAdaptateurGestionFichiers {
    
    /**
     * Charger les fichiers d'un dossier.
     * @param dossier Le dossier à charger.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     * @throws ExceptionTailleDuFichierTropImportante Taille du fichier trop importante.
     * @throws ExceptionFichierExistePas Chargement d'un fichier qui n'existe pas.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     */
    public void chargerFichersDansUnDossier(Dossier dossier) throws ExceptionAccesAuFichierRefuse, ExceptionTailleDuFichierTropImportante, ExceptionFichierExistePas, ExceptionGeneraleFichier;
    
    /**
     * Charger un fichier.
     * @param fichier Le fichier à charger.
     * @throws ExceptionTailleDuFichierTropImportante Taille du fichier trop importante.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     */
    public void chargerFichier(Fichier fichier) throws ExceptionTailleDuFichierTropImportante, ExceptionGeneraleFichier, ExceptionAccesAuFichierRefuse;
    
    /**
     * Ajouter un fichier.
     * @param fichier Le fichier à ajouter.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionFichierExisteDeja Ajout d'un fichier qui existe déjà.
     */
    public void ajouterFicher(Fichier fichier) throws ExceptionAccesAuFichierRefuse, ExceptionGeneraleFichier, ExceptionFichierExisteDeja;
    
    /**
     * Supprimer un fichier.
     * @param fichier L eficheir à supprimer.
     * @throws ExceptionFichierExistePas Chargement d'un fichier qui n'existe pas.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     */
    public void supprimerFichier(Fichier fichier) throws ExceptionFichierExistePas, ExceptionGeneraleFichier, ExceptionAccesAuFichierRefuse;
    
    /**
     * Modifier le contenu du fichier.
     * @param fichier Le fichier à modifier.
     * @param contenuFichier Le nouveau contenu du fichier.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     */
    public void modifierContenuFichier(Fichier fichier, String contenuFichier) throws ExceptionAccesAuFichierRefuse, ExceptionGeneraleFichier;
    
    /**
     * Modifier le nom et l'extension du fichier.
     * @param fichier Le ficher à modfier.
     * @param nomFichier Le nouveau avec la nouvelle extension du fichier.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionFichierExisteDeja Ajout d'un fichier qui existe déjà.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     */
    public void modifierNomFichier(Fichier fichier, String nomFichier) throws ExceptionGeneraleFichier, ExceptionFichierExisteDeja, ExceptionAccesAuFichierRefuse;
    
    /**
     * Modifier le chemin du fichier.
     * @param fichier Le fichier à modifier.
     * @param cheminFichier L enouveau chemin du fichier.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionFichierExisteDeja Ajout d'un fichier qui existe déjà.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     */
    public void modifierCheminFichier(Fichier fichier, String cheminFichier) throws ExceptionGeneraleFichier, ExceptionFichierExisteDeja, ExceptionAccesAuFichierRefuse;
    
    /**
     * Chiffrer le fichier dans le fichier passé en paramètre.
     * @param fichier Le fichier à chiffrer.
     * @param fichierAChiffre Le fichier chiffré.
     * @throws ExceptionGeneraleChiffrement Problèmes généraux du chiffrement.
     * @throws ExceptionFichierInchiffrable Fichier inchiffrable.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     * @throws ExceptionFichierExisteDeja Ajout d'un fichier qui existe déjà.
     */
    public void chiffrerFichier(Fichier fichier, Fichier fichierAChiffre) throws ExceptionGeneraleChiffrement, ExceptionFichierInchiffrable, ExceptionGeneraleFichier, ExceptionAccesAuFichierRefuse, ExceptionFichierExisteDeja;
    
    /**
     * Déchiffrer le fichier dans le fichier passé en paramètre.
     * @param fichier Le fichier à déchiffrer.
     * @param fichierADechiffre Le fichier déchiffré.
     * @throws ExceptionGeneraleChiffrement Problèmes généraux du chiffrement.
     * @throws ExceptionFichierIndechiffrable Fichier indéchiffrable.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     * @throws ExceptionFichierExisteDeja Ajout d'un fichier qui existe déjà.
     */
    public void dechiffrerFichier(Fichier fichier, Fichier fichierADechiffre) throws ExceptionGeneraleChiffrement, ExceptionFichierIndechiffrable, ExceptionGeneraleFichier, ExceptionAccesAuFichierRefuse, ExceptionFichierExisteDeja;
    
    /**
     * Retourner le chemin du parent du fichier.
     * @param fichier Le fichier.
     * @return Le chemin du parent du fichier.
     */
    public String getCheminParentFichier(Fichier fichier);
    
    /**
     * Concatener le chemin du dossier parent avec le chemin du dossier fichier.
     * @param dossierParent Le dossier parent.
     * @param fichier Le fichier
     * @return Le chemin du dossier parent concaténé avec le chemin du dossier fichier.
     */
    public String concatenerCheminParentAvecFichier(Dossier dossierParent, Fichier fichier);
    
    /**
     * Concatener le chemin du dossier parent avec le chemin du dossier fichier.
     * @param dossierParent Le chemin du dossier parent.
     * @param fichier Le chemin du fichier
     * @return Le chemin du dossier parent concaténé avec le chemin du dossier fichier.
     */
    public String concatenerCheminParentAvecFichier(String dossierParent, String fichier);
    
    /**
     * Retourner le nom et l'extension du fichier avec le chemin du fichier.
     * @param cheminFichier Le chemin du fichier.
     * @return Le nom et l'extension du fichier avec le chemin du fichier.
     */
    public String getNomExtensionFichierAvecChemin(String cheminFichier);
    
    /**
     * Retourner le nom du fichier avec le chemin du fichier.
     * @param cheminFichier Le chemin du fichier.
     * @return Le nom du fichier avec le chemin du fichier.
     */
    public String getNomFichierAvecChemin(String cheminFichier);
    
    /**
     * Retourner l'extension du fichier avec le chemin du fichier.
     * @param cheminFichier Le chemin du fichier.
     * @return L'extension du fichier avec le chemin du fichier.
     */    
    public String getExtensionFichierAvecChemin(String cheminFichier);
    
    /**
     * Retourner le nom du fichier avec le nom du fichier avec l'extension.
     * @param nomFichierAvecExtension Le nom du fichier avec l'extension.
     * @return Le nom du fichier.
     */
    public String getNomFichierAvecNomFichierAvecExtension(String nomFichierAvecExtension);
    
    /**
     * Retourner l'extension du fichier avec le nom du fichier avec l'extension.
     * @param nomFichierAvecExtension Le nom du fichier avec l'extension.
     * @return L'extension du fichier.
     */   
    public String getExtensionFichierAvecNomFichierAvecExtension(String nomFichierAvecExtension);
    
    /**
     * Retourner l'extension du fichier avec un point s'il n'y en a pas.
     * @param extensionFichier L'extension du fichier.
     * @return L'extension du fichier avec un point s'il n'y en a pas.
     */  
    public String getExtensionCorrect(String extensionFichier);
    
}
