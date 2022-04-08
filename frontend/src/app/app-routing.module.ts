import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from './pages/about/about.component';
import { ContatoComponent } from './pages/contato/contato.component';
import { HomeComponent } from './pages/home/home.component';
import { InformeComponent } from './pages/informe/informe.component';
import { ProjetosComponent } from './pages/projetos/projetos.component';
import { ServicosComponent } from './pages/servicos/servicos.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'sobre', component: AboutComponent },
  { path: 'projetos', component: ProjetosComponent },
  { path: 'servicos', component: ServicosComponent },
  { path: 'info', component: InformeComponent },
  { path: 'contato', component: ContatoComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }];

@NgModule({
  imports: [RouterModule.forRoot(routes), CommonModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
