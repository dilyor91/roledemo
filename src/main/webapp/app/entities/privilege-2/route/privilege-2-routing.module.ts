import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { Privilege2Component } from '../list/privilege-2.component';
import { Privilege2DetailComponent } from '../detail/privilege-2-detail.component';
import { Privilege2UpdateComponent } from '../update/privilege-2-update.component';
import { Privilege2RoutingResolveService } from './privilege-2-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const privilege2Route: Routes = [
  {
    path: '',
    component: Privilege2Component,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: Privilege2DetailComponent,
    resolve: {
      privilege2: Privilege2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: Privilege2UpdateComponent,
    resolve: {
      privilege2: Privilege2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: Privilege2UpdateComponent,
    resolve: {
      privilege2: Privilege2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(privilege2Route)],
  exports: [RouterModule],
})
export class Privilege2RoutingModule {}
