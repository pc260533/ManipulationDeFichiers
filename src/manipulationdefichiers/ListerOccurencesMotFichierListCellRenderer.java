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
import manipulationdefichiers_gestionfichierihm.FichierIhmListeOccurencesMot;

/**
 * 
 * ListerOccurencesMotFichierListCellRenderer est la classe représentant un renderer de JList qui affiche les FichierIhmListeOccurencesMot dans une JList.
 * Elle hérite de DefaultListCellRenderer.
 * 
 * @author Pierre-Nicolas
 */
public class ListerOccurencesMotFichierListCellRenderer extends DefaultListCellRenderer {
    
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
        if (value instanceof FichierIhmListeOccurencesMot) {
            FichierIhmListeOccurencesMot fichierIhmListeOccurencesMot = (FichierIhmListeOccurencesMot) value;
            label.setIcon(fichierIhmListeOccurencesMot.getFichierIhm().getIcone());
            label.setText("<html>" + fichierIhmListeOccurencesMot.getFichierIhm().getNomFichier() + fichierIhmListeOccurencesMot.getListeOccurencesString() + "</html>");
        }
        label.setHorizontalTextPosition(JLabel.RIGHT);
        return label;
    }
}
