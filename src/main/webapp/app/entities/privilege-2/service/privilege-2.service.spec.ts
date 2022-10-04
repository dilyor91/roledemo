import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPrivilege2 } from '../privilege-2.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../privilege-2.test-samples';

import { Privilege2Service } from './privilege-2.service';

const requireRestSample: IPrivilege2 = {
  ...sampleWithRequiredData,
};

describe('Privilege2 Service', () => {
  let service: Privilege2Service;
  let httpMock: HttpTestingController;
  let expectedResult: IPrivilege2 | IPrivilege2[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(Privilege2Service);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Privilege2', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const privilege2 = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(privilege2).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Privilege2', () => {
      const privilege2 = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(privilege2).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Privilege2', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Privilege2', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Privilege2', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPrivilege2ToCollectionIfMissing', () => {
      it('should add a Privilege2 to an empty array', () => {
        const privilege2: IPrivilege2 = sampleWithRequiredData;
        expectedResult = service.addPrivilege2ToCollectionIfMissing([], privilege2);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(privilege2);
      });

      it('should not add a Privilege2 to an array that contains it', () => {
        const privilege2: IPrivilege2 = sampleWithRequiredData;
        const privilege2Collection: IPrivilege2[] = [
          {
            ...privilege2,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPrivilege2ToCollectionIfMissing(privilege2Collection, privilege2);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Privilege2 to an array that doesn't contain it", () => {
        const privilege2: IPrivilege2 = sampleWithRequiredData;
        const privilege2Collection: IPrivilege2[] = [sampleWithPartialData];
        expectedResult = service.addPrivilege2ToCollectionIfMissing(privilege2Collection, privilege2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(privilege2);
      });

      it('should add only unique Privilege2 to an array', () => {
        const privilege2Array: IPrivilege2[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const privilege2Collection: IPrivilege2[] = [sampleWithRequiredData];
        expectedResult = service.addPrivilege2ToCollectionIfMissing(privilege2Collection, ...privilege2Array);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const privilege2: IPrivilege2 = sampleWithRequiredData;
        const privilege22: IPrivilege2 = sampleWithPartialData;
        expectedResult = service.addPrivilege2ToCollectionIfMissing([], privilege2, privilege22);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(privilege2);
        expect(expectedResult).toContain(privilege22);
      });

      it('should accept null and undefined values', () => {
        const privilege2: IPrivilege2 = sampleWithRequiredData;
        expectedResult = service.addPrivilege2ToCollectionIfMissing([], null, privilege2, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(privilege2);
      });

      it('should return initial array if no Privilege2 is added', () => {
        const privilege2Collection: IPrivilege2[] = [sampleWithRequiredData];
        expectedResult = service.addPrivilege2ToCollectionIfMissing(privilege2Collection, undefined, null);
        expect(expectedResult).toEqual(privilege2Collection);
      });
    });

    describe('comparePrivilege2', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePrivilege2(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePrivilege2(entity1, entity2);
        const compareResult2 = service.comparePrivilege2(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePrivilege2(entity1, entity2);
        const compareResult2 = service.comparePrivilege2(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePrivilege2(entity1, entity2);
        const compareResult2 = service.comparePrivilege2(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
