package com.ApEng.Ap_Engenharia.repositories;

import com.ApEng.Ap_Engenharia.models.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    //Iterable<Cliente> findByProjeto(Projeto projeto);
    Cliente findById(long id);
    Cliente findByCpf(String cpf);
    Cliente findByNome(String nome);

    // QUERY QUE FARÁ A BUSCA NO BANCO DE DADOS
    @Query(value = "SELECT u FROM Cliente u WHERE u.nome LIKE %?1%")
    List<Cliente> findByNomesClientes(String nome);
}
