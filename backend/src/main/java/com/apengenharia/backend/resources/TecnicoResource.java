package com.apengenharia.backend.resources;

import com.apengenharia.backend.domain.Tecnico;
import com.apengenharia.backend.domain.dtos.TecnicoDTO;
import com.apengenharia.backend.services.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tecnicos") //localhost:8080/tecnicos
public class TecnicoResource {

    @Autowired
    private TecnicoService service;

    // MÉTODO - SERVE PARA REPRESENTAR TODA A RESPOSTA HTTP
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
        // COMUNICAÇÃO COM O BANCO DE DADOS
        Tecnico obj = service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }

    // MÉTODO LISTARÁ TODOS OS TÉCNICOS DTO - QUANDO NÃO HÁ PARÂMETROS DEFINIDOS
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        List<Tecnico> list = service.findAll();
        List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE TÉCNICO
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico newObj = service.create(objDTO);
        // SETA O ENDEREÇO DO NOVO OBJETO
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // MÉTODO PARA ATUALIZAR UM NOVO REGISTRO DE TÉCNICO
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }

    // MÉTODO PARA DELETAR UM NOVO REGISTRO DE TÉCNICO
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
