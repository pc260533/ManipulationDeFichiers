/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_exceptionspersonnaliees;

/**
 *
 * ExceptionAccesAuFichierRefuse est la classe représentant une exception correspondant à un refus d'accès au fichier.
 * Elle hérite de ExceptionPersonnalisee.
 * 
 * @author Pierre-Nicolas
 */
public class ExceptionAccesAuFichierRefuse extends ExceptionPersonnalisee {
    
    /**
     * Constructeur ExceptionAccesAuFichierRefuse prenant en paramètre l'exception précédente.
     * @param precedenteException L'exception précédente.
     */
    public ExceptionAccesAuFichierRefuse(Throwable precedenteException) {
        super("Vous ne pouvez pas accéder au fichier.", "Accès au fichier réfusé", precedenteException);
    }
    
}
