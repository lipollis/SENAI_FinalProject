package com.ApEng.Ap_Engenharia.repositories;

import com.ApEng.Ap_Engenharia.models.Cliente;
import com.ApEng.Ap_Engenharia.models.Parceiro;
import com.ApEng.Ap_Engenharia.models.Projeto;
import com.ApEng.Ap_Engenharia.models.Terceirizado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjetoRepository extends CrudRepository<Projeto, Long> {
    Iterable<Projeto> findByCliente(Cliente cliente);
    Iterable<Projeto> findByParceiro(Parceiro parceiro);
    Iterable<Projeto> findByTerceirizado(Terceirizado terceirizado);

    Projeto findById(long id);
    Projeto findByNome(String nome);

    // QUERY QUE FARÁ A BUSCA NO BANCO DE DADOS
    @Query(value = "SELECT u FROM Projeto u WHERE u.nome LIKE %?1%")
    List<Projeto> findByNomesProjetos(String nome);
}
