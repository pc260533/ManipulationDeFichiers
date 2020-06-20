/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_exceptionspersonnaliees;

/**
 *
 * ExceptionGeneraleFichier est la classe représentant une exception correspondant à une erreur général de la gestion des fichiers.
 * Elle hérite de ExceptionPersonnalisee.
 * 
 * @author Pierre-Nicolas
 */
public class ExceptionGeneraleFichier extends ExceptionPersonnalisee {
    
    /**
     * Constructeur ExceptionGeneraleFichier prenant en paramètre l'exception précédente.
     * @param precedenteException L'exception précédente.
     */     
    public ExceptionGeneraleFichier(Throwable precedenteException) {
        super("Une erreur générale de gestion des fichiers s'est produite.", "Exception générale de gestion des fichiers.", precedenteException);
    }
    
}
