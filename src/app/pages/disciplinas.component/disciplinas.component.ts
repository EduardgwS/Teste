import { Component, OnInit } from '@angular/core';
import { Disciplina } from '../../models/disciplina.model';
import { DisciplinaService } from '../../services/disciplina';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-disciplinas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './disciplinas.component.html',
  styleUrls: ['./disciplinas.component.css']
})
export class DisciplinasComponent implements OnInit {
  disciplinas: Disciplina[] = [];
  form: any = { codigo: 0, nome: '', curso: '' };
  editando = false;
  formEdicao: any = {};

  constructor(private router: Router, private disciplinaService: DisciplinaService) { }

  ngOnInit(): void {
    this.load();
  }

  load() { this.disciplinas = this.disciplinaService.getAll(); }

  adicionar() {
    if (!this.form.codigo || !this.form.nome) { alert('Código e nome são obrigatórios'); return; }
    const d: Disciplina = { codigo: Number(this.form.codigo), nome: this.form.nome, curso: this.form.curso, professor: { matricula: 0, nome: 'Prof. Padrão', cpf: '', dtAdmissao: '' } };
    this.disciplinaService.add(d);
    this.form = { codigo: 0, nome: '', curso: '' };
    this.load();
  }

  abrirLancamentos(d: Disciplina) { this.router.navigate(['/lancamentos', d.codigo]); }
  remover(codigo: number) { if (!confirm('Remover?')) return; this.disciplinaService.remove(codigo); this.load(); }
  selecionarParaEdicao(d: Disciplina) { this.editando = true; this.formEdicao = { ...d }; }
  salvarEdicao() { this.disciplinaService.update(this.formEdicao); this.editando = false; this.load(); }
  cancelar() { this.editando = false; }
}
