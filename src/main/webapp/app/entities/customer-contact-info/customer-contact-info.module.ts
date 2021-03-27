import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CustomerContactInfoComponent } from './customer-contact-info.component';
import { CustomerContactInfoDetailComponent } from './customer-contact-info-detail.component';
import { CustomerContactInfoUpdateComponent } from './customer-contact-info-update.component';
import { CustomerContactInfoDeleteDialogComponent } from './customer-contact-info-delete-dialog.component';
import { customerContactInfoRoute } from './customer-contact-info.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(customerContactInfoRoute)],
  declarations: [
    CustomerContactInfoComponent,
    CustomerContactInfoDetailComponent,
    CustomerContactInfoUpdateComponent,
    CustomerContactInfoDeleteDialogComponent,
  ],
  entryComponents: [CustomerContactInfoDeleteDialogComponent],
})
export class JhipsterSampleApplicationCustomerContactInfoModule {}
