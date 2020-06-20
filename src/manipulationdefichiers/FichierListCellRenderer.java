/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import manipulationdefichiers_gestionfichierihm.DossierIhm;
import manipulationdefichiers_gestionfichierihm.FichierIhm;

/**
 * 
 * FichierListCellRenderer est la classe représentant un renderer de JList qui affiche les fichierIhm et dossierIhm dans une JList.
 * Elle hérite de DefaultListCellRenderer.
 * 
 * @author Pierre-Nicolas
 */
public class FichierListCellRenderer extends DefaultListCellRenderer {

    /**
     * Retourne le composant modélisant un élément de la JList possédant ce renderer.
     * @param list La JList.
     * @param value La valeur retournée par list.getModel().getElementAt(index).
     * @param index L'index du composant dans la JList.
     * @param isSelected Un booléen indiquant si le composant est sélectionné.
     * @param cellHasFocus Un booléen indiquant si le composant a le focus.
     * @return Le composant de la JList.
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof FichierIhm) {
            FichierIhm fichierIhm = (FichierIhm) value;
            label.setIcon(fichierIhm.getIcone());
        }
        else if (value instanceof DossierIhm) {
            DossierIhm dossierIhm = (DossierIhm) value;
            label.setIcon(dossierIhm.getIcone());
        }
        label.setHorizontalTextPosition(JLabel.RIGHT);
        return label;
    }
}
