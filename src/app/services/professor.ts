import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfessorService {

  private apiUrl = 'https://api.catskittycat.com/api/professor'; // confirme no swagger

  constructor(private http: HttpClient) {}

  getTodosProfessores(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  getProfessorPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }
}
