/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.dws;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author Aluno
 */
@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @Basic
    String login;

    @Basic
    String senha;

    @ManyToMany
    Set<Grupo> gruposUsuario;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    void adicionaGrupo(Grupo k) {
        if (this.gruposUsuario == null) {
            this.gruposUsuario = new HashSet<>();
        }
        this.gruposUsuario.add(k);
}
}

