package com.apengenharia.backend.services;

import com.apengenharia.backend.domain.Projeto;
import com.apengenharia.backend.domain.Cliente;
import com.apengenharia.backend.domain.Tecnico;
import com.apengenharia.backend.domain.enums.Perfil;
import com.apengenharia.backend.domain.enums.Prioridade;
import com.apengenharia.backend.domain.enums.Status;
import com.apengenharia.backend.repositories.ProjetoRepository;
import com.apengenharia.backend.repositories.PessoaRepository;
import com.apengenharia.backend.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
// SERVIÇO CAPAZ DE INJETAR OUTRAS PARTES NO PROJETO
public class DBService {
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    // MÉTODO
    public void instanciaDB() {

        Tecnico tec1 = new Tecnico(null, "admin", "550.482.150-95", "admin@mail.com",  encoder.encode("admin"));
        tec1.addPerfil(Perfil.ADMIN);
        Tecnico tec2 = new Tecnico(null, "Tarsila do Amaral", "903.347.070-56", "caipirinha.amaral@mail.com.br",  encoder.encode("123"));
        Tecnico tec3 = new Tecnico(null, "Oswald de Andrade", "271.068.470-54", "oswald.andrade@mail.com.br",  encoder.encode("123"));
        Tecnico tec4 = new Tecnico(null, "Anita Malfatti", "162.720.120-39", "anita.malfatti@mail.com.br",  encoder.encode("123"));
        Tecnico tec5 = new Tecnico(null, "Mário de Andrade", "778.556.170-27", "mario.andrade@mail.com.br",  encoder.encode("123"));

        Cliente cli1 = new Cliente(null, "Graça Aranha", "111.661.890-74", "g.aranha@mail.com.br",  encoder.encode("123"));
        Cliente cli2 = new Cliente(null, "Victor Brecheret", "322.429.140-06", "victor.brecheret@mail.com.br",  encoder.encode("123"));
        Cliente cli3 = new Cliente(null, "Manuel Bandeira", "792.043.830-62", "manu.bandeira@mail.com.br",  encoder.encode("123"));
        Cliente cli4 = new Cliente(null, "Guiomar Novaes", "177.409.680-30", "gugu.novaes@mail.com.br",  encoder.encode("123"));
        Cliente cli5 = new Cliente(null, "Heitor Villa-Lobos", "081.399.300-83", "villa.lobos@mail.com.br",  encoder.encode("123"));

        Projeto p1 = new Projeto(null, Prioridade.MEDIA, Status.ANDAMENTO, "Projeto 1", "Teste chamado 1", tec1, cli1);
        Projeto p2 = new Projeto(null, Prioridade.ALTA, Status.ABERTO, "Projeto 2", "Teste chamado 2", tec1, cli2);
        Projeto p3 = new Projeto(null, Prioridade.BAIXA, Status.ENCERRADO, "Projeto 3", "Teste chamado 3", tec2, cli3);
        Projeto p4 = new Projeto(null, Prioridade.ALTA, Status.ABERTO, "Projeto 4", "Teste chamado 4", tec3, cli3);
        Projeto p5 = new Projeto(null, Prioridade.MEDIA, Status.ANDAMENTO, "Projeto 5", "Teste chamado 5", tec2, cli1);
        Projeto p6 = new Projeto(null, Prioridade.BAIXA, Status.ENCERRADO, "Projeto 7", "Teste chamado 6", tec1, cli5);

        pessoaRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4, tec5, cli1, cli2, cli3, cli4, cli5));
        projetoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));
    }
}
