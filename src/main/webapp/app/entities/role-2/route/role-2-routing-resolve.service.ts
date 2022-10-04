import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRole2 } from '../role-2.model';
import { Role2Service } from '../service/role-2.service';

@Injectable({ providedIn: 'root' })
export class Role2RoutingResolveService implements Resolve<IRole2 | null> {
  constructor(protected service: Role2Service, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRole2 | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((role2: HttpResponse<IRole2>) => {
          if (role2.body) {
            return of(role2.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
