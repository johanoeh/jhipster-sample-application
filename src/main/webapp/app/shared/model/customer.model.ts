import { IBillingAccount } from 'app/shared/model/billing-account.model';
import { ICustomerContactInfo } from 'app/shared/model/customer-contact-info.model';
import { IAddress } from 'app/shared/model/address.model';

export interface ICustomer {
  id?: number;
  legalId?: string;
  tscid?: string;
  corporateName?: string;
  marketCompany?: boolean;
  marketArea?: string;
  billingAccount?: IBillingAccount;
  customerContactInfo?: ICustomerContactInfo;
  shippingAddress?: IAddress;
  legalAddress?: IAddress;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public legalId?: string,
    public tscid?: string,
    public corporateName?: string,
    public marketCompany?: boolean,
    public marketArea?: string,
    public billingAccount?: IBillingAccount,
    public customerContactInfo?: ICustomerContactInfo,
    public shippingAddress?: IAddress,
    public legalAddress?: IAddress
  ) {
    this.marketCompany = this.marketCompany || false;
  }
}
