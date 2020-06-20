/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionAccesAuFichierRefuse;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionFichierExistePas;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionGeneraleFichier;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionPersonnalisee;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionTailleDuFichierTropImportante;
import manipulationdefichiers_gestionfichierihm.DossierIhm;
import manipulationdefichiers_gestionfichierihm.FichierIhm;
import manipulationdefichiers_gestionfichierihm.FichierIhmListeOccurencesMot;
import manipulationdefichiers_gestionfichierihm.LigneNombreMotFichierIhm;
import manipulationdefichiers_gestionfichiers.AdaptateurGestionFichiers;
import manipulationdefichiers_metier.IAdaptateurGestionFichiers;
import manipulationdefichiers_metier.Fichier;
import manipulationdefichiers_metier.Dossier;
import manipulationdefichiers_metier.IVue;

/**
 * 
 * MainWindow est la classe représentant la JFrame principale de l'application ManipulationDeFichiers.
 * Elle hérite de JFrame et implémente l'interface IVue.
 * 
 * @author Pierre-Nicolas
 */
public class MainWindow extends javax.swing.JFrame implements IVue {
    /**
     * Le dossierIhm contenant le répertoire courant.
     */
    private DossierIhm repertoireCourant;
    /**
     * Le controleur.
     */
    private Controleur controleur;
    /**
     * Le iadaptateurGestionFichiers.
     */
    private IAdaptateurGestionFichiers iadaptateurGestionFichiers;
    /**
     * La fenêtre d'attente affichée pendant un traitement long.
     */
    private WaitWindow waitWindow;
    /**
     * Le dossierIhm contenant le dossier sélectionnée sur lequel on applique les foncitonnalités de l'application.
     */
    private DossierIhm dossierSelectionne;
    /**
     * Le noeud du JTree contenant le dossier sélectionnée sur lequel on applique les foncitonnalités de l'application.
     */
    private DefaultMutableTreeNode nodeDossierSelectionne;
    
    /**
     * Le model de JTree contenant le répertoire courant.
     */
    private DefaultTreeModel modelJtree;
    
    /**
     * Action à exécuter après l'ajout d'un fichier.
     * @param fichier Le fichier ajouté.
     */
    @Override
    public void ajoutFichier(Fichier fichier) {
        FichierIhm fichierIhmAAjouter = new FichierIhm(fichier);
        DefaultMutableTreeNode nodeAJouter = new DefaultMutableTreeNode(fichierIhmAAjouter);
        this.modelJtree.insertNodeInto(nodeAJouter, this.nodeDossierSelectionne, this.nodeDossierSelectionne.getChildCount());
        DefaultListModel listModel = (DefaultListModel) this.listeFichiersPanel.getjListListeFichiersDossierSelectionne().getModel();
        listModel.addElement(fichierIhmAAjouter);
        this.afficherPanel(this.listeFichiersPanel);
        this.activerLesBoutons();
    }
    
    /**
     * Action à exécuter après la suppression d'un fichier.
     * @param fichier Le fichier supprimé.
     */    
    @Override
    public void suppressionFichier(Fichier fichier) {
        DefaultMutableTreeNode nodeASupprimer = this.getNodeDuDossierSelectionneAvecFichierIhm(new FichierIhm(fichier));
        if (nodeASupprimer != null) {
            this.modelJtree.removeNodeFromParent(nodeASupprimer);
        }
        DefaultListModel listModel = (DefaultListModel) this.listeFichiersPanel.getjListListeFichiersDossierSelectionne().getModel();
        
        FichierIhm fichierIhmASupprimer = null;
        
        for (int i = 0; i < listModel.getSize(); i++) {
            Object item = listModel.getElementAt(i);
            if (item instanceof FichierIhm) {
                FichierIhm fichierIhm = (FichierIhm) item;
                if (fichierIhm.getFichier() == fichier) {
                    fichierIhmASupprimer = fichierIhm;
                }
            }
        }
        listModel.removeElement(fichierIhmASupprimer);
    }
    
    /**
     * Action à exécuter après la modification du nom et de l'extensinon d'un fichier.
     * @param fichier Le fichier modifié.
     */
    @Override
    public void modificationNomExtensionFichier(Fichier fichier) {
        this.modelJtree.reload();
        this.afficherPanel(this.listeFichiersPanel);
        this.activerLesBoutons();
    }
    
    /**
     * Action à exécuter après la modification du chemin d'un fichier.
     * @param fichier Le fichier modifié.
     */
    @Override
    public void modificationCheminFichier(Fichier fichier) {
        this.modelJtree.reload();
        this.afficherPanel(this.listeFichiersPanel);
        this.activerLesBoutons();
    }
        
    /**
     * Action à exécuter après la modification du contenu d'un fichier.
     * @param fichier Le fichier modifié.
     */
    @Override
    public void modificationContenuFichier(Fichier fichier) {
        this.modelJtree.reload();
        this.afficherPanel(this.listeFichiersPanel);
        this.activerLesBoutons();
    }
    
