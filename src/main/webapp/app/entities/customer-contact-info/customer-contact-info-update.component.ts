import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICustomerContactInfo, CustomerContactInfo } from 'app/shared/model/customer-contact-info.model';
import { CustomerContactInfoService } from './customer-contact-info.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';

@Component({
  selector: 'jhi-customer-contact-info-update',
  templateUrl: './customer-contact-info-update.component.html',
})
export class CustomerContactInfoUpdateComponent implements OnInit {
  isSaving = false;
  addresses: IAddress[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    email: [],
    phone: [],
    mobilePhone: [],
    address: [],
  });

  constructor(
    protected customerContactInfoService: CustomerContactInfoService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerContactInfo }) => {
      this.updateForm(customerContactInfo);

      this.addressService
        .query({ filter: 'customercontactinfo-is-null' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!customerContactInfo.address || !customerContactInfo.address.id) {
            this.addresses = resBody;
          } else {
            this.addressService
              .find(customerContactInfo.address.id)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => (this.addresses = concatRes));
          }
        });
    });
  }

  updateForm(customerContactInfo: ICustomerContactInfo): void {
    this.editForm.patchValue({
      id: customerContactInfo.id,
      firstName: customerContactInfo.firstName,
      lastName: customerContactInfo.lastName,
      email: customerContactInfo.email,
      phone: customerContactInfo.phone,
      mobilePhone: customerContactInfo.mobilePhone,
      address: customerContactInfo.address,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerContactInfo = this.createFromForm();
    if (customerContactInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.customerContactInfoService.update(customerContactInfo));
    } else {
      this.subscribeToSaveResponse(this.customerContactInfoService.create(customerContactInfo));
    }
  }

  private createFromForm(): ICustomerContactInfo {
    return {
      ...new CustomerContactInfo(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      mobilePhone: this.editForm.get(['mobilePhone'])!.value,
      address: this.editForm.get(['address'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerContactInfo>>): void {
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

  trackById(index: number, item: IAddress): any {
    return item.id;
  }
}
