
package com.ApEng.Ap_Engenharia.controllers;

import com.ApEng.Ap_Engenharia.models.Projeto;
import com.ApEng.Ap_Engenharia.models.Terceirizado;
import com.ApEng.Ap_Engenharia.repositories.ProjetoRepository;
import com.ApEng.Ap_Engenharia.repositories.TerceirizadoRepository;
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
public class TerceirizadoController {

    @Autowired
    private TerceirizadoRepository terceirizadoRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    // GET REQUISITA O FORM DE CADASTRO
    @RequestMapping("/cadastrarTerceirizado")
    public String form(){ return "terceirizado/form-terceirizado";}

    // POST CADASTRAR O PRESTADOR DE SERVIÇO
    @RequestMapping(value = "/cadastrarTerceirizado", method = RequestMethod.POST)
    public String form(@Valid Terceirizado terceirizado, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos...");
            return "redirect:/cadastrarTerceirizado";
        }

        terceirizadoRepository.save(terceirizado);
        attributes.addFlashAttribute("mensagem", "Terceirizado cadastrado com sucesso!");
        return "redirect:/cadastrarTerceirizado";
    }

    // GET QUE LIST OS TERCEIRIZADOS
    @RequestMapping("/terceirizados")
    public ModelAndView listaTerceirizados(){
        ModelAndView modelAndView = new ModelAndView("terceirizado/lista-terceirizado");
        Iterable<Terceirizado> terceirizados = terceirizadoRepository.findAll();
        modelAndView.addObject("terceirizados",terceirizados);
        return modelAndView;
    }

    // GET QUE MOSTRA OS DETALHES DE TERCEIRIZADO
    @RequestMapping("/detalhes-terceirizado/{id}")
    public ModelAndView detalhesTerceirizado(@PathVariable("id") long id){
        Terceirizado terceirizado = terceirizadoRepository.findById(id);
        ModelAndView modelAndView = new ModelAndView("terceirizado/detalhes-terceirizado");
        modelAndView.addObject("terceirizado", terceirizado);

        Iterable<Projeto>projetos = projetoRepository.findByTerceirizado(terceirizado);
        modelAndView.addObject("projetos", projetos);

        return modelAndView;
    }

    // POST QUE ADICIONA PROJETO AO PARCEIRO
    @RequestMapping(value = "/detalhes-terceirizado/{id}", method = RequestMethod.POST)
    public String detalhesTerceirizadoPost(@PathVariable("id") long id, Projeto projeto,
                                       BindingResult result, RedirectAttributes attributes){

        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos.");
            return "redirect:/detalhes-terceirizado/{id}";
        }
/*
        if(projetoRepository.findById(projeto.getId()) != null){
            attributes.addFlashAttribute("mensagem_erro", "ID duplicado.");
            return "redirect:/detalhes-terceirizado/{id}";
        }*/

        Terceirizado terceirizado = terceirizadoRepository.findById(id);
        projeto.setTerceirizado(terceirizado);
        projetoRepository.save(projeto);
        attributes.addFlashAttribute("mensagem", "Projeto adicionado com sucesso!");
        return "redirect:/detalhes-terceirizado/{id}";
    }

    // REQUISIÇÃO PARA DELETAR
    @RequestMapping("/deletarTerceirizado")
    public String deletarTerceirizado(long id){
        Terceirizado terceirizado = terceirizadoRepository.findById(id);
        terceirizadoRepository.delete(terceirizado);
        return "redirect:/terceirizados";
    }

    // DELETE PARA O PROJETO USANDO O CNPJ
    @RequestMapping("/deletarProjetoTerceirizado")
    public String deletarProjetoTerceirizado(String nome){
        Projeto projeto = projetoRepository.findByNome(nome);
        Terceirizado terceirizado = projeto.getTerceirizado();

        String cnpj = "" + terceirizado.getCnpj();

        projetoRepository.delete(projeto);

        return "redirect:/detalhes-terceirizado/" + cnpj;
    }

    // MÉTODOS UPDATE PARA TERCEIRIZADO
    // GET CHAMA O FORMULÁRIO DE EDIÇÃO DO TERCEIRIZADO
    @RequestMapping("/editar-terceirizado")
    public ModelAndView editarTerceirizado(long id){
        Terceirizado terceirizado = terceirizadoRepository.findById(id);
        ModelAndView modelAndView = new ModelAndView("terceirizado/update-terceirizado");
        modelAndView.addObject("terceirizado", terceirizado);
        return modelAndView;
    }

    // POST DO FORM QUE ATUALIZA TERCEIRIZADO
    @RequestMapping(value = "/editar-terceirizado", method = RequestMethod.POST)
    public String updateTerceirizado(@Valid Terceirizado terceirizado, BindingResult result, RedirectAttributes attributes){
        terceirizadoRepository.save(terceirizado);
        attributes.addFlashAttribute("sucess", "Terceirizado alterado com sucesso!");

        long idLong = terceirizado.getId();
        String id = "" + idLong;
        return "redirect:/detalhes-terceirizado/" + id;
    }
}
