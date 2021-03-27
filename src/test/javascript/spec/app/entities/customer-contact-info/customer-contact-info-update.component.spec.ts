import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CustomerContactInfoUpdateComponent } from 'app/entities/customer-contact-info/customer-contact-info-update.component';
import { CustomerContactInfoService } from 'app/entities/customer-contact-info/customer-contact-info.service';
import { CustomerContactInfo } from 'app/shared/model/customer-contact-info.model';

describe('Component Tests', () => {
  describe('CustomerContactInfo Management Update Component', () => {
    let comp: CustomerContactInfoUpdateComponent;
    let fixture: ComponentFixture<CustomerContactInfoUpdateComponent>;
    let service: CustomerContactInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CustomerContactInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CustomerContactInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerContactInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerContactInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerContactInfo(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerContactInfo();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
