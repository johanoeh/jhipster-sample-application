import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BillingAccountDetailComponent } from 'app/entities/billing-account/billing-account-detail.component';
import { BillingAccount } from 'app/shared/model/billing-account.model';

describe('Component Tests', () => {
  describe('BillingAccount Management Detail Component', () => {
    let comp: BillingAccountDetailComponent;
    let fixture: ComponentFixture<BillingAccountDetailComponent>;
    const route = ({ data: of({ billingAccount: new BillingAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BillingAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BillingAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BillingAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load billingAccount on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.billingAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
