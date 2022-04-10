package com.apengenharia.backend.repositories;

import com.apengenharia.backend.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
}
