package com.apengenharia.backend.repositories;

import com.apengenharia.backend.domain.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {
}
