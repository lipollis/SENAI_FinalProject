package com.apengenharia.backend.services;

import com.apengenharia.backend.domain.Cliente;
import com.apengenharia.backend.domain.Pessoa;
import com.apengenharia.backend.domain.dtos.ClienteDTO;
import com.apengenharia.backend.repositories.ClienteRepository;
import com.apengenharia.backend.repositories.PessoaRepository;
import com.apengenharia.backend.services.exceptions.DataIntegrityViolationException;
import com.apengenharia.backend.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    // MÉTODO IRÁ BUSCAR NO DATABASE O ID E RETORNAR O OBJETO
    public Cliente findById(Integer id){
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    // MÉTODO LISTARÁ TODOS OS CLIENTES
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE CLIENTES
    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null); // GARANTE RECEBER O ID NULLO
        objDTO.setSenha(encoder.encode(objDTO.getSenha())); //A SENHA É SALVA NO BANCO JÁ CRIPTOGRAFADA
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO); // CONVERTE AS INFORMAÇÕES DO BANCO PARA UMA NOVA REQUISIÇÃO
        return repository.save(newObj);
    }

    // MÉTODO PARA VALIDAR SE O CPF E O EMAIL JÁ ESTÃO CADASTRADOS
    private void validaPorCpfEEmail(ClienteDTO objDTO) {
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
    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        // busca o registro antes da alteração
        objDTO.setId(id);
        Cliente oldObj = findById(id);

        if(!objDTO.getSenha().equals(oldObj.getSenha())){
            objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        }

        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return repository.save(oldObj);
    }

    // ENDPOINT PARA DELETAR UM REGISTRO
    public void delete(Integer id) {
        Cliente obj = findById(id);

        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
        }

        repository.deleteById(id);
    }
}
