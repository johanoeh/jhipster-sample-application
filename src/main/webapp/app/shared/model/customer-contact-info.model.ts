import { IAddress } from 'app/shared/model/address.model';

export interface ICustomerContactInfo {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phone?: string;
  mobilePhone?: string;
  address?: IAddress;
}

export class CustomerContactInfo implements ICustomerContactInfo {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phone?: string,
    public mobilePhone?: string,
    public address?: IAddress
  ) {}
}
