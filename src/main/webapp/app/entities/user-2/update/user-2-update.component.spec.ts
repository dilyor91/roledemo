import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { User2FormService } from './user-2-form.service';
import { User2Service } from '../service/user-2.service';
import { IUser2 } from '../user-2.model';
import { IRole2 } from 'app/entities/role-2/role-2.model';
import { Role2Service } from 'app/entities/role-2/service/role-2.service';

import { User2UpdateComponent } from './user-2-update.component';

describe('User2 Management Update Component', () => {
  let comp: User2UpdateComponent;
  let fixture: ComponentFixture<User2UpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let user2FormService: User2FormService;
  let user2Service: User2Service;
  let role2Service: Role2Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [User2UpdateComponent],
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
      .overrideTemplate(User2UpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(User2UpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    user2FormService = TestBed.inject(User2FormService);
    user2Service = TestBed.inject(User2Service);
    role2Service = TestBed.inject(Role2Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Role2 query and add missing value', () => {
      const user2: IUser2 = { id: 456 };
      const role2s: IRole2[] = [{ id: 68856 }];
      user2.role2s = role2s;

      const role2Collection: IRole2[] = [{ id: 36039 }];
      jest.spyOn(role2Service, 'query').mockReturnValue(of(new HttpResponse({ body: role2Collection })));
      const additionalRole2s = [...role2s];
      const expectedCollection: IRole2[] = [...additionalRole2s, ...role2Collection];
      jest.spyOn(role2Service, 'addRole2ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ user2 });
      comp.ngOnInit();

      expect(role2Service.query).toHaveBeenCalled();
      expect(role2Service.addRole2ToCollectionIfMissing).toHaveBeenCalledWith(
        role2Collection,
        ...additionalRole2s.map(expect.objectContaining)
      );
      expect(comp.role2sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const user2: IUser2 = { id: 456 };
      const role2: IRole2 = { id: 54864 };
      user2.role2s = [role2];

      activatedRoute.data = of({ user2 });
      comp.ngOnInit();

      expect(comp.role2sSharedCollection).toContain(role2);
      expect(comp.user2).toEqual(user2);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUser2>>();
      const user2 = { id: 123 };
      jest.spyOn(user2FormService, 'getUser2').mockReturnValue(user2);
      jest.spyOn(user2Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ user2 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: user2 }));
      saveSubject.complete();

      // THEN
      expect(user2FormService.getUser2).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(user2Service.update).toHaveBeenCalledWith(expect.objectContaining(user2));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUser2>>();
      const user2 = { id: 123 };
      jest.spyOn(user2FormService, 'getUser2').mockReturnValue({ id: null });
      jest.spyOn(user2Service, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ user2: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: user2 }));
      saveSubject.complete();

      // THEN
      expect(user2FormService.getUser2).toHaveBeenCalled();
      expect(user2Service.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUser2>>();
      const user2 = { id: 123 };
      jest.spyOn(user2Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ user2 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(user2Service.update).toHaveBeenCalled();
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
