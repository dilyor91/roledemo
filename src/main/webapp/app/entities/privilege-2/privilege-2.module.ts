import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { Privilege2Component } from './list/privilege-2.component';
import { Privilege2DetailComponent } from './detail/privilege-2-detail.component';
import { Privilege2UpdateComponent } from './update/privilege-2-update.component';
import { Privilege2DeleteDialogComponent } from './delete/privilege-2-delete-dialog.component';
import { Privilege2RoutingModule } from './route/privilege-2-routing.module';

@NgModule({
  imports: [SharedModule, Privilege2RoutingModule],
  declarations: [Privilege2Component, Privilege2DetailComponent, Privilege2UpdateComponent, Privilege2DeleteDialogComponent],
})
export class Privilege2Module {}
