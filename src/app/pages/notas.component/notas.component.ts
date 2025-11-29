import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AlunoService } from '../../services/aluno';
import { DisciplinaService } from '../../services/disciplina';
import { LancamentoService } from '../../services/lancamento';
import { Lancamento } from '../../models/lancamento.model';

@Component({
  selector: 'app-notas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './notas.component.html',
  styleUrls: ['./notas.component.css']
})
export class NotasComponent implements OnInit {

  alunos: any[] = [];
  disciplinas: any[] = [];
  lancamentos: Lancamento[] = [];

  constructor(
    private alunoService: AlunoService,
    private disciplinaService: DisciplinaService,
    private lancamentoService: LancamentoService
  ) {}

  ngOnInit(): void {
    this.alunos = this.alunoService.getAll();
    this.disciplinas = this.disciplinaService.getAll();
    this.lancamentos = this.lancamentoService.getAll();
  }

  getLancamento(ra: number, codigo: number): Lancamento | undefined {
    return this.lancamentos.find(l =>
      l.aluno.ra === ra && l.disciplina.codigo === codigo
    );
  }

  atualizarMedia(l: Lancamento) {
    const n1 = l.notas.bimestre1 ?? 0;
    const n2 = l.notas.bimestre2 ?? 0;
    l.mediaFinal = (n1 + n2) / 2;
  }

  salvar() {
    this.lancamentoService.saveAll(this.lancamentos);
    alert("Notas salvas com sucesso!");
  }
}
