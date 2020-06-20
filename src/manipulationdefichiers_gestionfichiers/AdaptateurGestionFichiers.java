/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_gestionfichiers;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionAccesAuFichierRefuse;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionFichierExisteDeja;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionFichierExistePas;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionFichierInchiffrable;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionFichierIndechiffrable;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionGeneraleChiffrement;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionGeneraleFichier;
import manipulationdefichiers_exceptionspersonnaliees.ExceptionTailleDuFichierTropImportante;
import manipulationdefichiers_metier.Dossier;
import manipulationdefichiers_metier.Fichier;
import manipulationdefichiers_metier.IAdaptateurGestionFichiers;

/**
 *
 * AdaptateurGestionFichiers est la classe représentant un adaptateur de gestion de fichiers qui regroupe toute les foncitonnalités de manipulation de fichiers.
 * Elle implémente IAdaptateurGestionFichiers.
 * 
 * @author Pierre-Nicolas
 */
public class AdaptateurGestionFichiers implements IAdaptateurGestionFichiers {
    /**
     * La clé AES utilisé pour le chiffrement et le déchiffrement des fichiers par l'application.
     */
    private static final String CLE_AES = "6BD199AF4DBAE738FA68805590B1159C";
    
    /**
     * Charger les fichiers d'un dossier.
     * @param dossier Le dossier à charger.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     * @throws ExceptionTailleDuFichierTropImportante Taille du fichier trop importante.
     * @throws ExceptionFichierExistePas Chargement d'un fichier qui n'existe pas.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     */
    @Override
    public void chargerFichersDansUnDossier(Dossier dossier) throws ExceptionAccesAuFichierRefuse, ExceptionTailleDuFichierTropImportante, ExceptionFichierExistePas, ExceptionGeneraleFichier {
        File fileDossier = new File(dossier.getCheminFichier());
        for (File file : fileDossier.listFiles()) {
            if (file.isDirectory()) {
                Dossier sousDossier = new Dossier(file.getAbsolutePath(), file.getName());
                dossier.ajouterFichier(sousDossier);
                this.chargerFichersDansUnDossier(sousDossier);
            }
            else {
                try {
                    String cheminFichier = file.getAbsolutePath();
                    String nomFichier = this.getNomFichierAvecNomFichierAvecExtension(file.getName());
                    String extensionFichier = this.getExtensionFichierAvecNomFichierAvecExtension(file.getName());
                    Fichier fichier = new Fichier(cheminFichier, nomFichier, extensionFichier);
                    fichier.setContenuFichier(new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())), "UTF-8"));
                    dossier.ajouterFichier(fichier);
                }
                catch (AccessDeniedException exception) {
                    throw new ExceptionAccesAuFichierRefuse(exception);
                }
                catch (OutOfMemoryError exception) {
                    throw new ExceptionTailleDuFichierTropImportante(exception);
                }
                catch (NoSuchFileException exception) {
                    throw new ExceptionFichierExistePas(exception);
                }
                catch (IOException exception) {
                    throw new ExceptionGeneraleFichier(exception);
                }
            }
        }
    }
    
    /**
     * Charger un fichier.
     * @param fichier Le fichier à charger.
     * @throws ExceptionTailleDuFichierTropImportante Taille du fichier trop importante.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     */
    @Override
    public void chargerFichier(Fichier fichier) throws ExceptionTailleDuFichierTropImportante, ExceptionGeneraleFichier, ExceptionAccesAuFichierRefuse {
        try {
            File fileFichier = new File(fichier.getCheminFichier());
            fichier.setNomFichier(this.getNomFichierAvecNomFichierAvecExtension(fileFichier.getName()));
            fichier.setExtensionFichier(this.getExtensionFichierAvecNomFichierAvecExtension(fileFichier.getName()));
            fichier.setContenuFichier(new String(Files.readAllBytes(Paths.get(fileFichier.getAbsolutePath())), "UTF-8"));
        }
        catch (OutOfMemoryError exception) {
            throw new ExceptionTailleDuFichierTropImportante(exception);
        }
        catch (IOException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (SecurityException exception) {
            throw new ExceptionAccesAuFichierRefuse(exception);
        }
    }
    
    /**
     * Ajouter un fichier.
     * @param fichier Le fichier à ajouter.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionFichierExisteDeja Ajout d'un fichier qui existe déjà.
     */    
    @Override
    public void ajouterFicher(Fichier fichier) throws ExceptionAccesAuFichierRefuse, ExceptionGeneraleFichier, ExceptionFichierExisteDeja {
        try {
            Path cheminFichierACreer = Paths.get(fichier.getCheminFichier());
            Files.createFile(cheminFichierACreer);
            
            BufferedWriter writer = Files.newBufferedWriter(cheminFichierACreer, Charset.forName("UTF-8"));
            writer.write(fichier.getContenuFichier());
            writer.close();
            
        }
        catch (AccessDeniedException exception) {
            throw new ExceptionAccesAuFichierRefuse(exception);
        }
        catch (UnsupportedOperationException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (FileAlreadyExistsException exception) {
            throw new ExceptionFichierExisteDeja(exception);
        }
        catch (IOException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (SecurityException exception) {
            throw new ExceptionAccesAuFichierRefuse(exception);
        }
    }
    
    /**
     * Supprimer un fichier.
     * @param fichier L eficheir à supprimer.
     * @throws ExceptionFichierExistePas Chargement d'un fichier qui n'existe pas.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     */    
    @Override
    public void supprimerFichier(Fichier fichier) throws ExceptionFichierExistePas, ExceptionGeneraleFichier, ExceptionAccesAuFichierRefuse {
        try {
            Path fichierASupprimer = Paths.get(fichier.getCheminFichier());
            Files.delete(fichierASupprimer);
        }
        catch (NoSuchFileException exception) {
            throw new ExceptionFichierExistePas(exception);
        }
        catch (DirectoryNotEmptyException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (IOException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (SecurityException exception) {
            throw new ExceptionAccesAuFichierRefuse(exception);
        }
    }
    
    /**
     * Modifier le contenu du fichier.
     * @param fichier Le fichier à modifier.
     * @param contenuFichier Le nouveau contenu du fichier.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     */    
    @Override
    public void modifierContenuFichier(Fichier fichier, String contenuFichier) throws ExceptionAccesAuFichierRefuse, ExceptionGeneraleFichier {
        try {
            Path cheminFichierAModifier = Paths.get(fichier.getCheminFichier());
            
            BufferedWriter writer = Files.newBufferedWriter(cheminFichierAModifier, Charset.forName("UTF-8"));
            writer.write(fichier.getContenuFichier());
            writer.close();
            
        }
        catch (AccessDeniedException exception) {
            throw new ExceptionAccesAuFichierRefuse(exception);
        }
        catch (UnsupportedOperationException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (IOException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (SecurityException exception) {
            throw new ExceptionAccesAuFichierRefuse(exception);
        }
    }
    
    /**
     * Modifier le nom et l'extension du fichier.
     * @param fichier Le ficher à modfier.
     * @param nomFichier Le nouveau avec la nouvelle extension du fichier.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionFichierExisteDeja Ajout d'un fichier qui existe déjà.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     */    
    @Override
    public void modifierNomFichier(Fichier fichier, String nomFichier) throws ExceptionGeneraleFichier, ExceptionFichierExisteDeja, ExceptionAccesAuFichierRefuse {
        try {
            Path fichierARenommer = Paths.get(fichier.getCheminFichier());
            Path dossierParent = fichierARenommer.getParent();
            Files.move(fichierARenommer, dossierParent.resolve(nomFichier));
        }
        catch (UnsupportedOperationException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (FileAlreadyExistsException exception) {
            throw new ExceptionFichierExisteDeja(exception);
        }
        catch (DirectoryNotEmptyException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (AtomicMoveNotSupportedException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (IOException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (SecurityException exception) {
            throw new ExceptionAccesAuFichierRefuse(exception);
        }
    }
    
    /**
     * Modifier le chemin du fichier.
     * @param fichier Le fichier à modifier.
     * @param cheminFichier L enouveau chemin du fichier.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionFichierExisteDeja Ajout d'un fichier qui existe déjà.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     */    
    @Override
    public void modifierCheminFichier(Fichier fichier, String cheminFichier) throws ExceptionGeneraleFichier, ExceptionFichierExisteDeja, ExceptionAccesAuFichierRefuse {
        try {
            Path fichierADeplacer = Paths.get(fichier.getCheminFichier());
            Path fichierDeplace = Paths.get(cheminFichier);
            Files.move(fichierADeplacer, fichierDeplace);
        }
        catch (UnsupportedOperationException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (FileAlreadyExistsException exception) {
            throw new ExceptionFichierExisteDeja(exception);
        }
        catch (DirectoryNotEmptyException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (AtomicMoveNotSupportedException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (IOException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (SecurityException exception) {
            throw new ExceptionAccesAuFichierRefuse(exception);
        }
    }
    
    /**
     * Chiffrer le fichier dans le fichier passé en paramètre.
     * @param fichier Le fichier à chiffrer.
     * @param fichierAChiffre Le fichier chiffré.
     * @throws ExceptionGeneraleChiffrement Problèmes généraux du chiffrement.
     * @throws ExceptionFichierInchiffrable Fichier inchiffrable.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     * @throws ExceptionFichierExisteDeja Ajout d'un fichier qui existe déjà.
     */    
    @Override
    public void chiffrerFichier(Fichier fichier, Fichier fichierAChiffre) throws ExceptionGeneraleChiffrement, ExceptionFichierInchiffrable, ExceptionGeneraleFichier, ExceptionAccesAuFichierRefuse, ExceptionFichierExisteDeja {
        try {
            SecretKey secretKey = new SecretKeySpec(AdaptateurGestionFichiers.CLE_AES.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getIV();
            byte[] contenuChiffreByte = cipher.doFinal(fichier.getContenuFichier().getBytes());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(iv);
            byteArrayOutputStream.write(contenuChiffreByte);
            byte contenuChiffreCompletByte[] = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            String contenuChiffreCompletString = Base64.getEncoder().encodeToString(contenuChiffreCompletByte);
            
            fichierAChiffre.setContenuFichier(contenuChiffreCompletString);
            
            this.ajouterFicher(fichierAChiffre);
            
        }
        catch (NoSuchAlgorithmException exception) {
            throw new ExceptionGeneraleChiffrement(exception);
        }
        catch (NoSuchPaddingException exception) {
            throw new ExceptionGeneraleChiffrement(exception);
        }
        catch (InvalidKeyException exception) {
            throw new ExceptionGeneraleChiffrement(exception);
        }
        catch (IllegalBlockSizeException exception) {
            throw new ExceptionFichierInchiffrable(exception);
        }
        catch (BadPaddingException exception) {
            throw new ExceptionFichierInchiffrable(exception);
        }
        catch (IOException exception) {
            throw new ExceptionGeneraleFichier(exception);
        }
        catch (ExceptionAccesAuFichierRefuse exception) {
            throw exception;
        }
        catch (ExceptionFichierExisteDeja exception) {
            throw exception;
        }
    }
    
    /**
     * Déchiffrer le fichier dans le fichier passé en paramètre.
     * @param fichier Le fichier à déchiffrer.
     * @param fichierADechiffre Le fichier déchiffré.
     * @throws ExceptionGeneraleChiffrement Problèmes généraux du chiffrement.
     * @throws ExceptionFichierIndechiffrable Fichier indéchiffrable.
     * @throws ExceptionGeneraleFichier Problèmes généraux de la gestion des fichiers.
     * @throws ExceptionAccesAuFichierRefuse Problèmes d'accès au fichier.
     * @throws ExceptionFichierExisteDeja Ajout d'un fichier qui existe déjà.
     */    
    @Override
    public void dechiffrerFichier(Fichier fichier, Fichier fichierADechiffre) throws ExceptionGeneraleChiffrement, ExceptionFichierIndechiffrable, ExceptionGeneraleFichier, ExceptionAccesAuFichierRefuse, ExceptionFichierExisteDeja {
        try {
            SecretKey secretKey = new SecretKeySpec(AdaptateurGestionFichiers.CLE_AES.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] contenuChiffreCompletByte = Base64.getDecoder().decode(fichier.getContenuFichier());
            byte[] iv = new byte[16];
            iv = Arrays.copyOfRange(contenuChiffreCompletByte, 0, 16);
            contenuChiffreCompletByte = Arrays.copyOfRange(contenuChiffreCompletByte, 16, contenuChiffreCompletByte.length);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] contenuDechiffreByte = cipher.doFinal(contenuChiffreCompletByte);
            String contenuDechiffreString = new String(contenuDechiffreByte);
            
            fichierADechiffre.setContenuFichier(contenuDechiffreString);
            
            this.ajouterFicher(fichierADechiffre);
        }
        catch (NoSuchAlgorithmException exception) {
            throw new ExceptionGeneraleChiffrement(exception);
        }
        catch (NoSuchPaddingException exception) {
            throw new ExceptionGeneraleChiffrement(exception);
        }
        catch (InvalidKeyException exception) {
            throw new ExceptionFichierIndechiffrable(exception);
        }
        catch (InvalidAlgorithmParameterException exception) {
            throw new ExceptionFichierIndechiffrable(exception);
        }
        catch (IllegalBlockSizeException exception) {
            throw new ExceptionFichierIndechiffrable(exception);
        }
        catch (BadPaddingException exception) {
            throw new ExceptionFichierIndechiffrable(exception);
        }
        catch (ExceptionAccesAuFichierRefuse exception) {
            throw exception;
        }
        catch (ExceptionFichierExisteDeja exception) {
            throw exception;
        }
    }
    
    /**
     * Retourner le chemin du parent du fichier.
     * @param fichier Le fichier.
     * @return Le chemin du parent du fichier.
     */    
    @Override
    public String getCheminParentFichier(Fichier fichier) {
        Path path = Paths.get(fichier.getCheminFichier());
        return path.getParent().toString();
    }
    
    /**
     * Concatener le chemin du dossier parent avec le chemin du dossier fichier.
     * @param dossierParent Le dossier parent.
     * @param fichier Le fichier
     * @return Le chemin du dossier parent concaténé avec le chemin du dossier fichier.
     */    
    @Override
    public String concatenerCheminParentAvecFichier(Dossier dossierParent, Fichier fichier) {
        Path pathDossierParent = Paths.get(dossierParent.getCheminFichier());
        Path pathfichier = Paths.get(fichier.getCheminFichier());
        return pathDossierParent.resolve(pathfichier).toString();
    }
    
    /**
     * Concatener le chemin du dossier parent avec le chemin du dossier fichier.
     * @param dossierParent Le chemin du dossier parent.
     * @param fichier Le chemin du fichier
     * @return Le chemin du dossier parent concaténé avec le chemin du dossier fichier.
     */    
    @Override
    public String concatenerCheminParentAvecFichier(String dossierParent, String fichier) {
        Path pathDossierParent = Paths.get(dossierParent);
        Path pathfichier = Paths.get(fichier);
        return pathDossierParent.resolve(pathfichier).toString();
    }
    
    /**
     * Retourner le nom et l'extension du fichier avec le chemin du fichier.
     * @param cheminFichier Le chemin du fichier.
     * @return Le nom et l'extension du fichier avec le chemin du fichier.
     */    
    @Override
    public String getNomExtensionFichierAvecChemin(String cheminFichier) {
        return Paths.get(cheminFichier).getFileName().toString();
    }
    
    /**
     * Retourner le nom du fichier avec le chemin du fichier.
     * @param cheminFichier Le chemin du fichier.
     * @return Le nom du fichier avec le chemin du fichier.
     */    
    @Override
    public String getNomFichierAvecChemin(String cheminFichier) {
        String nomFichierAvecExtension = this.getNomExtensionFichierAvecChemin(cheminFichier);
        return this.getNomFichierAvecNomFichierAvecExtension(nomFichierAvecExtension);
    }
    
    /**
     * Retourner l'extension du fichier avec le chemin du fichier.
     * @param cheminFichier Le chemin du fichier.
     * @return L'extension du fichier avec le chemin du fichier.
     */    
    @Override
    public String getExtensionFichierAvecChemin(String cheminFichier) {
        String nomFichierAvecExtension = this.getNomExtensionFichierAvecChemin(cheminFichier);
        return this.getExtensionFichierAvecNomFichierAvecExtension(nomFichierAvecExtension);
    }
    
    /**
     * Retourner le nom du fichier avec le nom du fichier avec l'extension.
     * @param nomFichierAvecExtension Le nom du fichier avec l'extension.
     * @return Le nom du fichier.
     */    
    @Override
    public String getNomFichierAvecNomFichierAvecExtension(String nomFichierAvecExtension) {
        String nomFichier = nomFichierAvecExtension;
        if (nomFichierAvecExtension.contains(".")) {
            nomFichier = nomFichierAvecExtension.substring(0, nomFichierAvecExtension.lastIndexOf("."));
        }
        return nomFichier;
    }
    
    /**
     * Retourner l'extension du fichier avec le nom du fichier avec l'extension.
     * @param nomFichierAvecExtension Le nom du fichier avec l'extension.
     * @return L'extension du fichier.
     */    
    @Override
    public String getExtensionFichierAvecNomFichierAvecExtension(String nomFichierAvecExtension) {
        String extensionFichier = "";
        if (nomFichierAvecExtension.contains(".")) {
            extensionFichier = nomFichierAvecExtension.substring(nomFichierAvecExtension.lastIndexOf("."));
        }
        return extensionFichier;
    }
    
    /**
     * Retourner l'extension du fichier avec un point s'il n'y en a pas.
     * @param extensionFichier L'extension du fichier.
     * @return L'extension du fichier avec un point s'il n'y en a pas.
     */      
    @Override
    public String getExtensionCorrect(String extensionFichier) {
        if (!extensionFichier.isEmpty()) {
            if (!extensionFichier.contains(".")) {
                extensionFichier = "." + extensionFichier;
            }
        }
        return extensionFichier;
    }
    
    /**
     * Constructeur AdaptateurGestionFichiers sans paramètres.
     */
    public AdaptateurGestionFichiers() {
        
    }
    
}
