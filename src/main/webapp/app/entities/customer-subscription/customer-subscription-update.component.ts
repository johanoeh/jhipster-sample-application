import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICustomerSubscription, CustomerSubscription } from 'app/shared/model/customer-subscription.model';
import { CustomerSubscriptionService } from './customer-subscription.service';

@Component({
  selector: 'jhi-customer-subscription-update',
  templateUrl: './customer-subscription-update.component.html',
})
export class CustomerSubscriptionUpdateComponent implements OnInit {
  isSaving = false;
  startDateDp: any;
  endDateDp: any;
  changeDateDp: any;

  editForm = this.fb.group({
    id: [],
    emnId: [],
    type: [],
    agreement: [],
    startDate: [],
    endDate: [],
    changeDate: [],
    quarterlyFee: [],
    oneTimeFee: [],
    invoiceText: [],
  });

  constructor(
    protected customerSubscriptionService: CustomerSubscriptionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerSubscription }) => {
      this.updateForm(customerSubscription);
    });
  }

  updateForm(customerSubscription: ICustomerSubscription): void {
    this.editForm.patchValue({
      id: customerSubscription.id,
      emnId: customerSubscription.emnId,
      type: customerSubscription.type,
      agreement: customerSubscription.agreement,
      startDate: customerSubscription.startDate,
      endDate: customerSubscription.endDate,
      changeDate: customerSubscription.changeDate,
      quarterlyFee: customerSubscription.quarterlyFee,
      oneTimeFee: customerSubscription.oneTimeFee,
      invoiceText: customerSubscription.invoiceText,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerSubscription = this.createFromForm();
    if (customerSubscription.id !== undefined) {
      this.subscribeToSaveResponse(this.customerSubscriptionService.update(customerSubscription));
    } else {
      this.subscribeToSaveResponse(this.customerSubscriptionService.create(customerSubscription));
    }
  }

  private createFromForm(): ICustomerSubscription {
    return {
      ...new CustomerSubscription(),
      id: this.editForm.get(['id'])!.value,
      emnId: this.editForm.get(['emnId'])!.value,
      type: this.editForm.get(['type'])!.value,
      agreement: this.editForm.get(['agreement'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      changeDate: this.editForm.get(['changeDate'])!.value,
      quarterlyFee: this.editForm.get(['quarterlyFee'])!.value,
      oneTimeFee: this.editForm.get(['oneTimeFee'])!.value,
      invoiceText: this.editForm.get(['invoiceText'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerSubscription>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
