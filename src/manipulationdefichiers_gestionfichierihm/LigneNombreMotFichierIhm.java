/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_gestionfichierihm;

/**
 *
 * LigneNombreMotFichierIhm est une classe représentant une occurrence (couple position/nombre) d'un mot.
 * 
 * @author Pierre-Nicolas
 */
public class LigneNombreMotFichierIhm {
    /**
     * La ligne où se situe le mot dans le fichierIhm.
     */
    private int lignePostionMot;
    /**
     * Le nombre de mots présent dans la ligne du fichierIhm.
     */
    private int nombreMotsDansLaLigne;

    /**
     * Retourner la ligne où se situe le mot dans le fichierIhm.
     * @return La ligne où se situe le mot dans le fichierIhm.
     */
    public int getLignePostionMot() {
        return this.lignePostionMot;
    }

    /**
     * Modifier la ligne où se situe le mot dans le fichierIhm.
     * @param lignePostionMot La nouvelle ligne où se situe le mot dans le fichierIhm.
     */
    public void setLignePostionMot(int lignePostionMot) {
        this.lignePostionMot = lignePostionMot;
    }
    
    /**
     * Retourner le nombre de mots présent dans la ligne du fichierIhm.
     * @return Le nombre de mots présent dans la ligne du fichierIhm.
     */
    public int getNombreMotsDansLaLigne() {
        return this.nombreMotsDansLaLigne;
    }

    /**
     * Modifier le nombre de mots présent dans la ligne du fichierIhm.
     * @param nombreMotsDansLaLigne Le nouveau nombre de mots présent dans la ligne du fichierIhm.
     */
    public void setNombreMotsDansLaLigne(int nombreMotsDansLaLigne) {
        this.nombreMotsDansLaLigne = nombreMotsDansLaLigne;
    }
    
    /**
     * Constructeur LigneNombreMotFichierIhm prenant en paramètres la ligne où se situe le mot et le nombre de mots présent dans la ligne du fichierIhm.
     * @param lignePostionMot La ligne où se situe le mot dans le fichierIhm.
     * @param nombreMotsDansLaLigne Le nombre de mots présent dans la ligne du fichierIhm.
     */
    public LigneNombreMotFichierIhm(int lignePostionMot, int nombreMotsDansLaLigne) {
        this.lignePostionMot = lignePostionMot;
        this.nombreMotsDansLaLigne = nombreMotsDansLaLigne;
    }
    
}
