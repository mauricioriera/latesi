/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DonarTestModule } from '../../../test.module';
import { HospitalComponent } from 'app/entities/hospital/hospital.component';
import { HospitalService } from 'app/entities/hospital/hospital.service';
import { Hospital } from 'app/shared/model/hospital.model';

describe('Component Tests', () => {
  describe('Hospital Management Component', () => {
    let comp: HospitalComponent;
    let fixture: ComponentFixture<HospitalComponent>;
    let service: HospitalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DonarTestModule],
        declarations: [HospitalComponent],
        providers: []
      })
        .overrideTemplate(HospitalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HospitalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HospitalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Hospital(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.hospitals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
