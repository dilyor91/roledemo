import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUser2, NewUser2 } from '../user-2.model';

export type PartialUpdateUser2 = Partial<IUser2> & Pick<IUser2, 'id'>;

export type EntityResponseType = HttpResponse<IUser2>;
export type EntityArrayResponseType = HttpResponse<IUser2[]>;

@Injectable({ providedIn: 'root' })
export class User2Service {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-2-s');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(user2: NewUser2): Observable<EntityResponseType> {
    return this.http.post<IUser2>(this.resourceUrl, user2, { observe: 'response' });
  }

  update(user2: IUser2): Observable<EntityResponseType> {
    return this.http.put<IUser2>(`${this.resourceUrl}/${this.getUser2Identifier(user2)}`, user2, { observe: 'response' });
  }

  partialUpdate(user2: PartialUpdateUser2): Observable<EntityResponseType> {
    return this.http.patch<IUser2>(`${this.resourceUrl}/${this.getUser2Identifier(user2)}`, user2, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUser2>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUser2[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUser2Identifier(user2: Pick<IUser2, 'id'>): number {
    return user2.id;
  }

  compareUser2(o1: Pick<IUser2, 'id'> | null, o2: Pick<IUser2, 'id'> | null): boolean {
    return o1 && o2 ? this.getUser2Identifier(o1) === this.getUser2Identifier(o2) : o1 === o2;
  }

  addUser2ToCollectionIfMissing<Type extends Pick<IUser2, 'id'>>(
    user2Collection: Type[],
    ...user2sToCheck: (Type | null | undefined)[]
  ): Type[] {
    const user2s: Type[] = user2sToCheck.filter(isPresent);
    if (user2s.length > 0) {
      const user2CollectionIdentifiers = user2Collection.map(user2Item => this.getUser2Identifier(user2Item)!);
      const user2sToAdd = user2s.filter(user2Item => {
        const user2Identifier = this.getUser2Identifier(user2Item);
        if (user2CollectionIdentifiers.includes(user2Identifier)) {
          return false;
        }
        user2CollectionIdentifiers.push(user2Identifier);
        return true;
      });
      return [...user2sToAdd, ...user2Collection];
    }
    return user2Collection;
  }
}
