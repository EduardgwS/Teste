import { Injectable } from '@angular/core';
import { Disciplina } from '../models/disciplina.model';

@Injectable({ providedIn: 'root' })
export class DisciplinaService {

  private storageKey = 'disciplinas';

  getAll(): Disciplina[] {
    return JSON.parse(localStorage.getItem(this.storageKey) ?? '[]');
  }

  saveAll(lista: Disciplina[]) {
    localStorage.setItem(this.storageKey, JSON.stringify(lista));
  }

  add(d: Disciplina) {
    const lista = this.getAll();
    lista.push(d);
    this.saveAll(lista);
  }

  update(disciplina: Disciplina) {
    const lista = this.getAll().map(d =>
      d.codigo === disciplina.codigo ? disciplina : d
    );
    this.saveAll(lista);
  }

  remove(codigo: number) {
    const lista = this.getAll().filter(d => d.codigo !== codigo);
    this.saveAll(lista);
  }

  findByCodigo(codigo: number): Disciplina | undefined {
    return this.getAll().find(d => d.codigo === codigo);
  }
}
