import { IPrivilege2, NewPrivilege2 } from './privilege-2.model';

export const sampleWithRequiredData: IPrivilege2 = {
  id: 11982,
};

export const sampleWithPartialData: IPrivilege2 = {
  id: 4666,
  name: 'SCSI',
};

export const sampleWithFullData: IPrivilege2 = {
  id: 15190,
  name: 'Reunion',
};

export const sampleWithNewData: NewPrivilege2 = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
