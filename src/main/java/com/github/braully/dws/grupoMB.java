package com.github.braully.dws;

import org.springframework.beans.factory.annotation.Autowired;

public class grupoMB {

    @Autowired
    grupoDAO grupoDAO;

    grupo grupos = new grupo();

    public grupo getGrupo() {
        return grupos;
    }

    public void salvarGrupo() {
        grupoDAO.save(grupos);
        grupos = new grupo();
    }

}
