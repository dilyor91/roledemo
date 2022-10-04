import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrivilege2 } from '../privilege-2.model';
import { Privilege2Service } from '../service/privilege-2.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './privilege-2-delete-dialog.component.html',
})
export class Privilege2DeleteDialogComponent {
  privilege2?: IPrivilege2;

  constructor(protected privilege2Service: Privilege2Service, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.privilege2Service.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
