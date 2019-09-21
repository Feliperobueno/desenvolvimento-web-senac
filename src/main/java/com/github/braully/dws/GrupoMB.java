package com.github.braully.dws;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("view")
@Component
public class GrupoMB {

    @Autowired
    GrupoDAO grupoDAO;

    Grupo grupos = new Grupo();

    public Grupo getGrupo() {
        return grupos;
    }

    public void salvarGrupo() {
        grupoDAO.save(grupos);
        grupos = new Grupo();
    }

    List<Grupo> listaGrupos;
        
    public List<Grupo> getListaGrupos(){
        if (listaGrupos == null){
            listaGrupos = new ArrayList<>();
        for (Grupo g : grupoDAO.findAll()){
            listaGrupos.add(g);
        }
        }
        return listaGrupos;
    }
   
}
