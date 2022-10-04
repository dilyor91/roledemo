import { IRole2, NewRole2 } from './role-2.model';

export const sampleWithRequiredData: IRole2 = {
  id: 53631,
};

export const sampleWithPartialData: IRole2 = {
  id: 69240,
};

export const sampleWithFullData: IRole2 = {
  id: 75788,
  name: 'Devolved',
};

export const sampleWithNewData: NewRole2 = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
