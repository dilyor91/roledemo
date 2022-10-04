import { IUser2, NewUser2 } from './user-2.model';

export const sampleWithRequiredData: IUser2 = {
  id: 36246,
};

export const sampleWithPartialData: IUser2 = {
  id: 44900,
  name: 'Towels Automotive',
  position: 'XML',
};

export const sampleWithFullData: IUser2 = {
  id: 83403,
  name: 'Account',
  position: 'index deposit',
};

export const sampleWithNewData: NewUser2 = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
