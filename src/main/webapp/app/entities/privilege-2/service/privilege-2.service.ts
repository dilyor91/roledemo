import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrivilege2, NewPrivilege2 } from '../privilege-2.model';

export type PartialUpdatePrivilege2 = Partial<IPrivilege2> & Pick<IPrivilege2, 'id'>;

export type EntityResponseType = HttpResponse<IPrivilege2>;
export type EntityArrayResponseType = HttpResponse<IPrivilege2[]>;

@Injectable({ providedIn: 'root' })
export class Privilege2Service {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/privilege-2-s');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(privilege2: NewPrivilege2): Observable<EntityResponseType> {
    return this.http.post<IPrivilege2>(this.resourceUrl, privilege2, { observe: 'response' });
  }

  update(privilege2: IPrivilege2): Observable<EntityResponseType> {
    return this.http.put<IPrivilege2>(`${this.resourceUrl}/${this.getPrivilege2Identifier(privilege2)}`, privilege2, {
      observe: 'response',
    });
  }

  partialUpdate(privilege2: PartialUpdatePrivilege2): Observable<EntityResponseType> {
    return this.http.patch<IPrivilege2>(`${this.resourceUrl}/${this.getPrivilege2Identifier(privilege2)}`, privilege2, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrivilege2>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrivilege2[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrivilege2Identifier(privilege2: Pick<IPrivilege2, 'id'>): number {
    return privilege2.id;
  }

  comparePrivilege2(o1: Pick<IPrivilege2, 'id'> | null, o2: Pick<IPrivilege2, 'id'> | null): boolean {
    return o1 && o2 ? this.getPrivilege2Identifier(o1) === this.getPrivilege2Identifier(o2) : o1 === o2;
  }

  addPrivilege2ToCollectionIfMissing<Type extends Pick<IPrivilege2, 'id'>>(
    privilege2Collection: Type[],
    ...privilege2sToCheck: (Type | null | undefined)[]
  ): Type[] {
    const privilege2s: Type[] = privilege2sToCheck.filter(isPresent);
    if (privilege2s.length > 0) {
      const privilege2CollectionIdentifiers = privilege2Collection.map(privilege2Item => this.getPrivilege2Identifier(privilege2Item)!);
      const privilege2sToAdd = privilege2s.filter(privilege2Item => {
        const privilege2Identifier = this.getPrivilege2Identifier(privilege2Item);
        if (privilege2CollectionIdentifiers.includes(privilege2Identifier)) {
          return false;
        }
        privilege2CollectionIdentifiers.push(privilege2Identifier);
        return true;
      });
      return [...privilege2sToAdd, ...privilege2Collection];
    }
    return privilege2Collection;
  }
}
