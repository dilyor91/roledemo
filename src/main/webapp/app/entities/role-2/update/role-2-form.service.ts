import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRole2, NewRole2 } from '../role-2.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRole2 for edit and NewRole2FormGroupInput for create.
 */
type Role2FormGroupInput = IRole2 | PartialWithRequiredKeyOf<NewRole2>;

type Role2FormDefaults = Pick<NewRole2, 'id' | 'privileges' | 'users'>;

type Role2FormGroupContent = {
  id: FormControl<IRole2['id'] | NewRole2['id']>;
  name: FormControl<IRole2['name']>;
  privileges: FormControl<IRole2['privileges']>;
  users: FormControl<IRole2['users']>;
};

export type Role2FormGroup = FormGroup<Role2FormGroupContent>;

@Injectable({ providedIn: 'root' })
export class Role2FormService {
  createRole2FormGroup(role2: Role2FormGroupInput = { id: null }): Role2FormGroup {
    const role2RawValue = {
      ...this.getFormDefaults(),
      ...role2,
    };
    return new FormGroup<Role2FormGroupContent>({
      id: new FormControl(
        { value: role2RawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(role2RawValue.name),
      privileges: new FormControl(role2RawValue.privileges ?? []),
      users: new FormControl(role2RawValue.users ?? []),
    });
  }

  getRole2(form: Role2FormGroup): IRole2 | NewRole2 {
    return form.getRawValue() as IRole2 | NewRole2;
  }

  resetForm(form: Role2FormGroup, role2: Role2FormGroupInput): void {
    const role2RawValue = { ...this.getFormDefaults(), ...role2 };
    form.reset(
      {
        ...role2RawValue,
        id: { value: role2RawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): Role2FormDefaults {
    return {
      id: null,
      privileges: [],
      users: [],
    };
  }
}
