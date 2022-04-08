
package com.ApEng.Ap_Engenharia.controllers;

import com.ApEng.Ap_Engenharia.models.Parceiro;
import com.ApEng.Ap_Engenharia.models.Projeto;
import com.ApEng.Ap_Engenharia.repositories.ParceiroRepository;
import com.ApEng.Ap_Engenharia.repositories.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ParceiroController {
    @Autowired
    private ParceiroRepository parceiroRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    // GET REQUISITA O FORM DE CADASTRO
    @RequestMapping("/cadastrarParceiro")
    public String form(){ return "parceiro/form-parceiro";}

    // POST CADASTRAR O PARCEIRO
    @RequestMapping(value = "/cadastrarParceiro", method = RequestMethod.POST)
    public String form(@Valid Parceiro parceiro, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos...");
            return "redirect:/cadastrarParceiro";
        }

        parceiroRepository.save(parceiro);
        attributes.addFlashAttribute("mensagem", "Parceiro cadastrado com sucesso!");
        return "redirect:/cadastrarParceiro";
    }

    // GET QUE LIST OS PARCEIROS
    @RequestMapping("/parceiros")
    public ModelAndView listaParceiros(){
        ModelAndView modelAndView = new ModelAndView("parceiro/lista-parceiro");
        Iterable<Parceiro> parceiros = parceiroRepository.findAll();
        modelAndView.addObject("parceiros",parceiros);
        return modelAndView;
    }

    // GET QUE MOSTRA OS DETALHES DE PARCEIRO E PROJETO
    @RequestMapping("/detalhes-parceiro/{id}")
    public ModelAndView detalhesParceiro(@PathVariable("id") long id){
        Parceiro parceiro = parceiroRepository.findById(id);
        ModelAndView modelAndView = new ModelAndView("parceiro/detalhes-parceiro");
        modelAndView.addObject("parceiros", parceiro);

        Iterable<Projeto> projetos = projetoRepository.findByParceiro(parceiro);
        modelAndView.addObject("projetos", projetos);

        return modelAndView;
    }

    // POST QUE ADICIONA PROJETO AO PARCEIRO
    @RequestMapping(value = "/detalhes-parceiro/{id}", method = RequestMethod.POST)
    public String detalhesParceiroPost(@PathVariable("id") long id, Projeto projeto,
                                      BindingResult result, RedirectAttributes attributes){

        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos.");
            return "redirect:/detalhes-parceiro/{id}";
        }
/*
        if(projetoRepository.findById(projeto.getId()) != null){
            attributes.addFlashAttribute("mensagem_erro", "ID duplicado");
            return "redirect:/detalhes-parceiro/{id}";
        }*/

        Parceiro parceiro = parceiroRepository.findById(id);
        projeto.setParceiro(parceiro);
        projetoRepository.save(projeto);
        attributes.addFlashAttribute("mensagem", "Projeto adicionado com sucesso!");
        return "redirect:/detalhes-parceiro/{id}";
    }


    // REQUISIÇÃO PARA DELETAR
    @RequestMapping("/deletarParceiro")
    public String deletarParceiro(long id){
        Parceiro parceiro = parceiroRepository.findById(id);
        parceiroRepository.delete(parceiro);
        return "redirect:/parceiros";
    }

    // DELETE PARA O PROJETO USANDO O CNPJ
    @RequestMapping("/deletarProjetoParceiro")
    public String deletarProjetoParceiro(String nome){
        Projeto projeto = projetoRepository.findByNome(nome);
        Parceiro parceiro = projeto.getParceiro();

        String cnpj = "" + parceiro.getCnpj();

        projetoRepository.delete(projeto);

        return "redirect:/detalhes-parceiro/" + cnpj;
    }


    // MÉTODOS UPDATE PARA PARCEIRO
    // GET CHAMA O FORMULÁRIO DE EDIÇÃO DO PARCEIRO
    @RequestMapping("/editar-parceiro")
    public ModelAndView editarParceiro(long id){
        Parceiro parceiro = parceiroRepository.findById(id);
        ModelAndView modelAndView = new ModelAndView("parceiro/update-parceiro");
        modelAndView.addObject("parceiro", parceiro);
        return modelAndView;
    }

    // POST DO FORM QUE ATUALIZA PARCEIRO
    @RequestMapping(value = "/editar-parceiro", method = RequestMethod.POST)
    public String updateParceiro(@Valid Parceiro parceiro, BindingResult result, RedirectAttributes attributes){
        parceiroRepository.save(parceiro);
        attributes.addFlashAttribute("sucess", "Parceiro alterado com sucesso!");

        long idLong = parceiro.getId();
        String id = "" + idLong;
        return "redirect:/detalhes-parceiro/" + id;
    }
}
