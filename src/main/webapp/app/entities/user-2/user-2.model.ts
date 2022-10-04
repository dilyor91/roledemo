import { IRole2 } from 'app/entities/role-2/role-2.model';

export interface IUser2 {
  id: number;
  name?: string | null;
  position?: string | null;
  role2s?: Pick<IRole2, 'id'>[] | null;
}

export type NewUser2 = Omit<IUser2, 'id'> & { id: null };
