/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { DonarTestModule } from '../../../test.module';
import { DonanteUpdateComponent } from 'app/entities/donante/donante-update.component';
import { DonanteService } from 'app/entities/donante/donante.service';
import { Donante } from 'app/shared/model/donante.model';

describe('Component Tests', () => {
  describe('Donante Management Update Component', () => {
    let comp: DonanteUpdateComponent;
    let fixture: ComponentFixture<DonanteUpdateComponent>;
    let service: DonanteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DonarTestModule],
        declarations: [DonanteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DonanteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DonanteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DonanteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Donante(123);
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
        const entity = new Donante();
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
