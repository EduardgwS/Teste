import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class MatriculasService {

  private storageKey = 'matriculas';

  getAll() {
    return JSON.parse(localStorage.getItem(this.storageKey) ?? '{}');
  }

  save(data: any) {
    localStorage.setItem(this.storageKey, JSON.stringify(data));
  }

  getForAluno(ra: number): number[] {
    const all = this.getAll();
    return all[ra] ?? [];
  }
}
