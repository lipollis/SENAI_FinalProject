package com.ApEng.Ap_Engenharia.repositories;

import com.ApEng.Ap_Engenharia.models.Terceirizado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TerceirizadoRepository extends CrudRepository<Terceirizado, Long> {


    Terceirizado findById(long id);
    Terceirizado findByCnpj(String cnpj);
    //List<Terceirizado> findByNome(String nome);

    // QUERY QUE FARÁ A BUSCA NO BANCO DE DADOS
    @Query(value = "SELECT u FROM Terceirizado u WHERE u.nome LIKE %?1%")
    List<Terceirizado> findByNomesTerceirizados(String nome);
}
