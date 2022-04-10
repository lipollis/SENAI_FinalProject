# Frontend


## Passos de criação do projeto:
#### Instalação do Angular
```typescript
npm install -g @angular/cli
```
#### Criação do projeto
```typescript
ng new frontend
```
#### Para 'rodar' o projeto
```typescript
ng serve
```
### Configurações
#### Em tsconfig.json, alterar comando para flexibilizar classes string e date
```json
"strict": false,
```
#### Em angular.json, alterar as configuração, pois o template e o css não estarão inline:
```json
"schematics": {
        "@schematics/angular:component": {
          "inlineTemplate": false,
          "inlineStyle": false,
          "skipTests": true
        },
        "@schematics/angular:class": {
          "skipTests": true
        },
        "@schematics/angular:directive": {
          "skipTests": true
        },
        "@schematics/angular:guard": {
          "skipTests": true
        },
        "@schematics/angular:interceptor": {
          "skipTests": true
        },
        "@schematics/angular:pipe": {
          "skipTests": true
        },
        "@schematics/angular:service": {
          "skipTests": true
        },
        "@schematics/angular:application": {
          "strict": true
        }
      },
```
### Bibliotecas
#### Instalando a biblioteca Angular Material - Em CSS e sem tema sugerido
```typescript
ng add @angular/material
```
#### Instalando a biblioteca Bootstrap
```typescript
ng add @ng-bootstrap/ng-bootstrap
```
#### Instalando o toastr de forma global
```typescript
npm i ngx-toastr --save
```
#### Instalação de máscara
```typescript
npm i ngx-mask --save
```
#### Instalação do JWT
```typescript
npm i @auth0/angular-jwt --save
```

### Criando componentes
```typescript
ng g c components/backend/Bnav
ng g c components/backend/Bhome
ng g c components/backend/Bheader
ng g c components/backend/Bfooter
ng g c components/backend/tecnico/tecnico-list
ng g c components/backend/tecnico/tecnico-create
ng g c components/backend/tecnico/tecnico-update
ng g c components/backend/tecnico/tecnico-delete
ng g c components/backend/login
ng g c components/backend/cliente/cliente-list
ng g c components/backend/cliente/cliente-create
ng g c components/backend/cliente/cliente-update
ng g c components/backend/cliente/cliente-delete
ng g c components/backend/projeto/projeto-list
ng g c components/backend/projeto/projeto-create
ng g c components/backend/projeto/projeto-update
ng g c components/backend/projeto/projeto-read
ng g c components/frontend/tabbar
ng g c components/frontend/home
ng g c components/frontend/header
ng g c components/frontend/footer
ng g c components/frontend/menu-home
ng g c components/frontend/info
ng g c components/frontend/sobre
ng g c components/frontend/portifolio-projeto
ng g c components/frontend/portifolio-servico
```

