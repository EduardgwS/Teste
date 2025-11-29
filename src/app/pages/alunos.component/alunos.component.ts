import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-alunos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './alunos.component.html',
  styleUrls: ['./alunos.component.css']
})
export class AlunosComponent implements OnInit {
  form = { nome: '', cpf: '', ra: '', anoIngresso: null, periodoAtual: null };
  alunos: any[] = [];

  ngOnInit(): void {
    const raw = localStorage.getItem('app_alunos_v1');
    this.alunos = raw ? JSON.parse(raw) : [];
  }

  adicionar() {
    if (!this.form.nome || !this.form.ra) { alert('Preencha nome e RA'); return; }
    this.alunos.push({ ...this.form });
    localStorage.setItem('app_alunos_v1', JSON.stringify(this.alunos));
    this.form = { nome: '', cpf: '', ra: '', anoIngresso: null, periodoAtual: null };
  }

  limpar() {
    this.form = { nome: '', cpf: '', ra: '', anoIngresso: null, periodoAtual: null };
  }
}
