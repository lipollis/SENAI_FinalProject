package com.apengenharia.backend.resources;

import com.apengenharia.backend.domain.Cliente;
import com.apengenharia.backend.domain.Tecnico;
import com.apengenharia.backend.domain.dtos.ClienteDTO;
import com.apengenharia.backend.domain.dtos.TecnicoDTO;
import com.apengenharia.backend.services.ClienteService;
import com.apengenharia.backend.services.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes") //localhost:8080/clientes
public class ClienteResource {

    @Autowired
    private ClienteService service;

    // MÉTODO - SERVE PARA REPRESENTAR TODA A RESPOSTA HTTP
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id){
        // COMUNICAÇÃO COM O BANCO DE DADOS
        Cliente obj = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }

    // MÉTODO LISTARÁ TODOS OS CLIENTE DTO - QUANDO NÃO HÁ PARÂMETROS DEFINIDOS
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE CLIENTE
    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO) {
        Cliente newObj = service.create(objDTO);
        // SETA O ENDEREÇO DO NOVO OBJETO
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // MÉTODO PARA ATUALIZAR UM NOVO REGISTRO DE CLIENTE
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO) {
        Cliente obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }

    // MÉTODO PARA DELETAR UM NOVO REGISTRO DE CLIENTE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
