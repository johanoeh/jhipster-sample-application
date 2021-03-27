import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustomerSubscription } from 'app/shared/model/customer-subscription.model';

type EntityResponseType = HttpResponse<ICustomerSubscription>;
type EntityArrayResponseType = HttpResponse<ICustomerSubscription[]>;

@Injectable({ providedIn: 'root' })
export class CustomerSubscriptionService {
  public resourceUrl = SERVER_API_URL + 'api/customer-subscriptions';

  constructor(protected http: HttpClient) {}

  create(customerSubscription: ICustomerSubscription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerSubscription);
    return this.http
      .post<ICustomerSubscription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customerSubscription: ICustomerSubscription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerSubscription);
    return this.http
      .put<ICustomerSubscription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomerSubscription>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomerSubscription[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(customerSubscription: ICustomerSubscription): ICustomerSubscription {
    const copy: ICustomerSubscription = Object.assign({}, customerSubscription, {
      startDate:
        customerSubscription.startDate && customerSubscription.startDate.isValid()
          ? customerSubscription.startDate.format(DATE_FORMAT)
          : undefined,
      endDate:
        customerSubscription.endDate && customerSubscription.endDate.isValid()
          ? customerSubscription.endDate.format(DATE_FORMAT)
          : undefined,
      changeDate:
        customerSubscription.changeDate && customerSubscription.changeDate.isValid()
          ? customerSubscription.changeDate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
      res.body.changeDate = res.body.changeDate ? moment(res.body.changeDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((customerSubscription: ICustomerSubscription) => {
        customerSubscription.startDate = customerSubscription.startDate ? moment(customerSubscription.startDate) : undefined;
        customerSubscription.endDate = customerSubscription.endDate ? moment(customerSubscription.endDate) : undefined;
        customerSubscription.changeDate = customerSubscription.changeDate ? moment(customerSubscription.changeDate) : undefined;
      });
    }
    return res;
  }
}
