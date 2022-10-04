import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'privilege-2',
        data: { pageTitle: 'roleAndPermissionDemoApp.privilege2.home.title' },
        loadChildren: () => import('./privilege-2/privilege-2.module').then(m => m.Privilege2Module),
      },
      {
        path: 'role-2',
        data: { pageTitle: 'roleAndPermissionDemoApp.role2.home.title' },
        loadChildren: () => import('./role-2/role-2.module').then(m => m.Role2Module),
      },
      {
        path: 'user-2',
        data: { pageTitle: 'roleAndPermissionDemoApp.user2.home.title' },
        loadChildren: () => import('./user-2/user-2.module').then(m => m.User2Module),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
