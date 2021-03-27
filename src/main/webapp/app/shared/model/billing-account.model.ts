import { ICustomerSubscription } from 'app/shared/model/customer-subscription.model';
import { IAddress } from 'app/shared/model/address.model';

export interface IBillingAccount {
  id?: number;
  invoiceReference?: string;
  accountId?: string;
  primaryBillingProfileId?: string;
  exemptFromVat?: boolean;
  invoiceContactPhone?: string;
  invoiceMethod?: string;
  invoiceEMailAddress?: string;
  customerSubscription?: ICustomerSubscription;
  invoiceAddress?: IAddress;
}

export class BillingAccount implements IBillingAccount {
  constructor(
    public id?: number,
    public invoiceReference?: string,
    public accountId?: string,
    public primaryBillingProfileId?: string,
    public exemptFromVat?: boolean,
    public invoiceContactPhone?: string,
    public invoiceMethod?: string,
    public invoiceEMailAddress?: string,
    public customerSubscription?: ICustomerSubscription,
    public invoiceAddress?: IAddress
  ) {
    this.exemptFromVat = this.exemptFromVat || false;
  }
}
