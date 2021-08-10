package com.pibitaim.us.msjavagerenciadorusuarios.data.form;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PerfisUsuarioForm {

    @NotNull
    private List<Long> perfisUsuarioAtual;

    @NotNull
    private List<Long> perfisUsuarioNovo;

}
