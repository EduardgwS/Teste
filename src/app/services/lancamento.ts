import { Injectable } from '@angular/core';
import { Lancamento } from '../models/lancamento.model';

@Injectable({ providedIn: 'root' })
export class LancamentoService {

  private storageKey = 'lancamentos';

  constructor() {}

  /** Retorna todos os lançamentos armazenados */
  getAll(): Lancamento[] {
    const raw = localStorage.getItem(this.storageKey);
    return raw ? JSON.parse(raw) : [];
  }

  /** Salva TODOS os lançamentos */
  saveAll(list: Lancamento[]) {
    localStorage.setItem(this.storageKey, JSON.stringify(list));
  }

  /** Busca lançamentos por disciplina */
  getByDisciplina(codigoDisciplina: number): Lancamento[] {
    return this.getAll().filter(
      l => l.disciplina && Number(l.disciplina.codigo) === Number(codigoDisciplina)
    );
  }

  /**
   * Salva somente os lançamentos da disciplina informada
   * sem apagar os lançamentos de outras disciplinas.
   */
  saveForDisciplina(codigoDisciplina: number, lancamentos: Lancamento[]) {
    const outros = this.getAll().filter(
      l => !(l.disciplina && Number(l.disciplina.codigo) === Number(codigoDisciplina))
    );

    const combinado = [...outros, ...lancamentos];

    this.saveAll(combinado);
  }
}
