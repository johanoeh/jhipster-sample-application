import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustomerContactInfo } from 'app/shared/model/customer-contact-info.model';

type EntityResponseType = HttpResponse<ICustomerContactInfo>;
type EntityArrayResponseType = HttpResponse<ICustomerContactInfo[]>;

@Injectable({ providedIn: 'root' })
export class CustomerContactInfoService {
  public resourceUrl = SERVER_API_URL + 'api/customer-contact-infos';

  constructor(protected http: HttpClient) {}

  create(customerContactInfo: ICustomerContactInfo): Observable<EntityResponseType> {
    return this.http.post<ICustomerContactInfo>(this.resourceUrl, customerContactInfo, { observe: 'response' });
  }

  update(customerContactInfo: ICustomerContactInfo): Observable<EntityResponseType> {
    return this.http.put<ICustomerContactInfo>(this.resourceUrl, customerContactInfo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomerContactInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerContactInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
