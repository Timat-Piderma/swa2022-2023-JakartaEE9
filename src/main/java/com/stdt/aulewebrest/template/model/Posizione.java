package com.stdt.aulewebrest.template.model;

import java.util.List;

public class Posizione {
    private Integer id;
    private String nome;
    private String edificio;
    private String piano;
    private List<Aula> aule;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return the edificio
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * @param edificio the edificio to set
     */
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    /**
     * @return the piano
     */
    public String getPiano() {
        return piano;
    }

    /**
     * @param piano the piano to set
     */
    public void setPiano(String piano) {
        this.piano = piano;
    }

    /**
     * @return the aule
     */
    public List<Aula> getAule() {
        return aule;
    }

    /**
     * @param aule the aule to set
     */
    public void setAule(List<Aula> aule) {
        this.aule = aule;
    }
    
}
