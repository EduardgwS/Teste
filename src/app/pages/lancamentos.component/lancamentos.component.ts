import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Disciplina } from '../../models/disciplina.model';
import { Aluno } from '../../models/aluno.model';
import { Falta } from '../../models/falta.model';
import { Lancamento } from '../../models/lancamento.model';

import { DisciplinaService } from '../../services/disciplina';
import { AlunoService } from '../../services/aluno';
import { MatriculasService } from '../../services/matriculas';
import { LancamentoService } from '../../services/lancamento';

@Component({
  selector: 'app-lancamentos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './lancamentos.component.html',
  styleUrls: ['./lancamentos.component.css']
})
export class LancamentosComponent implements OnInit {

  disciplina!: Disciplina | undefined;
  alunosMatriculados: Aluno[] = [];
  lancamentos: Lancamento[] = [];

  constructor(
    private route: ActivatedRoute,
    private disciplinaService: DisciplinaService,
    private alunoService: AlunoService,
    private matriculasService: MatriculasService,
    private lancamentoService: LancamentoService
  ) {}

  ngOnInit(): void {

    // 1️⃣ PEGAR CÓDIGO DA DISCIPLINA
    const codigo = Number(this.route.snapshot.paramMap.get('codigo'));
    if (!codigo) return;

    this.disciplina = this.disciplinaService.findByCodigo(codigo);
    if (!this.disciplina) return;

    // 2️⃣ PEGAR MATRÍCULAS
    const matriculas = this.matriculasService.getAll(); // { ra: [codigos] }

    // 3️⃣ PEGAR ALUNOS MATRICULADOS
    this.alunosMatriculados = this.alunoService
      .getAll()
      .filter(a => matriculas[a.ra]?.includes(codigo));

    // 4️⃣ CONSTRUIR LANÇAMENTOS
    this.buildLancamentos();
  }

  private buildLancamentos() {
    const dias = Array.from({ length: 30 }, (_, i) => i + 1);

    // Buscar lançamentos já salvos
    const saved = this.lancamentoService.getByDisciplina(this.disciplina!.codigo);

    if (saved.length > 0) {
      this.lancamentos = saved;
      return;
    }

    // Criar novos lançamentos
    this.lancamentos = this.alunosMatriculados.map(aluno => ({
      aluno,
      disciplina: this.disciplina!,
      notas: { bimestre1: 0, bimestre2: 0 },
      faltas: {
        bimestre1: dias.map(d => ({ dia: d, falta: false })),
        bimestre2: dias.map(d => ({ dia: d, falta: false }))
      },
      mediaFinal: 0,
      showFaltas1: false,
      showFaltas2: false
    }));
  }

  atualizarMedia(l: Lancamento) {
    const n1 = l.notas.bimestre1 ?? 0;
    const n2 = l.notas.bimestre2 ?? 0;
    l.mediaFinal = (n1 + n2) / 2;
  }

  contarFaltas(l: Lancamento, bim: 1 | 2) {
    const arr = bim === 1 ? l.faltas.bimestre1 : l.faltas.bimestre2;
    return arr.filter(f => f.falta).length;
  }

  contarFaltasPorBimestre(l: Lancamento, bim: 1 | 2): number {
  const arr = bim === 1 ? l.faltas.bimestre1 : l.faltas.bimestre2;
  return arr.filter((f: Falta) => f.falta).length;
}

  salvar() {
    this.lancamentoService.saveForDisciplina(this.disciplina!.codigo, this.lancamentos);
    alert("Lançamentos salvos com sucesso!");
  }
}
