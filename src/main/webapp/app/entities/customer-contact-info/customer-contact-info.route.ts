import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustomerContactInfo, CustomerContactInfo } from 'app/shared/model/customer-contact-info.model';
import { CustomerContactInfoService } from './customer-contact-info.service';
import { CustomerContactInfoComponent } from './customer-contact-info.component';
import { CustomerContactInfoDetailComponent } from './customer-contact-info-detail.component';
import { CustomerContactInfoUpdateComponent } from './customer-contact-info-update.component';

@Injectable({ providedIn: 'root' })
export class CustomerContactInfoResolve implements Resolve<ICustomerContactInfo> {
  constructor(private service: CustomerContactInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerContactInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((customerContactInfo: HttpResponse<CustomerContactInfo>) => {
          if (customerContactInfo.body) {
            return of(customerContactInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomerContactInfo());
  }
}

export const customerContactInfoRoute: Routes = [
  {
    path: '',
    component: CustomerContactInfoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.customerContactInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomerContactInfoDetailComponent,
    resolve: {
      customerContactInfo: CustomerContactInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.customerContactInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomerContactInfoUpdateComponent,
    resolve: {
      customerContactInfo: CustomerContactInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.customerContactInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomerContactInfoUpdateComponent,
    resolve: {
      customerContactInfo: CustomerContactInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.customerContactInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