    /**
     * Action à exécuter après qu'une exception personnalisée a été throw.
     * @param exceptionPersonnalisee l'exception personnalisée throw.
     */    
    @Override
    public void afficherException(ExceptionPersonnalisee exceptionPersonnalisee) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(this,
                exceptionPersonnalisee.getMessage() + "\n" + exceptionPersonnalisee.getStacktrace(),
                exceptionPersonnalisee.getTitre(),
                JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Action à exécuter avant un traitement long.
     */    
    @Override
    public void affichageAvantTraitementLong() {
        this.waitWindow.setVisible(true);
        this.setEnabled(false);
    }
    
    /**
     * Action à exécuter après un traitement long.
     */
    @Override
    public void affichageApresTraitementLong() {
        this.setEnabled(true);
        this.waitWindow.setVisible(false);
    }
    
    /**
     * Remplir le JTree avec les fichiersIhm et dossiersIhm d'un dossier racine à partir d'un noeud racine.
     * @param node Le noeud racine.
     * @param dossierIhm Le dossierIhm racine.
     */
    private void ajouterDossierDansJTree(DefaultMutableTreeNode node, DossierIhm dossierIhm) {
        for (Fichier fichier : dossierIhm.getListeFichiers()) {
            if (fichier.isDossier()) {
                DossierIhm dossier = new DossierIhm((Dossier)fichier);
                DefaultMutableTreeNode nodeDossier = new DefaultMutableTreeNode(dossier);
                this.modelJtree.insertNodeInto(nodeDossier, node, node.getChildCount());
                this.ajouterDossierDansJTree(nodeDossier, dossier);
            }
            else {
                DefaultMutableTreeNode nodeFichier = new DefaultMutableTreeNode(new FichierIhm(fichier));
                this.modelJtree.insertNodeInto(nodeFichier, node, node.getChildCount());
            }
        }
    }
    
    /**
     * Afficher les fichiersIhm et les dossiersIhm contenus dans le dossierIhm du répertoire courant dans le JTree.
     */
    private void afficherRepertoireCourant() {
        DefaultMutableTreeNode nodeRepertoire = new DefaultMutableTreeNode(this.repertoireCourant);
        this.modelJtree = new DefaultTreeModel(nodeRepertoire);
        this.ajouterDossierDansJTree(nodeRepertoire, this.repertoireCourant);
        this.jTreeFichiersRepertoireCourant.setModel(this.modelJtree);
    }
    
    /**
     * Afficher les fichiersIhm contenus dans le dossierIhm sélectionné parmi les dossiersIhm du répertoire courant dans la JList du panel listeFichiersPanel.
     */
    private void afficherDossierSelectionne() {
        DefaultListModel listModel = (DefaultListModel) this.listeFichiersPanel.getjListListeFichiersDossierSelectionne().getModel();
        listModel.removeAllElements();
        for(Fichier fichier : this.dossierSelectionne.getListeFichiersNonDossiers()){
            listModel.addElement(new FichierIhm(fichier));
        }
        this.jLabelNomDossierSelectionné.setText(this.dossierSelectionne.getNomFichier());
    }
    
    /**
     * Afficher la liste des fichiersIhm sélectionnés depuis la JListe du panel listeFichiersPanel dans la JList du panel listeFichiersAConcatenerPanel.
     * @param listeFichiersIhmSelectionnes La liste des fichiersIhm sélectionnés depuis la JListe du panel listeFichiersPanel.
     */
    private void afficherListeFichiersIhmAConcatener(ArrayList<FichierIhm> listeFichiersIhmSelectionnes) {
        DefaultListModel<FichierIhm> listModel = (DefaultListModel<FichierIhm>) this.listeFichiersAConcatenerPanel.getjListListeFichiersDossierSelectionne().getModel();
        listModel.removeAllElements();
        for(FichierIhm fichierIhm : listeFichiersIhmSelectionnes){
            listModel.addElement(fichierIhm);
        }
    }
    
    /**
     * Afficher la liste des fichiersIhm sélectionnés depuis la JListe du panel listeFichiersPanel dans la JList du panel listerOccurencesMotPanel.
     * @param listeFichiersIhm La liste des fichiersIhm sélectionnés depuis la JListe du panel listeFichiersPanel.
     */
    private void afficherListeFichiersIhmALister(ArrayList<FichierIhm> listeFichiersIhm) {
        DefaultListModel<FichierIhmListeOccurencesMot> listModel = (DefaultListModel<FichierIhmListeOccurencesMot>) this.listerOccurencesMotPanel.getjListListeFichiersALister().getModel();
        listModel.removeAllElements();
        for(FichierIhm fichierIhm : listeFichiersIhm){
            listModel.addElement(new FichierIhmListeOccurencesMot(fichierIhm, new ArrayList()));
        }
    }
    
    /**
     * Afficher le panel.
     * @param jPanel Le panel à afficher
     */
    private void afficherPanel(JPanel jPanel) {
        this.jLayeredPane.removeAll();
        this.jLayeredPane.add(jPanel);
        this.jLayeredPane.repaint();
        this.jLayeredPane.revalidate();
    }
    
    /**
     * Activer tous les boutons de fonctionnalités.
     */
    private void activerLesBoutons() {
        this.jButtonAjouterFichier.setEnabled(true);
        this.jButtonModifierFichier.setEnabled(true);
        this.jButtonSupprimerFichier.setEnabled(true);
        this.jButtonDupliquerFichier.setEnabled(true);
        this.jButtonChiffrerFichier.setEnabled(true);
        this.jButtonChiffrerFichier.setEnabled(true);
        this.jButtonDechiffrerFichier.setEnabled(true);
        this.jButtonConcatenerFichier.setEnabled(true);
        this.jButtonListerOccurenceMotFichier.setEnabled(true);
    }
    
    /**
     * Désactiver tous les boutons de fonctionnalités.
     */
    private void desactiverLesBoutons() {
        this.jButtonAjouterFichier.setEnabled(false);
        this.jButtonModifierFichier.setEnabled(false);
        this.jButtonSupprimerFichier.setEnabled(false);
        this.jButtonDupliquerFichier.setEnabled(false);
        this.jButtonChiffrerFichier.setEnabled(false);
        this.jButtonChiffrerFichier.setEnabled(false);
        this.jButtonDechiffrerFichier.setEnabled(false);
        this.jButtonConcatenerFichier.setEnabled(false);
        this.jButtonListerOccurenceMotFichier.setEnabled(false);
    }
    
    /**
     * Retourner le noeud du Jtree correspondant au fichierIhm.
     * @param fichierIhm Le fichierIhm.
     * @return Le noeud du JTree correspondant.
     */
    private DefaultMutableTreeNode getNodeDuDossierSelectionneAvecFichierIhm(FichierIhm fichierIhm) {
        DefaultMutableTreeNode res = null;
        for (int i = 0; i < this.nodeDossierSelectionne.getChildCount(); i++) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.nodeDossierSelectionne.getChildAt(i);
            if (node.getUserObject() instanceof FichierIhm) {
                FichierIhm fichierIhmSelectionne = (FichierIhm) node.getUserObject();
                if (fichierIhmSelectionne.getFichier() == fichierIhm.getFichier()) {
                    res = node;
                }
            }
        }
        return res;
    }
    
