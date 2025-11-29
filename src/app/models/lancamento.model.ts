import { Aluno } from './aluno.model';
import { Disciplina } from './disciplina.model';
import { Falta } from './falta.model';

export interface Lancamento {
  aluno: Aluno;
  disciplina: Disciplina;
  notas: {
    bimestre1: number;
    bimestre2: number;
  };
  faltas: {
    bimestre1: Falta[];
    bimestre2: Falta[];
  };
  mediaFinal?: number;
  showFaltas1?: boolean;
  showFaltas2?: boolean;
}
