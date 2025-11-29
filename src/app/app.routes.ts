import { Routes } from '@angular/router';

import { LayoutComponent } from './layout.component/layout.component';

import { AlunosComponent } from './pages/alunos.component/alunos.component';
import { ProfessoresComponent } from './pages/professores.component/professores.component';
import { DisciplinasComponent } from './pages/disciplinas.component/disciplinas.component';
import { LancamentosComponent } from './pages/lancamentos.component/lancamentos.component';
import { BoletimComponent } from './pages/boletim.component/boletim.component';
import { MatriculasComponent } from './pages/matriculas.component/matriculas.component';
import { NotasComponent } from './pages/notas.component/notas.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: 'alunos', component: AlunosComponent },
      { path: 'professores', component: ProfessoresComponent },
      { path: 'disciplinas', component: DisciplinasComponent },
      { path: 'lancamentos/:codigo', component: LancamentosComponent },
      { path: 'matriculas', component: MatriculasComponent },
      { path: 'notas', component: NotasComponent },
      { path: 'boletim', component: BoletimComponent },
      { path: '', redirectTo: 'alunos', pathMatch: 'full' }
    ]
  }
];
