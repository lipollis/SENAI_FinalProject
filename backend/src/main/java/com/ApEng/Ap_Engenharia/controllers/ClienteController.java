package com.ApEng.Ap_Engenharia.controllers;

import com.ApEng.Ap_Engenharia.models.Cliente;
import com.ApEng.Ap_Engenharia.models.Projeto;
import com.ApEng.Ap_Engenharia.repositories.ClienteRepository;
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
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    // GET REQUISITA O FORM DE CADASTRO
    @RequestMapping("/cadastrarCliente")
    public String form(){ return "cliente/form-cliente";}

    // POST CADASTRAR O CLIENTE
    @RequestMapping(value = "/cadastrarCliente", method = RequestMethod.POST)
    public String form(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos...");
            return "redirect:/cadastrarCliente";
        }

        clienteRepository.save(cliente);
        attributes.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso!");
        return "redirect:/cadastrarCliente";
    }

    // GET QUE LIST OS CLIENTES
    @RequestMapping("/clientes")
    public ModelAndView listaClientes(){
        ModelAndView modelAndView = new ModelAndView("cliente/lista-cliente");
        Iterable<Cliente> clientes = clienteRepository.findAll();
        modelAndView.addObject("clientes",clientes);
        return modelAndView;
    }

    // GET QUE MOSTRA OS DETALHES DE CLIENTE E PROJETO
    @RequestMapping("/detalhes-cliente/{id}")
    public ModelAndView detalhesCliente(@PathVariable("id") long id){
        Cliente cliente = clienteRepository.findById(id);
        ModelAndView modelAndView = new ModelAndView("cliente/detalhes-cliente");
        modelAndView.addObject("clientes", cliente);

        Iterable<Projeto> projetos = projetoRepository.findByCliente(cliente);
        modelAndView.addObject("projetos", projetos);

        return modelAndView;
    }

    // POST QUE ADICIONA PROJETO AO CLIENTE
    @RequestMapping(value = "/detalhes-cliente/{id}", method = RequestMethod.POST)
    public String detalhesClientePost(@PathVariable("id") long id, Projeto projeto,
                                      BindingResult result, RedirectAttributes attributes){

        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos.");
            return "redirect:/detalhes-cliente/{id}";
        }

        if(projetoRepository.findById(projeto.getId()) != null){
            attributes.addFlashAttribute("mensagem_erro", "ID duplicado");
            return "redirect:/detalhes-cliente/{id}";
        }

        Cliente cliente = clienteRepository.findById(id);
        projeto.setCliente(cliente);
        projetoRepository.save(projeto);
        attributes.addFlashAttribute("mensagem", "Projeto adicionado com sucesso!");
        return "redirect:/detalhes-cliente/{id}";
    }

    // REQUISIÇÃO PARA DELETAR
    @RequestMapping("/deletarCliente")
    public String deletarCliente(long id){
        Cliente cliente = clienteRepository.findById(id);
        clienteRepository.delete(cliente);
        return "redirect:/clientes";
    }

    // DELETE PARA O PROJETO
    @RequestMapping("/deletarProjeto")
    public String deletarProjeto(long id){
        Projeto projeto = projetoRepository.findById(id);
        Cliente cliente = projeto.getCliente();

        String cpf = "" + cliente.getCpf();

        projetoRepository.delete(projeto);

        return "redirect:/cliente/" + cpf;
    }

    // MÉTODOS UPDATE PARA CLIENTE
    // GET CHAMA O FORMULÁRIO DE EDIÇÃO DO CLIENTE
    @RequestMapping("/editar-cliente")
    public ModelAndView editarCliente(long id){
        Cliente cliente = clienteRepository.findById(id);
        ModelAndView modelAndView = new ModelAndView("cliente/update-cliente");
        modelAndView.addObject("cliente", cliente);
        return modelAndView;
    }

    // POST DO FORM QUE ATUALIZA CLIENTE
    @RequestMapping(value = "/editar-cliente", method = RequestMethod.POST)
    public String updateCliente(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes){
        clienteRepository.save(cliente);
        attributes.addFlashAttribute("sucess", "Cliente alterado com sucesso!");

        long idLong = cliente.getId();
        String id = "" + idLong;
        return "redirect:/cliente/" + id;
    }
}
