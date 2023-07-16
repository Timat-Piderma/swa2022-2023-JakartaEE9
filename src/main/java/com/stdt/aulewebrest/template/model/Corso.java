package com.stdt.aulewebrest.template.model;

import java.util.List;

public class Corso {
    private String nome;
    private Integer id;
    private List<Evento> eventi;

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
     * @return the eventi
     */
    public List<Evento>  getEventi() {
        return eventi;
    }

    /**
     * @param eventi the eventi to set
     */
    public void setEventi(List<Evento> eventi) {
        this.eventi = eventi;
    }
}
