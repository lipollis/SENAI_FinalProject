import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Projeto } from 'src/app/models/projeto';
import { ProjetoService } from 'src/app/services/projeto.service';

@Component({
  selector: 'app-projeto-read',
  templateUrl: './projeto-read.component.html',
  styleUrls: ['./projeto-read.component.css']
})
export class ProjetoReadComponent implements OnInit {

  projeto: Projeto = {
    prioridade:  '',
    status:      '',
    titulo:      '',
    observacoes: '',
    tecnico:     '',
    cliente:     '',
    nomeCliente: '',
    nomeTecnico: '',
  }

  constructor(
    private projetoService: ProjetoService,
    private toastService:    ToastrService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.projeto.id = this.route.snapshot.paramMap.get('id');
    this.findById();
  }

  findById(): void {
    this.projetoService.findById(this.projeto.id).subscribe(resposta => {
      this.projeto = resposta;
    }, ex => {
      this.toastService.error(ex.error.error);
    })
  }

  retornaStatus(status: any): string {
    if(status == '0') {
      return 'ABERTO'
    } else if(status == '1') {
      return 'EM ANDAMENTO'
    } else {
      return 'ENCERRADO'
    }
  }

  retornaPrioridade(prioridade: any): string {
    if(prioridade == '0') {
      return 'BAIXA'
    } else if(prioridade == '1') {
      return 'MÉDIA'
    } else {
      return 'ALTA'
    }
  }
  
}