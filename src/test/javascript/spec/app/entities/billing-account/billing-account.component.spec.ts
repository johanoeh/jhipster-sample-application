import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BillingAccountComponent } from 'app/entities/billing-account/billing-account.component';
import { BillingAccountService } from 'app/entities/billing-account/billing-account.service';
import { BillingAccount } from 'app/shared/model/billing-account.model';

describe('Component Tests', () => {
  describe('BillingAccount Management Component', () => {
    let comp: BillingAccountComponent;
    let fixture: ComponentFixture<BillingAccountComponent>;
    let service: BillingAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BillingAccountComponent],
      })
        .overrideTemplate(BillingAccountComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillingAccountComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillingAccountService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BillingAccount(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.billingAccounts && comp.billingAccounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
