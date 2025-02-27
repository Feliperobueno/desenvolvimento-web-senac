package com.github.braully.dws;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ContatoServico {

    @Autowired
    SolicitacaoContatoDAO conexao;

    @RequestMapping("/processar-form-contato")
    public String recebeDadosParaContato(@RequestParam Map<String, String> todosParametros) {
        System.out.println("Entrei no metodo processar");
        System.out.println(todosParametros);
        SolicitacaoContato novaSolicitacao = new SolicitacaoContato();
        novaSolicitacao.nome = todosParametros.get("nome");
        novaSolicitacao.email = todosParametros.get("email");
        novaSolicitacao.telefone = todosParametros.get("telefone");
        novaSolicitacao.celular = todosParametros.get("celular");
        novaSolicitacao.empresa = todosParametros.get("empresa");
        novaSolicitacao.duvida = todosParametros.get("duvida");

        System.out.println("Solicitações anteriores" + conexao);
        System.out.println("Nova solicitação recebida" + novaSolicitacao);

        conexao.save(novaSolicitacao);

        return "redirect:/Principal.xhtml";
    }

    @RequestMapping("/todas-solicitacoes")
    @ResponseBody
    public String gerarTelaContatos() {

        String html = "<html>\n"
                + "    <head>\n"
                + "        <title>TODO supply a title</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "     <link rel=\"stylesheet\"href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">\n"
                + "        <table class=\"table table-hover table-dark\"\n>"
                + "            <tr>\n"
                + "                <th>nome</th>\n"
                + "                <th>email</th>\n"
                + "                <th>telefone</th>\n"
                + "                <th>celular</th>\n"
                + "                <th>empresa</th>\n"
                + "                <th>duvida</th>\n"
                + "            </tr>";

        for (SolicitacaoContato sol : conexao.findAll()) {
            String linhaTabela = "<tr>";

            linhaTabela += "<td>";
            linhaTabela += sol.nome;
            linhaTabela += "</td>";

            linhaTabela += "<td>";
            linhaTabela += sol.email;
            linhaTabela += "</td>";

            linhaTabela += "<td>";
            linhaTabela += sol.telefone;
            linhaTabela += "</td>";

            linhaTabela += "<td>";
            linhaTabela += sol.celular;
            linhaTabela += "</td>";

            linhaTabela += "<td>";
            linhaTabela += sol.empresa;
            linhaTabela += "</td>";

            linhaTabela += "<td>";
            linhaTabela += sol.duvida;
            linhaTabela += "</td>";

            linhaTabela += "</tr>";
            html += linhaTabela;

        }
        html += "<tr>\n"
                + "<td>\n"
                + "<a href=\"/Principal.xhtml\"> Voltar</a>"
                + "</td>\n"
                + "</tr>\n";
        html += "       </table>\n"
                + "    </body>\n"
                + "</html>\n"
                + "";

        return html;
    }

}
