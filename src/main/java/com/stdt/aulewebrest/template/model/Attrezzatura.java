package com.stdt.aulewebrest.template.model;

import java.util.List;

public class Attrezzatura {
    private Integer id;
    private String nome;
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
