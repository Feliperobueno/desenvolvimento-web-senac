/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.dws;

import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("view")
@Component
public class UsuarioMB {

    @Autowired
    UsuarioDAO usuarioDAO;

    Usuario usuario = new Usuario();

    public Usuario getUsuario() {
        return usuario;
    }

for(String id : grupoSelecionado){
    grupo g = grupoDAO.findById(Long.parseLong(id)).get();
    usuario.adicionarGrupo(g);
        usuarioDAO.save(usuario);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario salvo com sucesso."));

        usuario = new Usuario();
    }
    String [] grupoSelecionado;
public String [] getGrupoSelecionado(){
    return grupoSelecionado;
}

    public void setGrupoSelecionado(String[] grupoSelecionado) {
        this.grupoSelecionado = grupoSelecionado;
    }


}
