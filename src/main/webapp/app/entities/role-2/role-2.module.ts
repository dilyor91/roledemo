import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { Role2Component } from './list/role-2.component';
import { Role2DetailComponent } from './detail/role-2-detail.component';
import { Role2UpdateComponent } from './update/role-2-update.component';
import { Role2DeleteDialogComponent } from './delete/role-2-delete-dialog.component';
import { Role2RoutingModule } from './route/role-2-routing.module';

@NgModule({
  imports: [SharedModule, Role2RoutingModule],
  declarations: [Role2Component, Role2DetailComponent, Role2UpdateComponent, Role2DeleteDialogComponent],
})
export class Role2Module {}
