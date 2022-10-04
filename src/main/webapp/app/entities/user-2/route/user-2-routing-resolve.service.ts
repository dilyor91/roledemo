import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUser2 } from '../user-2.model';
import { User2Service } from '../service/user-2.service';

@Injectable({ providedIn: 'root' })
export class User2RoutingResolveService implements Resolve<IUser2 | null> {
  constructor(protected service: User2Service, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUser2 | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((user2: HttpResponse<IUser2>) => {
          if (user2.body) {
            return of(user2.body);
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
