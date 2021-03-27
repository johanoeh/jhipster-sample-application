import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CustomerContactInfoDetailComponent } from 'app/entities/customer-contact-info/customer-contact-info-detail.component';
import { CustomerContactInfo } from 'app/shared/model/customer-contact-info.model';

describe('Component Tests', () => {
  describe('CustomerContactInfo Management Detail Component', () => {
    let comp: CustomerContactInfoDetailComponent;
    let fixture: ComponentFixture<CustomerContactInfoDetailComponent>;
    const route = ({ data: of({ customerContactInfo: new CustomerContactInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CustomerContactInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CustomerContactInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerContactInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customerContactInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerContactInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
