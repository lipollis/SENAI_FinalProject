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
    NgxMaskModule.forRoot(),
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
