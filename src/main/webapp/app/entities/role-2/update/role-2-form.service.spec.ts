import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../role-2.test-samples';

import { Role2FormService } from './role-2-form.service';

describe('Role2 Form Service', () => {
  let service: Role2FormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Role2FormService);
  });

  describe('Service methods', () => {
    describe('createRole2FormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRole2FormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            privileges: expect.any(Object),
            users: expect.any(Object),
          })
        );
      });

      it('passing IRole2 should create a new form with FormGroup', () => {
        const formGroup = service.createRole2FormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            privileges: expect.any(Object),
            users: expect.any(Object),
          })
        );
      });
    });

    describe('getRole2', () => {
      it('should return NewRole2 for default Role2 initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRole2FormGroup(sampleWithNewData);

        const role2 = service.getRole2(formGroup) as any;

        expect(role2).toMatchObject(sampleWithNewData);
      });

      it('should return NewRole2 for empty Role2 initial value', () => {
        const formGroup = service.createRole2FormGroup();

        const role2 = service.getRole2(formGroup) as any;

        expect(role2).toMatchObject({});
      });

      it('should return IRole2', () => {
        const formGroup = service.createRole2FormGroup(sampleWithRequiredData);

        const role2 = service.getRole2(formGroup) as any;

        expect(role2).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRole2 should not enable id FormControl', () => {
        const formGroup = service.createRole2FormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRole2 should disable id FormControl', () => {
        const formGroup = service.createRole2FormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
