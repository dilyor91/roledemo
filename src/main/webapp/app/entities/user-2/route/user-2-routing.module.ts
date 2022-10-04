import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { User2Component } from '../list/user-2.component';
import { User2DetailComponent } from '../detail/user-2-detail.component';
import { User2UpdateComponent } from '../update/user-2-update.component';
import { User2RoutingResolveService } from './user-2-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const user2Route: Routes = [
  {
    path: '',
    component: User2Component,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: User2DetailComponent,
    resolve: {
      user2: User2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: User2UpdateComponent,
    resolve: {
      user2: User2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: User2UpdateComponent,
    resolve: {
      user2: User2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(user2Route)],
  exports: [RouterModule],
})
export class User2RoutingModule {}
