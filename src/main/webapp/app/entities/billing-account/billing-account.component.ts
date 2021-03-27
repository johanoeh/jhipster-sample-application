import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBillingAccount } from 'app/shared/model/billing-account.model';
import { BillingAccountService } from './billing-account.service';
import { BillingAccountDeleteDialogComponent } from './billing-account-delete-dialog.component';

@Component({
  selector: 'jhi-billing-account',
  templateUrl: './billing-account.component.html',
})
export class BillingAccountComponent implements OnInit, OnDestroy {
  billingAccounts?: IBillingAccount[];
  eventSubscriber?: Subscription;

  constructor(
    protected billingAccountService: BillingAccountService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.billingAccountService.query().subscribe((res: HttpResponse<IBillingAccount[]>) => (this.billingAccounts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBillingAccounts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBillingAccount): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBillingAccounts(): void {
    this.eventSubscriber = this.eventManager.subscribe('billingAccountListModification', () => this.loadAll());
  }

  delete(billingAccount: IBillingAccount): void {
    const modalRef = this.modalService.open(BillingAccountDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.billingAccount = billingAccount;
  }
}
