import { IRole2 } from 'app/entities/role-2/role-2.model';

export interface IPrivilege2 {
  id: number;
  name?: string | null;
  role2s?: Pick<IRole2, 'id'>[] | null;
}

export type NewPrivilege2 = Omit<IPrivilege2, 'id'> & { id: null };
