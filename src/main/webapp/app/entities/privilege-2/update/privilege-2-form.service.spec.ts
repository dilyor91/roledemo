import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../privilege-2.test-samples';

import { Privilege2FormService } from './privilege-2-form.service';

describe('Privilege2 Form Service', () => {
  let service: Privilege2FormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Privilege2FormService);
  });

  describe('Service methods', () => {
    describe('createPrivilege2FormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPrivilege2FormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            role2s: expect.any(Object),
          })
        );
      });

      it('passing IPrivilege2 should create a new form with FormGroup', () => {
        const formGroup = service.createPrivilege2FormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            role2s: expect.any(Object),
          })
        );
      });
    });

    describe('getPrivilege2', () => {
      it('should return NewPrivilege2 for default Privilege2 initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPrivilege2FormGroup(sampleWithNewData);

        const privilege2 = service.getPrivilege2(formGroup) as any;

        expect(privilege2).toMatchObject(sampleWithNewData);
      });

      it('should return NewPrivilege2 for empty Privilege2 initial value', () => {
        const formGroup = service.createPrivilege2FormGroup();

        const privilege2 = service.getPrivilege2(formGroup) as any;

        expect(privilege2).toMatchObject({});
      });

      it('should return IPrivilege2', () => {
        const formGroup = service.createPrivilege2FormGroup(sampleWithRequiredData);

        const privilege2 = service.getPrivilege2(formGroup) as any;

        expect(privilege2).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPrivilege2 should not enable id FormControl', () => {
        const formGroup = service.createPrivilege2FormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPrivilege2 should disable id FormControl', () => {
        const formGroup = service.createPrivilege2FormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
