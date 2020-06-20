/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_exceptionspersonnaliees;

/**
 *
 * ExceptionFichierIndechiffrable est la classe représentant une exception correspondant au déchiffrement d'un fichier non chiffré.
 * Elle hérite de ExceptionPersonnalisee.
 * 
 * @author Pierre-Nicolas
 */
public class ExceptionFichierInchiffrable extends ExceptionPersonnalisee {
    
    /**
     * Constructeur ExceptionFichierIndechiffrable prenant en paramètre l'exception précédente.
     * @param precedenteException L'exception précédente.
     */     
    public ExceptionFichierInchiffrable(Throwable precedenteException) {
        super("Le fichier ne peut pas être chiffrer par l'algorithme de chiffrement.", "Fichier indéchiffrable.", precedenteException);
    }
    
}

