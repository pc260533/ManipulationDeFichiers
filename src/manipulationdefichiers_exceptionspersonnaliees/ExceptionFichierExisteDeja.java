/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_exceptionspersonnaliees;

/**
 *
 * ExceptionFichierExisteDeja est la classe représentant une exception correspondant à l'ajout d'un fichier qui existe déjà.
 * Elle hérite de ExceptionPersonnalisee.
 * 
 * @author Pierre-Nicolas
 */
public class ExceptionFichierExisteDeja extends ExceptionPersonnalisee {
    
    /**
     * Constructeur ExceptionFichierExisteDeja prenant en paramètre l'exception précédente.
     * @param precedenteException L'exception précédente.
     */
    public ExceptionFichierExisteDeja(Throwable precedenteException) {
        super("Le fichier existe déjà.", "Fichier existe déjà", precedenteException);
    }
    
}
