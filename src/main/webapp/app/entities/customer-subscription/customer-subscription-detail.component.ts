import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerSubscription } from 'app/shared/model/customer-subscription.model';

@Component({
  selector: 'jhi-customer-subscription-detail',
  templateUrl: './customer-subscription-detail.component.html',
})
export class CustomerSubscriptionDetailComponent implements OnInit {
  customerSubscription: ICustomerSubscription | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerSubscription }) => (this.customerSubscription = customerSubscription));
  }

  previousState(): void {
    window.history.back();
  }
}
