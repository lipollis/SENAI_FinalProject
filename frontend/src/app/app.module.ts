import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { ProjetosComponent } from './pages/projetos/projetos.component';
import { AboutComponent } from './pages/about/about.component';
import { ServicosComponent } from './pages/servicos/servicos.component';
import { InformeComponent } from './pages/informe/informe.component';
import { ContatoComponent } from './pages/contato/contato.component';
import { FooterComponent } from './component/footer/footer.component';
import { NavbarComponent } from './component/navbar/navbar.component';
import { HeaderComponent } from './component/header/header.component';
import { TabbarComponent } from './component/tabbar/tabbar.component';
import { MenuHomeComponent } from './component/menu-home/menu-home.component';
import { FormLoginComponent } from './component/form-login/form-login.component';
import { FormContatoComponent } from './component/form-contato/form-contato.component';
import { ListaProjetosComponent } from './component/lista-projetos/lista-projetos.component';
import { ListaServicoComponent } from './component/lista-servico/lista-servico.component';
import { SobreComponent } from './component/sobre/sobre.component';
import { InfoComponent } from './component/info/info.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ProjetosComponent,
    AboutComponent,
    ServicosComponent,
    InformeComponent,
    ContatoComponent,
    FooterComponent,
    NavbarComponent,
    HeaderComponent,
    TabbarComponent,
    MenuHomeComponent,
    FormLoginComponent,
    FormContatoComponent,
    ListaProjetosComponent,
    ListaServicoComponent,
    SobreComponent,
    InfoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
