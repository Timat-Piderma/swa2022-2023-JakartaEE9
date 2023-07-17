package com.stdt.aulewebrest.template.model;

import java.util.List;

public class Aula {

    private String nome;
    private int capienza;
    private String emailResponsabile;
    private int numeroPreseElettriche;
    private int numeroPreseRete;
    private String note;
    private List<Attrezzatura> attrezzature;
    private Gruppo gruppo;
    private Posizione posizione;
    private List<Evento> eventi;
    private int ID;

    public Aula() {
        //dati di default
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the capienza
     */
    public int getCapienza() {
        return capienza;
    }

    /**
     * @param capienza the capienza to set
     */
    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    /**
     * @return the emailResponsabile
     */
    public String getEmailResponsabile() {
        return emailResponsabile;
    }

    /**
     * @param emailResponsabile the emailResponsabile to set
     */
    public void setEmailResponsabile(String emailResponsabile) {
        this.emailResponsabile = emailResponsabile;
    }

    /**
     * @return the numeroPreseElettriche
     */
    public int getNumeroPreseElettriche() {
        return numeroPreseElettriche;
    }

    /**
     * @param numeroPreseElettriche the numeroPreseElettriche to set
     */
    public void setNumeroPreseElettriche(int numeroPreseElettriche) {
        this.numeroPreseElettriche = numeroPreseElettriche;
    }

    /**
     * @return the numeroPreseRete
     */
    public int getNumeroPreseRete() {
        return numeroPreseRete;
    }

    /**
     * @param numeroPreseRete the numeroPreseRete to set
     */
    public void setNumeroPreseRete(int numeroPreseRete) {
        this.numeroPreseRete = numeroPreseRete;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the attrezzatura
     */
    public List<Attrezzatura> getAttrezzature() {
        return attrezzature;
    }

    /**
     * @param attrezzature the attrezzature to set
     */
    public void setAttrezzature(List<Attrezzatura> attrezzature) {
        this.attrezzature = attrezzature;
    }

    /**
     * @return the gruppo
     */
    public Gruppo getGruppo() {
        return gruppo;
    }

    /**
     * @param gruppo the gruppo to set
     */
    public void setGruppo(Gruppo gruppo) {
        this.gruppo = gruppo;
    }

    /**
     * @return the posizione
     */
    public Posizione getPosizione() {
        return posizione;
    }

    /**
     * @param posizione the posizione to set
     */
    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }

    /**
     * @return the eventi
     */
    public List<Evento> getEventi() {
        return eventi;
    }

    /**
     * @param eventi the eventi to set
     */
    public void setEventi(List<Evento> eventi) {
        this.eventi = eventi;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }
}
