import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './auth/auth.guard';
import { BhomeComponent } from './components/backend/bhome/bhome.component';
import { LoginComponent } from './components/backend/login/login.component';
import { BnavComponent } from './components/backend/bnav/bnav.component';
import { ProjetoCreateComponent } from './components/backend/projeto/projeto-create/projeto-create.component';
import { ProjetoListComponent } from './components/backend/projeto/projeto-list/projeto-list.component';
import { ProjetoReadComponent } from './components/backend/projeto/projeto-read/projeto-read.component';
import { ProjetoUpdateComponent } from './components/backend/projeto/projeto-update/projeto-update.component';
import { ClienteCreateComponent } from './components/backend/cliente/cliente-create/cliente-create.component';
import { ClienteDeleteComponent } from './components/backend/cliente/cliente-delete/cliente-delete.component';
import { ClienteListComponent } from './components/backend/cliente/cliente-list/cliente-list.component';
import { ClienteUpdateComponent } from './components/backend/cliente/cliente-update/cliente-update.component';
import { TecnicoCreateComponent } from './components/backend/tecnico/tecnico-create/tecnico-create.component';
import { TecnicoDeleteComponent } from './components/backend/tecnico/tecnico-delete/tecnico-delete.component';
import { TecnicoListComponent } from './components/backend/tecnico/tecnico-list/tecnico-list.component';
import { TecnicoUpdateComponent } from './components/backend/tecnico/tecnico-update/tecnico-update.component';

import { HomeComponent } from './components/frontend/home/home.component';
import { InfoComponent } from './components/frontend/info/info.component';
import { PortifolioProjetoComponent } from './components/frontend/portifolio-projeto/portifolio-projeto.component';
import { PortifolioServicoComponent } from './components/frontend/portifolio-servico/portifolio-servico.component';
import { SobreComponent } from './components/frontend/sobre/sobre.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'info', component: InfoComponent },
  { path: 'portifolioprojeto', component: PortifolioProjetoComponent },
  { path: 'portifolioservico', component: PortifolioServicoComponent },
  { path: 'sobre', component: SobreComponent },
  {
    path: '', component: BnavComponent, canActivate: [AuthGuard], children: [
      { path: 'home', component: BhomeComponent },

      { path: 'tecnicos',            component:   TecnicoListComponent },
      { path: 'tecnicos/create',     component: TecnicoCreateComponent },
      { path: 'tecnicos/update/:id', component: TecnicoUpdateComponent },
      { path: 'tecnicos/delete/:id', component: TecnicoDeleteComponent },

      { path: 'clientes',            component:   ClienteListComponent },
      { path: 'clientes/create',     component: ClienteCreateComponent },
      { path: 'clientes/update/:id', component: ClienteUpdateComponent },
      { path: 'clientes/delete/:id', component: ClienteDeleteComponent },

      { path: 'projetos',                       component:     ProjetoListComponent },
      { path: 'projetos/create',                component:   ProjetoCreateComponent },
      { path: 'projetos/update/:id',            component:   ProjetoUpdateComponent },
      { path: 'projetos/read/:id',              component:     ProjetoReadComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
