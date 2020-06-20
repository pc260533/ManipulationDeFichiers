/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_exceptionspersonnaliees;

/**
 *
 * ExceptionTailleDuFichierTropImportante est la classe représentant une exception correspondant au chargement d'un fichier d'une taille trop importante.
 * Elle hérite de ExceptionPersonnalisee.
 * 
 * @author Pierre-Nicolas
 */
public class ExceptionTailleDuFichierTropImportante extends ExceptionPersonnalisee {
    
    /**
     * Constructeur ExceptionTailleDuFichierTropImportante prenant en paramètre l'exception précédente.
     * @param precedenteException L'exception précédente.
     */     
    public ExceptionTailleDuFichierTropImportante(Throwable precedenteException) {
        super("La taille du fichier est trop importante pour être traitée.", "Taille de fichier trop importante.", precedenteException);
    }
    
}
