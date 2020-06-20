/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationdefichiers_exceptionspersonnaliees;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * ExceptionPersonnalisee est la classe abstraite représentant une exception personnalisée de l'application.
 * 
 * @author Pierre-Nicolas
 */
public abstract class ExceptionPersonnalisee extends Exception {
    /**
     * Le titre de l'exception.
     */
    private String titre;

    /**
     * Retourner le titre de l'exception.
     * @return Le titre de l'exception.
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Modifier le titre de l'exception.
     * @param titre Le titre de l'exception.
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Retourner le message de l'exception pour le développpeur.
     * @return Le message de l'exception pour le développpeur.
     */
    public String getDeveloppeurMessage() {
        return this.getCause().getMessage();
    }

    /**
     * Retourner le stacktrace de l'exception.
     * @return Le stacktrace de l'exception.
     */
    public String getStacktrace() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        this.getCause().printStackTrace(printWriter);
        return stringWriter.toString();
    }
    
    /**
     * Constructeur ExceptionPersonnalisee prenant en paramètres le message, le titre et l'exception précédente de l'exception.
     * @param message Le message de l'exception.
     * @param titre Le titre de l'exception.
     * @param precedenteException L'exception précédente.
     */
    public ExceptionPersonnalisee(String message, String titre, Throwable precedenteException) {
        super(message, precedenteException);
        this.titre = titre;
    }
    
}
