import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPrivilege2, NewPrivilege2 } from '../privilege-2.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrivilege2 for edit and NewPrivilege2FormGroupInput for create.
 */
type Privilege2FormGroupInput = IPrivilege2 | PartialWithRequiredKeyOf<NewPrivilege2>;

type Privilege2FormDefaults = Pick<NewPrivilege2, 'id' | 'role2s'>;

type Privilege2FormGroupContent = {
  id: FormControl<IPrivilege2['id'] | NewPrivilege2['id']>;
  name: FormControl<IPrivilege2['name']>;
  role2s: FormControl<IPrivilege2['role2s']>;
};

export type Privilege2FormGroup = FormGroup<Privilege2FormGroupContent>;

@Injectable({ providedIn: 'root' })
export class Privilege2FormService {
  createPrivilege2FormGroup(privilege2: Privilege2FormGroupInput = { id: null }): Privilege2FormGroup {
    const privilege2RawValue = {
      ...this.getFormDefaults(),
      ...privilege2,
    };
    return new FormGroup<Privilege2FormGroupContent>({
      id: new FormControl(
        { value: privilege2RawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(privilege2RawValue.name),
      role2s: new FormControl(privilege2RawValue.role2s ?? []),
    });
  }

  getPrivilege2(form: Privilege2FormGroup): IPrivilege2 | NewPrivilege2 {
    return form.getRawValue() as IPrivilege2 | NewPrivilege2;
  }

  resetForm(form: Privilege2FormGroup, privilege2: Privilege2FormGroupInput): void {
    const privilege2RawValue = { ...this.getFormDefaults(), ...privilege2 };
    form.reset(
      {
        ...privilege2RawValue,
        id: { value: privilege2RawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): Privilege2FormDefaults {
    return {
      id: null,
      role2s: [],
    };
  }
}
