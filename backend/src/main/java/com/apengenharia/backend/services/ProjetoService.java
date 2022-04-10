package com.apengenharia.backend.services;

import com.apengenharia.backend.domain.Projeto;
import com.apengenharia.backend.domain.Cliente;
import com.apengenharia.backend.domain.Tecnico;
import com.apengenharia.backend.domain.dtos.ProjetoDTO;
import com.apengenharia.backend.domain.enums.Prioridade;
import com.apengenharia.backend.domain.enums.Status;
import com.apengenharia.backend.repositories.ProjetoRepository;
import com.apengenharia.backend.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository repository; // COMUNICAÇÃO COM O BANCO
    @Autowired
    private TecnicoService tecnicoService;
    @Autowired
    private ClienteService clienteService;

    // MÉTODO IRÁ BUSCAR NO DATABASE O ID E RETORNAR O OBJETO
    public Projeto findById(Integer id) {
        Optional<Projeto> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
    }

    // MÉTODO LISTARÁ TODOS OS PROJETOS
    public List<Projeto> findAll() {
        return repository.findAll();
    }

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE PROJETOS
    public Projeto create(ProjetoDTO obj) {
        return repository.save(newChamado(obj));
    }

    // VERIFICA SE O ID DO TÉCNICO E CLIENTE EXISTEM QUANDO O PROJETOS É CRIADO
    private Projeto newChamado(ProjetoDTO obj) {
        Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
        Cliente cliente = clienteService.findById(obj.getCliente());

        Projeto projeto = new Projeto();
        // PARA ATUALIZAR O PROJETOS, POIS != NULL
        if(obj.getId() != null) {
            projeto.setId(obj.getId());
        }

        // PARA APRESENTAR A DATA ATUAL QUANDO ENCERRADO O PROJETOS
        if(obj.getStatus().equals(2)) {
            projeto.setDataFechamento(LocalDate.now());
        }

        projeto.setTecnico(tecnico);
        projeto.setCliente(cliente);
        projeto.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        projeto.setStatus(Status.toEnum(obj.getStatus()));
        projeto.setTitulo(obj.getTitulo());
        projeto.setObservacoes(obj.getObservacoes());
        return projeto;
    }

    // ENDPOINT PARA ATUALIZAR AS INFORMAÇÕES
    public Projeto update(Integer id, @Valid ProjetoDTO objDTO) {
        objDTO.setId(id);
        Projeto oldObj = findById(id);
        oldObj = newChamado(objDTO);
        return repository.save(oldObj);
    }
}
