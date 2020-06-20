/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_exceptionspersonnaliees;

/**
 *
 * ExceptionGeneraleChiffrement est la classe représentant une exception correspondant à une erreur général du chiffrement.
 * Elle hérite de ExceptionPersonnalisee.
 * 
 * @author Pierre-Nicolas
 */
public class ExceptionGeneraleChiffrement extends ExceptionPersonnalisee {
    
    /**
     * Constructeur ExceptionGeneraleChiffrement prenant en paramètre l'exception précédente.
     * @param precedenteException L'exception précédente.
     */     
    public ExceptionGeneraleChiffrement(Throwable precedenteException) {
        super("Une erreur générale de chiffrement s'est produite.", "Erreue générale de chiffrement.", precedenteException);
    }
    
}
