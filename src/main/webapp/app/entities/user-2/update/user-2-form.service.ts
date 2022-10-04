import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUser2, NewUser2 } from '../user-2.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUser2 for edit and NewUser2FormGroupInput for create.
 */
type User2FormGroupInput = IUser2 | PartialWithRequiredKeyOf<NewUser2>;

type User2FormDefaults = Pick<NewUser2, 'id' | 'role2s'>;

type User2FormGroupContent = {
  id: FormControl<IUser2['id'] | NewUser2['id']>;
  name: FormControl<IUser2['name']>;
  position: FormControl<IUser2['position']>;
  role2s: FormControl<IUser2['role2s']>;
};

export type User2FormGroup = FormGroup<User2FormGroupContent>;

@Injectable({ providedIn: 'root' })
export class User2FormService {
  createUser2FormGroup(user2: User2FormGroupInput = { id: null }): User2FormGroup {
    const user2RawValue = {
      ...this.getFormDefaults(),
      ...user2,
    };
    return new FormGroup<User2FormGroupContent>({
      id: new FormControl(
        { value: user2RawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(user2RawValue.name),
      position: new FormControl(user2RawValue.position),
      role2s: new FormControl(user2RawValue.role2s ?? []),
    });
  }

  getUser2(form: User2FormGroup): IUser2 | NewUser2 {
    return form.getRawValue() as IUser2 | NewUser2;
  }

  resetForm(form: User2FormGroup, user2: User2FormGroupInput): void {
    const user2RawValue = { ...this.getFormDefaults(), ...user2 };
    form.reset(
      {
        ...user2RawValue,
        id: { value: user2RawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): User2FormDefaults {
    return {
      id: null,
      role2s: [],
    };
  }
}
