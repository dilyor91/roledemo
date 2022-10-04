import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRole2, NewRole2 } from '../role-2.model';

export type PartialUpdateRole2 = Partial<IRole2> & Pick<IRole2, 'id'>;

export type EntityResponseType = HttpResponse<IRole2>;
export type EntityArrayResponseType = HttpResponse<IRole2[]>;

@Injectable({ providedIn: 'root' })
export class Role2Service {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/role-2-s');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(role2: NewRole2): Observable<EntityResponseType> {
    return this.http.post<IRole2>(this.resourceUrl, role2, { observe: 'response' });
  }

  update(role2: IRole2): Observable<EntityResponseType> {
    return this.http.put<IRole2>(`${this.resourceUrl}/${this.getRole2Identifier(role2)}`, role2, { observe: 'response' });
  }

  partialUpdate(role2: PartialUpdateRole2): Observable<EntityResponseType> {
    return this.http.patch<IRole2>(`${this.resourceUrl}/${this.getRole2Identifier(role2)}`, role2, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRole2>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRole2[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRole2Identifier(role2: Pick<IRole2, 'id'>): number {
    return role2.id;
  }

  compareRole2(o1: Pick<IRole2, 'id'> | null, o2: Pick<IRole2, 'id'> | null): boolean {
    return o1 && o2 ? this.getRole2Identifier(o1) === this.getRole2Identifier(o2) : o1 === o2;
  }

  addRole2ToCollectionIfMissing<Type extends Pick<IRole2, 'id'>>(
    role2Collection: Type[],
    ...role2sToCheck: (Type | null | undefined)[]
  ): Type[] {
    const role2s: Type[] = role2sToCheck.filter(isPresent);
    if (role2s.length > 0) {
      const role2CollectionIdentifiers = role2Collection.map(role2Item => this.getRole2Identifier(role2Item)!);
      const role2sToAdd = role2s.filter(role2Item => {
        const role2Identifier = this.getRole2Identifier(role2Item);
        if (role2CollectionIdentifiers.includes(role2Identifier)) {
          return false;
        }
        role2CollectionIdentifiers.push(role2Identifier);
        return true;
      });
      return [...role2sToAdd, ...role2Collection];
    }
    return role2Collection;
  }
}
