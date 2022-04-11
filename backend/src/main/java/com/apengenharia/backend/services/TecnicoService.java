package com.apengenharia.backend.services;

import com.apengenharia.backend.domain.Pessoa;
import com.apengenharia.backend.domain.Tecnico;
import com.apengenharia.backend.domain.dtos.TecnicoDTO;
import com.apengenharia.backend.repositories.PessoaRepository;
import com.apengenharia.backend.repositories.TecnicoRepository;
import com.apengenharia.backend.services.exceptions.DataIntegrityViolationException;
import com.apengenharia.backend.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    // MÉTODO IRÁ BUSCAR NO DATABASE O ID E RETORNAR O OBJETO
    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    // MÉTODO LISTARÁ TODOS OS TÉCNICOS
    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE TÉCNICO
    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null); // GARANTE RECEBER O ID NULO
        objDTO.setSenha(encoder.encode(objDTO.getSenha())); //A SENHA É SALVA NO BANCO JÁ CRIPTOGRAFADA
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO); // CONVERTE AS INFORMAÇÕES DO BANCO PARA UMA NOVA REQUISIÇÃO
        return repository.save(newObj);
    }

    // MÉTODO PARA VALIDAR SE O CPF E O EMAIL JÁ ESTÃO CADASTRADOS
    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        // verifica em Pessoa se CPF já existe
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        // verifica em Pessoa se email já existe
        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }

    // ENDPOINT PARA ATUALIZAR AS INFORMAÇÕES
    public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
        // busca o registro antes da alteração
        objDTO.setId(id);
        Tecnico oldObj = findById(id);

        if(!objDTO.getSenha().equals(oldObj.getSenha())){
            objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        }

        validaPorCpfEEmail(objDTO);
        oldObj = new Tecnico(objDTO);
        return repository.save(oldObj);
    }

    // ENDPOINT PARA DELETAR UM REGISTRO
    public void delete(Integer id) {
        Tecnico obj = findById(id);

        if (obj.getProjetos().size() > 0) {
            throw new DataIntegrityViolationException("Técnico possui projetos e não pode ser deletado!");
        }

        repository.deleteById(id);
    }
}
