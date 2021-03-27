import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBillingAccount, BillingAccount } from 'app/shared/model/billing-account.model';
import { BillingAccountService } from './billing-account.service';
import { ICustomerSubscription } from 'app/shared/model/customer-subscription.model';
import { CustomerSubscriptionService } from 'app/entities/customer-subscription/customer-subscription.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';

type SelectableEntity = ICustomerSubscription | IAddress;

@Component({
  selector: 'jhi-billing-account-update',
  templateUrl: './billing-account-update.component.html',
})
export class BillingAccountUpdateComponent implements OnInit {
  isSaving = false;
  customersubscriptions: ICustomerSubscription[] = [];
  invoiceaddresses: IAddress[] = [];

  editForm = this.fb.group({
    id: [],
    invoiceReference: [],
    accountId: [],
    primaryBillingProfileId: [],
    exemptFromVat: [],
    invoiceContactPhone: [],
    invoiceMethod: [],
    invoiceEMailAddress: [],
    customerSubscription: [],
    invoiceAddress: [],
  });

  constructor(
    protected billingAccountService: BillingAccountService,
    protected customerSubscriptionService: CustomerSubscriptionService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billingAccount }) => {
      this.updateForm(billingAccount);

      this.customerSubscriptionService
        .query({ filter: 'billingaccount-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomerSubscription[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomerSubscription[]) => {
          if (!billingAccount.customerSubscription || !billingAccount.customerSubscription.id) {
            this.customersubscriptions = resBody;
          } else {
            this.customerSubscriptionService
              .find(billingAccount.customerSubscription.id)
              .pipe(
                map((subRes: HttpResponse<ICustomerSubscription>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomerSubscription[]) => (this.customersubscriptions = concatRes));
          }
        });

      this.addressService
        .query({ filter: 'billingaccount-is-null' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!billingAccount.invoiceAddress || !billingAccount.invoiceAddress.id) {
            this.invoiceaddresses = resBody;
          } else {
            this.addressService
              .find(billingAccount.invoiceAddress.id)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => (this.invoiceaddresses = concatRes));
          }
        });
    });
  }

  updateForm(billingAccount: IBillingAccount): void {
    this.editForm.patchValue({
      id: billingAccount.id,
      invoiceReference: billingAccount.invoiceReference,
      accountId: billingAccount.accountId,
      primaryBillingProfileId: billingAccount.primaryBillingProfileId,
      exemptFromVat: billingAccount.exemptFromVat,
      invoiceContactPhone: billingAccount.invoiceContactPhone,
      invoiceMethod: billingAccount.invoiceMethod,
      invoiceEMailAddress: billingAccount.invoiceEMailAddress,
      customerSubscription: billingAccount.customerSubscription,
      invoiceAddress: billingAccount.invoiceAddress,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const billingAccount = this.createFromForm();
    if (billingAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.billingAccountService.update(billingAccount));
    } else {
      this.subscribeToSaveResponse(this.billingAccountService.create(billingAccount));
    }
  }

  private createFromForm(): IBillingAccount {
    return {
      ...new BillingAccount(),
      id: this.editForm.get(['id'])!.value,
      invoiceReference: this.editForm.get(['invoiceReference'])!.value,
      accountId: this.editForm.get(['accountId'])!.value,
      primaryBillingProfileId: this.editForm.get(['primaryBillingProfileId'])!.value,
      exemptFromVat: this.editForm.get(['exemptFromVat'])!.value,
      invoiceContactPhone: this.editForm.get(['invoiceContactPhone'])!.value,
      invoiceMethod: this.editForm.get(['invoiceMethod'])!.value,
      invoiceEMailAddress: this.editForm.get(['invoiceEMailAddress'])!.value,
      customerSubscription: this.editForm.get(['customerSubscription'])!.value,
      invoiceAddress: this.editForm.get(['invoiceAddress'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBillingAccount>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
