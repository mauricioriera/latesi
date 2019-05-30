/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DonarTestModule } from '../../../test.module';
import { DonanteComponent } from 'app/entities/donante/donante.component';
import { DonanteService } from 'app/entities/donante/donante.service';
import { Donante } from 'app/shared/model/donante.model';

describe('Component Tests', () => {
  describe('Donante Management Component', () => {
    let comp: DonanteComponent;
    let fixture: ComponentFixture<DonanteComponent>;
    let service: DonanteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DonarTestModule],
        declarations: [DonanteComponent],
        providers: []
      })
        .overrideTemplate(DonanteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DonanteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DonanteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Donante(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.donantes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
