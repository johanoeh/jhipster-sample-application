import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBillingAccount, BillingAccount } from 'app/shared/model/billing-account.model';
import { BillingAccountService } from './billing-account.service';
import { BillingAccountComponent } from './billing-account.component';
import { BillingAccountDetailComponent } from './billing-account-detail.component';
import { BillingAccountUpdateComponent } from './billing-account-update.component';

@Injectable({ providedIn: 'root' })
export class BillingAccountResolve implements Resolve<IBillingAccount> {
  constructor(private service: BillingAccountService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBillingAccount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((billingAccount: HttpResponse<BillingAccount>) => {
          if (billingAccount.body) {
            return of(billingAccount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BillingAccount());
  }
}

export const billingAccountRoute: Routes = [
  {
    path: '',
    component: BillingAccountComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.billingAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BillingAccountDetailComponent,
    resolve: {
      billingAccount: BillingAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.billingAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BillingAccountUpdateComponent,
    resolve: {
      billingAccount: BillingAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.billingAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BillingAccountUpdateComponent,
    resolve: {
      billingAccount: BillingAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.billingAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
