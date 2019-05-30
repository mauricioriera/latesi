/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DonarTestModule } from '../../../test.module';
import { HospitalDeleteDialogComponent } from 'app/entities/hospital/hospital-delete-dialog.component';
import { HospitalService } from 'app/entities/hospital/hospital.service';

describe('Component Tests', () => {
  describe('Hospital Management Delete Component', () => {
    let comp: HospitalDeleteDialogComponent;
    let fixture: ComponentFixture<HospitalDeleteDialogComponent>;
    let service: HospitalService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DonarTestModule],
        declarations: [HospitalDeleteDialogComponent]
      })
        .overrideTemplate(HospitalDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HospitalDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HospitalService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
