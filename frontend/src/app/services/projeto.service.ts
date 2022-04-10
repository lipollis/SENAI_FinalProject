import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../config/api.config';
import { Projeto } from '../models/projeto';

@Injectable({
  providedIn: 'root'
})
export class ProjetoService {

  constructor(private http: HttpClient) { }

  findById(id: any): Observable<Projeto> {
    return this.http.get<Projeto>(`${API_CONFIG.baseUrl}/projetos/${id}`);
  }

  findAll(): Observable<Projeto[]> {
    return this.http.get<Projeto[]>(`${API_CONFIG.baseUrl}/projetos`);
  }

  create(projeto: Projeto): Observable<Projeto> {
    return this.http.post<Projeto>(`${API_CONFIG.baseUrl}/projetos`, projeto);
  }

  update(projeto: Projeto): Observable<Projeto> {
    return this.http.put<Projeto>(`${API_CONFIG.baseUrl}/projetos/${projeto.id}`, projeto);
  }
}