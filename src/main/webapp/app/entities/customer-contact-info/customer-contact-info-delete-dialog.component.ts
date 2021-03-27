import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerContactInfo } from 'app/shared/model/customer-contact-info.model';
import { CustomerContactInfoService } from './customer-contact-info.service';

@Component({
  templateUrl: './customer-contact-info-delete-dialog.component.html',
})
export class CustomerContactInfoDeleteDialogComponent {
  customerContactInfo?: ICustomerContactInfo;

  constructor(
    protected customerContactInfoService: CustomerContactInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customerContactInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('customerContactInfoListModification');
      this.activeModal.close();
    });
  }
}
