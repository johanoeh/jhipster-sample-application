import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BillingAccountService } from 'app/entities/billing-account/billing-account.service';
import { IBillingAccount, BillingAccount } from 'app/shared/model/billing-account.model';

describe('Service Tests', () => {
  describe('BillingAccount Service', () => {
    let injector: TestBed;
    let service: BillingAccountService;
    let httpMock: HttpTestingController;
    let elemDefault: IBillingAccount;
    let expectedResult: IBillingAccount | IBillingAccount[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BillingAccountService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new BillingAccount(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BillingAccount', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new BillingAccount()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BillingAccount', () => {
        const returnedFromService = Object.assign(
          {
            invoiceReference: 'BBBBBB',
            accountId: 'BBBBBB',
            primaryBillingProfileId: 'BBBBBB',
            exemptFromVat: true,
            invoiceContactPhone: 'BBBBBB',
            invoiceMethod: 'BBBBBB',
            invoiceEMailAddress: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BillingAccount', () => {
        const returnedFromService = Object.assign(
          {
            invoiceReference: 'BBBBBB',
            accountId: 'BBBBBB',
            primaryBillingProfileId: 'BBBBBB',
            exemptFromVat: true,
            invoiceContactPhone: 'BBBBBB',
            invoiceMethod: 'BBBBBB',
            invoiceEMailAddress: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a BillingAccount', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
