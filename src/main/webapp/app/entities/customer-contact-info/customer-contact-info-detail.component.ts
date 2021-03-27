import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerContactInfo } from 'app/shared/model/customer-contact-info.model';

@Component({
  selector: 'jhi-customer-contact-info-detail',
  templateUrl: './customer-contact-info-detail.component.html',
})
export class CustomerContactInfoDetailComponent implements OnInit {
  customerContactInfo: ICustomerContactInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerContactInfo }) => (this.customerContactInfo = customerContactInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
