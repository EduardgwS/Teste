import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { AlunoService } from '../../services/aluno';
import { DisciplinaService } from '../../services/disciplina';
import { MatriculasService } from '../../services/matriculas';
import { LancamentoService } from '../../services/lancamento';
import { Lancamento } from '../../models/lancamento.model';

@Component({
  selector: 'app-boletim',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './boletim.component.html',
  styleUrls: ['./boletim.component.css']
})
export class BoletimComponent implements OnInit {

  alunos: any[] = [];
  alunoSelecionado: any = null;
  boletim: Lancamento[] = [];

  constructor(
    private alunoService: AlunoService,
    private disciplinaService: DisciplinaService,
    private matriculaService: MatriculasService,
    private lancamentoService: LancamentoService
  ) {}

  ngOnInit(): void {
    this.alunos = this.alunoService.getAll();
  }

  carregarBoletim() {
    if (!this.alunoSelecionado) return;

    const matriculas = this.matriculaService.getAll();
    const lanc = this.lancamentoService.getAll();

    const disciplinasAluno = matriculas[this.alunoSelecionado.ra] ?? [];

    this.boletim = lanc.filter(l =>
      disciplinasAluno.includes(l.disciplina.codigo)
    );
  }

  media(l: Lancamento) {
    return ((l.notas.bimestre1 ?? 0) + (l.notas.bimestre2 ?? 0)) / 2;
  }

  faltasTotal(l: Lancamento) {
    return l.faltas.bimestre1.filter(f => f.falta).length +
           l.faltas.bimestre2.filter(f => f.falta).length;
  }

  status(l: Lancamento) {
    return this.media(l) >= 6 ? 'Aprovado' : 'Reprovado';
  }
}