    /**
     * Retourner le fichierIhm sélectionné dans la JList du panel listeFichiersPanel.
     * @return Le fichierIhm sélectionné dans la JList du panel listeFichiersPanel.
     */
    private FichierIhm getFichierIhmSelectionne() {
        return this.listeFichiersPanel.getjListListeFichiersDossierSelectionne().getSelectedValue();
    }
    
    /**
     * Retourner la liste des fichiersIhm sélectionnés dans la JList du panel listeFichiersPanel.
     * @return La liste des fichiersIhm sélectionnés dans la JList du panel listeFichiersPanel.
     */
    private ArrayList<FichierIhm> getListeFichiersIhmSelectionnes() {
        return new ArrayList(this.listeFichiersPanel.getjListListeFichiersDossierSelectionne().getSelectedValuesList());
    }
    
    /**
     * Retourner la liste de tous les fichiersIhm de la JList du panel listeFichiersPanel.
     * @return La liste de tous les fichiersIhm de la JList du panel listeFichiersPanel.
     */
    private ArrayList<FichierIhm> getListeFichiersIhmDossierSelectionnes() {
        ArrayList<FichierIhm> res = new ArrayList();
        for (int i = 0; i < this.listeFichiersPanel.getjListListeFichiersDossierSelectionne().getModel().getSize(); i++) {
            res.add(this.listeFichiersPanel.getjListListeFichiersDossierSelectionne().getModel().getElementAt(i));
        }
        return res;
    }
    
