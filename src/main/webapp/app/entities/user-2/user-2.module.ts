import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { User2Component } from './list/user-2.component';
import { User2DetailComponent } from './detail/user-2-detail.component';
import { User2UpdateComponent } from './update/user-2-update.component';
import { User2DeleteDialogComponent } from './delete/user-2-delete-dialog.component';
import { User2RoutingModule } from './route/user-2-routing.module';

@NgModule({
  imports: [SharedModule, User2RoutingModule],
  declarations: [User2Component, User2DetailComponent, User2UpdateComponent, User2DeleteDialogComponent],
})
export class User2Module {}
