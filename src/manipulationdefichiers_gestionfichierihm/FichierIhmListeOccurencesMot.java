/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_gestionfichierihm;

import java.util.ArrayList;

/**
 *
 * FichierIhmListeOccurencesMot est une classe représentant fichierIhm avec les fonctionnalités de contenir la liste des occurrences (couple position/nombre) d'un mot.
 * 
 * @author Pierre-Nicolas
 */
public class FichierIhmListeOccurencesMot {
    /**
     * Le fichierIhm décoré.
     */
    FichierIhm fichierIhm;
    /**
     * La liste des couples position/nombre de mots.
     */
    ArrayList<LigneNombreMotFichierIhm> ligneNombreMotFichierIhm;

    /**
     * Retourner le fichierIhm décoré.
     * @return Le fichierIhm décoré.
     */
    public FichierIhm getFichierIhm() {
        return this.fichierIhm;
    }

    /**
     * Retourner la liste des couples position/nombre de mots.
     * @return La liste des couples position/nombre de mots.
     */
    public ArrayList<LigneNombreMotFichierIhm> getLigneNombreMotFichierIhm() {
        return this.ligneNombreMotFichierIhm;
    }

    /**
     * Modifier la liste des couples position/nombre de mots.
     * @param ligneNombreMotFichierIhm La nouvelle liste des couples position/nombre de mots.
     */
    public void setLigneNombreMotFichierIhm(ArrayList<LigneNombreMotFichierIhm> ligneNombreMotFichierIhm) {
        this.ligneNombreMotFichierIhm = ligneNombreMotFichierIhm;
    }
    
    /**
     * Retourner une chaine de caractères représentant la liste des couple position/nombre de mots.
     * @return une chaine de caractères représentant la liste des couple position/nombre de mots.
     */
    public String getListeOccurencesString() {
        String res = "<br/>";
        for (LigneNombreMotFichierIhm ligneNombreMotFichierIhm : this.ligneNombreMotFichierIhm) {
            res += "A la ligne " + ligneNombreMotFichierIhm.getLignePostionMot() + " le mot apparait " + ligneNombreMotFichierIhm.getNombreMotsDansLaLigne() + " fois.<br/>";
        }
        return res;
    }
    
    /**
     * Constructeur FichierIhmListeOccurencesMot prenant en paramètres le fichierIhm et la liste des couples position/nombre de mots.
     * @param fichierIhm
     * @param ligneNombreMotFichierIhm 
     */
    public FichierIhmListeOccurencesMot(FichierIhm fichierIhm, ArrayList<LigneNombreMotFichierIhm> ligneNombreMotFichierIhm) {
        this.fichierIhm = fichierIhm;
        this.ligneNombreMotFichierIhm = ligneNombreMotFichierIhm;
    }
    
}
