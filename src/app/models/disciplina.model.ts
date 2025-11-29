import { Professor } from './professor.model';

export interface Disciplina {
  codigo: number;
  nome: string;
  professor: Professor;
  curso: string;
}
