import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CustomerContactInfoComponent } from 'app/entities/customer-contact-info/customer-contact-info.component';
import { CustomerContactInfoService } from 'app/entities/customer-contact-info/customer-contact-info.service';
import { CustomerContactInfo } from 'app/shared/model/customer-contact-info.model';

describe('Component Tests', () => {
  describe('CustomerContactInfo Management Component', () => {
    let comp: CustomerContactInfoComponent;
    let fixture: ComponentFixture<CustomerContactInfoComponent>;
    let service: CustomerContactInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CustomerContactInfoComponent],
      })
        .overrideTemplate(CustomerContactInfoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerContactInfoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerContactInfoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CustomerContactInfo(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.customerContactInfos && comp.customerContactInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
