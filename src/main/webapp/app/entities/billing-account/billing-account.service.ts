import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBillingAccount } from 'app/shared/model/billing-account.model';

type EntityResponseType = HttpResponse<IBillingAccount>;
type EntityArrayResponseType = HttpResponse<IBillingAccount[]>;

@Injectable({ providedIn: 'root' })
export class BillingAccountService {
  public resourceUrl = SERVER_API_URL + 'api/billing-accounts';

  constructor(protected http: HttpClient) {}

  create(billingAccount: IBillingAccount): Observable<EntityResponseType> {
    return this.http.post<IBillingAccount>(this.resourceUrl, billingAccount, { observe: 'response' });
  }

  update(billingAccount: IBillingAccount): Observable<EntityResponseType> {
    return this.http.put<IBillingAccount>(this.resourceUrl, billingAccount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBillingAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBillingAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
