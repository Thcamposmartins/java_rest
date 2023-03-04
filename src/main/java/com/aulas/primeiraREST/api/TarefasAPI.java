package com.aulas.primeiraREST.api;

import com.aulas.primeiraREST.dto.TarefaDTO;
import com.aulas.primeiraREST.facade.TarefasFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Controller
@RequestMapping(value = "/tarefas", produces = MediaType.APPLICATION_JSON_VALUE)
public class TarefasAPI {
    @Autowired
    private TarefasFacade tarefasFacade;

    @PostMapping
    @ResponseBody
    public TarefaDTO create(@RequestBody TarefaDTO tarefaDTO) {
        return tarefasFacade.criar(tarefaDTO);
    }
    @GetMapping("/cep/{cep}")
    @ResponseBody
    public StringBuilder getCEP(@PathVariable("cep") Long cep) throws IOException {
        URL url = new URL("https://viacep.com.br/ws/"+cep+"/json/");
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String endereco = "";
        StringBuilder jsonCep = new StringBuilder();

        while ((endereco = br.readLine()) != null){
            jsonCep.append(endereco);
        }
        return jsonCep;
    }

    @GetMapping("/{tarefaId}")
    @ResponseBody
    public TarefaDTO getTarefa(@PathVariable("tarefaId") Long tarefaId) {
        return tarefasFacade.getById(tarefaId);
    }

    @GetMapping
    @ResponseBody
    public List<TarefaDTO> getAll() {
        return tarefasFacade.getAll();
    }

    @PutMapping("/{tarefaId}")
    @ResponseBody
    public TarefaDTO put(@PathVariable("tarefaId") Long tarefaId, @RequestBody TarefaDTO tarefaDTO) {
        return tarefasFacade.atualizar(tarefaDTO, tarefaId);
    }

    @DeleteMapping("/{tarefaId}")
    @ResponseBody
    public String delete(@PathVariable("tarefaId") Long tarefaId) {
        return tarefasFacade.delete(tarefaId);
    }
}