#### Serviço para buscar no backend as informações
```typescript
ng g s services/auth
ng g guard auth/auth
ng g s services/tecnico
ng g s services/cliente
ng g s services/projeto
```
### Imports feitos no app.module
```typescript
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Para trabalhar com formulários no Angular 12
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

// Para realizar requisições HTTP
import { HttpClientModule } from '@angular/common/http';

// Imports para componentes do Angular Material
import { MatSliderModule } from '@angular/material/slider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatRadioModule } from '@angular/material/radio';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';

import { ToastrModule } from 'ngx-toastr';
import { NgxMaskModule } from 'ngx-mask';

// Componentes do projeto
import { BnavComponent } from './components/backend/bnav/bnav.component';
import { BhomeComponent } from './components/backend/bhome/bhome.component';
import { BheaderComponent } from './components/backend/bheader/bheader.component';
import { BfooterComponent } from './components/backend/bfooter/bfooter.component';
import { TecnicoListComponent } from './components/backend/tecnico/tecnico-list/tecnico-list.component';
import { TecnicoCreateComponent } from './components/backend/tecnico/tecnico-create/tecnico-create.component';
import { TecnicoUpdateComponent } from './components/backend/tecnico/tecnico-update/tecnico-update.component';
import { TecnicoDeleteComponent } from './components/backend/tecnico/tecnico-delete/tecnico-delete.component';
import { LoginComponent } from './components/backend/login/login.component';
import { ClienteListComponent } from './components/backend/cliente/cliente-list/cliente-list.component';
import { ClienteCreateComponent } from './components/backend/cliente/cliente-create/cliente-create.component';
import { ClienteUpdateComponent } from './components/backend/cliente/cliente-update/cliente-update.component';
import { ClienteDeleteComponent } from './components/backend/cliente/cliente-delete/cliente-delete.component';
import { ProjetoListComponent } from './components/backend/projeto/projeto-list/projeto-list.component';
import { ProjetoCreateComponent } from './components/backend/projeto/projeto-create/projeto-create.component';
import { ProjetoUpdateComponent } from './components/backend/projeto/projeto-update/projeto-update.component';
import { ProjetoReadComponent } from './components/backend/projeto/projeto-read/projeto-read.component';
import { TabbarComponent } from './components/frontend/tabbar/tabbar.component';
import { HomeComponent } from './components/frontend/home/home.component';
import { HeaderComponent } from './components/frontend/header/header.component';
import { FooterComponent } from './components/frontend/footer/footer.component';
import { MenuHomeComponent } from './components/frontend/menu-home/menu-home.component';
import { InfoComponent } from './components/frontend/info/info.component';
import { SobreComponent } from './components/frontend/sobre/sobre.component';
import { PortifolioProjetoComponent } from './components/frontend/portifolio-projeto/portifolio-projeto.component';
import { PortifolioServicoComponent } from './components/frontend/portifolio-servico/portifolio-servico.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    BnavComponent,
    BhomeComponent,
    BheaderComponent,
    BfooterComponent,
    TecnicoListComponent,
    TecnicoCreateComponent,
    TecnicoUpdateComponent,
    TecnicoDeleteComponent,
    LoginComponent,
    ClienteListComponent,
    ClienteCreateComponent,
    ClienteUpdateComponent,
    ClienteDeleteComponent,
    ProjetoListComponent,
    ProjetoCreateComponent,
    ProjetoUpdateComponent,
    ProjetoReadComponent,
    TabbarComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    MenuHomeComponent,
    InfoComponent,
    SobreComponent,
    PortifolioProjetoComponent,
    PortifolioServicoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    // Forms
    FormsModule,
    ReactiveFormsModule,
    // Requisições http
    HttpClientModule,
    // Angular Material
    MatSliderModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatSelectModule,
    MatInputModule,
    MatRadioModule,
    MatTableModule,
    MatIconModule,
    MatListModule,
    MatCardModule,
    ToastrModule.forRoot({
      timeOut: 4000,
      closeButton: true,
      progressBar: true
    }),
    NgxMaskModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
```
### Rotas definidas
``` typescript
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
```
### Models
#### Tecnico
```
export interface Tecnico {
   id?:         any;
   nome:     string;
   cpf:      string;
   email:    string;
   senha:    string;
   perfis: string[];
   dataCriacao: any;
 }
```
#### Projeto
```
export interface Projeto {
   id?:                any;
   dataAbertura?:   string;
   dataFechamento?: string;
   prioridade:      string;
   status:          string;
   titulo:          string;
   observacoes:     string;
   tecnico:            any;
   cliente:            any;
   nomeCliente:     string;
   nomeTecnico:     string;
}
```
#### Cliente
```
export interface Cliente {
   id?:         any;
   nome:     string;
   cpf:      string;
   email:    string;
   senha:    string;
   perfis: string[];
   dataCriacao: any;
 }
```
#### Credenciais
```
export interface Credenciais {
   email: string;
   senha: string;
}
```