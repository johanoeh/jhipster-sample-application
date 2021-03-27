import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.JhipsterSampleApplicationAddressModule),
      },
      {
        path: 'billing-account',
        loadChildren: () => import('./billing-account/billing-account.module').then(m => m.JhipsterSampleApplicationBillingAccountModule),
      },
      {
        path: 'customer-subscription',
        loadChildren: () =>
          import('./customer-subscription/customer-subscription.module').then(m => m.JhipsterSampleApplicationCustomerSubscriptionModule),
      },
      {
        path: 'customer-contact-info',
        loadChildren: () =>
          import('./customer-contact-info/customer-contact-info.module').then(m => m.JhipsterSampleApplicationCustomerContactInfoModule),
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.JhipsterSampleApplicationCustomerModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhipsterSampleApplicationEntityModule {}
