import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-professores',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './professores.component.html',
  styleUrls: ['./professores.component.css']
})
export class ProfessoresComponent implements OnInit {
  form = { nome: '', cpf: '', matricula: null, dtAdmissao: '' };
  professores: any[] = [];

  ngOnInit(): void {
    const raw = localStorage.getItem('app_professores_v1');
    this.professores = raw ? JSON.parse(raw) : [];
  }

  adicionar() {
    if (!this.form.nome) { alert('Preencha nome'); return; }
    this.professores.push({ ...this.form });
    localStorage.setItem('app_professores_v1', JSON.stringify(this.professores));
    this.form = { nome: '', cpf: '', matricula: null, dtAdmissao: '' };
  }
}
