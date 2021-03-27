import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomerContactInfo } from 'app/shared/model/customer-contact-info.model';
import { CustomerContactInfoService } from './customer-contact-info.service';
import { CustomerContactInfoDeleteDialogComponent } from './customer-contact-info-delete-dialog.component';

@Component({
  selector: 'jhi-customer-contact-info',
  templateUrl: './customer-contact-info.component.html',
})
export class CustomerContactInfoComponent implements OnInit, OnDestroy {
  customerContactInfos?: ICustomerContactInfo[];
  eventSubscriber?: Subscription;

  constructor(
    protected customerContactInfoService: CustomerContactInfoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.customerContactInfoService
      .query()
      .subscribe((res: HttpResponse<ICustomerContactInfo[]>) => (this.customerContactInfos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCustomerContactInfos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICustomerContactInfo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCustomerContactInfos(): void {
    this.eventSubscriber = this.eventManager.subscribe('customerContactInfoListModification', () => this.loadAll());
  }

  delete(customerContactInfo: ICustomerContactInfo): void {
    const modalRef = this.modalService.open(CustomerContactInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customerContactInfo = customerContactInfo;
  }
}
