import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { Role2FormService } from './role-2-form.service';
import { Role2Service } from '../service/role-2.service';
import { IRole2 } from '../role-2.model';

import { Role2UpdateComponent } from './role-2-update.component';

describe('Role2 Management Update Component', () => {
  let comp: Role2UpdateComponent;
  let fixture: ComponentFixture<Role2UpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let role2FormService: Role2FormService;
  let role2Service: Role2Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [Role2UpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(Role2UpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(Role2UpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    role2FormService = TestBed.inject(Role2FormService);
    role2Service = TestBed.inject(Role2Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const role2: IRole2 = { id: 456 };

      activatedRoute.data = of({ role2 });
      comp.ngOnInit();

      expect(comp.role2).toEqual(role2);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRole2>>();
      const role2 = { id: 123 };
      jest.spyOn(role2FormService, 'getRole2').mockReturnValue(role2);
      jest.spyOn(role2Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ role2 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: role2 }));
      saveSubject.complete();

      // THEN
      expect(role2FormService.getRole2).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(role2Service.update).toHaveBeenCalledWith(expect.objectContaining(role2));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRole2>>();
      const role2 = { id: 123 };
      jest.spyOn(role2FormService, 'getRole2').mockReturnValue({ id: null });
      jest.spyOn(role2Service, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ role2: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: role2 }));
      saveSubject.complete();

      // THEN
      expect(role2FormService.getRole2).toHaveBeenCalled();
      expect(role2Service.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRole2>>();
      const role2 = { id: 123 };
      jest.spyOn(role2Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ role2 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(role2Service.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
