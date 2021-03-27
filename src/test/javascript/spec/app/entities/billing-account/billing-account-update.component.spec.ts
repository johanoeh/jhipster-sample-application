import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BillingAccountUpdateComponent } from 'app/entities/billing-account/billing-account-update.component';
import { BillingAccountService } from 'app/entities/billing-account/billing-account.service';
import { BillingAccount } from 'app/shared/model/billing-account.model';

describe('Component Tests', () => {
  describe('BillingAccount Management Update Component', () => {
    let comp: BillingAccountUpdateComponent;
    let fixture: ComponentFixture<BillingAccountUpdateComponent>;
    let service: BillingAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BillingAccountUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BillingAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillingAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillingAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BillingAccount(123);
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
        const entity = new BillingAccount();
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
