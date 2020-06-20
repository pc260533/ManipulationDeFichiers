/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_metier;

import java.util.ArrayList;

/**
 *
 * Dossier est la classe représentant un dossier.
 * Elle hérite de Fichier et implémente IDossier.
 * 
 * @author Pierre-Nicolas
 */
public class Dossier extends Fichier implements IDossier {
    /**
     * La liste de fichiers contenus dans le dossier.
     */
    private ArrayList<Fichier> listeFichiers;

    /**
     * Retourne la liste de fichiers et de dossiers contenus dans le dossier.
     * @return La liste de fichiers et de dossiers contenus dans le dossier.
     */
    @Override
    public ArrayList<Fichier> getListeFichiers() {
        return this.listeFichiers;
    }
    
    /**
     * Retourner uniquement la liste de fichiers contenus dans le dossier.
     * @return La liste de fichiers contenus dans le dossier.
     */
    @Override
    public ArrayList<Fichier> getListeFichiersNonDossiers() {
        ArrayList<Fichier> res = new ArrayList();
        for (Fichier fichier : this.listeFichiers) {
            if (!fichier.isDossier()) {
                res.add(fichier);
            }
        }
        return res;
    }
    
    /**
     * Retourner uniquement la liste de dossiers contenus dans le dossier.
     * @return La liste de dossiers contenus dans le dossier.
     */    
    @Override
    public ArrayList<Dossier> getListeDossiers() {
        ArrayList<Dossier> res = new ArrayList();
        for (Fichier fichier : this.listeFichiers) {
            if (fichier.isDossier()) {
                res.add((Dossier)fichier);
            }
        }
        return res;
    }

    /**
     * Retourner un booléen indiquant si le fichier est un dossier.
     * @return Un booléen indiquant si le fichier est un dossier.
     */    
    @Override
    public boolean isDossier() {
        return true;
    }
    
    /**
     * Cosntructeur Dossier prenant en paramètre le chemin et le nom du dossier.
     * @param cheminFichier Le chemin du dossier.
     * @param nomFichier Le nom du dossier.
     */
    public Dossier(String cheminFichier, String nomFichier) {
        super(cheminFichier, nomFichier, "");
        this.listeFichiers = new ArrayList();
    }
    
    /**
     * Ajouter un fichier dans la liste de fichiers.
     * @param fichier Le fichier à ajouter.
     */    
    @Override
    public void ajouterFichier(Fichier fichier) {
        this.listeFichiers.add(fichier);
    }
    
    /**
     * Supprimer un fichier de la liste de fichiers.
     * @param fichier Le fichier à supprimer
     */    
    @Override
    public void supprimerFichier(Fichier fichier) {
        this.listeFichiers.remove(fichier);
    }
    
}
