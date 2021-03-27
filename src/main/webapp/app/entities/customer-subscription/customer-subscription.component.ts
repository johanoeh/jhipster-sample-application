import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomerSubscription } from 'app/shared/model/customer-subscription.model';
import { CustomerSubscriptionService } from './customer-subscription.service';
import { CustomerSubscriptionDeleteDialogComponent } from './customer-subscription-delete-dialog.component';

@Component({
  selector: 'jhi-customer-subscription',
  templateUrl: './customer-subscription.component.html',
})
export class CustomerSubscriptionComponent implements OnInit, OnDestroy {
  customerSubscriptions?: ICustomerSubscription[];
  eventSubscriber?: Subscription;

  constructor(
    protected customerSubscriptionService: CustomerSubscriptionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.customerSubscriptionService
      .query()
      .subscribe((res: HttpResponse<ICustomerSubscription[]>) => (this.customerSubscriptions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCustomerSubscriptions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICustomerSubscription): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCustomerSubscriptions(): void {
    this.eventSubscriber = this.eventManager.subscribe('customerSubscriptionListModification', () => this.loadAll());
  }

  delete(customerSubscription: ICustomerSubscription): void {
    const modalRef = this.modalService.open(CustomerSubscriptionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customerSubscription = customerSubscription;
  }
}
