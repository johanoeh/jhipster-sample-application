import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { BillingAccountComponent } from './billing-account.component';
import { BillingAccountDetailComponent } from './billing-account-detail.component';
import { BillingAccountUpdateComponent } from './billing-account-update.component';
import { BillingAccountDeleteDialogComponent } from './billing-account-delete-dialog.component';
import { billingAccountRoute } from './billing-account.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(billingAccountRoute)],
  declarations: [
    BillingAccountComponent,
    BillingAccountDetailComponent,
    BillingAccountUpdateComponent,
    BillingAccountDeleteDialogComponent,
  ],
  entryComponents: [BillingAccountDeleteDialogComponent],
})
export class JhipsterSampleApplicationBillingAccountModule {}
