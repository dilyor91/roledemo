import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrivilege2 } from '../privilege-2.model';
import { Privilege2Service } from '../service/privilege-2.service';

@Injectable({ providedIn: 'root' })
export class Privilege2RoutingResolveService implements Resolve<IPrivilege2 | null> {
  constructor(protected service: Privilege2Service, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrivilege2 | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((privilege2: HttpResponse<IPrivilege2>) => {
          if (privilege2.body) {
            return of(privilege2.body);
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
