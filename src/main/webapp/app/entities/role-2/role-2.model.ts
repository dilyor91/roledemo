import { IPrivilege2 } from 'app/entities/privilege-2/privilege-2.model';
import { IUser2 } from 'app/entities/user-2/user-2.model';

export interface IRole2 {
  id: number;
  name?: string | null;
  privileges?: Pick<IPrivilege2, 'id'>[] | null;
  users?: Pick<IUser2, 'id'>[] | null;
}

export type NewRole2 = Omit<IRole2, 'id'> & { id: null };
