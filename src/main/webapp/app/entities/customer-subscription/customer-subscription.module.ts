import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CustomerSubscriptionComponent } from './customer-subscription.component';
import { CustomerSubscriptionDetailComponent } from './customer-subscription-detail.component';
import { CustomerSubscriptionUpdateComponent } from './customer-subscription-update.component';
import { CustomerSubscriptionDeleteDialogComponent } from './customer-subscription-delete-dialog.component';
import { customerSubscriptionRoute } from './customer-subscription.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(customerSubscriptionRoute)],
  declarations: [
    CustomerSubscriptionComponent,
    CustomerSubscriptionDetailComponent,
    CustomerSubscriptionUpdateComponent,
    CustomerSubscriptionDeleteDialogComponent,
  ],
  entryComponents: [CustomerSubscriptionDeleteDialogComponent],
})
export class JhipsterSampleApplicationCustomerSubscriptionModule {}
