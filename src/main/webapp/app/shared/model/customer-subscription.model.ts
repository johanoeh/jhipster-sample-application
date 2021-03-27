import { Moment } from 'moment';

export interface ICustomerSubscription {
  id?: number;
  emnId?: string;
  type?: string;
  agreement?: string;
  startDate?: Moment;
  endDate?: Moment;
  changeDate?: Moment;
  quarterlyFee?: string;
  oneTimeFee?: string;
  invoiceText?: string;
}

export class CustomerSubscription implements ICustomerSubscription {
  constructor(
    public id?: number,
    public emnId?: string,
    public type?: string,
    public agreement?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public changeDate?: Moment,
    public quarterlyFee?: string,
    public oneTimeFee?: string,
    public invoiceText?: string
  ) {}
}
