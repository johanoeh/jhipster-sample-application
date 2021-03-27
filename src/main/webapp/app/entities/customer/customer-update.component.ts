import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { IBillingAccount } from 'app/shared/model/billing-account.model';
import { BillingAccountService } from 'app/entities/billing-account/billing-account.service';
import { ICustomerContactInfo } from 'app/shared/model/customer-contact-info.model';
import { CustomerContactInfoService } from 'app/entities/customer-contact-info/customer-contact-info.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';

type SelectableEntity = IBillingAccount | ICustomerContactInfo | IAddress;

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html',
})
export class CustomerUpdateComponent implements OnInit {
  isSaving = false;
  billingaccounts: IBillingAccount[] = [];
  customercontactinfos: ICustomerContactInfo[] = [];
  shippingaddresses: IAddress[] = [];
  legaladdresses: IAddress[] = [];

  editForm = this.fb.group({
    id: [],
    legalId: [],
    tscid: [],
    corporateName: [],
    marketCompany: [],
    marketArea: [],
    billingAccount: [],
    customerContactInfo: [],
    shippingAddress: [],
    legalAddress: [],
  });

  constructor(
    protected customerService: CustomerService,
    protected billingAccountService: BillingAccountService,
    protected customerContactInfoService: CustomerContactInfoService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);

      this.billingAccountService
        .query({ filter: 'customer-is-null' })
        .pipe(
          map((res: HttpResponse<IBillingAccount[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBillingAccount[]) => {
          if (!customer.billingAccount || !customer.billingAccount.id) {
            this.billingaccounts = resBody;
          } else {
            this.billingAccountService
              .find(customer.billingAccount.id)
              .pipe(
                map((subRes: HttpResponse<IBillingAccount>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBillingAccount[]) => (this.billingaccounts = concatRes));
          }
        });

      this.customerContactInfoService
        .query({ filter: 'customer-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomerContactInfo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomerContactInfo[]) => {
          if (!customer.customerContactInfo || !customer.customerContactInfo.id) {
            this.customercontactinfos = resBody;
          } else {
            this.customerContactInfoService
              .find(customer.customerContactInfo.id)
              .pipe(
                map((subRes: HttpResponse<ICustomerContactInfo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomerContactInfo[]) => (this.customercontactinfos = concatRes));
          }
        });

      this.addressService
        .query({ filter: 'customer-is-null' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!customer.shippingAddress || !customer.shippingAddress.id) {
            this.shippingaddresses = resBody;
          } else {
            this.addressService
              .find(customer.shippingAddress.id)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => (this.shippingaddresses = concatRes));
          }
        });

      this.addressService
        .query({ filter: 'customer-is-null' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!customer.legalAddress || !customer.legalAddress.id) {
            this.legaladdresses = resBody;
          } else {
            this.addressService
              .find(customer.legalAddress.id)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => (this.legaladdresses = concatRes));
          }
        });
    });
  }

  updateForm(customer: ICustomer): void {
    this.editForm.patchValue({
      id: customer.id,
      legalId: customer.legalId,
      tscid: customer.tscid,
      corporateName: customer.corporateName,
      marketCompany: customer.marketCompany,
      marketArea: customer.marketArea,
      billingAccount: customer.billingAccount,
      customerContactInfo: customer.customerContactInfo,
      shippingAddress: customer.shippingAddress,
      legalAddress: customer.legalAddress,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
      id: this.editForm.get(['id'])!.value,
      legalId: this.editForm.get(['legalId'])!.value,
      tscid: this.editForm.get(['tscid'])!.value,
      corporateName: this.editForm.get(['corporateName'])!.value,
      marketCompany: this.editForm.get(['marketCompany'])!.value,
      marketArea: this.editForm.get(['marketArea'])!.value,
      billingAccount: this.editForm.get(['billingAccount'])!.value,
      customerContactInfo: this.editForm.get(['customerContactInfo'])!.value,
      shippingAddress: this.editForm.get(['shippingAddress'])!.value,
      legalAddress: this.editForm.get(['legalAddress'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>): void {
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
