import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUser2 } from '../user-2.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../user-2.test-samples';

import { User2Service } from './user-2.service';

const requireRestSample: IUser2 = {
  ...sampleWithRequiredData,
};

describe('User2 Service', () => {
  let service: User2Service;
  let httpMock: HttpTestingController;
  let expectedResult: IUser2 | IUser2[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(User2Service);
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

    it('should create a User2', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const user2 = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(user2).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a User2', () => {
      const user2 = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(user2).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a User2', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of User2', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a User2', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUser2ToCollectionIfMissing', () => {
      it('should add a User2 to an empty array', () => {
        const user2: IUser2 = sampleWithRequiredData;
        expectedResult = service.addUser2ToCollectionIfMissing([], user2);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(user2);
      });

      it('should not add a User2 to an array that contains it', () => {
        const user2: IUser2 = sampleWithRequiredData;
        const user2Collection: IUser2[] = [
          {
            ...user2,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUser2ToCollectionIfMissing(user2Collection, user2);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a User2 to an array that doesn't contain it", () => {
        const user2: IUser2 = sampleWithRequiredData;
        const user2Collection: IUser2[] = [sampleWithPartialData];
        expectedResult = service.addUser2ToCollectionIfMissing(user2Collection, user2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(user2);
      });

      it('should add only unique User2 to an array', () => {
        const user2Array: IUser2[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const user2Collection: IUser2[] = [sampleWithRequiredData];
        expectedResult = service.addUser2ToCollectionIfMissing(user2Collection, ...user2Array);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const user2: IUser2 = sampleWithRequiredData;
        const user22: IUser2 = sampleWithPartialData;
        expectedResult = service.addUser2ToCollectionIfMissing([], user2, user22);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(user2);
        expect(expectedResult).toContain(user22);
      });

      it('should accept null and undefined values', () => {
        const user2: IUser2 = sampleWithRequiredData;
        expectedResult = service.addUser2ToCollectionIfMissing([], null, user2, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(user2);
      });

      it('should return initial array if no User2 is added', () => {
        const user2Collection: IUser2[] = [sampleWithRequiredData];
        expectedResult = service.addUser2ToCollectionIfMissing(user2Collection, undefined, null);
        expect(expectedResult).toEqual(user2Collection);
      });
    });

    describe('compareUser2', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUser2(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUser2(entity1, entity2);
        const compareResult2 = service.compareUser2(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUser2(entity1, entity2);
        const compareResult2 = service.compareUser2(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUser2(entity1, entity2);
        const compareResult2 = service.compareUser2(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
