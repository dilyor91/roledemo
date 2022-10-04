import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../user-2.test-samples';

import { User2FormService } from './user-2-form.service';

describe('User2 Form Service', () => {
  let service: User2FormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(User2FormService);
  });

  describe('Service methods', () => {
    describe('createUser2FormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUser2FormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            position: expect.any(Object),
            role2s: expect.any(Object),
          })
        );
      });

      it('passing IUser2 should create a new form with FormGroup', () => {
        const formGroup = service.createUser2FormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            position: expect.any(Object),
            role2s: expect.any(Object),
          })
        );
      });
    });

    describe('getUser2', () => {
      it('should return NewUser2 for default User2 initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUser2FormGroup(sampleWithNewData);

        const user2 = service.getUser2(formGroup) as any;

        expect(user2).toMatchObject(sampleWithNewData);
      });

      it('should return NewUser2 for empty User2 initial value', () => {
        const formGroup = service.createUser2FormGroup();

        const user2 = service.getUser2(formGroup) as any;

        expect(user2).toMatchObject({});
      });

      it('should return IUser2', () => {
        const formGroup = service.createUser2FormGroup(sampleWithRequiredData);

        const user2 = service.getUser2(formGroup) as any;

        expect(user2).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUser2 should not enable id FormControl', () => {
        const formGroup = service.createUser2FormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUser2 should disable id FormControl', () => {
        const formGroup = service.createUser2FormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
