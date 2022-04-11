package com.apengenharia.backend.resources;

import com.apengenharia.backend.domain.Projeto;
import com.apengenharia.backend.domain.dtos.ProjetoDTO;
import com.apengenharia.backend.services.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/projetos") //localhost:8080/projetos
public class ProjetoResource {

    @Autowired
    private ProjetoService service;

    // MÉTODO - SERVE PARA REPRESENTAR TODA A RESPOSTA HTTP
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProjetoDTO> findById(@PathVariable Integer id) {
        Projeto obj = service.findById(id);
        return ResponseEntity.ok().body(new ProjetoDTO(obj));
    }

    // MÉTODO LISTARÁ TODOS OS PROJETOS DTO - QUANDO NÃO HÁ PARÂMETROS DEFINIDOS
    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> findAll() {
        List<Projeto> list = service.findAll();
        List<ProjetoDTO> listDTO = list.stream().map(obj -> new ProjetoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE PROJETOS
    @PostMapping
    public ResponseEntity<ProjetoDTO> create(@Valid @RequestBody ProjetoDTO obj) {
        Projeto newObj = service.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // MÉTODO PARA ATUALIZAR UM NOVO REGISTRO DE PROJETOS
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProjetoDTO> update(@PathVariable Integer id, @Valid @RequestBody ProjetoDTO objDTO) {
        Projeto newObj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ProjetoDTO(newObj));
    }
}
