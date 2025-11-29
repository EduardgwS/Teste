import { Injectable } from '@angular/core';
import { Aluno } from '../models/aluno.model';

@Injectable({ providedIn: 'root' })
export class AlunoService {

  private storageKey = 'alunos';

  getAll(): Aluno[] {
    return JSON.parse(localStorage.getItem(this.storageKey) ?? '[]');
  }

  saveAll(alunos: Aluno[]) {
    localStorage.setItem(this.storageKey, JSON.stringify(alunos));
  }

  add(a: Aluno) {
    const lista = this.getAll();
    lista.push(a);
    this.saveAll(lista);
  }
}
