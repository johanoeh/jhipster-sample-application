import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerSubscription } from 'app/shared/model/customer-subscription.model';
import { CustomerSubscriptionService } from './customer-subscription.service';

@Component({
  templateUrl: './customer-subscription-delete-dialog.component.html',
})
export class CustomerSubscriptionDeleteDialogComponent {
  customerSubscription?: ICustomerSubscription;

  constructor(
    protected customerSubscriptionService: CustomerSubscriptionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customerSubscriptionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('customerSubscriptionListModification');
      this.activeModal.close();
    });
  }
}
