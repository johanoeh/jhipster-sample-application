import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustomerSubscription, CustomerSubscription } from 'app/shared/model/customer-subscription.model';
import { CustomerSubscriptionService } from './customer-subscription.service';
import { CustomerSubscriptionComponent } from './customer-subscription.component';
import { CustomerSubscriptionDetailComponent } from './customer-subscription-detail.component';
import { CustomerSubscriptionUpdateComponent } from './customer-subscription-update.component';

@Injectable({ providedIn: 'root' })
export class CustomerSubscriptionResolve implements Resolve<ICustomerSubscription> {
  constructor(private service: CustomerSubscriptionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerSubscription> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((customerSubscription: HttpResponse<CustomerSubscription>) => {
          if (customerSubscription.body) {
            return of(customerSubscription.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomerSubscription());
  }
}

export const customerSubscriptionRoute: Routes = [
  {
    path: '',
    component: CustomerSubscriptionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.customerSubscription.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomerSubscriptionDetailComponent,
    resolve: {
      customerSubscription: CustomerSubscriptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.customerSubscription.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomerSubscriptionUpdateComponent,
    resolve: {
      customerSubscription: CustomerSubscriptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.customerSubscription.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomerSubscriptionUpdateComponent,
    resolve: {
      customerSubscription: CustomerSubscriptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.customerSubscription.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
