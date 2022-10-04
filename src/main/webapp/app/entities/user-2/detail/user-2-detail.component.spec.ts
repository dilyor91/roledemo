import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { User2DetailComponent } from './user-2-detail.component';

describe('User2 Management Detail Component', () => {
  let comp: User2DetailComponent;
  let fixture: ComponentFixture<User2DetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [User2DetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ user2: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(User2DetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(User2DetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load user2 on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.user2).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
