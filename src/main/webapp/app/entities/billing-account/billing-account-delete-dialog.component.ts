import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBillingAccount } from 'app/shared/model/billing-account.model';
import { BillingAccountService } from './billing-account.service';

@Component({
  templateUrl: './billing-account-delete-dialog.component.html',
})
export class BillingAccountDeleteDialogComponent {
  billingAccount?: IBillingAccount;

  constructor(
    protected billingAccountService: BillingAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.billingAccountService.delete(id).subscribe(() => {
      this.eventManager.broadcast('billingAccountListModification');
      this.activeModal.close();
    });
  }
}
