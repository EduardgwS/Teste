import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AlunoService } from '../../services/aluno';
import { DisciplinaService } from '../../services/disciplina';
import { MatriculasService } from '../../services/matriculas';

@Component({
  selector: 'app-matriculas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './matriculas.component.html',
  styleUrls: ['./matriculas.component.css']
})
export class MatriculasComponent implements OnInit {

  alunos: any[] = [];
  disciplinas: any[] = [];
  matriculas: any = {};  // { alunoRa: [codigosDisciplinas] }

  constructor(
    private alunoService: AlunoService,
    private disciplinaService: DisciplinaService,
    private matriculasService: MatriculasService
  ) {}

  ngOnInit(): void {
    this.alunos = this.alunoService.getAll();
    this.disciplinas = this.disciplinaService.getAll();
    this.matriculas = this.matriculasService.getAll();
  }

  toggleMatricula(ra: number, cod: number) {
    if (!this.matriculas[ra]) this.matriculas[ra] = [];

    if (this.matriculas[ra].includes(cod)) {
      this.matriculas[ra] = this.matriculas[ra].filter((x: number) => x !== cod);
    } else {
      this.matriculas[ra].push(cod);
    }
  }

  salvar() {
    this.matriculasService.save(this.matriculas);
    alert("Matrículas salvas!");
  }

  verifica(ra: number, cod: number) {
    return this.matriculas[ra]?.includes(cod);
  }
}
