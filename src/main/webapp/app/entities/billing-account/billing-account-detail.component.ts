import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBillingAccount } from 'app/shared/model/billing-account.model';

@Component({
  selector: 'jhi-billing-account-detail',
  templateUrl: './billing-account-detail.component.html',
})
export class BillingAccountDetailComponent implements OnInit {
  billingAccount: IBillingAccount | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billingAccount }) => (this.billingAccount = billingAccount));
  }

  previousState(): void {
    window.history.back();
  }
}
