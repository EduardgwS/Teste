import { TestBed } from '@angular/core/testing';

import { Matriculas } from './matriculas';

describe('Matriculas', () => {
  let service: Matriculas;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Matriculas);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
