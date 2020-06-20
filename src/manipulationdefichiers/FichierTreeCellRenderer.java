/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers;

import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import manipulationdefichiers_gestionfichierihm.DossierIhm;
import manipulationdefichiers_gestionfichierihm.FichierIhm;

/**
 * 
 * FichierTreeCellRenderer est la classe représentant un renderer de JTree qui affiche les fichierIhm et dossierIhm dans un JTree.
 * Elle hérite de DefaultTreeCellRenderer.
 * 
 * @author Pierre-Nicolas
 */
public class FichierTreeCellRenderer extends DefaultTreeCellRenderer {
    
    /**
     * Retourne le composant modélisant un élément du JTree possédant ce renderer.
     * @param tree Le JTree.
     * @param value La valeur retournée par le model de JTree à l'index.
     * @param sel Un booléen indiquant si le composant est sélectionné.
     * @param expanded Un booléen indiquant si le composant est étendu.
     * @param leaf Un booléen indiquant si le composant est une feuille du JTree.
     * @param row Un booléen indiquant la ligne du composant dans le JTree.
     * @param hasFocus Un booléen indiquant si le composant a le focus.
     * @return Le composant du JTree.
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object userObject = node.getUserObject();
            if (userObject instanceof FichierIhm) {
                FichierIhm fichierIhm = (FichierIhm) userObject;
                label.setIcon(new ImageIcon(fichierIhm.getIcone().getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
            }
            else if (userObject instanceof DossierIhm) {
                DossierIhm dossierIhm = (DossierIhm) userObject;
                if (expanded) {
                    label.setIcon(new ImageIcon(dossierIhm.getIconeDossierOuvert().getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
                }
                else {
                    label.setIcon(new ImageIcon(dossierIhm.getIconeDossierFerme().getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
                }
            }
            else {
                label.setIcon(null);
            }
        }
        return label;
    }
}
