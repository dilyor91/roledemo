import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Role2DetailComponent } from './role-2-detail.component';

describe('Role2 Management Detail Component', () => {
  let comp: Role2DetailComponent;
  let fixture: ComponentFixture<Role2DetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [Role2DetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ role2: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(Role2DetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(Role2DetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load role2 on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.role2).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
