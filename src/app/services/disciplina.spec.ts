import { TestBed } from '@angular/core/testing';

import { Disciplina } from './disciplina';

describe('Disciplina', () => {
  let service: Disciplina;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Disciplina);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
