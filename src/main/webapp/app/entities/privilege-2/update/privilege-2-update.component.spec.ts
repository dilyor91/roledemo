import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { Privilege2FormService } from './privilege-2-form.service';
import { Privilege2Service } from '../service/privilege-2.service';
import { IPrivilege2 } from '../privilege-2.model';
import { IRole2 } from 'app/entities/role-2/role-2.model';
import { Role2Service } from 'app/entities/role-2/service/role-2.service';

import { Privilege2UpdateComponent } from './privilege-2-update.component';

describe('Privilege2 Management Update Component', () => {
  let comp: Privilege2UpdateComponent;
  let fixture: ComponentFixture<Privilege2UpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let privilege2FormService: Privilege2FormService;
  let privilege2Service: Privilege2Service;
  let role2Service: Role2Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [Privilege2UpdateComponent],
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
      .overrideTemplate(Privilege2UpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(Privilege2UpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    privilege2FormService = TestBed.inject(Privilege2FormService);
    privilege2Service = TestBed.inject(Privilege2Service);
    role2Service = TestBed.inject(Role2Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Role2 query and add missing value', () => {
      const privilege2: IPrivilege2 = { id: 456 };
      const role2s: IRole2[] = [{ id: 91981 }];
      privilege2.role2s = role2s;

      const role2Collection: IRole2[] = [{ id: 14153 }];
      jest.spyOn(role2Service, 'query').mockReturnValue(of(new HttpResponse({ body: role2Collection })));
      const additionalRole2s = [...role2s];
      const expectedCollection: IRole2[] = [...additionalRole2s, ...role2Collection];
      jest.spyOn(role2Service, 'addRole2ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ privilege2 });
      comp.ngOnInit();

      expect(role2Service.query).toHaveBeenCalled();
      expect(role2Service.addRole2ToCollectionIfMissing).toHaveBeenCalledWith(
        role2Collection,
        ...additionalRole2s.map(expect.objectContaining)
      );
      expect(comp.role2sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const privilege2: IPrivilege2 = { id: 456 };
      const role2: IRole2 = { id: 54488 };
      privilege2.role2s = [role2];

      activatedRoute.data = of({ privilege2 });
      comp.ngOnInit();

      expect(comp.role2sSharedCollection).toContain(role2);
      expect(comp.privilege2).toEqual(privilege2);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrivilege2>>();
      const privilege2 = { id: 123 };
      jest.spyOn(privilege2FormService, 'getPrivilege2').mockReturnValue(privilege2);
      jest.spyOn(privilege2Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ privilege2 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: privilege2 }));
      saveSubject.complete();

      // THEN
      expect(privilege2FormService.getPrivilege2).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(privilege2Service.update).toHaveBeenCalledWith(expect.objectContaining(privilege2));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrivilege2>>();
      const privilege2 = { id: 123 };
      jest.spyOn(privilege2FormService, 'getPrivilege2').mockReturnValue({ id: null });
      jest.spyOn(privilege2Service, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ privilege2: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: privilege2 }));
      saveSubject.complete();

      // THEN
      expect(privilege2FormService.getPrivilege2).toHaveBeenCalled();
      expect(privilege2Service.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrivilege2>>();
      const privilege2 = { id: 123 };
      jest.spyOn(privilege2Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ privilege2 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(privilege2Service.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRole2', () => {
      it('Should forward to role2Service', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(role2Service, 'compareRole2');
        comp.compareRole2(entity, entity2);
        expect(role2Service.compareRole2).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
