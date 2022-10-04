import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Privilege2DetailComponent } from './privilege-2-detail.component';

describe('Privilege2 Management Detail Component', () => {
  let comp: Privilege2DetailComponent;
  let fixture: ComponentFixture<Privilege2DetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [Privilege2DetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ privilege2: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(Privilege2DetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(Privilege2DetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load privilege2 on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.privilege2).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
