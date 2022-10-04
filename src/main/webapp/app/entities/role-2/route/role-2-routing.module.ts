import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { Role2Component } from '../list/role-2.component';
import { Role2DetailComponent } from '../detail/role-2-detail.component';
import { Role2UpdateComponent } from '../update/role-2-update.component';
import { Role2RoutingResolveService } from './role-2-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const role2Route: Routes = [
  {
    path: '',
    component: Role2Component,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: Role2DetailComponent,
    resolve: {
      role2: Role2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: Role2UpdateComponent,
    resolve: {
      role2: Role2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: Role2UpdateComponent,
    resolve: {
      role2: Role2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(role2Route)],
  exports: [RouterModule],
})
export class Role2RoutingModule {}
