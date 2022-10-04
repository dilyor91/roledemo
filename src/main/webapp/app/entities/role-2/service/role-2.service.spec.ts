import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRole2 } from '../role-2.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../role-2.test-samples';

import { Role2Service } from './role-2.service';

const requireRestSample: IRole2 = {
  ...sampleWithRequiredData,
};

describe('Role2 Service', () => {
  let service: Role2Service;
  let httpMock: HttpTestingController;
  let expectedResult: IRole2 | IRole2[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(Role2Service);
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

    it('should create a Role2', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const role2 = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(role2).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Role2', () => {
      const role2 = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(role2).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Role2', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Role2', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Role2', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRole2ToCollectionIfMissing', () => {
      it('should add a Role2 to an empty array', () => {
        const role2: IRole2 = sampleWithRequiredData;
        expectedResult = service.addRole2ToCollectionIfMissing([], role2);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(role2);
      });

      it('should not add a Role2 to an array that contains it', () => {
        const role2: IRole2 = sampleWithRequiredData;
        const role2Collection: IRole2[] = [
          {
            ...role2,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRole2ToCollectionIfMissing(role2Collection, role2);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Role2 to an array that doesn't contain it", () => {
        const role2: IRole2 = sampleWithRequiredData;
        const role2Collection: IRole2[] = [sampleWithPartialData];
        expectedResult = service.addRole2ToCollectionIfMissing(role2Collection, role2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(role2);
      });

      it('should add only unique Role2 to an array', () => {
        const role2Array: IRole2[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const role2Collection: IRole2[] = [sampleWithRequiredData];
        expectedResult = service.addRole2ToCollectionIfMissing(role2Collection, ...role2Array);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const role2: IRole2 = sampleWithRequiredData;
        const role22: IRole2 = sampleWithPartialData;
        expectedResult = service.addRole2ToCollectionIfMissing([], role2, role22);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(role2);
        expect(expectedResult).toContain(role22);
      });

      it('should accept null and undefined values', () => {
        const role2: IRole2 = sampleWithRequiredData;
        expectedResult = service.addRole2ToCollectionIfMissing([], null, role2, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(role2);
      });

      it('should return initial array if no Role2 is added', () => {
        const role2Collection: IRole2[] = [sampleWithRequiredData];
        expectedResult = service.addRole2ToCollectionIfMissing(role2Collection, undefined, null);
        expect(expectedResult).toEqual(role2Collection);
      });
    });

    describe('compareRole2', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRole2(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRole2(entity1, entity2);
        const compareResult2 = service.compareRole2(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRole2(entity1, entity2);
        const compareResult2 = service.compareRole2(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRole2(entity1, entity2);
        const compareResult2 = service.compareRole2(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
