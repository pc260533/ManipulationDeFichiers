/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_exceptionspersonnaliees;

/**
 *
 * ExceptionFichierExistePas est la classe représentant une exception correspondant à l'accès à un fichier qui n'existe pas.
 * Elle hérite de ExceptionPersonnalisee.
 * 
 * @author Pierre-Nicolas
 */
public class ExceptionFichierExistePas extends ExceptionPersonnalisee {
    
    /**
     * Constructeur ExceptionFichierExistePas prenant en paramètre l'exception précédente.
     * @param precedenteException L'exception précédente.
     */    
    public ExceptionFichierExistePas(Throwable precedenteException) {
        super("Le fichier n'existe pas.", "Fichier n'existe pas.", precedenteException);
    }
    
}