    /**
     * Initialiser les actions réalisés au clic sur les boutons de l'application.
     */
    private void initialiserEvenementsPanels() {
        this.listeFichiersAConcatenerPanel.getjButtonHaut().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FichierIhm fichierIhmSelectionne = listeFichiersAConcatenerPanel.getjListListeFichiersDossierSelectionne().getSelectedValue();
                int fichierIhmSelectionneIndex = listeFichiersAConcatenerPanel.getjListListeFichiersDossierSelectionne().getSelectedIndex();
                if (fichierIhmSelectionneIndex > 0) {
                    DefaultListModel<FichierIhm> listModel = (DefaultListModel<FichierIhm>) listeFichiersAConcatenerPanel.getjListListeFichiersDossierSelectionne().getModel();
                    listModel.removeElementAt(fichierIhmSelectionneIndex);
                    listModel.add(fichierIhmSelectionneIndex - 1, fichierIhmSelectionne);
                }
            }
        });
        
        this.listeFichiersAConcatenerPanel.getjButtonBas().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FichierIhm fichierIhmSelectionne = listeFichiersAConcatenerPanel.getjListListeFichiersDossierSelectionne().getSelectedValue();
                int fichierIhmSelectionneIndex = listeFichiersAConcatenerPanel.getjListListeFichiersDossierSelectionne().getSelectedIndex();
                DefaultListModel<FichierIhm> listModel = (DefaultListModel<FichierIhm>) listeFichiersAConcatenerPanel.getjListListeFichiersDossierSelectionne().getModel();
                if (fichierIhmSelectionneIndex < listModel.getSize() - 1) {
                    listModel.removeElementAt(fichierIhmSelectionneIndex);
                    listModel.add(fichierIhmSelectionneIndex + 1, fichierIhmSelectionne);
                }
            }
        });
        
        this.listeFichiersAConcatenerPanel.getjButtonAjouterFichierExterieur().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFileChooser fileChooseer = new JFileChooser(); 
                fileChooseer.setCurrentDirectory(new java.io.File("."));
                fileChooseer.setDialogTitle("Choisir des fichiers à concaténer avec les fichiers du dossier sélectionné");
                fileChooseer.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooseer.setMultiSelectionEnabled(true);
                if (fileChooseer.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                    File[] files = fileChooseer.getSelectedFiles();
                    for (File file : files) {
                        Fichier fichier = MainWindow.this.controleur.chargerFichier(file.getAbsolutePath());
                        if (fichier != null) {
                            FichierIhm fichierIhm = new FichierIhm(fichier);
                            DefaultListModel<FichierIhm> listModel = (DefaultListModel<FichierIhm>) MainWindow.this.listeFichiersAConcatenerPanel.getjListListeFichiersDossierSelectionne().getModel();
                            listModel.addElement(fichierIhm);
                        }
                    }
                }                
            }
        });
        
        this.listeFichiersAConcatenerPanel.getjButtonConcatener().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArrayList<Fichier> listeFichierAConcatener = new ArrayList();
                for (int i = 0; i < listeFichiersAConcatenerPanel.getjListListeFichiersDossierSelectionne().getModel().getSize(); i++) {
                    listeFichierAConcatener.add(listeFichiersAConcatenerPanel.getjListListeFichiersDossierSelectionne().getModel().getElementAt(i).getFichier());
                }
                controleur.concatenerListeFichier(listeFichierAConcatener, dossierSelectionne.getDossier(), listeFichiersAConcatenerPanel.getjTextFieldNomFichierConcatene().getText());
            }
        });
        
        this.listerOccurencesMotPanel.getjButtonLister().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String mot = listerOccurencesMotPanel.getjTextFieldListerFichier().getText();
                DefaultListModel<FichierIhmListeOccurencesMot> listModel = (DefaultListModel<FichierIhmListeOccurencesMot>) listerOccurencesMotPanel.getjListListeFichiersALister().getModel();
                if (!mot.isEmpty()) {
                    for (int i = 0; i < listerOccurencesMotPanel.getjListListeFichiersALister().getModel().getSize(); i++) {
                        FichierIhmListeOccurencesMot fichierIhmALister = listerOccurencesMotPanel.getjListListeFichiersALister().getModel().getElementAt(i);
                        ArrayList<LigneNombreMotFichierIhm> listeLigneNombreMotFichierIhm = controleur.listerOccurencesMot(fichierIhmALister.getFichierIhm().getFichier(), mot);
                        fichierIhmALister.setLigneNombreMotFichierIhm(listeLigneNombreMotFichierIhm);
                        listerOccurencesMotPanel.getjListListeFichiersALister().updateUI();
                    }
                }
                else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(MainWindow.this,
                            "Le mot de filtrage permettant de connaitre le nombre d'occurrence de ce mot par ligne de chaque fichier est vide.",
                            "Champ lister fichier vide.",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    /**
     * Constructeur MainWindow sans paramètres.
     */
    public MainWindow() {
        try {
            this.initComponents();
            this.setLocationRelativeTo(null);
            this.setTitle("Manipulation De Fichier");
            this.setIconImage(ImageIO.read(this.getClass().getResource("/manipulationdefichiers_images/icone.png")));
            
            this.iadaptateurGestionFichiers = new AdaptateurGestionFichiers();
            
            this.waitWindow = new WaitWindow(this, false);
            this.waitWindow.setLocationRelativeTo(this);
            
            this.controleur = new Controleur(this.iadaptateurGestionFichiers);
            this.controleur.inscrireVue(this);
            
            this.modelJtree = new DefaultTreeModel(new DefaultMutableTreeNode("Sélectionner un répertoire courant"));
            this.jTreeFichiersRepertoireCourant.setModel(this.modelJtree);
            
            this.jTreeFichiersRepertoireCourant.setCellRenderer(new FichierTreeCellRenderer());
            this.listeFichiersPanel.getjListListeFichiersDossierSelectionne().setCellRenderer(new FichierListCellRenderer());
            this.listeFichiersAConcatenerPanel.getjListListeFichiersDossierSelectionne().setCellRenderer(new FichierListCellRenderer());
            this.listerOccurencesMotPanel.getjListListeFichiersALister().setCellRenderer(new ListerOccurencesMotFichierListCellRenderer());
            
            this.afficherPanel(this.listeFichiersPanel);
            this.desactiverLesBoutons();
            
            this.initialiserEvenementsPanels();
        }
        catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeFichiersRepertoireCourant = new javax.swing.JTree();
        jLayeredPane = new javax.swing.JLayeredPane();
        listeFichiersPanel = new manipulationdefichiers.ListeFichiersPanel();
        listeFichiersAConcatenerPanel = new manipulationdefichiers.ListeFichiersAConcatenerPanel();
        editeFichierPanel = new manipulationdefichiers.EditeFichierPanel();
        listerOccurencesMotPanel = new manipulationdefichiers.ListerOccurencesMotPanel();
        jPanelChangementRepertoireCourant = new javax.swing.JPanel();
        jTextFieldCheminRepertoireCourant = new javax.swing.JTextField();
        jButtonParcourir = new javax.swing.JButton();
        jPanelFonctionnalites = new javax.swing.JPanel();
        jButtonAjouterFichier = new javax.swing.JButton();
        jButtonModifierFichier = new javax.swing.JButton();
        jButtonSupprimerFichier = new javax.swing.JButton();
        jButtonDupliquerFichier = new javax.swing.JButton();
        jButtonChiffrerFichier = new javax.swing.JButton();
        jButtonDechiffrerFichier = new javax.swing.JButton();
        jButtonConcatenerFichier = new javax.swing.JButton();
        jButtonListerOccurenceMotFichier = new javax.swing.JButton();
        jLabelNomDossierSelectionné = new javax.swing.JLabel();
        jLabelDossierSelectionne = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1250, 800));
        setPreferredSize(new java.awt.Dimension(1250, 800));
        setResizable(false);
        setSize(new java.awt.Dimension(1250, 800));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTreeFichiersRepertoireCourant.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTreeFichiersRepertoireCourant.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTreeFichiersRepertoireCourantMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeFichiersRepertoireCourant);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 270, 348));

        jLayeredPane.setMinimumSize(new java.awt.Dimension(750, 450));
        jLayeredPane.setLayout(new java.awt.CardLayout());
        jLayeredPane.add(listeFichiersPanel, "card2");
        jLayeredPane.add(listeFichiersAConcatenerPanel, "card4");
        jLayeredPane.add(editeFichierPanel, "card3");
        jLayeredPane.add(listerOccurencesMotPanel, "card5");

        getContentPane().add(jLayeredPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, 830, 550));
        jLayeredPane.getAccessibleContext().setAccessibleDescription("");

        jPanelChangementRepertoireCourant.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextFieldCheminRepertoireCourant.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextFieldCheminRepertoireCourant.setEnabled(false);
        jPanelChangementRepertoireCourant.add(jTextFieldCheminRepertoireCourant, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 580, 30));

        jButtonParcourir.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonParcourir.setText("Parcourir");
        jButtonParcourir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonParcourirActionPerformed(evt);
            }
        });
        jPanelChangementRepertoireCourant.add(jButtonParcourir, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 120, 30));

        getContentPane().add(jPanelChangementRepertoireCourant, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 730, 50));

        jPanelFonctionnalites.setLayout(new java.awt.GridLayout(8, 1, 0, 5));

        jButtonAjouterFichier.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonAjouterFichier.setText("Ajouter Fichier");
        jButtonAjouterFichier.setMaximumSize(new java.awt.Dimension(150, 25));
        jButtonAjouterFichier.setMinimumSize(new java.awt.Dimension(150, 25));
        jButtonAjouterFichier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAjouterFichierActionPerformed(evt);
            }
        });
        jPanelFonctionnalites.add(jButtonAjouterFichier);

        jButtonModifierFichier.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonModifierFichier.setText("Modifier Fichier");
        jButtonModifierFichier.setMaximumSize(new java.awt.Dimension(150, 25));
        jButtonModifierFichier.setMinimumSize(new java.awt.Dimension(150, 25));
        jButtonModifierFichier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifierFichierActionPerformed(evt);
            }
        });
        jPanelFonctionnalites.add(jButtonModifierFichier);

        jButtonSupprimerFichier.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonSupprimerFichier.setText("Supprimer Fichier");
        jButtonSupprimerFichier.setMaximumSize(new java.awt.Dimension(150, 25));
        jButtonSupprimerFichier.setMinimumSize(new java.awt.Dimension(150, 25));
        jButtonSupprimerFichier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSupprimerFichierActionPerformed(evt);
            }
        });
        jPanelFonctionnalites.add(jButtonSupprimerFichier);

        jButtonDupliquerFichier.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonDupliquerFichier.setText("Dupliquer Fichier");
        jButtonDupliquerFichier.setMaximumSize(new java.awt.Dimension(150, 25));
        jButtonDupliquerFichier.setMinimumSize(new java.awt.Dimension(150, 25));
        jButtonDupliquerFichier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDupliquerFichierActionPerformed(evt);
            }
        });
        jPanelFonctionnalites.add(jButtonDupliquerFichier);

        jButtonChiffrerFichier.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonChiffrerFichier.setText("Chiffrer Fichier");
        jButtonChiffrerFichier.setMaximumSize(new java.awt.Dimension(150, 25));
        jButtonChiffrerFichier.setMinimumSize(new java.awt.Dimension(150, 25));
        jButtonChiffrerFichier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChiffrerFichierActionPerformed(evt);
            }
        });
        jPanelFonctionnalites.add(jButtonChiffrerFichier);

        jButtonDechiffrerFichier.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonDechiffrerFichier.setText("Déchiffrer Fichier");
        jButtonDechiffrerFichier.setMaximumSize(new java.awt.Dimension(150, 25));
        jButtonDechiffrerFichier.setMinimumSize(new java.awt.Dimension(150, 25));
        jButtonDechiffrerFichier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDechiffrerFichierActionPerformed(evt);
            }
        });
        jPanelFonctionnalites.add(jButtonDechiffrerFichier);

        jButtonConcatenerFichier.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonConcatenerFichier.setText("Concaténer Fichiers");
        jButtonConcatenerFichier.setMaximumSize(new java.awt.Dimension(150, 25));
        jButtonConcatenerFichier.setMinimumSize(new java.awt.Dimension(150, 25));
        jButtonConcatenerFichier.setPreferredSize(new java.awt.Dimension(150, 25));
        jButtonConcatenerFichier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConcatenerFichierActionPerformed(evt);
            }
        });
        jPanelFonctionnalites.add(jButtonConcatenerFichier);

        jButtonListerOccurenceMotFichier.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonListerOccurenceMotFichier.setText("Lister les occurences d'un mot");
        jButtonListerOccurenceMotFichier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListerOccurenceMotFichierActionPerformed(evt);
            }
        });
        jPanelFonctionnalites.add(jButtonListerOccurenceMotFichier);

        getContentPane().add(jPanelFonctionnalites, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, 230, 310));

        jLabelNomDossierSelectionné.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabelNomDossierSelectionné.setText("Aucun dossier sélectioné dans le répertoire courant");
        getContentPane().add(jLabelNomDossierSelectionné, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 100, -1, -1));

        jLabelDossierSelectionne.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabelDossierSelectionne.setText("Dossier Sélectionné :");
        getContentPane().add(jLabelDossierSelectionne, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action réalisée au clic sur un dossiersIhm de la JTree.
     * @param evt L'événement.
     */
    private void jTreeFichiersRepertoireCourantMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeFichiersRepertoireCourantMouseClicked
        DefaultMutableTreeNode nodeSelectionne = (DefaultMutableTreeNode)this.jTreeFichiersRepertoireCourant.getLastSelectedPathComponent();
        if (nodeSelectionne != null) {
            if (nodeSelectionne.getUserObject() instanceof DossierIhm) {
                this.dossierSelectionne = (DossierIhm)nodeSelectionne.getUserObject();
                this.nodeDossierSelectionne = nodeSelectionne;
                this.afficherDossierSelectionne();
                this.afficherPanel(this.listeFichiersPanel);
                this.activerLesBoutons();
            }
        }
    }//GEN-LAST:event_jTreeFichiersRepertoireCourantMouseClicked

    /**
     * Action réalisée au clic sur le bouton parcourir pour sélectionner un nouveau répertoire courant.
     * @param evt L'événement.
     */
    private void jButtonParcourirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonParcourirActionPerformed
        JFileChooser fileChooseer = new JFileChooser(); 
        fileChooseer.setCurrentDirectory(new java.io.File("."));
        fileChooseer.setDialogTitle("Choisir un répertoire courant");
        fileChooseer.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooseer.setAcceptAllFileFilterUsed(false);
        if (fileChooseer.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                this.repertoireCourant = new DossierIhm(new Dossier(fileChooseer.getSelectedFile().getAbsolutePath(), fileChooseer.getSelectedFile().getName()));
                this.iadaptateurGestionFichiers.chargerFichersDansUnDossier(this.repertoireCourant.getDossier());
                this.afficherRepertoireCourant();
                this.dossierSelectionne = this.repertoireCourant;
                this.nodeDossierSelectionne = (DefaultMutableTreeNode) this.modelJtree.getRoot();
                this.afficherDossierSelectionne();
                this.jTextFieldCheminRepertoireCourant.setText(this.repertoireCourant.getCheminFichier());
                
                this.activerLesBoutons();
            }
            catch (ExceptionAccesAuFichierRefuse | ExceptionFichierExistePas | ExceptionGeneraleFichier | ExceptionTailleDuFichierTropImportante exception) {
                this.afficherException(exception);
            }
        }
    }//GEN-LAST:event_jButtonParcourirActionPerformed

    /**
     * Action réalisée au clic sur le bouton ajouter fichier pour ajouter un nouveau fichier dans le dossier sélectionné du répertoire courant.
     * @param evt L'événement.
     */
    private void jButtonAjouterFichierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAjouterFichierActionPerformed
        if (this.dossierSelectionne != null) {
            this.afficherPanel(this.editeFichierPanel);
            this.desactiverLesBoutons();
            
            Fichier fichierAAjouter = new Fichier();
            this.editeFichierPanel.getjButtonEditeNomFichier().setVisible(false);
            this.editeFichierPanel.getjTextFieldNomFichier().setText("");
            this.editeFichierPanel.getjTextFieldExtensionFichier().setText("");
            this.editeFichierPanel.getjTextAreaContenuFichier().setText("");
            
            for (ActionListener actionListener : this.editeFichierPanel.getjButtonEditeContenuFichier().getActionListeners()) {
                this.editeFichierPanel.getjButtonEditeContenuFichier().removeActionListener(actionListener);
            }
            
            this.editeFichierPanel.getjButtonEditeContenuFichier().addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String nomFichier = editeFichierPanel.getjTextFieldNomFichier().getText();
                    String extensionFichier = editeFichierPanel.getjTextFieldExtensionFichier().getText();
                    if (!nomFichier.isEmpty()) {
                        fichierAAjouter.setNomFichier(nomFichier);
                        fichierAAjouter.setExtensionFichier(extensionFichier);
                        fichierAAjouter.setContenuFichier(editeFichierPanel.getjTextAreaContenuFichier().getText());
                        controleur.ajouterFichier(fichierAAjouter, dossierSelectionne.getDossier());
                    }
                    else {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(MainWindow.this,
                                "Le champs indiquant le nom du fichier à créer est vide.",
                                "Champ nom fichier vide.",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
        }
    }//GEN-LAST:event_jButtonAjouterFichierActionPerformed

    /**
     * Action réalisée au clic sur le bouton modifier fichier pour modifier un fichier sélectionné dans le dossier sélectionné du répertoire courant.
     * @param evt L'événement.
     */
    private void jButtonModifierFichierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifierFichierActionPerformed
        if (this.dossierSelectionne != null) {
            FichierIhm fichierIhmAModifier = this.getFichierIhmSelectionne();
            if (fichierIhmAModifier != null) {
                this.afficherPanel(this.editeFichierPanel);
                this.desactiverLesBoutons();
                
                this.editeFichierPanel.getjButtonEditeNomFichier().setVisible(true);
                this.editeFichierPanel.getjTextFieldNomFichier().setText(fichierIhmAModifier.getNomFichier());
                this.editeFichierPanel.getjTextFieldExtensionFichier().setText(fichierIhmAModifier.getExtensionFichier());
                this.editeFichierPanel.getjTextAreaContenuFichier().setText(fichierIhmAModifier.getContenuFichier());
                
                for (ActionListener actionListener : this.editeFichierPanel.getjButtonEditeNomFichier().getActionListeners()) {
                    this.editeFichierPanel.getjButtonEditeNomFichier().removeActionListener(actionListener);
                }
                
                for (ActionListener actionListener : this.editeFichierPanel.getjButtonEditeContenuFichier().getActionListeners()) {
                    this.editeFichierPanel.getjButtonEditeContenuFichier().removeActionListener(actionListener);
                }
                
                this.editeFichierPanel.getjButtonEditeNomFichier().addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        String nomFichier = editeFichierPanel.getjTextFieldNomFichier().getText();
                        String extensionFichier = editeFichierPanel.getjTextFieldExtensionFichier().getText();
                        if (!nomFichier.isEmpty()) {
                            controleur.modifierNomExtensionFichier(fichierIhmAModifier.getFichier(), nomFichier, extensionFichier);
                        }
                        else {
                            Toolkit.getDefaultToolkit().beep();
                            JOptionPane.showMessageDialog(MainWindow.this,
                                    "Le champs indiquant le nom du fichier à modifier est vide.",
                                    "Champ nom fichier vide.",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                });
                
                this.editeFichierPanel.getjButtonEditeContenuFichier().addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        controleur.modifierContenuFichier(fichierIhmAModifier.getFichier(), editeFichierPanel.getjTextAreaContenuFichier().getText());
                    }
                });
            }
            else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(MainWindow.this,
                        "Pour modifier un fichier, il faut le sélectionner dans la liste centrale qui affiche tous les fichier du dossier sélectionné du répertoire courant.",
                        "Aucun fichier à supprimer sélectionné.",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonModifierFichierActionPerformed

    /**
     * Action réalisée au clic sur le bouton supprimer fichier pour supprimer un fichier sélectionné dans le dossier sélectionné du répertoire courant.
     * @param evt L'événement.
     */
    private void jButtonSupprimerFichierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSupprimerFichierActionPerformed
        this.afficherPanel(this.listeFichiersPanel);
        if (this.dossierSelectionne != null) {
            FichierIhm fichierIhmASupprimer = this.getFichierIhmSelectionne();
            if (fichierIhmASupprimer != null) {
                this.controleur.supprimerFichier(fichierIhmASupprimer.getFichier(), this.dossierSelectionne.getDossier());
            }
            else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(MainWindow.this,
                        "Pour supprimer un fichier, il faut le sélectionner dans la liste centrale qui affiche tous les fichier du dossier sélectionné du répertoire courant.",
                        "Aucun fichier sélectionné.",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonSupprimerFichierActionPerformed

    /**
     * Action réalisée au clic sur le bouton supliquer fichier pour dupliquer un fichier sélectionné dans le dossier sélectionné du répertoire courant.
     * @param evt L'événement.
     */
    private void jButtonDupliquerFichierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDupliquerFichierActionPerformed
        this.afficherPanel(this.listeFichiersPanel);
        if (this.dossierSelectionne != null) {
            FichierIhm fichierIhmADupliquer = this.getFichierIhmSelectionne();
            if (fichierIhmADupliquer != null) {
                this.controleur.dupliquerFichier(fichierIhmADupliquer.getFichier(), this.dossierSelectionne.getDossier());
            }
            else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(MainWindow.this,
                        "Pour supliquer un fichier, il faut le sélectionner dans la liste centrale qui affiche tous les fichier du dossier sélectionné du répertoire courant.",
                        "Aucun fichier à dupliquer sélectionné.",
                        JOptionPane.INFORMATION_MESSAGE);  
            }
        }
    }//GEN-LAST:event_jButtonDupliquerFichierActionPerformed

    /**
     * Action réalisée au clic sur le bouton chiffrer fichier pour chiffrer un fichier sélectionné dans le dossier sélectionné du répertoire courant.
     * @param evt L'événement.
     */
    private void jButtonChiffrerFichierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChiffrerFichierActionPerformed
        this.afficherPanel(this.listeFichiersPanel);
        if (this.dossierSelectionne != null) {
            FichierIhm fichierIhmAChiffrer = this.getFichierIhmSelectionne();
            if (fichierIhmAChiffrer != null) {
                this.controleur.chiffrerFichier(fichierIhmAChiffrer.getFichier(), this.dossierSelectionne.getDossier());
            }
            else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(MainWindow.this,
                        "Pour chiffrer un fichier, il faut le sélectionner dans la liste centrale qui affiche tous les fichier du dossier sélectionné du répertoire courant.",
                        "Aucun fichier à chiffrer sélectionné.",
                        JOptionPane.INFORMATION_MESSAGE);  
            }
        }
    }//GEN-LAST:event_jButtonChiffrerFichierActionPerformed

    /**
     * Action réalisée au clic sur le bouton déchiffrer fichier pour déchiffrer un fichier sélectionné dans le dossier sélectionné du répertoire courant.
     * @param evt L'événement.
     */
    private void jButtonDechiffrerFichierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDechiffrerFichierActionPerformed
        this.afficherPanel(this.listeFichiersPanel);
        if (this.dossierSelectionne != null) {
            FichierIhm fichierIhmADechiffrer = this.getFichierIhmSelectionne();
            if (fichierIhmADechiffrer != null) {
                this.controleur.dechiffrerFichier(fichierIhmADechiffrer.getFichier(), this.dossierSelectionne.getDossier());
            }
            else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(MainWindow.this,
                        "Pour déchiffrer un fichier, il faut sélectionner un fichier précdédement chiffrer dans la liste centrale qui affiche tous les fichier du dossier sélectionné du répertoire courant.",
                        "Aucun fichier chiffré à déchiffrer sélectionné.",
                        JOptionPane.INFORMATION_MESSAGE);  
            }
        }
    }//GEN-LAST:event_jButtonDechiffrerFichierActionPerformed

    /**
     * Action réalisée au clic sur le bouton concaténer fichier pour ouvrir le panel de concaténation avec les fichiers sélectionnés dans le dossier sélectionné du répertoire courant.
     * @param evt L'événement.
     */
    private void jButtonConcatenerFichierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConcatenerFichierActionPerformed
        if (this.dossierSelectionne != null) {
            ArrayList<FichierIhm> listeFichiersIhmSelectionnes = this.getListeFichiersIhmSelectionnes();
            if (!listeFichiersIhmSelectionnes.isEmpty()) {
                this.listeFichiersAConcatenerPanel.getjTextFieldNomFichierConcatene().setText("");
                this.afficherListeFichiersIhmAConcatener(listeFichiersIhmSelectionnes);
                this.afficherPanel(this.listeFichiersAConcatenerPanel);
                this.desactiverLesBoutons();
            }
            else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(MainWindow.this,
                        "Pour concatéer des fichiers, il faut les sélectionner dans la liste centrale qui affiche tous les fichier du dossier sélectionné du répertoire courant.",
                        "Aucun fichiers à concaténer sélectionnés.",
                        JOptionPane.INFORMATION_MESSAGE);  
            }
        }
    }//GEN-LAST:event_jButtonConcatenerFichierActionPerformed

    /**
     * Action réalisée au clic sur le bouton lister les occurences pour ouvrir le panel qui liste les couples position/nombre d'un mot dans les fichier du dossier sélectionné du répertoire courant.
     * @param evt L'événement.
     */
    private void jButtonListerOccurenceMotFichierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListerOccurenceMotFichierActionPerformed
        if (this.dossierSelectionne != null) {
            ArrayList<FichierIhm> listeFichiersIhmDossierSelectionnes = this.getListeFichiersIhmDossierSelectionnes();
            if (!listeFichiersIhmDossierSelectionnes.isEmpty()) {
                this.listerOccurencesMotPanel.getjTextFieldListerFichier().setText("");
                this.afficherListeFichiersIhmALister(listeFichiersIhmDossierSelectionnes);
                this.afficherPanel(this.listerOccurencesMotPanel);
                this.desactiverLesBoutons();
            }
            else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(MainWindow.this,
                        "Il n'y a aucun fichier dans le dossier sélectionné du répertoire courant pour lister toutes les occurences d'un mots dans les fichiers.",
                        "Aucun fichiers dans le dossier sélectionné du répertoire courant.",
                        JOptionPane.INFORMATION_MESSAGE);  
            }
        }
    }//GEN-LAST:event_jButtonListerOccurenceMotFichierActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private manipulationdefichiers.EditeFichierPanel editeFichierPanel;
    private javax.swing.JButton jButtonAjouterFichier;
    private javax.swing.JButton jButtonChiffrerFichier;
    private javax.swing.JButton jButtonConcatenerFichier;
    private javax.swing.JButton jButtonDechiffrerFichier;
    private javax.swing.JButton jButtonDupliquerFichier;
    private javax.swing.JButton jButtonListerOccurenceMotFichier;
    private javax.swing.JButton jButtonModifierFichier;
    private javax.swing.JButton jButtonParcourir;
    private javax.swing.JButton jButtonSupprimerFichier;
    private javax.swing.JLabel jLabelDossierSelectionne;
    private javax.swing.JLabel jLabelNomDossierSelectionné;
    private javax.swing.JLayeredPane jLayeredPane;
    private javax.swing.JPanel jPanelChangementRepertoireCourant;
    private javax.swing.JPanel jPanelFonctionnalites;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldCheminRepertoireCourant;
    private javax.swing.JTree jTreeFichiersRepertoireCourant;
    private manipulationdefichiers.ListeFichiersAConcatenerPanel listeFichiersAConcatenerPanel;
    private manipulationdefichiers.ListeFichiersPanel listeFichiersPanel;
    private manipulationdefichiers.ListerOccurencesMotPanel listerOccurencesMotPanel;
    // End of variables declaration//GEN-END:variables

}
